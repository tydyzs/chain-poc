package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * CLM206_资金实时-日间批量用信（预占用、占用、恢复、其他）(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundBatchRequestDTO extends Request {

	@NotBlank
	@Pattern(regexp = "^(\\d{0,18})$",message = "数据类型应为DECIMAL(18,0)")
	@XStreamAlias("sum_cnt")
	private String sumCnt;//总笔数

	@Valid
	@XStreamAlias("busi_infos")
	private List<BusiInfoRequestDTO> busiInfo;//明细报文数组
}
