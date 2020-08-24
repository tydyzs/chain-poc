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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 额度统计表-客户-历史实体类
 *
 * @author git
 * @since 2019-12-16
 */
@Data
@TableName("TB_CRD_STATIS_CSM_HS")
@ApiModel(value = "CrdStatisCsmHs对象", description = "额度统计表-客户-历史")
public class CrdStatisCsmHs implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 统计ID
	 */
	@ApiModelProperty(value = "统计ID")
	@TableId("STATIS_ID")
	private String statisId;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 批复数量
	 */
	@ApiModelProperty(value = "批复数量")
	@TableField("APPROVE_COUNT")
	private String approveCount;
	/**
	 * 批复敞口金额
	 */
	@ApiModelProperty(value = "批复敞口金额")
	@TableField("APPROVE_EXP_AMOUNT")
	private String approveExpAmount;
	/**
	 * 授信敞口余额
	 */
	@ApiModelProperty(value = "授信敞口余额")
	@TableField("CREDIT_EXP_BALANCE")
	private String creditExpBalance;
	/**
	 * 贷款敞口余额
	 */
	@ApiModelProperty(value = "贷款敞口余额")
	@TableField("LOAN_EXP_BALANCE")
	private String loanExpBalance;
	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private String limitCredit;
	/**
	 * 可用额度
	 */
	@ApiModelProperty(value = "可用额度")
	@TableField("LIMIT_AVI")
	private String limitAvi;
	/**
	 * 已用额度
	 */
	@ApiModelProperty(value = "已用额度")
	@TableField("LIMIT_USED")
	private String limitUsed;
	/**
	 * 已用敞口
	 */
	@ApiModelProperty(value = "已用敞口")
	@TableField("EXP_USED")
	private String expUsed;
	/**
	 * 可用敞口
	 */
	@ApiModelProperty(value = "可用敞口")
	@TableField("EXP_AVI")
	private String expAvi;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 年份
	 */
	@ApiModelProperty(value = "年份")
	@TableField("YEAR")
	private String year;
	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	@TableField("MONTH")
	private String month;
	/**
	 * 日
	 */
	@ApiModelProperty(value = "日")
	@TableField("DATE")
	private String date;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField("UPDATE_TIME")
	private LocalDateTime updateTime;


}
