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
 * 担保合同信息表实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@TableName("TB_CRD_SUBCONTRACT")
@ApiModel(value = "TbCrdSubcontract对象", description = "担保合同信息表")
public class TbCrdSubcontract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 担保合同编号
	 */
	@ApiModelProperty(value = "担保合同编号")
	@TableId("SUBCONTRACT_NUM")
	private String subcontractNum;
	/**
	 * 借款人客户编号
	 */
	@ApiModelProperty(value = "借款人客户编号")
	@TableField("CON_CUSTOMER_NUM")
	private String conCustomerNum;
	/**
	 * 担保合同类型（CD000102）
	 */
	@ApiModelProperty(value = "担保合同类型（CD000102）")
	@TableField("SUBCONTRACT_TYPE")
	private String subcontractType;
	/**
	 * 是否最高额（CD000167）
	 */
	@ApiModelProperty(value = "是否最高额（CD000167）")
	@TableField("IS_TOP")
	private String isTop;
	/**
	 * 是否联保小组担保（CD000167）
	 */
	@ApiModelProperty(value = "是否联保小组担保（CD000167）")
	@TableField("IS_GROUP")
	private String isGroup;
	/**
	 * 担保合同金额
	 */
	@ApiModelProperty(value = "担保合同金额")
	@TableField("SUBCONTRACT_AMT")
	private BigDecimal subcontractAmt;
	/**
	 * 担保人客户编号
	 */
	@ApiModelProperty(value = "担保人客户编号")
	@TableField("SURETY_CUSTOMER_NUM")
	private String suretyCustomerNum;
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
	 * 起始日
	 */
	@ApiModelProperty(value = "起始日")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 到期日
	 */
	@ApiModelProperty(value = "到期日")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 担保合同状态（CD000103）
	 */
	@ApiModelProperty(value = "担保合同状态（CD000103）")
	@TableField("SUBCONTRACT_STAUS")
	private String subcontractStaus;
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
