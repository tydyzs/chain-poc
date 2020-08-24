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

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 限额配置历史表实体类
 *
 * @author git
 * @since 2019-12-23
 */
@Data
@TableName("TB_RCM_CONFIG_HIS")
@ApiModel(value = "RcmConfigHis对象", description = "限额配置历史表")
public class RcmConfigHis implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 历史表主键
	 */
	@ApiModelProperty(value = "历史表主键")
	@TableId("HIS_ID")
	private String hisId;
	/**
	 * 集中度限额编号
	 */
	@ApiModelProperty(value = "集中度限额编号")
	@TableField("QUOTA_NUM")
	private String quotaNum;
	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称")
	@TableField("QUOTA_NAME")
	private String quotaName;
	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号")
	@TableField("QUOTA_INDEX_NUM")
	private String quotaIndexNum;
	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;
	/**
	 * 限额已用金额
	 */
	@ApiModelProperty(value = "限额已用金额")
	@TableField("QUOTA_USED_AMT")
	private BigDecimal quotaUsedAmt;
	/**
	 * 限额可用金额
	 */
	@ApiModelProperty(value = "限额可用金额")
	@TableField("QUOTA_FREE_AMT")
	private BigDecimal quotaFreeAmt;
	/**
	 * 限额已用比率
	 */
	@ApiModelProperty(value = "限额已用比率")
	@TableField("QUOTA_USED_RATIO")
	private BigDecimal quotaUsedRatio;
	/**
	 * 限额可用比率
	 */
	@ApiModelProperty(value = "限额可用比率")
	@TableField("QUOTA_FREE_RATIO")
	private BigDecimal quotaFreeRatio;
	/**
	 * 限额失效日期
	 */
	@ApiModelProperty(value = "限额失效日期")
	@TableField("INVALID_DATE")
	private String invalidDate;
	/**
	 * 限额状态:CD000261
	 */
	@ApiModelProperty(value = "限额状态:CD000261")
	@TableField("QUOTA_STATE")
	private String quotaState;
	/**
	 * 观察值历史触发次数
	 */
	@ApiModelProperty(value = "观察值历史触发次数")
	@TableField("HIS_FREQUENCY_A")
	private BigDecimal hisFrequencyA;
	/**
	 * 预警值历史触发次数
	 */
	@ApiModelProperty(value = "预警值历史触发次数")
	@TableField("HIS_FREQUENCY_B")
	private BigDecimal hisFrequencyB;
	/**
	 * 控制值历史触发次数
	 */
	@ApiModelProperty(value = "控制值历史触发次数")
	@TableField("HIS_FREQUENCY_C")
	private BigDecimal hisFrequencyC;
	/**
	 * 历史触发次数
	 */
	@ApiModelProperty(value = "历史触发次数")
	@TableField("HIS_FREQUENCY")
	private BigDecimal hisFrequency;
	/**
	 * 补充说明
	 */
	@ApiModelProperty(value = "补充说明")
	@TableField("EXPLAIN_INFO")
	private String explainInfo;
	/**
	 * 复核人
	 */
	@ApiModelProperty(value = "复核人")
	@TableField("INSPECT_USER_NUM")
	private String inspectUserNum;
	/**
	 * 复核机构
	 */
	@ApiModelProperty(value = "复核机构")
	@TableField("INSPECT_ORG_NUM")
	private String inspectOrgNum;
	/**
	 * 复核日期
	 */
	@ApiModelProperty(value = "复核日期")
	@TableField("INSPECT_TIME")
	private Date inspectTime;
	/**
	 * 申请状态:CD**
	 */
	@ApiModelProperty(value = "申请状态:CD**")
	@TableField("APPLY_STATE")
	private String applyState;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额")
	@TableField("QUOTA_TOTAL_SUM")
	private BigDecimal quotaTotalSum;
	/**
	 * 限额总额类型:CD000263
	 */
	@ApiModelProperty(value = "限额总额类型:CD000263")
	@TableField("QUOTA_TOTAL_TYPE")
	private String quotaTotalType;
	/**
	 * 限额生效日期
	 */
	@ApiModelProperty(value = "限额生效日期")
	@TableField("START_DATE")
	private String startDate;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;


}
