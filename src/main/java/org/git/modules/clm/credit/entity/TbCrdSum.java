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
 * 额度汇总表（实时）实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@TableName("TB_CRD_SUM")
@ApiModel(value = "TbCrdSum对象", description = "额度汇总表（实时）")
public class TbCrdSum implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 统计ID
	 */
	@ApiModelProperty(value = "统计ID")
	@TableId("STATIS_ID")
	private String statisId;


	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;


	/**
	 * 额度类型（CD000211）
	 */
	@ApiModelProperty(value = "额度类型（CD000211）")
	@TableField("CRD_PRODUCT_TYPE")
	private String crdProductType;


	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;


	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private BigDecimal limitCredit;


	/**
	 * 已用额度
	 */
	@ApiModelProperty(value = "已用额度")
	@TableField("LIMIT_USED")
	private BigDecimal limitUsed;


	/**
	 * 可用额度
	 */
	@ApiModelProperty(value = "可用额度")
	@TableField("LIMIT_AVI")
	private BigDecimal limitAvi;


	/**
	 * 授信敞口
	 */
	@ApiModelProperty(value = "授信敞口")
	@TableField("EXP_CREDIT")
	private BigDecimal expCredit;


	/**
	 * 已用敞口
	 */
	@ApiModelProperty(value = "已用敞口")
	@TableField("EXP_USED")
	private BigDecimal expUsed;


	/**
	 * 可用敞口
	 */
	@ApiModelProperty(value = "可用敞口")
	@TableField("EXP_AVI")
	private BigDecimal expAvi;


	/**
	 * 已用额度
	 */
	@ApiModelProperty(value = "已用额度")
	@TableField("LIMIT_PRE")
	private BigDecimal limitPre;


	/**
	 * 预占用敞口
	 */
	@ApiModelProperty(value = "预占用敞口")
	@TableField("EXP_PRE")
	private BigDecimal expPre;

	/**
	 * 冻结额度
	 */
	@ApiModelProperty(value = "冻结额度")
	@TableField("LIMIT_FROZEN")
	private BigDecimal limitFrozen;

	/**
	 * 冻结敞口
	 */
	@ApiModelProperty(value = "冻结敞口")
	@TableField("EXP_FROZEN")
	private BigDecimal expFrozen;
	/**
	 * 圈存额度
	 */
	@ApiModelProperty(value = "圈存额度")
	@TableField("LIMIT_EARMARK")
	private BigDecimal limitEarmark;

	/**
	 * 圈存已用额度
	 */
	@ApiModelProperty(value = "圈存已用额度")
	@TableField("LIMIT_EARMARK_USED")
	private BigDecimal limitEarmarkUsed;


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
