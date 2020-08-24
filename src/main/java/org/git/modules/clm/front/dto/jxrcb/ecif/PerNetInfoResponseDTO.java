package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_net_info_query(互联网地址信息组)子项
 */
@Data
@XStreamAlias("item")
public class PerNetInfoResponseDTO {
	@Length(max = 20)
	@XStreamAlias("net_id")
	private String netId;//互联网ID

	@Length(max = 3)
	@XStreamAlias("conn_type")
	private String connType;//联系类型

	@Length(max = 100)
	@XStreamAlias("internet_addr")
	private String internetAddr;//互联网联系信息
}
