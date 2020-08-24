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
package org.git.modules.clm.front.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.front.entity.ServiceRecord;

/**
 * 前置服务记录表视图实体类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ServiceRecordVO对象", description = "前置服务记录表")
public class ServiceRecordVO extends ServiceRecord {
	private static final long serialVersionUID = 1L;

	//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(exist = false)
	private String startCreateTime;

	//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(exist = false)
	private String endCreateTime;
}
