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
import org.git.modules.clm.credit.entity.FundAssignedOut;
import org.git.modules.clm.credit.vo.FundAssignedOutVO;
import org.git.modules.clm.credit.service.IFundAssignedOutService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金系统额度转让-转出 控制器
 *
 * @author git
 * @since 2020-01-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-fund/fundassignedout")
@Api(value = "资金系统额度转让-转出", tags = "资金系统额度转让-转出接口")
public class FundAssignedOutController extends ChainController {

	private IFundAssignedOutService fundAssignedOutService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundAssignedOut")
	public R<FundAssignedOut> detail(FundAssignedOut fundAssignedOut) {
		FundAssignedOut detail = fundAssignedOutService.getOne(Condition.getQueryWrapper(fundAssignedOut));
		return R.data(detail);
	}

	/**
	 * 分页 资金系统额度转让-转出
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundAssignedOut")
	public R<IPage<FundAssignedOut>> list(FundAssignedOut fundAssignedOut, Query query) {
		IPage<FundAssignedOut> pages = fundAssignedOutService.page(Condition.getPage(query), Condition.getQueryWrapper(fundAssignedOut));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金系统额度转让-转出
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundAssignedOut")
	public R<IPage<FundAssignedOutVO>> page(FundAssignedOutVO fundAssignedOut, Query query) {
		IPage<FundAssignedOutVO> pages = fundAssignedOutService.selectFundAssignedOutPage(Condition.getPage(query), fundAssignedOut);
		return R.data(pages);
	}

	/**
	 * 新增 资金系统额度转让-转出
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundAssignedOut")
	public R save(@Valid @RequestBody FundAssignedOut fundAssignedOut) {
		return R.status(fundAssignedOutService.save(fundAssignedOut));
	}

	/**
	 * 修改 资金系统额度转让-转出
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundAssignedOut")
	public R update(@Valid @RequestBody FundAssignedOut fundAssignedOut) {
		return R.status(fundAssignedOutService.updateById(fundAssignedOut));
	}

	/**
	 * 新增或修改 资金系统额度转让-转出
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundAssignedOut")
	public R submit(@Valid @RequestBody FundAssignedOut fundAssignedOut) {
		return R.status(fundAssignedOutService.saveOrUpdate(fundAssignedOut));
	}

	
	/**
	 * 删除 资金系统额度转让-转出
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundAssignedOutService.removeByIds(Func.toLongList(ids)));
	}

	
}
