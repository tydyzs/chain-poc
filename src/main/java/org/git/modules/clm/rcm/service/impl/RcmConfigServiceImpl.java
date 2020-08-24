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

import org.git.modules.clm.rcm.entity.RcmConfig;
import org.git.modules.clm.rcm.vo.RcmConfigVO;
import org.git.modules.clm.rcm.mapper.RcmConfigMapper;
import org.git.modules.clm.rcm.service.IRcmConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 限额详细信息表 服务实现类
 *
 * @author liuye
 * @since 2019-11-01
 */
@Service
public class RcmConfigServiceImpl extends ServiceImpl<RcmConfigMapper, RcmConfig> implements IRcmConfigService {

	@Override
	public IPage<RcmConfigVO> selectRcmConfigPage(IPage<RcmConfigVO> page, RcmConfigVO rcmConfigurationInfo) {
		return page.setRecords(baseMapper.selectRcmConfigPage(page, rcmConfigurationInfo));
	}

	@Override
	public boolean updateRcmConfigNameAndState(RcmConfig rcmConfigurationInfo){
		int u = baseMapper.updateRcmConfigNameAndState(rcmConfigurationInfo);
		if (u>0){
			return true;
		}
		return false;
	}

}
