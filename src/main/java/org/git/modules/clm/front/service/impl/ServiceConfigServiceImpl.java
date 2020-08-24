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
package org.git.modules.clm.front.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.core.mp.base.BaseServiceImpl;
import org.git.modules.clm.front.entity.ServiceConfig;
import org.git.modules.clm.front.mapper.ServiceConfigMapper;
import org.git.modules.clm.front.service.IServiceConfigService;
import org.git.modules.clm.front.vo.ServiceConfigVO;
import org.git.core.mp.support.Condition;
import org.springframework.stereotype.Service;

/**
 * 前置服务配置表 服务实现类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Service
public class ServiceConfigServiceImpl extends BaseServiceImpl<ServiceConfigMapper, ServiceConfig> implements IServiceConfigService {

	@Override
	public IPage<ServiceConfigVO> selectServiceConfigPage(IPage<ServiceConfigVO> page, ServiceConfigVO serviceConfig) {
		return page.setRecords(baseMapper.selectServiceConfigPage(page, serviceConfig));
	}

	@Override
	public ServiceConfig getOneByCode(String serviceCode) {
		ServiceConfig serviceConfig = new ServiceConfig();
		serviceConfig.setServiceCode(serviceCode);
		return super.getOne(Condition.getQueryWrapper(serviceConfig));
	}

}
