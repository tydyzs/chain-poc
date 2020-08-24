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

import org.git.modules.clm.rcm.entity.RcmIndexCredit;
import org.git.modules.clm.rcm.vo.RcmIndexCreditVO;
import org.git.modules.clm.rcm.mapper.RcmIndexCreditMapper;
import org.git.modules.clm.rcm.service.IRcmIndexCreditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author git
 * @since 2019-10-29
 */
@Service
public class RcmIndexCreditServiceImpl extends ServiceImpl<RcmIndexCreditMapper, RcmIndexCredit> implements IRcmIndexCreditService {

	@Override
	public IPage<RcmIndexCreditVO> selectRcmConfigurationCreditPage(IPage<RcmIndexCreditVO> page, RcmIndexCreditVO rcmConfigurationCredit) {
		return page.setRecords(baseMapper.selectRcmConfigurationCreditPage(page, rcmConfigurationCredit));
	}

}
