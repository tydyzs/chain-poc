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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;

import lombok.AllArgsConstructor;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.git.modules.clm.rcm.entity.RcmNetCapital;
import org.git.modules.clm.rcm.vo.RcmNetCapitalVO;
import org.git.modules.clm.rcm.service.IRcmNetCapitalService;
import org.git.core.boot.ctrl.ChainController;
import springfox.documentation.annotations.ApiIgnore;


import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;

/**
 * 资本信息配置表 控制器
 *
 * @author git
 * @since 2019-10-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmNetCapital")
@Api(value = "资本信息配置表", tags = "资本信息配置表接口")
public class RcmNetCapitalController extends ChainController {

	private IRcmNetCapitalService rcmNetCapitalService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmNetCapital")
	public R<RcmNetCapital> detail(RcmNetCapitalVO rcmNetCapital) {
		RcmNetCapital detail = rcmNetCapitalService.getOne(Condition.getQueryWrapper(rcmNetCapital));
		return R.data(detail);
	}

	/**
	 * 分页 资本信息配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmNetCapital")
	public R<IPage<RcmNetCapital>> list(@RequestParam(value = "useDates[]", required = false) String[] useDates,
										@RequestParam(required = false) String useOrgNum,
										@RequestParam(required = false) String netState,
										Query query) {
		RcmNetCapital rcmNetCapital = new RcmNetCapital();
		rcmNetCapital.setUseOrgNum(useOrgNum);
		rcmNetCapital.setNetState(netState);
		LambdaQueryWrapper<RcmNetCapital> params = Condition.getQueryWrapper(rcmNetCapital).lambda();
		String begin = null;
		String end = null;
		if (useDates != null && useDates.length == 2) {
			if (useDates[0] != null && useDates[0].length() >= 10) {
				begin = useDates[0].substring(0, 10);
			}
			if (useDates[1] != null && useDates[1].length() >= 10) {
				end = useDates[1].substring(0, 10);
			}
			params.between(RcmNetCapital::getUseDate, begin, end);
		}

		IPage<RcmNetCapital> pages = rcmNetCapitalService.page(Condition.getPage(query), params);
		return R.data(pages);
	}

	/**
	 * 多表联合查询自定义分页
	 */
	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "useOrgNum", value = "机构编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "机构名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "useDate", value = "导入日期", paramType = "query", dataType = "Date[]"),
		@ApiImplicitParam(name = "netState", value = "生效状态", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmNetCapital")
	public R<IPage<RcmNetCapitalVO>> page(@ApiIgnore RcmNetCapitalVO rcmNetCapital, Query query) {
//		IPage<RcmNetCapitalVO> pages = rcmNetCapitalService.selectRcmNetCapitalPage(Condition.getPage(query), rcmNetCapital);
//		return R.data(pages);
		return null;
	}

	/**
	 * 新增 资本信息配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入RcmNetCapitalVO")
	public R save(@Valid @RequestBody RcmNetCapitalVO rcmNetCapitalVO) {
		if (rcmNetCapitalVO != null && rcmNetCapitalVO.getUseDate().length() >= 10) {
			rcmNetCapitalVO.setUseDate(rcmNetCapitalVO.getUseDate().substring(0, 10));//分割日期字符串
		}
		return R.status(rcmNetCapitalService.saveNetCapital(rcmNetCapitalVO));
	}

	/**
	 * 新增或修改 资本信息配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmNetCapital")
	public R submit(@Valid @RequestBody RcmNetCapital rcmNetCapital) {
		return R.status(rcmNetCapitalService.saveOrUpdate(rcmNetCapital));
	}


	/**
	 * 删除 资本信息配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入netCapitalNum")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String netCapitalNum) {
		return R.status(rcmNetCapitalService.removeRcmNetCapital(netCapitalNum));
	}


}
