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
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.common.cache.SysCache;
import org.git.common.constant.AppConstant;
import org.git.common.utils.CommonUtil;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.git.modules.system.service.IUserRoleService;
import org.git.modules.system.vo.DeptVO;
import org.git.modules.system.wrapper.DeptWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
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
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/dept")
@Api(value = "部门", tags = "部门")
//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class DeptController extends ChainController {

	private IDeptService deptService;

	private IUserRoleService userRoleService;

	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dept")
	public R<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return R.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dept")
	public R<IPage<DeptVO>> list(@ApiIgnore @RequestParam Map<String, Object> dept, Query query, ChainUser chainUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		IPage<Dept> pages = deptService.page(Condition.getPage(query), (!chainUser.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, chainUser.getTenantId()) : queryWrapper);
		return R.data(DeptWrapper.build().pageVO(pages));
	}

	/**
	 * 获取部门树形结构
	 */
	@PostMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DeptVO>> tree(String tenantId, ChainUser chainUser) {
		List<DeptVO> tree = deptService.tree(Func.toStrWithEmpty(tenantId, chainUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 获取部门树形结构（仅管理机构）
	 */
	@PostMapping("/treeManage2")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DeptVO>> treeManage2(String tenantId, ChainUser chainUser) {
		List<DeptVO> tree = deptService.treeManage2(Func.toStrWithEmpty(tenantId, chainUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 获取部门树形结构（仅管理机构）?
	 */
	@PostMapping("/treeManage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DeptVO>> treeManage(String orgNum,ChainUser user) {
		if (StringUtil.isEmpty(orgNum)) {
			orgNum = user.getDeptId();
		}
		Dept dept = SysCache.getDept(orgNum);
		String isManage = "0";
		if(dept.getOrgType().equals("1") || dept.getOrgType().equals("2")){
			isManage = "1";
		}
		List<DeptVO> tree = deptService.treeManage(orgNum,isManage);
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入dept")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R submit(@Valid @RequestBody Dept dept, ChainUser user) {
		if (Func.isEmpty(dept.getId())) {
			dept.setTenantId(user.getTenantId());
		}
		return R.status(deptService.submit(dept));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deptService.removeDept(ids));
	}

	/**
	 * 详情（分页）
	 */
	@PostMapping("/getList")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "详情（分页）", notes = "传入dept")
	public R<IPage<DeptVO>> list(DeptVO dept,Query query, ChainUser chainUser) {
		List<String> loginUserDeptIds = new ArrayList<String>();
//		获取当前用户登录机构下的所有下级机构信息
		loginUserDeptIds.add(chainUser.getDeptId());
//		获取当前用户所有挂职机构的所有下级机构信息
//		List<UserRoleVO> userRoleVos =  userRoleService.deptsByUserId(chainUser.getUserId());
//		for (UserRoleVO vo :userRoleVos ) {
//			loginUserDeptIds.add(vo.getDeptId());
//		}
		IPage<DeptVO> pages = deptService.selectDeptPage(Condition.getPage(query),dept,loginUserDeptIds);
		return R.data(pages);
	}
}
