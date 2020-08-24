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
import org.git.modules.clm.customer.entity.CsmCorporation;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.customer.vo.CsmCorporationVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 公司客户基本信息 控制器
 *
 * @author git
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmcorporation")
@Api(value = "公司客户基本信息", tags = "公司客户基本信息接口")
public class CsmCorporationController extends ChainController {

	private ICsmCorporationService csmCorporationService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmCorporation")
	public R<CsmCorporation> detail(CsmCorporation csmCorporation) {
		CsmCorporation detail = csmCorporationService.getOne(Condition.getQueryWrapper(csmCorporation));
		return R.data(detail);
	}

	/**
	 * 分页 公司客户基本信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmCorporation")
	public R<IPage<CsmCorporation>> list(CsmCorporation csmCorporation, Query query) {
		IPage<CsmCorporation> pages = csmCorporationService.page(Condition.getPage(query), Condition.getQueryWrapper(csmCorporation));
		return R.data(pages);
	}

	/**
	 * 自定义分页 公司客户基本信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmCorporation")
	public R<IPage<CsmCorporationVO>> page(CsmCorporationVO csmCorporation, Query query) {
		IPage<CsmCorporationVO> pages = csmCorporationService.selectCsmCorporationPage(Condition.getPage(query), csmCorporation);
		return R.data(pages);
	}

	/**
	 * 新增 公司客户基本信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmCorporation")
	public R save(@Valid @RequestBody CsmCorporation csmCorporation) {
		return R.status(csmCorporationService.save(csmCorporation));
	}

	/**
	 * 修改 公司客户基本信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmCorporation")
	public R update(@Valid @RequestBody CsmCorporation csmCorporation) {
		return R.status(csmCorporationService.updateById(csmCorporation));
	}

	/**
	 * 新增或修改 公司客户基本信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmCorporation")
	public R submit(@Valid @RequestBody CsmCorporation csmCorporation) {
		return R.status(csmCorporationService.saveOrUpdate(csmCorporation));
	}


	/**
	 * 删除 公司客户基本信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmCorporationService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 通过客户编号查询公司客户信息
	 * @param customerNum
	 * @return
	 */
	@GetMapping("/getCsmCorporationPageByCusNum")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "通过客户编号查询公司客户信息", notes = "传入customerNum")
	public R<CsmCorporationVO> detail(String customerNum) {
		CsmCorporationVO csmCorporationVO = csmCorporationService.selectCsmCorporationPageByCusNum(customerNum);
		return R.data(csmCorporationVO);
	}
}
