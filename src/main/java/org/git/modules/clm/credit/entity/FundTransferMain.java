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
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金用信转让事件主表实体类
 *
 * @author liuye
 * @since 2019-12-05
 */
@Data
@TableName("TB_FUND_TRANSFER_MAIN")
@ApiModel(value = "FundTransferMain对象", description = "资金用信转让事件主表")
public class FundTransferMain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 事件主表id
	 */
	@ApiModelProperty(value = "事件主表id")
	@TableId("EVENT_MAIN_ID")
	private String eventMainId;

	/**
	 * 应能唯一确认交易
	 */
	@ApiModelProperty(value = "应能唯一确认交易")
	@TableField("TRAN_SEQ_SN")
	private String tranSeqSn;

	/**
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;

	/**
	 * 应能唯一确认业务
	 */
	@ApiModelProperty(value = "应能唯一确认业务")
	@TableField("BUSI_DEAL_NUM")
	private String busiDealNum;

	/**
	 * 交易类型
	 * <p>
	 * 03 占用
	 * 04 占用撤销
	 * 05 恢复（到期恢复时用票号）
	 * 06 恢复撤销
	 */
	@ApiModelProperty(value = "交易类型")
	@TableField("TRAN_TYPE_CD")
	private String tranTypeCd;

	/**
	 * 交易金额
	 */
	@ApiModelProperty(value = "交易金额")
	@TableField("CRD_APPLY_AMT")
	private BigDecimal crdApplyAmt;

	/**
	 * 本方交易状态 0 未处理  1 处理成功 2 处理失败
	 */
	@ApiModelProperty(value = "本方交易状态")
	@TableField("TRAN_EVENT_STATUS")
	private String tranEventStatus;

	/**
	 * 事件明细
	 */
	@ApiModelProperty(value = "事件明细")
	@TableField("TRAN_EVENT_INFO")
	private String tranEventInfo;

	/**
	 * 对方交易状态 0 未处理  1 处理成功 2 处理失败
	 */
	@ApiModelProperty(value = "对方交易状态 ")
	@TableField("TRAN_ACCT_STATUS")
	private String tranAcctStatus;

	/**
	 * 币种(CD000019)
	 */
	@ApiModelProperty(value = "币种(CD000019)")
	@TableField("CRD_CURRENCY_CD")
	private String crdCurrencyCd;


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
	 * 交易方向
	 */
	@ApiModelProperty(value = "交易方向")
	@TableField("tran_direction")
	private String tranDirection;
}
