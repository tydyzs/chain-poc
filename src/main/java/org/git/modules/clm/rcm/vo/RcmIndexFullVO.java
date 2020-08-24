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

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmIndex;

/**
 * 视图实体类
 *
 * @author git
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmIndexFullVO对象", description = "RcmIndexFullVO对象")
public class RcmIndexFullVO extends RcmIndex {
	private static final long serialVersionUID = 1L;

	//以下为共有

	@ApiModelProperty(value = "客户类型")
	@TableField("RANGE_CUSTOMER")
	private String rangeCustomer;

	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY")
	private String currency;

	@ApiModelProperty(value = "国别")
	@TableField("RANGE_COUNTRY")
	private String rangeCountry;

	@ApiModelProperty(value = "产品编号")
	@TableField("RANGE_PRODUCT")
	private String rangeProduct;

	//以下为tb_rcm_index_credit非同业独有

	@ApiModelProperty(value = "区域")
	@TableField("RANGE_REGION")
	private String rangeRegion;

	@ApiModelProperty(value = "行业")
	@TableField("RANGE_INDUSTRY")
	private String rangeIndustry;

	@ApiModelProperty(value = "期限范围")
	@TableField("range_term")
	private String rangeTerm;

	@ApiModelProperty(value = "风险缓释")
	@TableField("RANGER_RISK_MITIGATION")
	private String rangerRiskMitigation;


	//以下为tb_rcm_index_bank同业独有

	@ApiModelProperty(value = "业务场景")
	@TableField("BUSS_SCENE")
	private String bussScene;

	@ApiModelProperty(value = "业务类型")
	@TableField("BUSINESS_TYPE")
	private String businessType;

//
//	private String orgName;
//
//	private String userName;

}
