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
package org.git.modules.clm.param.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 串用流水明细表实体类
 *
 * @author git
 * @since 2020-03-31
 */
@Data
@TableName("TB_CRD_BORROW_DETAIL")
@ApiModel(value = "TbCrdBorrowDetail对象", description = "串用流水明细表")
public class TbCrdBorrowDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 串用流水明细ID
	 */
	@ApiModelProperty(value = "串用流水明细ID")
	@TableId("LSTG_DETAIL_ID")
	private String lstgDetailId;
	/**
	 * 串用组ID
	 */
	@ApiModelProperty(value = "串用组ID")
	@TableField("LSTG_ID")
	private String lstgId;
	/**
	 * 串用业务编号
	 */
	@ApiModelProperty(value = "串用业务编号")
	@TableField("BIZ_NUM")
	private String bizNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 串用金额
	 */
	@ApiModelProperty(value = "串用金额")
	@TableField("BORROW_AMT")
	private BigDecimal borrowAmt;
	/**
	 * 串用余额
	 */
	@ApiModelProperty(value = "串用余额")
	@TableField("BORROW_BALANCE")
	private BigDecimal borrowBalance;
	/**
	 * 串用日期
	 */
	@ApiModelProperty(value = "串用日期")
	@TableField("BORROW_DATE")
	private String borrowDate;


}
