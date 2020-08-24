/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmConfig;

import java.math.BigDecimal;

/**
 * 机构限额内容
 *
 * @author caohaijie
 * @since 2020-1-9
 */
@Data
@ApiModel(value = "RcmLedgerOrgQuotaVO对象", description = "机构限额内容")
public class RcmLedgerOrgQuotaVO {

	/**
	 * 限额编号
	 */
	@ApiModelProperty(value = "限额编号")
	private String quotaNum;

	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称")
	private String quotaName;

	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率")
	private BigDecimal quotaUsedRatio;

	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率")
	private BigDecimal quotaFreeRatio;

	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额")
	private BigDecimal quotaUsedAmt;

	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额")
	private BigDecimal quotaFreeAmt;

	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额")
	private BigDecimal quotaTotalSum;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号")
	private String quotaIndexNum;

	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	private String useOrgNum;
}
