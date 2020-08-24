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
package org.git.modules.clm.credit.vo;

import org.git.modules.clm.credit.entity.TbCrdSubcontractCon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 担保合同与业务合同关联关系视图实体类
 *
 * @author git
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TbCrdSubcontractConVO对象", description = "担保合同与业务合同关联关系")
public class TbCrdSubcontractConVO extends TbCrdSubcontractCon {
	private static final long serialVersionUID = 1L;

}