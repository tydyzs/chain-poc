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
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.service.IRcmIndexService;
import org.git.modules.clm.rcm.service.IRcmIndexFullService;
import org.git.modules.clm.rcm.vo.RcmIndexVO;
import org.git.modules.clm.rcm.vo.RcmIndexFullVO;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liuye
 * @since 2019-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmConfigurationQuota")
@Api(value = "限额基础指标库表，信贷业务限额基础指标表，同业业务限额基础指标库表", tags = "接口")
public class RcmIndexFullController extends ChainController {

	private IRcmIndexService rcmConfigurationBaseService;

	private IRcmIndexFullService rcmConfigurationService;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmConfigurationBase")
	public R<RcmIndexFullVO> detail(RcmIndex rcmIndex) {

		RcmIndexFullVO detail = rcmConfigurationService.selectRcmConfigDetail(rcmIndex.getQuotaIndexNum());
		detail.setOrgNum(SysCache.getDeptName(detail.getOrgNum()));
		detail.setUserNum(UserCache.getUser(detail.getUserNum()).getName());
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationBase")
	public R<IPage<RcmIndex>> list(RcmIndex rcmIndex, Query query) {
		IPage<RcmIndex> pages = rcmConfigurationBaseService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmIndex));
		return R.data(pages);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationBase")
	public R<IPage<RcmIndexVO>> page(RcmIndexVO rcmConfigurationBase, Query query) {
		IPage<RcmIndexVO> pages = rcmConfigurationBaseService.selectRcmConfigurationBasePage(Condition.getPage(query), rcmConfigurationBase);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmConfigurationDetialVO")
	public R save(@RequestBody RcmIndexFullVO rcmIndexFullVO) {
		return R.status(rcmConfigurationService.addRcmConfig(rcmIndexFullVO));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmConfigurationDetialVO")
	public R update(@Valid @RequestBody RcmIndexFullVO rcmIndexFullVO) {
		return R.status(rcmConfigurationService.updateRcmConfig(rcmIndexFullVO));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入quotaIndexNum")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String quotaIndexNum) {
		return R.status(rcmConfigurationService.removeRcmConfigById(quotaIndexNum));
	}

	/**
	 * 启用
	 */
	@PostMapping("/usable")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "启用", notes = "传入quotaIndexNum")
	public R usable(@ApiParam(value = "主键", required = true) @RequestParam String quotaIndexNum) {
		return R.status(rcmConfigurationBaseService.updateToUsable(quotaIndexNum));
	}

	/**
	 * 停用
	 */
	@PostMapping("/unusable")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "停用", notes = "传入quotaIndexNum")
	public R unusable(@ApiParam(value = "主键", required = true) @RequestParam String quotaIndexNum) {
		return R.status(rcmConfigurationBaseService.updateToUnUsable(quotaIndexNum));
	}

}
