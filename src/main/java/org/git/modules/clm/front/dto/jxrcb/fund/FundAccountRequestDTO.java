package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * CLM200-资金-对账通知（向资金系统申请对账）(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundAccountRequestDTO extends Request {
	@NotBlank
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("tran_date")
	private String tranDate;//交易日期
}
