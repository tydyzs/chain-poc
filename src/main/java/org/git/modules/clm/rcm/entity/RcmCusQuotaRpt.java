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
 * 客户授信集中度简表实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@TableName("TB_RCM_CUS_QUOTA_RPT")
@ApiModel(value = "RcmCusQuotaRpt对象", description = "客户授信集中度简表")
public class RcmCusQuotaRpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "信息编号")
	@TableId("INFO_NUM")
	private String infoNum;

	@ApiModelProperty(value = "集中度指标")
	@TableField("QUOTA_TYPE")
	private String quotaIndexNum;

	@ApiModelProperty(value = "实际授信情况(余额)")
	@TableField("AMT")
	private BigDecimal quotaUsedAmt;

	@ApiModelProperty(value = "实际授信情况(占比)")
	@TableField("RATIO")
	private BigDecimal quotaUsedRatio;

	@ApiModelProperty(value = "观察值")
	@TableField("QUOTA_LEVEL_A_AMT")
	private BigDecimal observeValue;

	@ApiModelProperty(value = "预警值")
	@TableField("QUOTA_LEVEL_B_AMT")
	private BigDecimal warnValue;

	@ApiModelProperty(value = "控制值")
	@TableField("QUOTA_LEVEL_C_AMT")
	private BigDecimal controlValue;

	@ApiModelProperty(value = "月份")
	@TableField("TOTAL_MONTH")
	private String totalMonth;

	@ApiModelProperty(value = "年份")
	@TableField("TOTAL_YEAR")
	private String totalYear;

	@ApiModelProperty(value = "生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;

	@ApiModelProperty(value = "维护人")
	@TableField("USER_NUM")
	private String userNum;

	@ApiModelProperty(value = "维护机构")
	@TableField("ORG_NUM")
	private String orgNum;

	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;

	@ApiModelProperty(value = "维护时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;

	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;


}
