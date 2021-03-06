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
package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.rcm.entity.RcmConfigParaHis;

/**
 * 限额详细信息表视图实体类
 *
 * @author liuye
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RcmConfigVO对象", description = "限额详细信息表")
public class RcmQuotaManagerHisVO extends RcmQuotaManagerVO {
	private static final long serialVersionUID = 1L;

	/**
	 * 限额参数历史信息
	 */
	@ApiModelProperty(value = "限额参数历史信息")
	IPage<RcmConfigParaHis> rcmConfigParaHis;
}
