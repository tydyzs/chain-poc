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

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 台帐限额统计趋势
 *
 * @author caohaijie
 * @since 2020-1-7
 */
@Data
@ApiModel(value = "台帐限额统计趋势", description = "台帐限额统计趋势")
public class RcmLedgerQuotaVO {


	@ApiModelProperty(value = "年份")
	private String totalYear;

	@ApiModelProperty(value = "月份")
	private String totalMonth;

	@ApiModelProperty(value = "集中度限额编号")
	private String quotaNum;

	@ApiModelProperty(value = "限额已用")
	private String quotaUsedAmt;

	@ApiModelProperty(value = "比上月增加值")
	private String monthToMonthAmt;

	@ApiModelProperty(value = "比上月增加率")
	private String monthToMonthRatio;

	@ApiModelProperty(value = "观察值本月触发次数")
	private String hisFrequencyA3;

	@ApiModelProperty(value = "预警值本月触发次数")
	private String hisFrequencyB3;

	@ApiModelProperty(value = "控制值本月触发次数")
	private String hisFrequencyC3;

	@ApiModelProperty(value = "本月累计触发次数")
	private String hisFrequencyMonth;


}
