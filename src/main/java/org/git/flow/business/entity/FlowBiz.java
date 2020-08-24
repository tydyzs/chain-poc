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
package org.git.flow.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.flow.core.entity.FlowEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 请假流程实体类
 *
 * @author Chill
 */
@Data
@TableName("tb_flow_biz")
@EqualsAndHashCode(callSuper = true)
public class FlowBiz extends FlowEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义Key（标识）
	 */
	@TableField(exist = false)
	private String processDefinitionKey;

	/**
	 * 流程定义id（主键）
	 */
	private String processDefinitionId;

	/**
	 * 流程实例id
	 */
	private String processInstanceId;

	/**
	 * 客户编号
	 */
	private String customerNum;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 业务编号
	 */
	private String bizNum;
	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 业务描述
	 */
	private String bizDesc;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 审批人
	 */
	private String taskUser;
	/**
	 * 流程申请时间
	 */
	private Date applyTime;

}
