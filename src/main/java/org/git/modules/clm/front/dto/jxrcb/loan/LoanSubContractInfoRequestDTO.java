package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * CLM108_信贷系统-担保合同信息同步(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanSubContractInfoRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("contract_num")
	private String contractNum;//合同编号

	@XStreamAlias("subcontract_info")
	private List<SubContractInfoRequestDTO> subcontractInfo;//担保合同分组

	@XStreamAlias("pledge_info")
	private List<PledgeInfoRequestDTO> pledgeInfo;//押品信息分组

	@XStreamAlias("surety_info")
	private List<SuretyInfoRequestDTO> suretyInfo;//保证信息分组


}
