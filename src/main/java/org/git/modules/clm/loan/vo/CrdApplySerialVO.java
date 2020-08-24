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
package org.git.modules.clm.loan.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.loan.entity.CrdApplySerial;

/**
 * 额度使用流水视图实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdApplySerialVO对象", description = "额度使用流水")
public class CrdApplySerialVO extends CrdApplySerial {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务品种编号
	 */
	@ApiModelProperty(value = "业务品种编号")
	private String busiPrdNum;


	/**
	 * 额度产品名称
	 */
	@ApiModelProperty(value = "额度产品名称")
	private String crdProductName;

	/**
	 * 经办系统名称
	 */
	@ApiModelProperty(value = "经办系统名称")
	private String terminalName;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;

	@ApiModelProperty(value = "交易类型 CD000087")
	private String tranTypeCdName;

	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;

	@ApiModelProperty(value = "是否串用 CD000161")
	private String isMixName;

}
