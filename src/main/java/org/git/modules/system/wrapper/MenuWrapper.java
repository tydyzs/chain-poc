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
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.system.entity.Menu;
import org.git.modules.system.service.IMenuService;
import org.git.modules.system.service.impl.MenuServiceImpl;
import org.git.modules.system.vo.MenuVO;
import org.git.core.mp.support.BaseEntityWrapper;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.utils.BeanUtil;
import org.git.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段3
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private IMenuService menuService = SpringUtil.getBean(MenuServiceImpl.class);

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		assert menuVO != null;
		if (Func.equals(menu.getParentId(), ChainConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(ChainConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = SysCache.getMenu(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		String category = DictCache.getValue("menu_category", menuVO.getCategory());
		String action = DictCache.getValue("button_func", menuVO.getAction().toString());
		String open = DictCache.getValue("yes_no", menuVO.getIsOpen().toString());
		menuVO.setCategoryName(category);
		menuVO.setActionName(action);
		menuVO.setIsOpenName(open);
		return menuVO;
	}

	/**
	 * 转换类，将menu翻译后转换为MenuVO
	 * cc 2020-02-20
	 *
	 * @param menu
	 * @return MenuVO
	 */
	public MenuVO transVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		assert menuVO != null;
		String category = DictCache.getValue("menu_category", menuVO.getCategory());
		menuVO.setCategoryName(category);


		return menuVO;
	}

	/**
	 * 转换类，将menu翻译后转换为MenuVO
	 * cc 2020-02-20
	 *
	 * @param menus
	 * @return MenuVO
	 */
	public List<MenuVO> transVO(List<Menu> menus) {
		List<MenuVO> menuVOs = new ArrayList<MenuVO>();
		//将menu转换成menuVO
		for (Menu menu : menus) {
			MenuVO menuVO = MenuWrapper.build().transVO(menu);
			menuVOs.add(menuVO);
		}
		//将menuVO转换成树状结构
		menuVOs = ForestNodeMerger.merge(menuVOs);
		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
		for (MenuVO menuVO : menuVOs) {
			Menu menuT = new Menu();
			menuT.setParentId(menuVO.getId());
			int n = menuService.count(Condition.getQueryWrapper(menuT));
			if (n > 0) {
				menuVO.setHasChildren(true);
			} else {
				menuVO.setHasChildren(false);
			}
			menuVOList.add(menuVO);
		}
		return menuVOs;
	}

	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
