package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * rel_people_info_query(关系人信息组)子项
 */
@Data
@XStreamAlias("item")
public class RelPeopleInfoResponseDTO {

	@Length(max = 20)
	@XStreamAlias("rel_id")
	private String relId;//关系人ID

	@Length(max = 3)
	@XStreamAlias("rel_type")
	private String relType;//客户关系类型 CD000029 客户关系类型包括代理人

	@Length(max = 200)
	@XStreamAlias("cust_name")
	private String custName;//关系人名称

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

	@Length(max = 1)
	@XStreamAlias("gender")
	private String gender;//性别 CD000010

	@Length(max = 10)
	@XStreamAlias("birth_date")
	@ValidatorDate(fmt="yyyy-MM-dd", message = "需要匹配格式yyyy-MM-dd")
	private String birthDate;//出生日期

	@Length(max = 3)
	@XStreamAlias("nation")
	private String nation;//国籍 CD000005

	@Length(max = 2)
	@XStreamAlias("people")
	private String people;//民族 CD000012

	@Length(max = 2)
	@XStreamAlias("education")
	private String education;//最高学历 CD000022

	@Length(max = 1)
	@XStreamAlias("high_acade_degree")
	private String highAcadeDegree;//最高学位 CD000021

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
	@XStreamAlias("duty")
	private String duty;//职务 CD000024

	@Length(max = 24)
	@XStreamAlias("per_year_income")
	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,6})$",message = "类型应为DECIMAL")
	private String perYearIncome;//个人年收入

	@Length(max = 11)
	@XStreamAlias("rel_phone")
	private String relPhone;//联系电话

	@Length(max = 256)
	@XStreamAlias("rel_addr")
	private String relAddr;//联系地址
}
