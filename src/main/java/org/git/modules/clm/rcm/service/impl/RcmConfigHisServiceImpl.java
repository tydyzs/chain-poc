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
package org.git.modules.clm.rcm.service.impl;

import org.git.modules.clm.rcm.entity.RcmConfigHis;
import org.git.modules.clm.rcm.vo.RcmConfigHisVO;
import org.git.modules.clm.rcm.mapper.RcmConfigHisMapper;
import org.git.modules.clm.rcm.service.IRcmConfigHisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 限额详细信息历史表 服务实现类
 *
 * @author git
 * @since 2019-11-05
 */
@Service
public class RcmConfigHisServiceImpl extends ServiceImpl<RcmConfigHisMapper, RcmConfigHis> implements IRcmConfigHisService {

	@Override
	public IPage<RcmConfigHisVO> selectRcmConfigHisPage(IPage<RcmConfigHisVO> page, RcmConfigHisVO rcmConfigurationInfoHis) {
		return page.setRecords(baseMapper.selectRcmConfigHisPage(page, rcmConfigurationInfoHis));
	}

	@Override
	public boolean moveToHis(RcmConfigHis rcmConfigurationInfoHis){
		int m = baseMapper.moveToHis(rcmConfigurationInfoHis);
		if (m>0){
			return true;
		}
		return false;
	}
}
