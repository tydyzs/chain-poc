package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全客户明细查询条件视图
 * 风险暴露超过1亿元（含）的单一客户查询条件视图
 *
 * @author zhouweijie
 */
@Data
@ApiModel(value = "RcmFullCusDetailQueryVO对象", description = "查询条件")
public class RcmFullCusDetailQueryVO {

	/**
	 * 金额单位
	 */
	@ApiModelProperty(value = "金额单位(页面传入)", required = true)
	private String amtUnit;

	/**
	 * 机构号
	 */
	@ApiModelProperty(value = "查询机构号(页面传入)")
	private String orgNum;

	/**
	 * 查询机构类型
	 */
	@ApiModelProperty(value = "查询机构类型")
	private String orgType;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称(页面传入)")
	private String customerName;

//	/**
//	 * 客户经理
//	 */
//	@ApiModelProperty(value = "客户经理(页面传入)")
//	private String userNum;
//
//	/**
//	 * 管护机构
//	 */
//	@ApiModelProperty(value = "管护机构(页面传入)")
//	private String manageOrgNum;

	/**
	 * 当前登录用户机构
	 */
	@ApiModelProperty(value = "当前登录用户机构")
	private String userOrgNum;

	/**
	 * 当前登录用户机构类型
	 */
	@ApiModelProperty(value = "当前登录用户机构类型")
	private String userOrgType;

}
