package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *  CLM200-资金-对账通知（向资金系统申请对账）(响应报文response)
 */
@Data
@XStreamAlias("response")
public class FundAccountResponseDTO extends Response {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("tran_seq_no")
	private String tranSeqNo;//交易流水号

	@Length(max = 2)
	@XStreamAlias("tran_status")
	private String tranStatus;//处理状态

	@Length(max = 20)
	@XStreamAlias("tran_desc")
	private String tranDesc;//返回信息
}
