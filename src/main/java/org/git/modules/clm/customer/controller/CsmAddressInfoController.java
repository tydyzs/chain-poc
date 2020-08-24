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
import org.git.modules.clm.customer.entity.CsmAddressInfo;
import org.git.modules.clm.customer.vo.CsmAddressInfoVO;
import org.git.modules.clm.customer.service.ICsmAddressInfoService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 地址信息 控制器
 *
 * @author git
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmaddressinfo")
@Api(value = "地址信息", tags = "地址信息接口")
public class CsmAddressInfoController extends ChainController {

	private ICsmAddressInfoService csmAddressInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmAddressInfo")
	public R<CsmAddressInfo> detail(CsmAddressInfo csmAddressInfo) {
		CsmAddressInfo detail = csmAddressInfoService.getOne(Condition.getQueryWrapper(csmAddressInfo));
		return R.data(detail);
	}

	/**
	 * 分页 地址信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmAddressInfo")
	public R<IPage<CsmAddressInfo>> list(CsmAddressInfo csmAddressInfo, Query query) {
		IPage<CsmAddressInfo> pages = csmAddressInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(csmAddressInfo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 地址信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmAddressInfo")
	public R<IPage<CsmAddressInfoVO>> page(CsmAddressInfoVO csmAddressInfo, Query query) {
		IPage<CsmAddressInfoVO> pages = csmAddressInfoService.selectCsmAddressInfoPage(Condition.getPage(query), csmAddressInfo);
		return R.data(pages);
	}

	/**
	 * 新增 地址信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmAddressInfo")
	public R save(@Valid @RequestBody CsmAddressInfo csmAddressInfo) {
		return R.status(csmAddressInfoService.save(csmAddressInfo));
	}

	/**
	 * 修改 地址信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmAddressInfo")
	public R update(@Valid @RequestBody CsmAddressInfo csmAddressInfo) {
		return R.status(csmAddressInfoService.updateById(csmAddressInfo));
	}

	/**
	 * 新增或修改 地址信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmAddressInfo")
	public R submit(@Valid @RequestBody CsmAddressInfo csmAddressInfo) {
		return R.status(csmAddressInfoService.saveOrUpdate(csmAddressInfo));
	}

	
	/**
	 * 删除 地址信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmAddressInfoService.removeByIds(Func.toLongList(ids)));
	}

	
}
