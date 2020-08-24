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
 * 前置终端配置表实体类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Data
@TableName("tb_front_terminal_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TerminalConfig对象", description = "前置终端配置表")
public class TerminalConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 终端代码
	 */
	@ApiModelProperty(value = "终端代码")
	private String terminalCode;
	/**
	 * 终端名称
	 */
	@ApiModelProperty(value = "终端名称")
	private String terminalName;
	/**
	 * 终端缩写
	 */
	@ApiModelProperty(value = "终端缩写")
	private String terminalShortName;

	/**
	 * 终端角色
	 */
	@ApiModelProperty(value = "终端角色")
	private String terminalRole;

	/**
	 * 接收地址
	 */
	@ApiModelProperty(value = "接收地址")
	private String receiveAddress;

	/**
	 * 发送地址
	 */
	@ApiModelProperty(value = "发送地址")
	private String sendAddress;

	/**
	 * 连接方式
	 */
	@ApiModelProperty(value = "连接方式")
	private String connectMode;
	/**
	 * 是否授权
	 */
	@ApiModelProperty(value = "是否授权")
	private Integer isAuth;
	/**
	 * 校验码
	 */
	@ApiModelProperty(value = "校验码")
	private String token;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 父级终端
	 */
	@ApiModelProperty(value = "父级终端")
	private String parentTerminalId;


}
