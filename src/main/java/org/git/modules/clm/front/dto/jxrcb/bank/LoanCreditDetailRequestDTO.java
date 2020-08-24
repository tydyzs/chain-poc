package org.git.modules.clm.front.dto.jxrcb.bank;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 *  S00870000259004 票据额度查询(请求报文request)
 *  刘烨
 */
@Data
@XStreamAlias("request")
public class LoanCreditDetailRequestDTO extends Request {

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("crd_cust_kind")
	private String crdCustKind;//客户编号类型

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//客户编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种


	@NotBlank
	@Length(max = 24)
	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构

}
