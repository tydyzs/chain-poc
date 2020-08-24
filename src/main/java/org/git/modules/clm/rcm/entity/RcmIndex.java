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
 *  Author: liuye
 */
package org.git.modules.clm.rcm.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实体类
 *
 * @author git
 * @since 2019-10-28
 */
@Data
@TableName("tb_rcm_index")
@ApiModel(value = "RcmIndex对象", description = "RcmIndex对象")
public class RcmIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "资产信息配置编号")
	@TableId("QUOTA_INDEX_NUM")
	private String quotaIndexNum;

	@ApiModelProperty(value = "限额指标名称")
	@TableField("QUOTA_INDEX_NAME")
	private String quotaIndexName;

	@ApiModelProperty(value = "限额类型")
	@TableField("QUOTA_TYPE")
	private String quotaType;

	@ApiModelProperty(value = "限额指标类型")
	@TableField("QUOTA_INDEX_TYPE")
	private String quotaIndexType;

	/**
	 * 统计口径
	 */
	@ApiModelProperty(value = "统计口径")
	@TableField("QUOTA_INDEX_CALIBER")
	private String quotaIndexCaliber;
	/**
	 * 分析维度
	 */
	@ApiModelProperty(value = "分析维度")
	@TableField("QUOTA_INDEX_RANGE")
	private String quotaIndexRange;

	/**
	 * 限额计算方式
	 */
	@ApiModelProperty(value = "限额计算方式")
	@TableField("COMPUTING_METHOD")
	private String computingMethod;
	/**
	 * 限额计算对象
	 */
	@ApiModelProperty(value = "限额计算对象")
	@TableField("COMPUTING_TARGET")
	private String computingTarget;

	@ApiModelProperty(value = "补充说明")
	@TableField("EXPLAIN_INFO")
	private String explainInfo;

	@ApiModelProperty(value = "指标状态")
	@TableField("QUOTA_INDEX_STATE")
	private String quotaIndexState;

	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;

	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@TableField("CREATE_TIME")
	private Date createTime;

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
