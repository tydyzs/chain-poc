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
package org.git.modules.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.modules.system.entity.RoleMenu;
import org.git.modules.system.entity.User;
import org.git.modules.system.entity.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图实体类
 *
 * @author Chill
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserROleVO对象", description = "UserROleVO对象")
public class UserRoleVO extends UserRole {

	private static final long serialVersionUID = 1L;

	/**
	 * 机构类型名称
	 */
	@ApiModelProperty(value = "机构类型名称")
	private String deptCategoryName;
	/**
	 * 机构全称
	 */
	@ApiModelProperty(value = "机构全称")
	private String fullName;

	/**
	 * 机构名
	 */
	@ApiModelProperty(value = "机构名")
	private String deptName;

	/**
	 * 机构等级名称
	 */
	@ApiModelProperty(value = "机构等级名称")
	private String deptLevelName;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	/**
	 * 机构等级
	 */
	@ApiModelProperty(value = "机构等级")
	private String deptLevel;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDeptCategoryName() {
		return deptCategoryName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDeptLevelName() {
//		if(StringUtils.isBlank(deptLevelName) && StringUtils.isNotBlank(this.getDeptLevel())){
//			DictCache.getValue(this.getDeptLevel())
//		}
		return deptLevelName;
	}

	public String getRoleName() {
		if(StringUtils.isBlank(roleName) && StringUtils.isNotBlank(this.getRoleId())){
			roleName = Arrays.stream(this.getRoleId().split(","))
				.map(roleid -> SysCache.getRoleName(roleid))
				.collect(Collectors.joining(","));
		}
		return roleName;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptCategoryName(String deptCategoryName) {
		this.deptCategoryName = deptCategoryName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDeptLevelName(String deptLevelName) {
		this.deptLevelName = deptLevelName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
}
