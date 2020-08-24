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
package org.git.modules.clm.param.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.param.entity.TbParBorrow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 串用组信息表视图实体类
 *
 * @author git
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TbParBorrowVO对象", description = "串用组信息表")
public class TbParBorrowVO extends TbParBorrow {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "串用组类型名称")
	private String lstgTypeName;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "机构名称")
	private String orgName;


	@ApiModelProperty(value = "额度产品名称")
	private String crdDetailPrdName;

	@ApiModelProperty(value = "串用额度产品名称")
	private String lstgPrdName;
}
