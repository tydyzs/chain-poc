package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

/**
 * CLM001_通用-客户合并(响应报文response)
 */
@Data
@XStreamAlias("response")
public class CommonCustomerMergeResponseDTO extends Response {

	@Length(max = 50)
	@XStreamAlias("reserve_ecif_cust_num")
	private String reserveEcifCustNum;//保留的ECIF客户号
}
