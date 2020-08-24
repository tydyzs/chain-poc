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
import org.git.modules.clm.credit.entity.CrdCcInfo;
import org.git.modules.clm.credit.service.ICrdCcInfoService;
import org.git.modules.clm.credit.vo.CrdCcInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 信用卡系统信息 控制器
 *
 * @author git
 * @since 2019-12-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-crd/crdccinfo")
@Api(value = "信用卡系统信息", tags = "信用卡系统信息接口")
public class CrdCcInfoController extends ChainController {

	private ICrdCcInfoService crdCcInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdCcInfo")
	public R<CrdCcInfo> detail(CrdCcInfo crdCcInfo) {
		CrdCcInfo detail = crdCcInfoService.getOne(Condition.getQueryWrapper(crdCcInfo));
		return R.data(detail);
	}

	/**
	 * 分页 信用卡系统信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdCcInfo")
	public R<IPage<CrdCcInfo>> list(CrdCcInfo crdCcInfo, Query query) {
		IPage<CrdCcInfo> pages = crdCcInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(crdCcInfo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 信用卡系统信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdCcInfo")
	public R<IPage<CrdCcInfoVO>> page(CrdCcInfoVO crdCcInfo, Query query) {
		IPage<CrdCcInfoVO> pages = crdCcInfoService.selectCrdCcInfoPage(Condition.getPage(query), crdCcInfo);
		return R.data(pages);
	}

	/**
	 * 新增 信用卡系统信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdCcInfo")
	public R save(@Valid @RequestBody CrdCcInfo crdCcInfo) {
		return R.status(crdCcInfoService.save(crdCcInfo));
	}

	/**
	 * 修改 信用卡系统信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdCcInfo")
	public R update(@Valid @RequestBody CrdCcInfo crdCcInfo) {
		return R.status(crdCcInfoService.updateById(crdCcInfo));
	}

	/**
	 * 新增或修改 信用卡系统信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdCcInfo")
	public R submit(@Valid @RequestBody CrdCcInfo crdCcInfo) {
		return R.status(crdCcInfoService.saveOrUpdate(crdCcInfo));
	}


	/**
	 * 删除 信用卡系统信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdCcInfoService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 信用卡信息（带有客户名称）
	 *
	 * @param query
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/getCrdCcInfoAndCustomerNamePage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "信用卡信息（带有客户名称）", notes = "传入customerNum,orgNum")
	public R<IPage<CrdCcInfoVO>> getCrdCcInfoAndCustomerNamePage(
		@ApiParam(required = true) String customerNum,
		@ApiParam(required = true) String orgNum,
		Query query) {
		IPage<CrdCcInfoVO> pages = crdCcInfoService.selectCrdCcInfoAndCustomerNamePage(Condition.getPage(query), customerNum,orgNum);
		return R.data(pages);
	}

}
