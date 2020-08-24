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
import org.git.modules.clm.rcm.entity.RcmIndustryQuotaDetailRpt;

/**
 * 行业授信集中度明细表视图实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmIndustryQuotaDetailRptVO对象", description = "行业授信集中度明细表")
public class RcmIndustryQuotaDetailRptVO extends RcmIndustryQuotaDetailRpt {
	private static final long serialVersionUID = 1L;

	/**
	 * 限额指标名称
	 */
	@ApiModelProperty(value = "限额指标名称")
	private String quotaIndexName;

	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String unitFlag;


}
