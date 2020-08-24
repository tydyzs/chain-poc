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
package org.git.modules.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.LoginLog;
import org.git.modules.system.entity.UserRole;

import java.util.List;

/**
 * 登录日志记录Service
 * 1、记录用户登录系统的时间，ip、下线时间在tb_login_log表
 * 2、实现强制在线用户下线功能
 * @author lixiaowei
 * @date
 */
public interface ILoginLogService extends IService<LoginLog> {

	/**
	 * 判断用户是否已登录
	 * @param account
	 * @return
	 */
	boolean isLoginByUser(String account);

	/**
	 * 用户登录
	 * @param account
	 * @param deptId
	 */
	void userLogin(String account, String deptId);

	/**
	 * 用户退出
	 * @param account 用户帐号
	 * @param outType 退出类型 2 用户退出 3 强制退出
	 */
	void userLogout(String account,String outType);


	/**
	 * 用户登录列表
	 */
	List<LoginLog> userLoginList(String account);
}
