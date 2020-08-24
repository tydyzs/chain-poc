package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM501_ecif实时-查询同业客户信息(请求报文request)
 */
@Data
@XStreamAlias("request")
public class ECIFBankCustomerRequestDTO extends Request {

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("resolve_type")
	private String resolveType;//识别方式

	@Length(max = 10)
	@XStreamAlias("cust_num")
	private String custNum;//客户编号

	@Length(max = 2)
	@XStreamAlias("cert_type_cd")
	private String certTypeCd;//证件类型

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称
}
