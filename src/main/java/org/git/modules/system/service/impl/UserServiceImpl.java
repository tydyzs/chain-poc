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
package org.git.modules.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.SysCache;
import org.git.common.constant.CommonConstant;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.base.BaseServiceImpl;
import org.git.core.mp.support.Condition;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.DigestUtil;
import org.git.core.tool.utils.Func;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserInfo;
import org.git.modules.system.entity.UserRole;
import org.git.modules.system.mapper.UserMapper;
import org.git.modules.system.mapper.UserRoleMapper;
import org.git.modules.system.service.IUserService;
import org.git.modules.system.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public boolean submit(User user) {
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, Func.toStr(user.getTenantId(), ChainConstant.ADMIN_TENANT_ID)).eq(User::getAccount, user.getAccount()));
		if (cnt > 0 && Func.isEmpty(user.getId())) {
			throw new ApiException("当前用户已存在!");
		}
		return save(user);
	}

	@Override
	public boolean updateUser(User user) {
		user.setPassword(null);
		return updateById(user);
	}

	@Override
	public IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user, List<String> loginUserDeptIds) {
		IPage<UserVO> pageData = page.setRecords(baseMapper.selectUserPage(page, user,loginUserDeptIds.toArray(new String[loginUserDeptIds.size()])));
		//把多个角色id通过缓存翻译成角色名称
		pageData.getRecords().stream().map(vo ->{
			if(vo != null && StringUtils.isNotBlank(vo.getRoleId())){
				vo.setRoleName(Arrays.asList(vo.getRoleId().split(",")).stream().map(str -> SysCache.getRoleName(str)).collect(Collectors.joining(",")));
			}
			return vo;
		}).collect(Collectors.toList());
		return pageData;
	}

	@Override
	public UserInfo userInfo(String userId, String deptId) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.selectById(userId);
		user.setDeptId(deptId);

		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			List<String> roleAlias = new ArrayList<>();
			if (StringUtils.isNotBlank(deptId)) {
				UserRole userRole = userRoleMapper.selectOne(Wrappers.<UserRole>query().lambda().in(UserRole::getUserId, userId.toString()).in(UserRole::getDeptId, deptId));
				roleAlias = SysCache.getRoleAliases(userRole.getRoleId());
				user.setRoleId(userRole.getRoleId());
			} else {
				roleAlias = SysCache.getRoleAliases(user.getRoleId());
			}
			userInfo.setRoles(roleAlias);
		}
		return userInfo;
	}

	@Override
	public User user(String tenantId, String account) {
		UserInfo userInfo = new UserInfo();
		return baseMapper.selectOne(Wrappers.<User>query().lambda().in(User::getTenantId, tenantId).in(User::getAccount, account).eq(User::getIsDeleted,0));
	}

	@Override
	public UserVO getUserByAccount(String account){
		return baseMapper.getUserByAccount(account);
	}

	@Override
	public UserInfo userInfo(String tenantId, String account, String password, String deptId) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.getUser(tenantId, account, password);
		if (Func.isNotEmpty(user)) {
			UserRole userRole = null;
			if (StringUtils.isNotBlank(deptId)) {
				userRole = userRoleMapper.selectOne(Wrappers.<UserRole>query().lambda().in(UserRole::getUserId, user.getId().toString()).in(UserRole::getDeptId, deptId));
			}
			//兼容之前的权限，如果设置了用户机构角色，就取用户机构角色信息，否则还是从用户信息的角色列表
			if (Func.isNotEmpty(userRole)) {
				user.setDeptId(userRole.getDeptId());
				user.setRoleId(userRole.getRoleId());
				userInfo.setUserOrgRole(userRole);
			}
			List<String> roleAlias = SysCache.getRoleAliases(user.getRoleId());
			userInfo.setRoles(roleAlias);
		}
		userInfo.setUser(user);
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toStrList(userIds)));
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(DateUtil.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toStrList(userIds)));
	}

	@Override
	public boolean updatePassword(String userId, String oldPassword, String newPassword, String newPassword1) {
		User user = getById(userId);
		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
	}

	@Override
	public boolean removeUser(String userIds) {
		if (Func.contains(Func.toStrArray(userIds), SecureUtil.getUserId())) {
			throw new ApiException("不能删除本账号!");
		}
		return deleteLogic(Func.toStrList(userIds));
	}


	@Override
	public Map<String, String> listAll() {
		Map<String, String> userMap = new HashMap<String, String>();
		List<User> userList = baseMapper.selectList(Condition.getQueryWrapper(new User()));
		for (User user : userList) {
			userMap.put(user.getAccount(), user.getName());
		}
		return userMap;
	}

	@Override
	public IPage<Map> selectUserList(IPage<Map> page, User user) {
		return page.setRecords(baseMapper.selectUserList(page, user));
	}

	/**
	 * 获取全量用户数据
	 *
	 * @return
	 */
	public List<User> selectAllUser() {
		return baseMapper.selectList(Condition.getQueryWrapper(new User()));
	}


}
