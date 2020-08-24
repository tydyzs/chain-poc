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
 * 行业授信集中度简表实体类
 *
 * @author git
 * @since 2019-12-11
 */
@Data
@TableName("TB_RCM_INDUSTRY_QUOTA_BASE_RPT")
@ApiModel(value = "RcmIndustryQuotaBaseRpt对象", description = "行业授信集中度简表")
public class RcmIndustryQuotaBaseRpt implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信息编号
	 */
	@ApiModelProperty(value = "信息编号")
	@TableId("INFO_NUM")
	private String infoNum;
	/**
	 * 集中度限额编号
	 */
	@ApiModelProperty(value = "集中度限额编号")
	@TableField("QUOTA_NUM")
	private String quotaNum;
	/**
	 * 行业
	 */
	@ApiModelProperty(value = "行业")
	@TableField("INDUSTRY")
	private String industry;
	/**
	 * 余额
	 */
	@ApiModelProperty(value = "余额")
	@TableField("CRD_BALANCE")
	private BigDecimal crdBalance;
	/**
	 * 占比
	 */
	@ApiModelProperty(value = "占比")
	@TableField("CRD_RATIO")
	private BigDecimal crdRatio;
	/**
	 * 观察值(余额)
	 */
	@ApiModelProperty(value = "观察值(余额)")
	@TableField("QUOTA_LEVEL_A_AMT")
	private BigDecimal quotaLevelAAmt;
	/**
	 * 观察值(占比)
	 */
	@ApiModelProperty(value = "观察值(占比)")
	@TableField("QUOTA_LEVEL_A_RATIO")
	private BigDecimal quotaLevelARatio;
	/**
	 * 预警值(余额)
	 */
	@ApiModelProperty(value = "预警值(余额)")
	@TableField("QUOTA_LEVEL_B_AMT")
	private BigDecimal quotaLevelBAmt;
	/**
	 * 预警值(占比)
	 */
	@ApiModelProperty(value = "预警值(占比)")
	@TableField("QUOTA_LEVEL_B_RATIO")
	private BigDecimal quotaLevelBRatio;
	/**
	 * 控制值(余额)
	 */
	@ApiModelProperty(value = "控制值(余额)")
	@TableField("QUOTA_LEVEL_C_AMT")
	private BigDecimal quotaLevelCAmt;
	/**
	 * 控制值(占比)
	 */
	@ApiModelProperty(value = "控制值(占比)")
	@TableField("QUOTA_LEVEL_C_RATIO")
	private BigDecimal quotaLevelCRatio;
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
