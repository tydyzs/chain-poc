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
package org.git.modules.system.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.LoginCache;
import org.git.common.cache.SysCache;
import org.git.common.config.ClmConfiguration;
import org.git.modules.system.entity.LoginLog;
import org.git.modules.system.entity.User;
import org.git.modules.system.mapper.LoginLogMapper;
import org.git.modules.system.mapper.UserMapper;
import org.git.modules.system.service.ILoginLogService;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.core.tool.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录日志记录Service实现类
 * @author Chill
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ClmConfiguration clmConfiguration;

	@Override
	public boolean isLoginByUser(String account) {
		LoginLog loginLog = LoginCache.getLoginUser(account);
		return loginLog != null;
	}

	@Override
	public void userLogin(String account, String deptId) {
		String deptName = StringUtil.isNotBlank(deptId)?SysCache.getDeptName(deptId):"";
		LoginLog loginLog = saveLoginLog(account,deptName,LoginLog.ACTION_TYPE_LOGIN);
		LoginCache.addLoginUser(account,loginLog,clmConfiguration.getLoginExpire());
	}

	@Override
	public void userLogout(String account,String outType) {
		LoginLog loginLog = LoginCache.getLoginUser(account);
		if(loginLog == null){
			throw new ServiceException("当前用户未查询到登录信息！");
		}
		saveLoginLog(account,loginLog.getOrgName(),outType);
		LoginCache.removeLoginUser(account);
	}

	@Override
	public List<LoginLog> userLoginList(String account) {
		List<LoginLog> res = LoginCache.loginUserList(account);
		return res.stream().filter(s -> StringUtils.isNotBlank(s.getAccount())).collect(Collectors.toList());
	}


	private LoginLog saveLoginLog(String account, String orgName,String actionType){
		User user = userMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getAccount,account).eq(User::getIsDeleted,0));
		LoginLog loginLog = new LoginLog();
		if(null!=user){
			loginLog.setAccount(account);
			loginLog.setUserName(user.getName());
			loginLog.setActionClientIp(WebUtil.getIP());
			loginLog.setOrgName(orgName);
			loginLog.setActionDatetime(DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));
			loginLog.setActionType(actionType);
			this.save(loginLog);
		}
		return loginLog;
	}
}
