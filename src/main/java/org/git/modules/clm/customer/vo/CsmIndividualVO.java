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

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.customer.entity.CsmIndividual;

/**
 * 个人客户基本信息视图实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsmIndividualVO对象", description = "个人客户基本信息")
public class CsmIndividualVO extends CsmIndividual {
	private static final long serialVersionUID = 1L;

	/**
	 *证件类型
	 */
	@ApiModelProperty(value = "证件类型")
	private String certType;

	/**
	 *证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String certNum;

	@ApiModelProperty(value = "证件类型 CD000003")
	private String certTypeName;

	@ApiModelProperty(value = "性别 CD000004")
	private String genderName;

	@ApiModelProperty(value = "国籍 CD000001")
	private String nationName;

	@ApiModelProperty(value = "民族 CD000005")
	private String raceName;

	@ApiModelProperty(value = "最高学位 CD000010")
	private String highAcadeDegreeName;

	@ApiModelProperty(value = "最高学历(CD000011)")
	private String educationName;

	@ApiModelProperty(value = "是否涉农 CD000167")
	private String agriRelatedIndName;

	@ApiModelProperty(value = "职业 CD000012")
	private String occupation3Name;

	@ApiModelProperty(value = "婚姻状况(CD000007)")
	private String marrStatusName;

	@ApiModelProperty(value = "健康状况(CD000009)")
	private String healthyStatusName;

	@ApiModelProperty(value = "客户等级(CD000216)")
	private String custGradeName;
}
