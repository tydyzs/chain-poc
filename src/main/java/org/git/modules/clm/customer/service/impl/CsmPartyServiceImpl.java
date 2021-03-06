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
package org.git.modules.clm.customer.service.impl;

import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.vo.CsmPartyVO;
import org.git.modules.clm.customer.mapper.CsmPartyMapper;
import org.git.modules.clm.customer.service.ICsmPartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户主表 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CsmPartyServiceImpl extends ServiceImpl<CsmPartyMapper, CsmParty> implements ICsmPartyService {

	@Override
	public IPage<CsmPartyVO> selectCsmPartyPage(IPage<CsmPartyVO> page, CsmPartyVO csmParty) {
		return page.setRecords(baseMapper.selectCsmPartyPage(page, csmParty));
	}

}
