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
package org.git.modules.clm.loan.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.loan.entity.CsmGroupMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * 集团成员表视图实体类
 *
 * @author git
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsmGroupMemberVO对象", description = "集团成员表")
public class CsmGroupMemberVO extends CsmGroupMember {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;


	/**
	 * 额度类型（CD000211）
	 */
	@ApiModelProperty(value = "额度类型（CD000211）")
	@TableField("CRD_PRODUCT_TYPE")
	private String crdProductType;


	/**
	 * 币种(CD000019)
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;


	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private BigDecimal limitCredit;


	/**
	 * 已用额度
	 */
	@ApiModelProperty(value = "已用额度")
	@TableField("LIMIT_USED")
	private BigDecimal limitUsed;


	/**
	 * 可用额度
	 */
	@ApiModelProperty(value = "可用额度")
	@TableField("LIMIT_AVI")
	private BigDecimal limitAvi;

	/**
	 * 已用额度
	 */
	@ApiModelProperty(value = "已用额度")
	@TableField("LIMIT_PRE")
	private BigDecimal limitPre;

	/**
	 * 授信敞口
	 */
	@ApiModelProperty(value = "授信敞口")
	@TableField("EXP_CREDIT")
	private BigDecimal expCredit;


	/**
	 * 已用敞口
	 */
	@ApiModelProperty(value = "已用敞口")
	@TableField("EXP_USED")
	private BigDecimal expUsed;


	/**
	 * 可用敞口
	 */
	@ApiModelProperty(value = "可用敞口")
	@TableField("EXP_AVI")
	private BigDecimal expAvi;


	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "主办机构")
	@TableField("ORG_NUM")
	private String orgNum;

	/**
	 * 主办客户经理
	 */
	@ApiModelProperty(value = "主办客户经理")
	@TableField("USER_NUM")
	private String userNum;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableField("CUSTOMER_NAME")
	private String customerName;

	/**
	 * 证件类型
	 */
	@ApiModelProperty(value = "证件类型")
	private String certType;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String certNum;

	/**
	 * 总授信额度
	 */
	@ApiModelProperty(value = "总授信额度")
	private BigDecimal limitCreditTotal;

	/**
	 * 总已用额度
	 */
	@ApiModelProperty(value = "总已用额度")
	private BigDecimal limitUsedTotal;

	/**
	 * 总可用额度
	 */
	@ApiModelProperty(value = "总可用额度")
	private BigDecimal limitAviTotal;

	/**
	 * 总授信敞口
	 */
	@ApiModelProperty(value = "总授信敞口")
	private BigDecimal expCreditTotal;

	/**
	 * 总已用敞口
	 */
	@ApiModelProperty(value = "总已用敞口")
	private BigDecimal expUsedTotal;

	/**
	 * 总可用敞口
	 */
	@ApiModelProperty(value = "总可用敞口")
	private BigDecimal expAviTotal;

	/**
	 * 成员客户编号
	 */
	@ApiModelProperty(value = "成员客户编号")
	@TableField("MEMBER_CUSTOMER_NUM")
	private String memberCustomerNum;

	/**
	 * 成员客户编号
	 */
	@ApiModelProperty(value = "集团成员客户名称")
	@TableField("member_customer_name")
	private String memberCustomerName;

	/**
	 * 集团客户名称
	 */
	@ApiModelProperty(value = "集团客户名称")
	@TableId("company_customer_name")
	private String companyCustomerName;

	/**
	 * 机构名
	 */
	@ApiModelProperty(value = "机构名")
	private String orgNumName;

	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种(CD000019)")
	private String currencyCdName;

	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;
}
