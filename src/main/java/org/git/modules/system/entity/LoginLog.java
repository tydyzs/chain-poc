package org.git.modules.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色
 *
 * @author Chill
 */
@Data
@TableName("tb_login_log")
public class LoginLog implements Serializable  {

	private static final long serialVersionUID = 1L;



	/** 操作类型:登录 */
	public final  static  String ACTION_TYPE_LOGIN = "1";
	/** 操作类型:登出 */
	public final  static  String ACTION_TYPE_LOGOUT = "2";
	/** 操作类型:强制退出 */
	public final  static  String ACTION_TYPE_FORCED_OUT = "3";

	/** 主键 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/** 用户帐号 */
	@ApiModelProperty(value = "用户帐号",required = true)
	private String account;
	/** 用户名称 */
	@ApiModelProperty(value = "用户名称",required = true)
	private String userName;
	/** 登录机构名称 */
	@ApiModelProperty(value = "登录机构名称",required = false)
	private String orgName;
	/** 操作时间 */
	@ApiModelProperty(value = "操作时间",required = true)
	private String actionDatetime;
	/** 操作客户端IP */
	@ApiModelProperty(value = "登录客户端IP",required = false)
	private String actionClientIp;
	/** 操作类型 */
	@ApiModelProperty(value = "操作类型",required = true)
	private String actionType;

}
