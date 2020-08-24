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
package org.git.modules.clm.credit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.modules.clm.credit.entity.TbCrdApproveEvent;
import org.git.modules.clm.credit.mapper.TbCrdApproveEventMapper;
import org.git.modules.clm.credit.service.ITbCrdApproveEventService;
import org.git.modules.clm.credit.vo.TbCrdApproveEventVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 信贷实时-批复信息 服务实现类
 *
 * @author git
 * @since 2019-11-14
 */
@Service
public class TbCrdApproveEventServiceImpl extends ServiceImpl<TbCrdApproveEventMapper, TbCrdApproveEvent> implements ITbCrdApproveEventService {

	@Override
	public IPage<TbCrdApproveEventVO> selectTbCrdApproveEventPage(IPage<TbCrdApproveEventVO> page, TbCrdApproveEventVO tbCrdApproveEvent) {
		return page.setRecords(baseMapper.selectTbCrdApproveEventPage(page, tbCrdApproveEvent));
	}

}
