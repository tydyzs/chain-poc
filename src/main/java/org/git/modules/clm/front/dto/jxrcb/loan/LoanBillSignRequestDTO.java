package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM109_信贷系统-银票签发(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanBillSignRequestDTO extends Request {

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
	@XStreamAlias("contract_num")
	private String contractNum;//合同号

/*
	@NotBlank
	@Pattern(regexp = "^(([1-9][0-9]{0,14})|([0]{1})|(([0]\\.\\d{1,2}|[1-9][0-9]{0,14}\\.\\d{1,2})))$", message = "数据类型应为DECIMAL(18,2)")
	@Length(max = 20)
	@XStreamAlias("total_amt")
	private String totalAmt;//总金额
*/


	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 4, fraction = 6, message = "【汇率】，不符合number(10,6)规范")
	@XStreamAlias("exchange_rate")
	private BigDecimal exchangeRate;//汇率


	@NotBlank
	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@XStreamAlias("bill_infos")
	private List<BillInfoRequestDTO> billInfo;//票据信息数组
}
