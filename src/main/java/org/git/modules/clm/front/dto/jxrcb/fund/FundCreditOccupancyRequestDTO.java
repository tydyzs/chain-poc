package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDict;
import org.git.common.constant.CommonConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * CLM207_资金实时-额度占用转让(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundCreditOccupancyRequestDTO extends Request {

	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务申请编号

	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("busi_sum_amt")
	private String busiSumAmt;//业务交易金额

	@NotBlank
	@Length(max=2)
	@ValidatorDict(code="CD000270")
	@XStreamAlias("tran_direction")
	private String tranDirection;//业务交易方向

	@Valid
	@XStreamAlias("crd_out_infos")
	private List<CrdOutInfoRequestDTO> crdOutInfo;//额度转让数组（转出）

	@Valid
	@XStreamAlias("crd_in_infos")
	private List<CrdInInfoRequestDTO> crdInInfo;//额度转让数组（转入）
}
