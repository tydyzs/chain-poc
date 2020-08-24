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
package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 风险暴露超过1亿（含）的单一客户视图实体类
 *
 * @author zhouweijie
 */
@Data
@ApiModel(value = "RcmRiskOverOneHundredMillionRptVO对象", description = "风险暴露超过1亿（含）的单一客户")
public class RcmRiskOverOneHundredMillionRptVO {

	/**
	 * 客户号
	 */
	@ApiModelProperty(value = "客户号")
	private String customerNum;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String customerName;

	/**
	 * 批复额度
	 */
	@ApiModelProperty(value = "批复额度")
	private BigDecimal approveExpAmount;

	/**
	 * 授信敞口余额
	 */
	@ApiModelProperty(value = "授信敞口余额")
	private BigDecimal creditExpBalance;

	/**
	 * 所属集团
	 */
	@ApiModelProperty(value = "所属集团")
	private String groupName;

	/**
	 * 较上月变化
	 */
	@ApiModelProperty(value = "较上月变化")
	private BigDecimal change;

	/**
	 * 占一级资本净额比例(授信敞口余额/一级资本净额)
	 */
	@ApiModelProperty(value = "占一级资本净额比例(授信敞口余额/一级资本净额)")
	private BigDecimal ratio;

//	/**
//	 * 客户经理
//	 */
//	@ApiModelProperty(value = "客户经理")
//	private String userNum;
//
//	/**
//	 * 客户经理名称
//	 */
//	@ApiModelProperty(value = "客户经理名称")
//	private String userName;
//
//	/**
//	 * 管护机构
//	 */
//	@ApiModelProperty(value = "管护机构")
//	private String orgNum;
//
//	/**
//	 * 管护机构名称
//	 */
//	@ApiModelProperty(value = "管护机构名称")
//	private String orgName;

}
