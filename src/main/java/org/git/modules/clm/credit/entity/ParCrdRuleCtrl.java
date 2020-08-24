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
package org.git.modules.clm.credit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 额度管控规则表实体类
 *
 * @author liuye
 * @since 2019-12-03
 */
@Data
@TableName("TB_PAR_CRD_RULE_CTRL")
@ApiModel(value = "ParCrdRuleCtrl对象", description = "额度管控规则表")
public class ParCrdRuleCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键
	*/
		@ApiModelProperty(value = "主键")
		@TableId("SERIAL_ID")
	private String serialId;

	/**
	* 205，302，日终到期，日终对账
	*/
		@ApiModelProperty(value = "205，302，日终到期，日终对账")
		@TableField("EVENT_TYPE_CD")
	private String eventTypeCd;
	/**
	* 交易类型(cd000197)
	*/
		@ApiModelProperty(value = "交易类型(cd000197)")
		@TableField("TRAN_TYPE_CD")
	private String tranTypeCd;
	/**
	* 检查项
	*/
		@ApiModelProperty(value = "检查项")
		@TableField("CHECK_ITEM")
	private String checkItem;
	/**
	* 是否检查 0不检查，1检查
	*/
		@ApiModelProperty(value = "是否检查 0不检查，1检查")
		@TableField("CHECK_FLAG")
	private String checkFlag;
	/**
	* 检查方法
	*/
		@ApiModelProperty(value = "检查方法")
		@TableField("CHECK_METHOD")
	private String checkMethod;
	/**
	* 创建时间
	*/
		@ApiModelProperty(value = "创建时间")
		@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	* 修改时间
	*/
		@ApiModelProperty(value = "修改时间")
		@TableField("UPDATE_TIME")
	private Timestamp updateTime;


}
