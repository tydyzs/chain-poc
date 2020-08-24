package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * CLM106_信贷系统-额度冻结解冻(请求报文request)
 * credit_info(额度信息数组)
 */
@Data
@XStreamAlias("credit_info")
public class CreditInfoRequestDTO {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("approve_detail_num")
	private String approveDetailNum;//批复明细编号

	@NotBlank
	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("limit_credit")
	private String limitCredit;//授信额度

	@NotBlank
	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("open_credit")
	private String openCredit;//授信敞口

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("frozen_unfreeze")
	private String frozenUnfreeze;//冻结/解冻

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("part_all")
	private String partAll;//部分/全部

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("limit_frozen")
	private String limitFrozen;//冻结额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("open_frozen")
	private String openFrozen;//冻结敞口

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("limit_unfreeze")
	private String limitUnfreeze;//解冻额度

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("open_unfrozen")
	private String openUnfrozen;//解冻敞口
}
