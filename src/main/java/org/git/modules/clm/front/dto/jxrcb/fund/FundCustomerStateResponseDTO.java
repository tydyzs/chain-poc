package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM203_资金实时-资金客户状态维护(响应报文response)
 */
@Data
@XStreamAlias("response")
public class FundCustomerStateResponseDTO extends Response {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@Length(max = 2)
	@XStreamAlias("tran_status")
	private String tranStatus;//处理状态

	@Length(max = 200)
	@XStreamAlias("tran_desc")
	private String tranDesc;//返回信息
}
