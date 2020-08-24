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
package org.git.modules.clm.front.dto.jxrcb.fund;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.FundTransferIn;
import org.git.modules.clm.credit.entity.FundTransferMain;
import org.git.modules.clm.credit.entity.FundTransferOut;

import java.io.Serializable;
import java.util.List;

/**
 * 204接口事件落地实体类
 *
 * @author liuye
 * @since 2019-12-09
 */
@Data
public class FundCreditTransferDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private FundTransferMain fundTransferMain;

	private List<FundTransferOut> fundTransferOuts;

	private List<FundTransferIn> fundTransferIns;
}
