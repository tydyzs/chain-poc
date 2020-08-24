package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import org.git.modules.clm.rcm.entity.RcmConfig;

import java.math.BigDecimal;

/**
 * Chain-Boot
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/1/3
 * @since 1.8
 */
public class RcmConfigFullVO extends RcmConfig {

	/**
	 * 阈值层级:CD000223
	 */
	@ApiModelProperty(value = "阈值层级:CD000223")
	@TableField("QUOTA_LEVEL")
	private String quotaLevel;
	/**
	 * 阈值（余额）
	 */
	@ApiModelProperty(value = "阈值（余额）")
	@TableField("QUOTA_CONTROL_AMT")
	private BigDecimal quotaControlAmt;
	/**
	 * 阈值（占比）
	 */
	@ApiModelProperty(value = "阈值（占比）")
	@TableField("QUOTA_CONTROL_RATIO")
	private BigDecimal quotaControlRatio;
	/**
	 * 阈值类型:CD000258
	 */
	@ApiModelProperty(value = "阈值类型:CD000258")
	@TableField("QUOTA_CONTROL_TYPE")
	private String quotaControlType;
	/**
	 * 限额管控节点:CD000259
	 */
	@ApiModelProperty(value = "限额管控节点:CD000259")
	@TableField("CONTROL_NODE")
	private String controlNode;
	/**
	 * 管控节点的应对措施:CD000260
	 */
	@ApiModelProperty(value = "管控节点的应对措施:CD000260")
	@TableField("NODE_MEASURE")
	private String nodeMeasure;
	/**
	 * 应对措施等级:CD000265
	 */
	@ApiModelProperty(value = "应对措施等级:CD000265")
	@TableField("MEASURE_LEVEL")
	private String measureLevel;

}
