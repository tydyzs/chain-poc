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
package org.git.modules.clm.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 关系信息表实体类
 *
 * @author git
 * @since 2019-12-05
 */
@Data
@TableName("TB_CSM_RELATION")
@ApiModel(value = "CsmRelation对象", description = "关系信息表")
public class CsmRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关系ID
	 */
	@ApiModelProperty(value = "关系ID")
	@TableId(value = "REL_ID", type = IdType.UUID)
	private String relId;

	/**
	 * 行内外关联标志
	 */
	@ApiModelProperty(value = "行内外关联标志")
	@TableField("REL_MARK")
	private String relMark;
	/**
	 * 人行支付清算行号
	 */
	@ApiModelProperty(value = "人行支付清算行号")
	@TableField("SETTLE_BANK_NUM")
	private String settleBankNum;
	/**
	 * 上级人行支付清算行号
	 */
	@ApiModelProperty(value = "上级人行支付清算行号")
	@TableField("SUP_SETTLE_BANK_NUM")
	private String supSettleBankNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 关系客户编号
	 */
	@ApiModelProperty(value = "关系客户编号")
	@TableField("REL_CUSTOMER_NUM")
	private String relCustomerNum;
	/**
	 * 关系客户类型
	 */
	@ApiModelProperty(value = "关系客户类型")
	@TableField("REL_CUSTOMER_TYPE")
	private String relCustomerType;
	/**
	 * 客户关系类型
	 */
	@ApiModelProperty(value = "客户关系类型")
	@TableField("REL_TYPE")
	private String relType;
	/**
	 * 关系人名称
	 */
	@ApiModelProperty(value = "关系人名称")
	@TableField("CUST_NAME")
	private String custName;
	/**
	 * 证件类型
	 */
	@ApiModelProperty(value = "证件类型")
	@TableField("CERT_TYPE")
	private String certType;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	@TableField("CERT_NUM")
	private String certNum;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	@TableField("GENDER")
	private String gender;

	/**
	 * 企业规模
	 */
	@ApiModelProperty(value = "企业规模")
	@TableField("UNIT_SCALE")
	private String unitScale;

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


}
