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
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 借据信息表实体类
 *
 * @author git
 * @since 2019-11-22
 */
@Data
@TableName("TB_CRD_SUMMARY")
@ApiModel(value = "TbCrdSummary对象", description = "借据信息表")
public class TbCrdSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 借据ID
	 */
	@ApiModelProperty(value = "借据ID")
	@TableId("SUMMARY_ID")
	private String summaryId;

	/**
	 * 借据号
	 */
	@ApiModelProperty(value = "借据号")
	@TableField("SUMMARY_NUM")
	private String summaryNum;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value = "合同编号")
	@TableField("CONTRACT_NUM")
	private String contractNum;
	/**
	 * 批复ID
	 */
	@ApiModelProperty(value = "批复ID")
	@TableField("APPROVE_ID")
	private String approveId;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 业务品种
	 */
	@ApiModelProperty(value = "业务品种")
	@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	@TableField("EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	/**
	 * 借据金额
	 */
	@ApiModelProperty(value = "借据金额")
	@TableField("SUMMARY_AMT")
	private BigDecimal summaryAmt;
	/**
	 * 借据余额
	 */
	@ApiModelProperty(value = "借据余额")
	@TableField("SUMMARY_BAL")
	private BigDecimal summaryBal;
	/**
	 * 上月借据余额
	 */
	@ApiModelProperty(value = "上月借据余额")
	@TableField("LAST_SUMMARY_BAL")
	private BigDecimal lastSummaryBal;
	/**
	 * 借据起期
	 */
	@ApiModelProperty(value = "借据起期")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 借据止期
	 */
	@ApiModelProperty(value = "借据止期")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 借据状态（CD000172）
	 */
	@ApiModelProperty(value = "借据状态（CD000172）")
	@TableField("SUMMARY_STATUS")
	private String summaryStatus;
	/**
	 * 五级分类结果（CD000171）
	 */
	@ApiModelProperty(value = "五级分类结果（CD000171）")
	@TableField("CLASSIFY_RESULT")
	private String classifyResult;
	/**
	 * 投向行业代码（CD000015）
	 */
	@ApiModelProperty(value = "投向行业代码（CD000015）")
	@TableField("INDUSTRY")
	private String industry;
	/**
	 * 担保方式
	 */
	@ApiModelProperty(value = "担保方式")
	@TableField("GUARANTEE_TYPE")
	private String guaranteeType;
	/**
	 * 主要担保方式
	 */
	@ApiModelProperty(value = "主要担保方式")
	@TableField("MAIN_GUARANTEE_TYPE")
	private String mainGuaranteeType;
	/**
	 * 保证金比例
	 */
	@ApiModelProperty(value = "保证金比例")
	@TableField("DEPOSIT_RATIO")
	private BigDecimal depositRatio;
	/**
	 * 票据编号
	 */
	@ApiModelProperty(value = "票据编号")
	@TableField("BILL_NUM")
	private String billNum;
	/**
	 * 票据种类（CD000080）
	 */
	@ApiModelProperty(value = "票据种类（CD000080）")
	@TableField("BILL_TYPE")
	private String billType;

	/**
	 * 承兑行ECIF客户号
	 */
	@ApiModelProperty(value = "承兑行ECIF客户号")
	@TableField("acceptor_ecif_num")
	private String acceptorEcifNum;


	/**
	 * 收款人名称
	 */
	@ApiModelProperty(value = "收款人名称")
	@TableField("PAYEE_NAME")
	private String payeeName;
	/**
	 * 收款人账号
	 */
	@ApiModelProperty(value = "收款人账号")
	@TableField("PAYEE_ACCT")
	private String payeeAcct;
	/**
	 * 收款人开户行行号
	 */
	@ApiModelProperty(value = "收款人开户行行号")
	@TableField("PAYEE_BANK_NUM")
	private String payeeBankNum;
	/**
	 * 收款人开户行行名
	 */
	@ApiModelProperty(value = "收款人开户行行名")
	@TableField("PAYEE_BANK_NAME")
	private String payeeBankName;
	/**
	 * 付款行名称
	 */
	@ApiModelProperty(value = "付款行名称")
	@TableField("PAY_NAME")
	private String payName;
	/**
	 * 付款行行号
	 */
	@ApiModelProperty(value = "付款行行号")
	@TableField("PAY_ACCT")
	private String payAcct;
	/**
	 * 出票人名称
	 */
	@ApiModelProperty(value = "出票人名称")
	@TableField("DRAWER_NAME")
	private String drawerName;
	/**
	 * 出票人账号
	 */
	@ApiModelProperty(value = "出票人账号")
	@TableField("DRAWER_ACCT")
	private String drawerAcct;
	/**
	 * 出票人开户行行号
	 */
	@ApiModelProperty(value = "出票人开户行行号")
	@TableField("DRAWER_BANK_NUM")
	private String drawerBankNum;
	/**
	 * 出票人开户行行名
	 */
	@ApiModelProperty(value = "出票人开户行行名")
	@TableField("DRAWER_BANK_NAME")
	private String drawerBankName;
	/**
	 * 票据状态（CD000078）
	 */
	@ApiModelProperty(value = "票据状态（CD000078）")
	@TableField("BILL_STATUS")
	private String billStatus;
	/**
	 * 入账机构
	 */
	@ApiModelProperty(value = "入账机构")
	@TableField("LOAN_ORG")
	private String loanOrg;
	/**
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
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
