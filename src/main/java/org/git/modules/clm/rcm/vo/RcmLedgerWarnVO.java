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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 台帐预警统计趋势
 *
 * @author caohaijie
 * @since 2020-1-7
 */
@Data
@ApiModel(value = "台帐预警统计趋势", description = "台帐预警统计趋势")
public class RcmLedgerWarnVO {


	@ApiModelProperty(value = "年份")
	private String recoYear;

	@ApiModelProperty(value = "月份")
	private String recoMonth;

	@ApiModelProperty(value = "限额编号")
	private String quotaNum;

	@ApiModelProperty(value = "观察次数")
	private String hisFrequencyA;

	@ApiModelProperty(value = "预警次数")
	private String hisFrequencyB;

	@ApiModelProperty(value = "控制次数")
	private String hisFrequencyC;


}
