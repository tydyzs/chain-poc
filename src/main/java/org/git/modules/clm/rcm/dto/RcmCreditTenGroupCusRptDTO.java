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
package org.git.modules.clm.rcm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.modules.clm.rcm.vo.RcmCreditTenGroupCusRptVO;
import org.git.modules.clm.rcm.vo.RcmQuotaInfoVO;

import java.util.List;

/**
 * 最大十家集团客户授信集中度明细表数据传输对象实体类
 *
 * @author zhouwj
 * @since 2020-02-25
 */
@Data
@ApiModel(value = "RcmCreditTenGroupCusRptDTO", description = "限额分析-最大十家集团客户授信集中度（前端展示）")
public class RcmCreditTenGroupCusRptDTO {

	/**
	 * 最大十家集团客户授信集中度
	 */
	@ApiModelProperty(value = "最大十家集团客户授信集中度")
	private List<RcmCreditTenGroupCusRptVO> creditTenGroupCusRptVOList;

	/**
	 * 限额管控参数信息
	 */
	@ApiModelProperty(value = "限额管控参数信息")
	private RcmQuotaInfoVO quotaInfoVO;

}
