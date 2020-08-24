package org.git.modules.clm.front.dto.jxrcb.bill;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM302_票据实时-直贴、转帖用信交易(请求报文request)
 */
@Data
@XStreamAlias("request")
public class BillDiscountRequestDTO extends Request {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务编号

	@NotBlank
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@NotNull
	@Digits(integer=22, fraction=2)
	@XStreamAlias("crd_apply_amt")
	private BigDecimal crdApplyAmt;//用信金额

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("busi_prd_num")
	private String busiPrdNum;//业务产品编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("busi_deal_desc")
	private String busiDealDesc;//业务交易描述

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("busi_deal_org_num")
	private String busiDealOrgNum;//本方机构

	@NotBlank
	@Length(max = 100)
	@XStreamAlias("busi_deal_org_name")
	private String busiDealOrgName;//本方机构名称

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("busi_oppt_org_num")
	private String busiOpptOrgNum;//对手机构

	@NotBlank
	@Length(max = 100)
	@XStreamAlias("busi_oppt_org_name")
	private String busiOpptOrgName;//对手机构名称

	@NotNull
	@Digits(integer=22, fraction=2)
	@XStreamAlias("busi_sum_amt")
	private BigDecimal busiSumAmt;//交易总金额

	@NotNull
	@Digits(integer=18, fraction=0)
	@XStreamAlias("busi_cert_cnt")
	private BigDecimal busiCertCnt;//凭证张数

	@Valid
	@XStreamAlias("cert_infos")
	private List<CertInfoRequestDTO> certInfo;//凭证数组
}
