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
 * 客户主表实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_PARTY")
@ApiModel(value = "CsmParty对象", description = "客户主表")
public class CsmParty implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableId(value = "CUSTOMER_NUM", type = IdType.UUID)
	private String customerNum;

	/**
	 * 是否我行关联方(:YesOrNo)
	 */
	@ApiModelProperty(value = "是否我行关联方(:YesOrNo)")
	@TableField("IS_BANK_REL")
	private String isBankRel;
	/**
	 * 参与人类型(CD000033)
	 */
	@ApiModelProperty(value = "参与人类型(CD000033)")
	@TableField("CUSTOMER_TYPE")
	private String customerType;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableField("CUSTOMER_NAME")
	private String customerName;
	/**
	 * 是否迁移数据
	 */
	@ApiModelProperty(value = "是否迁移数据")
	@TableField("IF_DATA_MOVE")
	private String ifDataMove;
	/**
	 * 评级信息
	 */
	@ApiModelProperty(value = "评级信息")
	@TableField("GRADE")
	private String grade;
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;


}
