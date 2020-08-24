package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.git.common.annotation.ValidatorDict;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM101_信贷系统-额度申请(请求报文request)
 * limit_detail_info(额度明细信息分组)
 */
@Valid
@Data
@XStreamAlias("limit_detail_info")
public class LimitDetailInfoRequestDTO {
	@NotBlank
	@Length(max = 2)
	@XStreamAlias("op_type")
	private String opType;//操作类型

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("approve_num")
	private String approveNum;//批复编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("customer_num")
	private String customerNum;//客户编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("customer_type")
	private String customerType;//客户类型

	@NotBlank
	@Length(max = 1)
	@ValidatorDict(code = "CD000167", message = "【是否我行关联方】，不符合CD000167规范")
	@XStreamAlias("is_bank_rel")
	private String isBankRel;//是否我行关联方

	@NotBlank
	@Length(max = 1)
	@ValidatorDict(code = "CD000167", message = "【是否联保】不符合CD000167规范")
	@XStreamAlias("is_joint_guarantee")
	private String isJointGuarantee;//是否联保

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【批复总金额】，不符合number(24,2)规范")
	@XStreamAlias("total_amt")
	private BigDecimal totalAmt;//批复总金额

	@NotBlank
	@ValidatorDict(code = "CD000170", message = "【业务类型】，不符合CD000170规范")
	@XStreamAlias("biz_type")
	private String bizType;//业务类型

	@NotBlank
	@ValidatorDate(fmt = "yyyy-MM-dd", message = "【申请日期】，不符合yyyy-MM-dd规范")
	@XStreamAlias("tran_date")
	private String tranDate;//申请日期

	@NotBlank
	@ValidatorDict(code = "CD000109", message = "【批复状态】，不符合CD000109规范")
	@XStreamAlias("approve_status")
	private String approveStatus;//批复状态

	//@NotBlank
	@ValidatorDict(code = "CD000168", message = "【业务发生方式】，不符合CD000168规范")
	@XStreamAlias("biz_happen_type")
	private String bizHappenType;//业务发生方式


	@NotBlank
	@ValidatorDict(code = "CD000167", message = "【是否低风险业务】，不符合CD000167规范")
	@XStreamAlias("is_low_risk")
	private String isLowRisk;//是否低风险业务

	@Length(max = 50)
	@XStreamAlias("old_summary_num")
	private String oldSummaryNum;//旧借据编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 10, fraction = 6)
	@XStreamAlias("exchange_rate")
	private BigDecimal exchangeRate;//汇率

	@NotNull
	@Digits(integer = 22, fraction = 2)
	@XStreamAlias("approve_amt")
	private BigDecimal approveAmt;//批复明细金额

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("product_num")
	private String productNum;//业务品种

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("product_type")
	private String productType;//业务种类

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("is_cycle")
	private String isCycle;//额度循环标志

	@NotBlank
//	@Length(max = 5)
	@XStreamAlias("industry")
	private String industry;//行业投向

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("main_guarantee_type")
	private String mainGuaranteeType;//主担保方式

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("guarantee_type_detail")
	private String guaranteeTypeDetail;//担保方式分类

	@NotNull
	@Digits(integer = 10, fraction = 6)
	@XStreamAlias("deposit_ratio")
	private BigDecimal depositRatio;//保证金比例


	@NotNull
	@Digits(integer = 22, fraction = 2)
	@XStreamAlias("term")
	private BigDecimal term;//申请期限

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("term_unit")
	private String termUnit;//申请期限单位

	@Length(max = 50)
	@XStreamAlias("project_num")
	private String projectNum;//项目协议号
}
