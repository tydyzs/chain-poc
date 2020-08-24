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
package org.git.common.cache;

import org.git.modules.clm.front.entity.TerminalConfig;
import org.git.modules.clm.front.service.ITerminalConfigService;
import org.git.modules.clm.front.service.impl.TerminalConfigServiceImpl;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.clm.param.service.IProductService;
import org.git.modules.clm.param.service.impl.CrdServiceImpl;
import org.git.modules.clm.param.service.impl.ProductServiceImpl;
import org.git.modules.system.service.*;
import org.git.core.cache.utils.CacheUtil;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.Menu;
import org.git.modules.system.entity.Role;
import org.git.modules.system.entity.Tenant;

import java.util.List;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 系统缓存
 *
 * @author caohaijie
 */
public class SysCache {
	private static final String MENU_ID = "menu:id:";
	private static final String DEPT_ID = "dept:id:";
	private static final String DEPT_NAME_ID = "deptName:id:";
	private static final String DEPT_NAMES_ID = "deptNames:id:";
	private static final String ROLE_ID = "role:id:";
	private static final String ROLE_NAME_ID = "roleName:id:";
	private static final String ROLE_NAMES_ID = "roleNames:id:";
	private static final String ROLE_ALIAS_ID = "roleAlias:id:";
	private static final String ROLE_ALIASES_ID = "roleAliases:id:";
	private static final String TENANT_ID = "tenant:id:";
	private static final String COMMON_WORK_DATE = "common:workdate:";//营业日期
	private static final String COMMON_BATCH_DATE = "common:batchdate:";//批量日期
	private static final String CRD_ID = "crd:id:";
	private static final String PRODUCT_ID = "product:id:";
	private static final String SYSTEM_ID = "system:id:";

	private static IMenuService menuService;
	private static IDeptService deptService;
	private static IRoleService roleService;
	private static ITenantService tenantService;
	private static ISysDateService sysDateService;
	private static ICrdService crdService;
	private static IProductService productService;
	private static ITerminalConfigService terminalConfigService;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		deptService = SpringUtil.getBean(IDeptService.class);
		roleService = SpringUtil.getBean(IRoleService.class);
		tenantService = SpringUtil.getBean(ITenantService.class);
		sysDateService = SpringUtil.getBean(ISysDateService.class);
		crdService = SpringUtil.getBean(CrdServiceImpl.class);
		productService = SpringUtil.getBean(ProductServiceImpl.class);
		terminalConfigService = SpringUtil.getBean(TerminalConfigServiceImpl.class);

	}

	/**
	 * 获取菜单
	 *
	 * @param id 主键
	 * @return
	 */
	public static Menu getMenu(String id) {
		return CacheUtil.get(SYS_CACHE, MENU_ID, id, () -> menuService.getById(id));
	}

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return
	 */
	public static Dept getDept(String id) {
		return CacheUtil.get(SYS_CACHE, DEPT_ID, id, () -> deptService.getById(id));
	}


	/**
	 * 获取全量机构数据
	 *
	 * @return
	 */
	public static List<Dept> getAllDept() {
		return CacheUtil.get(SYS_CACHE, DEPT_ID, "all", () -> deptService.listAllDept());
	}


	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	public static String getDeptName(String id) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAME_ID, id, () -> deptService.getById(id).getDeptName());
	}

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	public static Role getRole(String id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ID, id, () -> roleService.getById(id));
	}

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	public static String getRoleName(String id) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAME_ID, id, () -> roleService.getById(id).getRoleName());
	}

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	public static String getRoleAlias(String id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIAS_ID, id, () -> roleService.getById(id).getRoleAlias());
	}


	/**
	 * 获取部门名集合
	 *
	 * @param deptIds 主键集合
	 * @return 部门名
	 */
	public static List<String> getDeptNames(String deptIds) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAMES_ID, deptIds, () -> deptService.getDeptNames(deptIds));
	}

	/**
	 * 获取角色名集合
	 *
	 * @param roleIds 主键集合
	 * @return 角色名
	 */
	public static List<String> getRoleNames(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAMES_ID, roleIds, () -> roleService.getRoleNames(roleIds));
	}

	/**
	 * 获取角色别名集合
	 *
	 * @param roleIds 主键集合
	 * @return 角色别名
	 */
	public static List<String> getRoleAliases(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIASES_ID, roleIds, () -> roleService.getRoleAliases(roleIds));
	}

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	public static Tenant getTenant(String id) {
		return CacheUtil.get(SYS_CACHE, TENANT_ID, id, () -> tenantService.getById(id));
	}

	/**
	 * 获取营业日期
	 *
	 * @return Tenant
	 */
	public static String getWorkDate() {
		return CacheUtil.get(SYS_CACHE, COMMON_WORK_DATE, "string", () -> sysDateService.selectSysDate().getWorkDate());
	}

	/**
	 * 获取批量日期
	 *
	 * @return Tenant
	 */
	public static String getBatchDate() {
		return CacheUtil.get(SYS_CACHE, COMMON_BATCH_DATE, "string", () -> sysDateService.selectSysDate().getBatchDate());
	}

	/**
	 * 获取额度品种
	 *
	 * @return
	 */
	public static List<Crd> getAllCrd() {
		return CacheUtil.get(SYS_CACHE, CRD_ID, "all", () -> crdService.listCrd(new Crd()));
	}

	/**
	 * 获取业务品种
	 *
	 * @return
	 */
	public static List<Product> getAllProduct() {
		return CacheUtil.get(SYS_CACHE, PRODUCT_ID, "all", () -> productService.listProduct(new Product()));
	}

	/**
	 * 获取来源系统
	 *
	 * @return
	 */
	public static List<TerminalConfig> getAllTerminalConfig() {
		return CacheUtil.get(SYS_CACHE, PRODUCT_ID, "all", () -> terminalConfigService.listTerminalConfig());
	}



}
