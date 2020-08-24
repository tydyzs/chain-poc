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
package org.git.modules.clm.loan.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;

import javax.print.DocFlavor;

/**
 * 业务凭证信息表视图实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdBusiCertInfoVO对象", description = "业务凭证信息表")
public class CrdBusiCertInfoVO extends CrdBusiCertInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableId("ORG_NUM")
	private String orgNum;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableId("CUSTOMER_NUM")
	private String customerNum;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableId("CUSTOMER_NAME")
	private String customerName;

	/**
	 * 额度产品名称
	 */
	@ApiModelProperty(value = "额度产品名称")
	@TableId("crd_product_name")
	private String crdProductName;

	/**
	 * 额度产品名称
	 */
	@ApiModelProperty(value = "业务产品名称")
	@TableId("product_name")
	private String productName;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String certCurrencyCdName;

	@ApiModelProperty(value = "凭证状态 CD000201")
	private String certStatusName;

	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;
}
