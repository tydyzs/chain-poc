/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
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
 *  Author: 高伟达武汉事业部
 */
package org.git.modules.clm.rcm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 限额预警信息表实体类
 *
 * @author git
 * @since 2019-12-04
 */
@Data
@TableName("TB_RCM_WARN_INFO")
@ApiModel(value = "RcmWarnInfo对象", description = "限额预警信息表")
public class RcmWarnInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 预警信息编号
	 */
	@ApiModelProperty(value = "预警信息编号")
	@TableId("WARN_NUM")
	private String warnNum;
	/**
	 * 集中度限额编号
	 */
	@ApiModelProperty(value = "集中度限额编号")
	@TableField("QUOTA_NUM")
	private String quotaNum;
	/**
	 * 限额名称
	 */
	@ApiModelProperty(value = "限额名称")
	@TableField("QUOTA_NAME")
	private String quotaName;
	/**
	 * 限额指标编号
	 */
	@ApiModelProperty(value = "限额指标编号")
	@TableField("QUOTA_INDEX_NUM")
	private String quotaIndexNum;
	/**
	 * 限额已用金额-预警时点数
	 */
	@ApiModelProperty(value = "限额已用金额-预警时点数")
	@TableField("QUOTA_USED_AMT")
	private BigDecimal quotaUsedAmt;
	/**
	 * 限额可用金额-预警时点数
	 */
	@ApiModelProperty(value = "限额可用金额-预警时点数")
	@TableField("QUOTA_FREE_AMT")
	private BigDecimal quotaFreeAmt;
	/**
	 * 限额已用比率-预警时点数
	 */
	@ApiModelProperty(value = "限额已用比率-预警时点数")
	@TableField("QUOTA_USED_RATIO")
	private BigDecimal quotaUsedRatio;
	/**
	 * 限额可用比率-预警时点数
	 */
	@ApiModelProperty(value = "限额可用比率-预警时点数")
	@TableField("QUOTA_FREE_RATIO")
	private BigDecimal quotaFreeRatio;
	/**
	 * 限额总额
	 */
	@ApiModelProperty(value = "限额总额")
	@TableField("QUOTA_TOTAL_SUM")
	private BigDecimal quotaTotalSum;
	/**
	 * 限额总额类型(1-资本净额；2-一级资本净额；3-我行授信总额；)
	 */
	@ApiModelProperty(value = "限额总额类型(1-资本净额；2-一级资本净额；3-我行授信总额；)")
	@TableField("QUOTA_TOTAL_TYPE")
	private String quotaTotalType;
	/**
	 * 限额生效机构
	 */
	@ApiModelProperty(value = "限额生效机构")
	@TableField("USE_ORG_NUM")
	private String useOrgNum;
	/**
	 * 触发时间
	 */
	@ApiModelProperty(value = "触发时间")
	@TableField("TRIGGER_TIME")
	private Timestamp triggerTime;
	/**
	 * 阈值层级(1、观察；2、预警；3、控制)
	 */
	@ApiModelProperty(value = "阈值层级(1、观察；2、预警；3、控制)")
	@TableField("TRIGGER_LEVEL")
	private String triggerLevel;
	/**
	 * 预警阀值(余额)
	 */
	@ApiModelProperty(value = "预警阀值(余额)")
	@TableField("QUOTA_CONTROL_AMT")
	private BigDecimal quotaControlAmt;
	/**
	 * 预警阀值(比例)
	 */
	@ApiModelProperty(value = "预警阀值(比例)")
	@TableField("QUOTA_CONTROL_RATIO")
	private BigDecimal quotaControlRatio;
	/**
	 * 触发当前值(余额)
	 */
	@ApiModelProperty(value = "触发当前值(余额)")
	@TableField("TRIGGER_AMT")
	private BigDecimal triggerAmt;
	/**
	 * 触发当前值(占比)
	 */
	@ApiModelProperty(value = "触发当前值(占比)")
	@TableField("TRIGGER_RATIO")
	private BigDecimal triggerRatio;
	/**
	 * 触发管控节点
	 */
	@ApiModelProperty(value = "触发管控节点")
	@TableField("TRIGGER_CONTROL_NODE")
	private String triggerControlNode;
	/**
	 * 应对措施(1-提示；2-触发报备；3-禁止操作)
	 */
	@ApiModelProperty(value = "应对措施(1-提示；2-触发报备；3-禁止操作)")
	@TableField("NODE_MEASURE")
	private String nodeMeasure;
	/**
	 * 对应措施等级(1-一级；2-二级；3。三级)
	 */
	@ApiModelProperty(value = "对应措施等级(1-一级；2-二级；3。三级)")
	@TableField("MEASURE_LEVEL")
	private String measureLevel;
	/**
	 * 关联业务流水号
	 */
	@ApiModelProperty(value = "关联业务流水号")
	@TableField("TRAN_SEQ_SN")
	private String tranSeqSn;
	/**
	 * 关联业务编号
	 */
	@ApiModelProperty(value = "关联业务编号")
	@TableField("BIZ_NUM")
	private String bizNum;
	/**
	 * 业务类型(1、额度申请   2、合同申请   3、放款申请   4、同业业务申请)
	 */
	@ApiModelProperty(value = "业务类型(1、额度申请   2、合同申请   3、放款申请   4、同业业务申请)")
	@TableField("BIZ_TYPE")
	private String bizType;
	/**
	 * 业务客户经理
	 */
	@ApiModelProperty(value = "业务客户经理")
	@TableField("USER_NUM")
	private String userNum;
	/**
	 * 业务网点机构
	 */
	@ApiModelProperty(value = "业务网点机构")
	@TableField("ORG_NUM")
	private String orgNum;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	@TableField("CUSTOMER_NUM")
	private String customerNum;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	@TableField("PARTY_NAME")
	private String partyName;
	/**
	 * 业务品种
	 */
	@ApiModelProperty(value = "业务品种")
	@TableField("PRODUCT_NUM")
	private String productNum;
	/**
	 * 发生金额
	 */
	@ApiModelProperty(value = "发生金额")
	@TableField("AMT")
	private BigDecimal amt;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableField("CURRENCY_CD")
	private String currencyCd;
	/**
	 * 发生年份
	 */
	@ApiModelProperty(value = "发生年份")
	@TableField("RECO_YEAR")
	private String recoYear;
	/**
	 * 发生月份
	 */
	@ApiModelProperty(value = "发生月份")
	@TableField("RECO_MONTH")
	private String recoMonth;
	/**
	 * 备用字段1
	 */
	@ApiModelProperty(value = "备用字段1")
	@TableField("CREATE_TIME")
	private Timestamp createTime;
	/**
	 * 维护时间
	 */
	@ApiModelProperty(value = "维护时间")
	@TableField("UPDATE_TIME")
	private Timestamp updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("REMARK")
	private String remark;

	@ApiModelProperty(value = "统计口径")
	@TableField(exist = false)
	private String quotaIndexCaliber;


}
