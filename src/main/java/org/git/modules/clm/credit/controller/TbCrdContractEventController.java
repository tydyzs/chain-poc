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
import org.git.modules.clm.credit.entity.TbCrdContractEvent;
import org.git.modules.clm.credit.vo.TbCrdContractEventVO;
import org.git.modules.clm.credit.service.ITbCrdContractEventService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 信贷实时-合同信息 控制器
 *
 * @author git
 * @since 2019-11-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdcontractevent")
@Api(value = "信贷实时-合同信息", tags = "信贷实时-合同信息接口")
public class TbCrdContractEventController extends ChainController {

	private ITbCrdContractEventService tbCrdContractEventService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdContractEvent")
	public R<TbCrdContractEvent> detail(TbCrdContractEvent tbCrdContractEvent) {
		TbCrdContractEvent detail = tbCrdContractEventService.getOne(Condition.getQueryWrapper(tbCrdContractEvent));
		return R.data(detail);
	}

	/**
	 * 分页 信贷实时-合同信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdContractEvent")
	public R<IPage<TbCrdContractEvent>> list(TbCrdContractEvent tbCrdContractEvent, Query query) {
		IPage<TbCrdContractEvent> pages = tbCrdContractEventService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdContractEvent));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信贷实时-合同信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdContractEvent")
	public R<IPage<TbCrdContractEventVO>> page(TbCrdContractEventVO tbCrdContractEvent, Query query) {
		IPage<TbCrdContractEventVO> pages = tbCrdContractEventService.selectTbCrdContractEventPage(Condition.getPage(query), tbCrdContractEvent);
		return R.data(pages);
	}

	/**
	 * 新增 信贷实时-合同信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdContractEvent")
	public R save(@Valid @RequestBody TbCrdContractEvent tbCrdContractEvent) {
		return R.status(tbCrdContractEventService.save(tbCrdContractEvent));
	}

	/**
	 * 修改 信贷实时-合同信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdContractEvent")
	public R update(@Valid @RequestBody TbCrdContractEvent tbCrdContractEvent) {
		return R.status(tbCrdContractEventService.updateById(tbCrdContractEvent));
	}

	/**
	 * 新增或修改 信贷实时-合同信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdContractEvent")
	public R submit(@Valid @RequestBody TbCrdContractEvent tbCrdContractEvent) {
		return R.status(tbCrdContractEventService.saveOrUpdate(tbCrdContractEvent));
	}

	
	/**
	 * 删除 信贷实时-合同信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdContractEventService.removeByIds(Func.toLongList(ids)));
	}

	
}
