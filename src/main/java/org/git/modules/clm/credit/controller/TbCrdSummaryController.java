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
import org.git.modules.clm.credit.entity.TbCrdSummary;
import org.git.modules.clm.credit.vo.TbCrdSummaryVO;
import org.git.modules.clm.credit.service.ITbCrdSummaryService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 借据信息表 控制器
 *
 * @author git
 * @since 2019-11-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdsummary")
@Api(value = "借据信息表", tags = "借据信息表接口")
public class TbCrdSummaryController extends ChainController {

	private ITbCrdSummaryService tbCrdSummaryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdSummary")
	public R<TbCrdSummary> detail(TbCrdSummary tbCrdSummary) {
		TbCrdSummary detail = tbCrdSummaryService.getOne(Condition.getQueryWrapper(tbCrdSummary));
		return R.data(detail);
	}

	/**
	 * 分页 借据信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdSummary")
	public R<IPage<TbCrdSummary>> list(TbCrdSummary tbCrdSummary, Query query) {
		IPage<TbCrdSummary> pages = tbCrdSummaryService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdSummary));
		return R.data(pages);
	}

	/**
	 * 自定义分页 借据信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdSummary")
	public R<IPage<TbCrdSummaryVO>> page(TbCrdSummaryVO tbCrdSummary, Query query) {
		IPage<TbCrdSummaryVO> pages = tbCrdSummaryService.selectTbCrdSummaryPage(Condition.getPage(query), tbCrdSummary);
		return R.data(pages);
	}

	/**
	 * 新增 借据信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdSummary")
	public R save(@Valid @RequestBody TbCrdSummary tbCrdSummary) {
		return R.status(tbCrdSummaryService.save(tbCrdSummary));
	}

	/**
	 * 修改 借据信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdSummary")
	public R update(@Valid @RequestBody TbCrdSummary tbCrdSummary) {
		return R.status(tbCrdSummaryService.updateById(tbCrdSummary));
	}

	/**
	 * 新增或修改 借据信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdSummary")
	public R submit(@Valid @RequestBody TbCrdSummary tbCrdSummary) {
		return R.status(tbCrdSummaryService.saveOrUpdate(tbCrdSummary));
	}


	/**
	 * 删除 借据信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdSummaryService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 借据详细（带有客户名称）
	 * @param summaryNum
	 * @return
	 */
	@GetMapping("/summaryDetail")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "详情(客户名称)", notes = "传入summaryId")
	public R<TbCrdSummaryVO> summaryDetail(@ApiParam( value = "借据ID",required = true)String summaryNum) {
		TbCrdSummaryVO detail = tbCrdSummaryService.selectSummaryDetailByCusNum(summaryNum);
		return R.data(detail);
	}


	/**
	 * 借据详情 （从 额度台账 来）
	 */
	@GetMapping("/getSummaryListFromCusNum")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "分页", notes = "传入customerNum")
	public R<IPage<TbCrdSummaryVO>> getSummaryListFromCusNum(@ApiParam(value = "客户编号",required = true) String customerNum,@ApiParam(value = "经办机构",required = true) String orgNum,Query query) {
		IPage<TbCrdSummaryVO> pages = tbCrdSummaryService.selectSummaryListFromCusNum(Condition.getPage(query), customerNum,orgNum);
		return R.data(pages);
	}

}
