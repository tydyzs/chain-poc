package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM206_资金实时-日间批量用信（预占用、占用、恢复、其他）(请求报文request)
 * busi_info(明细报文数组)
 */
@Data
@XStreamAlias("busi_info")
public class BusiInfoRequestDTO {

	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务申请编号

	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("crd_apply_amt")
	private String crdApplyAmt;//用信金额

	@Valid
	@XStreamAlias("crd_apply_infos")
	private List<CrdApplyInfoRequestDTO> crdApplyInfo;//业务用信数组
}
