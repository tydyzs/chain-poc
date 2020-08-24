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

/**
 * CLM104_信贷系统-项目协议同步(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanProjectRequestDTO extends Request {

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
	@XStreamAlias("customer_num")
	private String customerNum;//ECIF客户编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("customer_type")
	private String customerType;//客户类型

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("project_num")
	private String projectNum;//项目编号

	@NotBlank
	@Length(max = 500)
	@XStreamAlias("project_name")
	private String projectName;//项目名称

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("project_type")
	private String projectType;//项目类型

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【金额】，不符合number(24,2)规范")
	@XStreamAlias("total_amt")
	private BigDecimal totalAmt;//金额

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("limit_control_type")
	private String limitControlType;//贷款规模额度控制方式

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("apply_date")
	private String applyDate;//协议申请日期

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【协议有效期】，不符合number(24,2)规范")
	@XStreamAlias("agreement_term")
	private BigDecimal agreementTerm;//协议有效期

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("agreement_term_unit")
	private String agreementTermUnit;//协议有效期单位

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("project_status")
	private String projectStatus;//项目状态

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("org_num")
	private String orgNum;//机构号


}
