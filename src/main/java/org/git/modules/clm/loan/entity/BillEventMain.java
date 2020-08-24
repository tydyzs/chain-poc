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
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 票据用信事件主表实体类
 *
 * @author git
 * @since 2019-11-11
 */
@Data
@TableName("TB_BILL_EVENT_MAIN")
@ApiModel(value = "BillEventMain对象", description = "票据用信事件主表")
public class BillEventMain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 事件主表id
	 */
	@ApiModelProperty(value = "事件主表id")
	@TableId(value = "EVENT_MAIN_ID", type = IdType.UUID)
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
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	@TableField("BUSI_DEAL_NUM")
	private String busiDealNum;
	/**
	 * 交易类型（03 占用，04 占用撤销，05 恢复（到期恢复时用票号），06 恢复撤销）
	 */
	@ApiModelProperty(value = "交易类型（03 占用，04 占用撤销，05 恢复（到期恢复时用票号），06 恢复撤销）")
	@TableField("TRAN_TYPE_CD")
	private String tranTypeCd;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("cert_currency_cd")
	private String certCurrencyCd;
	/**
	 * 用信金额
	 */
	@ApiModelProperty(value = "用信金额")
	@TableField("CRD_APPLY_AMT")
	private BigDecimal crdApplyAmt;
	/**
	 * 本方交易状态（0 未处理  1 处理成功 2 处理失败 ）
	 */
	@ApiModelProperty(value = "本方交易状态（0 未处理  1 处理成功 2 处理失败 ）")
	@TableField("TRAN_EVENT_STATUS")
	private String tranEventStatus;
	/**
	 * 事件处理信息
	 */
	@ApiModelProperty(value = "事件处理信息")
	@TableField("TRAN_EVENT_INFO")
	private String tranEventInfo;
	/**
	 * 对方对账状态（0 未处理  1 处理成功 2 处理失败）
	 */
	@ApiModelProperty(value = "对方对账状态（0 未处理  1 处理成功 2 处理失败）")
	@TableField("TRAN_ACCT_STATUS")
	private String tranAcctStatus;

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
	@TableField("user_num")
	private String userNum;

}
