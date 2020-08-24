package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

/**
 * ECIF_00880000420301_客户清单查询(请求报文request)
 */
@Data
@XStreamAlias("request")
public class ECIFCustomerListRequestDTO extends Request {

	@Length(max = 10)
	@XStreamAlias("cust_no")
	private String custNo;//客户号

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private String certType;//证件类型

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称

	@Length(max = 3)
	@XStreamAlias("page_num")
	private String pageNum;//起始笔数

	@Length(max = 3)
	@XStreamAlias("page_size")
	private String pageSize;//查询笔数
}
