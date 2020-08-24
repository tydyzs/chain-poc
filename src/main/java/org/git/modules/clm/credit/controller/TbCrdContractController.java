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
import org.git.modules.clm.credit.entity.TbCrdContract;
import org.git.modules.clm.credit.vo.TbCrdContractVO;
import org.git.modules.clm.credit.service.ITbCrdContractService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 合同信息表 控制器
 *
 * @author git
 * @since 2019-11-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdcontract")
@Api(value = "合同信息表", tags = "合同信息表接口")
public class TbCrdContractController extends ChainController {

	private ITbCrdContractService tbCrdContractService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdContract")
	public R<TbCrdContract> detail(TbCrdContract tbCrdContract) {
		TbCrdContract detail = tbCrdContractService.getOne(Condition.getQueryWrapper(tbCrdContract));
		return R.data(detail);
	}

	/**
	 * 分页 合同信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdContract")
	public R<IPage<TbCrdContract>> list(TbCrdContract tbCrdContract, Query query) {
		IPage<TbCrdContract> pages = tbCrdContractService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdContract));
		return R.data(pages);
	}

	/**
	 * 自定义分页 合同信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdContract")
	public R<IPage<TbCrdContractVO>> page(TbCrdContractVO tbCrdContract, Query query) {
		IPage<TbCrdContractVO> pages = tbCrdContractService.selectTbCrdContractPage(Condition.getPage(query), tbCrdContract);
		return R.data(pages);
	}

	/**
	 * 新增 合同信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdContract")
	public R save(@Valid @RequestBody TbCrdContract tbCrdContract) {
		return R.status(tbCrdContractService.save(tbCrdContract));
	}

	/**
	 * 修改 合同信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdContract")
	public R update(@Valid @RequestBody TbCrdContract tbCrdContract) {
		return R.status(tbCrdContractService.updateById(tbCrdContract));
	}

	/**
	 * 新增或修改 合同信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdContract")
	public R submit(@Valid @RequestBody TbCrdContract tbCrdContract) {
		return R.status(tbCrdContractService.saveOrUpdate(tbCrdContract));
	}


	/**
	 * 删除 合同信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdContractService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 合同明细（带有客户名称）
	 * @param contractNum
	 * @return
	 */
	@GetMapping("/contractDetail")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "详情(客户名称)", notes = "传入contractNum")
	public R<TbCrdContractVO> contractDetail(@ApiParam(value = "合同编号",required = true) String contractNum) {
		TbCrdContractVO detail = tbCrdContractService.selectContractDetailByCusNum(contractNum);
		return R.data(detail);
	}

	/**
	 * 分页 查询合同信息（从 额度台账 过来）
	 */
	@GetMapping("/getContractList")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "分页 查询合同信息（从 额度台账 过来）", notes = "传入customerNum")
	public R<IPage<TbCrdContractVO>> getContractList(@ApiParam(value = "客户编号",required = true) String customerNum,@ApiParam(value = "经办机构",required = true)String orgNum, Query query) {
		IPage<TbCrdContractVO> pages = tbCrdContractService.selectContractListByCusNum(Condition.getPage(query), customerNum,orgNum);
		return R.data(pages);
	}

	/**
	 * 合同明细（带有客户名称）
	 * @param contractNum
	 * @return
	 */
	@GetMapping("/queryCrdContractDetail")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "合同明细详情", notes = "传入contractNum")
	public R<TbCrdContractVO> queryCrdContractDetail(@ApiParam(value = "合同编号",required = true) String contractNum) {
		TbCrdContractVO detail = tbCrdContractService.queryCrdContractDetail(contractNum);
		return R.data(detail);
	}
}
