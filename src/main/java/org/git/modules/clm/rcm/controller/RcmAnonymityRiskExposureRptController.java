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
package org.git.modules.clm.rcm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.rcm.entity.RcmAnonymityRiskExposureRpt;
import org.git.modules.clm.rcm.service.IRcmAnonymityRiskExposureRptService;
import org.git.modules.clm.rcm.vo.RcmAnonymityRiskExposureRptVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 匿名客户风险暴露金额变动图 控制器
 *
 * @author git
 * @since 2019-12-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmanonymityriskexposurerpt")
@Api(value = "匿名客户风险暴露金额变动图", tags = "匿名客户风险暴露金额变动图接口")
public class RcmAnonymityRiskExposureRptController extends ChainController {

	private IRcmAnonymityRiskExposureRptService rcmAnonymityRiskExposureRptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmAnonymityRiskExposureRpt")
	public R<RcmAnonymityRiskExposureRpt> detail(RcmAnonymityRiskExposureRpt rcmAnonymityRiskExposureRpt) {
		RcmAnonymityRiskExposureRpt detail = rcmAnonymityRiskExposureRptService.getOne(Condition.getQueryWrapper(rcmAnonymityRiskExposureRpt));
		return R.data(detail);
	}

	/**
	 * 分页 匿名客户风险暴露金额变动图
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmAnonymityRiskExposureRpt")
	public R<IPage<RcmAnonymityRiskExposureRpt>> list(RcmAnonymityRiskExposureRpt rcmAnonymityRiskExposureRpt, Query query) {
		IPage<RcmAnonymityRiskExposureRpt> pages = rcmAnonymityRiskExposureRptService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmAnonymityRiskExposureRpt));
		return R.data(pages);
	}

	/**
	 * 自定义分页 匿名客户风险暴露金额变动图
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmAnonymityRiskExposureRpt")
	public R<IPage<RcmAnonymityRiskExposureRptVO>> page(RcmAnonymityRiskExposureRptVO rcmAnonymityRiskExposureRpt, Query query) {
		IPage<RcmAnonymityRiskExposureRptVO> pages = rcmAnonymityRiskExposureRptService.selectRcmAnonymityRiskExposureRptPage(Condition.getPage(query), rcmAnonymityRiskExposureRpt);
		return R.data(pages);
	}

	/**
	 * 新增 匿名客户风险暴露金额变动图
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmAnonymityRiskExposureRpt")
	public R save(@Valid @RequestBody RcmAnonymityRiskExposureRpt rcmAnonymityRiskExposureRpt) {
		return R.status(rcmAnonymityRiskExposureRptService.save(rcmAnonymityRiskExposureRpt));
	}

	/**
	 * 修改 匿名客户风险暴露金额变动图
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmAnonymityRiskExposureRpt")
	public R update(@Valid @RequestBody RcmAnonymityRiskExposureRpt rcmAnonymityRiskExposureRpt) {
		return R.status(rcmAnonymityRiskExposureRptService.updateById(rcmAnonymityRiskExposureRpt));
	}

	/**
	 * 新增或修改 匿名客户风险暴露金额变动图
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmAnonymityRiskExposureRpt")
	public R submit(@Valid @RequestBody RcmAnonymityRiskExposureRpt rcmAnonymityRiskExposureRpt) {
		return R.status(rcmAnonymityRiskExposureRptService.saveOrUpdate(rcmAnonymityRiskExposureRpt));
	}


	/**
	 * 删除 匿名客户风险暴露金额变动图
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmAnonymityRiskExposureRptService.removeByIds(Func.toLongList(ids)));
	}


}
