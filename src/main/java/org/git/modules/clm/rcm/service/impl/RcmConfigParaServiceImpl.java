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

import org.git.modules.clm.rcm.entity.RcmConfigPara;
import org.git.modules.clm.rcm.vo.RcmConfigParaVO;
import org.git.modules.clm.rcm.mapper.RcmConfigParaMapper;
import org.git.modules.clm.rcm.service.IRcmConfigParaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 限额参数配置信息表 服务实现类
 *
 * @author liuye
 * @since 2019-11-01
 */
@Service
public class RcmConfigParaServiceImpl extends ServiceImpl<RcmConfigParaMapper, RcmConfigPara> implements IRcmConfigParaService {

	@Override
	public IPage<RcmConfigParaVO> selectRcmControlPage(IPage<RcmConfigParaVO> page, RcmConfigParaVO rcmConfigurationPara) {
		return page.setRecords(baseMapper.selectRcmControlPage(page, rcmConfigurationPara));
	}

	@Override
	public List<RcmConfigParaVO> selectOneRcmControl(String quotaNum){
		return baseMapper.selectOneRcmControl(quotaNum);
	}

	@Override
	public boolean addRcmControl(List<RcmConfigPara> rcmConfigurationParas){
		int a = baseMapper.addRcmControl(rcmConfigurationParas);
		if (a>0){
			return true;
		}else{
			return false;
		}
	}
}
