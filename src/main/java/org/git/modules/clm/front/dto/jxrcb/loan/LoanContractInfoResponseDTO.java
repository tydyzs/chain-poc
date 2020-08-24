package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;

import java.util.List;

/**
 * CLM102_信贷系统-合同信息同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanContractInfoResponseDTO extends Response {

	//	@NotBlank
//	@Length(max = 50)
	@XStreamAlias("contract_num")
	private String contractNum;//合同编号

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	//	@NotBlank
//	@Length(max = 50)
	@XStreamAlias("control_type")
	private String controlType;//管控类型

	@XStreamAlias("quota_infos")
	private List<QuotaInfoResponseDTO> quotaInfo;//预警信息数组
}
