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

import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.core.mp.support.BaseEntityWrapper;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.node.INode;
import org.git.core.tool.utils.BeanUtil;
import org.git.core.tool.utils.Func;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.vo.DeptVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
		assert deptVO != null;
		if (Func.equals(dept.getParentId(), ChainConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(ChainConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = SysCache.getDept(dept.getParentId());
			if(null != parent) {
				deptVO.setParentName(parent.getDeptName());
			}
		}
		if(StringUtils.isNotBlank(dept.getDeptCategory())){
			String category = DictCache.getValue("CD000214", dept.getDeptCategory());
			deptVO.setDeptCategoryName(category);
		}
		if(StringUtils.isNotBlank(dept.getDeptLevel())) {
			String level = DictCache.getValue("org_level",dept.getDeptLevel());
			deptVO.setDeptLevelName(level);
		}
		return deptVO;
	}

	public List<INode> listNodeVO(List<Dept> list) {
		List<INode> collect = list.stream().map(dept -> {
			DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
			if(StringUtils.isNotBlank(dept.getDeptCategory())){
				String category = DictCache.getValue("org_category", dept.getDeptCategory());
				Objects.requireNonNull(deptVO).setDeptCategoryName(category);
			}
			if(StringUtils.isNotBlank(dept.getDeptLevel())) {
				String level = DictCache.getValue("org_level", dept.getDeptLevel());
				Objects.requireNonNull(deptVO).setDeptLevelName(level);
			}
			return deptVO;
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
