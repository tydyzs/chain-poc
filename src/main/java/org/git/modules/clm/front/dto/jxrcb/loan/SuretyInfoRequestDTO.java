package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM102_信贷系统-合同信息同步(请求报文request)
 * CLM108_信贷系统-担保合同信息同步
 * surety_info(保证信息分组)
 */
@Valid
@Data
@XStreamAlias("surety_info")
public class SuretyInfoRequestDTO {

	@Length(max = 50)
	@XStreamAlias("subcontract_num")
	private String subcontractNum;//担保合同编号

	@Length(max = 50)
	@XStreamAlias("surety_num")
	private String suretyNum;//保证人编号

	@Length(max = 50)
	@XStreamAlias("customer_num")
	private String customerNum;//保证人ECIF客户号

	@Length(max = 10)
	@XStreamAlias("customer_type")
	private String customerType;//客户类型

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【最高可担保金额】，不符合number(24,2)规范")
	@XStreamAlias("total_amt")
	private BigDecimal totalAmt;//最高可担保金额

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【本次担保金额】，不符合number(24,2)规范")
	@XStreamAlias("surety_amt")
	private BigDecimal suretyAmt;//本次担保金额
}
