/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmConfig;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 限额详情视图
 *
 * @author liuye
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmConfigurationManagerVO对象", description = "限额详情视图")
public class RcmQuotaManagerVO extends RcmConfig {
	private static final long serialVersionUID = 1L;

	/**
	 * 生效机构名称
	 */
	@ApiModelProperty(value = "生效机构名称")
	private String useOrgName;

	//--------------------------限额参数--------------------------
	/**
	 * 控制值
	 */
	@ApiModelProperty(value = "控制值")
	private BigDecimal controlValue;

	/**
	 * 控制值类型
	 */
	@ApiModelProperty(value = "控制值类型 1.金额 2.百分比")
	private String controlValueType;

	/**
	 * 控制值管控节点
	 */
	@ApiModelProperty(value = "控制值管控节点")
	private String controlNode;

	/**
	 * 控制值管控节点应对措施
	 */
	@ApiModelProperty(value = "控制值管控节点应对措施")
	private String controlNodeMeasure;

	/**
	 * 预警值
	 */
	@ApiModelProperty(value = "预警值")
	private BigDecimal warnValue;

	/**
	 * 预警值类型
	 */
	@ApiModelProperty(value = "预警值类型 1.金额 2.百分比")
	private String warnValueType;

	/**
	 * 预警值管控节点
	 */
	@ApiModelProperty(value = "预警值管控节点")
	private String warnNode;

	/**
	 * 预警值管控节点应对措施
	 */
	@ApiModelProperty(value = "预警值管控节点应对措施")
	private String warnNodeMeasure;

	/**
	 * 观察值
	 */
	@ApiModelProperty(value = "观察值")
	private BigDecimal observeValue;

	/**
	 * 观察值类型
	 */
	@ApiModelProperty(value = "观察值类型 1.金额 2.百分比")
	private String observeValueType;

	/**
	 * 观察值管控节点
	 */
	@ApiModelProperty(value = "观察值管控节点")
	private String observeNode;

	/**
	 * 观察值管控节点应对措施
	 */
	@ApiModelProperty(value = "观察值管控节点应对措施")
	private String observeNodeMeasure;

	//--------------------------限额指标--------------------------

	/**
	 * 限额指标类型
	 */
	@ApiModelProperty(value = "限额指标类型")
	private String quotaIndexType;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号")
	private String quotaIndexNum;

	/**
	 * 限额指标名称
	 */
	@ApiModelProperty(value = "限额指标名称")
	private String quotaIndexName;

	/**
	 * 限额类型
	 */
	@ApiModelProperty(value = "限额类型")
	private String quotaType;

	/**
	 * 限额计算方式
	 */
	@ApiModelProperty(value = "限额计算方式")
	private String computingMethod;

	/**
	 * 统计口径
	 */
	@ApiModelProperty(value = "统计口径")
	private String quotaIndexCaliber;

	/**
	 * 限额计算对象
	 */
	@ApiModelProperty(value = "限额计算对象")
	private String computingTarget;

	/**
	 * 指标状态
	 */
	@ApiModelProperty(value = "指标状态")
	private String quotaIndexState;

	/**
	 * 限额指标要素
	 */
	@ApiModelProperty(value = "限额指标要素")
	private String quotaIndexElements;

	/**
	 * 补充说明
	 */
	@ApiModelProperty(value = "补充说明")
	private String explainInfo;

	@ApiModelProperty(value = "经办人")
	private String userNum;

	@ApiModelProperty(value = "经办人")
	private String userName;

	@ApiModelProperty(value = "经办机构")
	private String orgNum;

	@ApiModelProperty(value = "经办机构")
	private String orgName;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	//--------------------------对象值--------------------------

	/**
	 * 限额指标信息
	 */
	@ApiModelProperty(value = "限额指标信息")
	private RcmIndexFullVO rcmIndex;

	/**
	 * 限额参数信息
	 */
	@ApiModelProperty(value = "限额参数信息")
	List<RcmConfigParaVO> rcmConfigPara;
}
