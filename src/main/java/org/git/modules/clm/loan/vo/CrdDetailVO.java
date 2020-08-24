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
import org.git.modules.clm.loan.entity.CrdDetail;

/**
 * 额度明细表（客户+三级额度产品+机构）视图实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdDetailVO对象", description = "额度明细表（客户+三级额度产品+机构）")
public class CrdDetailVO extends CrdDetail {
	private static final long serialVersionUID = 1L;

	/**
	 *业务品种编号
	 */
	@ApiModelProperty(value = "业务品种编号")
	private String productNum;

	/**
	 * 业务品种名称
	 */
	@ApiModelProperty(value = "业务品种名称")
	private String productName;

	/**
	 * 额度产品编号
	 */
	@ApiModelProperty(value = "额度产品编号")
	private String crdProductNum;

	/**
	 * 额度产品名称
	 */
	@ApiModelProperty(value = "额度产品名称")
	private String crdProductName;

	/**
	 * 额度品种类型
	 */
	@ApiModelProperty(value = "额度品种类型 CD000211")
	private String crdProductType;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;

	@ApiModelProperty(value = "额度类型 CD000211")
	private String crdProductTypeName;

	@ApiModelProperty(value = "是否可循环 CD000167")
	private String isCycleName;
}
