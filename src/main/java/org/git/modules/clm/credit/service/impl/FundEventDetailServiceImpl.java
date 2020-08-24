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
package org.git.modules.clm.credit.service.impl;

import org.git.modules.clm.credit.entity.FundEventDetail;
import org.git.modules.clm.credit.vo.FundEventDetailVO;
import org.git.modules.clm.credit.mapper.FundEventDetailMapper;
import org.git.modules.clm.credit.service.IFundEventDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 资金用信事件明细 服务实现类
 *
 * @author liuye
 * @since 2019-11-27
 */
@Service
public class FundEventDetailServiceImpl extends ServiceImpl<FundEventDetailMapper, FundEventDetail> implements IFundEventDetailService {

	@Override
	public IPage<FundEventDetailVO> selectFundEventDetailPage(IPage<FundEventDetailVO> page, FundEventDetailVO fundEventDetail) {
		return page.setRecords(baseMapper.selectFundEventDetailPage(page, fundEventDetail));
	}

	@Override
	public List<FundEventDetail> selectToSerial(String eventMainId){
		return baseMapper.selectToSerial(eventMainId);
	}
}
