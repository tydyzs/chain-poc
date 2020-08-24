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
package org.git.modules.clm.rcm.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实体类
 *
 * @author git
 * @since 2019-10-29
 */
@Data
@TableName("tb_rcm_index_credit")
@ApiModel(value = "RcmIndexCredit对象", description = "RcmIndexCredit对象")
public class RcmIndexCredit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号")
	@TableId("QUOTA_INDEX_NUM")
	private String quotaIndexNum;

	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域")
	@TableField("RANGE_REGION")
	private String rangeRegion;
	/**
	 * 客户类型
	 */
	@ApiModelProperty(value = "客户类型")
	@TableField("RANGE_CUSTOMER")
	private String rangeCustomer;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY")
	private String currency;
	/**
	 * 国别
	 */
	@ApiModelProperty(value = "国别")
	@TableField("RANGE_COUNTRY")
	private String rangeCountry;
	/**
	 * 行业
	 */
	@ApiModelProperty(value = "行业")
	@TableField("RANGE_INDUSTRY")
	private String rangeIndustry;
	/**
	 * 产品编号
	 */
	@ApiModelProperty(value = "产品编号")
	@TableField("RANGE_PRODUCT")
	private String rangeProduct;
	/**
	 * 期限
	 */
	@ApiModelProperty(value = "期限")
	@TableField("RANGE_TERM")
	private String rangeTerm;
	/**
	 * 风险缓释
	 */
	@ApiModelProperty(value = "风险缓释")
	@TableField("RANGER_RISK_MITIGATION")
	private String rangerRiskMitigation;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@TableField("CREATE_TIME")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@TableField("UPDATE_TIME")
	private Date updateTime;

	/**
	 * 删除标志
	 */
	@TableLogic//添加此注解，mybatis-plus删除变为逻辑删除,mybatis-plus的查询也会过滤
	private String isDelete;
}
