package org.git.common.cache;

import org.git.common.constant.CommonConstant;
import org.git.modules.system.entity.LoginLog;
import org.git.modules.system.service.IUserService;
import org.git.core.cache.utils.CacheUtil;
import org.git.core.redis.RedisUtil;
import org.git.core.tool.utils.SpringUtil;
import org.git.core.tool.utils.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

public class LoginCache {

	private static final String LOGIN_CACHE = "chain:login";
	private static final String USER_LOGIN_ACCOUNT = "user:account:";

	private static IUserService userService;

	private static RedisUtil redisUtil;

	private static RedisTemplate redisTemplate;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		redisUtil =  SpringUtil.getBean("redisUtil",RedisUtil.class);
		redisTemplate = SpringUtil.getBean("redisTemplate",RedisTemplate.class);
	}

	/**
	 * 登录用户信息存入缓存
	 * @param account 用户帐号作不缓存的KEY
	 * @param loginLog    用户实体对象作为缓存Value
	 */
	public static void addLoginUser(String account, LoginLog loginLog,long expire) {
		CacheUtil.put(LOGIN_CACHE,USER_LOGIN_ACCOUNT,account,loginLog);
		redisUtil.expire(LOGIN_CACHE+USER_LOGIN_ACCOUNT+account, expire);
	}

	/**
	 * 延长登录失效时间
	 */
	public static void loginExpire(String account,long expire) {
		redisUtil.expire(LOGIN_CACHE+"::"+USER_LOGIN_ACCOUNT+account,expire);
	}


	/**
	 * 获取登录用户列表
	 */
	public static List loginUserList(String account) {
		account = StringUtil.isNotBlank(account)?account:"";
		Set<String> keys = redisTemplate.keys(LOGIN_CACHE+"::"+USER_LOGIN_ACCOUNT +account+"*");
		return	 redisTemplate.opsForValue().multiGet(keys);
	}

	/**
	 * 根据用户帐号移除缓存中登录用户信息（场景，用户退出系统时调用）
	 * @param account 用户帐号作不缓存的KEY
	 */
	public static void removeLoginUser(String account) {
		CacheUtil.evict(LOGIN_CACHE,USER_LOGIN_ACCOUNT,account);
	}

	/**
	 * 通过用户帐号获取已登录的用户信息
	 * @param account 用户帐号
	 * @return 用户实体
	 */
	public static LoginLog getLoginUser(String account) {
		return CacheUtil.get(LOGIN_CACHE,USER_LOGIN_ACCOUNT,account,LoginLog.class);
	}
}
