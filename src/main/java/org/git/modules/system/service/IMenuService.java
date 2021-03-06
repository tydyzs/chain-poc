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
import org.git.modules.system.entity.Menu;
import org.git.modules.system.vo.MenuVO;
import org.git.core.secure.ChainUser;
import org.git.core.tool.support.Kv;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<MenuVO> routes(String roleId, String topMenuId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<MenuVO> routesExt(String roleId, String topMenuId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> buttons(String roleId);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantTree(ChainUser user);

	/**
	 * 顶部菜单树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantTopTree(ChainUser user);

	/**
	 * 数据权限授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantDataScopeTree(ChainUser user);

	/**
	 * 接口权限授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantApiScopeTree(ChainUser user);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 *
	 * @param topMenuIds
	 * @return
	 */
	List<String> topTreeKeys(String topMenuIds);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> dataScopeTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> apiScopeTreeKeys(String roleIds);

	/**
	 * 获取配置的角色权限
	 *
	 * @param user
	 * @return
	 */
	List<Kv> authRoutes(ChainUser user);

	/**
	 * 删除菜单
	 *
	 * @param ids
	 * @return
	 */
	boolean removeMenu(String ids);

	/**
	 * 提交
	 *
	 * @param menu
	 * @return
	 */
	boolean submit(Menu menu);


	/**
	 * 判断菜单是否存在
	 *
	 * @param menu
	 * @return
	 */
	boolean checkExists(Menu menu);
}
