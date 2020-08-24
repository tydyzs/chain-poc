package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;

import javax.validation.constraints.NotNull;

/**
 * CLM207-资金-纯券转入数组(请求报文request)
 */
@Data
@XStreamAlias("bond_in_info")
public class BondTranInInfoRequestDTO extends Request {

	@NotNull
	@XStreamAlias("crd_in_org_num")
	private String crdInOrgNum;//转入机构

	@NotNull
	@XStreamAlias("busi_new_req_num")
	private String busiNewReqNum;//业务编号（新唯一）
}
