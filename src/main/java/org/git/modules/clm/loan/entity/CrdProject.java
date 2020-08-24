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

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目协议表实体类
 *
 * @author git
 * @since 2019-11-21
 */
@Data
@TableName("TB_CRD_PROJECT")
@ApiModel(value = "CrdProject对象", description = "项目协议表")
public class CrdProject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value = "项目ID")
	@TableId("PROJECT_NUM")
	private String projectNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 三级额度编号
	 */
	@ApiModelProperty(value = "三级额度编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 三级额度产品
	 */
	@ApiModelProperty(value = "三级额度产品")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 项目协议名称
	 */
	@ApiModelProperty(value = "项目协议名称")
	@TableField("PROJECT_NAME")
	private String projectName;
	/**
	 * 项目类型（CD000172）
	 */
	@ApiModelProperty(value = "项目类型（CD000172）")
	@TableField("PROJECT_TYPE")
	private String projectType;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 总金额
	 */
	@ApiModelProperty(value = "总金额")
	@TableField("TOTAL_AMT")
	private BigDecimal totalAmt;
	/**
	 * 已用金额
	 */
	@ApiModelProperty(value = "已用金额")
	@TableField("USED_AMT")
	private BigDecimal usedAmt;
	/**
	 * 可用金额
	 */
	@ApiModelProperty(value = "可用金额")
	@TableField("AVI_AMT")
	private BigDecimal aviAmt;
	/**
	 * 额度控制方式（CD000173）
	 */
	@ApiModelProperty(value = "额度控制方式（CD000173）")
	@TableField("LIMIT_CONTROL_TYPE")
	private String limitControlType;
	/**
	 * 申请日期
	 */
	@ApiModelProperty(value = "申请日期")
	@TableField("TRAN_DATE")
	private String tranDate;
	/**
	 * 协议期限
	 */
	@ApiModelProperty(value = "协议期限")
	@TableField("AGREEMENT_TERM")
	private BigDecimal agreementTerm;
	/**
	 * 协议期限单位（CD000169）
	 */
	@ApiModelProperty(value = "协议期限单位（CD000169）")
	@TableField("AGREEMENT_TERM_UNIT")
	private String agreementTermUnit;
	/**
	 * 项目状态
	 */
	@ApiModelProperty(value = "项目状态")
	@TableField("PROJECT_STATUS")
	private String projectStatus;
	/**
	 * 客户经理
	 */
	@ApiModelProperty(value = "客户经理")
	@TableField("USER_NUM")
	private String userNum;
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
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 二级额度编号
	 */
	@ApiModelProperty(value = "二级额度编号")
	@TableField("CRD_MAIN_NUM")
	private String crdMainNum;
	/**
	 * 二级额度品种
	 */
	@ApiModelProperty(value = "二级额度品种")
	@TableField("CRD_MAIN_PRD")
	private String crdMainPrd;


}
