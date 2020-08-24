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
package org.git.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("chain_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 父主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "父主键")
	private String parentId;

	/**
	 * 祖级列表
	 */
	@ApiModelProperty(value = "祖级列表")
	private String ancestors;

	/**
	 * 部门类型
	 */
	@ApiModelProperty(value = "部门类型")
	private String deptCategory;

	/**
	 * 部门名
	 */
	@ApiModelProperty(value = "部门名")
	private String deptName;

	/**
	 * 部门全称
	 */
	@ApiModelProperty(value = "部门全称")
	private String fullName;

	/**
	 * 法人机构
	 */
	@ApiModelProperty(value = "法人机构")
	private String corpOrgCode;

	/**
	 * 机构级别
	 */
	@ApiModelProperty(value = "机构级别")
	private String deptLevel;

	/**
	 * 机构状态
	 */
	@ApiModelProperty(value = "机构状态")
	private String orgState;

	/**
	 * 机构属性
	 */
	@ApiModelProperty(value = "机构属性")
	private String orgAttr;

	/**
	 * 管理机构类型
	 */
	@ApiModelProperty(value = "管理机构类型")
	private String manageOrgType;

	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "机构类型")
	private String orgType;

	/**
	 * 组织机构代码
	 */
	@ApiModelProperty(value = "组织机构代码")
	private String orgCodeCertifi;

	/**
	 * 人行机构信用证
	 */
	@ApiModelProperty(value = "人行机构信用证")
	private String pbOrgLc;

	/**
	 * 开业日期
	 */
	@ApiModelProperty(value = "开业日期")
	private String startDate;

	/**
	 * 结业日期
	 */
	@ApiModelProperty(value = "结业日期")
	private String endDate;

	/**
	 * 省区代码
	 */
	@ApiModelProperty(value = "省区代码")
	private String provinceCode;

	/**
	 * 地区代码
	 */
	@ApiModelProperty(value = "地区代码")
	private String aeraCode;

	/**
	 * 机构地址
	 */
	@ApiModelProperty(value = "机构地址")
	private String orgAddress;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String telNum;

	/**
	 * 邮政编码
	 */
	@ApiModelProperty(value = "邮政编码")
	private String zipCode;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Timestamp updateTime;

	/**
	 * 删除
	 */
	@ApiModelProperty(value = "删除")
	private Integer isDeleted;

}
