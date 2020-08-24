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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 前置服务记录表实体类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Data
@TableName("tb_front_service_record")
@ApiModel(value = "ServiceRecord对象", description = "前置服务记录表")
public class ServiceRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 记录ID
	 */
	@ApiModelProperty(value = "记录ID")
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/**
	 * 分组ID
	 */
	@ApiModelProperty(value = "分组ID")
	private String groupId;
	/**
	 * 请求终端
	 */
	@ApiModelProperty(value = "请求终端")
	private String requestorCode;
	/**
	 * 响应终端
	 */
	@ApiModelProperty(value = "响应终端")
	private String responderCode;
	/**
	 * 请求报文
	 */
	@ApiModelProperty(value = "请求报文")
	private String requestMessage;
	/**
	 * 响应报文
	 */
	@ApiModelProperty(value = "响应报文")
	private String responseMessage;
	/**
	 * 处理状态
	 */
	@ApiModelProperty(value = "处理状态")
	private Integer status;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Timestamp updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 服务代码
	 */
	@ApiModelProperty(value = "服务代码")
	private String serviceCode;
	/**
	 * 交易流水号
	 */
	@ApiModelProperty(value = "交易流水号")
	private String serviceSn;
	/**
	 * 交易时间
	 */
	@ApiModelProperty(value = "交易时间")
	private Timestamp serviceTime;
	/**
	 * 业务流水号
	 */
	@ApiModelProperty(value = "业务流水号")
	private String bizSn;
	/**
	 * 重试次数
	 */
	@ApiModelProperty(value = "重试次数")
	private Integer retryCount;


}
