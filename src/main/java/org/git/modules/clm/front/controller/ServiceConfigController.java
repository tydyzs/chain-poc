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
package org.git.modules.clm.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.front.entity.ServiceConfig;
import org.git.modules.clm.front.service.IServiceConfigService;
import org.git.modules.clm.front.vo.ServiceConfigVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 前置服务配置表 控制器
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_FRONT_NAME + "/serviceconfig")
@Api(value = "前置服务配置表", tags = "前置服务配置表接口")
public class ServiceConfigController extends ChainController {

	private IServiceConfigService serviceConfigService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入serviceConfig")
	public R<ServiceConfig> detail(ServiceConfig serviceConfig) {
		ServiceConfig detail = serviceConfigService.getOne(Condition.getQueryWrapper(serviceConfig));
		return R.data(detail);
	}

	/**
	 * 分页 前置服务配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入serviceConfig")
	public R<IPage<ServiceConfig>> list(ServiceConfig serviceConfig, Query query) {
		IPage<ServiceConfig> pages = serviceConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(serviceConfig));
		return R.data(pages);
	}

	/**
	 * 自定义分页 前置服务配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入serviceConfig")
	public R<IPage<ServiceConfigVO>> page(ServiceConfigVO serviceConfig, Query query) {
		IPage<ServiceConfigVO> pages = serviceConfigService.selectServiceConfigPage(Condition.getPage(query), serviceConfig);
		return R.data(pages);
	}

	/**
	 * 新增 前置服务配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入serviceConfig")
	public R save(@Valid @RequestBody ServiceConfig serviceConfig) {
		return R.status(serviceConfigService.save(serviceConfig));
	}

	/**
	 * 修改 前置服务配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入serviceConfig")
	public R update(@Valid @RequestBody ServiceConfig serviceConfig) {
		return R.status(serviceConfigService.updateById(serviceConfig));
	}

	/**
	 * 新增或修改 前置服务配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入serviceConfig")
	public R submit(@Valid @RequestBody ServiceConfig serviceConfig) {
		return R.status(serviceConfigService.saveOrUpdate(serviceConfig));
	}

	
	/**
	 * 删除 前置服务配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceConfigService.deleteLogic(Func.toStrList(ids)));
	}

	
}
