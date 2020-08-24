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
import org.git.modules.clm.rcm.entity.RcmConfig;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.rcm.vo.RcmConfigVO;
import org.git.modules.clm.rcm.service.IRcmConfigService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 限额详细信息表 控制器
 *
 * @author liuye
 * @since 2019-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmConfigurationInfo")
@Api(value = "限额详细信息表", tags = "限额详细信息表接口")
public class RcmConfigController extends ChainController {

	private IRcmConfigService rcmConfigurationInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmConfigurationInfo")
	public R<RcmConfig> detail(RcmConfig rcmConfigurationInfo) {
		RcmConfig detail = rcmConfigurationInfoService.getOne(Condition.getQueryWrapper(rcmConfigurationInfo));
		return R.data(detail);

	}

	/**
	 * 分页 限额详细信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationInfo")
	public R<IPage<RcmConfig>> list(RcmConfig rcmConfigurationInfo, Query query) {
		IPage<RcmConfig> pages = rcmConfigurationInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmConfigurationInfo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 限额详细信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationInfo")
	public R<IPage<RcmConfigVO>> page(RcmConfigVO rcmConfigurationInfo, Query query) {
		IPage<RcmConfigVO> pages = rcmConfigurationInfoService.selectRcmConfigPage(Condition.getPage(query), rcmConfigurationInfo);
		return R.data(pages);
	}

	/**
	 * 新增 限额详细信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmConfigurationInfo")
	public R save(@Valid @RequestBody RcmConfig rcmConfigurationInfo) {
		return R.status(rcmConfigurationInfoService.save(rcmConfigurationInfo));
	}

	/**
	 * 修改 限额详细信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmConfigurationInfo")
	public R update(@Valid @RequestBody RcmConfig rcmConfigurationInfo) {
		return R.status(rcmConfigurationInfoService.updateById(rcmConfigurationInfo));
	}

	/**
	 * 新增或修改 限额详细信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmConfigurationInfo")
	public R submit(@Valid @RequestBody RcmConfig rcmConfigurationInfo) {
		return R.status(rcmConfigurationInfoService.saveOrUpdate(rcmConfigurationInfo));
	}


	/**
	 * 删除 限额详细信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmConfigurationInfoService.removeByIds(Func.toLongList(ids)));
	}


}
