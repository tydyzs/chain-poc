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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金客户状态维护明细实体类
 *
 * @author git
 * @since 2019-11-28
 */
@Data
@TableName("TB_FUND_ADMIT_DETAIL")
@ApiModel(value = "FundAdmitDetail对象", description = "资金客户状态维护明细")
public class FundAdmitDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键ID
	*/
		@ApiModelProperty(value = "主键ID")
		@TableId(value = "EVENT_DETAIL_ID", type = IdType.UUID)
	private String eventDetailId;
	/**
	 * 主表id
	 */
	@ApiModelProperty(value = "主表id")
	@TableField("EVENT_MAIN_ID")
	private String eventMainId;
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
	* ECIF客户号
	*/
		@ApiModelProperty(value = "ECIF客户号")
		@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	* 额度状态
	*/
		@ApiModelProperty(value = "额度状态")
		@TableField("CRD_STATUS")
	private String crdStatus;
	/**
	* 三级额度产品
	*/
		@ApiModelProperty(value = "三级额度产品")
		@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	* 客户准入标识
	*/
		@ApiModelProperty(value = "客户准入标识")
		@TableField("CRD_ADMIT_FLAG")
	private String crdAdmitFlag;
	/**
	* 冻结申请日期
	*/
		@ApiModelProperty(value = "冻结申请日期")
		@TableField("FROZEN_REQ_DATE")
	private String frozenReqDate;
	/**
	* 冻结开始日期
	*/
		@ApiModelProperty(value = "冻结开始日期")
		@TableField("FROZEN_BEGIN_DATE")
	private String frozenBeginDate;
	/**
	* 冻结结束日期
	*/
		@ApiModelProperty(value = "冻结结束日期")
		@TableField("FROZEN_END_DATE")
	private String frozenEndDate;

	/**
	 * 授信机构
	 */
	@ApiModelProperty(value = "授信机构")
	@TableField("CRD_GRANT_ORG_NUM")
	private String crdGrantOrgNum;

}
