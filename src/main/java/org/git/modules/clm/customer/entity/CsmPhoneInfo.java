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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 电话号码信息表实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_PHONE_INFO")
@ApiModel(value = "CsmPhoneInfo对象", description = "电话号码信息表")
public class CsmPhoneInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 电话ID
	 */
	@ApiModelProperty(value = "电话ID")
	@TableId(value = "PHONE_ID", type = IdType.UUID)
	private String phoneId;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	private String customerNum;

	/**
	 * 联系类型(CD000003)
	 */
	@ApiModelProperty(value = "联系类型(CD000003)")
	private String connType;
	/**
	 * 国际长途区号
	 */
	@ApiModelProperty(value = "国际长途区号")
	private String interCode;
	/**
	 * 国内长途区号
	 */
	@ApiModelProperty(value = "国内长途区号")
	private String inlandCode;
	/**
	 * 联系号码
	 */
	@ApiModelProperty(value = "联系号码")
	private String telNumber;
	/**
	 * 分机号
	 */
	@ApiModelProperty(value = "分机号")
	private String extenNum;
	/**
	 * 是否已核实
	 */
	@ApiModelProperty(value = "是否已核实")
	private String isCheckFlag;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Timestamp updateTime;


}
