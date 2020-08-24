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
public class CreditQueryRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("customer_num")
	private String customerNum;//ECIF客户号

	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

}
