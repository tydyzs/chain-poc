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
package org.git.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.vo.DeptVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	List<DeptVO> selectDeptPage(IPage page, DeptVO dept,String[] deptIds);

	/**
	 * 获取树形节点
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 获取树形节点（只包含管理机构）
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> treeManage2(String tenantId);
/**
	 * 获取树形节点（只包含管理机构）
	 *
	 * @param orgNum
	 * @return
	 */
	List<DeptVO> treeManage(@Param("orgNum") String orgNum,String isManage);
	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(String[] ids);

	/**
	 * 向上查询省联社
	 * @param id
	 * @param deptLevel
	 * @return
	 */
	Dept upperDeptByType(@Param("id") String id, @Param("deptLevel") String deptLevel);
}
