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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.vo.ServiceRecordVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前置服务记录表 服务类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
public interface ILoanBillService  {
	/**插入事件主表和明细表数据数据*/
	String insertEvent(ExtAttributes extAttributes, BillDiscountRequestDTO billDiscountReqDTO);
	/**
	 *额度拆分，本地更新处理前的检查
	 * */
	String check(ExtAttributes extAttributes,BillDiscountRequestDTO billDiscountReqDTO,BillDiscountResponseDTO billDiscountResDTO);
	/**
	 *额度拆分，本地更新处理
	 * */
	 String crdSplitUp(ExtAttributes extAttributes,BillDiscountRequestDTO billDiscountReqDTO,BillDiscountResponseDTO billDiscountResDTO);

	/**
	 * 额度更新本地处理范围
	 * crdUpdateMap
	 * */
	void crdUpdate(String EventId);

	/***
	 * 更新事件表状态
	 */
	void updateEventStatus(String EventMainUUID,String eventStatus);
}
