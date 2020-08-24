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
 * 风险暴露超过一级资本净额2.5%的集团客户视图实体类
 *
 * @author zhouweijie
 */
@Data
@ApiModel(value = "RcmRiskOverTwoPointFivePercentRptVOO对象", description = "风险暴露超过一级资本净额2.5%的集团客户视图实体类")
public class RcmRiskOverTwoPointFivePercentRptVO {

	/**
	 * 集团客户编号
	 */
	@ApiModelProperty(value = "集团客户编号")
	private String groupCustomerNum;

	/**
	 * 集团名称
	 */
	@ApiModelProperty(value = "集团名称")
	private String groupCustomerName;

	/**
	 * 集团批复总额
	 */
	@ApiModelProperty(value = "集团批复总额")
	private BigDecimal groupApproveExpAmount;

	/**
	 * 集团已用总额
	 */
	@ApiModelProperty(value = "集团已用总额")
	private BigDecimal groupCreditExpBalance;

	/**
	 * 一级资本净额占比
	 */
	@ApiModelProperty(value = "一级资本净额占比")
	private BigDecimal groupCapitalRatio;


	/**
	 * 成员关系类型
	 */
	@ApiModelProperty(value = "成员关系类型")
	private String memberRelType;

	/**
	 * 成员客户名称
	 */
	@ApiModelProperty(value = "成员客户名称")
	private String customerName;

	/**
	 * 成员客户编号
	 */
	@ApiModelProperty(value = "成员客户编号")
	private String customerNum;

	/**
	 * 批复金额
	 */
	@ApiModelProperty(value = "批复金额")
	private BigDecimal approveExpAmount;

	/**
	 * 敞口余额
	 */
	@ApiModelProperty(value = "敞口余额")
	private BigDecimal creditExpBalance;

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
