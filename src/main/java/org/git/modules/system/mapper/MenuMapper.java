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
import org.git.modules.system.dto.MenuDTO;
import org.git.modules.system.entity.Menu;
import org.git.modules.system.vo.MenuVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	List<MenuVO> selectMenuPage(IPage page, MenuVO menu);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantTree();

	/**
	 * 授权树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantTreeByRole(List<String> roleId);

	/**
	 * 顶部菜单树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantTopTree();

	/**
	 * 顶部菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantTopTreeByRole(List<String> roleId);

	/**
	 * 数据权限授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantDataScopeTree();

	/**
	 * 接口权限授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantApiScopeTree();

	/**
	 * 数据权限授权树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantDataScopeTreeByRole(List<String> roleId);

	/**
	 * 接口权限授权树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantApiScopeTreeByRole(List<String> roleId);

	/**
	 * 所有菜单
	 *
	 * @return
	 */
	List<Menu> allMenu();

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<Menu> roleMenu(List<String> roleId, String topMenuId);

	/**
	 * 所有菜单
	 *
	 * @return
	 */
	List<Menu> allMenuExt();

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<Menu> roleMenuExt(List<String> roleId, String topMenuId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> routes(List<String> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @return
	 */
	List<Menu> allButtons();

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> buttons(List<String> roleId);

	/**
	 * 获取配置的角色权限
	 *
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<String> roleIds);
}
