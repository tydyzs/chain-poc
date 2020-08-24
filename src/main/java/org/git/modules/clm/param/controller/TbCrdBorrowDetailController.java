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
package org.git.modules.clm.param.controller;

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
import org.git.modules.clm.param.entity.TbCrdBorrowDetail;
import org.git.modules.clm.param.vo.TbCrdBorrowDetailVO;
import org.git.modules.clm.param.service.ITbCrdBorrowDetailService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 串用流水明细表 控制器
 *
 * @author git
 * @since 2020-03-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-param/borrowDetail")
@Api(value = "串用流水明细表", tags = "串用流水明细表接口")
public class TbCrdBorrowDetailController extends ChainController {

	private ITbCrdBorrowDetailService tbCrdBorrowDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdBorrowDetail")
	public R<TbCrdBorrowDetail> detail(TbCrdBorrowDetail tbCrdBorrowDetail) {
		TbCrdBorrowDetail detail = tbCrdBorrowDetailService.getOne(Condition.getQueryWrapper(tbCrdBorrowDetail));
		return R.data(detail);
	}

	/**
	 * 分页 串用流水明细表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdBorrowDetail")
	public R<IPage<TbCrdBorrowDetail>> list(TbCrdBorrowDetail tbCrdBorrowDetail, Query query) {
		IPage<TbCrdBorrowDetail> pages = tbCrdBorrowDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdBorrowDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 串用流水明细表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdBorrowDetail")
	public R<IPage<TbCrdBorrowDetailVO>> page(TbCrdBorrowDetailVO tbCrdBorrowDetail, Query query) {
		IPage<TbCrdBorrowDetailVO> pages = tbCrdBorrowDetailService.selectTbCrdBorrowDetailPage(Condition.getPage(query), tbCrdBorrowDetail);
		return R.data(pages);
	}

	/**
	 * 新增 串用流水明细表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdBorrowDetail")
	public R save(@Valid @RequestBody TbCrdBorrowDetail tbCrdBorrowDetail) {
		return R.status(tbCrdBorrowDetailService.save(tbCrdBorrowDetail));
	}

	/**
	 * 修改 串用流水明细表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdBorrowDetail")
	public R update(@Valid @RequestBody TbCrdBorrowDetail tbCrdBorrowDetail) {
		return R.status(tbCrdBorrowDetailService.updateById(tbCrdBorrowDetail));
	}

	/**
	 * 新增或修改 串用流水明细表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdBorrowDetail")
	public R submit(@Valid @RequestBody TbCrdBorrowDetail tbCrdBorrowDetail) {
		return R.status(tbCrdBorrowDetailService.saveOrUpdate(tbCrdBorrowDetail));
	}


	/**
	 * 删除 串用流水明细表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdBorrowDetailService.removeByIds(Func.toLongList(ids)));
	}


}
