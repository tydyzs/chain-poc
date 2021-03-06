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
package org.git.modules.system.service;

import org.git.modules.system.entity.SysDate;
import org.git.modules.system.vo.SysDateVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 营业日期 服务类
 *
 * @author git
 * @since 2019-11-20
 */
public interface ISysDateService extends IService<SysDate> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sysDate
	 * @return
	 */
	IPage<SysDateVO> selectSysDatePage(IPage<SysDateVO> page, SysDateVO sysDate);

	/**
	 * 获取一条记录
	 *
	 * @return
	 */
	SysDate selectSysDate();

}
