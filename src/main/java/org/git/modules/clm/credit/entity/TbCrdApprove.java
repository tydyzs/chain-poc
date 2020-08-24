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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批复信息表实体类
 *
 * @author git
 * @since 2019-11-13
 */
@Data
@TableName("TB_CRD_APPROVE")
@ApiModel(value = "TbCrdApprove对象", description = "批复信息表")
public class TbCrdApprove implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 批复ID
	 */
	@ApiModelProperty(value = "批复ID")
	@TableId("APPROVE_ID")
	private String approveId;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 批复编号
	 */
	@ApiModelProperty(value = "批复编号")
	@TableField("APPROVE_NUM")
	private String approveNum;

	/**
	 * 额度二级编号
	 */
	@ApiModelProperty(value = "额度二级编号")
	@TableField("CRD_MAIN_NUM")
	private String crdMainNum;

	/**
	 * 额度二级品种
	 */
	@ApiModelProperty(value = "额度二级编号")
	@TableField("CRD_MAIN_PRD")
	private String crdMainPrd;
	/**
	 * 三级额度编号
	 */
	@ApiModelProperty(value = "三级额度编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;

	/**
	 * 是否可循环（CD000167）
	 */
	@ApiModelProperty(value = "是否可循环（CD000167）")
	@TableField("IS_CYCLE")
	private String isCycle;

	/**
	 * 是否联保（CD000167）
	 */
	@ApiModelProperty(value = "是否联保（CD000167）")
	@TableField("IS_JOINT_GUARANTEE")
	private String isJointGuarantee;

	/**
	 * 业务类型（CD000170）
	 */
	@ApiModelProperty(value = "业务类型（CD000170）")
	@TableField("BIZ_TYPE")
	private String bizType;
	/**
	 * 是否低风险（CD000167）
	 */
	@ApiModelProperty(value = "是否低风险（CD000167）")
	@TableField("IS_LOW_RISK")
	private String isLowRisk;
	/**
	 * 低风险业务类别
	 */
	@ApiModelProperty(value = "低风险业务类别")
	@TableField("LOW_RISK_TYPE")
	private String lowRiskType;
	/**
	 * 业务发生方式(CD000168)
	 */
	@ApiModelProperty(value = "业务发生方式(CD000168)")
	@TableField("BIZ_HAPPEN_TYPE")
	private String bizHappenType;
	/**
	 * 业务品种(:tb_sys_product)
	 */
	@ApiModelProperty(value = "业务品种(:tb_sys_product)")
	@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	 * 业务种类（CD000061）
	 */
	@ApiModelProperty(value = "业务种类（CD000061）")
	@TableField("PRODUCT_TYPE")
	private String productType;

	/**
	 * 行业投向（CD000015）
	 */
	@ApiModelProperty(value = "行业投向（CD000015）")
	@TableField("INDUSTRY")
	private String industry;
	/**
	 * 担保方式（CD000100）
	 */
	@ApiModelProperty(value = "担保方式（CD000100）")
	@TableField("GUARANTEE_TYPE")

	private String guaranteeType;
	/**
	 * 主担保方式（CD000100）
	 */
	@ApiModelProperty(value = "主担保方式（CD000100）")
	@TableField("MAIN_GUARANTEE_TYPE")
	private String mainGuaranteeType;

	/**
	 * 担保方式分类（CD000101）
	 */
	@ApiModelProperty(value = "担保方式分类（CD000101）")
	private String guaranteeTypeDetail;

	/**
	 * 期限
	 */
	@ApiModelProperty(value = "期限")
	@TableField("TERM")
	private BigDecimal term;
	/**
	 * 期限单位（CD000169）
	 */
	@ApiModelProperty(value = "期限单位（CD000169）")
	@TableField("TERM_UNIT")
	private String termUnit;

	/**
	 * 期限类型（CD000210）
	 */
	@ApiModelProperty(value = "期限类型（CD000210）")
	private String termType;
	/**
	 * 批复状态（CD000109）
	 */
	@ApiModelProperty(value = "批复状态（CD000109）")
	@TableField("APPROVE_STATUS")
	private String approveStatus;
	/**
	 * 批复金额
	 */
	@ApiModelProperty(value = "批复金额")
	@TableField("APPROVE_AMT")
	private BigDecimal approveAmt;
	/**
	 * 批复已用
	 */
	@ApiModelProperty(value = "批复已用")
	@TableField("APPROVE_USED")
	private BigDecimal approveUsed;
	/**
	 * 批复可用
	 */
	@ApiModelProperty(value = "批复可用")
	@TableField("APPROVE_AVI")
	private BigDecimal approveAvi;
	/**
	 * 批复敞口金额
	 */
	@ApiModelProperty(value = "批复敞口金额")
	@TableField("APPROVE_EXP_AMT")
	private BigDecimal approveExpAmt;
	/**
	 * 批复敞口已用
	 */
	@ApiModelProperty(value = "批复敞口已用")
	@TableField("APPROVE_EXP_USED")
	private BigDecimal approveExpUsed;
	/**
	 * 批复敞口可用
	 */
	@ApiModelProperty(value = "批复敞口可用")
	@TableField("APPROVE_EXP_AVI")
	private BigDecimal approveExpAvi;
	/**
	 * 申请日期
	 */
	@ApiModelProperty(value = "申请日期")
	@TableField("TRAN_DATE")
	private String tranDate;

	/**
	 * 批复起期
	 */
	@ApiModelProperty(value = "批复起期")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 批复止期
	 */
	@ApiModelProperty(value = "批复止期")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 借新还旧借据编号
	 */
	@ApiModelProperty(value = "借新还旧借据编号")
	@TableField("OLD_SUMMARY_NUM")
	private String oldSummaryNum;
	/**
	 * 数据来源
	 */
	@ApiModelProperty(value = "数据来源")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 项目协议号
	 */
	@ApiModelProperty(value = "项目协议号")
	@TableField("PROJECT_NUM")
	private String projectNum;
	/**
	 * 经办机构编号
	 */
	@ApiModelProperty(value = "经办机构编号")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 经办人编号
	 */
	@ApiModelProperty(value = "经办人编号")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;
	/**
	 * 三级额度产品
	 */
	@ApiModelProperty(value = "三级额度产品")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 预占用额度
	 */
	@ApiModelProperty(value = "预占用额度")
	@TableField("APPROVE_PRE_AMT")
	private BigDecimal approvePreAmt;
	/**
	 * 预占用敞口
	 */
	@ApiModelProperty(value = "预占用敞口")
	@TableField("APPROVE_PRE_EXP")
	private BigDecimal approvePreExp;
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
	 * 批复总金额
	 */
	@ApiModelProperty(value = "批复总金额")
	@TableField("TOTAL_AMT")
	private BigDecimal totalAmt;

	/**
	 * 保证金比例
	 */
	@ApiModelProperty(value = "保证金比例")
	@TableField("DEPOSIT_RATIO")
	private BigDecimal depositRatio;
}
