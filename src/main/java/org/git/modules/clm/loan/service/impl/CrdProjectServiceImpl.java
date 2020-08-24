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
package org.git.modules.clm.loan.service.impl;

import org.git.modules.clm.loan.entity.CrdProject;
import org.git.modules.clm.loan.vo.CrdProjectVO;
import org.git.modules.clm.loan.mapper.CrdProjectMapper;
import org.git.modules.clm.loan.service.ICrdProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目协议表 服务实现类
 *
 * @author git
 * @since 2019-11-21
 */
@Service
public class CrdProjectServiceImpl extends ServiceImpl<CrdProjectMapper, CrdProject> implements ICrdProjectService {

	@Override
	public IPage<CrdProjectVO> selectCrdProjectPage(IPage<CrdProjectVO> page, CrdProjectVO crdProject) {
		return page.setRecords(baseMapper.selectCrdProjectPage(page, crdProject));
	}

}
