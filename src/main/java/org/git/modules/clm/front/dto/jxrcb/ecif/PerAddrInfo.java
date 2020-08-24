package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_addr_info_query(联系地址信息组)子项
 */
@Data
public class PerAddrInfo {

	@XStreamImplicit(itemFieldName = "item")
	private List<PerAddrInfoResponseDTO> perAddrInfoQuery;//联系地址信息组
}
