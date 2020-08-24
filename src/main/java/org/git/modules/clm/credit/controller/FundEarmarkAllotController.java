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

import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.credit.entity.FundEarmarkAllot;
import org.git.modules.clm.credit.vo.FundEarmarkAllotVO;
import org.git.modules.clm.credit.service.IFundEarmarkAllotService;

/**
 * 资金授信圈存分配 控制器
 *
 * @author git
 * @since 2019-12-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/fundearmarkallot")
@Api(value = "资金授信圈存分配", tags = "资金授信圈存分配接口")
public class FundEarmarkAllotController extends ChainController {

	private IFundEarmarkAllotService fundEarmarkAllotService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundEarmarkAllot")
	public R<FundEarmarkAllot> detail(FundEarmarkAllot fundEarmarkAllot) {
		FundEarmarkAllot detail = fundEarmarkAllotService.getOne(Condition.getQueryWrapper(fundEarmarkAllot));
		return R.data(detail);
	}

	/**
	 * 分页 资金授信圈存分配
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundEarmarkAllot")
	public R<IPage<FundEarmarkAllot>> list(FundEarmarkAllot fundEarmarkAllot, Query query) {
		IPage<FundEarmarkAllot> pages = fundEarmarkAllotService.page(Condition.getPage(query), Condition.getQueryWrapper(fundEarmarkAllot));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金授信圈存分配
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundEarmarkAllot")
	public R<IPage<FundEarmarkAllotVO>> page(FundEarmarkAllotVO fundEarmarkAllot, Query query) {
		IPage<FundEarmarkAllotVO> pages = fundEarmarkAllotService.selectFundEarmarkAllotPage(Condition.getPage(query), fundEarmarkAllot);
		return R.data(pages);
	}

	/**
	 * 新增 资金授信圈存分配
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundEarmarkAllot")
	public R save(@Valid @RequestBody FundEarmarkAllot fundEarmarkAllot) {
		return R.status(fundEarmarkAllotService.save(fundEarmarkAllot));
	}

	/**
	 * 修改 资金授信圈存分配
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundEarmarkAllot")
	public R update(@Valid @RequestBody FundEarmarkAllot fundEarmarkAllot) {
		return R.status(fundEarmarkAllotService.updateById(fundEarmarkAllot));
	}

	/**
	 * 新增或修改 资金授信圈存分配
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundEarmarkAllot")
	public R submit(@Valid @RequestBody FundEarmarkAllot fundEarmarkAllot) {
		return R.status(fundEarmarkAllotService.saveOrUpdate(fundEarmarkAllot));
	}

	
	/**
	 * 删除 资金授信圈存分配
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundEarmarkAllotService.removeByIds(Func.toLongList(ids)));
	}

	
}
