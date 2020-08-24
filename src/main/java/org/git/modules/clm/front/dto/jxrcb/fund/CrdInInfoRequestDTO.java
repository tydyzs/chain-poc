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
 * crd_in_info(额度转让数组（转入）)
 */
@Data
@XStreamAlias("crd_in_info")
public class CrdInInfoRequestDTO {

	@NotBlank
	@Length(max = 12)
	@XStreamAlias("crd_in_org_num")
	private String crdInOrgNum;//转入机构(成员行)

	@Length(max = 10)
	@XStreamAlias("busi_prd_num")
	private String busiPrdNum;//业务产品类型

	@Length(max = 40)
	@XStreamAlias("busi_new_req_num")
	private String busiNewReqNum;//业务申请编号（新唯一）

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@NotBlank
	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("crd_apply_in_amt")
	private String crdApplyInAmt;//转入金额
}
