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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.core.mp.support.Condition;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.StringPool;
import org.git.modules.auth.utils.TokenUtil;
import org.git.modules.system.constant.DeptConstant;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.mapper.DeptMapper;
import org.git.modules.system.service.IDeptService;
import org.git.modules.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept,List<String> loginUserDeptIds) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept,
			loginUserDeptIds.toArray(new String[loginUserDeptIds.size()])));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}
	@Override
	public List<DeptVO> treeManage(String orgNum,String isManage) {
		List<DeptVO> dvoList = baseMapper.treeManage(orgNum,isManage);
		dvoList= ForestNodeMerger.merge(dvoList);
		return  dvoList;
	}
	@Override
	public List<DeptVO> treeManage2(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.treeManage(tenantId,"1"));
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toStrArray(deptIds));
	}

	@Override
	public boolean removeDept(String ids) {
		Integer cnt = baseMapper.selectCount(Wrappers.<Dept>query().lambda().in(Dept::getParentId, Func.toStrList(ids)));
		if (cnt > 0) {
			throw new ApiException("请先删除子节点!");
		}
		return removeByIds(Func.toStrList(ids));
	}

	@Override
	public boolean submit(Dept dept) {
		if (dept.getParentId() == null) {
			dept.setParentId(ChainConstant.TOP_PARENT_ID);
		}
		if (dept.getParentId() != null && !"".equals(dept.getParentId()) && !"0".equals(dept.getParentId()) && !"-1".equals(dept.getParentId())) {
			Dept parent = getById(dept.getParentId());
			String ancestors = (parent ==null ? StringPool.COMMA : parent.getAncestors())+ dept.getId() + StringPool.COMMA ;
			dept.setAncestors(ancestors);
		}
		dept.setIsDeleted(ChainConstant.DB_NOT_DELETED);
		dept.setTenantId(TokenUtil.DEFAULT_TENANT_ID);
		return saveOrUpdate(dept);
	}

	@Override
	public boolean isMemberBank(String id) {
		Dept dept = new Dept();
		dept.setId(id);
		dept.setOrgType(DeptConstant.ORG_TYPE_MEMBER);//CD000214 机构类型：成员行
		int count = baseMapper.selectCount(Condition.getQueryWrapper(dept));
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Dept upperDeptByType(String id, String deptLevel) {
		return baseMapper.upperDeptByType(id, deptLevel);
	}

	@Override
	public Dept selectProvincialCooperative() {
		Dept params = new Dept();
		params.setDeptLevel(DeptConstant.PROVINCIAL_COOPERATIVE);
		return baseMapper.selectOne(Condition.getQueryWrapper(params));
	}


	@Override
	public List<Dept> listAllDept() {
		return baseMapper.selectList(Condition.getQueryWrapper(new Dept()));
	}

}

