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
package org.git.modules.clm.loan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 额度主表（客户+二级额度产品+机构）实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@TableName("TB_CRD_MAIN")
@ApiModel(value = "CrdMain对象", description = "额度主表（客户+二级额度产品+机构）")
public class CrdMain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 额度编号
	 */
	@ApiModelProperty(value = "二级额度编号")
	@TableId("CRD_MAIN_NUM")
	private String crdMainNum;
	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "二级额度产品")
	@TableField("CRD_MAIN_PRD")
	private String crdMainPrd;

	/**
	 * 授信机构
	 */
	@ApiModelProperty(value = "授信机构")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;
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
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	@TableField("EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	/**
	 * 额度状态
	 */
	@ApiModelProperty(value = "额度状态")
	@TableField("CREDIT_STATUS")
	private String creditStatus;
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
	 * 预占用额度
	 */
	@ApiModelProperty(value = "预占用额度")
	@TableField("LIMIT_PRE")
	private BigDecimal limitPre;
	/**
	 * 预占用敞口
	 */
	@ApiModelProperty(value = "预占用敞口")
	@TableField("EXP_PRE")
	private BigDecimal expPre;
	/**
	 * 冻结额度
	 */
	@ApiModelProperty(value = "冻结额度")
	@TableField("LIMIT_FROZEN")
	private BigDecimal limitFrozen;
	/**
	 * 冻结敞口
	 */
	@ApiModelProperty(value = "冻结敞口")
	@TableField("EXP_FROZEN")
	private BigDecimal expFrozen;
	/**
	 * 额度生效日
	 */
	@ApiModelProperty(value = "额度生效日")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 额度到期日
	 */
	@ApiModelProperty(value = "额度到期日")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 冻结日期
	 */
	@ApiModelProperty(value = "冻结日期")
	@TableField("FROZEN_DATE")
	private String frozenDate;
	/**
	 * 终止日期
	 */
	@ApiModelProperty(value = "终止日期")
	@TableField("OVER_DATE")
	private String overDate;
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
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;


	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("ORG_NUM")
	private String orgNum;

	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;

	/**
	 * 圈存额度
	 */
	@ApiModelProperty(value = "圈存额度")
	@TableField("LIMIT_EARMARK")
	private BigDecimal limitEarmark;

	/**
	 * 圈存已用额度
	 */
	@ApiModelProperty(value = "圈存已用额度")
	@TableField("LIMIT_EARMARK_USED")
	private BigDecimal limitEarmarkUsed;

	/**
	 * 圈存开始日
	 */
	@ApiModelProperty(value = "圈存开始日")
	@TableField("EARMARK_BEGIN_DATE")
	private String earmarkBeginDate;

	/**
	 * 圈存到期日
	 */
	@ApiModelProperty(value = "圈存到期日")
	@TableField("EARMARK_END_DATE")
	private String earmarkEndDate;

}
