package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * CLM002_通用-额度查询(响应报文response)
 */
@Data
@XStreamAlias("response")
public class CreditQueryResponseDTO extends Response {

	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//ECIF客户号

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种 CD000019
	// 01人民币元  13港元  27日元  39英镑  14美元  38欧元  59其他

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("limit_credit")
	private String limitCredit;//总授信额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("limit_used")
	private String limitUsed;//总已用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("limit_avi")
	private String limitAvi;//总可用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("open_credit")
	private String openCredit;//总授信敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("open_used")
	private String openUsed;//总已用敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("open_avi")
	private String openAvi;//总可用敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("guarantee_limit")
	private String guaranteeLimit;//总担保额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("guarantee_used")
	private String guaranteeUsed;//担保额度已用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("guarantee_avi")
	private String guaranteeAvi;//担保额度可用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("third_limit")
	private String thirdLimit;//第三方额度已用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("third_used")
	private String thirdUsed;//第三方额度可用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 18)
	@XStreamAlias("third_avi")
	private String thirdAvi;//第三方额度可用

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

	@XStreamAlias("credit_info")
	private List<CreditInfoResponseDTO> creditInfo;//额度信息数组

	@XStreamAlias("credit_detail_info")
	private List<CreditDetailInfoResponseDTO> creditDetailInfo;//额度明细数组

	@XStreamAlias("approve_info")
	private List<ApproveInfoResponseDTO> approveInfo;//额度批复信息

	@XStreamAlias("water_info")
	private List<WaterInfoResponseDTO> waterInfo;//占用恢复流水信息
}
