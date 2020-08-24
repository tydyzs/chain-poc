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
package org.git.modules.clm.rcm.service.impl;

import org.git.modules.clm.rcm.entity.RcmRelationQuotaBaseRpt;
import org.git.modules.clm.rcm.vo.RcmRelationQuotaBaseRptVO;
import org.git.modules.clm.rcm.mapper.RcmRelationQuotaBaseRptMapper;
import org.git.modules.clm.rcm.service.IRcmRelationQuotaBaseRptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 关联客户授信集中度简表 服务实现类
 *
 * @author git
 * @since 2019-12-11
 */
@Service
public class RcmRelationQuotaBaseRptServiceImpl extends ServiceImpl<RcmRelationQuotaBaseRptMapper, RcmRelationQuotaBaseRpt> implements IRcmRelationQuotaBaseRptService {

	@Override
	public IPage<RcmRelationQuotaBaseRptVO> selectRcmRelationQuotaBaseRptPage(IPage<RcmRelationQuotaBaseRptVO> page, RcmRelationQuotaBaseRptVO rcmRelationQuotaBaseRpt) {
		return page.setRecords(baseMapper.selectRcmRelationQuotaBaseRptPage(page, rcmRelationQuotaBaseRpt));
	}

}
