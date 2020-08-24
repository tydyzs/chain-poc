package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * ECIF_00880000420301-客户清单查询(响应报文response)
 * cust_info_query(客户信息组)子项
 */
@Data
@XStreamAlias("item")
public class CustInfoQueryResponseDTO {

	@Length(max = 10)
	@XStreamAlias("cust_no")
	private String custNo;//客户编号

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private String certType;//证件类型 CD000009

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称 客户名称暂时只支持64位

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@Length(max = 10)
	@XStreamAlias("cert_start_date")
	private String certStartDate;//证件签发日

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@Length(max = 10)
	@XStreamAlias("cert_end_date")
	private String certEndDate;//证件到期日

	@Length(max = 2)
	@XStreamAlias("cust_type")
	private String custType;//客户类型

	@Length(max = 1)
	@XStreamAlias("bank_cust_flag")
	private String bankCustFlag;//同业客户简易标志

	@Length(max = 5)
	@XStreamAlias("last_update_org")
	private String lastUpdateOrg;//开户机构
}
