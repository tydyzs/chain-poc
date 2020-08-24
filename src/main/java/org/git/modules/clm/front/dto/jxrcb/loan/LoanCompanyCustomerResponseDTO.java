package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;

import java.util.List;

/**
 * 信贷系统-对公客户信息同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanCompanyCustomerResponseDTO extends Response {

	@XStreamAlias("lastDate")
	private String lastDate;//登记日期

	@XStreamAlias("customerId")
	private String customerId;//客户号

	@XStreamAlias("custName")
	private String custName;//客户名称

	@XStreamAlias("paperType")
	private String paperType;//证件类型

	@XStreamAlias("idCard")
	private String idCard;//证件号码

	@XStreamAlias("generalName")
	private String generalName;//客户简称

	@XStreamAlias("foreignName")
	private String foreignName;//英文名称

	@XStreamAlias("country")
	private String country;//注册国家或地区

	@XStreamAlias("homeAddrTotal")
	private String homeAddrTotal;//注册地址

	@XStreamAlias("postQuality")
	private String postQuality;//注册区域属性

	@XStreamAlias("createDate")
	private String createDate;//注册日期

	@XStreamAlias("beginDate")
	private String beginDate;//证件注册日期

	@XStreamAlias("licenceEndDate")
	private String licenceEndDate;//证件到期日期

	@XStreamAlias("customerType")
	private String customerType;//客户类型

	@XStreamAlias("registerCurrency")
	private String registerCurrency;//注册或开办资金币种

	@XStreamAlias("registerCapital")
	private String registerCapital;//注册或开办资金

	@XStreamAlias("realityCurrency")
	private String realityCurrency;//实收资本币种

	@XStreamAlias("realityCapital")
	private String realityCapital ;//实收资本金额

	@XStreamAlias("businessRange")
	private String businessRange;//经营范围

	@XStreamAlias("organiseId")
	private String organiseId;//组织机构代码

	@XStreamAlias("organiseIdEnd")
	private String organiseIdEnd;//组织机构代码到期日

	@XStreamAlias("unitCreditCode")
	private String unitCreditCode;//机构信用代码

	@XStreamAlias("creditCodeEnd")
	private String creditCodeEnd;//机构信用代码到期日

	@XStreamAlias("taxCode")
	private String taxCode;//国税登记证号码

	@XStreamAlias("taxCodeLocal")
	private String taxCodeLocal;//地税登记证号码

	@XStreamAlias("loancardCode")
	private String loancardCode;//中征码

	@XStreamAlias("townType")
	private String townType;//注册地城乡属性

	@XStreamAlias("tradeType")
	private String tradeType;//行业分类

	@XStreamAlias("tradeTypeName")
	private String tradeTypeName;//行业分类名称

	@XStreamAlias("incomeType")
	private String incomeType;//企业出资人经济成分

	@XStreamAlias("incomeTypeName")
	private String incomeTypeName;//企业出资人经济成分名称

	@XStreamAlias("sizeEnterprise")
	private String sizeEnterprise;//企业规模

	@XStreamAlias("smallEnterprise")
	private String smallEnterprise;//银监会小企业标准

	@XStreamAlias("nonCustomerType")
	private String nonCustomerType;//人行涉农法人客户类型

	@XStreamAlias("custTypeName")
	private String custTypeName;//所属客户关系名称

	@XStreamAlias("adminArgs")
	private String adminArgs;//行政级别

	@XStreamAlias("budgetType")
	private String budgetType;//预算类型

	@XStreamAlias("moneySource")
	private String moneySource;//主要经费来源

	@XStreamAlias("outlaySource")
	private String outlaySource;//主要经费来源

	@XStreamAlias("yearMoney")
	private String yearMoney ;//年拨款金额

	@XStreamAlias("balanceLastyear")
	private String balanceLastyear ;//上年度经费结余

	@XStreamAlias("chargeHoldSum")
	private String chargeHoldSum;//事业性收费自留资金

	@XStreamAlias("creditLevel")
	private String creditLevel;//信用等级

	@XStreamAlias("administerLevel")
	private String administerLevel;//监管评级

	@XStreamAlias("isplatFlag")
	private String isplatFlag;//政府融资平台标志

	@XStreamAlias("corporation")
	private String corporation;//法人或负责人

	@XStreamAlias("corporationPaperType")
	private String corporationPaperType;//法人或负责人证件类型

	@XStreamAlias("corporationId")
	private String corporationId;//法人或负责人证件号码

	@XStreamAlias("isRegion")
	private String isRegion;//是否位于赣江新区

	@XStreamAlias("region")
	private String region;//所属赣江新区区域

	@XStreamAlias("compGreenFlag")
	private String compGreenFlag;//是否绿色企业

	@XStreamAlias("isScientificCus")
	private String isScientificCus;//是否是科技企业

	@XStreamAlias("smallCusStandard")
	private String smallCusStandard;//人行小微客户标准

	@XStreamAlias("taking")
	private String taking;//营业收入

	@XStreamAlias("totalAssets")
	private String totalAssets;//资产总额

	@XStreamAlias("financingInstitutionCode")
	private String financingInstitutionCode;//基本账户行/主要结算账户所属银行

	@XStreamAlias("O1BAKUP1")
	private String O1BAKUP1;//备用1

	@XStreamAlias("O1BAKUP2")
	private String O1BAKUP2;//备用2

	@XStreamAlias("O1BAKUP3")
	private String O1BAKUP3;//备用3

	@XStreamAlias("O1BAKUP4")
	private String O1BAKUP4;//备用4

	@XStreamAlias("O1BAKUP5")
	private String O1BAKUP5;//备用5

	@XStreamAlias("relationInfoQuery")
	private List<RelationInfoQueryDTO> relationInfoQueryDTO;//关系信息组


}
