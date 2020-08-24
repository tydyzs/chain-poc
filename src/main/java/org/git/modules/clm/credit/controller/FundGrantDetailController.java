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
import org.git.modules.clm.credit.entity.FundGrantDetail;
import org.git.modules.clm.credit.vo.FundGrantDetailVO;
import org.git.modules.clm.credit.service.IFundGrantDetailService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金授信事件明细 控制器
 *
 * @author liuye
 * @since 2019-11-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-crd/fundgrantdetail")
@Api(value = "资金授信事件明细", tags = "资金授信事件明细接口")
public class FundGrantDetailController extends ChainController {

	private IFundGrantDetailService fundGrantDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundGrantDetail")
	public R<FundGrantDetail> detail(FundGrantDetail fundGrantDetail) {
		FundGrantDetail detail = fundGrantDetailService.getOne(Condition.getQueryWrapper(fundGrantDetail));
		return R.data(detail);
	}

	/**
	 * 分页 资金授信事件明细
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundGrantDetail")
	public R<IPage<FundGrantDetail>> list(FundGrantDetail fundGrantDetail, Query query) {
		IPage<FundGrantDetail> pages = fundGrantDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(fundGrantDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金授信事件明细
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundGrantDetail")
	public R<IPage<FundGrantDetailVO>> page(FundGrantDetailVO fundGrantDetail, Query query) {
		IPage<FundGrantDetailVO> pages = fundGrantDetailService.selectFundGrantDetailPage(Condition.getPage(query), fundGrantDetail);
		return R.data(pages);
	}

	/**
	 * 新增 资金授信事件明细
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundGrantDetail")
	public R save(@Valid @RequestBody FundGrantDetail fundGrantDetail) {
		return R.status(fundGrantDetailService.save(fundGrantDetail));
	}

	/**
	 * 修改 资金授信事件明细
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundGrantDetail")
	public R update(@Valid @RequestBody FundGrantDetail fundGrantDetail) {
		return R.status(fundGrantDetailService.updateById(fundGrantDetail));
	}

	/**
	 * 新增或修改 资金授信事件明细
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundGrantDetail")
	public R submit(@Valid @RequestBody FundGrantDetail fundGrantDetail) {
		return R.status(fundGrantDetailService.saveOrUpdate(fundGrantDetail));
	}

	
	/**
	 * 删除 资金授信事件明细
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundGrantDetailService.removeByIds(Func.toLongList(ids)));
	}

	
}
