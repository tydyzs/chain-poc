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
import org.git.modules.system.entity.Role;
import org.git.modules.system.vo.DeptVO;
import org.git.modules.system.vo.RoleVO;
import org.git.core.mp.support.BaseEntityWrapper;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.node.INode;
import org.git.core.tool.utils.BeanUtil;
import org.git.core.tool.utils.Func;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		assert roleVO != null;
		if (Func.equals(role.getParentId(), ChainConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(ChainConstant.TOP_PARENT_NAME);
		} else {
			Role parent = SysCache.getRole(role.getParentId());
			if(parent!=null){
				roleVO.setParentName(parent.getRoleName());
			}
		}
		if(role.getDeptLevel()!=null) {
			String level = DictCache.getValue("org_level", role.getDeptLevel());
			roleVO.setDeptLevelName(level);
		}
		return roleVO;
	}


	public List<INode> listNodeVO(List<Role> list) {
		List<INode> collect = list.stream().map(role -> {
				RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
				if(role.getDeptLevel()!=null) {
					String level = DictCache.getValue("org_level", roleVO.getDeptLevel());
					Objects.requireNonNull(roleVO).setDeptLevelName(level);
				}
				return roleVO;
			}
		).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
