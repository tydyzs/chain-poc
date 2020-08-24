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
package org.git.modules.clm.customer.controller;

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
import org.git.modules.clm.customer.entity.CsmIndividual;
import org.git.modules.clm.customer.service.ICsmIndividualService;
import org.git.modules.clm.customer.service.IECIFCustomerInfoService;
import org.git.modules.clm.customer.vo.CsmIndividualVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 个人客户基本信息 控制器
 *
 * @author git
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmindividual")
@Api(value = "个人客户基本信息", tags = "个人客户基本信息接口")
public class CsmIndividualController extends ChainController {

	private ICsmIndividualService csmIndividualService;
	private IECIFCustomerInfoService customerInfoService;


	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmIndividual")
	public R<CsmIndividual> detail(CsmIndividual csmIndividual) {
		CsmIndividual detail = csmIndividualService.getOne(Condition.getQueryWrapper(csmIndividual));
		return R.data(detail);
	}

	/**
	 * 分页 个人客户基本信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmIndividual")
	public R<IPage<CsmIndividual>> list(CsmIndividual csmIndividual, Query query) {
		IPage<CsmIndividual> pages = csmIndividualService.page(Condition.getPage(query), Condition.getQueryWrapper(csmIndividual));
		return R.data(pages);
	}

	/**
	 * 自定义分页 个人客户基本信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmIndividual")
	public R<IPage<CsmIndividualVO>> page(CsmIndividualVO csmIndividual, Query query) {
		IPage<CsmIndividualVO> pages = csmIndividualService.selectCsmIndividualPage(Condition.getPage(query), csmIndividual);
		return R.data(pages);
	}

	/**
	 * 新增 个人客户基本信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmIndividual")
	public R save(@Valid @RequestBody CsmIndividual csmIndividual) {
		return R.status(csmIndividualService.save(csmIndividual));
	}

	/**
	 * 修改 个人客户基本信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmIndividual")
	public R update(@Valid @RequestBody CsmIndividual csmIndividual) {
		return R.status(csmIndividualService.updateById(csmIndividual));
	}

	/**
	 * 新增或修改 个人客户基本信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmIndividual")
	public R submit(@Valid @RequestBody CsmIndividual csmIndividual) {
		return R.status(csmIndividualService.saveOrUpdate(csmIndividual));
	}


	/**
	 * 删除 个人客户基本信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmIndividualService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 通过客户号查询客户信息
	 * @param customerNum
	 * @return
	 */
	@GetMapping("/getCsmIndividualPageByCustNum")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "通过客户号查询客户信息", notes = "传入csmIndividual")
	public R<CsmIndividualVO> selectCsmIndividualPageByCustNum(@ApiParam(required = true) String customerNum) {
		CsmIndividualVO detail = csmIndividualService.selectCsmIndividualPageByCustNum(customerNum);
		return R.data(detail);
	}

}
