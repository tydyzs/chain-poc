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
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmCreditTenCusRpt;

import java.math.BigDecimal;

/**
 * 最大十家客户授信集中度明细表视图实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmCreditTenCusRptVO对象", description = "最大十家客户授信集中度明细表")
public class RcmCreditTenCusRptVO extends RcmCreditTenCusRpt {
	private static final long serialVersionUID = 1L;

	/**
	 * 较上月变化
	 */
	@ApiModelProperty(value = "较上月变化")
	private BigDecimal change;
//	/**
//	 * 限额指标编号
//	 */
//	@ApiModelProperty(value = "限额指标编号")
//	private String quotaIndexNum;
//	/**
//	 * 限额指标名称
//	 */
//	@ApiModelProperty(value = "限额指标名称")
//	private String quotaIndexName;
//	/**
//	 * 单位
//	 */
//	@ApiModelProperty(value = "单位")
//	private String unitFlag;
//	/**
//	 * 控制值(金额)
//	 */
//	@ApiModelProperty(value = "控制值(金额)")
//	private BigDecimal controlAmt;
//	/**
//	 * 控制值(占比)
//	 */
//	@ApiModelProperty(value = "控制值(占比)")
//	private BigDecimal controlRatio;
//	/**
//	 * 预警值(金额)
//	 */
//	@ApiModelProperty(value = "预警值(金额)")
//	private BigDecimal warningAmt;
//	/**
//	 * 预警值(占比)
//	 */
//	@ApiModelProperty(value = "预警值(占比)")
//	private BigDecimal warningRatio;
//	/**
//	 * 观察值(金额)
//	 */
//	@ApiModelProperty(value = "观察值(金额)")
//	private BigDecimal observationAmt;
//	/**
//	 * 观察值(占比)
//	 */
//	@ApiModelProperty(value = "观察值(占比)")
//	private BigDecimal observationRatio;

}
