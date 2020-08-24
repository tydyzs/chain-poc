package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

/**
 * CLM106_信贷系统-额度冻结解冻(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanCreditFreezeResponseDTO extends Response {

	@Length(max = 50)
	@XStreamAlias("credit_num")
	private String creditNum;//额度编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_num")
	private String creditDetailNum;//额度明细编号

	@Length(max = 50)
	@XStreamAlias("crd_product_num")
	private String crdProductNum;//额度产品编号
}
