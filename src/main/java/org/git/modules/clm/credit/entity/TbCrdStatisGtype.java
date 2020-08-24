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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 额度统计表-担保方式（历史+实时）实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@TableName("TB_CRD_STATIS_GTYPE")
@ApiModel(value = "TbCrdStatisGtype对象", description = "额度统计表-担保方式（历史+实时）")
public class TbCrdStatisGtype implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 统计ID
	 */
	@ApiModelProperty(value = "统计ID")
	@TableId("STATIS_ID")
	private String statisId;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;


	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;


	/**
	 * 批复数量
	 */
	@ApiModelProperty(value = "批复数量")
	@TableField("APPROVE_COUNT")
	private String approveCount;


	/**
	 * 授信敞口余额
	 */
	@ApiModelProperty(value = "授信敞口余额")
	@TableField("CREDIT_EXP_BALANCE")
	private String creditExpBalance;


	/**
	 * 贷款敞口余额
	 */
	@ApiModelProperty(value = "贷款敞口余额")
	@TableField("LOAN_EXP_BALANCE")
	private String loanExpBalance;


	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private String limitCredit;


	/**
	 * 可用额度
	 */
	@ApiModelProperty(value = "可用额度")
	@TableField("LIMIT_AVI")
	private String limitAvi;


	/**
	 * 已用敞口
	 */
	@ApiModelProperty(value = "已用敞口")
	@TableField("EXP_USED")
	private String expUsed;


	/**
	 * 可用敞口
	 */
	@ApiModelProperty(value = "可用敞口")
	@TableField("EXP_AVI")
	private String expAvi;
	/**
	 * 业务品种
	 */
	@ApiModelProperty(value = "业务品种")
	@TableField("PRODUCT_NUM")
	private String productNum;

	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "额度产品编号")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;

	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;

	/**
	 * 行业
	 */
	@ApiModelProperty(value = "行业")
	@TableField("INDUSTRY")
	private String industry;

	/**
	 * 客户类型
	 */
	@ApiModelProperty(value = "客户类型")
	@TableField("CUSTOMER_TYPE")
	private String customerType;

	/**
	 * 担保方式（CD000100）
	 */
	@ApiModelProperty(value = "担保方式（CD000100）")
	@TableField("GUARANTEE_TYPE")
	private String guaranteeType;

	/**
	 * 企业规模（CD000020）
	 */
	@ApiModelProperty(value = "企业规模（CD000020）")
	@TableField("UNIT_SCALE")
	private String unitScale;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;

}
