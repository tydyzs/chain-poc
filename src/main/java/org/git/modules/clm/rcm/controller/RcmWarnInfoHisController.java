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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.modules.clm.rcm.service.IRcmWarnInfoHisService;
import org.git.modules.clm.rcm.vo.RcmWarnInfoHisVO;
import org.git.modules.clm.rcm.vo.RcmWarnInfoQueryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限额预警历史信息表（一年以上） 控制器
 *
 * @author git
 * @since 2019-12-04
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/warn-info-his")
@Api(value = "限额预警历史信息表（一年以上）", tags = "限额预警历史信息表（一年以上）接口")
public class RcmWarnInfoHisController extends ChainController {

	private IRcmWarnInfoHisService rcmWarnInfoHisService;


	/**
	 * 自定义分页 限额预警历史信息表（一年以上）
	 */
	@GetMapping(value = "/query", produces = "application/json;charset=utf-8")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询限额预警历史信息", notes = "传入rcmWarnInfo")
	public R<IPage<RcmWarnInfoHisVO>> query(RcmWarnInfoQueryVO rcmWarnInfo, Query query) {
		IPage<RcmWarnInfoHisVO> pages = rcmWarnInfoHisService.queryRcmWarnInfoHis(Condition.getPage(query), rcmWarnInfo);
		return R.data(pages);
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "useOrgNum", value = "法人机构号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "warnNum", value = "预警信息编号", paramType = "query", dataType = "string"),
	})
	@ApiOperation(value = "详情", notes = "传入warnNum")
	public R<RcmWarnInfoHisVO> detail(RcmWarnInfoQueryVO rcmWarnInfo) {
		RcmWarnInfoHisVO detail = rcmWarnInfoHisService.selectById(rcmWarnInfo);
		return R.data(detail);
	}

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入rcmWarnInfoHis")
//	public R<RcmWarnInfoHis> detail(RcmWarnInfoHis rcmWarnInfoHis) {
//		RcmWarnInfoHis detail = rcmWarnInfoHisService.getOne(Condition.getQueryWrapper(rcmWarnInfoHis));
//		return R.data(detail);
//	}
//
//	/**
//	 * 分页 限额预警历史信息表（一年以上）
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入rcmWarnInfoHis")
//	public R<IPage<RcmWarnInfoHis>> list(RcmWarnInfoHis rcmWarnInfoHis, Query query) {
//		IPage<RcmWarnInfoHis> pages = rcmWarnInfoHisService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmWarnInfoHis));
//		return R.data(pages);
//	}
//
//
//	/**
//	 * 新增 限额预警历史信息表（一年以上）
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入rcmWarnInfoHis")
//	public R save(@Valid @RequestBody RcmWarnInfoHis rcmWarnInfoHis) {
//		return R.status(rcmWarnInfoHisService.save(rcmWarnInfoHis));
//	}
//
//	/**
//	 * 修改 限额预警历史信息表（一年以上）
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入rcmWarnInfoHis")
//	public R update(@Valid @RequestBody RcmWarnInfoHis rcmWarnInfoHis) {
//		return R.status(rcmWarnInfoHisService.updateById(rcmWarnInfoHis));
//	}
//
//	/**
//	 * 新增或修改 限额预警历史信息表（一年以上）
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入rcmWarnInfoHis")
//	public R submit(@Valid @RequestBody RcmWarnInfoHis rcmWarnInfoHis) {
//		return R.status(rcmWarnInfoHisService.saveOrUpdate(rcmWarnInfoHis));
//	}
//
//
//	/**
//	 * 删除 限额预警历史信息表（一年以上）
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value = "删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(rcmWarnInfoHisService.removeByIds(Func.toLongList(ids)));
//	}


}
