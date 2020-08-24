package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * CLM101_信贷系统-额度申请(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanCreditApplyResponseDTO extends Response {

	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@XStreamAlias("control_type")
	private String controlType;//管控类型


	@XStreamAlias("quota_infos")
	private List<QuotaInfoResponseDTO> quotaInfo;//预警信息数组
}
