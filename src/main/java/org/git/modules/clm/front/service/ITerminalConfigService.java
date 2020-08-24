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
package org.git.modules.clm.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.core.mp.base.BaseService;
import org.git.modules.clm.front.entity.TerminalConfig;
import org.git.modules.clm.front.vo.TerminalConfigVO;

import java.util.List;

/**
 * 前置终端配置表 服务类
 *
 * @author caohaijie
 * @since 2019-09-25
 */
public interface ITerminalConfigService extends BaseService<TerminalConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param terminalConfig
	 * @return
	 */
	IPage<TerminalConfigVO> selectTerminalConfigPage(IPage<TerminalConfigVO> page, TerminalConfigVO terminalConfig);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<TerminalConfigVO> tree();

	/**
	 * 根据代码获取单条记录
	 *
	 * @param terminalCode
	 * @return
	 */
	TerminalConfig getOneByCode(String terminalCode);

	/**
	 * 根据代码获取单条记录
	 *
	 * @param terminalCode
	 * @param terminalRole
	 * @return
	 */
	TerminalConfig getOneByCode(String terminalCode, String terminalRole);

	/**
	 * 获取来源系统（去重）
	 *
	 * @return
	 */
	List<TerminalConfig> listTerminalConfig();
}
