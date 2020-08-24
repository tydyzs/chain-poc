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
package org.git.modules.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiSort;
import lombok.AllArgsConstructor;
import org.git.common.config.ClmConfiguration;
import org.git.common.constant.AppConstant;
import org.git.core.log.annotation.ApiLog;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.git.core.tool.support.Kv;
import org.git.core.tool.utils.DigestUtil;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.WebUtil;
import org.git.modules.auth.granter.ITokenGranter;
import org.git.modules.auth.granter.TokenGranterBuilder;
import org.git.modules.auth.granter.TokenParameter;
import org.git.modules.auth.utils.TokenUtil;
import org.git.modules.system.entity.LoginLog;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserInfo;
import org.git.modules.system.service.ILoginLogService;
import org.git.modules.system.service.IUserRoleService;
import org.git.modules.system.service.IUserService;
import org.git.modules.system.vo.UserRoleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 认证模块
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_AUTH_NAME)
@ApiSort(1)
@Api(value = "用户授权认证", tags = "授权接口")

public class AuthController {

	private IUserService service;
	private ILoginLogService loginLogService;
	private IUserRoleService userRoleService;
	private ClmConfiguration clmConfiguration;
	private IUserService userService;

	@ApiLog("登录用户验证")
	@PostMapping("/oauth/token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public Kv token(@ApiParam(value = "租户ID", required = false) @RequestParam(defaultValue = "000000", required = false) String tenantId,
					@ApiParam(value = "账号", required = false) @RequestParam(required = false) String account,
					@ApiParam(value = "密码", required = false) @RequestParam(required = false) String password,
					@ApiParam(value = "登录机构", required = false) @RequestParam(required = false) String deptId) {

		Kv authInfo = Kv.create();

		String grantType = WebUtil.getRequest().getParameter("grant_type");
		String refreshToken = WebUtil.getRequest().getParameter("refresh_token");

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId)
			.set("deptId",deptId)
			.set("username", account)
			.set("password", password)
			.set("grantType", grantType)
			.set("refreshToken", refreshToken)
			.set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null) {
			return authInfo.set("code", HttpServletResponse.SC_BAD_REQUEST).set("msg", "用户名或密码不正确");
		}
		//判断用户是否登录 true 已登录，false 未登录
		boolean userLoginState = loginLogService.isLoginByUser(account);
		if(clmConfiguration.isLoginSingle() && userLoginState){
			return authInfo.set("code", HttpServletResponse.SC_BAD_REQUEST).set("msg", "当前用户已登录！");
		}
		//记录登录日志
		loginLogService.userLogin(account,deptId);
		return TokenUtil.createAuthInfo(userInfo);
	}

	@ApiLog("用户登出")
	@PostMapping("/oauth/logout")
	@ApiOperation(value = "用户登出", notes = "")
	public R logout(){
		ChainUser chainUser = SecureUtil.getUser();
		loginLogService.userLogout(chainUser.getAccount(),LoginLog.ACTION_TYPE_LOGOUT);
		return R.success("用户退出成功！");
	}


	@ApiLog("强制退出")
	@PostMapping("/oauth/forcedout")
	@ApiOperation(value = "强制退出", notes = "传入用户帐号：account")
	public R forcedout(@ApiParam(value = "账号", required = true) @RequestParam(required = true) String account){
		loginLogService.userLogout(account,LoginLog.ACTION_TYPE_FORCED_OUT);
		return R.success("用户退出成功！");
	}


	@ApiLog("用户登录列表")
	@PostMapping("/oauth/loginUserList")
	@ApiOperation(value = "用户登录列表", notes = "传入账号:account")
	public R loginUserList(@ApiParam(value = "账号", required = false) @RequestParam(required = false) String account, Query query){
		Map<String,Object> data = new HashMap<String,Object>();
		List<LoginLog> records = loginLogService.userLoginList(account);
		data.put("total",records.size());
		if ((query.getCurrent()-1) * query.getSize() <= records.size()) {
			records = records.stream().skip((query.getCurrent()-1) * query.getSize()).limit(query.getSize()).collect(Collectors.toList());
		}
		data.put("records",records);
		return R.data(data);
	}

	@ApiLog("切换登录机构")
	@PostMapping("/oauth/loginOrgSwitch")
	@ApiOperation(value = "切换登录机构", notes = "传入机构ID:deptId")
	public Kv loginOrgSwitch(@ApiParam(value = "机构ID", required = true) @RequestParam(required = true) String deptId){
		ChainUser chainUser = SecureUtil.getUser();
		UserInfo userInfo = userService.userInfo(chainUser.getUserId(),deptId);
		loginLogService.userLogin(chainUser.getAccount(),deptId);
		return TokenUtil.createAuthInfo(userInfo);
	}

	@ApiLog("获取用户机构列表")
	@PostMapping("/oauth/depts")
	@ApiOperation(value = "获取用户下的机构列表", notes = "传入租户ID:tenantId,账号:account")
	public R getDepts(@ApiParam(value = "租户ID", required = false) @RequestParam(defaultValue = "000000", required = false) String tenantId,
					   @ApiParam(value = "账号", required = true) @RequestParam(required = true) String account) {
		User user = service.user(tenantId, account);

		if (user == null) {
			return R.fail("用户帐号不存在！");
		}
		List<UserRoleVO> deptList = userRoleService.deptsByUserId(user.getId());
		return R.data(deptList);
	}


	@ApiLog("获取用户信息")
	@PostMapping("/oauth/userinfo")
	public Kv getUserInfo() {
		Kv authInfo = Kv.create();
		authInfo.put("roles",new String[]{"admin"});
		authInfo.put("introduction","I am a super administrator");
		authInfo.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		authInfo.put("name","Super Admin");
		return authInfo;
	}


}
