package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 信贷系统-对公客户信息同步(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanCompanyCustomerRequestDTO extends Request {

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("customerId")
	private String customerId;//客户号

	@Length(max = 10)
	@XStreamAlias("I1BAKUP1")
	private String I1BAKUP1;//备用1

	@Length(max = 20)
	@XStreamAlias("I1BAKUP2")
	private String I1BAKUP2;//备用2

	@Length(max = 20)
	@XStreamAlias("I1BAKUP3")
	private String I1BAKUP3;//备用3

	@Length(max = 30)
	@XStreamAlias("I1BAKUP4")
	private String I1BAKUP4;//备用4

	@XStreamAlias("I1BAKUP5")
	private BigDecimal I1BAKUP5;//备用5

}
