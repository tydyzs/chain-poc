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
import org.git.modules.clm.rcm.entity.RcmRelationQuotaDetailRpt;
import org.git.modules.clm.rcm.vo.RcmRelationQuotaDetailRptVO;
import org.git.modules.clm.rcm.service.IRcmRelationQuotaDetailRptService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 关联客户授信集中度明细表 控制器
 *
 * @author git
 * @since 2019-12-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmrelationquotadetailrpt")
@Api(value = "关联客户授信集中度明细表", tags = "关联客户授信集中度明细表接口")
public class RcmRelationQuotaDetailRptController extends ChainController {

	private IRcmRelationQuotaDetailRptService rcmRelationQuotaDetailRptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmRelationQuotaDetailRpt")
	public R<RcmRelationQuotaDetailRpt> detail(RcmRelationQuotaDetailRpt rcmRelationQuotaDetailRpt) {
		RcmRelationQuotaDetailRpt detail = rcmRelationQuotaDetailRptService.getOne(Condition.getQueryWrapper(rcmRelationQuotaDetailRpt));
		return R.data(detail);
	}

	/**
	 * 分页 关联客户授信集中度明细表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmRelationQuotaDetailRpt")
	public R<IPage<RcmRelationQuotaDetailRpt>> list(RcmRelationQuotaDetailRpt rcmRelationQuotaDetailRpt, Query query) {
		IPage<RcmRelationQuotaDetailRpt> pages = rcmRelationQuotaDetailRptService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmRelationQuotaDetailRpt));
		return R.data(pages);
	}

	/**
	 * 自定义分页 关联客户授信集中度明细表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmRelationQuotaDetailRpt")
	public R<IPage<RcmRelationQuotaDetailRptVO>> page(RcmRelationQuotaDetailRptVO rcmRelationQuotaDetailRpt, Query query) {
		IPage<RcmRelationQuotaDetailRptVO> pages = rcmRelationQuotaDetailRptService.selectRcmRelationQuotaDetailRptPage(Condition.getPage(query), rcmRelationQuotaDetailRpt);
		return R.data(pages);
	}

	/**
	 * 新增 关联客户授信集中度明细表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmRelationQuotaDetailRpt")
	public R save(@Valid @RequestBody RcmRelationQuotaDetailRpt rcmRelationQuotaDetailRpt) {
		return R.status(rcmRelationQuotaDetailRptService.save(rcmRelationQuotaDetailRpt));
	}

	/**
	 * 修改 关联客户授信集中度明细表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmRelationQuotaDetailRpt")
	public R update(@Valid @RequestBody RcmRelationQuotaDetailRpt rcmRelationQuotaDetailRpt) {
		return R.status(rcmRelationQuotaDetailRptService.updateById(rcmRelationQuotaDetailRpt));
	}

	/**
	 * 新增或修改 关联客户授信集中度明细表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmRelationQuotaDetailRpt")
	public R submit(@Valid @RequestBody RcmRelationQuotaDetailRpt rcmRelationQuotaDetailRpt) {
		return R.status(rcmRelationQuotaDetailRptService.saveOrUpdate(rcmRelationQuotaDetailRpt));
	}

	
	/**
	 * 删除 关联客户授信集中度明细表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmRelationQuotaDetailRptService.removeByIds(Func.toLongList(ids)));
	}

	
}
