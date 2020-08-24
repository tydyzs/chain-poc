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


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserInfo;
import org.git.core.mp.base.BaseService;
import org.git.modules.system.vo.DeptVO;
import org.git.modules.system.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增用户
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user,List<String> loginUserDeptIds);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(String userId, String deptId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User user(String tenantId, String account);

	/**
	 * 通过account获取用户信息
	 * @param account
	 * @return
	 */
	UserVO getUserByAccount(String account);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, String password, String deptId);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(String userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 删除用户
	 *
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);

	/**
	 * 查询所有的用户（不分页）
	 *
	 * @return
	 */
	Map<String, String> listAll();

	IPage<Map> selectUserList(IPage<Map> page, User user);

	List<User> selectAllUser();

}
