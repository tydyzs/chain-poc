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
import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.credit.vo.FundEventMainVO;
import org.git.modules.clm.credit.service.IFundEventMainService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 资金用信事件主表 控制器
 *
 * @author liuye
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/fundeventmain")
@Api(value = "资金用信事件主表", tags = "资金用信事件主表接口")
public class FundEventMainController extends ChainController {

	private IFundEventMainService fundEventMainService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundEventMain")
	public R<FundEventMain> detail(FundEventMain fundEventMain) {
		FundEventMain detail = fundEventMainService.getOne(Condition.getQueryWrapper(fundEventMain));
		return R.data(detail);
	}

	/**
	 * 分页 资金用信事件主表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundEventMain")
	public R<IPage<FundEventMain>> list(FundEventMain fundEventMain, Query query) {
		IPage<FundEventMain> pages = fundEventMainService.page(Condition.getPage(query), Condition.getQueryWrapper(fundEventMain));
		return R.data(pages);
	}

	/**
	 * 自定义分页 资金用信事件主表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundEventMain")
	public R<IPage<FundEventMainVO>> page(FundEventMainVO fundEventMain, Query query) {
		IPage<FundEventMainVO> pages = fundEventMainService.selectFundEventMainPage(Condition.getPage(query), fundEventMain);
		return R.data(pages);
	}

	/**
	 * 新增 资金用信事件主表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundEventMain")
	public R save(@Valid @RequestBody FundEventMain fundEventMain) {
		return R.status(fundEventMainService.save(fundEventMain));
	}

	/**
	 * 修改 资金用信事件主表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundEventMain")
	public R update(@Valid @RequestBody FundEventMain fundEventMain) {
		return R.status(fundEventMainService.updateById(fundEventMain));
	}

	/**
	 * 新增或修改 资金用信事件主表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundEventMain")
	public R submit(@Valid @RequestBody FundEventMain fundEventMain) {
		return R.status(fundEventMainService.saveOrUpdate(fundEventMain));
	}

	
	/**
	 * 删除 资金用信事件主表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundEventMainService.removeByIds(Func.toLongList(ids)));
	}

	
}
