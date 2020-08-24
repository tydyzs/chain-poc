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

import java.util.List;

/**
 * 客户授信集中度简表视图查询条件实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@ApiModel(value = "RcmQuotaAnalysisRptQueryVO对象", description = "客户授信集中度简表查询条件")
public class RcmQuotaAnalysisRptQueryVO {

	@ApiModelProperty(value = "限额指标编号")
	private List<String> quotaIndexNums;

	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	private String totalMonth;
	/**
	 * 年份
	 */
	@ApiModelProperty(value = "年份")
	private String totalYear;
	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	private String orgNum;
	/**
	 * 金额单位
	 */
	@ApiModelProperty(value = "金额单位", required = true)
	private String amtUnit;
	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "生效机构类型", hidden = true)
	private String orgType;
	/**
	 * 登录用户机构
	 */
	@ApiModelProperty(value = "登录用户机构", hidden = true)
	private String userOrgNum;
	/**
	 * 登录用户机构类型
	 */
	@ApiModelProperty(value = "登录用户机构类型", hidden = true)
	private String userOrgType;

	/**
	 *  上月的年份
	 */
	@ApiModelProperty(value = "上月的年份", hidden = true)
	private String lastMonthYear;

	/**
	 * 上月的月份
	 */
	@ApiModelProperty(value = "上月的月份", hidden = true)
	private String lastMonth;

}
