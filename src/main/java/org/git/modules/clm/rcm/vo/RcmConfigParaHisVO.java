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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.git.modules.clm.rcm.entity.RcmConfigParaHis;

import java.math.BigDecimal;

/**
 * 限额参数配置历史信息表视图实体类
 *
 * @author git
 * @since 2019-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmControlHisVO对象", description = "限额参数配置历史信息表")
public class RcmConfigParaHisVO extends RcmConfigParaHis {
	private static final long serialVersionUID = 1L;

	/**
	 * 历史状态
	 */
	@ApiModelProperty(value = "历史状态")
	private String quotaState;

	/**
	 * 阈值（百分比）或阈值（金额）
	 */
	@ApiModelProperty(value = "阈值（余额）")
	private BigDecimal amtOrRatio;
}
