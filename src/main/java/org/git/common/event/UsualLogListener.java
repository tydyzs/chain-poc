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
package org.git.common.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.launch.props.ChainProperties;
import org.git.core.launch.server.ServerInfo;
import org.git.core.log.constant.EventConstant;
import org.git.core.log.event.UsualLogEvent;
import org.git.core.log.model.LogUsual;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.UrlUtil;
import org.git.core.tool.utils.WebUtil;
import org.git.modules.system.service.ILogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final ChainProperties chainProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		HttpServletRequest request = (HttpServletRequest) source.get(EventConstant.EVENT_REQUEST);
		logUsual.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		logUsual.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		logUsual.setMethod(request.getMethod());
		logUsual.setParams(WebUtil.getRequestParamString(request));
		logUsual.setServerHost(serverInfo.getHostName());
		logUsual.setServiceId(chainProperties.getName());
		logUsual.setEnv(chainProperties.getEnv());
		logUsual.setServerIp(serverInfo.getIpWithPort());
		logUsual.setCreateBy(SecureUtil.getUserAccount(request));
		logUsual.setCreateTime(DateUtil.now());
		logService.saveUsualLog(logUsual);
	}

}
