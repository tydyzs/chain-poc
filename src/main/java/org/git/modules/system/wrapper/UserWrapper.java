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
package org.git.modules.system.wrapper;

import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.modules.system.entity.User;
import org.git.modules.system.vo.UserVO;
import org.git.core.mp.support.BaseEntityWrapper;
import org.git.core.tool.utils.BeanUtil;
import org.git.core.tool.utils.Func;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		assert userVO != null;
		List<String> roleName = SysCache.getRoleNames(user.getRoleId());
		List<String> deptName = SysCache.getDeptNames(user.getDeptId());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		userVO.setSexName(DictCache.getValue("sex", user.getSex()));
		return userVO;
	}

}
