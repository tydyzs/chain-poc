package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * CLM105_信贷系统-实时还款(请求报文request)
 */
@Data
@XStreamAlias("request")
@ApiModel(value = "LoanRepayRequestDTO对象", description = "请求报文request")
public class LoanRepayRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("tran_seq_sn")
	@ApiModelProperty(name = "tran_seq_sn", value = "交易流水号", required = true, allowableValues = "1,2,3")
	private String tranSeqNum;//交易流水号

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
	@Length(max = 2)
	@XStreamAlias("date_source")
	private String dateSource;//数据来源

	@Length(max = 50)
	@XStreamAlias("customer_num")
	private String customerNum;//ECIF客户号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("is_settle")
	private String isSettle;//是否结清

	@NotBlank
	@Length(max = 26)
	@XStreamAlias("summary_num")
	private String summaryNum;//借据号

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 22, fraction = 2,message = "【还款本金】，不符合number(24,2)规范")
	@XStreamAlias("repay_amt")
	private BigDecimal repayAmt;//还款本金

	@NotBlank
	@ValidatorDate(fmt="yyyy-MM-dd", message = "申请日期需要匹配格式yyyy-MM-dd")
	@XStreamAlias("repay_date")
	private String repayDate;//还款日期

	@NotNull
	@Digits(integer = 22, fraction = 2,message = "【借据余额】，不符合number(24,2)规范")
	@XStreamAlias("summary_bal")
	private String summaryBal;//借据余额

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("org_num")
	private String orgNum;//机构号


}
