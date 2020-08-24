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

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.SqlTimestampTypeHandler;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

/**
 * 资本信息配置表实体类
 *
 * @author git
 * @since 2019-10-24
 */
@Data
@TableName("tb_rcm_net_capital")
@ApiModel(value = "RcmNetCapital对象", description = "资本信息配置表")
public class RcmNetCapital implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "NET_CAPITAL_NUM", type = IdType.ID_WORKER_STR)
	private String netCapitalNum;

	/**
	 * 生效机构
	 */
	@ApiModelProperty(value = "生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;

	/**
	 * 生效日期
	 */
	@NotEmpty(message = "生效日期不能为空")
	@ApiModelProperty(value = "生效日期")
	@TableField("USE_DATE")
	private String useDate;


	/**
	 * 资本净额
	 */
	@ApiModelProperty(value = "资本净额")
	@TableField("NET_CAPITAL")
	private BigDecimal netCapital;

	/**
	 * 一级资本净额
	 */
	@ApiModelProperty(value = "一级资本净额")
	@TableField("NET_PRIMARY_CAPITAL")
	private BigDecimal netPrimaryCapital;

	/**
	 * 净资产
	 */
	@ApiModelProperty(value = "净资产")
	@TableField("NET_ASSETS")
	private BigDecimal netAssets;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	@TableField("NET_STATE")
	private String netState;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value = "CREATE_TIME", jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE)
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField(value = "UPDATE_TIME", jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE)
	private Timestamp updateTime;


}
