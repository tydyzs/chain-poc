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
import lombok.AllArgsConstructor;
import org.git.core.mp.support.Condition;
import org.git.core.tool.api.R;
import org.git.modules.system.dto.MenuDTO;
import org.git.modules.system.entity.Menu;
import org.git.modules.system.entity.RoleMenu;
import org.git.modules.system.entity.RoleScope;
import org.git.modules.system.entity.TopMenuSetting;
import org.git.modules.system.mapper.MenuMapper;
import org.git.modules.system.service.IMenuService;
import org.git.modules.system.service.IRoleMenuService;
import org.git.modules.system.service.IRoleScopeService;
import org.git.modules.system.service.ITopMenuSettingService;
import org.git.modules.system.vo.MenuVO;
import org.git.core.secure.ChainUser;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.support.Kv;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.system.wrapper.MenuWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.git.core.cache.constant.CacheConstant.MENU_CACHE;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	private IRoleMenuService roleMenuService;
	private IRoleScopeService roleScopeService;
	private ITopMenuSettingService topMenuSettingService;

	@Override
	public IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu) {
		return page.setRecords(baseMapper.selectMenuPage(page, menu));
	}

	@Override
	public List<MenuVO> routes(String roleId, String topMenuId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus = (SecureUtil.isAdministrator() && Func.isEmpty(topMenuId)) ? allMenus : baseMapper.roleMenu(Func.toStrList(roleId), topMenuId);
		return buildRoutes(allMenus, roleMenus);
	}

	@Override
	public List<MenuVO> routesExt(String roleId, String topMenuId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenuExt();
		List<Menu> roleMenus = baseMapper.roleMenuExt(Func.toStrList(roleId), topMenuId);
		return buildRoutes(allMenus, roleMenus);
	}

	private List<MenuVO> buildRoutes(List<Menu> allMenus, List<Menu> roleMenus) {
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), "1")).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	private void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = (SecureUtil.isAdministrator()) ? baseMapper.allButtons() : baseMapper.buttons(Func.toStrList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<MenuVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<MenuVO> grantTree(ChainUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toStrList(user.getRoleId())));
	}

	@Override
	public List<MenuVO> grantTopTree(ChainUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID) ? baseMapper.grantTopTree() : baseMapper.grantTopTreeByRole(Func.toStrList(user.getRoleId())));
	}

	@Override
	public List<MenuVO> grantDataScopeTree(ChainUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID) ? baseMapper.grantDataScopeTree() : baseMapper.grantDataScopeTreeByRole(Func.toStrList(user.getRoleId())));
	}

	@Override
	public List<MenuVO> grantApiScopeTree(ChainUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID) ? baseMapper.grantApiScopeTree() : baseMapper.grantApiScopeTreeByRole(Func.toStrList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toStrList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> topTreeKeys(String topMenuIds) {
		List<TopMenuSetting> settings = topMenuSettingService.list(Wrappers.<TopMenuSetting>query().lambda().in(TopMenuSetting::getTopMenuId, Func.toStrList(topMenuIds)));
		return settings.stream().map(setting -> Func.toStr(setting.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> dataScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().in(RoleScope::getRoleId, Func.toStrList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	public List<String> apiScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().in(RoleScope::getRoleId, Func.toStrList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	@Cacheable(cacheNames = MENU_CACHE, key = "'auth:routes:' + #user.roleId")
	public List<Kv> authRoutes(ChainUser user) {
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toStrList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.create().set(route.getPath(), Kv.create().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

	@Override
	public boolean removeMenu(String ids) {
		Integer cnt = baseMapper.selectCount(Wrappers.<Menu>query().lambda().in(Menu::getParentId, Func.toStrList(ids)));
		if (cnt > 0) {
			throw new ApiException("请先删除子节点!");
		}
		return removeByIds(Func.toStrList(ids));
	}

	@Override
	public boolean submit(Menu menu) {
		menu.setIsDeleted(ChainConstant.DB_NOT_DELETED);
		return saveOrUpdate(menu);
	}

	@Override
	public boolean checkExists(Menu menu) {
		if (StringUtil.isEmpty(menu.getId())) {//新增时
			Menu menuT = new Menu();
			menuT.setCode(menu.getCode());
			List<Menu> list = this.list(Condition.getQueryWrapper(menuT));
			if (list != null && list.size() > 0) {
				return true;
			}
		} else {//修改时
			Menu menuT = this.getById(menu.getId());
			if (menuT != null && menuT.getCode().equals(menu.getCode())) {//没有修改菜单代码
				return false;
			}
			menuT = new Menu();
			menuT.setCode(menu.getCode());
			List<Menu> list = this.list(Condition.getQueryWrapper(menuT));
			if (list != null && list.size() > 0) {
				return true;
			}
		}
		return false;
	}

}
