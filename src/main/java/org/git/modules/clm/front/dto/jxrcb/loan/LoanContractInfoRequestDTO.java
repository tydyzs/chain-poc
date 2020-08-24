package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorAmt;
import org.git.common.constant.CommonConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM102_信贷系统-合同信息同步(请求报文request)
 */
@Valid
@Data
@XStreamAlias("request")
public class LoanContractInfoRequestDTO extends Request {

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
	private String contractNum;//合同编号

	//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("approve_num")
	private String approveNum;//业务申请编号


	//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("product_num")
	private String productNum;//业务品种

	//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("product_type")
	private String productType;//业务种类

	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	//	@NotNull
	@ValidatorAmt(pattern = CommonConstant.PATTERN_RATIO, message = "【汇率】，不符合number(10,6)规范")
	@XStreamAlias("exchange_rate")
	private BigDecimal exchangeRate;//汇率

	//	@NotNull
	@ValidatorAmt(message = "【合同金额】，不符合number(24,2)规范")
	@XStreamAlias("contract_amt")
	private BigDecimal contractAmt;//合同金额


	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("begin_date")
	private String beginDate;//合同起期

	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("end_date")
	private String endDate;//合同止期

	//	@NotBlank
	@Length(max = 2)
	@XStreamAlias("is_cycle")
	private String isCycle;//合同循环标志

	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("industry")
	private String industry;//行业投向

	//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("main_guarantee_type")
	private String mainGuaranteeType;//主担保方式

	//	@NotBlank
	@Length(max = 10)
	@XStreamAlias("guarantee_type_detail")
	private String guaranteeTypeDetail;//担保方式分类

	//@NotNull
	@ValidatorAmt(pattern = CommonConstant.PATTERN_RATIO, message = "【保证金比例】，不符合number(10,6)规范")
	@XStreamAlias("deposit_ratio")
	private BigDecimal depositRatio;//保证金比例

//	@NotBlank
	@Length(max = 2)
	@XStreamAlias("contract_status")
	private String contractStatus;//合同状态

//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

//	@NotBlank
	@Length(max = 50)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@Valid
	@XStreamAlias("subcontract_infos")
	private List<SubContractInfoRequestDTO> subcontractInfo;//担保合同分组

	@Valid
	@XStreamAlias("pledge_infos")
	private List<PledgeInfoRequestDTO> pledgeInfo;//押品信息分组

	@Valid
	@XStreamAlias("surety_infos")
	private List<SuretyInfoRequestDTO> suretyInfo;//保证信息分组
}
