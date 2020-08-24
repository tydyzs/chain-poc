package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * ECIF_00880000420102_对私客户综合信息查询(响应报文response)
 */
@Data
@XStreamAlias("response")
public class ECIFPrivateCustomerResponseDTO extends Response {

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

	@NotBlank
	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//客户名称 客户名称暂时只支持64位

	@Length(max = 200)
	@XStreamAlias("pinyin_name")
	private String pinyinName;//客户拼音名称

	@Length(max = 1)
	@XStreamAlias("gender")
	private String gender;//性别 CD000010

	@Length(max = 3)
	@XStreamAlias("nation")
	private String nation;//国籍 CD000005

	@Length(max = 2)
	@XStreamAlias("race")
	private String race;//民族 CD000012

	@Length(max = 10)
	@XStreamAlias("birth_date")
	private String birthDate;//出生日期 YYYY-DD-MM

	@Length(max = 2)
	@XStreamAlias("poli_status")
	private String poliStatus;//政治面貌 CD000013

	@Length(max = 2)
	@XStreamAlias("marr_status")
	private String marrStatus;//婚姻状况 CD000014

	@Length(max = 2)
	@XStreamAlias("emp_stat")
	private String empStat;//从业状况 CD000017

	@Length(max = 6)
	@XStreamAlias("house_cicts")
	private String houseCicts;//户籍所在城市 CD000006

	@Length(max = 1)
	@XStreamAlias("house_type")
	private String houseType;//户籍类型 CD000020

	@Length(max = 1)
	@XStreamAlias("healthy_status")
	private String healthyStatus;//健康状况 CD000018

	@Length(max = 2)
	@XStreamAlias("family_num")
	private String familyNum;//家庭成员人数

	@Length(max = 2)
	@XStreamAlias("education")
	private String education;//最高学历 CD000022

	@Length(max = 3)
	@XStreamAlias("high_acade_degree")
	private String highAcadeDegree;//最高学位 CD000021

	@Length(max = 6)
	@XStreamAlias("cust_manager_no")
	private String custManagerNo;//客户经理编号

	@Length(max = 200)
	@XStreamAlias("work_unit_name")
	private String workUnitName;//工作单位全称

	@Length(max = 8)
	@XStreamAlias("work_industry")
	private String workIndustry;//工作单位所属行业 CD000028

	@Length(max = 9)
	@XStreamAlias("unit_character")
	private String unitCharacter;//工作单位性质 CD000041

	@Length(max = 1)
	@XStreamAlias("duty")
	private String duty;//职务 CD000024

	@Length(max = 5)
	@XStreamAlias("occupation1")
	private String occupation1;//职业1 CD000023

	@Length(max = 5)
	@XStreamAlias("occupation2")
	private String occupation2;//职业2 CD000023

	@Length(max = 5)
	@XStreamAlias("occupation3")
	private String occupation3;//职业3 CD000023

	@Length(max = 30)
	@XStreamAlias("occupation_explain")
	private String occupationExplain;//职业说明 其他从业人员，需要说明

	@Length(max = 1)
	@XStreamAlias("tech_tiyle_level")
	private String techTiyleLevel;//职称 CD000026

	@Length(max = 1)
	@XStreamAlias("pay_credit_flag")
	private String payCreditFlag;//是否代发工资 0-否 1-是

	@Length(max = 1)
	@XStreamAlias("is_blank_flag")
	private String isBlankFlag;//个人客户黑名单标志

	@Length(max = 1)
	@XStreamAlias("employee_flag")
	private String employeeFlag;//员工标志

	@Length(max = 1)
	@XStreamAlias("agri_related_ind")
	private String agriRelatedInd;//是否涉农

	@Length(max = 1)
	@XStreamAlias("seior_execu_ind")
	private String seiorExecuInd;//是否本行高管

	@Length(max = 1)
	@XStreamAlias("rel_party_ind")
	private String relPartyInd;//我行关联方标志

	@XStreamAlias("per_year_income")
	private String perYearIncome;//个人年收入

	@Length(max = 1)
	@XStreamAlias("tax_res_type")
	private String taxResType;//税收居民身份 CD000322

	@Length(max = 1)
	@XStreamAlias("resid_situat")
	private String residSituat;//居住状况 CD000016

	@Length(max = 1)
	@XStreamAlias("cust_grade")
	private String custGrade;//客户等级 CD000035

	@Length(max = 1)
	@XStreamAlias("cust_satis")
	private String custSatis;//客户满意度 CD000038

	@XStreamAlias("per_total_asset")
	private String perTotalAsset;//个人总资产

	@XStreamAlias("liability_balance")
	private String liabilityBalance;//个人总负债

	@Length(max = 2)
	@XStreamAlias("per_assest_type")
	private String perAssestType;//个人资产类型 CD000031

	@Length(max = 2)
	@XStreamAlias("per_liab_type")
	private String perLiabType;//个人负债类型 CD000032

	@Length(max = 2)
	@XStreamAlias("resid_non_resid")
	private String residNonResid;//居民/非居民 0-否 1-是

	@Length(max = 14)
	@XStreamAlias("created_ts")
	private String createdTs;//开户日期

	@Length(max = 5)
	@XStreamAlias("last_updated_org")
	private String lastUpdatedOrg;//开户机构

	@Length(max = 6)
	@XStreamAlias("last_updated_te")
	private String lastUpdatedTe;//开户柜员

	@Length(max = 1)
	@XStreamAlias("check_type")
	private String checkType;//核实方式 1-行内核实 2-客户核实 3-是什么

	@Length(max = 2)
	@XStreamAlias("check_flag")
	private String checkFlag;//核实标识 01-待核实 02- 核实为真实 03- 核实为虚名
		//04- 核实为假名 05- 核实为匿名 06- 无法核实 07- 拟不核实 08-无须核实的未核实


	@Length(max = 1)
	@XStreamAlias("no_check_reason")
	private String noCheckReason;//无法核实原因

	@Length(max = 2)
	@XStreamAlias("stat_flag")
	private String statFlag;//状态标识

	@XStreamAlias("per_cert_info_query")
	private PerCertInfo perCertInfo;//证件信息分组

	@XStreamAlias("rel_people_info_query")
	private RelPeopleInfo relPeopleInfo;//关系人信息组

	@XStreamAlias("per_addr_info_query")
	private PerAddrInfo perAddrInfo;//联系地址信息组

	@XStreamAlias("per_phone_info_query")
	private PerPhoneInfo perPhoneInfo;//联系电话信息组

	@XStreamAlias("per_net_info_query")
	private PerNetInfo perNetInfo;//互联网地址信息组

}
