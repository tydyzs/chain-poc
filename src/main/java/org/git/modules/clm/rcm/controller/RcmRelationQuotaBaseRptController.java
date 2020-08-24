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
package org.git.modules.clm.rcm.controller;

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
import org.git.modules.clm.rcm.entity.RcmRelationQuotaBaseRpt;
import org.git.modules.clm.rcm.vo.RcmRelationQuotaBaseRptVO;
import org.git.modules.clm.rcm.service.IRcmRelationQuotaBaseRptService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 关联客户授信集中度简表 控制器
 *
 * @author git
 * @since 2019-12-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmrelationquotabaserpt")
@Api(value = "关联客户授信集中度简表", tags = "关联客户授信集中度简表接口")
public class RcmRelationQuotaBaseRptController extends ChainController {

	private IRcmRelationQuotaBaseRptService rcmRelationQuotaBaseRptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmRelationQuotaBaseRpt")
	public R<RcmRelationQuotaBaseRpt> detail(RcmRelationQuotaBaseRpt rcmRelationQuotaBaseRpt) {
		RcmRelationQuotaBaseRpt detail = rcmRelationQuotaBaseRptService.getOne(Condition.getQueryWrapper(rcmRelationQuotaBaseRpt));
		return R.data(detail);
	}

	/**
	 * 分页 关联客户授信集中度简表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmRelationQuotaBaseRpt")
	public R<IPage<RcmRelationQuotaBaseRpt>> list(RcmRelationQuotaBaseRpt rcmRelationQuotaBaseRpt, Query query) {
		IPage<RcmRelationQuotaBaseRpt> pages = rcmRelationQuotaBaseRptService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmRelationQuotaBaseRpt));
		return R.data(pages);
	}

	/**
	 * 自定义分页 关联客户授信集中度简表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmRelationQuotaBaseRpt")
	public R<IPage<RcmRelationQuotaBaseRptVO>> page(RcmRelationQuotaBaseRptVO rcmRelationQuotaBaseRpt, Query query) {
		IPage<RcmRelationQuotaBaseRptVO> pages = rcmRelationQuotaBaseRptService.selectRcmRelationQuotaBaseRptPage(Condition.getPage(query), rcmRelationQuotaBaseRpt);
		return R.data(pages);
	}

	/**
	 * 新增 关联客户授信集中度简表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmRelationQuotaBaseRpt")
	public R save(@Valid @RequestBody RcmRelationQuotaBaseRpt rcmRelationQuotaBaseRpt) {
		return R.status(rcmRelationQuotaBaseRptService.save(rcmRelationQuotaBaseRpt));
	}

	/**
	 * 修改 关联客户授信集中度简表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmRelationQuotaBaseRpt")
	public R update(@Valid @RequestBody RcmRelationQuotaBaseRpt rcmRelationQuotaBaseRpt) {
		return R.status(rcmRelationQuotaBaseRptService.updateById(rcmRelationQuotaBaseRpt));
	}

	/**
	 * 新增或修改 关联客户授信集中度简表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmRelationQuotaBaseRpt")
	public R submit(@Valid @RequestBody RcmRelationQuotaBaseRpt rcmRelationQuotaBaseRpt) {
		return R.status(rcmRelationQuotaBaseRptService.saveOrUpdate(rcmRelationQuotaBaseRpt));
	}

	
	/**
	 * 删除 关联客户授信集中度简表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmRelationQuotaBaseRptService.removeByIds(Func.toLongList(ids)));
	}

	
}
