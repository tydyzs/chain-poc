package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorAmt;
import org.git.common.annotation.ValidatorDate;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * CLM103_信贷系统-借据信息同步(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanSummaryInfoRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("op_type")
	private String opType;//操作类型

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("summary_num")
	private String summaryNum;//借据编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("contract_num")
	private String contractNum;//合同编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 4, fraction = 6, message = "【汇率】，不符合number(10,6)规范")
	@XStreamAlias("exchange_rate")
	private String exchangeRate;//汇率

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【借据金额】，不符合number(24,2)规范")
	@XStreamAlias("summary_amt")
	private String summaryAmt;//借据金额

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【借据金额】，不符合number(24,2)规范")
	@XStreamAlias("summary_bal")
	private String summaryBal;//借据余额

	@NotBlank
	@ValidatorDate(fmt = "yyyy-MM-dd", message = "【借据起期】，不符合yyyy-MM-dd规范")
	@XStreamAlias("begin_date")
	private String beginDate;//借据起期

	@NotBlank
	@ValidatorDate(fmt = "yyyy-MM-dd", message = "【借据起期】，不符合yyyy-MM-dd规范")
	@XStreamAlias("end_date")
	private String endDate;//借据止期

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("summary_status")
	private String summaryStatus;//借据状态

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("main_guarantee_type")
	private String mainGuaranteeType;//主担保方式

	@ValidatorAmt(message = "【保证金比例】，不符合number(10,6)规范")
	@XStreamAlias("deposit_ratio")
	private String depositRatio;//保证金比例

	@Length(max = 200)
	@XStreamAlias("principal")
	private String principal;//受益人


}
