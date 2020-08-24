package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * ECIF_00880000420202-对公客户综合信息查询(响应报文response)
 * org_company_info_query(关系企业信息组)子项
 */
@Data
@XStreamAlias("org_company_info_query")
public class OrgCompanyInfoResponseDTO {
	@Length(max = 20)
	@XStreamAlias("rel_id")
	private String relId;//关系人ID

	@Length(max = 3)
	@XStreamAlias("rel_type")
	private String relType;//客户关系类型 CD000029

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//关系人名称 客户名称暂时只支持64位

	@Length(max = 200)
	@XStreamAlias("org_short_name")
	private String orgShortName;//组织中文简称

	@Length(max = 200)
	@XStreamAlias("cust_eng_name")
	private String custEngName;//客户英文名称

	@Length(max = 100)
	@XStreamAlias("org_rng_short_name")
	private String orgRngShortName;//组织英文简称

	@Length(max = 2)
	@XStreamAlias("cert_type")
	private String certType;//证件类型 CD000009

	@Length(max = 60)
	@XStreamAlias("cert_num")
	private String certNum;//证件号码

	@Length(max = 10)
	@XStreamAlias("cert_start_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String certStartDate;//证件生效日期

	@Length(max = 10)
	@XStreamAlias("cert_end_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String certEndDate;//证件到期日期

	@Length(max = 120)
	@XStreamAlias("issued_inst")
	private String issuedInst;//证件发放机关

	@Length(max = 10)
	@XStreamAlias("found_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String foundDate;//成立日期

	@Length(max = 3)
	@XStreamAlias("reg_cptl_curr")
	private String regCptlCurr;//注册资本币种 CD000042

	@Length(max = 24)
	@XStreamAlias("reg_capital")
	private String regCapital;//注册资本

	@Length(max = 1)
	@XStreamAlias("unit_scale")
	private String unitScale;//企业规模 CD000043

	@Length(max = 8)
	@XStreamAlias("emp_number")
	private String empNumber;//企业员工人数

	@Length(max = 1)
	@XStreamAlias("pay_credit_flag")
	private String payCreditFlag;//是否代发工资 0-否 1-是

	@Length(max = 3)
	@XStreamAlias("corporate_cust_type")
	private String corporateCustType;//涉农法人客户类型 CD000337

	@Length(max = 1)
	@XStreamAlias("operating_site_ownership")
	private String operatingSiteOwnership;//经营场地所有权 CD000045

	@Length(max = 2)
	@XStreamAlias("special_zone_type")
	private String specialZoneType;//特殊经济区类型 CD000046

	@Length(max = 2)
	@XStreamAlias("finance_mechanism_classi")
	private String financeMechanismClassi;//金融机构行业分类 CD000048

	@Length(max = 9)
	@XStreamAlias("economic_type1")
	private String economicType1;//经济类型1 CD000041

	@Length(max = 9)
	@XStreamAlias("economic_type2")
	private String economicType2;//经济类型2 CD000041

	@Length(max = 9)
	@XStreamAlias("economic_type3")
	private String economicType3;//经济类型3 CD000041

	@Length(max = 256)
	@XStreamAlias("busin_scope")
	private String businScope;//经营范围

	@Length(max = 3)
	@XStreamAlias("collect_capital_currency")
	private String collectCapitalCurrency;//实收资本币种 CD000042

	@Length(max = 24)
	@XStreamAlias("cap_inhd")
	private String capInhd;//实收资本
}
