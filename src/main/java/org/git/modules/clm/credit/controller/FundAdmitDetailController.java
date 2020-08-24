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
import org.git.modules.clm.credit.entity.FundAdmitDetail;
import org.git.modules.clm.credit.vo.FundAdmitDetailVO;
import org.git.modules.clm.credit.service.IFundAdmitDetailService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金客户状态维护明细 控制器
 *
 * @author git
 * @since 2019-11-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/fundadmitdetail")
@Api(value = "资金客户状态维护明细", tags = "资金客户状态维护明细接口")
public class FundAdmitDetailController extends ChainController {

	private IFundAdmitDetailService fundAdmitDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundAdmitDetail")
	public R<FundAdmitDetail> detail(FundAdmitDetail fundAdmitDetail) {
		FundAdmitDetail detail = fundAdmitDetailService.getOne(Condition.getQueryWrapper(fundAdmitDetail));
		return R.data(detail);
	}

	/**
	 * 分页 资金客户状态维护明细
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundAdmitDetail")
	public R<IPage<FundAdmitDetail>> list(FundAdmitDetail fundAdmitDetail, Query query) {
		IPage<FundAdmitDetail> pages = fundAdmitDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(fundAdmitDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金客户状态维护明细
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundAdmitDetail")
	public R<IPage<FundAdmitDetailVO>> page(FundAdmitDetailVO fundAdmitDetail, Query query) {
		IPage<FundAdmitDetailVO> pages = fundAdmitDetailService.selectFundAdmitDetailPage(Condition.getPage(query), fundAdmitDetail);
		return R.data(pages);
	}

	/**
	 * 新增 资金客户状态维护明细
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundAdmitDetail")
	public R save(@Valid @RequestBody FundAdmitDetail fundAdmitDetail) {
		return R.status(fundAdmitDetailService.save(fundAdmitDetail));
	}

	/**
	 * 修改 资金客户状态维护明细
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundAdmitDetail")
	public R update(@Valid @RequestBody FundAdmitDetail fundAdmitDetail) {
		return R.status(fundAdmitDetailService.updateById(fundAdmitDetail));
	}

	/**
	 * 新增或修改 资金客户状态维护明细
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundAdmitDetail")
	public R submit(@Valid @RequestBody FundAdmitDetail fundAdmitDetail) {
		return R.status(fundAdmitDetailService.saveOrUpdate(fundAdmitDetail));
	}

	
	/**
	 * 删除 资金客户状态维护明细
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundAdmitDetailService.removeByIds(Func.toLongList(ids)));
	}

	
}
