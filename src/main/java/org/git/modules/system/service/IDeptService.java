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
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept,List<String> loginUserDeptIds);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 管理机构的树形结构
	 *
	 * @param orgNum
	 * @return
	 */
	List<DeptVO> treeManage(String orgNum,String isManage);

	/**
	 * 管理机构的树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> treeManage2(String tenantId);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * 删除部门
	 *
	 * @param ids
	 * @return
	 */
	boolean removeDept(String ids);

	/**
	 * 提交
	 *
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);

	/**
	 * 是否成员行
	 *
	 * @param id
	 * @return
	 */
	boolean isMemberBank(String id);

	/**
	 * 向上查询某类型的机构
	 *
	 * @param id
	 * @param deptLevel
	 * @return
	 */
	Dept upperDeptByType(String id, String deptLevel);

	/**
	 * 获取省联社
	 */
	Dept selectProvincialCooperative();


	/**
	 * 获取全部机构信息
	 *
	 * @return
	 */
	List<Dept> listAllDept();
}
