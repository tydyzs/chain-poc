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
package org.git.modules.clm.front.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.core.mp.base.BaseEntity;

/**
 * 前置服务配置表实体类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Data
@TableName("tb_front_service_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ServiceConfig对象", description = "前置服务配置表")
public class ServiceConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 终端代码
	*/
		@ApiModelProperty(value = "终端代码")
		private String terminalCode;
	/**
	* 服务角色
	*/
		@ApiModelProperty(value = "服务角色")
		private String serviceRole;
	/**
	* 服务代码
	*/
		@ApiModelProperty(value = "服务代码")
		private String serviceCode;
	/**
	* 服务名称
	*/
		@ApiModelProperty(value = "服务名称")
		private String serviceName;
	/**
	* 服务适配器
	*/
		@ApiModelProperty(value = "服务适配器")
		private String serviceAdapter;
	/**
	* 调用方式
	*/
		@ApiModelProperty(value = "调用方式")
		private String invokeMode;
	/**
	* 调用接口
	*/
		@ApiModelProperty(value = "调用接口")
		private String invokeApi;
	/**
	* 编码方式
	*/
		@ApiModelProperty(value = "编码方式")
		private String encodeMode;
	/**
	* 报文风格
	*/
		@ApiModelProperty(value = "报文风格")
		private String messageStyle;
	/**
	* 超时毫秒
	*/
		@ApiModelProperty(value = "超时毫秒")
		private Integer timeout;
	/**
	* 事务方式
	*/
		@ApiModelProperty(value = "事务方式")
		private String transactionMode;
	/**
	* 版本号
	*/
		@ApiModelProperty(value = "版本号")
		private String version;
	/**
	* 备注
	*/
		@ApiModelProperty(value = "备注")
		private String remark;


}
