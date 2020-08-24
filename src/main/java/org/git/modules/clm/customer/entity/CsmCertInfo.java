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
package org.git.modules.clm.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 证件信息表实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_CERT_INFO")
@ApiModel(value = "CsmCertInfo对象", description = "证件信息表")
public class CsmCertInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 证件id
	 */
	@ApiModelProperty(value = "证件id")
	@TableId(value = "CERT_ID", type = IdType.UUID)
	private String certId;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	private String customerNum;

	/**
	 * 开户证件标识(00为辅证件01为主证件)
	 */
	@ApiModelProperty(value = "开户证件标识(00为辅证件01为主证件)")
	private String certFlag;
	/**
	 * 证件类型(CD000003)
	 */
	@ApiModelProperty(value = "证件类型(CD000003)")
	private String certType;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String certNum;
	/**
	 * 证件发放机关
	 */
	@ApiModelProperty(value = "证件发放机关")
	private String issuedInst;
	/**
	 * 证件生效日期(yyyy-MM-dd)
	 */
	@ApiModelProperty(value = "证件生效日期(yyyy-MM-dd)")
	private String certStartDate;
	/**
	 * 证件到期日期(yyyy-MM-dd)
	 */
	@ApiModelProperty(value = "证件到期日期(yyyy-MM-dd)")
	private String certEndDate;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Timestamp updateTime;


}
