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

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.param.entity.CrdProduct;

/**
 * 视图实体类
 *
 * @author git
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdProductVO对象", description = "CrdProductVO对象")
public class CrdProductVO extends CrdProduct {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务品种编号
	 */
	private String productNum;
	/**
	 * 业务品种名称
	 */
	private String productName;
	/**
	 * 业务品种描述
	 */
	private String productDescr;
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
	 * 业务种类名称
	 */
	private String productTypeName;
	/**
	 * 项目类型名称
	 */
	private String projectTypeName;
	/**
	 * 担保方式名称
	 */
	private String mainGuaranteeTypeName;



}
