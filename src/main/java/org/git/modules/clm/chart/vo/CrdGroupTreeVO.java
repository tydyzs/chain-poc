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
package org.git.modules.clm.chart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.core.tool.node.INode;


import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "对象", description = "CrdGroupTreeVO")
public class CrdGroupTreeVO implements INode {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private String id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private String parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	/**
	 * 母公司名称
	 */
	@ApiModelProperty(value = "母公司名称")
	@TableId("TITLE")
	private String TITLE;

	/**
	 *子公司名称
	 */
	@ApiModelProperty(value = "子公司名称")
	@TableId("member_customer_name")
	private String memberCustomerName;

	/**
	 * 股东名称
	 */
	@ApiModelProperty(value = "股东名称")
	@TableId("cust_name")
	private String custName;

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;

	@ApiModelProperty(value = "成员客户编号")
	@TableField("MEMBER_CUSTOMER_NUM")
	private String memberCustomerNum;

	/**
	 * 关系客户编号
	 */
	@ApiModelProperty(value = "关系客户编号")
	@TableField("REL_CUSTOMER_NUM")
	private String relCustomerNum;
}
