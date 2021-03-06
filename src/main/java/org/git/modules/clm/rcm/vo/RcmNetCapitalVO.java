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
import org.git.modules.clm.rcm.entity.RcmNetCapital;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


/**
 * 资本信息配置表视图实体类
 *
 * @author git
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmNetCapitalVO对象", description = "资本信息配置表")
public class RcmNetCapitalVO extends RcmNetCapital {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "机构名称")
	private String fullName;

	@ApiModelProperty(value = "维护机构")
	private String modifyFullName;

	@ApiModelProperty(value = "维护人名称")
	private String realName;

	@ApiModelProperty(value = "导入时间范围（大于条件）")
	private Date importTimeGt;

	@ApiModelProperty(value = "导入时间范围（小于条件）")
	private Date importTimeLt;

	@ApiModelProperty(value = "生效日期范围")
	private Date[] useDates;
}
