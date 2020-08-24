/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
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
 *  Author: 高伟达武汉事业部
 */
package org.git.modules.clm.loan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.loan.vo.CrdMainVO;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.loan.entity.CsmGroupMember;
import org.git.modules.clm.loan.vo.CsmGroupMemberVO;
import org.git.modules.clm.loan.service.ICsmGroupMemberService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 集团成员表 控制器
 *
 * @author git
 * @since 2019-12-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-loan/csmGroupMember")
@Api(value = "集团成员表", tags = "集团成员表接口")
public class CsmGroupMemberController extends ChainController {

	private ICsmGroupMemberService csmGroupMemberService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmGroupMember")
	public R<CsmGroupMember> detail(CsmGroupMember csmGroupMember) {
		CsmGroupMember detail = csmGroupMemberService.getOne(Condition.getQueryWrapper(csmGroupMember));
		return R.data(detail);
	}

	/**
	 * 分页 集团成员表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmGroupMember")
	public R<IPage<CsmGroupMember>> list(CsmGroupMember csmGroupMember, Query query) {
		IPage<CsmGroupMember> pages = csmGroupMemberService.page(Condition.getPage(query), Condition.getQueryWrapper(csmGroupMember));
		return R.data(pages);
	}

	/**
	 * 自定义分页 集团成员表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmGroupMember")
	public R<IPage<CsmGroupMemberVO>> page(CsmGroupMemberVO csmGroupMember, Query query) {
		IPage<CsmGroupMemberVO> pages = csmGroupMemberService.selectCsmGroupMemberPage(Condition.getPage(query), csmGroupMember);
		return R.data(pages);
	}

	/**
	 * 新增 集团成员表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmGroupMember")
	public R save(@Valid @RequestBody CsmGroupMember csmGroupMember) {
		return R.status(csmGroupMemberService.save(csmGroupMember));
	}

	/**
	 * 修改 集团成员表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmGroupMember")
	public R update(@Valid @RequestBody CsmGroupMember csmGroupMember) {
		return R.status(csmGroupMemberService.updateById(csmGroupMember));
	}

	/**
	 * 新增或修改 集团成员表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmGroupMember")
	public R submit(@Valid @RequestBody CsmGroupMember csmGroupMember) {
		return R.status(csmGroupMemberService.saveOrUpdate(csmGroupMember));
	}

	
	/**
	 * 删除 集团成员表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmGroupMemberService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询集团成员信息
	 * @param query
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/findCsmGroupMemberPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "集团成员信息", notes = "传入customerNum")
	public R<IPage<CsmGroupMemberVO>> findCsmGroupMemberPage(CsmGroupMemberVO csmGroupMemberVO,
															 @ApiParam(value = "主办机构", required = true)String orgNum,
															 Query query) {
		IPage<CsmGroupMemberVO> pages = csmGroupMemberService.findCsmGroupMemberPage(Condition.getPage(query),csmGroupMemberVO,orgNum);
		return R.data(pages);
	}


	/**
	 * 查询集团客户信息
	 * @param query
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/listCsmGroupMemberPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "集团客户信息", notes = "传入customerNum")
	public R<IPage<CsmGroupMemberVO>> listCsmGroupMemberPage(CsmGroupMemberVO csmGroupMemberVO,
															 @ApiParam(value = "主办机构", required = true)String orgNum,
															 Query query) {
		IPage<CsmGroupMemberVO> pages = csmGroupMemberService.listCsmGroupMemberPage(Condition.getPage(query),csmGroupMemberVO,orgNum);
		return R.data(pages);
	}
}
