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
package org.git.modules.clm.rcm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 最大十家客户授信集中度明细表实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@TableName("TB_RCM_CREDIT_TEN_CUS_RPT")
@ApiModel(value = "RcmCreditTenCusRpt对象", description = "最大十家客户授信集中度明细表")
public class RcmCreditTenCusRpt implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信息编号
	 */
	@ApiModelProperty(value = "信息编号")
	@TableId("INFO_NUM")
	private String infoNum;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableField("CUSTOMER_NAME")
	private String customerName;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 授信余额
	 */
	@ApiModelProperty(value = "授信余额")
	@TableField("CRD_BALANCE")
	private BigDecimal crdBalance;
	/**
	 * 上月授信余额
	 */
	@ApiModelProperty(value = "上月授信余额")
	@TableField("CRD_BALANCE_LAST_MONTH")
	private BigDecimal crdBalanceLastMonth;
	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	@TableField("TOTAL_MONTH")
	private String totalMonth;
	/**
	 * 年份
	 */
	@ApiModelProperty(value = "年份")
	@TableField("TOTAL_YEAR")
	private String totalYear;
	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;
	/**
	 * 维护人
	 */
	@ApiModelProperty(value = "维护人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 维护机构
	 */
	@ApiModelProperty(value = "维护机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	 * 维护时间
	 */
	@ApiModelProperty(value = "维护时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;


}
