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
import org.git.modules.clm.credit.entity.TbCrdSummaryEvent;
import org.git.modules.clm.credit.vo.TbCrdSummaryEventVO;
import org.git.modules.clm.credit.service.ITbCrdSummaryEventService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 信贷实时-借据信息表 控制器
 *
 * @author git
 * @since 2019-11-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdsummaryevent")
@Api(value = "信贷实时-借据信息表", tags = "信贷实时-借据信息表接口")
public class TbCrdSummaryEventController extends ChainController {

	private ITbCrdSummaryEventService tbCrdSummaryEventService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdSummaryEvent")
	public R<TbCrdSummaryEvent> detail(TbCrdSummaryEvent tbCrdSummaryEvent) {
		TbCrdSummaryEvent detail = tbCrdSummaryEventService.getOne(Condition.getQueryWrapper(tbCrdSummaryEvent));
		return R.data(detail);
	}

	/**
	 * 分页 信贷实时-借据信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdSummaryEvent")
	public R<IPage<TbCrdSummaryEvent>> list(TbCrdSummaryEvent tbCrdSummaryEvent, Query query) {
		IPage<TbCrdSummaryEvent> pages = tbCrdSummaryEventService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdSummaryEvent));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信贷实时-借据信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdSummaryEvent")
	public R<IPage<TbCrdSummaryEventVO>> page(TbCrdSummaryEventVO tbCrdSummaryEvent, Query query) {
		IPage<TbCrdSummaryEventVO> pages = tbCrdSummaryEventService.selectTbCrdSummaryEventPage(Condition.getPage(query), tbCrdSummaryEvent);
		return R.data(pages);
	}

	/**
	 * 新增 信贷实时-借据信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdSummaryEvent")
	public R save(@Valid @RequestBody TbCrdSummaryEvent tbCrdSummaryEvent) {
		return R.status(tbCrdSummaryEventService.save(tbCrdSummaryEvent));
	}

	/**
	 * 修改 信贷实时-借据信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdSummaryEvent")
	public R update(@Valid @RequestBody TbCrdSummaryEvent tbCrdSummaryEvent) {
		return R.status(tbCrdSummaryEventService.updateById(tbCrdSummaryEvent));
	}

	/**
	 * 新增或修改 信贷实时-借据信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdSummaryEvent")
	public R submit(@Valid @RequestBody TbCrdSummaryEvent tbCrdSummaryEvent) {
		return R.status(tbCrdSummaryEventService.saveOrUpdate(tbCrdSummaryEvent));
	}

	
	/**
	 * 删除 信贷实时-借据信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdSummaryEventService.removeByIds(Func.toLongList(ids)));
	}

	
}
