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
 * 公司客户基本信息实体类
 *
 * @author git
 * @since 2019-11-27
 */
@Data
@TableName("TB_CSM_CORPORATION")
@ApiModel(value = "CsmCorporation对象", description = "公司客户基本信息")
public class CsmCorporation implements Serializable {

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
	 * 同业客户简易标志(1 -  简易信息 2 -  详细信息 )
	 */
	@ApiModelProperty(value = "同业客户简易标志(1 -  简易信息 2 -  详细信息 )")
	private String bankCustFlag;
	/**
	 * 客户经理编号
	 */
	@ApiModelProperty(value = "客户经理编号")
	private String custManagerNo;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String custName;
	/**
	 * 组织中文简称
	 */
	@ApiModelProperty(value = "组织中文简称")
	private String orgShortName;
	/**
	 * 客户英文名称
	 */
	@ApiModelProperty(value = "客户英文名称")
	private String custEngName;
	/**
	 * 组织英文简称
	 */
	@ApiModelProperty(value = "组织英文简称")
	private String orgRngShortName;
	/**
	 * 国民经济部门(CD000039)
	 */
	@ApiModelProperty(value = "国民经济部门(CD000039)")
	private String nationalEconomyType;
	/**
	 * 国民经济行业1(CD000015)
	 */
	@ApiModelProperty(value = "国民经济行业1(CD000015)")
	private String nationalEconomyDepart1;
	/**
	 * 国民经济行业2(CD000015)
	 */
	@ApiModelProperty(value = "国民经济行业2(CD000015)")
	private String nationalEconomyDepart2;
	/**
	 * 国民经济行业3(CD000015)
	 */
	@ApiModelProperty(value = "国民经济行业3(CD000015)")
	private String nationalEconomyDepart3;
	/**
	 * 国民经济行业4(CD000015)
	 */
	@ApiModelProperty(value = "国民经济行业4(CD000015)")
	private String nationalEconomyDepart4;
	/**
	 * 成立日期
	 */
	@ApiModelProperty(value = "成立日期")
	private String foundDate;
	/**
	 * 注册资本
	 */
	@ApiModelProperty(value = "注册资本")
	private BigDecimal regCapital;
	/**
	 * 注册资本币种(CD000019)
	 */
	@ApiModelProperty(value = "注册资本币种(CD000019)")
	private String regCptlCurr;
	/**
	 * 企业规模(CD000020)
	 */
	@ApiModelProperty(value = "企业规模(CD000020)")
	private String unitScale;
	/**
	 * 企业员工人数
	 */
	@ApiModelProperty(value = "企业员工人数")
	private String empNumber;
	/**
	 * 是否代发工资(CD000167)
	 */
	@ApiModelProperty(value = "是否代发工资(CD000167)")
	private String payCreditFlag;


	/**
	 * 国别代码(CD000001)
	 */
	@ApiModelProperty(value = "国别代码(CD000001)")
	private String countryCode;
	/**
	 * 机构信用代码
	 */
	@ApiModelProperty(value = "机构信用代码")
	private String creditOrganCode;

	/**
	 * 经营范围
	 */
	@ApiModelProperty(value = "经营范围")
	private String businScope;

	/**
	 * 同业客户类型1(CD000218)
	 */
	@ApiModelProperty(value = "同业客户类型1(CD000218)")
	private String bankCustType1;
	/**
	 * 同业客户类型2(CD000218)
	 */
	@ApiModelProperty(value = "同业客户类型2(CD000218)")
	private String bankCustType2;

	/**
	 * 人行现代化支付系统银行行号
	 */
	@ApiModelProperty(value = "人行现代化支付系统银行行号")
	private String bankPaySysNum;

	/**
	 * 集团授信标志(CD000167)
	 */
	@ApiModelProperty(value = "集团授信标志(CD000167)")
	private String groupCreditIndicator;
	/**
	 * 本行黑名单标志(CD000167)
	 */
	@ApiModelProperty(value = "本行黑名单标志(CD000167)")
	private String bankIndicator;

	/**
	 * 我行关联方标志(CD000167)
	 */
	@ApiModelProperty(value = "我行关联方标志(CD000167)")
	private String relPartyInd;
	/**
	 * SWIFT代码
	 */
	@ApiModelProperty(value = "SWIFT代码")
	private String swiftCode;

	/**
	 * 对公客户类型(1- 企业 2- 公司 3- 其他  4- 免识别客户)
	 */
	@ApiModelProperty(value = "对公客户类型(1- 企业 2- 公司 3- 其他  4- 免识别客户)")
	private String beneCustType;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;
	/**
	 * 进入ECIF日期
	 */
	@ApiModelProperty(value = "进入ECIF日期")
	private String createdTs;
	/**
	 * 最后操作机构
	 */
	@ApiModelProperty(value = "最后操作机构")
	private String lastUpdatedOrg;
	/**
	 * 最后操作柜员
	 */
	@ApiModelProperty(value = "最后操作柜员")
	private String lastUpdatedTe;

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
