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
import org.git.modules.clm.loan.entity.BillEventMain;
import org.git.modules.clm.loan.vo.BillEventMainVO;
import org.git.modules.clm.loan.service.IBillEventMainService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 票据用信事件主表 控制器
 *
 * @author git
 * @since 2019-11-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/billeventmain")
@Api(value = "票据用信事件主表", tags = "票据用信事件主表接口")
public class BillEventMainController extends ChainController {

	private IBillEventMainService billEventMainService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入billEventMain")
	public R<BillEventMain> detail(BillEventMain billEventMain) {
		BillEventMain detail = billEventMainService.getOne(Condition.getQueryWrapper(billEventMain));
		return R.data(detail);
	}

	/**
	 * 分页 票据用信事件主表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入billEventMain")
	public R<IPage<BillEventMain>> list(BillEventMain billEventMain, Query query) {
		IPage<BillEventMain> pages = billEventMainService.page(Condition.getPage(query), Condition.getQueryWrapper(billEventMain));
		return R.data(pages);
	}

	/**
	 * 自定义分页 票据用信事件主表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入billEventMain")
	public R<IPage<BillEventMainVO>> page(BillEventMainVO billEventMain, Query query) {
		IPage<BillEventMainVO> pages = billEventMainService.selectBillEventMainPage(Condition.getPage(query), billEventMain);
		return R.data(pages);
	}

	/**
	 * 新增 票据用信事件主表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入billEventMain")
	public R save(@Valid @RequestBody BillEventMain billEventMain) {
		return R.status(billEventMainService.save(billEventMain));
	}

	/**
	 * 修改 票据用信事件主表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入billEventMain")
	public R update(@Valid @RequestBody BillEventMain billEventMain) {
		return R.status(billEventMainService.updateById(billEventMain));
	}

	/**
	 * 新增或修改 票据用信事件主表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入billEventMain")
	public R submit(@Valid @RequestBody BillEventMain billEventMain) {
		return R.status(billEventMainService.saveOrUpdate(billEventMain));
	}

	
	/**
	 * 删除 票据用信事件主表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(billEventMainService.removeByIds(Func.toLongList(ids)));
	}

	
}
