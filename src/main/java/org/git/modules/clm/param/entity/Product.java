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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author git
 * @since 2019-10-21
 */
@Data
@ApiModel(value = "Product对象", description = "Product对象")
@TableName("TB_PAR_PRODUCT")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务品种编号
	 */
	@TableId(value = "product_num", type = IdType.UUID)//mybatis-plus主键不是自动生成的，需要配置主键生成的算法
	@ApiModelProperty(value = "额度产品编号")
	private String productNum;

	/**
	 * 业务品种名称
	 */
	private String productName;
	/**
	 * 业务种类分类
	 */
	private String productType;
	/**
	 * 客户主体分类
	 */
	private String custType;
	/**
	 * 担保方式分类
	 */
	private String guaranteeType;

	/**
	 * 业务品种父类产品编号
	 */
	private String superProductNum;

	/**
	 * 业务品种父类产品名称
	 */
	private String superProductName;
	/**
	 * 业务品种描述
	 */
	private String productDescr;
	/**
	 * 业务品种状态
	 */
	private String productStatusCd;
	/**
	 * 产品额度占用方式
	 */
	private String limitUsedType;

	/**
	 * 用信系统
	 */
	private String creditSystem;

	/**
	 * 用信系统
	 */
	private String useSystem;
	/**
	 * 风险暴露类型
	 */
	private String riskExposureType;
	/**
	 * 1、信贷类产品；2、同业类产品；3、同有产品；
	 */
	private String productTarger;
	/**
	 * 表内外标志（CD000187）
	 */
	private String inoutTable;

	/**
	 * 经办人编号
	 */
	private String userNum;
	/**
	 * 经办机构编号
	 */
	@ApiModelProperty(value = "经办机构编号")
	private String orgNum;
	/**
	 * 最后修改人编号
	 */
	@ApiModelProperty(value = "最后修改人编号")
	private String updateUserNum;
	/**
	 * 最后修改人机构编号
	 */
	@ApiModelProperty(value = "最后修改人机构编号")
	private String updateOrgNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 启用日期
	 */
	private Date vaildTime;

	/**
	 * 是否删除
	 */
	private int isDeleted;


}
