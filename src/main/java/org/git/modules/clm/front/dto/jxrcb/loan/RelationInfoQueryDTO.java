package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 信贷系统-对公客户信息同步(响应报文response)
 * relationInfoQuery(关系信息组)
 */
@Data
@XStreamAlias("item")
public class RelationInfoQueryDTO {

	@XStreamAlias("relCustomerNum")
	private String relCustomerNum;//关系人客户号

	@XStreamAlias("relCustomerType")
	private String relCustomerType;//关系人客户类型

	@XStreamAlias("relType")
	private String relType;//客户关系类型

	@XStreamAlias("custName")
	private String custName;//关系人名称

	@XStreamAlias("certType")
	private String certType;//证件类型

	@XStreamAlias("certNum")
	private String certNum;//证件号码

	@XStreamAlias("bankRelMark")
	private String bankRelMark;//行内外客户标志
}
