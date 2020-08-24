package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

/**
 * CLM105_信贷系统-实时还款(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanRepayResponseDTO extends Response {

	@Length(max = 50)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号


	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@Length(max = 50)
	@XStreamAlias("summary_num")
	private String summaryNum;//借据编号


}
