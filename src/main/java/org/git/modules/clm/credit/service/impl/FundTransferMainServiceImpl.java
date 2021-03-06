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
package org.git.modules.clm.credit.service.impl;

import org.git.modules.clm.credit.entity.FundTransferMain;
import org.git.modules.clm.credit.vo.FundTransferMainVO;
import org.git.modules.clm.credit.mapper.FundTransferMainMapper;
import org.git.modules.clm.credit.service.IFundTransferMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资金用信转让事件主表 服务实现类
 *
 * @author liuye
 * @since 2019-12-05
 */
@Service
public class FundTransferMainServiceImpl extends ServiceImpl<FundTransferMainMapper, FundTransferMain> implements IFundTransferMainService {

	@Override
	public IPage<FundTransferMainVO> selectFundTransferMainPage(IPage<FundTransferMainVO> page, FundTransferMainVO fundTransferMain) {
		return page.setRecords(baseMapper.selectFundTransferMainPage(page, fundTransferMain));
	}

}
