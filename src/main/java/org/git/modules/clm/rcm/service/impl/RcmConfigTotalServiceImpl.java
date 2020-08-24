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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.vo.RcmConfigTotalVO;
import org.git.modules.clm.rcm.mapper.RcmConfigTotalMapper;
import org.git.modules.clm.rcm.service.IRcmConfigTotalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 限额详细统计表 服务实现类
 *
 * @author git
 * @since 2019-12-23
 */
@Service
public class RcmConfigTotalServiceImpl extends ServiceImpl<RcmConfigTotalMapper, RcmConfigTotal> implements IRcmConfigTotalService {

	@Override
	public IPage<RcmConfigTotalVO> selectRcmConfigTotalPage(IPage<RcmConfigTotalVO> page, RcmConfigTotalVO rcmConfigTotal) {
		return page.setRecords(baseMapper.selectRcmConfigTotalPage(page, rcmConfigTotal));
	}

	@Override
	public RcmConfigTotal selectRcmConfigTotal(String useOrgNum, String year, String month, String quotaIndexNum) {
		RcmConfigTotal rcmConfigTotal = new RcmConfigTotal();
		rcmConfigTotal.setUseOrgNum(useOrgNum);
		rcmConfigTotal.setQuotaIndexNum(quotaIndexNum);
		rcmConfigTotal.setTotalYear(year);
		rcmConfigTotal.setTotalMonth(month);
		return baseMapper.selectOne(Condition.getQueryWrapper(rcmConfigTotal));
	}

	public List<RcmConfigTotal> selectRcmConfigTotal(String useOrgNum, String year, String month, String... quotaIndexNums) {
		RcmConfigTotal rcmConfigTotal = new RcmConfigTotal();
		rcmConfigTotal.setUseOrgNum(useOrgNum);
		rcmConfigTotal.setTotalYear(year);
		rcmConfigTotal.setTotalMonth(month);
		LambdaQueryWrapper queryWrapper = Condition.getQueryWrapper(rcmConfigTotal).lambda().in(RcmConfigTotal::getQuotaIndexNum, quotaIndexNums);
		return baseMapper.selectList(queryWrapper);
	}


}
