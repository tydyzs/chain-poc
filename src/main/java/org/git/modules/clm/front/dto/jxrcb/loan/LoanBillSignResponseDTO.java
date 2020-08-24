package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;

import java.util.List;

/**
 * CLM109_信贷系统-银票签发(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanBillSignResponseDTO extends Response {

	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//合同编号

	@XStreamAlias("contract_num")
	private String contractNum;//合同编号

	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@XStreamAlias("control_type")
	private String controlType;//管控类型


	@XStreamAlias("quota_infos")
	private List<QuotaInfoResponseDTO> quotaInfo;//预警信息数组
}
