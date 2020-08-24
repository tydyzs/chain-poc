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

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserRole;
import org.git.core.launch.constant.TokenConstant;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.Func;
import org.git.modules.system.entity.UserInfo;
import org.git.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private IUserService service;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		String deptId =  tokenParameter.getArgs().getStr("deptId");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = SecureUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
			if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
				userInfo = service.userInfo(Func.toStr(claims.get(TokenConstant.USER_ID)),deptId);
			}
		}
		return userInfo;
	}

}
