package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.constant.CommonConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM107_信贷系统-押品信息同步(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanGuarantyInfoRequestDTO extends Request {

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("op_type")
	private String opType;//操作类型

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("surety_num")
	private String suretyNum;//押品编号

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("pledge_name")
	private String pledgeName;//押品名称

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("pledge_status")
	private String pledgeStatus;//押品状态

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("pledge_type")
	private String pledgeType;//押品类型

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//客户号

	@NotBlank
	@Length(max = 2)
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
	@Digits(integer = 22, fraction = 2, message = "【押品权利价值】，不符合number(24,2)规范")
	@XStreamAlias("amt_actual")
	private BigDecimal amtActual;//押品权利价值

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

}
