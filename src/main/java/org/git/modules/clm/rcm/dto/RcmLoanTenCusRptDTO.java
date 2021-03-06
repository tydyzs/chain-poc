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
package org.git.modules.clm.rcm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.modules.clm.rcm.vo.RcmLoanTenCusRptVO;
import org.git.modules.clm.rcm.vo.RcmQuotaInfoVO;

import java.util.List;

/**
 * 最大十家客户贷款集中度明细表数据传输对象实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@ApiModel(value = "RcmLoanTenCusRptDTO", description = "限额分析-最大十家客户贷款集中度（前端展示）")
public class RcmLoanTenCusRptDTO  {
	/**
	 * 最大十家客户贷款集中度明细
	 */
	@ApiModelProperty(value = "最大十家客户贷款集中度明细")
	private List<RcmLoanTenCusRptVO> loanTenCusRptVOList;

	/**
	 * 限额管控参数信息
	 */
	@ApiModelProperty(value = "限额管控参数信息")
	private RcmQuotaInfoVO quotaInfoVO;



}
