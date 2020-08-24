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

/**
 * 风险暴露-业务信息
 *
 * @author zhouweijie
 */
@Data
@ApiModel(value = "RcmRiskBusinessInfoVO对象", description = "风险暴露-业务信息")
public class RcmRiskBusinessInfoVO {

	/**
	 * 风险暴露类型（CD000177）
	 */
	@ApiModelProperty(value = "风险暴露类型（CD000177）")
	private String riskExposureType;

	/**
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	private String businessNum;

	/**
	 * 业务品种编号
	 */
	@ApiModelProperty(value = "业务品种编号")
	private String productNum;

	/**
	 * 业务品种名称
	 */
	@ApiModelProperty(value = "业务品种名称")
	private String productName;


	/**
	 * 单笔用信敞口余额
	 */
	@ApiModelProperty(value = "单笔用信敞口余额")
	private String balance;

	/**
	 * 担保方式(CD000100)
	 */
	@ApiModelProperty(value = "担保方式(CD000100)")
	private String guaranteeType;

	/**
	 * 用信起期
	 */
	@ApiModelProperty(value = "用信起期")
	private String beginDate;

	/**
	 * 用信止期
	 */
	@ApiModelProperty(value = "用信止期")
	private String endDate;

	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	private String orgNum;

	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	private String userNum;

}
