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
package org.git.modules.clm.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 管理团队实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_MANAGE_TEAM")
@ApiModel(value = "CsmManageTeam对象", description = "管理团队")
public class CsmManageTeam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户经理小组成员的唯一标识，一个用户的一个角色可能对应多个该ID(属于多个客户管理团队)
	 */
	@ApiModelProperty(value = "客户经理小组成员的唯一标识，一个用户的一个角色可能对应多个该ID(属于多个客户管理团队)")
	@TableId(value = "UUID", type = IdType.UUID)
	private String uuid;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 经办用户
	 */
	@ApiModelProperty(value = "经办用户")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 权限类型
	 */
	@ApiModelProperty(value = "权限类型")
	@TableField("USER_PLACING_CD")
	private String userPlacingCd;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;


}
