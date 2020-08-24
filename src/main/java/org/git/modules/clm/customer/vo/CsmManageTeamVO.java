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
import org.git.modules.clm.customer.entity.CsmManageTeam;

/**
 * 管理团队视图实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsmManageTeamVO对象", description = "管理团队")
public class CsmManageTeamVO extends CsmManageTeam {
	private static final long serialVersionUID = 1L;

	/**
	 *客户经理姓名
	 */
	@ApiModelProperty(value = "客户经理姓名")
	private String realName;

	/**
	 *机构名
	 */
	@ApiModelProperty(value = "机构名")
	private String deptName;

	/**
	 *机构地区
	 */
	@ApiModelProperty(value = "机构地区")
	private String aeraCode;

	@ApiModelProperty(value = "机构地区 CD000002")
	private String aeraCodeName;

	@ApiModelProperty(value = "权限类型 CD000185")
	private String userPlacingCdName;
}
