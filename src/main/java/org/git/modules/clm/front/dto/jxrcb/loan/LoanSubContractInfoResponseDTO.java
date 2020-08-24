package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM108_信贷系统-担保合同信息同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanSubContractInfoResponseDTO extends Response {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("contract_num")
	private String contractNum;//合同编号
}
