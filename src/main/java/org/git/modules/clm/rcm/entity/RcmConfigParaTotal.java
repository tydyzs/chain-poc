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

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 限额参数配置信息统计表实体类
 *
 * @author git
 * @since 2019-12-23
 */
@Data
@TableName("TB_RCM_CONFIG_PARA_TOTAL")
@ApiModel(value = "RcmControlTotal对象", description = "限额参数配置信息统计表")
public class RcmConfigParaTotal implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分项参数编号
	 */
	@ApiModelProperty(value = "分项参数编号")
	@TableField("SUB_PARA_NUM")
	private String subParaNum;
	/**
	 * 集中度限额编号
	 */
	@ApiModelProperty(value = "集中度限额编号")
	@TableField("QUOTA_NUM")
	private String quotaNum;
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
	 * 阈值层级
	 */
	@ApiModelProperty(value = "阈值层级")
	@TableField("QUOTA_LEVEL")
	private String quotaLevel;
	/**
	 * 阈值（余额）
	 */
	@ApiModelProperty(value = "阈值（余额）")
	@TableField("QUOTA_CONTROL_AMT")
	private BigDecimal quotaControlAmt;
	/**
	 * 阈值（占比）
	 */
	@ApiModelProperty(value = "阈值（占比）")
	@TableField("QUOTA_CONTROL_RATIO")
	private BigDecimal quotaControlRatio;
	/**
	 * 阈值类型
	 */
	@ApiModelProperty(value = "阈值类型")
	@TableField("QUOTA_CONTROL_TYPE")
	private String quotaControlType;
	/**
	 * 限额管控节点
	 */
	@ApiModelProperty(value = "限额管控节点")
	@TableField("CONTROL_NODE")
	private String controlNode;
	/**
	 * 管控节点的应对措施
	 */
	@ApiModelProperty(value = "管控节点的应对措施")
	@TableField("NODE_MEASURE")
	private String nodeMeasure;
	/**
	 * 应对措施等级
	 */
	@ApiModelProperty(value = "应对措施等级")
	@TableField("MEASURE_LEVEL")
	private String measureLevel;
	/**
	 * 补充说明
	 */
	@ApiModelProperty(value = "补充说明")
	@TableField("EXPLAIN_INFO")
	private String explainInfo;
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
