package org.git.modules.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.core.mp.base.BaseEntity;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户角色
 *
 * @author Chill
 */
@Data
@TableName("tb_user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID", required = true)
	private String userId;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色ID", required = true)
	private String roleId;
	/**
	 * 机构ID
	 */
	@ApiModelProperty(value = "机构ID", required = true)
	private String deptId;
	/**
	 * 是否主机构 0 否 1 是
	 */
	@ApiModelProperty(value = "是否主机构 0 否 1 是", required = true)
	private Integer isMasterOrg;
}
