package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM002_通用-额度查询(请求报文request)
 */
@Data
@XStreamAlias("request")
public class CommonCreditQueryRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("ecif_cust_num")
	private String ecifCustNum;//ECIF客户号

	@Length(max = 50)
	@XStreamAlias("credit_num")
	private String creditNum;//额度编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_num")
	private String creditDetailNum;//额度明细编号

	@Length(max = 50)
	@XStreamAlias("crd_product_num")
	private String crdProductNum;//额度产品编号

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@Length(max = 10)
	@XStreamAlias("credit_status")
	private String creditStatus;//额度状态

	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

}
