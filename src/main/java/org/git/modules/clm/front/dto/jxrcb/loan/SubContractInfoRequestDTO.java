package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM102_信贷系统-合同信息同步(请求报文request)
 * CLM108_信贷系统-担保合同信息同步
 * subcontract_info(担保合同分组)
 */
@Data
@Valid
@XStreamAlias("subcontract_info")
public class SubContractInfoRequestDTO {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("subcontract_num")
	private String subcontractNum;//担保合同编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("subcontract_type")
	private String subcontractType;//担保合同类型

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("is_top")
	private String isTop;//是否最高额担保合同

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotNull
	@Digits(integer = 4, fraction = 6, message = "【汇率】，不符合number(10,6)规范")
	@XStreamAlias("exchange_rate")
	private BigDecimal exchangeRate;//汇率

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【担保合同金额】，不符合number(24,2)规范")
	@XStreamAlias("subcontract_amt")
	private BigDecimal subcontractAmt;//担保合同金额

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("begin_date")
	private String beginDate;//担保合同起期

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("end_date")
	private String endDate;//担保合同止期

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【本次担保金额】，不符合number(24,2)规范")
	@XStreamAlias("surety_amt")
	private BigDecimal suretyAmt;//本次为贷款合同担保金额
}
