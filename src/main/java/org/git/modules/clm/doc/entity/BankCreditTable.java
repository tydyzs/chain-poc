package org.git.modules.clm.doc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * chain-boot
 * 同业额度表
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/2/3
 * @since 1.8
 */
@Data
@ApiModel(value = "同业额度表对象", description = "同业额度表")
public class BankCreditTable implements Serializable {

	@ApiModelProperty(value = "机构编号")
	private String customerNum;

	@Excel(name = "机构名称")
	@ApiModelProperty(value = "机构名称")
	private String customerName;

	@Excel(name = "授信开始日")
	@ApiModelProperty(value = "授信开始日")
	private String beginDate;//

	@Excel(name = "授信截止日")
	@ApiModelProperty(value = "授信截止日")
	private String endDate;//

	//--综合授信额度 包括以下额度类型
	//03010001	同业存单额度
	//03010002	债券回购额度
	//03010003	存放同业额度
	//03010004	同业拆借额度
	//03010005	同业借款额度
	//03010006	票据贴现额度（目前报表没有）
	//03010007	债券投资额度


	@Excel(name = "授信总额")
	@ApiModelProperty(value = "合计-授信总额")
	private String limitCredit;//

	@Excel(name = "已用额度")
	@ApiModelProperty(value = "合计-已用额度")
	private String limitUsed;//

	@Excel(name = "剩余额度")
	@ApiModelProperty(value = "合计-剩余额度")
	private String limitAvi;//

	@Excel(name = "已圈额度")
	@ApiModelProperty(value = "合计-已圈额度")
	private String limitEarmark;//

	//03010001	同业存单额度
	@Excel(name = "A授信总额")
	@ApiModelProperty(value = "同业存单-授信总额")
	private String taLimitCredit;//

	@Excel(name = "A已用额度")
	@ApiModelProperty(value = "同业存单-已用额度")
	private String taLimitUsed;//

	@Excel(name = "A剩余额度")
	@ApiModelProperty(value = "同业存单-剩余额度")
	private String taLimitAvi;//

	@Excel(name = "A已圈额度")
	@ApiModelProperty(value = "同业存单-已圈额度")
	private String taLimitEarmark;//

	//03010002	债券回购额度
	@Excel(name = "B授信总额")
	@ApiModelProperty(value = "债券回购-授信总额")
	private String tbLimitCredit;//

	@Excel(name = "B已用额度")
	@ApiModelProperty(value = "债券回购-已用额度")
	private String tbLimitUsed;//

	@Excel(name = "B剩余额度")
	@ApiModelProperty(value = "债券回购-剩余额度")
	private String tbLimitAvi;//

	@Excel(name = "B已圈额度")
	@ApiModelProperty(value = "债券回购-已圈额度")
	private String tbLimitEarmark;//

	//03010003	存放同业额度
	@Excel(name = "C授信总额")
	@ApiModelProperty(value = "存放同业-授信总额")
	private String tcLimitCredit;//

	@Excel(name = "C已用额度")
	@ApiModelProperty(value = "存放同业-已用额度")
	private String tcLimitUsed;//

	@Excel(name = "C剩余额度")
	@ApiModelProperty(value = "存放同业-剩余额度")
	private String tcLimitAvi;//

	@Excel(name = "C已圈额度")
	@ApiModelProperty(value = "存放同业-已圈额度")
	private String tcLimitEarmark;//

	//03010004	同业拆借额度
	@Excel(name = "D授信总额")
	@ApiModelProperty(value = "同业拆借-授信总额")
	private String tdLimitCredit;//

	@Excel(name = "D已用额度")
	@ApiModelProperty(value = "同业拆借-已用额度")
	private String tdLimitUsed;//

	@Excel(name = "D剩余额度")
	@ApiModelProperty(value = "同业拆借-剩余额度")
	private String tdLimitAvi;//

	@Excel(name = "D已圈额度")
	@ApiModelProperty(value = "同业拆借-已圈额度")
	private String tdLimitEarmark;//

	//03010005	同业借款额度
	@Excel(name = "E授信总额")
	@ApiModelProperty(value = "同业借款-授信总额")
	private String teLimitCredit;//

	@Excel(name = "E已用额度")
	@ApiModelProperty(value = "同业借款-已用额度")
	private String teLimitUsed;//

	@Excel(name = "E剩余额度")
	@ApiModelProperty(value = "同业借款-剩余额度")
	private String teLimitAvi;//

	@Excel(name = "E已圈额度")
	@ApiModelProperty(value = "同业借款-已圈额度")
	private String teLimitEarmark;//

	//03010006	票据贴现额度
	@Excel(name = "F授信总额")
	@ApiModelProperty(value = "票据贴现-授信总额")
	private String tfLimitCredit;//
	@Excel(name = "F已用额度")
	@ApiModelProperty(value = "票据贴现-已用额度")
	private String tfLimitUsed;//
	@Excel(name = "F剩余额度")
	@ApiModelProperty(value = "票据贴现-剩余额度")
	private String tfLimitAvi;//
	@Excel(name = "F已圈额度")
	@ApiModelProperty(value = "票据贴现-已圈额度")
	private String tfLimitEarmark;//

	//03010007	债券投资额度
	@Excel(name = "G授信总额")
	@ApiModelProperty(value = "债券投资-授信总额")
	private String tgLimitCredit;//

	@Excel(name = "G已用额度")
	@ApiModelProperty(value = "债券投资-已用额度")
	private String tgLimitUsed;//

	@Excel(name = "G剩余额度")
	@ApiModelProperty(value = "债券投资-剩余额度")
	private String tgLimitAvi;//

	@Excel(name = "G已圈额度")
	@ApiModelProperty(value = "债券投资-已圈额度")
	private String tgLimitEarmark;//

}
