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

import org.git.modules.clm.rcm.entity.RcmAnonymityRiskExposureRpt;
import org.git.modules.clm.rcm.vo.RcmAnonymityRiskExposureRptVO;
import org.git.modules.clm.rcm.mapper.RcmAnonymityRiskExposureRptMapper;
import org.git.modules.clm.rcm.service.IRcmAnonymityRiskExposureRptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 匿名客户风险暴露金额变动图 服务实现类
 *
 * @author git
 * @since 2019-12-11
 */
@Service
public class RcmAnonymityRiskExposureRptServiceImpl extends ServiceImpl<RcmAnonymityRiskExposureRptMapper, RcmAnonymityRiskExposureRpt> implements IRcmAnonymityRiskExposureRptService {

	@Override
	public IPage<RcmAnonymityRiskExposureRptVO> selectRcmAnonymityRiskExposureRptPage(IPage<RcmAnonymityRiskExposureRptVO> page, RcmAnonymityRiskExposureRptVO rcmAnonymityRiskExposureRpt) {
		return page.setRecords(baseMapper.selectRcmAnonymityRiskExposureRptPage(page, rcmAnonymityRiskExposureRpt));
	}

}
