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
package org.git.modules.clm.credit.vo;

import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 额度授信流水视图实体类
 *
 * @author liuye
 * @since 2019-11-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdGrantingSerialVO对象", description = "额度授信流水")
public class CrdGrantingSerialVO extends CrdGrantingSerial {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构号")
	private String orgNum;

	@ApiModelProperty(value = "额度产品")
	private String crdProductName;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "授信机构 SysCache")
	private String crdGrantOrgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String crdCurrencyCdName;

	@ApiModelProperty(value = "交易类型 CD000087")
	private String tranTypeCdName;

	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;

	@ApiModelProperty(value = "额度状态 CrdStatus")
	private String crdStatusName;

	@ApiModelProperty(value = "客户准入 crdAdmitFlag")
	private String crdAdmitFlagName;
}
