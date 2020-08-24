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
import lombok.AllArgsConstructor;
import org.git.modules.system.entity.TopMenu;
import org.git.modules.system.entity.TopMenuSetting;
import org.git.modules.system.mapper.TopMenuMapper;
import org.git.modules.system.service.ITopMenuService;
import org.git.modules.system.service.ITopMenuSettingService;
import org.git.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 顶部菜单表 服务实现类
 *
 * @author Chain
 * @since 2019-07-14
 */
@Service
@AllArgsConstructor
public class TopMenuServiceImpl extends BaseServiceImpl<TopMenuMapper, TopMenu> implements ITopMenuService {

	private ITopMenuSettingService topMenuSettingService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<String> topMenuIds, @NotEmpty List<String> menuIds) {
		// 删除顶部菜单配置的菜单集合
		topMenuSettingService.remove(Wrappers.<TopMenuSetting>update().lambda().in(TopMenuSetting::getTopMenuId, topMenuIds));
		// 组装配置
		List<TopMenuSetting> menuSettings = new ArrayList<>();
		topMenuIds.forEach(topMenuId -> menuIds.forEach(menuId -> {
			TopMenuSetting menuSetting = new TopMenuSetting();
			menuSetting.setTopMenuId(topMenuId);
			menuSetting.setMenuId(menuId);
			menuSettings.add(menuSetting);
		}));
		// 新增配置
		topMenuSettingService.saveBatch(menuSettings);
		return true;
	}
}
