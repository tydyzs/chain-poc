package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 限额预警信息查询条件 视图实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@ApiModel(value = "RcmWarnInfoQueryVO对象", description = "限额预警信息查询条件")
public class RcmWarnInfoQueryVO {

	/**
	 * 预警编号
	 */
	@ApiModelProperty(value = "预警编号")
	private String warnNum;

	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称")
	private String quotaName;

	/**
	 * 限额生效机构
	 */
	@ApiModelProperty(value = "限额生效机构")
	private String useOrgNum;

	/**
	 * 生效机构类型
	 */
	@ApiModelProperty(value = "生效机构类型(后台获取)")
	private String useOrgType;

	/**
	 * 阈值层级(1、观察；2、预警；3、控制)
	 */
	@ApiModelProperty(value = "阈值层级(1、观察；2、预警；3、控制)")
	private String triggerLevel;

	/**
	 * 触发时间区间	（开始）
	 */
	@ApiModelProperty(value = "触发时间（开始）")
	private String triggerTimeStart;

	/**
	 * 触发时间区间	（终止）
	 */
	@ApiModelProperty(value = "触发时间（终止）")
	private String triggerTimeEnd;

	/**
	 * 登录用户机构
	 */
	@ApiModelProperty(value = "登录用户机构(后台获取)")
	private String userOrgNum;

	/**
	 * 登录用户机构类型
	 */
	@ApiModelProperty(value = "登录用户机构类型(后台获取)")
	private String userOrgType;


}
