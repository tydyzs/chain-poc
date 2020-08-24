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
import org.git.modules.clm.loan.entity.BillEventDetail;
import org.git.modules.clm.loan.vo.BillEventDetailVO;
import org.git.modules.clm.loan.service.IBillEventDetailService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 票据用信事件明细表 控制器
 *
 * @author git
 * @since 2019-11-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/billeventdetail")
@Api(value = "票据用信事件明细表", tags = "票据用信事件明细表接口")
public class BillEventDetailController extends ChainController {

	private IBillEventDetailService billEventDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入billEventDetail")
	public R<BillEventDetail> detail(BillEventDetail billEventDetail) {
		BillEventDetail detail = billEventDetailService.getOne(Condition.getQueryWrapper(billEventDetail));
		return R.data(detail);
	}

	/**
	 * 分页 票据用信事件明细表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入billEventDetail")
	public R<IPage<BillEventDetail>> list(BillEventDetail billEventDetail, Query query) {
		IPage<BillEventDetail> pages = billEventDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(billEventDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 票据用信事件明细表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入billEventDetail")
	public R<IPage<BillEventDetailVO>> page(BillEventDetailVO billEventDetail, Query query) {
		IPage<BillEventDetailVO> pages = billEventDetailService.selectBillEventDetailPage(Condition.getPage(query), billEventDetail);
		return R.data(pages);
	}

	/**
	 * 新增 票据用信事件明细表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入billEventDetail")
	public R save(@Valid @RequestBody BillEventDetail billEventDetail) {
		return R.status(billEventDetailService.save(billEventDetail));
	}

	/**
	 * 修改 票据用信事件明细表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入billEventDetail")
	public R update(@Valid @RequestBody BillEventDetail billEventDetail) {
		return R.status(billEventDetailService.updateById(billEventDetail));
	}

	/**
	 * 新增或修改 票据用信事件明细表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入billEventDetail")
	public R submit(@Valid @RequestBody BillEventDetail billEventDetail) {
		return R.status(billEventDetailService.saveOrUpdate(billEventDetail));
	}

	
	/**
	 * 删除 票据用信事件明细表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(billEventDetailService.removeByIds(Func.toLongList(ids)));
	}

	
}
