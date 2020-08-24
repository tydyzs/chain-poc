package org.git.modules.clm.credit.vo;/*
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

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.git.modules.clm.credit.entity.TbCrdApprove;

/**
 * 批复信息表视图实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TbCrdApproveVO对象", description = "批复信息表")
public class TbCrdApproveVO extends TbCrdApprove {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String customerName;

	/**
	 * 业务品种名称
	 */
	@ApiModelProperty(value = "业务品种名称")
	private String productName;

	/**
	 * 项目协议编号
	 */
	@ApiModelProperty(value = "项目协议编号")
	private String projectNum;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;

	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;

	@ApiModelProperty(value = "业务种类 CD000061")
	private String productTypeName;

	@ApiModelProperty(value = "主担保方式 CD000100")
	private String mainGuaranteeTypeName;

	@ApiModelProperty(value = "业务类型 CD000170")
	private String bizTypeName;

	@ApiModelProperty(value = "是否低风险 CD000167")
	private String isLowRiskName;

	@ApiModelProperty(value = "低风险业务类别 CD000186")
	private String lowRiskTypeName;

	@ApiModelProperty(value = "期限单位 CD000169")
	private String termUnitName;

	@ApiModelProperty(value = "批复状态 CD000109")
	private String approveStatusName;
}
