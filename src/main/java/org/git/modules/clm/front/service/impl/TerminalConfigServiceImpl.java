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
import org.git.core.mp.support.Condition;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.modules.clm.front.entity.TerminalConfig;
import org.git.modules.clm.front.mapper.TerminalConfigMapper;
import org.git.modules.clm.front.service.ITerminalConfigService;
import org.git.modules.clm.front.vo.TerminalConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前置终端配置表 服务实现类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Service
public class TerminalConfigServiceImpl extends BaseServiceImpl<TerminalConfigMapper, TerminalConfig> implements ITerminalConfigService {

	@Override
	public IPage<TerminalConfigVO> selectTerminalConfigPage(IPage<TerminalConfigVO> page, TerminalConfigVO terminalConfig) {
		return page.setRecords(baseMapper.selectTerminalConfigPage(page, terminalConfig));
	}

	@Override
	public List<TerminalConfigVO> tree() {
		List<TerminalConfigVO> list = ForestNodeMerger.merge(baseMapper.tree());
		return list;
	}

	public TerminalConfig getOneByCode(String terminalCode) {
		TerminalConfig terminalConfig = new TerminalConfig();
		terminalConfig.setTerminalCode(terminalCode);
		return super.getOne(Condition.getQueryWrapper(terminalConfig));
	}

	public TerminalConfig getOneByCode(String terminalCode, String terminalRole) {
		TerminalConfig terminalConfig = new TerminalConfig();
		terminalConfig.setTerminalCode(terminalCode);
		terminalConfig.setTerminalRole(terminalRole);
		return super.getOne(Condition.getQueryWrapper(terminalConfig));
	}

	/**
	 * 获取来源系统
	 */
	public List<TerminalConfig> listTerminalConfig() {
		return baseMapper.selectSourceSystem();
	}

}
