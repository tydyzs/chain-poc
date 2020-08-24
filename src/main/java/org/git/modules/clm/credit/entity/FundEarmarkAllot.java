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
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金授信圈存分配实体类
 *
 * @author git
 * @since 2019-12-03
 */
@Data
@TableName("TB_FUND_EARMARK_ALLOT")
@ApiModel(value = "FundEarmarkAllot对象", description = "资金授信圈存分配")
public class FundEarmarkAllot implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键ID
	*/
		@ApiModelProperty(value = "主键ID")
		@TableId(value="EVENT_DETAIL_ID",type = IdType.UUID)
	private String eventDetailId;
	/**
	* 主表ID
	*/
		@ApiModelProperty(value = "主表ID")
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
	* 三级额度产品
	*/
		@ApiModelProperty(value = "三级额度产品")
		@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;
	/**
	* 币种
	*/
		@ApiModelProperty(value = "币种")
		@TableField("CRD_CURRENCY_CD")
	private String crdCurrencyCd;
	/**
	* 分配成员行
	*/
		@ApiModelProperty(value = "分配成员行")
		@TableField("CRD_ALLOT_ORG_NUM")
	private String crdAllotOrgNum;
	/**
	* 分配额度
	*/
		@ApiModelProperty(value = "分配额度")
		@TableField("CRD_ALLOC_AMT")
	private String crdAllocAmt;
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
