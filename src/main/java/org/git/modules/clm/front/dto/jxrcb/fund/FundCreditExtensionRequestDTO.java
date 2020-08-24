package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.git.common.constant.CommonConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * CLM201_资金实时-额度授信（启用、切分、调整）(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundCreditExtensionRequestDTO extends Request {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务申请编号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@Length(max = 12)
	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("customer_num")
	private String customerNum;//ecif客户编号

	@Length(max = 10)
	@XStreamAlias("crd_main_prd")
	private String crdMainPrd;//授信总额产品

	@Length(max = 10)
	@XStreamAlias("crd_currency_cd")
	private String crdCurrencyCd;//币种

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("crd_sum_amt")
	private String crdSumAmt;//授信总额度

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("crd_begin_date")
	private String crdBeginDate;//额度生效日

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@XStreamAlias("crd_end_date")
	private String crdEndDate;//额度到期日

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("busi_segm_amt")
	private String busiSegmAmt;//切分总额

	@Pattern(regexp = "^(\\d{0,18})$",message = "数据类型应为DECIMAL(18,0)")
	@XStreamAlias("busi_segm_cnt")
	private String busiSegmCnt;//切分数


	@XStreamAlias("crd_segm_info")
	@Valid
	private List<CrdSegmInfoRequestDTO> crdSegmInfo;//切分数组
}
