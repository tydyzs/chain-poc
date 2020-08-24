package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * CLM202-资金-额度圈存(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundCreditDepositRequestDTO extends Request {

	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务申请编号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//ecif客户编号

	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("crd_currency_cd")
	private String crdCurrencyCd;//币种

	@NotBlank
	@Length(max = 24)
	@XStreamAlias("crd_eark_amt")
	private String crdEarkAmt;//圈存额度

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("crd_type")
	private String crdType;//圈存额度类型（2：二级额度；3：三级额度）

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("earmark_begin_date")
	private String earmarkBeginDate;//圈存开始日

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("earmark_end_date")
	private String earmarkEndDate;//圈存到期日

	@XStreamAlias("crd_eark_info")
	private List<CrdEarkInfoRequestDTO> crdEarkInfo;//圈存分配数组
}
