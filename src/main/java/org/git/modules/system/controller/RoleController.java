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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.common.cache.SysCache;
import org.git.core.mp.support.Query;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.Dict;
import org.git.modules.system.entity.Role;
import org.git.modules.system.service.IDeptService;
import org.git.modules.system.service.IRoleService;
import org.git.modules.system.vo.DeptVO;
import org.git.modules.system.vo.GrantVO;
import org.git.modules.system.vo.RoleVO;
import org.git.core.boot.ctrl.ChainController;
import org.git.common.constant.AppConstant;
import org.git.core.mp.support.Condition;
import org.git.core.secure.ChainUser;
import org.git.core.secure.annotation.PreAuth;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.constant.RoleConstant;
import org.git.core.tool.node.INode;
import org.git.core.tool.utils.Func;
import org.git.modules.system.wrapper.DeptWrapper;
import org.git.modules.system.wrapper.RoleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/role")
@Api(value = "角色", tags = "角色")
//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class RoleController extends ChainController {

	private IRoleService roleService;

	@Autowired
	private IDeptService deptService;
	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role, ChainUser chainUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!chainUser.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, chainUser.getTenantId()) : queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}


	/**
	 * 列表(新框架使用)
	 */
	@PostMapping("/getList")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<IPage<RoleVO>> getList(RoleVO role, Query query, ChainUser chainUser) {
		IPage<RoleVO> pages = roleService.selectRolePage(Condition.getPage(query),role);
		return R.data(pages);

	}


	/**
	 * 获取角色树形结构
	 */
	@PostMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree(String tenantId, ChainUser chainUser) {
		List<RoleVO> tree = roleService.tree(Func.toStrWithEmpty(tenantId, chainUser.getTenantId()));
		return R.data(tree);
	}


	/**
	 * 获取角色树形结构
	 */
	@PostMapping("/treeByOrgLevel")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "通过级别获取角色树型列表", notes = "通过级别获取角色树型列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgLevel", value = "机构级别", paramType = "query", dataType = "string"),
	})
	public R<List<RoleVO>> treeByOrgLevel(String orgLevel) {
		List<RoleVO> tree = roleService.treeByOrgLevel(orgLevel);
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role, ChainUser user) {
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(user.getTenantId());
		}
		return R.status(roleService.submit(role));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.removeByIds(Func.toStrList(ids)));
	}

	/**
	 * 设置角色权限
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@RequestBody GrantVO grantVO) {
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds(), grantVO.getDataScopeIds(), grantVO.getApiScopeIds());
		return R.status(temp);
	}

	/**
	 * 按等级获取角色
	 */
	@PostMapping("/roleByLevel")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R getRoleByLevel(String deptId) {
		Dept dept = deptService.getById(deptId);
		List<Role> data = roleService.list(Wrappers.<Role>query().lambda().eq(Role::getDeptLevel,dept.getDeptLevel()));
		return R.data(data);
	}


}
