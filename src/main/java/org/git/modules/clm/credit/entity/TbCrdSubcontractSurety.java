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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 担保合同与押品关联关系表实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@TableName("TB_CRD_SUBCONTRACT_SURETY")
@ApiModel(value = "TbCrdSubcontractSurety对象", description = "担保合同与押品关联关系表")
public class TbCrdSubcontractSurety implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 关联ID
	 */
	@ApiModelProperty(value = "关联ID")
	@TableId(value = "RELATION_ID", type = IdType.UUID)
	private String relationId;
	/**
	 * 担保合同编号
	 */
	@ApiModelProperty(value = "担保合同编号")
	@TableField("SUBCONTRACT_NUM")
	private String subcontractNum;
	/**
	 * 抵质押物ID
	 */
	@ApiModelProperty(value = "抵质押物编号")
	@TableField("SURETY_NUM")
	private String suretyNum;
	/**
	 * 关联类型（1担保合同关联 2业务申请关联，）
	 */
	@ApiModelProperty(value = "关联类型（1担保合同关联 2业务申请关联，）")
	@TableField("REL_TYPE")
	private String relType;
	/**
	 * 占用金额
	 */
	@ApiModelProperty(value = "占用金额")
	@TableField("SURETY_AMT")
	private BigDecimal suretyAmt;
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
