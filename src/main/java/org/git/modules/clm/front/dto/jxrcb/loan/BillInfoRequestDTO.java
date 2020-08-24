package org.git.modules.clm.front.dto.jxrcb.loan;

import com.baomidou.mybatisplus.annotation.TableField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM109_信贷系统-银票签发(请求报文request)
 * bill_info(票据信息数组)
 */
@Data
@XStreamAlias("bill_info")
public class BillInfoRequestDTO {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("bill_num")
	private String billNum;//票据号

	@Length(max = 50)
	@XStreamAlias("summary_num")
	private String summaryNum;//借据号


	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【票面金额】，不符合number(24,2)规范")
	@XStreamAlias("summary_amt")
	private BigDecimal summaryAmt;//票面金额

//	@NotNull
//	@Digits(integer = 22, fraction = 2, message = "【票面金额】，不符合number(24,2)规范")
//	@XStreamAlias("summary_bal")
//	private BigDecimal summaryBal;//借据余额

	@NotBlank
	@ValidatorDate(fmt = "yyyy-MM-dd", message = "【票据起期】需要匹配格式yyyy-MM-dd")
	@XStreamAlias("begin_date")
	private String beginDate;//票据起期

	@NotBlank
	@Length(max = 6)
	@XStreamAlias("bill_type")
	private String billType;//票据类型

	/**
	 * 票据状态： CD000078 0 待兑付 1 提示付款 2 未用注销 3 实时清算 4 未用退回 5 待签发 9 提示付款（选择销登记簿）  E 结清
	 */
	@NotBlank
	@Length(max = 6)
	@XStreamAlias("bill_status")
	private String billStatus;//票据状态


	@NotBlank
	@Length(max = 50)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("main_guarantee_type")
	private String mainGuaranteeType;//主担保方式

	@NotNull
	@Digits(integer = 4, fraction = 6, message = "【保证金比例】，不符合number(10,6)规范")
	@XStreamAlias("deposit_ratio")
	private BigDecimal depositRatio;//保证金比例


	@NotBlank
	@Length(max = 10)
	@XStreamAlias("end_date")
	private String endDate;//票据止期


	@NotBlank
	@Length(max = 40)
	@XStreamAlias("acceptor_ecif_num")
	private String acceptorEcifNum;//承兑行ECIF客户号

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("drawer_name")
	private String drawerName;//出票人名称

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("drawer_acct")
	private String drawerAcct;//出票人账号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("drawer_bank_num")
	private String drawerBankNum;//出票人开户行行号

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("drawer_bank_name")
	private String drawerBankName;//出票人开户行行名

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("pay_name")
	private String payName;//付款行名称

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("pay_acct")
	private String payAcct;//付款行行号

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("payee_name")
	private String payeeName;//收款人名称

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("payee_acct")
	private String payeeAcct;//收款人账号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("payee_bank_num")
	private String payeeBankNum;//收款人开户行行号

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("payee_bank_name")
	private String payeeBankName;//收款人开户行行名
}
