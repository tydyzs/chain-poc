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
package org.git.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 营业日期实体类
 *
 * @author git
 * @since 2019-11-20
 */
@Data
@TableName("TB_SYS_DATE")
@ApiModel(value = "SysDate对象", description = "营业日期")
public class SysDate implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前营业日期
	 */
	@ApiModelProperty(value = "当前营业日期")
	@TableField("WORK_DATE")
	private String workDate;
	/**
	 * 当前批量日期
	 */
	@ApiModelProperty(value = "当前批量日期")
	@TableField("BATCH_DATE")
	private String batchDate;
	/**
	 * 系统开关（1开 2关）
	 */
	@ApiModelProperty(value = "系统开关（1开 2关）")
	@TableField("SYSTEM_SWITCH")
	private String systemSwitch;


}
