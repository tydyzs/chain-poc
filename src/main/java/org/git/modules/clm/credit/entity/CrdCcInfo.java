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
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 信用卡系统信息实体类
 *
 * @author git
 * @since 2019-12-27
 */
@Data
@TableName("TB_CRD_CC_INFO")
@ApiModel(value = "CrdCcInfo对象", description = "信用卡系统信息")
public class CrdCcInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 信用卡ID
	*/
		@ApiModelProperty(value = "信用卡ID")
		@TableId("ID")
	private String id;
	/**
	* 客户编号
	*/
		@ApiModelProperty(value = "客户编号")
		@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	* 账户编号
	*/
		@ApiModelProperty(value = "账户编号")
		@TableField("ACCT_NUM")
	private String acctNum;
	/**
	* 产品代码
	*/
		@ApiModelProperty(value = "产品代码")
		@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	* 币种
	*/
		@ApiModelProperty(value = "币种")
		@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	* 信用额度
	*/
		@ApiModelProperty(value = "信用额度")
		@TableField("CREDIT_LIMIT")
	private String creditLimit;
	/**
	* 临时额度
	*/
		@ApiModelProperty(value = "临时额度")
		@TableField("TEMP_LIMIT")
	private String tempLimit;
	/**
	* 临时额度开始日期
	*/
		@ApiModelProperty(value = "临时额度开始日期")
		@TableField("TEMP_LIMIT_BEGIN_DATE")
	private String tempLimitBeginDate;
	/**
	* 临时额度结束日期
	*/
		@ApiModelProperty(value = "临时额度结束日期")
		@TableField("TEMP_LIMIT_END_DATE")
	private String tempLimitEndDate;
	/**
	* 取现额度比例
	*/
		@ApiModelProperty(value = "取现额度比例")
		@TableField("CASH_LIMIT_RT")
	private String cashLimitRt;
	/**
	* 授权超限比例
	*/
		@ApiModelProperty(value = "授权超限比例")
		@TableField("OVRLMT_RATE")
	private String ovrlmtRate;
	/**
	* 额度内分期额度比例
	*/
		@ApiModelProperty(value = "额度内分期额度比例")
		@TableField("LOAN_LIMIT_RT")
	private String loanLimitRt;
	/**
	* 当前余额
	*/
		@ApiModelProperty(value = "当前余额")
		@TableField("CURR_BAL")
	private String currBal;
	/**
	* 取现余额
	*/
		@ApiModelProperty(value = "取现余额")
		@TableField("CASH_BAL")
	private String cashBal;
	/**
	* 本金余额
	*/
		@ApiModelProperty(value = "本金余额")
		@TableField("PRINCIPAL_BAL")
	private String principalBal;
	/**
	* 额度内分期余额
	*/
		@ApiModelProperty(value = "额度内分期余额")
		@TableField("LOAN_BAL")
	private String loanBal;
	/**
	* 全部应还款额
	*/
		@ApiModelProperty(value = "全部应还款额")
		@TableField("QUAL_GRACE_BAL")
	private String qualGraceBal;
	/**
	* 本月实际还款金额
	*/
		@ApiModelProperty(value = "本月实际还款金额")
		@TableField("ACTUAL_PAYMENT_AMT")
	private String actualPaymentAmt;
	/**
	* 核销金额
	*/
		@ApiModelProperty(value = "核销金额")
		@TableField("CHARGE_OFF_AMT")
	private String chargeOffAmt;
	/**
	* 未出账单余额
	*/
		@ApiModelProperty(value = "未出账单余额")
		@TableField("UNSTMT_BAL")
	private String unstmtBal;
	/**
	* 当期消费本金余额
	*/
		@ApiModelProperty(value = "当期消费本金余额")
		@TableField("CTD_REAIL_PRIN_BAL")
	private String ctdReailPrinBal;
	/**
	* 已出账单消费本金余额
	*/
		@ApiModelProperty(value = "已出账单消费本金余额")
		@TableField("STMT_REAIL_PRIN_BAL")
	private String stmtReailPrinBal;
	/**
	* 当期取现本金余额
	*/
		@ApiModelProperty(value = "当期取现本金余额")
		@TableField("CTD_CASH_PRIN_BAL")
	private String ctdCashPrinBal;
	/**
	* 已出账单取现本金余额
	*/
		@ApiModelProperty(value = "已出账单取现本金余额")
		@TableField("STMT_CASH_PRIN_BAL")
	private String stmtCashPrinBal;
	/**
	* 当期分期应还本金余额
	*/
		@ApiModelProperty(value = "当期分期应还本金余额")
		@TableField("CTD_LOAN_PRIN_BAL")
	private String ctdLoanPrinBal;
	/**
	* 已出账单分期应还本金余额
	*/
		@ApiModelProperty(value = "已出账单分期应还本金余额")
		@TableField("STMT_LOAN_PRIN_BAL")
	private String stmtLoanPrinBal;
	/**
	* 额度外分期余额
	*/
		@ApiModelProperty(value = "额度外分期余额")
		@TableField("LARGE_LOAN_BAL_XFROUT")
	private String largeLoanBalXfrout;
	/**
	* 未匹配借记金额
	*/
		@ApiModelProperty(value = "未匹配借记金额")
		@TableField("UNMATCH_DB_AMT")
	private String unmatchDbAmt;
	/**
	* 未匹配贷记金额
	*/
		@ApiModelProperty(value = "未匹配贷记金额")
		@TableField("UNMATCH_CR_AMT")
	private String unmatchCrAmt;
	/**
	* 超限金额
	*/
		@ApiModelProperty(value = "超限金额")
		@TableField("OVERLIMIT_AMT")
	private String overlimitAmt;
	/**
	* 当期欠款余额
	*/
		@ApiModelProperty(value = "当期欠款余额")
		@TableField("REMAIN_GRACE_BAL")
	private String remainGraceBal;
	/**
	* 是否全额还款
	*/
		@ApiModelProperty(value = "是否全额还款")
		@TableField("GRACE_DAYS_FULL_IND")
	private String graceDaysFullInd;
	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		@TableField("CREATE_TIME")
	private LocalDateTime createTime;
	/**
	* 更新时间
	*/
		@ApiModelProperty(value = "更新时间")
		@TableField("UPDATE_TIME")
	private LocalDateTime updateTime;

	/**
	 * 核算机构
	 */
	@ApiModelProperty(value = "核算机构")
	@TableField("ORG_NUM")
	private String orgNum;

	/**
	 * 账单周期
	 */
	@ApiModelProperty(value = "账单周期")
	@TableField("BILLING_CYCLE")
	private String billingCycle;
}
