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
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.loan.entity.CrdProjectEvent;
import org.git.modules.clm.loan.vo.CrdProjectEventVO;
import org.git.modules.clm.loan.service.ICrdProjectEventService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 信贷实时-项目协议表 控制器
 *
 * @author git
 * @since 2019-11-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-loan/crdprojectevent")
@Api(value = "信贷实时-项目协议表", tags = "信贷实时-项目协议表接口")
public class CrdProjectEventController extends ChainController {

	private ICrdProjectEventService crdProjectEventService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdProjectEvent")
	public R<CrdProjectEvent> detail(CrdProjectEvent crdProjectEvent) {
		CrdProjectEvent detail = crdProjectEventService.getOne(Condition.getQueryWrapper(crdProjectEvent));
		return R.data(detail);
	}

	/**
	 * 分页 信贷实时-项目协议表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdProjectEvent")
	public R<IPage<CrdProjectEvent>> list(CrdProjectEvent crdProjectEvent, Query query) {
		IPage<CrdProjectEvent> pages = crdProjectEventService.page(Condition.getPage(query), Condition.getQueryWrapper(crdProjectEvent));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信贷实时-项目协议表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdProjectEvent")
	public R<IPage<CrdProjectEventVO>> page(CrdProjectEventVO crdProjectEvent, Query query) {
		IPage<CrdProjectEventVO> pages = crdProjectEventService.selectCrdProjectEventPage(Condition.getPage(query), crdProjectEvent);
		return R.data(pages);
	}

	/**
	 * 新增 信贷实时-项目协议表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdProjectEvent")
	public R save(@Valid @RequestBody CrdProjectEvent crdProjectEvent) {
		return R.status(crdProjectEventService.save(crdProjectEvent));
	}

	/**
	 * 修改 信贷实时-项目协议表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdProjectEvent")
	public R update(@Valid @RequestBody CrdProjectEvent crdProjectEvent) {
		return R.status(crdProjectEventService.updateById(crdProjectEvent));
	}

	/**
	 * 新增或修改 信贷实时-项目协议表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdProjectEvent")
	public R submit(@Valid @RequestBody CrdProjectEvent crdProjectEvent) {
		return R.status(crdProjectEventService.saveOrUpdate(crdProjectEvent));
	}

	
	/**
	 * 删除 信贷实时-项目协议表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdProjectEventService.removeByIds(Func.toLongList(ids)));
	}

	
}
