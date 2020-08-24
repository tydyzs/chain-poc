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
package org.git.modules.clm.param.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.param.entity.TbParBorrow;

/**
 * 客户信息VO
 *
 * @author git
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerVO客户信息", description = "CustomerVO客户信息")
public class CustomerVO extends CsmParty {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "证件类型")
	private String certType;

	@ApiModelProperty(value = "证件类型名称")
	private String certTypeName;

	@ApiModelProperty(value = "证件号码")
	private String certNum;

}
