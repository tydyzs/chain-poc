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
import java.time.LocalDateTime;

/**
 * 额度明细表（客户+三级额度产品+机构）实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@TableName("TB_CRD_DETAIL")
@ApiModel(value = "CrdDetail对象", description = "额度明细表（客户+三级额度产品+机构）")
public class CrdDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 额度明细编号
	 */
	@ApiModelProperty(value = "三级额度编号")
	@TableId("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 额度编号
	 */
	@ApiModelProperty(value = "二级额度编号")
	@TableField("CRD_MAIN_NUM")
	private String crdMainNum;
	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "额度产品编号")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;

	/**
	 * 额度类型
	 */
	@ApiModelProperty(value = "额度类型（CD000211）")
	@TableField("CRD_PRODUCT_TYPE")
	private String crdProductType;

	/**
	 * 授信机构号
	 */
	@ApiModelProperty(value = "授信机构号")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 0：禁止 1：准入
	 */
	@ApiModelProperty(value = "0：禁止 1：准入")
	@TableField("CRD_ADMIT_FLAG")
	private String crdAdmitFlag;
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
	private String exchangeRate;
	/**
	 * 额度起期
	 */
	@ApiModelProperty(value = "额度起期")
	@TableField("BEGIN_DATE")
	private String beginDate;
	/**
	 * 额度止期
	 */
	@ApiModelProperty(value = "额度止期")
	@TableField("END_DATE")
	private String endDate;
	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("LIMIT_CREDIT")
	private BigDecimal limitCredit;
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
	@TableField("LIMIT_USED")
	private BigDecimal limitUsed;
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
	 * 是否可循环
	 */
	@ApiModelProperty(value = "是否可循环")
	@TableField("IS_CYCLE")
	private String isCycle;
	/**
	 * 是否可串用
	 */
	@ApiModelProperty(value = "是否可串用")
	@TableField("IS_MIX")
	private String isMix;
	/**
	 * 串用额度
	 */
	@ApiModelProperty(value = "串用额度")
	@TableField("MIX_CREDIT")
	private BigDecimal mixCredit;
	/**
	 * 串用已用
	 */
	@ApiModelProperty(value = "串用已用")
	@TableField("MIX_USED")
	private BigDecimal mixUsed;

	/**
	 * 串用说明
	 */
	@ApiModelProperty(value = "串用说明")
	@TableField("MIXREMARK")
	private String mixremark;
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
	 * 终结日期
	 */
	@ApiModelProperty(value = "终结日期")
	@TableField("CLOSE_DATE")
	private String closeDate;
	/**
	 * 终结原因
	 */
	@ApiModelProperty(value = "终结原因")
	@TableField("CLOSE_REASON")
	private String closeReason;
	/**
	 * 续作标志
	 */
	@ApiModelProperty(value = "续作标志")
	@TableField("IS_CONTINUE")
	private String isContinue;
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

}
