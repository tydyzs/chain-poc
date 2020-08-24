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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 首页限额内容
 *
 * @author caohaijie
 * @since 2020-1-9
 */
@Data
@ApiModel(value = "RcmLedgerQuotaHomeVO对象", description = "首页限额内容")
public class RcmLedgerQuotaHomeVO {

	//--------------CB20191226604691 单一客户授信集中度
	/**
	 * 限额编号
	 */
	@ApiModelProperty(value = "限额编号（单一客户授信集中度）")
	private String quotaNumA;

	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称（单一客户授信集中度）")
	private String quotaNameA;

	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率（单一客户授信集中度）")
	private BigDecimal quotaUsedRatioA;

	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率（单一客户授信集中度）")
	private BigDecimal quotaFreeRatioA;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号（单一客户授信集中度）")
	private String quotaIndexNumA;

	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额（单一客户授信集中度）")
	private BigDecimal quotaUsedAmtA;

	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额（单一客户授信集中度）")
	private BigDecimal quotaFreeAmtA;

	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额（单一客户授信集中度）")
	private BigDecimal quotaTotalSumA;

	//--------------------CB20191226053358	最大十家单一客户授信集中度
	/**
	 * 限额编号
	 */
	@ApiModelProperty(value = "限额编号（最大十家单一客户授信集中度）")
	private String quotaNumB;

	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称（最大十家单一客户授信集中度）")
	private String quotaNameB;

	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率（最大十家单一客户授信集中度）")
	private BigDecimal quotaUsedRatioB;

	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率（最大十家单一客户授信集中度）")
	private BigDecimal quotaFreeRatioB;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号（最大十家单一客户授信集中度）")
	private String quotaIndexNumB;

	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额（最大十家单一客户授信集中度）")
	private BigDecimal quotaUsedAmtB;

	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额（最大十家单一客户授信集中度）")
	private BigDecimal quotaFreeAmtB;

	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额（最大十家单一客户授信集中度）")
	private BigDecimal quotaTotalSumB;

	//-----------------CB20191226559982	最大十家集团客户授信集中度

	/**
	 * 限额编号
	 */
	@ApiModelProperty(value = "限额编号（最大十家集团客户授信集中度）")
	private String quotaNumC;

	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称（最大十家集团客户授信集中度）")
	private String quotaNameC;

	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率（最大十家集团客户授信集中度）")
	private BigDecimal quotaUsedRatioC;

	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率（最大十家集团客户授信集中度）")
	private BigDecimal quotaFreeRatioC;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号（最大十家集团客户授信集中度）")
	private String quotaIndexNumC;

	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额（最大十家集团客户授信集中度）")
	private BigDecimal quotaUsedAmtC;

	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额（最大十家集团客户授信集中度）")
	private BigDecimal quotaFreeAmtC;

	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额（最大十家集团客户授信集中度）")
	private BigDecimal quotaTotalSumC;

	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	private String useOrgNum;
}
