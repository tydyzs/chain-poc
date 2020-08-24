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
package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmWarnInfoHis;

import java.math.BigDecimal;

/**
 * 限额预警历史信息表（一年以上）视图实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmWarnInfoHisVO对象", description = "限额预警历史信息表（一年以上）")
public class RcmWarnInfoHisVO extends RcmWarnInfoHis {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据类型： 1.非历史数据 2.历史数据
	 */
	@ApiModelProperty(value = "数据类型")
	private String isHis = "2";

	/**
	 * 限额指标名称
	 */
	@ApiModelProperty(value = "限额指标名称")
	@TableId("QUOTA_INDEX_NAME")
	private String quotaIndexName;

	/**
	 * 历史触发次数
	 */
	@ApiModelProperty(value = "历史触发次数")
	@TableId("HIS_FREQUENCY")
	private String hisFrequency;

	/**
	 * 限额指标类型
	 */
	@ApiModelProperty(value = "限额指标类型")
	@TableId("QUOTA_INDEX_TYPE")
	private String quotaIndexType;

	/**
	 * 限额已用比例
	 */
	@ApiModelProperty(value = "限额已用比例")
	@TableId("quota_used_ratio")
	private BigDecimal quotaUsedRatio;

	/**
	 * 限额可用比例
	 */
	@ApiModelProperty(value = "限额可用比例")
	@TableId("quota_free_ratio")
	private BigDecimal quotaFreeRatio;

	/**
	 * 限额生效日期
	 */
	@ApiModelProperty(value = "限额生效日期")
	@TableId("start_date")
	private String startDate;

	/**
	 * 限额失效日期
	 */
	@ApiModelProperty(value = "限额失效日期")
	@TableId("invalid_date")
	private String invalidDate;

	/**
	 * 限额状态
	 */
	@ApiModelProperty(value = "限额状态")
	@TableId("quota_state")
	private String quotaState;

}
