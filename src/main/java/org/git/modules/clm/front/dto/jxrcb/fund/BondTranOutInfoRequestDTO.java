package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;

import javax.validation.constraints.NotNull;
/**
 * CLM207-资金-纯券转出数组(请求报文request)
 */
@Data
@XStreamAlias("bond_out_info")
public class BondTranOutInfoRequestDTO extends Request {

	@NotNull
	@XStreamAlias("crd_out_org_num")
	private String crdOutOrgNum;//转出机构

	@NotNull
	@XStreamAlias("busi_source_req_num")
	private String busiSourceReqNum;//业务编号
}
