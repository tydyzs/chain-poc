package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * ECIF_00880000420301-客户清单查询(响应报文response)
 */
@Data
@XStreamAlias("response")
public class ECIFCustomerListResponseDTO extends Response {

	@Length(max = 3)
	@XStreamAlias("page_num")
	private String pageNum;//起始笔数

	@Length(max = 3)
	@XStreamAlias("page_size")
	private String pageSize;//查询笔数

	@Length(max = 9)
	@XStreamAlias("total_count")
	private String totalCount;//记录总数 分页用的公共属性

	@XStreamAlias("cust_info_query")
	private List<CustInfoQueryResponseDTO> custInfoQuery;//客户信息组
}
