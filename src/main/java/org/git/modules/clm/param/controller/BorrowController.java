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

import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.param.vo.CustomerVO;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.param.entity.TbParBorrow;
import org.git.modules.clm.param.vo.TbParBorrowVO;
import org.git.modules.clm.param.service.ITbParBorrowService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 串用组信息表 控制器
 *
 * @author cc
 * @since 2020-03-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-param/borrow")
@Api(value = "串用组信息表", tags = "串用组信息表接口")
public class BorrowController extends ChainController {

	private ITbParBorrowService tbParBorrowService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbParBorrow")
	public R<TbParBorrow> detail(TbParBorrow tbParBorrow) {
		TbParBorrow detail = tbParBorrowService.getOne(Condition.getQueryWrapper(tbParBorrow));
		return R.data(detail);
	}

	/**
	 * 分页 串用组信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbParBorrow")
	public R<IPage<TbParBorrow>> list(TbParBorrow tbParBorrow, Query query) {
		IPage<TbParBorrow> pages = tbParBorrowService.page(Condition.getPage(query), Condition.getQueryWrapper(tbParBorrow));
		return R.data(pages);
	}

	/**
	 * 自定义分页 串用组信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbParBorrow")
	public R<IPage<TbParBorrowVO>> page(TbParBorrowVO tbParBorrow, Query query) {
		IPage<TbParBorrowVO> pages = tbParBorrowService.selectTbParBorrowPage(Condition.getPage(query), tbParBorrow);
		return R.data(pages);
	}

	/**
	 * 新增 串用组信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbParBorrow")
	public R save(@Valid @RequestBody TbParBorrow tbParBorrow) {
		return R.status(tbParBorrowService.save(tbParBorrow));
	}

	/**
	 * 修改 串用组信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbParBorrow")
	public R update(@Valid @RequestBody TbParBorrow tbParBorrow) {
		return R.status(tbParBorrowService.updateById(tbParBorrow));
	}

	/**
	 * 新增或修改 串用组信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbParBorrow")
	public R submit(@Valid @RequestBody TbParBorrow tbParBorrow, ChainUser chainUser) {
		tbParBorrow.setUserNum(chainUser.getAccount());
		tbParBorrow.setOrgNum(chainUser.getDeptId());
		tbParBorrow.setCreateTime(CommonUtil.getWorkDateTime());
		tbParBorrow.setUpdateTime(CommonUtil.getWorkDateTime());
		return R.status(tbParBorrowService.saveOrUpdate(tbParBorrow));
	}


	/**
	 * 删除 串用组信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbParBorrowService.removeByIds(Func.toStrList(ids)));
	}

	/**
	 * 自定义分页 客户信息列表
	 */
	@GetMapping("/customerPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "分页", notes = "传入CustomerVO")
	public R<IPage<CustomerVO>> page(CustomerVO customerVO, Query query) {
		IPage<CustomerVO> pages = tbParBorrowService.selectCustomerPage(Condition.getPage(query), customerVO);
		return R.data(pages);
	}
}
