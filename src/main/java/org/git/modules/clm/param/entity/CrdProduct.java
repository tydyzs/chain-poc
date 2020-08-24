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
package org.git.modules.clm.param.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author git
 * @since 2019-10-21
 */
@Data
@ApiModel(value = "CrdProduct对象", description = "CrdProduct对象")
@TableName("TB_PAR_CRD_PRODUCT")
public class CrdProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "额度产品编号")
	private String crdProductNum;
	/**
	 * 业务品种编号
	 */
	@ApiModelProperty(value = "业务品种编号")
	@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	 * 客户类型
	 */
	@ApiModelProperty(value = "客户类型")
	@TableField("CUST_TYPE")
	private String custType;
	/**
	 * 主要担保方式
	 */
	@ApiModelProperty(value = "主要担保方式")
	@TableField("MAIN_GUARANTEE_TYPE")
	private String mainGuaranteeType;
	/**
	 * 业务种类（个人消费 个人经营 流贷 固贷）
	 */
	@ApiModelProperty(value = "业务种类（个人消费 个人经营 流贷 固贷）")
	@TableField("PRODUCT_TYPE")
	private String productType;

	/**
	 * 项目类型
	 */
	@ApiModelProperty(value = "项目类型")
	@TableField("PROJECT_TYPE")
	private String projectType;

	/**
	 * 是否低风险
	 */
	@ApiModelProperty(value = "是否低风险")
	@TableField(exist = false)
	private String isLowRisk;

	/**
	 * 额度类型
	 */
	@ApiModelProperty(value = "额度类型")
	@TableField(exist = false)
	private String crdProductType;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String userNum;
	/**
	 * 创建机构
	 */
	@ApiModelProperty(value = "创建机构")
	private String orgNum;
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
	 * 是否已删除
	 */
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;

}
