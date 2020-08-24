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
package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 限额分析统计结果
 *
 * @author caohaijie
 * @since 2020-1-7
 */
@Data
@ApiModel(value = "限额分析统计结果", description = "限额分析统计结果")
public class RcmLedgerTotalVO {

	@ApiModelProperty(value = "限额编号")
	private String quotaNum;

	@ApiModelProperty(value = "本月值")
	private BigDecimal quotaUsedAmt;

	@ApiModelProperty(value = "上月值")
	private BigDecimal lastQuotaUsedAmt;

	@ApiModelProperty(value = "较上月增量")
	private BigDecimal monthToMonthAmt;

	@ApiModelProperty(value = "较上月增速")
	private BigDecimal monthToMonthRatio;

	@ApiModelProperty(value = "较上年增量")
	private BigDecimal lastYearAmt;

	@ApiModelProperty(value = "较上年增速")
	private BigDecimal lastYearRatio;

	@ApiModelProperty(value = "较同期增量")
	private BigDecimal yearToYearAmt;

	@ApiModelProperty(value = "较同期增速")
	private BigDecimal yearToYearRatio;

	@ApiModelProperty(value = "观察值历史触发次数")
	private BigDecimal hisFrequencyA;

	@ApiModelProperty(value = "预警值历史触发次数")
	private BigDecimal hisFrequencyB;

	@ApiModelProperty(value = "控制值历史触发次数")
	private BigDecimal hisFrequencyC;

	@ApiModelProperty(value = "历史累计触发次数")
	private BigDecimal hisFrequency;

	@ApiModelProperty(value = "观察值本年触发次数")
	private BigDecimal hisFrequencyA2;

	@ApiModelProperty(value = "预警值本年触发次数")
	private BigDecimal hisFrequencyB2;

	@ApiModelProperty(value = "控制值本年触发次数")
	private BigDecimal hisFrequencyC2;

	@ApiModelProperty(value = "本年累计触发次数")
	private BigDecimal hisFrequencyYear;

	@ApiModelProperty(value = "观察值本月触发次数")
	private BigDecimal hisFrequencyA3;

	@ApiModelProperty(value = "预警值本月触发次数")
	private BigDecimal hisFrequencyB3;

	@ApiModelProperty(value = "控制值本月触发次数")
	private BigDecimal hisFrequencyC3;

	@ApiModelProperty(value = "本月累计触发次数")
	private BigDecimal hisFrequencyMonth;


}
