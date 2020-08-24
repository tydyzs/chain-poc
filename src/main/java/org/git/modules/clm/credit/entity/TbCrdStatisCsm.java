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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 额度统计表-客户（实时）实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@TableName("TB_CRD_STATIS_CSM")
@ApiModel(value = "TbCrdStatisCsm对象", description = "额度统计表-客户（实时）")
public class TbCrdStatisCsm implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 统计ID
	 */
	@ApiModelProperty(value = "统计ID")
	@TableId("STATIS_ID")
	private String statisId;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 批复数量
	 */
	@ApiModelProperty(value = "批复数量")
	@TableField("APPROVE_COUNT")
	private String approveCount;

	/**
	 * 批复敞口金额
	 */
	@ApiModelProperty(value = "批复敞口金额")
	@TableField("APPROVE_EXP_AMOUNT")
	private BigDecimal approveExpAmount;


	/**
	 * 授信敞口余额
	 */
	@ApiModelProperty(value = "授信敞口余额")
	@TableField("CREDIT_EXP_BALANCE")
	private BigDecimal creditExpBalance;


	/**
	 * 贷款敞口余额
	 */
	@ApiModelProperty(value = "贷款敞口余额")
	@TableField("LOAN_EXP_BALANCE")
	private BigDecimal loanExpBalance;


	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private BigDecimal limitCredit;


	/**
	 * 可用授信额度
	 */
	@ApiModelProperty(value = "可用授信额度")
	@TableField("LIMIT_AVI")
	private BigDecimal limitAvi;


	/**
	 * 已用授信额度
	 */
	@ApiModelProperty(value = "已用授信额度")
	@TableField("LIMIT_USED")
	private BigDecimal limitUsed;

	/**
	 * 已用敞口
	 */
	@ApiModelProperty(value = "已用敞口")
	@TableField("EXP_USED")
	private BigDecimal expUsed;


	/**
	 * 可用敞口
	 */
	@ApiModelProperty(value = "可用敞口")
	@TableField("EXP_AVI")
	private BigDecimal expAvi;

	/**
	 * 机构
	 */
	@ApiModelProperty(value = "机构")
	@TableField("ORG_NUM")
	private String orgNum;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;


}
