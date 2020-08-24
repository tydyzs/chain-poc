package org.git.modules.clm.front.dto.jxrcb.bill;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorAmt;
import org.git.common.annotation.ValidatorDate;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM302_票据实时-直贴、转帖用信交易(请求报文request)
 * cert_info(凭证数组)
 */
@Data
@XStreamAlias("cert_info")
public class CertInfoRequestDTO {

	@Length(max = 40)
	@XStreamAlias("cert_num")
	private String certNum;//凭证编号

	@Length(max = 40)
	@XStreamAlias("cert_type_cd")
	private String certTypeCd;//凭证品种

	@Length(max = 40)
	@XStreamAlias("cert_ppt_cd")
	private String certPptCd;//凭证性质

	@Length(max = 40)
	@XStreamAlias("cert_interest_peri_type")
	private String certInterestPeriType;//凭证计息期限类型

	@Length(max = 40)
	@XStreamAlias("cert_interest_period")
	private String certInterestPeriod;//凭证计息期限

	@Length(max = 40)
	@XStreamAlias("cert_interest_rate_type")
	private String certInterestRateType;//凭证计息期限

	@ValidatorAmt(pattern = CommonConstant.PATTERN_RATIO,message = "数据类型应为DECIMAL(10,6)")
	@XStreamAlias("cert_interest_rate")
	private String certInterestRate;//收益率/利率

	@Length(max = 10)
	@XStreamAlias("cert_currency_cd")
	private String certCurrencyCd;//币种

	@XStreamAlias("cert_seq_amt")
	@ValidatorAmt(message = "数据类型应为DECIMAL(24,2)")
	private String certSeqAmt;//凭证原始金额

	@ValidatorAmt(message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("cert_apply_amt")
	private String certApplyAmt;//凭证用信金额

	@ValidatorAmt(message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("cert_apply_balance")
	private String certApplyBalance;//凭证用信余额

	@Length(max = 10)
	@XStreamAlias("cert_status")
	private String certStatus;//凭证状态

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("cert_begin_date")
	private String certBeginDate;//凭证起期

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("cert_end_date")
	private String certEndDate;//凭证止期

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("cert_finish_date")
	private String certFinishDate;//凭证结清日期

	@Length(max = 50)
	@XStreamAlias("cert_drawer_cust_num")
	private String certDrawerCustNum;//发行人客户号

	@Length(max = 200)
	@XStreamAlias("cert_drawer_name")
	private String certDrawerName;//发行人客户名称

	@Length(max = 1)
	@XStreamAlias("cert_is_my_bank")
	private String certIsMyBank;//是否本法人行

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("cert_drawer_bank_num")
	private String certDrawerBankNum;//发行人代理/承兑行号

	@Length(max = 50)
	@XStreamAlias("cert_drawer_bank_legal")
	private String certDrawerBankLegal;//发行人代理/承兑行法人行

	@Length(max = 40)
	@XStreamAlias("cert_guaranty_type")
	private String certGuarantyType;//担保方式

	@Length(max = 40)
	@XStreamAlias("cert_guaranty_person")
	private String certGuarantyPerson;//担保人

	@Length(max = 200)
	@XStreamAlias("cert_busi_remark")
	private String certBusiRemark;//备注信息
}
