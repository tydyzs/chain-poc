package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 * ECIF_00880000420202-对公客户综合信息查询(响应报文response)
 * org_cert_info_query(证件信息分组)子项
 */
@Data
public class OrgCertInfo {
	@XStreamImplicit(itemFieldName = "item")
	private List<OrgCertInfoResponseDTO> orgCertInfoQuery;//证件信息分组
}
