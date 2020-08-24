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
import org.git.modules.system.constant.UserConstant;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserRole;
import org.git.modules.system.mapper.UserRoleMapper;
import org.git.modules.system.service.IUserRoleService;
import org.git.modules.system.service.IUserService;
import org.git.modules.system.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
	@Autowired
	private IUserService userService;

	@Override
	public List<UserRoleVO> deptsByUserId(String userId) {
		return baseMapper.deptsByUserId(userId);
	}

	@Override
	public UserRole getUserRoleByUserIdAndDeptId(String userId, String deptId) {
		return baseMapper.selectOne(Wrappers.<UserRole>query().lambda().eq(UserRole::getUserId,userId).eq(UserRole::getDeptId,deptId));
	}

	@Override
	public boolean removeById(String id) {
		UserRole ur = super.getById(id);
		if(ur.getIsMasterOrg() == UserConstant.USER_MASTER_ORG_YES){
		  	throw new SecurityException("用户主机构信息不能删除！");
		}
		return super.removeById(id);
	}

	@Override
	public boolean save(UserRole userRole) {
		int count = count(Wrappers.<UserRole>query().lambda().eq(UserRole::getUserId,userRole.getUserId()));
		if(count == 0){
			userRole.setIsMasterOrg(UserConstant.USER_MASTER_ORG_YES);
			User user =userService.getById(userRole.getUserId());
			user.setDeptId(userRole.getDeptId());
			user.setRoleId(userRole.getRoleId());
			userService.updateUser(user);
		}else{
			userRole.setIsMasterOrg(UserConstant.USER_MASTER_ORG_NO);
		}
		return super.save(userRole);
	}

	@Override
	public boolean updateById(UserRole userRole) {
		UserRole ur = super.getById(userRole.getId());
		//如果更新用户角色是主机构，则更新用户信息中的所属角色及所属机构
		if(userRole.getIsMasterOrg()== UserConstant.USER_MASTER_ORG_YES){
			User user =userService.getById(userRole.getUserId());
			user.setDeptId(userRole.getDeptId());
			user.setRoleId(userRole.getRoleId());
			userService.updateUser(user);
		}
		userRole.setIsMasterOrg(null);
		return super.updateById(userRole);
	}

	@Override
	public boolean masterOrgSwitch(UserRole userRole){
		baseMapper.cancelMasterOrg(userRole.getUserId());
		User user =userService.getById(userRole.getUserId());
		user.setDeptId(userRole.getDeptId());
		user.setRoleId(userRole.getRoleId());
		userService.updateUser(user);
		userRole.setIsMasterOrg(UserConstant.USER_MASTER_ORG_YES);
		return super.updateById(userRole);
	}
}
