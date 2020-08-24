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
package org.git.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 演示表实体类
 *
 * @author git
 * @since 2019-11-06
 */
@Data
@TableName("TB_SYS_DEMO")
@ApiModel(value = "Demo对象", description = "演示表")
public class Demo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "3232",required = true)
	@NotBlank
	@TableId("DEMO_ID")
	private String demoId;
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	@TableField("SEX")
	private String sex;
	@TableField("CERT_NO")
	private String certNo;
	@TableField("CERT_TYPE")
	private String certType;
	@TableField("ADDRESS")
	private String address;
	@TableField("PHONE_NO")
	private String phoneNo;
	@TableField("WORK_YEAR")
	private String workYear;

	@TableField("DEPOSIT_AMOUNT")
	private Double depositAmount;


}
