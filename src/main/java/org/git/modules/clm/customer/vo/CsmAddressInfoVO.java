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
import org.git.modules.clm.customer.entity.CsmAddressInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 地址信息视图实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsmAddressInfoVO对象", description = "地址信息")
public class CsmAddressInfoVO extends CsmAddressInfo {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "联系类型(CD000031)")
	private String connTypeName;

	@ApiModelProperty(value = "联系地址国家/地区(CD000001)")
	private String counRegiName;

	@ApiModelProperty(value = "省代码(CD000002)")
	private String provinceName;

	@ApiModelProperty(value = "市代码(CD000002)")
	private String cityName;

	@ApiModelProperty(value = "县代码(CD000002)")
	private String countyName;
}
