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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金系统额度转让-转出实体类
 *
 * @author git
 * @since 2020-01-15
 */
@Data
@TableName("TB_FUND_ASSIGNED_OUT")
@ApiModel(value = "FundAssignedOut对象", description = "资金系统额度转让-转出")
public class FundAssignedOut implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资金系统额度转让-转出id
	 */
	@ApiModelProperty(value = "资金系统额度转让-转出id")
	@TableId("TRANSFER_OUT_ID")
	private String transferOutId;
	/**
	 * 事件主表id
	 */
	@ApiModelProperty(value = "事件主表id")
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
	 * 转出机构
	 */
	@ApiModelProperty(value = "转出机构")
	@TableField("CRD_OUT_ORG_NUM")
	private String crdOutOrgNum;
	/**
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	@TableField("BUSI_SOURCE_REQ_NUM")
	private String busiSourceReqNum;


}
