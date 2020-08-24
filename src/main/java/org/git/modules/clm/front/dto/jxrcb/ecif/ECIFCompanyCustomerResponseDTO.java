package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ECIF_00880000420202-对公客户综合信息查询(响应报文response)
 */
@Data
@XStreamAlias("response")
public class ECIFCompanyCustomerResponseDTO extends Response {

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("cust_no")
	private String custNo;//客户编号

	@Length(max = 2)
	@XStreamAlias("cust_type")
	private String custType;//客户类型 CD000002

	@Length(max = 1)
	@XStreamAlias("cust_status")
	private String custStatus;//客户状态 CD000003

	@Length(max = 1)
	@XStreamAlias("bank_cust_flag")
	private String bankCustFlag;//同业客户简易标志 1-简易信息 2-详细信息

	@Length(max = 6)
	@XStreamAlias("cust_manager_no")
	private String custManagerNo;//客户经理编号

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称 客户名称暂时只支持64位

	@Length(max = 200)
	@XStreamAlias("org_short_name")
	private String orgShortName;//组织中文简称

	@Length(max = 200)
	@XStreamAlias("cust_eng_name")
	private String custEngName;//客户英文名称

	@Length(max = 100)
	@XStreamAlias("org_rng_short_name")
	private String orgRngShortName;//组织英文简称

	@NotBlank
	@Length(max = 3)
	@XStreamAlias("national_economy_type")
	private String nationalEconomyType;//国民经济部门 CD000061

	@NotBlank
	@Length(max = 4)
	@XStreamAlias("national_economy_depart1")
	private String nationalEconomyDepart1;//国民经济行业1 CD000028

	@Length(max = 4)
	@XStreamAlias("national_economy_depart2")
	private String nationalEconomyDepart2;//国民经济行业2 CD000028

	@Length(max = 4)
	@XStreamAlias("national_economy_depart3")
	private String nationalEconomyDepart3;//国民经济行业3 CD000028

	@Length(max = 4)
	@XStreamAlias("national_economy_depart4")
	private String nationalEconomyDepart4;//国民经济行业4 CD000028

	@Length(max = 10 )
	@XStreamAlias("found_date")
	private String foundDate;//成立日期 YYYY-MM-DD

	@Length(max = 24)
	@XStreamAlias("reg_capital")
	private String regCapital;//注册资本

	@Length(max = 3)
	@XStreamAlias("reg_cptl_curr")
	private String regCptlCurr;//注册资本币种 CD000042

	@Length(max = 1)
	@XStreamAlias("unit_scale")
	private String unitScale;//企业规模 CD000043

	@Length(max = 8)
	@XStreamAlias("emp_number")
	private String empNumber;//企业员工人数

	@Length(max = 1)
	@XStreamAlias("pay_credit_flag")
	private String payCreditFlag;//是否代发工资 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("current_stat_corp")
	private String currentStatCorp;//企业经营状态 CD000058

	@Length(max = 2)
	@XStreamAlias("safe_auth_list_stat")
	private String safeAuthListStat;//外汇局企业分类 CD000059

	@Length(max = 2)
	@XStreamAlias("enrter_affiliated")
	private String enrterAffiliated;//上级隶属 CD000062

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

	@Length(max = 15)
	@XStreamAlias("financial_licen_num")
	private String financialLicenNum;//金融许可证号

	@Length(max = 2)
	@XStreamAlias("unit_type")
	private String unitType;//单位类型 1消极非金融机构 2其他

	@Length(max = 2)
	@XStreamAlias("contr_shar_tax_relted_type")
	private String contrSharTaxReltedType;//控制人涉税类型

	@Length(max = 3)
	@XStreamAlias("country_code")
	private String countryCode;//国别代码 CD000005

	@Length(max = 18)
	@XStreamAlias("credit_organ_code")
	private String creditOrganCode;//机构信用代码

	@NotBlank
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

	@Length(max = 4)
	@XStreamAlias("bank_cust_type1")
	private String bankCustType1;//同业客户类型1 CD000053

	@Length(max = 4)
	@XStreamAlias("bank_cust_type2")
	private String bankCustType2;//同业客户类型2 CD000053

	@Length(max = 2)
	@XStreamAlias("reg_town_property")
	private String regTownProperty;//注册地城乡属性 CD000338

	@Length(max = 14)
	@XStreamAlias("bank_pay_sys_num")
	private String bankPaySysNum;//人行现代化支付系统银行行号

	@Length(max = 1)
	@XStreamAlias("pay_taxes_stat")
	private String payTaxesStat;//纳税情况 CD000050

	@Length(max = 1)
	@XStreamAlias("vat_taxeser_type")
	private String vatTaxeserType;//纳税人规模 CD000051

	@Length(max = 15)
	@XStreamAlias("tax_res_code")
	private String taxResCode;//组织纳税人识别号 国税、地税、税务登记号

	@Length(max = 1)
	@XStreamAlias("special_zone_indicator")
	private String specialZoneIndicator;//特殊经济区内企业标志 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("group_credit_indicator")
	private String groupCreditIndicator;//集团授信标志 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("bank_indicator")
	private String bankIndicator;//本行黑名单标志 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("gover_financing_in")
	private String goverFinancingIn;//政府融资平台标志 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("cbrc_small_corp_indicat")
	private String cbrcSmallCorpIndicat;//银监会小企业标志 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("rel_party_ind")
	private String relPartyInd;//我行关联方标志 0-否 1-是

	@Length(max = 11)
	@XStreamAlias("swift_code")
	private String swiftCode;//SWIFT代码

