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
package org.git.modules.clm.param.vo;

import org.git.modules.clm.param.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author git
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProductVO对象", description = "ProductVO对象")
public class ProductVO extends Product {
	private static final long serialVersionUID = 1L;
	/**
	 * 创建用户名称
	 */
	private String createUserName;
	/**
	 * 创建机构名称
	 */
	private String createDeptName;
	/**
	 * 创建时间格式化
	 */
	private String createTimeStr;
	/**
	 * 更新时间格式化
	 */
	private String updateTimeStr;

	/**
	 * 业务种类-名称
	 */
	private String productTypeName;

	/**
	 * 产品状态-名称
	 */
	private String productStatusCdName;

	/**
	 * 产品额度占用方式-名称
	 */
	private String limitUsedTypeName;

	/**
	 * 风险暴露类型-名称
	 */
	private String riskExposureTypeName;

	/**
	 * 表内外标志（CD000187）-名称
	 */
	private String inoutTableName;
}
