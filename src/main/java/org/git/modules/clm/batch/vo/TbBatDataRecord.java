package org.git.modules.clm.batch.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类
 *
 * @author lijing
 */
@Data
@TableName("TB_BAT_DATA_RECORD")
@ApiModel(value = "批量数据处理记录", description = "批量数据处理记录")
public class TbBatDataRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "RECORD_ID", type = IdType.UUID)
	private String recordId;

	/**
	 * 业务编号
	 */
	@ApiModelProperty(value = "业务编号")
	@TableField("BIZ_NUM")
	private String bizNum;

	/**
	 * 业务类型
	 */
	@ApiModelProperty(value = "业务类型")
	@TableField("BIZ_TYPE")
	private String bizType;

	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "操作类型")
	@TableField("OP_TYPE")
	private String opType;

	/**
	 * 交易状态
	 */
	@ApiModelProperty(value = "交易状态")
	@TableField("STATUS")
	private String status;

	/**
	 * 交易状态码
	 */
	@ApiModelProperty(value = "交易状态码")
	@TableField("CODE")
	private String code;

	/**
	 * 状态描述
	 */
	@ApiModelProperty(value = "状态描述")
	@TableField("MSG")
	private String msg;

	/**
	 * 交易日期
	 */
	@ApiModelProperty(value = "交易日期")
	@TableField("TRAN_DATE")
	private String tranDate;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATE_TIME")
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;

}
