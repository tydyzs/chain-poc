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
package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.FundGrantDetail;
import org.git.modules.clm.credit.vo.FundGrantDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditExtensionRequestDTO;

/**
 * 资金授信事件明细 服务类
 *
 * @author liuye
 * @since 2019-11-15
 */
public interface IFundGrantDetailService extends IService<FundGrantDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundGrantDetail
	 * @return
	 */
	IPage<FundGrantDetailVO> selectFundGrantDetailPage(IPage<FundGrantDetailVO> page, FundGrantDetailVO fundGrantDetail);

}
