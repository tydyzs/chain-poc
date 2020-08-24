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
 * 地址信息实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_ADDRESS_INFO")
@ApiModel(value = "CsmAddressInfo对象", description = "地址信息")
public class CsmAddressInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物理地址ID
	 */
	@ApiModelProperty(value = "物理地址ID")
	@TableId(value = "ADDR_ID", type = IdType.UUID)
	private String addrId;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	private String customerNum;

	/**
	 * 联系类型(CD000031)
	 */
	@ApiModelProperty(value = "联系类型(CD000031)")
	private String connType;
	/**
	 * 联系地址国家/地区(CD000001)
	 */
	@ApiModelProperty(value = "联系地址国家/地区(CD000001)")
	private String counRegi;
	/**
	 * 省代码(CD000002)
	 */
	@ApiModelProperty(value = "省代码(CD000002)")
	private String province;
	/**
	 * 市代码(CD000002)
	 */
	@ApiModelProperty(value = "市代码(CD000002)")
	private String city;
	/**
	 * 县代码(CD000002)
	 */
	@ApiModelProperty(value = "县代码(CD000002)")
	private String county;
	/**
	 * 街道地址
	 */
	@ApiModelProperty(value = "街道地址")
	private String street;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String detailAddr;
	/**
	 * 英文地址
	 */
	@ApiModelProperty(value = "英文地址")
	private String engAddr;
	/**
	 * 邮政编码
	 */
	@ApiModelProperty(value = "邮政编码")
	private String postCode;
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
