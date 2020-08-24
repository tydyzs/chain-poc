package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * CLM002_通用-额度查询(响应报文response)
 * credit_detail_info(额度明细数组)
 */
@Data
@XStreamAlias("credit_detail_info")
public class CreditDetailInfoResponseDTO {

	@Length(max = 50)
	@XStreamAlias("credit_main_num")
	private String creditMainNum;//额度编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_num")
	private String creditDetailNum;//额度明细编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_prd")
	private String creditDetailPrd;//额度产品编号（三级）

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种 CD000019
	// 01人民币元  13港元  27日元  39英镑  14美元  38欧元  59其他

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_credit")
	private String limitCredit;//授信额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_used")
	private String limitUsed;//已用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_avi")
	private String limitAvi;//可用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("exp_credit")
	private String expCredit;//授信敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("exp_used")
	private String expUsed;//已用敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("exp_avi")
	private String expAvi;//可用敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_pre_used")
	private String limitPreUsed;//预占用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("exp_pre_used")
	private String expPreUsed;//预占用敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_earmark")
	private String limitEarmark;//圈存额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_earmark_used")
	private String limitEarmarkUsed;//圈存已用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_earmark_avi")
	private String limitEarmarkAvi;//圈存可用额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("limit_frozen")
	private String limitFrozen;//冻结额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("exp_frozen")
	private String expFrozen;//冻结敞口

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号
}
