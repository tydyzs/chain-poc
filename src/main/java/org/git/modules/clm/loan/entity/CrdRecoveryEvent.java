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
package org.git.modules.clm.loan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 信贷实时-还款交易信息实体类
 *
 * @author git
 * @since 2019-11-26
 */
@Data
@TableName("TB_CRD_RECOVERY_EVENT")
@ApiModel(value = "CrdRecoveryEvent对象", description = "信贷实时-还款交易信息")
public class CrdRecoveryEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易流水号
	 */
	@ApiModelProperty(value = "交易流水号")
	@TableId("TRAN_SEQ_SN")
	private String tranSeqSn;

	/**
	 * 操作类型（01 新增02 调整 03删除）
	 */
	@ApiModelProperty(value = "操作类型（01 新增02 调整 03删除）")
	@TableField("OP_TYPE")
	private String opType;



	/**
	 * 借据编号
	 */
	@ApiModelProperty(value = "借据编号")
	@TableField("SUMMARY_NUM")
	private String summaryNum;

	@XStreamAlias("biz_scene")
	@TableField("BIZ_SCENE")
	private String bizScene;//业务场景

	@TableField("BIZ_ACTION")
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点
	/**
	 * 数据来源
	 */
	@ApiModelProperty(value = "数据来源")
	@TableField("DATE_SOURCE")
	private String dateSource;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 还款本金
	 */
	@ApiModelProperty(value = "还款本金")
	@TableField("REPAY_AMT")
	private BigDecimal repayAmt;
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
	 * 还款日期
	 */
	@ApiModelProperty(value = "还款日期")
	@TableField("REPAY_DATE")
	private String repayDate;
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
