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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 个人客户基本信息实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_INDIVIDUAL")
@ApiModel(value = "CsmIndividual对象", description = "个人客户基本信息")
public class CsmIndividual implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableId(value = "CUSTOMER_NUM", type = IdType.UUID)
	private String customerNum;

	/**
	 * 客户类型(CD000212)
	 */
	@ApiModelProperty(value = "客户类型(CD000212)")
	private String customerType;
	/**
	 * 客户状态(CD000032)
	 */
	@ApiModelProperty(value = "客户状态(CD000032)")
	private String custStatus;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String custName;
	/**
	 * 客户拼音名称
	 */
	@ApiModelProperty(value = "客户拼音名称")
	private String pinyinName;
	/**
	 * 性别(CD000004)
	 */
	@ApiModelProperty(value = "性别(CD000004)")
	private String gender;
	/**
	 * 国籍(CD000001)
	 */
	@ApiModelProperty(value = "国籍(CD000001)")
	private String nation;
	/**
	 * 民族(CD000005)
	 */
	@ApiModelProperty(value = "民族(CD000005)")
	private String race;
	/**
	 * 出生日期
	 */
	@ApiModelProperty(value = "出生日期")
	private String birthDate;
	/**
	 * 政治面貌(CD000006)
	 */
	@ApiModelProperty(value = "政治面貌(CD000006)")
	private String poliStatus;
	/**
	 * 婚姻状况(CD000007)
	 */
	@ApiModelProperty(value = "婚姻状况(CD000007)")
	private String marrStatus;
	/**
	 * 从业状况(CD000008)
	 */
	@ApiModelProperty(value = "从业状况(CD000008)")
	private String empStat;
	/**
	 * 户籍所在城市(CD000002)
	 */
	@ApiModelProperty(value = "户籍所在城市(CD000002)")
	private String houseCicts;
	/**
	 * 户籍类型(CD000215)
	 */
	@ApiModelProperty(value = "户籍类型(CD000215)")
	private String houseType;
	/**
	 * 健康状况(CD000009)
	 */
	@ApiModelProperty(value = "健康状况(CD000009)")
	private String healthyStatus;
	/**
	 * 家庭成员人数
	 */
	@ApiModelProperty(value = "家庭成员人数")
	private String familyNum;
	/**
	 * 最高学历(CD000011)
	 */
	@ApiModelProperty(value = "最高学历(CD000011)")
	private String education;
	/**
	 * 最高学位(CD000010)
	 */
	@ApiModelProperty(value = "最高学位(CD000010)")
	private String highAcadeDegree;
	/**
	 * 客户经理编号
	 */
	@ApiModelProperty(value = "客户经理编号")
	private String custManagerNo;
	/**
	 * 工作单位全称
	 */
	@ApiModelProperty(value = "工作单位全称")
	private String workUnitName;
	/**
	 * 工作单位所属行业(CD000015)
	 */
	@ApiModelProperty(value = "工作单位所属行业(CD000015)")
	private String workIndustry;
	/**
	 * 工作单位性质(CD000026)
	 */
	@ApiModelProperty(value = "工作单位性质(CD000026)")
	private String unitCharacter;
	/**
	 * 职务(CD000013)
	 */
	@ApiModelProperty(value = "职务(CD000013)")
	private String duty;
	/**
	 * 职业1(CD000012)
	 */
	@ApiModelProperty(value = "职业1(CD000012)")
	private String occupation1;
	/**
	 * 职业2(CD000012)
	 */
	@ApiModelProperty(value = "职业2(CD000012)")
	private String occupation2;
	/**
	 * 职业3(CD000012)
	 */
	@ApiModelProperty(value = "职业3(CD000012)")
	private String occupation3;
	/**
	 * 职业说明
	 */
	@ApiModelProperty(value = "职业说明")
	private String occupationExplain;
	/**
	 * 职称(CD000014)
	 */
	@ApiModelProperty(value = "职称(CD000014)")
	private String techTiyleLevel;
	/**
	 * 是否代发工资(CD000167)
	 */
	@ApiModelProperty(value = "是否代发工资(CD000167)")
	private String payCreditFlag;
	/**
	 * 个人客户黑名单标志
	 */
	@ApiModelProperty(value = "个人客户黑名单标志")
	private String isBlankFlag;
	/**
	 * 员工标志
	 */
	@ApiModelProperty(value = "员工标志")
	private String employeeFlag;
	/**
	 * 是否涉农
	 */
	@ApiModelProperty(value = "是否涉农")
	private String agriRelatedInd;
	/**
	 * 是否本行高管
	 */
	@ApiModelProperty(value = "是否本行高管")
	private String seiorExecuInd;
	/**
	 * 我行关联方标志
	 */
	@ApiModelProperty(value = "我行关联方标志")
	private String relPartyInd;
	/**
	 * 个人年收入
	 */
	@ApiModelProperty(value = "个人年收入")
	private BigDecimal perYearIncome;
	/**
	 * 税收居民身份(CD000017)
	 */
	@ApiModelProperty(value = "税收居民身份(CD000017)")
	private String taxResType;
	/**
	 * 居住状况(CD000035)
	 */
	@ApiModelProperty(value = "居住状况(CD000035)")
	private String residSituat;
	/**
	 * 客户等级(CD000216)
	 */
	@ApiModelProperty(value = "客户等级(CD000216)")
	private String custGrade;
	/**
	 * 客户满意度(CD000217)
	 */
	@ApiModelProperty(value = "客户满意度(CD000217)")
	private String custSatis;
	/**
	 * 个人总资产
	 */
	@ApiModelProperty(value = "个人总资产")
	private BigDecimal perTotalAsset;
	/**
	 * 个人总负债
	 */
	@ApiModelProperty(value = "个人总负债")
	private BigDecimal liabilityBalance;
	/**
	 * 个人资产类型(CD000046)
	 */
	@ApiModelProperty(value = "个人资产类型(CD000046)")
	private String perAssestType;
	/**
	 * 个人负债类型(CD000045)
	 */
	@ApiModelProperty(value = "个人负债类型(CD000045)")
	private String perLiabType;
	/**
	 * 居民/非居民(CD000167)
	 */
	@ApiModelProperty(value = "居民/非居民(CD000167)")
	private String residNonResid;
	/**
	 * 开户日期
	 */
	@ApiModelProperty(value = "开户日期")
	private String createdTs;
	/**
	 * 开户机构
	 */
	@ApiModelProperty(value = "开户机构")
	private String lastUpdatedOrg;
	/**
	 * 开户柜员
	 */
	@ApiModelProperty(value = "开户柜员")
	private String lastUpdatedTe;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Timestamp updateTime;


}
