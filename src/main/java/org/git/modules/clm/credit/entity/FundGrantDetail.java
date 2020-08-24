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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 资金授信事件明细实体类
 *
 * @author liuye
 * @since 2019-11-15
 */
@Data
@TableName("TB_FUND_GRANT_DETAIL")
@ApiModel(value = "FundGrantDetail对象", description = "资金授信事件明细")
public class FundGrantDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 事件名细表ID
	 */
	@ApiModelProperty(value = "事件名细表ID")
	@TableId("EVENT_DETAIL_ID")
	private String eventDetailId;

	/**
	 * 事件主表ID
	 */
	@ApiModelProperty(value = "事件主表ID")
	@TableField("EVENT_MAIN_ID")
	private String eventMainId;

	/**
	 * 交易流水号
	 */
	@ApiModelProperty(value = "交易流水号")
	@TableField("TRAN_SEQ_SN")
	private String tranSeqSn;
	/**
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;
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
	 * 授信总额产品
	 */
	@TableField("CRD_MAIN_PRD")
	@ApiModelProperty(value = "授信总额产品")
	private String crdMainPrd;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CRD_CURRENCY_CD")
	private String crdCurrencyCd;
	/**
	 * 授信总额度
	 */
	@ApiModelProperty(value = "授信总额度")
	@TableField("CRD_SUM_AMT")
	private BigDecimal crdSumAmt;
	/**
	 * 额度生效日
	 */
	@ApiModelProperty(value = "额度生效日")
	@TableField("CRD_BEGIN_DATE")
	private String crdBeginDate;
	/**
	 * 额度到期日
	 */
	@ApiModelProperty(value = "额度到期日")
	@TableField("CRD_END_DATE")
	private String crdEndDate;
	/**
	 * 切分总额
	 */
	@ApiModelProperty(value = "切分总额")
	@TableField("BUSI_SEGM_AMT")
	private BigDecimal busiSegmAmt;
	/**
	 * 切分数
	 */
	@ApiModelProperty(value = "切分数")
	@TableField("BUSI_SEGM_CNT")
	private BigDecimal busiSegmCnt;
	/**
	 * 明细额度产品
	 */
	@ApiModelProperty(value = "明细额度产品")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 明细额度
	 */
	@ApiModelProperty(value = "明细额度")
	@TableField("CRD_DETAIL_AMT")
	private BigDecimal crdDetailAmt;
}
