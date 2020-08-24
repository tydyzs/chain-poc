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
import org.git.modules.clm.loan.entity.CrdRecoveryEvent;
import org.git.modules.clm.loan.vo.CrdRecoveryEventVO;
import org.git.modules.clm.loan.service.ICrdRecoveryEventService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 信贷实时-还款交易信息 控制器
 *
 * @author git
 * @since 2019-11-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-loan/crdrecoveryevent")
@Api(value = "信贷实时-还款交易信息", tags = "信贷实时-还款交易信息接口")
public class CrdRecoveryEventController extends ChainController {

	private ICrdRecoveryEventService crdRecoveryEventService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdRecoveryEvent")
	public R<CrdRecoveryEvent> detail(CrdRecoveryEvent crdRecoveryEvent) {
		CrdRecoveryEvent detail = crdRecoveryEventService.getOne(Condition.getQueryWrapper(crdRecoveryEvent));
		return R.data(detail);
	}

	/**
	 * 分页 信贷实时-还款交易信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdRecoveryEvent")
	public R<IPage<CrdRecoveryEvent>> list(CrdRecoveryEvent crdRecoveryEvent, Query query) {
		IPage<CrdRecoveryEvent> pages = crdRecoveryEventService.page(Condition.getPage(query), Condition.getQueryWrapper(crdRecoveryEvent));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信贷实时-还款交易信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdRecoveryEvent")
	public R<IPage<CrdRecoveryEventVO>> page(CrdRecoveryEventVO crdRecoveryEvent, Query query) {
		IPage<CrdRecoveryEventVO> pages = crdRecoveryEventService.selectCrdRecoveryEventPage(Condition.getPage(query), crdRecoveryEvent);
		return R.data(pages);
	}

	/**
	 * 新增 信贷实时-还款交易信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdRecoveryEvent")
	public R save(@Valid @RequestBody CrdRecoveryEvent crdRecoveryEvent) {
		return R.status(crdRecoveryEventService.save(crdRecoveryEvent));
	}

	/**
	 * 修改 信贷实时-还款交易信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdRecoveryEvent")
	public R update(@Valid @RequestBody CrdRecoveryEvent crdRecoveryEvent) {
		return R.status(crdRecoveryEventService.updateById(crdRecoveryEvent));
	}

	/**
	 * 新增或修改 信贷实时-还款交易信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdRecoveryEvent")
	public R submit(@Valid @RequestBody CrdRecoveryEvent crdRecoveryEvent) {
		return R.status(crdRecoveryEventService.saveOrUpdate(crdRecoveryEvent));
	}

	
	/**
	 * 删除 信贷实时-还款交易信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdRecoveryEventService.removeByIds(Func.toLongList(ids)));
	}

	
}
