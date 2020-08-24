package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_net_info_query(互联网地址信息组)子项
 */
@Data
public class PerNetInfo {
	@XStreamImplicit(itemFieldName = "item")
	private List<PerNetInfoResponseDTO> perNetInfoQuery;
}
