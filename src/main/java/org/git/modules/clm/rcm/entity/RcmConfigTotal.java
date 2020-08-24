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
package org.git.modules.clm.rcm.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 限额详细统计表实体类
 *
 * @author git
 * @since 2019-12-23
 */
@Data
@TableName("TB_RCM_CONFIG_TOTAL")
@ApiModel(value = "RcmConfigTotal对象", description = "限额详细统计表")
public class RcmConfigTotal implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 集中度限额编号
	 */
	@ApiModelProperty(value = "集中度限额编号")
	@TableField("QUOTA_NUM")
	private String quotaNum;
	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额")
	@TableField("QUOTA_USED_AMT")
	private BigDecimal quotaUsedAmt;
	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额")
	@TableField("QUOTA_FREE_AMT")
	private BigDecimal quotaFreeAmt;
	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率")
	@TableField("QUOTA_USED_RATIO")
	private BigDecimal quotaUsedRatio;
	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率")
	@TableField("QUOTA_FREE_RATIO")
	private BigDecimal quotaFreeRatio;
	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额")
	@TableField("QUOTA_TOTAL_SUM")
	private BigDecimal quotaTotalSum;
	/**
	 * 比年初增长量
	 */
	@ApiModelProperty(value = "比年初增长量")
	@TableField("LAST_YEAR_AMT")
	private BigDecimal lastYearAmt;
	/**
	 * 比年初增长率
	 */
	@ApiModelProperty(value = "比年初增长率")
	@TableField("LAST_YEAR_RATIO")
	private BigDecimal lastYearRatio;
	/**
	 * 比上期增长量（环比）
	 */
	@ApiModelProperty(value = "比上期增长量（环比）")
	@TableField("MONTH_TO_MONTH_AMT")
	private BigDecimal monthToMonthAmt;
	/**
	 * 比上期增长率（环比）
	 */
	@ApiModelProperty(value = "比上期增长率（环比）")
	@TableField("MONTH_TO_MONTH_RATIO")
	private BigDecimal monthToMonthRatio;
	/**
	 * 观察值历史触发次数
	 */
	@ApiModelProperty(value = "观察值历史触发次数")
	@TableField("HIS_FREQUENCY_A")
	private BigDecimal hisFrequencyA;
	/**
	 * 预警值历史触发次数
	 */
	@ApiModelProperty(value = "预警值历史触发次数")
	@TableField("HIS_FREQUENCY_B")
	private BigDecimal hisFrequencyB;
	/**
	 * 控制值历史触发次数
	 */
	@ApiModelProperty(value = "控制值历史触发次数")
	@TableField("HIS_FREQUENCY_C")
	private BigDecimal hisFrequencyC;
	/**
	 * 历史累计触发次数
	 */
	@ApiModelProperty(value = "历史累计触发次数")
	@TableField("HIS_FREQUENCY")
	private BigDecimal hisFrequency;
	/**
	 * 观察值本年触发次数
	 */
	@ApiModelProperty(value = "观察值本年触发次数")
	@TableField("HIS_FREQUENCY_A2")
	private BigDecimal hisFrequencyA2;
	/**
	 * 预警值本年触发次数
	 */
	@ApiModelProperty(value = "预警值本年触发次数")
	@TableField("HIS_FREQUENCY_B2")
	private BigDecimal hisFrequencyB2;
	/**
	 * 控制值本年触发次数
	 */
	@ApiModelProperty(value = "控制值本年触发次数")
	@TableField("HIS_FREQUENCY_C2")
	private BigDecimal hisFrequencyC2;
	/**
	 * 本年累计触发次数
	 */
	@ApiModelProperty(value = "本年累计触发次数")
	@TableField("HIS_FREQUENCY_YEAR")
	private BigDecimal hisFrequencyYear;
	/**
	 * 观察值本月触发次数
	 */
	@ApiModelProperty(value = "观察值本月触发次数")
	@TableField("HIS_FREQUENCY_A3")
	private BigDecimal hisFrequencyA3;
	/**
	 * 限额总额类型
	 */
	@ApiModelProperty(value = "限额总额类型")
	@TableField("QUOTA_TOTAL_TYPE")
	private String quotaTotalType;
	/**
	 * 比同期增长量（同比）
	 */
	@ApiModelProperty(value = "比同期增长量（同比）")
	@TableField("YEAR_TO_YEAR_AMT")
	private BigDecimal yearToYearAmt;
	/**
	 * 比同期增长率（同比）
	 */
	@ApiModelProperty(value = "比同期增长率（同比）")
	@TableField("YEAR_TO_YEAR_RATIO")
	private BigDecimal yearToYearRatio;
	/**
	 * 当月净资产
	 */
	@ApiModelProperty(value = "当月净资产")
	@TableField("NET_ASSETS")
	private BigDecimal netAssets;
	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	@TableField("TOTAL_MONTH")
	private String totalMonth;
	/**
	 * 年份
	 */
	@ApiModelProperty(value = "年份")
	@TableField("TOTAL_YEAR")
	private String totalYear;
	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;
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
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;
	/**
	 * 预警值本月触发次数
	 */
	@ApiModelProperty(value = "预警值本月触发次数")
	@TableField("HIS_FREQUENCY_B3")
	private BigDecimal hisFrequencyB3;
	/**
	 * 控制值本月触发次数
	 */
	@ApiModelProperty(value = "控制值本月触发次数")
	@TableField("HIS_FREQUENCY_C3")
	private BigDecimal hisFrequencyC3;
	/**
	 * 当月累计触发次数
	 */
	@ApiModelProperty(value = "当月累计触发次数")
	@TableField("HIS_FREQUENCY_MONTH")
	private BigDecimal hisFrequencyMonth;
	/**
	 * 当月资本净额
	 */
	@ApiModelProperty(value = "当月资本净额")
	@TableField("NET_CAPITAL")
	private BigDecimal netCapital;
	/**
	 * 当月一级资本净额
	 */
	@ApiModelProperty(value = "当月一级资本净额")
	@TableField("NET_PRIMARY_CAPITAL")
	private BigDecimal netPrimaryCapital;

	//新增控制参数字段 2020-2-27 by caohaijie

	@ApiModelProperty(value = "限额名称")
	@TableField("QUOTA_NAME")
	private String quotaName;

	@ApiModelProperty(value = "限额指标名称")
	@TableField("QUOTA_INDEX_NAME")
	private String quotaIndexName;

	@ApiModelProperty(value = "限额指标编号")
	@TableField("QUOTA_INDEX_NUM")
	private String quotaIndexNum;

	@ApiModelProperty("观察值")
	@TableField("observe_value")
	private BigDecimal observeValue;

	@ApiModelProperty("预警值")
	@TableField("warn_value")
	private BigDecimal warnValue;

	@ApiModelProperty("控制值")
	@TableField("control_value")
	private BigDecimal controlValue;

	@ApiModelProperty("观察值类型(1.金额 2.百分比)")
	@TableField("observe_value_type")
	private String observeValueType;

	@ApiModelProperty("预警值类型(1.金额 2.百分比)")
	@TableField("warn_value_type")
	private String warnValueType;

	@ApiModelProperty("控制值类型(1.金额 2.百分比)")
	@TableField("control_value_type")
	private String controlValueType;

	@ApiModelProperty("观察值应对措施")
	@TableField("observe_node_measure")
	private BigDecimal observeNodeMeasure;

	@ApiModelProperty("预警值应对措施")
	@TableField("warn_node_measure")
	private BigDecimal warnNodeMeasure;

	@ApiModelProperty("控制值应对措施")
	@TableField("control_node_measure")
	private BigDecimal controlNodeMeasure;


}
