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
package org.git.modules.clm.param.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.Element;

/**
 * 串用组信息表实体类
 *
 * @author git
 * @since 2020-03-31
 */
@Data
@TableName("TB_PAR_BORROW")
@ApiModel(value = "TbParBorrow对象", description = "串用组信息表")
public class TbParBorrow implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务品种编号
	 */
	@ApiModelProperty(value = "业务品种编号")
	@TableId(value = "LSTG_ID", type = IdType.UUID)
	private String lstgId;
	/**
	 * 串用组名称
	 */
	@ApiModelProperty(value = "串用组名称")
	@TableField("LSTG_NAME")
	private String lstgName;
	/**
	 * 串用组类型
	 */
	@ApiModelProperty(value = "串用组类型")
	@TableField("LSTG_TYPE")
	private String lstgType;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableField("CUSTOMER_NAME")
	private String customerName;
	/**
	 * 三级额度产品
	 */
	@ApiModelProperty(value = "三级额度产品")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 串用三级额度
	 */
	@ApiModelProperty(value = "串用三级额度")
	@TableField("LSTG_PRD")
	private String lstgPrd;
	/**
	 * 串用比例上限
	 */
	@ApiModelProperty(value = "串用比例上限")
	@TableField("PRO_LIMIT")
	private BigDecimal proLimit;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 机构号
	 */
	@ApiModelProperty(value = "机构号")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;


	/**
	 * 是否删除
	 */
	@ApiModelProperty(value = "是否删除")
	@TableField("IS_DELETED")
	private Integer isDeleted;


}
