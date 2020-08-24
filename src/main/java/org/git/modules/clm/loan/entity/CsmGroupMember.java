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
package org.git.modules.clm.loan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 集团成员表实体类
 *
 * @author git
 * @since 2019-12-22
 */
@Data
@TableName("TB_CSM_GROUP_MEMBER")
@ApiModel(value = "CsmGroupMember对象", description = "集团成员表")
public class CsmGroupMember implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键ID
	*/
		@ApiModelProperty(value = "主键ID")
		@TableId("ID")
	private String id;

	/**
	* 客户编号
	*/
		@ApiModelProperty(value = "客户编号")
		@TableField("CUSTOMER_NUM")
	private String customerNum;


	/**
	* 成员客户编号
	*/
		@ApiModelProperty(value = "成员客户编号")
		@TableField("MEMBER_CUSTOMER_NUM")
	private String memberCustomerNum;

	/**
	* 关联关系状态
	*/
		@ApiModelProperty(value = "关联关系状态")
		@TableField("STATUS")
	private String status;

	/**
	* 更新时间
	*/
		@ApiModelProperty(value = "更新时间")
		@TableField("UPDATE_TIME")
	private LocalDateTime updateTime;

	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		@TableField("CREATE_TIME")
	private LocalDateTime createTime;

	/**
	* 成员关系类型（CD000213）
	*/
		@ApiModelProperty(value = "成员关系类型（CD000213）")
		@TableField("MEMBER_REL_TYPE")
	private String memberRelType;



}
