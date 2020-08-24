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
package org.git.modules.clm.credit.service;

import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditDepositRequestDTO;

/**
 * CLM202额度圈存、分配、调整
 *
 * @author shc
 * @since 2019-12-03
 */
public interface IFundEarmarkService {
	/**
	 * 事件落地前的检查
	 * */
	String checkEventStatus(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO);
	/**
	 * 事件落地
	 * */
	String saveEventStatus(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO);

	/**
	 * 客户管控检查
	 * */
	 String control(String branchId,FundCreditDepositRequestDTO fundCreditDepositRequestDTO);
	/**
	 * 本地处理范围
	 * */
	void localHandle(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO, String EventMainUUID);

	/**
	 * 时间状态修改
	 */
	void updateEventStatus(String EventMainUUID, String eventStatus);

}
