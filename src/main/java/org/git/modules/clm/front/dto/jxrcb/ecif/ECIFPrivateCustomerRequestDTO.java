package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ECIF_00880000420102_对私客户综合信息查询(请求报文request)
 */
@Data
@XStreamAlias("request")
public class ECIFPrivateCustomerRequestDTO extends Request {
	@NotBlank
	@Length(max = 2)
	@XStreamAlias("resolve_type")
	private	String resolveType;//识别方式 "01按客户编号02按证件查询"

	@Length(max = 10)
	@XStreamAlias("cust_no")
	private	String custNo;//客户编号 识别方式为01时必填

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private	String certType;//证件类型	 "CD000009识别方式为02时必填"

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private	String certNum;//证件号码	识别方式为02时必填

	@Length(max = 64)
	@XStreamAlias("cust_name")
	private	String custName;//客户名称	 "不为空时按三要素查询，客户名称暂时只支持64位"
}