	@Length(max = 1)
	@XStreamAlias("tax_res_type")
	private String taxResType;//税收居民身份 CD000322

	@Length(max = 10)
	@XStreamAlias("listing_date")
	private String listingDate;//上市日期

	@Length(max = 4)
	@XStreamAlias("external_credit_result")
	private String externalCreditResult;//客户外部评级结果 CD000075

	@Length(max = 2)
	@XStreamAlias("nature_credit_agency")
	private String natureCreditAgency;//评级机构性质 CD000076

	@Length(max = 1)
	@XStreamAlias("listed_com_type")
	private String listedComType;//上市公司类型 CD000323

	@Length(max = 2)
	@XStreamAlias("external_assess_instit")
	private String externalAssessInstit;//外部信用等级评级机构 CD000054

	@Length(max = 2)
	@XStreamAlias("resid_non_resid")
	private String residNonResid;//居民/非居民 0-否 1-是

	@Length(max = 20)
	@XStreamAlias("basic_account_auth_num")
	private String basicAccountAuthNum;//基本账户开户许可证核准号

	@Length(max = 30)
	@XStreamAlias("basic_local_open_bank")
	private String basicLocalOpenBank;//本币基本账户开户行

	@Length(max = 10)
	@XStreamAlias("cust_register_num")
	private String custRegisterNum;//海关注册号

	@Length(max = 20)
	@XStreamAlias("curr_busin_permit_no")
	private String currBusinPermitNo;//外汇经营许可证号

	@Length(max = 10)
	@XStreamAlias("frist_date")
	private String fristDate;//最初设立日期

	@Length(max = 1)
	@XStreamAlias("whether_trans_company")
	private String whetherTransCompany;//是否跨国公司 0-否 1-是

	@Length(max = 3)
	@XStreamAlias("resid_country_code")
	private String residCountryCode;//常驻国家代码 CD000005

	@Length(max = 3)
	@XStreamAlias("foreign_inves_country1")
	private String foreignInvesCountry1;//外方投资者国别1 CD000005

	@Length(max = 3)
	@XStreamAlias("foreign_inves_country2")
	private String foreignInvesCountry2;//外方投资者国别2 CD000005

	@Length(max = 3)
	@XStreamAlias("foreign_inves_country3")
	private String foreignInvesCountry3;//外方投资者国别3 CD000005

	@Length(max = 3)
	@XStreamAlias("foreign_inves_country4")
	private String foreignInvesCountry4;//外方投资者国别4 CD000005

	@Length(max = 3)
	@XStreamAlias("foreign_inves_country5")
	private String foreignInvesCountry5;//外方投资者国别5 CD000005

	@Length(max = 6)
	@XStreamAlias("fore_exch_bure_code")
	private String foreExchBureCode;//所属外汇局代码

	@Length(max = 30)
	@XStreamAlias("trade_e_mail")
	private String tradeEMail;//交易用E_MAIL

	@Length(max = 1)
	@XStreamAlias("declare_mode")
	private String declareMode;//申报方式

	@Length(max = 20)
	@XStreamAlias("inter_inco_expen_decla")
	private String interIncoExpenDecla;//国际收支申报联系方式

	@Length(max = 30)
	@XStreamAlias("rel_e_mail")
	private String relEMail;//联系用E_MAIL

	@Length(max = 1)
	@XStreamAlias("bene_cust_type")
	private String beneCustType;//对公客户类型 1-企业 2-公司 3-其他 4-免识别客户

	@Length(max = 64)
	@XStreamAlias("remarks")
	private String remarks;//备注

	@XStreamAlias("jxcdgd1")
	private String jxcdgd1;//外管局行业属性1

	@XStreamAlias("jxcdgd2")
	private String jxcdgd2;//外管局行业属性2

	@XStreamAlias("orgtype")
	private String orgType;//机构类型

	@XStreamAlias("custcontact")
	private String custContact;//联系人姓名

	@XStreamAlias("custtel")
	private String custTel;//联系号码

	@XStreamAlias("creditcode")
	private String creditCode;//统一社会信用代码(国结)

	@XStreamAlias("leicode")
	private String leiCode;//LEI编码

	@XStreamAlias("economic_type_gb")
	private String economicTypeGb;//经济类型(国标)

	@XStreamAlias("remarks1")
	private String remarks1;//备用1

	@XStreamAlias("remarks2")
	private String remarks2;//备用2

	@XStreamAlias("remarks3")
	private String remarks3;//备用3

	@XStreamAlias("init_created_ts")
	private String initCreatedTs;//开户日期

	@XStreamAlias("created_ts")
	private String createdTs;//进入ECIF日期

	@Length(max = 5)
	@XStreamAlias("last_updated_org")
	private String lastUpdatedOrg;//最后操作机构

	@Length(max = 6)
	@XStreamAlias("last_updated_te")
	private String lastUpdatedTe;//最后操作柜员

	@XStreamAlias("org_cert_info_query")
	private OrgCertInfo orgCertInfoQuery;//证件信息分组

	@XStreamAlias("org_addr_info_query")
	private OrgAddrInfo orgAddrInfoQuery;//证件信息分组


	@XStreamAlias("org_phone_info_query")
	private OrgPhoneInfo orgPhoneInfoQuery;//证件信息分组


	@XStreamAlias("org_net_info_query")
	private OrgNetInfoQ orgNetInfoQuery;//证件信息分组


	@XStreamAlias("org_company_info_query")
	private OrgCompanyInfo orgCompanyInfoQuery;//证件信息分组


	@XStreamAlias("org_people_info_query")
	private OrgPeopleInfo orgPeopleInfoQuery;//证件信息分组

}
