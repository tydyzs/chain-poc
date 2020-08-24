package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * CLM002_通用-额度查询(响应报文response)
 * water_info(占用恢复流水信息)子项
 */
@Data
@XStreamAlias("water_info")
public class WaterInfoResponseDTO {

	@Length(max = 50)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@Length(max = 50)
	@XStreamAlias("credit_main_num")
	private String creditMainNum;//额度编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_num")
	private String creditDetailNum;//额度明细编号

	@Length(max = 50)
	@XStreamAlias("credit_detail_prd")
	private String creditDetailPrd;//额度产品编号（三级）


	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$", message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("amt")
	private String amt;//发生额

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$", message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("limit_credit_amt")
	private String limitCreditAmt;//占用/恢复额度金额

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$", message = "类型应为NUMBER(24,6)")
	@Length(max = 24)
	@XStreamAlias("limit_open_amt")
	private String limitOpenAmt;//占用/恢复敞口金额

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种

	@Length(max = 10)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@Length(max = 10)
	@XStreamAlias("tran_date")
	private String tranDate;//交易日期

	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号
}
