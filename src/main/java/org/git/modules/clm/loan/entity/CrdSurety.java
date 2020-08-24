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
package org.git.modules.clm.loan.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 抵质押物/保证人信息表实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@TableName("TB_CRD_SURETY")
@ApiModel(value = "CrdSurety对象", description = "抵质押物/保证人信息表")
public class CrdSurety implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 抵押物id
	 */
	@ApiModelProperty(value = "抵押物id")
	@TableId(value = "SURETY_ID", type = IdType.UUID)
	private String suretyId;

	/**
	 * 额度三级品种
	 */
	@ApiModelProperty(value = "额度三级品种")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 额度三级编号
	 */
	@ApiModelProperty(value = "额度三级编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 抵押物编号
	 */
	@ApiModelProperty(value = "抵押物编号")
	@TableField("PLEDGE_NUM")
	private String pledgeNum;
	/**
	 * 客户号
	 */
	@ApiModelProperty(value = "客户号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 担保方式（CD000100）
	 */
	@ApiModelProperty(value = "担保方式（CD000100）")
	@TableField("GUARANTEE_TYPE")
	private String guaranteeType;
	/**
	 * 担保物类型
	 */
	@ApiModelProperty(value = "担保物类型")
	@TableField("PLEDGE_TYPE")
	private String pledgeType;
	/**
	 * 担保物名称
	 */
	@ApiModelProperty(value = "担保物名称")
	@TableField("PLEDGE_NAME")
	private String pledgeName;
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
	@TableField("EXCHANGE_RETE")
	private BigDecimal exchangeRete;
	/**
	 * 评估价值
	 */
	@ApiModelProperty(value = "评估价值")
	@TableField("AMT_ASSES")
	private BigDecimal amtAsses;
	/**
	 * 权利价值
	 */
	@ApiModelProperty(value = "权利价值")
	@TableField("AMT_ACTUAL")
	private BigDecimal amtActual;
	/**
	 * 已用金额
	 */
	@ApiModelProperty(value = "已用金额")
	@TableField("AMT_USED")
	private BigDecimal amtUsed;
	/**
	 * 可用金额
	 */
	@ApiModelProperty(value = "可用金额")
	@TableField("AMT_AVI")
	private BigDecimal amtAvi;
	/**
	 * 抵质押率
	 */
	@ApiModelProperty(value = "抵质押率")
	@TableField("PLEDGE_RATE")
	private BigDecimal pledgeRate;
	/**
	 * 押品状态（CD000062）
	 */
	@ApiModelProperty(value = "押品状态（CD000062）")
	@TableField("PLEDGE_STAUS")
	private String pledgeStaus;
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
	 * 删除标志
	 */
	@ApiModelProperty(value = "删除标志")
	@TableField("DELETE_FLAG")
	@TableLogic
	private String deleteFlag;
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
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;
	/**
	 * 交易系统
	 */
	@ApiModelProperty(value = "交易系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;

}
