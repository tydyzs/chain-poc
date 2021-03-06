/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.clm.loan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 业务凭证信息表实体类
 *
 * @author liuye
 * @since 2019-11-27
 */
@Data
@TableName("TB_CRD_BUSI_CERT_INFO")
@ApiModel(value = "CrdBusiCertInfo对象", description = "业务凭证信息表")
public class CrdBusiCertInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 凭证信息id
	 */
	@ApiModelProperty(value = "凭证信息id")
	@TableId(value = "CRET_INFO_ID", type = IdType.UUID)
	private String cretInfoId;
	/**
	 * 额度明细编号
	 */
	@ApiModelProperty(value = "额度明细编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 三级额度产品
	 */
	@ApiModelProperty(value = "三级额度产品")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 授信机构
	 */
	@ApiModelProperty(value = "授信机构")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	@TableField("BUSI_DEAL_NUM")
	private String busiDealNum;
	/**
	 * 业务产品编号
	 */
	@ApiModelProperty(value = "业务产品编号")
	@TableField("BUSI_PRD_NUM")
	private String busiPrdNum;
	/**
	 * 交易描述
	 */
	@ApiModelProperty(value = "交易描述")
	@TableField("BUSI_DEAL_DESC")
	private String busiDealDesc;
	/**
	 * 本方机构
	 */
	@ApiModelProperty(value = "本方机构")
	@TableField("BUSI_DEAL_ORG_NUM")
	private String busiDealOrgNum;
	/**
	 * 本方机构名称
	 */
	@ApiModelProperty(value = "本方机构名称")
	@TableField("BUSI_DEAL_ORG_NAME")
	private String busiDealOrgName;
	/**
	 * 对手机构
	 */
	@ApiModelProperty(value = "对手机构")
	@TableField("BUSI_OPPT_ORG_NUM")
	private String busiOpptOrgNum;
	/**
	 * 对手机构名称
	 */
	@ApiModelProperty(value = "对手机构名称")
	@TableField("BUSI_OPPT_ORG_NAME")
	private String busiOpptOrgName;
	/**
	 * 交易总金额
	 */
	@ApiModelProperty(value = "交易总金额")
	@TableField("BUSI_SUM_AMT")
	private BigDecimal busiSumAmt;
	/**
	 * 凭证张数
	 */
	@ApiModelProperty(value = "凭证张数")
	@TableField("BUSI_CERT_CNT")
	private BigDecimal busiCertCnt;
	/**
	 * 凭证编号
	 */
	@ApiModelProperty(value = "凭证编号")
	@TableField("CERT_NUM")
	private String certNum;
	/**
	 * 凭证品种
	 */
	@ApiModelProperty(value = "凭证品种")
	@TableField("CERT_TYPE_CD")
	private String certTypeCd;
	/**
	 * 凭证性质
	 */
	@ApiModelProperty(value = "凭证性质")
	@TableField("CERT_PPT_CD")
	private String certPptCd;

	/**
	 *凭证计息期限类型
	 */
	@ApiModelProperty(value = "凭证计息期限类型")
	@TableField("CERT_INTEREST_PERI_TYPE")
	private String certInterestPeriType;

	/**
	 * 凭证计息期限
	 */
	@ApiModelProperty(value = "凭证计息期限")
	@TableField("CERT_INTEREST_PERIOD")
	private String certInterestPeriod;

	/**
	 * 收益率/利率类型
	 */
	@ApiModelProperty(value = "收益率/利率类型")
	@TableField("CERT_INTEREST_RATE_TYPE")
	private String certInterestRateType;
	/**
	 * 收益率/利率
	 */
	@ApiModelProperty(value = "收益率/利率")
	@TableField("CERT_INTEREST_RATE")
	private BigDecimal certInterestRate;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CERT_CURRENCY_CD")
	private String certCurrencyCd;
	/**
	 * 凭证申请金额
	 */
	@ApiModelProperty(value = "凭证申请金额")
	@TableField("CERT_SEQ_AMT")
	private BigDecimal certSeqAmt;
	/**
	 * 凭证用信金额
	 */
	@ApiModelProperty(value = "凭证用信金额")
	@TableField("CERT_APPLY_AMT")
	private BigDecimal certApplyAmt;
	/**
	 * 凭证用信余额
	 */
	@ApiModelProperty(value = "凭证用信余额")
	@TableField("CERT_APPLY_BALANCE")
	private BigDecimal certApplyBalance;
	/**
	 * 凭证状态
	 */
	@ApiModelProperty(value = "凭证状态")
	@TableField("CERT_STATUS")
	private String certStatus;
	/**
	 * 凭证起期
	 */
	@ApiModelProperty(value = "凭证起期")
	@TableField("CERT_BEGIN_DATE")
	private String certBeginDate;
	/**
	 * 凭证止期
	 */
	@ApiModelProperty(value = "凭证止期")
	@TableField("CERT_END_DATE")
	private String certEndDate;
	/**
	 * 凭证结清日期
	 */
	@ApiModelProperty(value = "凭证结清日期")
	@TableField("CERT_FINISH_DATE")
	private String certFinishDate;
	/**
	 * 发行人客户号
	 */
	@ApiModelProperty(value = "发行人客户号")
	@TableField("CERT_DRAWER_CUST_NUM")
	private String certDrawerCustNum;
	/**
	 * 发行人客户名称
	 */
	@ApiModelProperty(value = "发行人客户名称")
	@TableField("CERT_DRAWER_NAME")
	private String certDrawerName;
	/**
	 * 发行人代理/承兑行号
	 */
	@ApiModelProperty(value = "发行人代理/承兑行号")
	@TableField("CERT_DRAWER_BANK_NUM")
	private String certDrawerBankNum;
	/**
	 * 发行人代理/承兑行名
	 */
	@ApiModelProperty(value = "发行人代理/承兑行名")
	@TableField("CERT_DRAWER_BANK_NAME")
	private String certDrawerBankName;
	/**
	 * 发行人代理/承兑行法人行
	 */
	@ApiModelProperty(value = "发行人代理/承兑行法人行")
	@TableField("cert_drawer_bank_legal")
	private String certDrawerBankLegal;

	/**
	 * 担保方式
	 */
	@ApiModelProperty(value = "担保方式")
	@TableField("CERT_GUARANTY_TYPE")
	private String certGuarantyType;
	/**
	 * 担保人
	 */
	@ApiModelProperty(value = "担保人")
	@TableField("CERT_GUARANTY_PERSON")
	private String certGuarantyPerson;
	/**
	 * 上月凭证金额
	 */
	@ApiModelProperty(value = "上月凭证金额")
	@TableField("LAST_SUMMARY_BAL")
	private BigDecimal lastSummaryBal;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 备注信息
	 */
	@ApiModelProperty(value = "备注信息")
	@TableField("CERT_BUSI_REMARK")
	private String certBusiRemark;
	/**
	 * 原业务编号
	 */
	@ApiModelProperty(value = "原业务编号")
	@TableField("SOURCE_BUSI_NUM")
	private String sourceBusiNum;
	/**
	 * 原授信机构号
	 */
	@ApiModelProperty(value = "原授信机构号")
	@TableField("SOURCE_ORG_NUM")
	private String sourceOrgNum;

}
