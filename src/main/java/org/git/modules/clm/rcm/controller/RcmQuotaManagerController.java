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
import lombok.AllArgsConstructor;
import org.git.modules.clm.rcm.service.IRcmConfigManagerService;
import org.git.modules.clm.rcm.vo.*;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 限额详细信息表 控制器
 *
 * @author liuye
 * @since 2019-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmConfigurationManager")
@Api(value = "限额详细信息表", tags = "限额详细信息表接口")
public class RcmQuotaManagerController extends ChainController {

	private IRcmConfigManagerService rcmConfigurationManagerService;

	/**
	 * 自定义分页 限额管理列表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationManager")
	public R<IPage<RcmQuotaManagerVO>> page(RcmQuotaManagerVO rcmConfigurationManager, Query query) {
		IPage<RcmQuotaManagerVO> pages = rcmConfigurationManagerService.selectRcmConfigurationManager(Condition.getPage(query), rcmConfigurationManager);
		return R.data(pages);
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "详情", notes = "传入rcmConfigurationManager")
	public R<RcmQuotaManagerVO> detail(RcmQuotaManagerVO rcmConfigurationManager) {
		return R.data(rcmConfigurationManagerService.detail(rcmConfigurationManager));
	}

	/**
	 * 新增 限额详细信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入rcmConfigurationManager")
	public R save(@Valid @RequestBody RcmQuotaManagerVO rcmConfigurationManager) {

		return R.status(rcmConfigurationManagerService.addRcmConfigInfoAndPara(rcmConfigurationManager));
	}

	/**
	 * 修改 限额详细信息历史表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "rcmConfigurationManager")
	public R update(@Valid @RequestBody RcmQuotaManagerVO rcmConfigurationManager) {
		return R.status(rcmConfigurationManagerService.updateRcmConfigInfoAndPara(rcmConfigurationManager));
	}

	/**
	 * 历史记录(初始页面，包含现在值和修改记录列表)
	 */
	@GetMapping("/hisList")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "历史记录(初始页面，包含现在值和修改记录列表)", notes = "传入rcmConfigurationInfoHis")
	public R<RcmQuotaManagerHisVO> hisList(@RequestParam String quotaNum, Query query) {
		return R.data(rcmConfigurationManagerService.hisList(Condition.getPage(query),quotaNum));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入quotaNum")
	public R remove(@RequestParam String quotaNum) {
		return R.status(rcmConfigurationManagerService.removeById(quotaNum));
	}
}
