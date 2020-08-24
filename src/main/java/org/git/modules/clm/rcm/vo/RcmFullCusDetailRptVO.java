package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 全客户明细表视图
 *
 * @author zhouweijei
 */
@Data
@ApiModel(value = "RcmFullCusDetailRptVO对象", description = "全客户明细表")
public class RcmFullCusDetailRptVO {

	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	private String customerNum;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String customerName;

	/**
	 * 贷款余额
	 */
	@ApiModelProperty(value = "贷款余额")
	private BigDecimal loanExpBalance;

	/**
	 * 较上月变化
	 */
	@ApiModelProperty(value = "较上月变化")
	private BigDecimal change;
}
