package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM205-资金-额度用信(请求报文request)
 * CLM206_资金实时-日间批量用信
 * crd_apply_info(业务用信数组)
 */
@Data
@XStreamAlias("crd_apply_info")
public class CrdApplyInfoRequestDTO {

	@Length(max = 12)
	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构

	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//用信客户号

	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@Length(max = 10)
	@XStreamAlias("crd_currency_cd")
	private String crdCurrencyCd;//币种

	@Length(max = 10)
	@XStreamAlias("busi_prd_num")
	private String busiPrdNum;//业务产品编号

	@Length(max = 50)
	@XStreamAlias("busi_deal_desc")
	private String busiDealDesc;//交易描述

	@Length(max = 40)
	@XStreamAlias("busi_deal_org_num")
	private String busiDealOrgNum;//本方机构

	@Length(max = 100)
	@XStreamAlias("busi_deal_org_name")
	private String busiDealOrgName;//本方机构名称

	@Length(max = 40)
	@XStreamAlias("busi_oppt_org_num")
	private String busiOpptOrgNum;//对手机构

	@Length(max = 100)
	@XStreamAlias("busi_oppt_org_name")
	private String busiOpptOrgName;//对手机构名称

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("busi_sum_amt")
	private String busiSumAmt;//交易总金额

	@Pattern(regexp = "^(\\d{0,18})$",message = "数据类型应为DECIMAL(18,0)")
	@XStreamAlias("busi_cert_cnt")
	private String busiCertCnt;//凭证张数

	@XStreamAlias("cert_infos")
	@Valid
	private List<CertInfoRequestDTO> certInfo;//凭证数组
}
