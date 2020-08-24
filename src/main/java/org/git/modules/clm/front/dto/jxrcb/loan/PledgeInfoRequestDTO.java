package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorAmt;
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
 * pledge_info(押品信息分组)
 */
@Data
@Valid
@XStreamAlias("pledge_info")
public class PledgeInfoRequestDTO {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("subcontract_num")
	private String subcontractNum;//担保合同编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("surety_num")
	private String suretyNum;//押品编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("pledge_name")
	private String pledgeName;//押品名称

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("pledge_type")
	private String pledgeType;//押品类型

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("customer_num")
	private String customerNum;//押品持有人ECIF客户号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("customer_type")
	private String customerType;//客户类型

	@NotNull
	@Digits(integer = 4, fraction = 6, message = "【抵质押率】，不符合number(10,6)规范")
	@XStreamAlias("pledge_rate")
	private BigDecimal pledgeRate;//抵质押率

	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【押品评估价值】，不符合number(24,2)规范")
	@XStreamAlias("amt_asses")
	private BigDecimal amtAsses;//押品评估价值


	@NotNull
	@Digits(integer = 22, fraction = 2, message = "【权利价值】，不符合number(24,2)规范")
	@XStreamAlias("amt_actual")
	private BigDecimal amtActual;//押品权利价值


	@ValidatorAmt(message = "【已用价值】，不符合number(24,2)规范")
	@XStreamAlias("amt_used")
	private BigDecimal amtUsed;//已用价值

	@ValidatorAmt(message = "【可用价值】，不符合number(24,2)规范")
	@XStreamAlias("amt_avi")
	private BigDecimal amtAvi;//可用价值

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("pledge_status")
	private String pledgeStatus;//押品状态

	@NotNull
	@ValidatorAmt(message = "【本次担保金额】，不符合number(24,2)规范")
	@XStreamAlias("surety_amt")
	private BigDecimal suretyAmt;//本次担保金额
}
