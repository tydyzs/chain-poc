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

import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.credit.entity.CrdSum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 额度汇总表（实时）视图实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdSumVO对象", description = "额度汇总表（实时）")
public class CrdSumVO extends CrdSum {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "客户经理号")
	private String userNum;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

	@ApiModelProperty(value = "客户类型")
	private String customerType;

	@ApiModelProperty(value = "证件类型")
	private String certType;

	@ApiModelProperty(value = "证件号码")
	private String certNum;

	@ApiModelProperty(value = "备用机构号")
	private String orgNumAnother;

	@ApiModelProperty(value = "机构名")
	private String orgNumName;

	@ApiModelProperty(value = "币种(CD000019)")
	private String currencyCdName;

	@ApiModelProperty(value = "证件类型(CD000003)")
	private String certTypeName;

}
