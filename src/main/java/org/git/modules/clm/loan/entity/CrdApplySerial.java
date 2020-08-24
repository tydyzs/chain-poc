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

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 额度使用流水实体类
 *
 * @author liuye
 * @since 2019-11-27
 */
@Data
@TableName("TB_CRD_APPLY_SERIAL")
@ApiModel(value = "CrdApplySerial对象", description = "额度使用流水")
public class CrdApplySerial implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "SERIAL_ID", type = IdType.UUID)
	private String serialId;
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
	 * 业务编号（存放不同的业务场景下的业务编号）
	 */
	@ApiModelProperty(value = "业务编号（存放不同的业务场景下的业务编号）")
	@TableField("BUSI_DEAL_NUM")
	private String busiDealNum;
	/**
	 * 交易类型
	 */
	@ApiModelProperty(value = "交易类型")
	@TableField("TRAN_TYPE_CD")
	private String tranTypeCd;
	/**
	 * 额度明细编号
	 */
	@ApiModelProperty(value = "额度明细编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 授信机构
	 */
	@ApiModelProperty(value = "授信机构")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;
	/**
	 * 用信客户号
	 */
	@ApiModelProperty(value = "用信客户号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "额度产品编号")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 占用/恢复额度金额
	 */
	@ApiModelProperty(value = "占用/恢复额度金额")
	@TableField("LIMIT_CREDIT_AMT")
	private BigDecimal limitCreditAmt;
	/**
	 * 占用/恢复敞口金额
	 */
	@ApiModelProperty(value = "占用/恢复敞口金额")
	@TableField("EXP_CREDIT_AMT")
	private BigDecimal expCreditAmt;

	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 是否串用
	 */
	@ApiModelProperty(value = "是否串用")
	@TableField("IS_MIX")
	private String isMix;

	/**
	 * 串用金额
	 */
	@ApiModelProperty(value = "串用金额")
	@TableField("MIX_CREDIT")
	private String mixCredit;
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
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
