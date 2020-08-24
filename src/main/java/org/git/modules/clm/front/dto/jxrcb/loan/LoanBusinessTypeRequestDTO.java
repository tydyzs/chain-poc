package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * 信贷系统-业务品种查询(请求报文request)
 */
@Data
@XStreamAlias("request")
public class LoanBusinessTypeRequestDTO extends Request {

	@Length(max = 7)
	@XStreamAlias("I1PRODUCTID")
	private String I1PRODUCTID;//产品编号

	@Length(max = 500)
	@XStreamAlias("I1PRODUCTNAME")
	private String I1PRODUCTNAME;//产品名称

	@Length(max = 30)
	@XStreamAlias("I1BAKUP1")
	private String I1BAKUP1;//备用1

	@Length(max = 50)
	@XStreamAlias("I1BAKUP2")
	private String I1BAKUP2;//备用2

	@Digits(integer = 13, fraction = 2, message = "【备用3】，不符合number(15,2)规范")
	@XStreamAlias("I1BAKUP3")
	private BigDecimal I1BAKUP3;//备用3s

}
