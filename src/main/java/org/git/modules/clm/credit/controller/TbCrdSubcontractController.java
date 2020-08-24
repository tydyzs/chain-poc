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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;
import org.git.modules.clm.credit.service.ITbCrdSubcontractService;
import org.git.modules.clm.credit.vo.TbCrdSubcontractVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 担保合同信息表 控制器
 *
 * @author git
 * @since 2019-11-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/subcontractStaus")
@Api(value = "担保合同信息表", tags = "担保合同信息表接口")
public class TbCrdSubcontractController extends ChainController {

	private ITbCrdSubcontractService tbCrdSubcontractService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdSubcontract")
	public R<TbCrdSubcontract> detail(TbCrdSubcontract tbCrdSubcontract) {
		TbCrdSubcontract detail = tbCrdSubcontractService.getOne(Condition.getQueryWrapper(tbCrdSubcontract));
		return R.data(detail);
	}

	/**
	 * 分页 担保合同信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdSubcontract")
	public R<IPage<TbCrdSubcontract>> list(TbCrdSubcontract tbCrdSubcontract, Query query) {
		IPage<TbCrdSubcontract> pages = tbCrdSubcontractService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdSubcontract));
		return R.data(pages);
	}

	/**
	 * 自定义分页 担保合同信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdSubcontract")
	public R<IPage<TbCrdSubcontractVO>> page(TbCrdSubcontractVO tbCrdSubcontract, Query query) {
		IPage<TbCrdSubcontractVO> pages = tbCrdSubcontractService.selectTbCrdSubcontractPage(Condition.getPage(query), tbCrdSubcontract);
		return R.data(pages);
	}

	/**
	 * 新增 担保合同信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdSubcontract")
	public R save(@Valid @RequestBody TbCrdSubcontract tbCrdSubcontract) {
		return R.status(tbCrdSubcontractService.save(tbCrdSubcontract));
	}

	/**
	 * 修改 担保合同信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdSubcontract")
	public R update(@Valid @RequestBody TbCrdSubcontract tbCrdSubcontract) {
		return R.status(tbCrdSubcontractService.updateById(tbCrdSubcontract));
	}

	/**
	 * 新增或修改 担保合同信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdSubcontract")
	public R submit(@Valid @RequestBody TbCrdSubcontract tbCrdSubcontract) {
		return R.status(tbCrdSubcontractService.saveOrUpdate(tbCrdSubcontract));
	}


	/**
	 * 删除 担保合同信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdSubcontractService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 限额台账  对外担保信息数据
	 */
	@GetMapping("/getTbCrdSubcontractList")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "限额台账  对外担保信息数据", notes = "传入customerNum")
	public R<IPage<TbCrdSubcontractVO>> getTbCrdSubcontractList(@ApiParam(required = true) String customerNum,@ApiParam(required = true)String orgNum, Query query) {
		IPage<TbCrdSubcontractVO> pages = tbCrdSubcontractService.selectTbCrdSubcontractList(Condition.getPage(query), customerNum,orgNum);
		return R.data(pages);
	}


	/**
	 * 查询合同详情中担保合同信息
	 */
	@GetMapping("/queryCrdSubcontractPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询合同详情中担保合同信息", notes = "传入contractNum")
	public R<IPage<TbCrdSubcontractVO>> queryCrdSubcontractPage(@ApiParam(value = "担保合同编号",required = true)String contractNum, Query query) {
		IPage<TbCrdSubcontractVO> pages = tbCrdSubcontractService.queryCrdSubcontractPage(Condition.getPage(query), contractNum);
		return R.data(pages);
	}
}
