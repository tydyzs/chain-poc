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
package org.git.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserRole;
import org.git.core.tool.utils.DigestUtil;
import org.git.core.tool.utils.Func;
import org.git.modules.auth.enums.ChainUserEnum;
import org.git.modules.system.entity.UserInfo;
import org.git.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserService service;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String deptId = tokenParameter.getArgs().getStr("deptId");
		String username = tokenParameter.getArgs().getStr("username");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(username, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(ChainUserEnum.WEB.getName())) {
				userInfo = service.userInfo(tenantId, username, DigestUtil.encrypt(password),deptId);
			} else if (userType.equals(ChainUserEnum.APP.getName())) {
				userInfo = service.userInfo(tenantId, username, DigestUtil.encrypt(password),deptId);
			} else {
				userInfo = service.userInfo(tenantId, username, DigestUtil.encrypt(password),deptId);
			}
		}
		return userInfo;
	}


}
