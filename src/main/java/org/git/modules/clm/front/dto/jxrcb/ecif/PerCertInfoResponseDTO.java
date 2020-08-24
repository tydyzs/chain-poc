package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_cert_info_query(证件信息分组)子项
 */
@Data
@XStreamAlias("item")
public class PerCertInfoResponseDTO {

	@NotBlank
	@Length(max = 20)
	@XStreamAlias("cert_id")
	private String certId;//证件id 证件号索引记录ID（预留字段,二期可能会用）

	@Length(max = 2)
	@XStreamAlias("cert_flag")
	private String certFlag;//开户证件标识 00为辅证件 01为主证件

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private String certType;//证件类型 CD000009

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 120)
	@XStreamAlias("issued_inst")
	private String issuedInst;//证件发放机关

	@Length(max = 10)
	@XStreamAlias("cert_start_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String certStartDate;//证件生效日期 YYYY-MM-DD

	@Length(max = 10)
	@XStreamAlias("cert_end_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String certEndDate;//证件到期日期
}
