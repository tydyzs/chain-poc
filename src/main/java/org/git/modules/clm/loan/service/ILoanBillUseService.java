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
package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.loan.dto.LoanBillUseEventDTO;

/**
 * 前置服务记录表 服务类
 *
 * @author liuye
 * @since 2019-12-30
 */
public interface ILoanBillUseService {
	/**
	 * 事件落地
	 * @param extAttributes
	 * @param billDiscountReqDTO
	 * @return
	 */
	LoanBillUseEventDTO insertEvent(ExtAttributes extAttributes, BillDiscountRequestDTO billDiscountReqDTO);

	/**
	 * 中间处理
	 * @param loanBillUseEventDTO
	 * @param extAttributes
	 */
	void middleHandle(LoanBillUseEventDTO loanBillUseEventDTO,ExtAttributes extAttributes,String eventTypeCd);
}
