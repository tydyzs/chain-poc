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
package org.git.modules.clm.credit.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;

/**
 * 担保合同信息表视图实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TbCrdSubcontractVO对象", description = "担保合同信息表")
public class TbCrdSubcontractVO extends TbCrdSubcontract {
	private static final long serialVersionUID = 1L;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value = "合同编号")
	private String contractNum;

	/**
	 * 本次担保金额
	 */
	@ApiModelProperty(value = "本次担保金额")
	private String suretyAmt;

	/**
	 * 担保物类型
	 */
	@ApiModelProperty(value = "担保物类型")
	private String pledgeType;

	/**
	 * 已用金额
	 */
	@ApiModelProperty(value = "已用金额")
	private String amtUsed;


	/**
	 * 可用金额
	 */
	@ApiModelProperty(value = "可用金额")
	private String amtAvi;

	@ApiModelProperty(value = "担保合同类型 CD000102")
	private String subcontractTypeName;

	@ApiModelProperty(value = "是否最高额 CD000167")
	private String isTopName;

	@ApiModelProperty(value = "担保合同状态 CD000103")
	private String subcontractStausName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;
}
