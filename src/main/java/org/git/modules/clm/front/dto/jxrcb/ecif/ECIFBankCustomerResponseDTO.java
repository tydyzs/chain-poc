package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * CLM501_ecif实时-查询同业客户信息(响应报文response)
 */
@Data
@XStreamAlias("response")
public class ECIFBankCustomerResponseDTO extends Response {

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("cust_num")
	private String custNum;//客户编号

	@Length(max = 2)
	@XStreamAlias("cust_type")
	private String custType;//客户类型

	@Length(max = 1)
	@XStreamAlias("cust_status")
	private String custStatus;//客户状态

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称

	@Length(max = 14)
	@XStreamAlias("bank_pay_sys_no")
	private String bankPaySysNo;//人行支付清算行号

	@Length(max = 1)
	@XStreamAlias("organ_level_type")
	private String organLevelType;//机构层级类型

	@Length(max = 10)
	@XStreamAlias("higher_level_cust_on")
	private String higherLevelCustOn;//上级机构ECIF客户号

	@NotBlank
	@Length(max = 3)
	@XStreamAlias("national_economy_type")
	private String nationalEconomyType;//国民经济部门

	@NotBlank
	@Length(max = 4)
	@XStreamAlias("national_economy_depart1")
	private String nationalEconomyDepart1;//国民经济行业1

	@Length(max = 4)
	@XStreamAlias("national_economy_depart2")
	private String nationalEconomyDepart2;//国民经济行业2

	@Length(max = 4)
	@XStreamAlias("national_economy_depart3")
	private String nationalEconomyDepart3;//国民经济行业3

	@Length(max = 4)
	@XStreamAlias("national_economy_depart4")
	private String nationalEconomyDepart4;//国民经济行业4

	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	@Length(max = 10 )
	@XStreamAlias("found_date")
	private Date foundDate;//成立日期

	@Length(max = 24)
	@XStreamAlias("reg_capital")
	private String regCapital;//注册资本

	@Length(max = 3)
	@XStreamAlias("reg_cptl_curr")
	private String regCptlCurr;//注册资本币种

	@Length(max = 1)
	@XStreamAlias("unit_scale")
	private String unitScale;//企业规模

	@Length(max = 8)
	@XStreamAlias("emp_nober")
	private String empNober;//企业员工人数

	@Length(max = 256)
	@XStreamAlias("busin_scope")
	private String businScope;//经营范围

	@Length(max = 24)
	@XStreamAlias("cap_inhd")
	private String capInhd;//实收资本

	@Length(max = 15)
	@XStreamAlias("tax_res_code")
	private String taxResCode;//组织纳税人识别号

	@Length(max = 4)
	@XStreamAlias("external_crd_result")
	private String externalCrdResult;//客户外部评级结果

	@Length(max = 2)
	@XStreamAlias("nature_crd_agency")
	private String natureCrdAgency;//评级机构性质

	@Length(max = 200)
	@XStreamAlias("rel_name")
	private String relName;//关系人名称

	@Length(max = 11)
	@XStreamAlias("rel_phone")
	private String relPhone;//联系电话

	@Length(max = 256)
	@XStreamAlias("rel_addr")
	private String relAddr;//联系地址

	@Length(max = 30)
	@XStreamAlias("rel_e_mail")
	private String relEMail;//联系用E_MAIL

	@Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)([\\s\\p{Zs}]+)([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).([0-9]{3})$", message = "需要匹配格式yyyy-MM-dd HH:mm:ss.SSS")
	@Length(max = 14)
	@XStreamAlias("created_ts")
	private Timestamp createdTs;//开户日期

	@Length(max = 5)
	@XStreamAlias("last_updated_org")
	private String lastUpdatedOrg;//开户机构

	@Length(max = 6)
	@XStreamAlias("last_updated_te")
	private String lastUpdatedTe;//开户柜员

	@Length(max = 64)
	@XStreamAlias("remarks")
	private String remarks;//备注

}
