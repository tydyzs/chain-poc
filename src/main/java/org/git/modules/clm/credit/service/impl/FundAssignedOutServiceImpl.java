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

import org.git.modules.clm.credit.entity.FundAssignedOut;
import org.git.modules.clm.credit.vo.FundAssignedOutVO;
import org.git.modules.clm.credit.mapper.FundAssignedOutMapper;
import org.git.modules.clm.credit.service.IFundAssignedOutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资金系统额度转让-转出 服务实现类
 *
 * @author git
 * @since 2020-01-15
 */
@Service
public class FundAssignedOutServiceImpl extends ServiceImpl<FundAssignedOutMapper, FundAssignedOut> implements IFundAssignedOutService {

	@Override
	public IPage<FundAssignedOutVO> selectFundAssignedOutPage(IPage<FundAssignedOutVO> page, FundAssignedOutVO fundAssignedOut) {
		return page.setRecords(baseMapper.selectFundAssignedOutPage(page, fundAssignedOut));
	}

}
