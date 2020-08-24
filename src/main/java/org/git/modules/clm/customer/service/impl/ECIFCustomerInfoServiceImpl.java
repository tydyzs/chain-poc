package org.git.modules.clm.customer.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.customer.entity.*;
import org.git.modules.clm.customer.service.*;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ecif.*;
import org.git.modules.clm.front.service.IBMMQService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ILoanCompanyInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 从ECIF同步客户信息 服务实体类
 *
 * @author zhouweijie
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ECIFCustomerInfoServiceImpl implements IECIFCustomerInfoService {

	ICsmPartyService csmPartyService;
	ICsmIndividualService csmIndividualService;
	ICsmAddressInfoService csmAddressInfoService;
	ICsmCertInfoService csmCertInfoService;
	ICsmManageTeamService csmManageTeamService;
	ICsmCorporationService csmCorporationService;
	ICrdDetailService crdDetailService;
	ICsmRelationService csmRelationService;
	ICsmPhoneInfoService csmPhoneInfoService;
	IBMMQService ibmmqService;
	ILoanCompanyInfoService loanCompanyInfoService;


	/**
	 * 异步处理客户信息同步
	 *
	 * @param customerNum  客户编号
	 * @param customerType 客户类型
	 */
	@Async
	@Override
	public void asyncDealCustomerInfo(String customerNum, String customerType) {
		dealCustomerInfo(customerNum, customerType);
	}


	/**
	 * 同步客户信息
	 *
	 * @param customerNum  客户编号
	 * @param customerType 客户类型
	 * @return 客户主表实体类
	 */
	@Override
	public CsmParty dealCustomerInfo(String customerNum, String customerType) {
		try {
			if (JxrcbBizConstant.CUSTOMER_TYPE_PRIVATE.equals(customerType)) {
				/*同步对私客户信息*/
				return dealPrivateCustomerInfo(customerNum, customerType);

			} else if (JxrcbBizConstant.CUSTOMER_TYPE_CORPORATION.equals(customerType) ||
				JxrcbBizConstant.CUSTOMER_TYPE_INTERBANK.equals(customerType)) {
				/*同步对公客户信息*/
				return loanCompanyInfoService.dealCompanyCustomerInfo(customerNum);
//				return dealCorporationAndInterbankCustomerInfo(customerNum, customerType);

			} else {
				/*未知客户类型*/
				log.error("未知客户类型码值：" + customerType);
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 同步对私客户信息
	 *
	 * @param customerNum  客户编号
	 * @param customerType 客户类型
	 */
	private CsmParty dealPrivateCustomerInfo(String customerNum, String customerType) {
		/*发送请求，获取响应*/
		ECIFPrivateCustomerResponseDTO rs = (ECIFPrivateCustomerResponseDTO) sendAndReceiveResponse(customerNum, customerType);

		/*创建需要存入数据库的实体对象*/
		CsmManageTeam csmManageTeam = new CsmManageTeam();//管理团队实体类
		CsmParty csmParty = new CsmParty();//客户主表实体类
		CsmIndividual csmIndividual = new CsmIndividual();//个人客户基本信息实体类
		List<CsmRelation> csmRelationList = new ArrayList<>();//关系人信息表
		List<CsmPhoneInfo> csmPhoneInfoList = new ArrayList<>();//电话号码信息组
		List<CsmAddressInfo> csmAddressInfoList = new ArrayList<>();//地址信息组
		List<CsmCertInfo> csmCertInfoList = new ArrayList<>();//证件信息表组

		/*地址信息实体类组赋值*/
		setCsmAddressInfoList(csmAddressInfoList, rs);
		/*证件信息表实体类组赋值*/
		setCsmCertInfoList(csmCertInfoList, rs);
		/*关系人信息实体类组赋值*/
		setCsmRelationList(csmRelationList, rs);
		/*联系电话信息实体类*/
		setCsmPhoneInfoList(csmPhoneInfoList, rs);
		/*管理团队实体类赋值*/
		setCsmManageTeam(csmManageTeam, rs);
		/*客户主表实体类赋值*/
		setCsmParty(csmParty, rs);
		/*个人客户基本信息实体类赋值*/
		setCsmIndividual(csmIndividual, rs);

		/*同步各表信息,并返回对应客户号的客户主表对象*/
		return savePrivateInfo(csmAddressInfoList, csmCertInfoList, csmRelationList,
			csmPhoneInfoList, csmManageTeam, csmParty, csmIndividual);

	}


	/**
	 * 同步对公客户信息
	 *
	 * @param customerNum  客户编号
	 * @param customerType 客户类型
	 */
	private CsmParty dealCorporationAndInterbankCustomerInfo(String customerNum, String customerType) {
		/*发送请求，获取响应*/
		ECIFCompanyCustomerResponseDTO rs = (ECIFCompanyCustomerResponseDTO) sendAndReceiveResponse(customerNum, customerType);

		/*创建需要存入数据库的实体对象*/
		CsmManageTeam csmManageTeam = new CsmManageTeam();//管理团队实体类
		CsmParty csmParty = new CsmParty();//客户主表实体类
		CsmCorporation csmCorporation = new CsmCorporation();//对公客户基本信息实体类
		List<CsmRelation> csmRelationList = new ArrayList<>();//关系人信息组
		List<CsmAddressInfo> csmAddressInfoList = new ArrayList<>();//地址信息组
		List<CsmCertInfo> csmCertInfoList = new ArrayList<>();//证件信息表组
		List<CsmPhoneInfo> csmPhoneInfoList = new ArrayList<>();//电话信息组

		/*关系信息组赋值*/
		setCsmRelationList(csmRelationList, rs);
		/*地址信息实体类部分字段赋值*/
		setCsmAddressInfoList(csmAddressInfoList, rs);
		/*证件信息表实体类部分字段赋值*/
		setCsmCertInfoList(csmCertInfoList, rs);
		/*电话信息组赋值*/
		setCsmPhoneInfoList(csmPhoneInfoList, rs);
		/*管理团队实体类赋值*/
		setCsmManageTeam(csmManageTeam, rs);
		/*客户主表实体类赋值*/
		setCsmParty(csmParty, rs);
		/*对公客户基本信息实体类*/
		setCsmCorporation(csmCorporation, rs);

		/*同步各表信息,并返回对应客户号的客户主表对象*/
		return saveCompanyInfo(csmRelationList, csmAddressInfoList,
			csmCertInfoList, csmPhoneInfoList, csmManageTeam,
			csmParty, csmCorporation);

	}


	/**
	 * 获得响应
	 *
	 * @param customerNum  客户编号
	 * @param customerType 客户类型
	 */
	private Response sendAndReceiveResponse(String customerNum, String customerType) {
		String serviceId;//服务代码
		Request request;//请求对象
		if (JxrcbBizConstant.CUSTOMER_TYPE_PRIVATE.equals(customerType)) {
			/*对私客户*/
			serviceId = JxrcbConstant.ECIF_SERVICE_ID_P01;
			ECIFPrivateCustomerRequestDTO privateCustomerRequestDTO = new ECIFPrivateCustomerRequestDTO();
			privateCustomerRequestDTO.setResolveType(JxrcbBizConstant.RESOLVE_BY_NUM);//识别方式
			privateCustomerRequestDTO.setCustNo(customerNum);//客户编号
			request = privateCustomerRequestDTO;
		} else {
			/*对公客户*/
			serviceId = JxrcbConstant.ECIF_SERVICE_ID_C02;
			ECIFCompanyCustomerRequestDTO companyCustomerRequestDTO = new ECIFCompanyCustomerRequestDTO();
			companyCustomerRequestDTO.setResolveType(JxrcbBizConstant.RESOLVE_BY_NUM);//识别方式
			companyCustomerRequestDTO.setCustNo(customerNum);//客户编号
			request = companyCustomerRequestDTO;
		}
		/*发送请求，获取响应*/
		Response response = ibmmqService.request(serviceId, request);

		return response;
	}

	/**
	 * 客户主表实体类赋值
	 *
	 * @param csmParty 客户主表实体类
	 * @param rs       响应体
	 */
	private void setCsmParty(CsmParty csmParty, ECIFPrivateCustomerResponseDTO rs) {
		csmParty.setCustomerNum(rs.getCustNo());//客户编号
		csmParty.setIsBankRel(rs.getRelPartyInd());//是否我行关联方
		csmParty.setCustomerType(rs.getCustType());//客户类型
		csmParty.setCustomerName(rs.getCustName());//客户名称
	}

	/**
	 * 客户主表实体类赋值
	 *
	 * @param csmParty 客户主表实体类
	 * @param rs       响应体
	 */
	private void setCsmParty(CsmParty csmParty, ECIFCompanyCustomerResponseDTO rs) {
		csmParty.setCustomerNum(rs.getCustNo());//客户编号
		csmParty.setIsBankRel(rs.getRelPartyInd());//是否我行关联方
		csmParty.setCustomerType(rs.getCustType());//客户类型
		csmParty.setCustomerName(rs.getCustName());//客户名称
	}

	/**
	 * 管理团队实体类赋值
	 *
	 * @param csmManageTeam 管理团队实体类
	 * @param rs            响应体
	 */
	private void setCsmManageTeam(CsmManageTeam csmManageTeam, ECIFPrivateCustomerResponseDTO rs) {
		csmManageTeam.setCustomerNum(rs.getCustNo());//客户编号
		csmManageTeam.setUserNum(rs.getCustManagerNo());//经办用户
		csmManageTeam.setOrgNum(rs.getLastUpdatedOrg());//经办机构
		csmManageTeam.setUserPlacingCd(JxrcbBizConstant.MANAGEMENT_RIGHT);//权限类型
	}

	/**
	 * 管理团队实体类赋值
	 *
	 * @param csmManageTeam 管理团队实体类
	 * @param rs            响应体
	 */
	private void setCsmManageTeam(CsmManageTeam csmManageTeam, ECIFCompanyCustomerResponseDTO rs) {
		csmManageTeam.setCustomerNum(rs.getCustNo());//客户编号
		csmManageTeam.setUserNum(rs.getCustManagerNo());//经办用户
		csmManageTeam.setOrgNum(rs.getLastUpdatedOrg());//经办机构
		csmManageTeam.setUserPlacingCd(JxrcbBizConstant.MANAGEMENT_RIGHT);//权限类型
	}

	/**
	 * 电话信息实体类组赋值
	 *
	 * @param csmPhoneInfoList 电话信息实体类组
	 * @param rs               响应体
	 */
	private void setCsmPhoneInfoList(List<CsmPhoneInfo> csmPhoneInfoList, ECIFPrivateCustomerResponseDTO rs) {
		if (rs.getPerPhoneInfo().getPerPhoneInfoQuery() != null) {
			for (PerPhoneInfoResponseDTO phoneInfo : rs.getPerPhoneInfo().getPerPhoneInfoQuery()) {
				CsmPhoneInfo csmPhoneInfo = new CsmPhoneInfo();
				csmPhoneInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmPhoneInfo.setConnType(phoneInfo.getConnType());//联系类型(CD000003)
				csmPhoneInfo.setInterCode(phoneInfo.getInterCode());//国际长途区号
				csmPhoneInfo.setInlandCode(phoneInfo.getInlandCode());//国内长途区号
				csmPhoneInfo.setTelNumber(phoneInfo.getTelNumber());//联系号码
				csmPhoneInfo.setExtenNum(phoneInfo.getExtenNum());//分机号
				csmPhoneInfo.setIsCheckFlag(phoneInfo.getIsCheckFlag());//是否已核实
				csmPhoneInfo.setCreateTime(CommonUtil.getWorkDateTime());
				csmPhoneInfo.setUpdateTime(CommonUtil.getWorkDateTime());
				csmPhoneInfoList.add(csmPhoneInfo);
			}
		}

	}

	/**
	 * 电话信息实体类组赋值
	 *
	 * @param csmPhoneInfoList 电话信息实体类组
	 * @param rs               响应体
	 */
	private void setCsmPhoneInfoList(List<CsmPhoneInfo> csmPhoneInfoList, ECIFCompanyCustomerResponseDTO rs) {
		if (rs.getOrgPhoneInfoQuery().getOrgPhoneInfoQuery() != null) {
			for (OrgPhoneInfoResponseDTO phoneInfo : rs.getOrgPhoneInfoQuery().getOrgPhoneInfoQuery()) {
				CsmPhoneInfo csmPhoneInfo = new CsmPhoneInfo();
				csmPhoneInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmPhoneInfo.setConnType(phoneInfo.getConnType());//联系类型(CD000003)
				csmPhoneInfo.setInterCode(phoneInfo.getInterCode());//国际长途区号
				csmPhoneInfo.setInlandCode(phoneInfo.getInlandCode());//国内长途区号
				csmPhoneInfo.setTelNumber(phoneInfo.getTelNumber());//联系号码
				csmPhoneInfo.setExtenNum(phoneInfo.getExtenNum());//分机号
				csmPhoneInfo.setIsCheckFlag(phoneInfo.getIsCheckFlag());//是否已核实
				csmPhoneInfo.setCreateTime(CommonUtil.getWorkDateTime());
				csmPhoneInfo.setUpdateTime(CommonUtil.getWorkDateTime());
				csmPhoneInfoList.add(csmPhoneInfo);
			}
		}

	}

	/**
	 * 证件信息实体类组赋值
	 *
	 * @param csmCertInfoList 证件信息实体类组
	 * @param rs              响应体
	 */
	private void setCsmCertInfoList(List<CsmCertInfo> csmCertInfoList, ECIFPrivateCustomerResponseDTO rs) {
		if (rs.getPerCertInfo().getPerCertInfoQuery() != null) {
			for (PerCertInfoResponseDTO certInfo : rs.getPerCertInfo().getPerCertInfoQuery()) {
				CsmCertInfo csmCertInfo = new CsmCertInfo();
				csmCertInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmCertInfo.setCertType(certInfo.getCertType());//证件类型
				csmCertInfo.setCertNum(certInfo.getCertNum());//证件号码
				csmCertInfo.setCertFlag(certInfo.getCertFlag());//开户证件标识
				csmCertInfo.setCertStartDate(certInfo.getCertStartDate());//证件生效日期
				csmCertInfo.setCertEndDate(certInfo.getCertEndDate());//证件到期日期
				csmCertInfo.setIssuedInst(certInfo.getIssuedInst());//证件发放机关
				csmCertInfo.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
				csmCertInfo.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
				csmCertInfoList.add(csmCertInfo);
			}
		}

	}

	/**
	 * 证件信息实体类组赋值
	 *
	 * @param csmCertInfoList 证件信息实体类组
	 * @param rs              响应体
	 */
	private void setCsmCertInfoList(List<CsmCertInfo> csmCertInfoList, ECIFCompanyCustomerResponseDTO rs) {
		if (rs.getOrgCertInfoQuery().getOrgCertInfoQuery() != null) {
			for (OrgCertInfoResponseDTO certInfo : rs.getOrgCertInfoQuery().getOrgCertInfoQuery()) {
				CsmCertInfo csmCertInfo = new CsmCertInfo();
				csmCertInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmCertInfo.setCertType(certInfo.getCertType());//证件类型
				csmCertInfo.setCertNum(certInfo.getCertNum());//证件号码
				csmCertInfo.setCertFlag(certInfo.getCertFlag());//开户证件标识
				csmCertInfo.setCertStartDate(certInfo.getCertStartDate());//证件生效日期
				csmCertInfo.setCertEndDate(certInfo.getCertEndDate());//证件到期日期
				csmCertInfo.setIssuedInst(certInfo.getIssuedInst());//证件发放机关
				csmCertInfo.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
				csmCertInfo.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
				csmCertInfoList.add(csmCertInfo);
			}
		}

	}

	/**
	 * 地址信息实体类组赋值
	 *
	 * @param csmAddressInfoList 地址信息实体类组
	 * @param rs                 响应体
	 */
	private void setCsmAddressInfoList(List<CsmAddressInfo> csmAddressInfoList, ECIFPrivateCustomerResponseDTO rs) {
		if (rs.getPerAddrInfo().getPerAddrInfoQuery() != null) {
			for (PerAddrInfoResponseDTO addInfo : rs.getPerAddrInfo().getPerAddrInfoQuery()) {
				CsmAddressInfo csmAddressInfo = new CsmAddressInfo();
				csmAddressInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmAddressInfo.setConnType(addInfo.getConnType());//联系类型(CD000031)
				csmAddressInfo.setCounRegi(addInfo.getCounRegi());//国家和地区代码
				csmAddressInfo.setProvince(addInfo.getProvince());//省代码
				csmAddressInfo.setCity(addInfo.getCity());//市代码
				csmAddressInfo.setCounty(addInfo.getCounty());//县代码
				csmAddressInfo.setStreet(addInfo.getStreet());//街道地址
				csmAddressInfo.setDetailAddr(addInfo.getDetailAddr());//详细地址
				csmAddressInfo.setEngAddr(addInfo.getEngAddr());//英文地址
				csmAddressInfo.setPostCode(addInfo.getPostCode());//邮政编码
				csmAddressInfo.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
				csmAddressInfo.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
				csmAddressInfoList.add(csmAddressInfo);
			}
		}

	}


	/**
	 * 地址信息实体类组赋值
	 *
	 * @param csmAddressInfoList 地址信息实体类组
	 * @param rs                 响应体
	 */
	private void setCsmAddressInfoList(List<CsmAddressInfo> csmAddressInfoList, ECIFCompanyCustomerResponseDTO rs) {
		if (rs.getOrgAddrInfoQuery().getOrgAddrInfoQuery() != null) {
			for (OrgAddrInfoResponseDTO addInfo : rs.getOrgAddrInfoQuery().getOrgAddrInfoQuery()) {
				CsmAddressInfo csmAddressInfo = new CsmAddressInfo();
				csmAddressInfo.setCustomerNum(rs.getCustNo());//客户编号
				csmAddressInfo.setConnType(addInfo.getConnType());//联系类型(CD000031)
				csmAddressInfo.setCounRegi(addInfo.getCounRegi());//国家和地区代码
				csmAddressInfo.setProvince(addInfo.getProvince());//省代码
				csmAddressInfo.setCity(addInfo.getCity());//市代码
				csmAddressInfo.setCounty(addInfo.getCounty());//县代码
				csmAddressInfo.setStreet(addInfo.getStreet());//街道地址
				csmAddressInfo.setDetailAddr(addInfo.getDetailAddr());//详细地址
				csmAddressInfo.setEngAddr(addInfo.getEngAddr());//英文地址
				csmAddressInfo.setPostCode(addInfo.getPostCode());//邮政编码
				csmAddressInfo.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
				csmAddressInfo.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
				csmAddressInfoList.add(csmAddressInfo);
			}
		}

	}

	/**
	 * 关系人信息实体类组赋值
	 *
	 * @param csmRelationList 关系人信息组
	 * @param rs              响应体
	 */
	private void setCsmRelationList(List<CsmRelation> csmRelationList, ECIFPrivateCustomerResponseDTO rs) {
		if (rs.getRelPeopleInfo().getRelPeopleInfoQuery() != null) {
			for (RelPeopleInfoResponseDTO relPeopleInfo : rs.getRelPeopleInfo().getRelPeopleInfoQuery()) {
				CsmRelation csmRelation = new CsmRelation();
				csmRelation.setCustomerNum(rs.getCustNo());//客户编号
				//TODO 获取关系人客户号
				//csmRelation.setRelMark(rs.getRelCustNo()==null ? "0" : "1");//关联客户标志
				//csmRelation.setRelCustomerNum(rs.getRelCustNo());//关系人客户编号
				csmRelation.setRelType(relPeopleInfo.getRelType());//客户关系类型(CD000016)
				csmRelation.setCustName(relPeopleInfo.getCustName());//关系人名称
				csmRelation.setCertType(relPeopleInfo.getCertType());//证件类型(CD000003)
				csmRelation.setCertNum(relPeopleInfo.getCertNum());//证件号码
				csmRelation.setGender(relPeopleInfo.getGender());//性别(CD000004)
				csmRelation.setCreateTime(CommonUtil.getWorkDateTime());
				csmRelation.setUpdateTime(CommonUtil.getWorkDateTime());
				csmRelationList.add(csmRelation);
			}
		}

	}

	/**
	 * 关系人信息实体类组赋值
	 *
	 * @param csmRelationList 关系人信息组
	 * @param rs              响应体
	 */
	private void setCsmRelationList(List<CsmRelation> csmRelationList, ECIFCompanyCustomerResponseDTO rs) {
		if (rs.getOrgPeopleInfoQuery().getOrgPeopleInfoQuery() != null) {
			for (OrgPeopleInfoResponseDTO peopleInfo : rs.getOrgPeopleInfoQuery().getOrgPeopleInfoQuery()) {
				CsmRelation relation = new CsmRelation();
				relation.setRelMark(peopleInfo.getCustNo() == null ? "0" : "1");//关联客户标志
				relation.setRelCustomerNum(peopleInfo.getCustNo());//关系人客户编号
				relation.setCustomerNum(rs.getCustNo());//客户编号
				relation.setRelType(peopleInfo.getRelType());//客户关系类型(CD000016)
				relation.setCustName(peopleInfo.getCustName());//关系人名称
				relation.setCertType(peopleInfo.getCertType());//证件类型(CD000003)
				relation.setCertNum(peopleInfo.getCertNum());//证件号码
				relation.setGender(peopleInfo.getGender());//性别(CD000004)
				relation.setCreateTime(CommonUtil.getWorkDateTime());
				relation.setUpdateTime(CommonUtil.getWorkDateTime());
				csmRelationList.add(relation);
			}
		}

		if (rs.getOrgCompanyInfoQuery().getOrgCompanyInfoQuery() != null) {
			for (OrgCompanyInfoResponseDTO companyInfo : rs.getOrgCompanyInfoQuery().getOrgCompanyInfoQuery()) {
				CsmRelation companyRelation = new CsmRelation();
				//TODO 获取关系人客户号
				//csmRelation.setRelMark(rs.getRelCustNo()==null ? "0" : "1");//关联客户标志
				//csmRelation.setRelCustomerNum(rs.getRelCustNo());//关系人客户编号
				companyRelation.setCustomerNum(rs.getCustNo());//客户编号
				companyRelation.setRelType(companyInfo.getRelType());//客户关系类型(CD000016)
				companyRelation.setCustName(companyInfo.getCustName());//关系人名称
				companyRelation.setCertType(companyInfo.getCertType());//证件类型(CD000003)
				companyRelation.setCertNum(companyInfo.getCertNum());//证件号码
				companyRelation.setUnitScale(companyInfo.getUnitScale());//企业规模(CD000020)
				companyRelation.setCreateTime(CommonUtil.getWorkDateTime());
				companyRelation.setUpdateTime(CommonUtil.getWorkDateTime());
				csmRelationList.add(companyRelation);
			}
		}

	}


	/**
	 * 个人客户基本信息实体类赋值
	 *
	 * @param csmIndividual 个人客户基本信息实体类
	 * @param rs            响应体
	 */
	private void setCsmIndividual(CsmIndividual csmIndividual, ECIFPrivateCustomerResponseDTO rs) {
		csmIndividual.setCustomerNum(rs.getCustNo());//客户编号
		csmIndividual.setCustomerType(rs.getCustType());//客户类型
		csmIndividual.setCustStatus(rs.getCustStatus());//客户状态
		csmIndividual.setCustName(rs.getCustName());//客户名称
		csmIndividual.setPinyinName(rs.getPinyinName());//客户拼音名称
		csmIndividual.setGender(rs.getGender());//性别
		csmIndividual.setNation(rs.getNation());//国籍
		csmIndividual.setRace(rs.getRace());//民族
		csmIndividual.setBirthDate(rs.getBirthDate());//出生日期
		csmIndividual.setPoliStatus(rs.getPoliStatus());//政治面貌
		csmIndividual.setMarrStatus(rs.getMarrStatus());//婚姻状况
		csmIndividual.setEmpStat(rs.getEmpStat());//从业状态
		csmIndividual.setHouseCicts(rs.getHouseCicts());//户籍所在城市
		csmIndividual.setHouseType(rs.getHouseType());//户籍类型
		csmIndividual.setHealthyStatus(rs.getHealthyStatus());//健康状态
		csmIndividual.setFamilyNum(rs.getFamilyNum());//家庭成员数
		csmIndividual.setEducation(rs.getEducation());//最高学历
		csmIndividual.setHighAcadeDegree(rs.getHighAcadeDegree());//最高学位
		csmIndividual.setCustManagerNo(rs.getCustManagerNo());//客户经理编号
		csmIndividual.setWorkUnitName(rs.getWorkUnitName());//工作单位全称
		csmIndividual.setWorkIndustry(rs.getWorkIndustry());//工作单位所属行业
		csmIndividual.setUnitCharacter(rs.getUnitCharacter());//工作单位性质
		csmIndividual.setDuty(rs.getDuty());//职务
		csmIndividual.setOccupation1(rs.getOccupation1());//职业1
		csmIndividual.setOccupation2(rs.getOccupation2());//职业2
		csmIndividual.setOccupation3(rs.getOccupation3());//职业3
		csmIndividual.setOccupationExplain(rs.getOccupationExplain());//职业说明
		csmIndividual.setTechTiyleLevel(rs.getTechTiyleLevel());//职称
		csmIndividual.setPayCreditFlag(rs.getPayCreditFlag());//是否代发工资
		csmIndividual.setIsBlankFlag(rs.getIsBlankFlag());//个人客户黑名单标志
		csmIndividual.setEmployeeFlag(rs.getEmployeeFlag());//员工标志
		csmIndividual.setAgriRelatedInd(rs.getAgriRelatedInd());//是否涉农
		csmIndividual.setSeiorExecuInd(rs.getSeiorExecuInd());//是否本行高管
		csmIndividual.setRelPartyInd(rs.getRelPartyInd());//我行关联方标志
		csmIndividual.setPerYearIncome(
			new BigDecimal(rs.getPerYearIncome().isEmpty() ? "0" : rs.getPerYearIncome())
		);//个人年收入（若为空则记录为0）
		csmIndividual.setTaxResType(rs.getTaxResType());//税收居民身份
		csmIndividual.setResidSituat(rs.getResidSituat());//居住状况
		csmIndividual.setCustGrade(rs.getCustGrade());//客户等级
		csmIndividual.setCustSatis(rs.getCustSatis());//客户满意度
		csmIndividual.setPerTotalAsset(
			new BigDecimal(rs.getPerTotalAsset().isEmpty() ? "0" : rs.getPerTotalAsset())
		);//个人总资产（若为空则记录为0）
		csmIndividual.setLiabilityBalance(
			new BigDecimal(rs.getLiabilityBalance().isEmpty() ? "0" : rs.getLiabilityBalance())
		);//个人总负债（若为空则记录为0）
		csmIndividual.setPerAssestType(rs.getPerAssestType());//个人资产类型
		csmIndividual.setPerLiabType(rs.getPerLiabType());//个人负债类型
		csmIndividual.setResidNonResid(rs.getResidNonResid());//居民/非居民
		csmIndividual.setCreatedTs(rs.getCreatedTs());//开户日期
		csmIndividual.setLastUpdatedOrg(rs.getLastUpdatedOrg());//开户机构
		csmIndividual.setLastUpdatedTe(rs.getLastUpdatedTe());//开户柜员
		csmIndividual.setCreateTime(CommonUtil.getWorkDateTime());
		csmIndividual.setUpdateTime(CommonUtil.getWorkDateTime());
	}


	/**
	 * 对公客户基本信息实体类附体
	 *
	 * @param csmCorporation 对公客户基本信息实体类
	 * @param rs             响应体
	 */
	private void setCsmCorporation(CsmCorporation csmCorporation, ECIFCompanyCustomerResponseDTO rs) {
		csmCorporation.setCustomerNum(rs.getCustNo());//客户编号
		csmCorporation.setCustomerType(rs.getCustType());//客户类型(CD000212)
		csmCorporation.setCustStatus(rs.getCustStatus());//客户状态(CD000032)
		csmCorporation.setBankCustFlag(rs.getBankCustFlag());//同业客户简易标志(1 -简易信息 2 -详细信息 )
		csmCorporation.setCustManagerNo(rs.getCustManagerNo());//客户经理编号
		csmCorporation.setCustName(rs.getCustName());//客户名称
		csmCorporation.setOrgShortName(rs.getOrgShortName());//组织中文简称
		csmCorporation.setCustEngName(rs.getCustEngName());//客户英文名称
		csmCorporation.setOrgRngShortName(rs.getOrgRngShortName());//组织英文简称
		csmCorporation.setNationalEconomyType(rs.getNationalEconomyType());//国民经济部门(CD000039)
		csmCorporation.setNationalEconomyDepart1(rs.getNationalEconomyDepart1());//国民经济行业1(CD000015)
		csmCorporation.setNationalEconomyDepart2(rs.getNationalEconomyDepart2());//国民经济行业2(CD000015)
		csmCorporation.setNationalEconomyDepart3(rs.getNationalEconomyDepart3());//国民经济行业3(CD000015)
		csmCorporation.setNationalEconomyDepart4(rs.getNationalEconomyDepart4());//国民经济行业4(CD000015)
		csmCorporation.setFoundDate(rs.getFoundDate());//成立日期
		csmCorporation.setRegCapital(CommonUtil.stringToBigDecimal(rs.getRegCapital()));//注册资本
		csmCorporation.setRegCptlCurr(rs.getRegCptlCurr());//注册资本币种(CD000019)
		csmCorporation.setUnitScale(rs.getUnitScale());//企业规模(CD000020)
		csmCorporation.setEmpNumber(rs.getEmpNumber());//企业员工人数
		csmCorporation.setPayCreditFlag(rs.getPayCreditFlag());//是否代发工资(CD000167)
		csmCorporation.setCountryCode(rs.getCountryCode());//国别代码(CD000001)
		csmCorporation.setCreditOrganCode(rs.getCreditOrganCode());//机构信用代码
		csmCorporation.setBusinScope(rs.getBusinScope());//经营范围
		csmCorporation.setBankCustType1(rs.getBankCustType1());//同业客户类型1(CD000218)
		csmCorporation.setBankCustType2(rs.getBankCustType2());//同业客户类型2(CD000218)
		csmCorporation.setBankPaySysNum(rs.getBankPaySysNum());//人行现代化支付系统银行行号
		csmCorporation.setGroupCreditIndicator(rs.getGroupCreditIndicator());//集团授信标志(CD000167)
		csmCorporation.setBankIndicator(rs.getBankIndicator());//本行黑名单标志(CD000167)
		csmCorporation.setRelPartyInd(rs.getRelPartyInd());//我行关联方标志(CD000167)
		csmCorporation.setSwiftCode(rs.getSwiftCode());//SWIFT代码
		csmCorporation.setBeneCustType(rs.getBeneCustType());//对公客户类型(1- 企业 2- 公司 3- 其他  4- 免识别客户)
		csmCorporation.setRemarks(rs.getRemarks());//备注
		csmCorporation.setCreatedTs(rs.getCreatedTs());//进入ECIF日期
		csmCorporation.setLastUpdatedOrg(rs.getLastUpdatedOrg());//最后操作机构
		csmCorporation.setLastUpdatedTe(rs.getLastUpdatedTe());//最后操作柜员
		csmCorporation.setCreateTime(CommonUtil.getWorkDateTime());
		csmCorporation.setUpdateTime(CommonUtil.getWorkDateTime());
	}


	/**
	 * 同步对私客户各表信息
	 *
	 * @param csmAddressInfoList 地址信息组
	 * @param csmCertInfoList    证件信息组
	 * @param csmRelationList    关系人信息组
	 * @param csmPhoneInfoList   电话信息组
	 * @param csmManageTeam      管理团队实体类
	 * @param csmParty           客户主表实体类
	 * @param csmIndividual      个人客户基本信息实体类
	 * @return 客户主表对象
	 */
	private CsmParty savePrivateInfo(List<CsmAddressInfo> csmAddressInfoList, List<CsmCertInfo> csmCertInfoList,
									 List<CsmRelation> csmRelationList, List<CsmPhoneInfo> csmPhoneInfoList,
									 CsmManageTeam csmManageTeam, CsmParty csmParty, CsmIndividual csmIndividual) {
		try {
			/*通过客户号查询客户主表是否存在数据*/
			CsmParty csmPartyQuery = csmPartyService.getById(csmParty.getCustomerNum());
			if (csmPartyQuery == null) {
				/*不存在数据*/
				/*插入 管理团队表*/
				csmManageTeam.setCreateTime(CommonUtil.getWorkDateTime());
				csmManageTeamService.save(csmManageTeam);
				/*插入 客户主表*/
				csmParty.setCreateTime(CommonUtil.getWorkDateTime());
				csmPartyService.save(csmParty);
				/*插入 人客户基本信息表*/
				csmIndividual.setCreateTime(CommonUtil.getWorkDateTime());
				csmIndividualService.save(csmIndividual);
				/*插入 对私关系人信息组*/
				csmRelationService.saveBatch(csmRelationList);
				/*插入 电话信息组*/
				csmPhoneInfoService.saveBatch(csmPhoneInfoList);
				/*插入 地址信息表*/
				csmAddressInfoService.saveBatch(csmAddressInfoList);
				/*插入 证件信息表*/
				csmCertInfoService.saveBatch(csmCertInfoList);
			} else {
				/*存在数据*/
				/*更新 管理团队表*/
				csmManageTeam.setUpdateTime(CommonUtil.getWorkDateTime());
				CsmManageTeam manageTeam = new CsmManageTeam();
				manageTeam.setCustomerNum(csmParty.getCustomerNum());
				csmManageTeamService.update(csmManageTeam, Condition.getQueryWrapper(manageTeam));
				/*更新 客户主表*/
				csmParty.setUpdateTime(CommonUtil.getWorkDateTime());
				csmPartyService.updateById(csmParty);
				/*更新 个人客户基本信息表*/
				csmIndividual.setUpdateTime(CommonUtil.getWorkDateTime());
				csmIndividualService.updateById(csmIndividual);
				/*更新 关系人信息组 (先清空再插入)*/
				CsmRelation relation = new CsmRelation();
				relation.setCustomerNum(csmParty.getCustomerNum());//删除条件
				csmRelationService.remove(Condition.getQueryWrapper(relation));
				csmRelationService.saveBatch(csmRelationList);
				/*更新 电话信息组 (先清空再插入)*/
				CsmPhoneInfo csmPhoneInfo = new CsmPhoneInfo();
				csmPhoneInfo.setCustomerNum(csmParty.getCustomerNum());//删除条件
				csmPhoneInfoService.remove(Condition.getQueryWrapper(csmPhoneInfo));
				csmPhoneInfoService.saveBatch(csmPhoneInfoList);
				/*更新 地址信息表 (先清空再插入)*/
				CsmAddressInfo csmAddressInfo = new CsmAddressInfo();
				csmAddressInfo.setCustomerNum(csmParty.getCustomerNum());//删除条件
				csmAddressInfoService.remove(Condition.getQueryWrapper(csmAddressInfo));
				csmAddressInfoService.saveBatch(csmAddressInfoList);
				/*更新 证件信息表 (先清空再插入)*/
				CsmCertInfo csmCertInfo = new CsmCertInfo();
				csmCertInfo.setCustomerNum(csmParty.getCustomerNum());//删除条件
				csmCertInfoService.remove(Condition.getQueryWrapper(csmCertInfo));
				csmCertInfoService.saveBatch(csmCertInfoList);
			}
		} catch (Exception e) {
			log.error("同步对私客户信息异常！");
			throw e;
		}
		/*各表同步完成 返回对应客户号的客户主表实体类*/
		return csmPartyService.getById(csmParty.getCustomerNum());
	}


	/**
	 * 同步对公客户各表信息
	 *
	 * @param csmRelationList    关系人信息组
	 * @param csmAddressInfoList 地址信息组
	 * @param csmCertInfoList    证件信息表组
	 * @param csmPhoneInfoList   电话信息组
	 * @param csmManageTeam      管理团队实体类
	 * @param csmParty           客户主表实体类
	 * @param csmCorporation     对公客户基本信息实体类
	 * @return 客户主表实体类
	 */
	private CsmParty saveCompanyInfo(List<CsmRelation> csmRelationList,
									 List<CsmAddressInfo> csmAddressInfoList,
									 List<CsmCertInfo> csmCertInfoList,
									 List<CsmPhoneInfo> csmPhoneInfoList,
									 CsmManageTeam csmManageTeam, CsmParty csmParty,
									 CsmCorporation csmCorporation) {
		try {
			/*通过客户号查询客户主表是否存在数据*/
			CsmParty csmPartyQuery = csmPartyService.getById(csmParty.getCustomerNum());
			if (csmPartyQuery == null) {
				/*不存在数据*/
				/*插入 管理团队表*/
				csmManageTeam.setCreateTime(CommonUtil.getWorkDateTime());
				csmManageTeamService.save(csmManageTeam);
				/*插入 客户主表*/
				csmParty.setCreateTime(CommonUtil.getWorkDateTime());
				csmPartyService.save(csmParty);
				/*插入 公司客户基本信息表*/
				csmCorporation.setCreateTime(CommonUtil.getWorkDateTime());
				csmCorporationService.save(csmCorporation);
				/*插入 关系信息组*/
				csmRelationService.saveBatch(csmRelationList);
				/*插入 电话信息组*/
				csmPhoneInfoService.saveBatch(csmPhoneInfoList);
				/*插入 地址信息表*/
				csmAddressInfoService.saveBatch(csmAddressInfoList);
				/*插入 证件信息表*/
				csmCertInfoService.saveBatch(csmCertInfoList);
			} else {
				/*更新 管理团队表*/
				CsmManageTeam manageTeam = new CsmManageTeam();
				manageTeam.setCustomerNum(csmParty.getCustomerNum());
				csmManageTeam.setUpdateTime(CommonUtil.getWorkDateTime());
				csmManageTeamService.update(csmManageTeam, Condition.getQueryWrapper(manageTeam));
				/*更新 客户主表*/
				csmParty.setUpdateTime(CommonUtil.getWorkDateTime());
				csmPartyService.updateById(csmParty);
				/*更新 公司客户基本信息表*/
				csmCorporation.setUpdateTime(CommonUtil.getWorkDateTime());
				csmCorporationService.updateById(csmCorporation);
				/*更新 关系信息组*/
				CsmRelation csmRelation = new CsmRelation();
				csmRelation.setCustomerNum(csmParty.getCustomerNum());
				csmRelationService.remove(Condition.getQueryWrapper(csmRelation));
				csmRelationService.saveBatch(csmRelationList);
				/*更新 电话信息组*/
				CsmPhoneInfo phoneInfo = new CsmPhoneInfo();
				phoneInfo.setCustomerNum(csmParty.getCustomerNum());
				csmPhoneInfoService.remove(Condition.getQueryWrapper(phoneInfo));
				csmPhoneInfoService.saveBatch(csmPhoneInfoList);
				/*更新 地址信息表*/
				CsmAddressInfo csmAddressInfo = new CsmAddressInfo();
				csmAddressInfo.setCustomerNum(csmParty.getCustomerNum());
				csmAddressInfoService.remove(Condition.getQueryWrapper(csmAddressInfo));
				csmAddressInfoService.saveBatch(csmAddressInfoList);
				/*更新 证件信息表*/
				CsmCertInfo csmCertInfo = new CsmCertInfo();
				csmCertInfo.setCustomerNum(csmParty.getCustomerNum());
				csmCertInfoService.remove(Condition.getQueryWrapper(csmCertInfo));
				csmCertInfoService.saveBatch(csmCertInfoList);
			}
		} catch (Exception e) {
			log.error("同步对公客户信息异常！");
			throw e;
		}
		/*同步信息完成，返回对应客户的客户主表对象*/
		return csmPartyService.getById(csmParty.getCustomerNum());
	}

}
