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
package org.git.modules.clm.credit.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.modules.clm.credit.service.IFund;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundAccountRequestDTO;
import org.git.modules.clm.front.service.IBMMQService;
import org.springframework.stereotype.Service;

/**
 * 调用资金esb
 * @author SHC
 * @since 2019-12-25
 */
@Service
@AllArgsConstructor
@Slf4j
public class FundImpl implements IFund {
	IBMMQService ibmmqService;
	@Override
	public Response verifyNoticeService(String tranDate){
		String serviceId = JxrcbConstant.S00100000430200.name();//服务代码
		serviceId=serviceId.substring(1);
		Request request;//请求对象
		FundAccountRequestDTO fundAccountRequestDTO = new FundAccountRequestDTO();
		fundAccountRequestDTO.setTranDate(tranDate);
		/*发送请求，获取响应*/
		request=fundAccountRequestDTO;
		Response response = ibmmqService.request(serviceId, request);
		return response;
	}

}
