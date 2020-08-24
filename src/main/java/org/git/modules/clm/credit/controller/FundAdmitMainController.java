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
package org.git.modules.clm.credit.controller;

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
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.credit.entity.FundAdmitMain;
import org.git.modules.clm.credit.vo.FundAdmitMainVO;
import org.git.modules.clm.credit.service.IFundAdmitMainService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金客户状态维护主表 控制器
 *
 * @author git
 * @since 2019-11-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/fundadmitmain")
@Api(value = "资金客户状态维护主表", tags = "资金客户状态维护主表接口")
public class FundAdmitMainController extends ChainController {

	private IFundAdmitMainService fundAdmitMainService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundAdmitMain")
	public R<FundAdmitMain> detail(FundAdmitMain fundAdmitMain) {
		FundAdmitMain detail = fundAdmitMainService.getOne(Condition.getQueryWrapper(fundAdmitMain));
		return R.data(detail);
	}

	/**
	 * 分页 资金客户状态维护主表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundAdmitMain")
	public R<IPage<FundAdmitMain>> list(FundAdmitMain fundAdmitMain, Query query) {
		IPage<FundAdmitMain> pages = fundAdmitMainService.page(Condition.getPage(query), Condition.getQueryWrapper(fundAdmitMain));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金客户状态维护主表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundAdmitMain")
	public R<IPage<FundAdmitMainVO>> page(FundAdmitMainVO fundAdmitMain, Query query) {
		IPage<FundAdmitMainVO> pages = fundAdmitMainService.selectFundAdmitMainPage(Condition.getPage(query), fundAdmitMain);
		return R.data(pages);
	}

	/**
	 * 新增 资金客户状态维护主表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundAdmitMain")
	public R save(@Valid @RequestBody FundAdmitMain fundAdmitMain) {
		return R.status(fundAdmitMainService.save(fundAdmitMain));
	}

	/**
	 * 修改 资金客户状态维护主表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundAdmitMain")
	public R update(@Valid @RequestBody FundAdmitMain fundAdmitMain) {
		return R.status(fundAdmitMainService.updateById(fundAdmitMain));
	}

	/**
	 * 新增或修改 资金客户状态维护主表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundAdmitMain")
	public R submit(@Valid @RequestBody FundAdmitMain fundAdmitMain) {
		return R.status(fundAdmitMainService.saveOrUpdate(fundAdmitMain));
	}

	
	/**
	 * 删除 资金客户状态维护主表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundAdmitMainService.removeByIds(Func.toLongList(ids)));
	}

	
}
