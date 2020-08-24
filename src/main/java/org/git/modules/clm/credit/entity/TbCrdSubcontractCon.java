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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 担保合同与业务合同关联关系实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@TableName("TB_CRD_SUBCONTRACT_CON")
@ApiModel(value = "TbCrdSubcontractCon对象", description = "担保合同与业务合同关联关系")
public class TbCrdSubcontractCon implements Serializable {

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
	 * 合同编号
	 */
	@ApiModelProperty(value = "合同编号")
	@TableField("CONTRACT_NUM")
	private String contractNum;
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
