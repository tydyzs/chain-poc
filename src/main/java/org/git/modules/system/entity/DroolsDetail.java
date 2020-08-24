package org.git.modules.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色
 *
 * @author Chill
 */
@Data
@TableName("chain_drools_detail")
public class DroolsDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String ruleId;

	private String ruleName;

	private String rulePriority;

	private String ruleCondition;

	private String conditionCn;

	private String ruleExpression;

	private String expressionCn;
	/**
	 * 删除
	 */
	@ApiModelProperty(value = "删除")
	private Integer isDeleted;

}
