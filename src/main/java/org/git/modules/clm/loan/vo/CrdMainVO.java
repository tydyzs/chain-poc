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

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.loan.entity.CrdMain;

/**
 * edu
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdMainVO对象", description = "额度主表（客户+二级额度产品+机构）")
public class CrdMainVO extends CrdMain {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String custName;

	/**
	 * 机构信用代码
	 */
	@ApiModelProperty(value = "机构信用代码")
	private String creditOrganCode;

	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String certNum;

	/**
	 *证件类型
	 */
	@ApiModelProperty(value = "证件类型")
	private String certType;

	/**
	 *额度产品名称
	 */
	@ApiModelProperty(value = "额度产品名称")
	private String crdProductName;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;

}
