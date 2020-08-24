package org.git.modules.clm.batch.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("TT_CRD_BILL_INFO_SOURCE")
@ApiModel(value = "TtCrdBillInfoSource对象", description = "银票信息落地表")
public class TtCrdBillInfoSourceVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value = "合同编号")
	@TableId("CONTRACT_NUM")
	private String contractNum;

	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	@TableId("CURRENCY_CD")
	private String currencyCd;

	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	@TableId("EXCHANGE_RATE")
	private BigDecimal exchangeRate;

	/**
	 * 客户经理
	 */
	@ApiModelProperty(value = "客户经理")
	@TableId("USER_NUM")
	private String userNum;

	/**
	 * 机构号
	 */
	@ApiModelProperty(value = "机构号")
	@TableId("ORG_NUM")
	private String orgNum;

	/**
	 * 票据号
	 */
	@ApiModelProperty(value = "票据号")
	@TableId("BILL_NUM")
	private String billNum;


	/**
	 * 借据号
	 */
	@ApiModelProperty(value = "借据号")
	@TableId("SUMMARY_NUM")
	private String summaryNum;

	/**
	 * 票据类型
	 */
	@ApiModelProperty(value = "票据类型")
	@TableId("BILL_TYPE")
	private String billType;

	/**
	 * 票据状态
	 */
	@ApiModelProperty(value = "票据状态")
	@TableId("BILL_STATUS")
	private String billStatus;

	/**
	 * 票面金额
	 */
	@ApiModelProperty(value = "票面金额")
	@TableId("SUMMARY_AMT")
	private BigDecimal summaryAmt;

	/**
	 * 票据起期
	 */
	@ApiModelProperty(value = "票据起期")
	@TableId("BEGIN_DATE")
	private String beginDate;

	/**
	 * 票据止期
	 */
	@ApiModelProperty(value = "票据止期")
	@TableId("END_DATE")
	private String endDate;

	/**
	 * 保证金比例
	 */
	@ApiModelProperty(value = "保证金比例")
	@TableId("DEPOSIT_RATIO")
	private BigDecimal depositRatio;

	/**
	 * 担保方式
	 */
	@ApiModelProperty(value = "担保方式")
	@TableId("GUARANTEE_TYPE")
	private String guaranteeType;

	/**
	 * 主担保方式
	 */
	@ApiModelProperty(value = "主担保方式")
	@TableId("MAIN_GUARANTEE_TYPE")
	private String mainGuaranteeType;

	/**
	 * 承兑行ECIF客户号
	 */
	@ApiModelProperty(value = "承兑行ECIF客户号")
	@TableId("ACCEPTOR_ECIF_NUM")
	private String acceptorEcifNum;

	/**
	 * 出票人名称
	 */
	@ApiModelProperty(value = "出票人名称")
	@TableId("DRAWER_NAME")
	private String drawerName;

	/**
	 * 出票人账号
	 */
	@ApiModelProperty(value = "出票人账号")
	@TableId("DRAWER_ACCT")
	private String drawerAcct;

	/**
	 * 出票人开户行行号
	 */
	@ApiModelProperty(value = "出票人开户行行号")
	@TableId("DRAWER_BANK_NUM")
	private String drawerBankNum;

	/**
	 * 出票人开户行行名
	 */
	@ApiModelProperty(value = "出票人开户行行名")
	@TableId("DRAWER_BANK_NAME")
	private String drawerBankName;

	/**
	 * 付款行名称
	 */
	@ApiModelProperty(value = "付款行名称")
	@TableId("PAY_NAME")
	private String payName;

	/**
	 * 付款行行号
	 */
	@ApiModelProperty(value = "付款行行号")
	@TableId("PAY_ACCT")
	private String payAcct;

	/**
	 * 收款人名称
	 */
	@ApiModelProperty(value = "收款人名称")
	@TableId("PAYEE_NAME")
	private String payeeName;

	/**
	 * 收款人账号
	 */
	@ApiModelProperty(value = "收款人账号")
	@TableId("PAYEE_ACCT")
	private String payeeAcct;

	/**
	 * 收款人开户行行号
	 */
	@ApiModelProperty(value = "收款人开户行行号")
	@TableId("PAYEE_BANK_NUM")
	private String payeeBankNum;

	/**
	 * 收款人开户行行名
	 */
	@ApiModelProperty(value = "收款人开户行行名")
	@TableId("PAYEE_BANK_NAME")
	private String payeeBankName;
}
