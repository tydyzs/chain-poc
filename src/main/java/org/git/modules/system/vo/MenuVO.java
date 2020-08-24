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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.core.tool.node.INode;
import org.git.modules.system.entity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MenuVO对象", description = "MenuVO对象")
public class MenuVO extends Menu implements INode {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private String id;


	@ApiModelProperty(value = "父节点ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private String parentId;

	@ApiModelProperty(value = "是否有字节点")
	private boolean hasChildren = true;


	@ApiModelProperty(value = "子孙节点")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}


	@ApiModelProperty(value = "上级菜单")
	private String parentName;


	@ApiModelProperty(value = "菜单类型")
	private String categoryName;

	@ApiModelProperty(value = "按钮功能")
	private String actionName;


	@ApiModelProperty(value = "是否新窗口打开")
	private String isOpenName;
}
