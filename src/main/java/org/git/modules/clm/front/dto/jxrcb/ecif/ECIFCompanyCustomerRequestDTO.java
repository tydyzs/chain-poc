package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ECIF_00880000420202-对公客户综合信息查询(请求报文request)
 */
@Data
@XStreamAlias("request")
public class ECIFCompanyCustomerRequestDTO extends Request {

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("resolve_type")
	private String resolveType;//识别方式

	@Length(max = 10)
	@XStreamAlias("cust_no")
	private String custNo;//客户编号

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private String certType;//证件类型

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称
}
