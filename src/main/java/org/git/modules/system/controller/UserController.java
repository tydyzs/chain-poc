/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.common.constant.CommonConstant;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.utils.Func;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserRole;
import org.git.modules.system.service.IUserRoleService;
import org.git.modules.system.service.IUserService;
import org.git.modules.system.vo.UserRoleVO;
import org.git.modules.system.vo.UserVO;
import org.git.modules.system.wrapper.UserWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.USER_CACHE;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@RequestMapping(AppConstant.APPLICATION_USER_NAME)
@Api(value = "用户", tags = "用户")
@AllArgsConstructor
public class UserController {

	private IUserService userService;
	private IUserRoleService userRoleService;

	/**
	 * 通过Id查询用户信息
	 */
	@ApiOperationSupport(order = 01)
	@ApiOperation(value = "查看用户详情", notes = "传入id")
	@PostMapping("/detail")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		UserVO userVO = null;
		if(detail!=null){
			userVO = UserWrapper.build().entityVO(detail);
		}
		return R.data(userVO);
	}

	/**
	 * 查看当前登录用户信息
	 */
	@ApiOperationSupport(order = 02)
	@ApiOperation(value = "查看当前登录人信息")
	@PostMapping("/info")
	public R<UserVO> info(ChainUser user) {
		User detail = userService.getById(user.getUserId());
		detail.setDeptId(user.getDeptId());
		detail.setRoleId(user.getRoleId());
		UserVO userVO = null;
		if(detail!=null){
			userVO = UserWrapper.build().entityVO(detail);
		}
		return R.data(userVO);
	}

	/**
	 * 用户列表分面（旧）
	 */
	@PostMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 03)
	@ApiOperation(value = "用户列表(分页)", notes = "传入account和realName")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query, ChainUser chainUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		IPage<User> pages = userService.page(Condition.getPage(query), (!chainUser.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, chainUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 用户列表分页（新）
	 * 查询当前登录机构下的所有机构 + 查询条件过滤
	 */
	@PostMapping("/getList")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "用户名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "deptId", value = "所属机构", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 03)
	@ApiOperation(value = "用户列表(分页)", notes = "传入account和realName")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<UserVO>> getList(UserVO user, Query query, ChainUser chainUser) {
//		获取当前用户登录机构的所有下级机构的用户信息
		List<String> loginUserDeptIds = new ArrayList<String>();
		loginUserDeptIds.add(chainUser.getDeptId());
		user.setDeptId(chainUser.getDeptId());
//		获取当前用户所有挂职机构的所有下级机构的用户信息
//		List<UserRoleVO> userRoleVos =  userRoleService.deptsByUserId(chainUser.getUserId());
//		for (UserRoleVO vo :userRoleVos ) {
//			loginUserDeptIds.add(vo.getDeptId());
//		}
		IPage<UserVO> pages = userService.selectUserPage(Condition.getPage(query),user,loginUserDeptIds);
		return R.data(pages);

	}


	/**
	 * 新增用户
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 04)
	@ApiOperation(value = "用户新增", notes = "传入User")
//	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@CacheEvict(cacheNames = {USER_CACHE}, allEntries = true)
	public R submit(@Valid @RequestBody User user,ChainUser chainUser) {
		user.setTenantId(SecureUtil.getTenantId());
		if (Func.isBlank((user.getPassword()))) {
			user.setPassword(CommonConstant.DEFAULT_PASSWORD);
			user.setDeptId(chainUser.getDeptId());
		}
		userService.submit(user);
		return R.data(user);
	}

	/**
	 * 修改用户
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 05)
	@ApiOperation(value = "用户修改", notes = "传入User")
	@CacheEvict(cacheNames = {USER_CACHE}, allEntries = true)
	public R update(@Valid @RequestBody User user) {
		return R.status(userService.updateUser(user));
	}

	/**
	 * 删除用户
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 06)
	@ApiOperation(value = "用户删除", notes = "传入id集合")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@CacheEvict(cacheNames = {USER_CACHE}, allEntries = true)
	public R remove(@RequestParam String ids) {
		return R.status(userService.removeUser(ids));
	}

	/**
	 * 设置菜单权限
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 07)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
				   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return R.status(temp);
	}

	/**
	 * 用户角色列表
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/user-role-list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "用户角色列表", notes = "传入user")
	public R<List<User>> userRoleList(User user) {
		List<User> list = userService.list(Condition.getQueryWrapper(user));
		return R.data(list);
	}

	/**
	 * 获取用户角色列表_新
	 *
	 * @param userId
	 * @return
	 */
	@PostMapping("/user-role-list-new")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "用户角色列表（新）", notes = "传入用户ID")
	public R<List<UserRoleVO>> userRoleListNew(@ApiParam(value = "用户ID", required = true) @RequestParam(required = true) String userId) {
		List<UserRoleVO> usrRoleList = userRoleService.deptsByUserId(userId);
		return R.data(usrRoleList);
	}

	/**
	 * 添加用户角色
	 *
	 * @param userRole
	 * @return
	 */
	@PostMapping("/save-user-role")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "添加用户角色", notes = "传入userRole")
	public R saveUserRole(@Valid @RequestBody UserRole userRole) {
		UserRole persistUserRole = userRoleService.getUserRoleByUserIdAndDeptId(userRole.getUserId(), userRole.getDeptId());
		if (null != persistUserRole) {
			throw new ApiException("用户机构已存在！");
		}
		userRole.setIsMasterOrg(0);
		return R.status(userRoleService.save(userRole));
	}


	/**
	 * 更新用户角色
	 *
	 * @param userRole
	 * @return
	 */
	@PostMapping("/update-user-role")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "修改用户角色", notes = "传入userRole")
	public R updateUserRole(@Valid @RequestBody UserRole userRole) {
		return R.status(userRoleService.updateById(userRole));
	}

	/**
	 * 通过用户角色ID获取实体对象
	 *
	 * @param userRoleId
	 * @return UserRole
	 */
	@PostMapping("/user-role")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "通过ID获取用户角色", notes = "传入userRoleId")
	public R getUserRoleById(String userRoleId) {
		return R.data(userRoleService.getById(userRoleId));
	}


	/**
	 * 主机构切换
	 * @param userRoleId
	 * @return
	 */
	@PostMapping("/update-master-org")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "主机构切换", notes = "传入userRole")
	public R masterOrgSwitch(String userRoleId) {
		UserRole userRole = userRoleService.getById(userRoleId);
		return R.status(userRoleService.masterOrgSwitch(userRole));
	}



	/**
	 * 删除用户角色
	 *
	 * @param userRoleId
	 * @return
	 */
	@PostMapping("/remove-user-role")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "删除用户角色", notes = "传入userRoleId")
	public R removeRoleList(String userRoleId) {
		return R.status(userRoleService.removeById(userRoleId));
	}


	/**
	 * 重置密码
	 *
	 * @param userIds
	 * @return
	 */
	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return R.status(temp);
	}

	/**
	 * 修改密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	@PostMapping("/update-password")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "修改密码", notes = "传入密码")
	public R updatePassword(ChainUser user, @ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return R.status(temp);
	}

	/**
	 * 用户列表
	 * @param user
	 * @return
	 */
	@PostMapping("/user-list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "用户列表（不分页）", notes = "传入user")
	public R<List<User>> userList(User user) {
		List<User> list = userService.list(Condition.getQueryWrapper(user));
		return R.data(list);
	}

	/**
	 * 用户列表（不分页）
	 *
	 * @author chenchuan
	 */
	@PostMapping("/list-All")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "列表（不分页）（只返回用户account和用户名称的键值对）", notes = "无参数")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<Map<String, String>> listAll() {
		Map<String, String> userMap = userService.listAll();
		return R.data(userMap);
	}

	/**
	 * 用户选择器
	 *
	 * @param user
	 * @return
	 * @author chenchuan
	 */
	@PostMapping("/select-user")
	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "系统通用的用户选择器（分页）", notes = "传入user对象")
	public R<IPage<Map>> List(@ApiParam(value = "user对象", required = false) User user, Query query) {
		IPage<Map> pages = userService.selectUserList(Condition.getPage(query), user);
		return R.data(pages);
	}


}
