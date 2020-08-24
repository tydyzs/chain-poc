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
package org.git.modules.clm.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.customer.entity.CsmCorporation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 公司客户基本信息视图实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsmCorporationVO对象", description = "公司客户基本信息")
public class CsmCorporationVO extends CsmCorporation {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "对公客户类型 CD000184")
	private String beneCustTypeName;

	@ApiModelProperty(value = "国别代码 CD000001")
	private String countryCodeName;

	@ApiModelProperty(value = "注册资本币种 CD000019")
	private String regCptlCurrName;

	@ApiModelProperty(value = "国民经济行业4 CD000015")
	private String nationalEconomyDepart4Name;

	@ApiModelProperty(value = "企业规模 CD000020")
	private String unitScaleName;

}
