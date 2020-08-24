package org.git.modules.clm.loan.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.customer.entity.CsmCorporation;
import org.git.modules.clm.customer.entity.CsmManageTeam;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.entity.CsmRelation;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.customer.service.ICsmManageTeamService;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.customer.service.ICsmRelationService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCompanyCustomerRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCompanyCustomerResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.RelationInfoQueryDTO;
import org.git.modules.clm.front.service.IBMMQService;
import org.git.modules.clm.loan.service.ILoanCompanyInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 信贷系统-对公客户信息同步 服务实现类
 *
 * @author zhouweijie
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoanCompanyInfoServiceImpl implements ILoanCompanyInfoService {

	ICsmCorporationService csmCorporationService;
	ICsmRelationService csmRelationService;
	ICsmManageTeamService csmManageTeamService;
	ICsmPartyService csmPartyService;
	IBMMQService ibmmqService;

	/**
	 * 同步对公客户信息
	 *
	 * @param customer 客户编号
	 * @return 对公客户信息表实体类
	 */
	@Override
	public CsmParty dealCompanyCustomerInfo(String customer) {
		LoanCompanyCustomerResponseDTO rs = (LoanCompanyCustomerResponseDTO) sendAndReceiveResponse(customer);
		/*创建需要存入数据库的实体对象*/
		CsmManageTeam csmManageTeam = new CsmManageTeam();//管理团队实体类
		CsmParty csmParty = new CsmParty();//客户主表实体类
		CsmCorporation csmCorporation = new CsmCorporation();//对公客户基本信息实体类
		List<CsmRelation> csmRelationList = new ArrayList<>();//关系人信息组

		/*关系信息组赋值*/
		setCsmRelationList(csmRelationList, rs);
		/*管理团队实体类赋值*/
		setCsmManageTeam(csmManageTeam, rs);
		/*客户主表实体类赋值*/
		setCsmParty(csmParty, rs);
		/*对公客户基本信息实体类*/
		setCsmCorporation(csmCorporation, rs);

		/*同步各表信息,并返回对应客户号的客户主表对象*/
		return saveCompanyInfo(csmRelationList, csmManageTeam, csmParty, csmCorporation);
	}

	/**
	 * 同步对公客户额信息
	 * @param csmRelationList 关系人信息组
	 * @param csmManageTeam 管理团队实体类
	 * @param csmParty 客户主表实体类
	 * @param csmCorporation 对公客户基本信息实体类
	 * @return
	 */
	private CsmParty saveCompanyInfo(List<CsmRelation> csmRelationList,
										   CsmManageTeam csmManageTeam,
										   CsmParty csmParty,
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
			}
		} catch (Exception e) {
			log.error("同步对公客户信息异常！");
			throw e;
		}
		/*同步信息完成，返回对应客户的客户主表对象*/
		return csmPartyService.getById(csmCorporation.getCustomerNum());
	}

	/**
	 *
	 * @param csmCorporation
	 * @param rs
	 */
	private void setCsmCorporation(CsmCorporation csmCorporation, LoanCompanyCustomerResponseDTO rs) {
		csmCorporation.setCustomerNum(rs.getCustomerId());//客户编号
		csmCorporation.setCustomerType(rs.getCustomerType());//客户类型
		csmCorporation.setCustName(rs.getCustName());//客户名称
		csmCorporation.setOrgShortName(rs.getGeneralName());//组织中文简称
		csmCorporation.setCustEngName(rs.getForeignName ());//客户英文名称
		csmCorporation.setNationalEconomyDepart1(rs.getTradeType());//国民经济行业1
		csmCorporation.setFoundDate(rs.getCreateDate());//成立日期
		csmCorporation.setRegCapital(CommonUtil.stringToBigDecimal(rs.getRegisterCapital()));//注册资本
		csmCorporation.setRegCptlCurr(rs.getRegisterCurrency());//注册资本币种(CD000019)
		csmCorporation.setUnitScale(rs.getSizeEnterprise());//企业规模(CD000020)
		csmCorporation.setCountryCode(rs.getCountry());//国别代码
		csmCorporation.setCreditOrganCode(rs.getUnitCreditCode ());//机构信用代码
		csmCorporation.setBusinScope(rs.getBusinessRange());//经营范围
		csmCorporation.setCreateTime(CommonUtil.getWorkDateTime());
		csmCorporation.setUpdateTime(CommonUtil.getWorkDateTime());
	}

	/**
	 * 客户主表实体类赋值
	 *
	 * @param csmParty 客户主表实体类
	 * @param rs       响应体
	 */
	private void setCsmParty(CsmParty csmParty, LoanCompanyCustomerResponseDTO rs) {
		csmParty.setCustomerNum(rs.getCustomerId());//客户编号
		//csmParty.setIsBankRel(rs.get);//是否我行关联方
		csmParty.setCustomerType(rs.getCustomerType());//客户类型
		csmParty.setCustomerName(rs.getCustName());//客户名称
	}

	/**
	 * 管理团队实体类赋值
	 *
	 * @param csmManageTeam 管理团队实体类
	 * @param rs            响应实体
	 */
	private void setCsmManageTeam(CsmManageTeam csmManageTeam, LoanCompanyCustomerResponseDTO rs) {
		csmManageTeam.setCustomerNum(rs.getCustomerId());//客户编号
		//csmManageTeam.setUserNum();//经办用户
		//csmManageTeam.setOrgNum();//经办机构
		csmManageTeam.setUserPlacingCd(JxrcbBizConstant.MANAGEMENT_RIGHT);//权限类型
	}

	/**
	 * 关系人信息组赋值
	 *
	 * @param csmRelationList 关系人信息组
	 * @param rs              响应实体
	 */
	private void setCsmRelationList(List<CsmRelation> csmRelationList, LoanCompanyCustomerResponseDTO rs) {
		for (RelationInfoQueryDTO relationInfo : rs.getRelationInfoQueryDTO()) {
			CsmRelation csmRelation = new CsmRelation();
			csmRelation.setCustomerNum(rs.getCustomerId());//客户号
			csmRelation.setRelCustomerNum(relationInfo.getRelCustomerNum());//关系人客户号
			csmRelation.setRelCustomerType(relationInfo.getRelCustomerType());//关系人客户类型
			csmRelation.setCustName(relationInfo.getCustName());//关系人名称
			csmRelation.setRelType(relationInfo.getRelType());//关系人类型
			csmRelation.setCertType(relationInfo.getCertType());//证件类型
			csmRelation.setCertNum(relationInfo.getCertNum());//证件类型
			csmRelation.setRelMark(relationInfo.getBankRelMark());//行内外关联标志(CD000167)
			csmRelationList.add(csmRelation);
		}
	}

	/**
	 * 发送请求获取响应
	 *
	 * @param customer 客户编号
	 * @return 响应实体
	 */
	private Response sendAndReceiveResponse(String customer) {
		String serviceId = JxrcbConstant.ECIF_SERVICE_ID_L02;//服务代码
		LoanCompanyCustomerRequestDTO loanCompanyCustomerRequestDTO = new LoanCompanyCustomerRequestDTO();
		loanCompanyCustomerRequestDTO.setCustomerId(customer);
		/*发送请求，获取响应*/
		return ibmmqService.request(serviceId, loanCompanyCustomerRequestDTO);
	}


}
