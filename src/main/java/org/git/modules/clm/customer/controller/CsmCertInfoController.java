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
import org.git.modules.clm.customer.entity.CsmCertInfo;
import org.git.modules.clm.customer.vo.CsmCertInfoVO;
import org.git.modules.clm.customer.service.ICsmCertInfoService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 证件信息表 控制器
 *
 * @author git
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmcertinfo")
@Api(value = "证件信息表", tags = "证件信息表接口")
public class CsmCertInfoController extends ChainController {

	private ICsmCertInfoService csmCertInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmCertInfo")
	public R<CsmCertInfo> detail(CsmCertInfo csmCertInfo) {
		CsmCertInfo detail = csmCertInfoService.getOne(Condition.getQueryWrapper(csmCertInfo));
		return R.data(detail);
	}

	/**
	 * 分页 证件信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmCertInfo")
	public R<IPage<CsmCertInfo>> list(CsmCertInfo csmCertInfo, Query query) {
		IPage<CsmCertInfo> pages = csmCertInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(csmCertInfo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 证件信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmCertInfo")
	public R<IPage<CsmCertInfoVO>> page(CsmCertInfoVO csmCertInfo, Query query) {
		IPage<CsmCertInfoVO> pages = csmCertInfoService.selectCsmCertInfoPage(Condition.getPage(query), csmCertInfo);
		return R.data(pages);
	}

	/**
	 * 新增 证件信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmCertInfo")
	public R save(@Valid @RequestBody CsmCertInfo csmCertInfo) {
		return R.status(csmCertInfoService.save(csmCertInfo));
	}

	/**
	 * 修改 证件信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmCertInfo")
	public R update(@Valid @RequestBody CsmCertInfo csmCertInfo) {
		return R.status(csmCertInfoService.updateById(csmCertInfo));
	}

	/**
	 * 新增或修改 证件信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmCertInfo")
	public R submit(@Valid @RequestBody CsmCertInfo csmCertInfo) {
		return R.status(csmCertInfoService.saveOrUpdate(csmCertInfo));
	}

	
	/**
	 * 删除 证件信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmCertInfoService.removeByIds(Func.toLongList(ids)));
	}

	
}
