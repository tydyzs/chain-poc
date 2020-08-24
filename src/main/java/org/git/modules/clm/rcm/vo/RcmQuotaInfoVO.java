package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 限额分析-限额管控参数信息
 *
 * @author zhouwj
 * @since 2020-02-24
 */
@Data
@ApiModel(value = "RcmQuotaInfoVO", description = "限额分析-限额管控参数信息")
public class RcmQuotaInfoVO {

	@ApiModelProperty(value = "限额名称")
	private String quotaName;

	@ApiModelProperty(value = "限额编号")
	private String quotaNum;

	@ApiModelProperty(value = "限额指标名称")
	private String quotaIndexName;

	@ApiModelProperty(value = "限额指标编号")
	private String quotaIndexNum;

	@ApiModelProperty(value = "限额已用金额")
	private BigDecimal quotaUsedAmt = BigDecimal.ZERO;

	@ApiModelProperty(value = "限额已用比率")
	private BigDecimal quotaUsedRatio = BigDecimal.ZERO;

	@ApiModelProperty(value = "观察值")
	private BigDecimal observeValue = BigDecimal.ZERO;

	@ApiModelProperty(value = "预警值")
	private BigDecimal warnValue = BigDecimal.ZERO;

	@ApiModelProperty(value = "控制值")
	private BigDecimal controlValue = BigDecimal.ZERO;

	@ApiModelProperty(value = "观察值类型(1.金额 2.百分比)")
	private String observeValueType;

	@ApiModelProperty(value = "预警值类型(1.金额 2.百分比)")
	private String warnValueType;

	@ApiModelProperty(value = "控制值类型(1.金额 2.百分比)")
	private String controlValueType;

	@ApiModelProperty(value = "月份")
	private String totalMonth;

	@ApiModelProperty(value = "年份")
	private String totalYear;

	@ApiModelProperty(value = "子节点")
	private List<RcmQuotaInfoVO> children;

	@ApiModelProperty(value = "单位（万元、亿元、%）")
	private String unitFlag;

	@ApiModelProperty(value = "比上期增长量（环比）")
	private BigDecimal monthToMonthAmt;

	@ApiModelProperty(value = "比上期增长率（环比）")
	private BigDecimal monthToMonthRatio;

}
