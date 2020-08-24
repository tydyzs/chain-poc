package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * CLM106_信贷系统-额度冻结解冻(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanCreditFreezeRequestDTO extends Request {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("ecif_cust_num")
	private String ecifCustNum;//ECIF客户号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("approve_num")
	private String approveNum;//批复编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@XStreamAlias("credit_info")
	private List<CreditInfoRequestDTO> creditInfo;//额度信息数组
}
