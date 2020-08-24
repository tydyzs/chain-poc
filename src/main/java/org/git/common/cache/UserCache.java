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
package org.git.common.cache;

import org.git.modules.system.entity.User;
import org.git.modules.system.service.IUserService;
import org.git.core.cache.utils.CacheUtil;
import org.git.core.redis.RedisUtil;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.SpringUtil;
import org.git.core.tool.utils.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.git.core.cache.constant.CacheConstant.USER_CACHE;
import static org.git.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 系统缓存
 *
 * @author Chill
 */
public class UserCache {
	private static final String USER_CACHE_ID = "user:id:";
	private static final String USER_CACHE_ACCOUNT = "user:account:";

	private static IUserService userService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
	}

	/**
	 * 根据任务用户id获取用户信息
	 *
	 * @param taskUserId 任务用户id
	 * @return
	 */
	public static User getUserByTaskUser(String taskUserId) {
		String userId = Func.toStr(StringUtil.removePrefix(taskUserId, TASK_USR_PREFIX));
		return getUser(userId);
	}

	/**
	 * 获取用户名
	 *
	 * @param userId 用户id
	 * @return
	 */
	public static User getUser(String userId) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, userId, () -> userService.getById(userId));
	}

	/**
	 * 根据用户号获取用户姓名
	 * @param userNum
	 * @return
	 */
	public static String getUserName(String userNum){
		return CacheUtil.get(USER_CACHE, USER_CACHE_ACCOUNT, userNum, () -> userService.getUserByAccount(userNum).getRealName());
	}

	/**
	 * 获取用户名
	 *
	 * @param
	 * @return
	 */
	public static List<User> getAllUser() {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, "all", () -> userService.selectAllUser());
	}


}
