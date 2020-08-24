package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * CLM103_信贷系统-借据信息同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanSummaryInfoResponseDTO extends Response {

	@XStreamAlias("contract_num")
	private String contractNum;//合同编号

	@XStreamAlias("summary_num")
	private String summaryNum;//借据编号

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@XStreamAlias("control_type")
	private String controlType;//管控类型

	@XStreamAlias("quota_infos")
	private List<QuotaInfoResponseDTO> quotaInfo;//预警信息数组
}
