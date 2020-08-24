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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.tool.api.R;
import org.git.modules.clm.credit.service.IFund;
import org.springframework.web.bind.annotation.*;

/**
 * 访问票据esb接口
 *
 * @author shc
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-credit/fund")
@Api(value = "调用资金接口类", tags = "调用资金接口类")
public class FundController extends ChainController {

	private IFund iFund;

	/**
	 * 资金对账
	 */
	@GetMapping("/verifyNoticeService")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "对账", notes = "传入交易日期")
	public R verifyNoticeService(@ApiParam(value = "交易日期", required = true) @RequestParam String tranDate) {
		return R.data(iFund.verifyNoticeService(tranDate));
	}

}
