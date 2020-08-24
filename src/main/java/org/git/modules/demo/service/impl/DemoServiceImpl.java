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
package org.git.modules.demo.service.impl;

import org.git.modules.demo.entity.Demo;
import org.git.modules.demo.vo.DemoVO;
import org.git.modules.demo.mapper.DemoMapper;
import org.git.modules.demo.service.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 演示表 服务实现类
 *
 * @author git
 * @since 2019-11-06
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

	@Override
	public IPage<DemoVO> selectDemoPage(IPage<DemoVO> page, DemoVO demo) {
		return page.setRecords(baseMapper.selectDemoPage(page, demo));
	}

}
