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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 额度授信流水实体类
 *
 * @author liuye
 * @since 2019-11-15
 */
@Data
@TableName("TB_CRD_GRANTING_SERIAL")
@ApiModel(value = "CrdGrantingSerial对象", description = "额度授信流水")
public class CrdGrantingSerial implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 授信流水id
	 */
	@ApiModelProperty(value = "授信流水id")
	@TableId("GRANTING_SERIAL_ID")
	private String grantingSerialId;
	/**
	 * 交易流水号
	 */
	@ApiModelProperty(value = "交易流水号")
	@TableField("TRAN_SEQ_SN")
	private String tranSeqSn;
	/**
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;
	/**
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	@TableField("BUSI_DEAL_NUM")
	private String busiDealNum;
	/**
	 * 交易类型
	 */
	@ApiModelProperty(value = "交易类型")
	@TableField("TRAN_TYPE_CD")
	private String tranTypeCd;
	/**
	 * 经办机构
	 */
	@ApiModelProperty(value = "经办机构")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;
	/**
	 * ecif客户编号
	 */
	@ApiModelProperty(value = "ecif客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 额度产品号
	 */
	@ApiModelProperty(value = "额度产品号")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	 * 额度编号
	 */
	@ApiModelProperty(value = "额度编号")
	@TableField("CRD_DETAIL_NUM")
	private String crdDetailNum;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CRD_CURRENCY_CD")
	private String crdCurrencyCd;
	/**
	 * 授信额度
	 */
	@ApiModelProperty(value = "授信额度")
	@TableField("CRD_DETAIL_AMT")
	private BigDecimal crdDetailAmt;
	/**
	 * 圈存额度
	 */
	@ApiModelProperty(value = "圈存额度")
	@TableField("CRD_EARK_AMT")
	private BigDecimal crdEarkAmt;
	/**
	 * 额度起期
	 */
	@ApiModelProperty(value = "额度起期")
	@TableField("CRD_BEGIN_DATE")
	private String crdBeginDate;
	/**
	 * 额度止期
	 */
	@ApiModelProperty(value = "额度止期")
	@TableField("CRD_END_DATE")
	private String crdEndDate;
	/**
	 * 额度状态
	 */
	@ApiModelProperty(value = "额度状态")
	@TableField("crd_status")
	private String crdStatus;
	/**
	 * 客户准入
	 */
	@ApiModelProperty(value = "客户准入")
	@TableField("crd_admit_flag")
	private String crdAdmitFlag;
	/**
	 * 经办系统
	 */
	@ApiModelProperty(value = "经办系统")
	@TableField("TRAN_SYSTEM")
	private String tranSystem;
	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField("UPDATE_TIME")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date updateTime;



}
