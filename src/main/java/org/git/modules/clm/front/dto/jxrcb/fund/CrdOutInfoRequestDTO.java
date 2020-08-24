package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM207_资金实时-额度占用转让(请求报文request)
 * crd_out_info(额度转让数组（转出）)
 */
@Data
@XStreamAlias("crd_out_info")
public class CrdOutInfoRequestDTO {

	@NotBlank
	@Length(max = 12)
	@XStreamAlias("crd_out_org_num")
	private String crdOutOrgNum;//转出机构

	@Length(max = 40)
	@XStreamAlias("busi_source_req_num")
	private String busiSourceReqNum;//业务申请编号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String CurrencyCd;//币种

	@NotBlank
	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("crd_apply_out_amt")
	private String crdApplyOutAmt;//转出金额
}
