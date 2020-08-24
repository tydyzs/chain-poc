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

package org.git.common.config;

import lombok.AllArgsConstructor;
import org.git.common.event.ApiLogListener;
import org.git.common.event.ErrorLogListener;
import org.git.common.event.UsualLogListener;
import org.git.core.launch.props.ChainProperties;
import org.git.core.launch.server.ServerInfo;
import org.git.modules.system.service.ILogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
public class GitLogConfiguration {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final ChainProperties chainProperties;

	@Bean(name = "apiLogListener")
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, chainProperties);
	}

	@Bean(name = "errorEventListener")
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, chainProperties);
	}

	@Bean(name = "usualEventListener")
	public UsualLogListener usualEventListener() {
		return new UsualLogListener(logService, serverInfo, chainProperties);
	}

}
