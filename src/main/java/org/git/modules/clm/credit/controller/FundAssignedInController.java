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
import org.git.modules.clm.credit.entity.FundAssignedIn;
import org.git.modules.clm.credit.vo.FundAssignedInVO;
import org.git.modules.clm.credit.service.IFundAssignedInService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金纯券过户事件表-转入 控制器
 *
 * @author git
 * @since 2020-01-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-fund/fundassignedin")
@Api(value = "资金纯券过户事件表-转入", tags = "资金纯券过户事件表-转入接口")
public class FundAssignedInController extends ChainController {

	private IFundAssignedInService fundAssignedInService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundAssignedIn")
	public R<FundAssignedIn> detail(FundAssignedIn fundAssignedIn) {
		FundAssignedIn detail = fundAssignedInService.getOne(Condition.getQueryWrapper(fundAssignedIn));
		return R.data(detail);
	}

	/**
	 * 分页 资金纯券过户事件表-转入
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundAssignedIn")
	public R<IPage<FundAssignedIn>> list(FundAssignedIn fundAssignedIn, Query query) {
		IPage<FundAssignedIn> pages = fundAssignedInService.page(Condition.getPage(query), Condition.getQueryWrapper(fundAssignedIn));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金纯券过户事件表-转入
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundAssignedIn")
	public R<IPage<FundAssignedInVO>> page(FundAssignedInVO fundAssignedIn, Query query) {
		IPage<FundAssignedInVO> pages = fundAssignedInService.selectFundAssignedInPage(Condition.getPage(query), fundAssignedIn);
		return R.data(pages);
	}

	/**
	 * 新增 资金纯券过户事件表-转入
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundAssignedIn")
	public R save(@Valid @RequestBody FundAssignedIn fundAssignedIn) {
		return R.status(fundAssignedInService.save(fundAssignedIn));
	}

	/**
	 * 修改 资金纯券过户事件表-转入
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundAssignedIn")
	public R update(@Valid @RequestBody FundAssignedIn fundAssignedIn) {
		return R.status(fundAssignedInService.updateById(fundAssignedIn));
	}

	/**
	 * 新增或修改 资金纯券过户事件表-转入
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundAssignedIn")
	public R submit(@Valid @RequestBody FundAssignedIn fundAssignedIn) {
		return R.status(fundAssignedInService.saveOrUpdate(fundAssignedIn));
	}

	
	/**
	 * 删除 资金纯券过户事件表-转入
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundAssignedInService.removeByIds(Func.toLongList(ids)));
	}

	
}
