/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
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
 *  Author: 高伟达武汉事业部
 */
package org.git.test;


import org.git.develop.support.ChainCodeGenerator;

/**
 * 代码生成器
 *
 * @author Chill
 */
public class CodeGenerator {

	/**
	 * 代码生成的模块名
	 */
	public static String CODE_NAME = "测试代码";
	/**
	 * 代码所在服务名
	 */
	public static String SERVICE_NAME = "git-demo";
	/**
	 * 代码生成的包名
	 */
	public static String PACKAGE_NAME = "org.git.modules.clm.credit";
	/**
	 * 前端代码生成所属系统
	 */
	public static String SYSTEM_NAME = "saber";

	/**
	 * 后端代码生成地址
	 */
	public static String PACKAGE_DIR = "D:\\git_workspace\\demo\\service";

	/**
	 * 前端代码生成地址
	 */
	public static String PACKAGE_WEB_DIR = "D:\\idea_workspace\\demo\\web";
	/**
	 * 需要去掉的表前缀tb_front_terminal_config
	 */
	public static String[] TABLE_PREFIX = {""};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	public static String[] INCLUDE_TABLES = {"TB_CRD_SUM", "TB_CRD_STATIS", "tb_crd_statis_crdpt",
		"tb_crd_statis_uscale", "tb_crd_statis_custype","tb_crd_Statis_gtype","tb_crd_statis_industry","tb_crd_statis_product"};

	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	public static String[] EXCLUDE_TABLES = {};
	/**
	 * 是否包含基础业务字段
	 */
	public static Boolean HAS_SUPER_ENTITY = Boolean.FALSE;
	/**
	 * 基础业务字段
	 */
	public static String[] SUPER_ENTITY_COLUMNS = {"id", "create_time", "create_user", "create_dept", "update_time", "update_user", "status", "is_deleted"};

	/**
	 * RUN THIS
	 */
	public static void main(String[] args) {
		ChainCodeGenerator generator = new ChainCodeGenerator();
		generator.setCodeName(CODE_NAME);
		generator.setServiceName(SERVICE_NAME);
		generator.setSystemName(SYSTEM_NAME);
		generator.setPackageName(PACKAGE_NAME);
		generator.setPackageDir(PACKAGE_DIR);
		generator.setPackageWebDir(PACKAGE_WEB_DIR);
		generator.setTablePrefix(TABLE_PREFIX);
		generator.setIncludeTables(INCLUDE_TABLES);
		generator.setExcludeTables(EXCLUDE_TABLES);
		generator.setHasSuperEntity(HAS_SUPER_ENTITY);
		generator.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
		generator.run();
	}

}
