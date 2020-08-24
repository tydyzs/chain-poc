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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同信息表实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@TableName("TB_CRD_CONTRACT")
@ApiModel(value = "TbCrdContract对象", description = "合同信息表")
public class TbCrdContract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value = "合同编号")
	@TableId("CONTRACT_NUM")
	private String contractNum;
	/**
	 * 批复ID
	 */
	@ApiModelProperty(value = "批复ID")
	@TableField("APPROVE_ID")
	private String approveId;
	/**
	 * ecif客户编号
	 */
	@ApiModelProperty(value = "ecif客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 业务品种
	 */
	@ApiModelProperty(value = "业务品种")
	@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	 * 币种（CD000019）
	 */
	@ApiModelProperty(value = "币种（CD000019）")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	@TableField("EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	/**
	 * 合同金额
	 */
	@ApiModelProperty(value = "合同金额")
	@TableField("CONTRACT_AMT")
	private BigDecimal contractAmt;
	/**
	 * 合同已用
	 */
	@ApiModelProperty(value = "合同已用")
	@TableField("CONTRACT_USED")
	private BigDecimal contractUsed;
	/**
	 * 合同可用
	 */
	@ApiModelProperty(value = "合同可用")
	@TableField("CONTRACT_AVI")
	private BigDecimal contractAvi;

	/**
	 * 合同起期
	 */
	@ApiModelProperty(value = "合同起期")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 合同止期
	 */
	@ApiModelProperty(value = "合同止期")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 保证金比例
	 */
	@ApiModelProperty(value = "保证金比例")
	@TableField("DEPOSIT_RATIO")
	private BigDecimal depositRatio;
	/**
	 * 投向行业代码（CD000015）
	 */
	@ApiModelProperty(value = "投向行业代码（CD000015）")
	@TableField("INDUSTRY")
	private String industry;
	/**
	 * 担保方式（CD000100）
	 */
	@ApiModelProperty(value = "担保方式（CD000100）")
	@TableField("GUARANTEE_TYPE")
	private String guaranteeType;
	/**
	 * 主要担保方式（CD000100）
	 */
	@ApiModelProperty(value = "主要担保方式（CD000100）")
	@TableField("MAIN_GUARANTEE_TYPE")
	private String mainGuaranteeType;

	/**
	 * 担保方式分类（CD000101）
	 */
	@ApiModelProperty(value = "担保方式分类（CD000101）")
	private String guaranteeTypeDetail;
	/**
	 * 合同余额
	 */
	@ApiModelProperty(value = "合同余额")
	@TableField("CONTRACT_BAL")
	private BigDecimal contractBal;
	/**
	 * 五级分类结果（CD000171）
	 */
	@ApiModelProperty(value = "五级分类结果（CD000171）")
	@TableField("CLASSIFY_RESULT")
	private String classifyResult;
	/**
	 * 终结日期
	 */
	@ApiModelProperty(value = "终结日期")
	@TableField("CLOSE_DATE")
	private Timestamp closeDate;
	/**
	 * 合同状态（CD000094）
	 */
	@ApiModelProperty(value = "合同状态（CD000094）")
	@TableField("CONTRACT_STATUS")
	private String contractStatus;
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
	/**
	 * 是否可循环（CD000167）
	 */
	@ApiModelProperty(value = "是否可循环（CD000167）")
	@TableField("IS_CYCLE")
	private String isCycle;
	/**
	 * 申请日期
	 */
	@ApiModelProperty(value = "申请日期")
	@TableField("TRAN_DATE")
	private String tranDate;
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;


}
