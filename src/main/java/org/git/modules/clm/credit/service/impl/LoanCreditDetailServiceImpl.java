/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: 高伟达武汉事业部
 */
package org.git.modules.clm.credit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.modules.clm.credit.service.IBankCreditDetailService;
import org.git.modules.clm.credit.service.ILoanCreditDetailService;
import org.git.modules.clm.customer.entity.CsmCorporation;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.bank.*;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  S00870000259004 票据额度查询
 *  刘烨
 */
@Service
@AllArgsConstructor
@Slf4j
public class LoanCreditDetailServiceImpl implements ILoanCreditDetailService {
	private ICrdMainService iCrdMainService;
	private IDeptService deptService;
	private ICsmCorporationService iCsmCorporationService;
	private ICrdService crdService;

	@Override
	public LoanCreditQueryResponseDTO getResponse(LoanCreditDetailRequestDTO loanCreditDetailRequestDTO){
		LoanCreditQueryResponseDTO loanCreditQueryResponseDTO = new LoanCreditQueryResponseDTO();
		String crdCustKind=loanCreditDetailRequestDTO.getCrdCustKind();
		String ecifCustNum="";
		if("01".equals(crdCustKind)){//ecif客户号
			ecifCustNum=loanCreditDetailRequestDTO.getCustomerNum();
		}else if("02".equals(crdCustKind)){//02 清算行号
			ecifCustNum=iCsmCorporationService.getCustomerNum(loanCreditDetailRequestDTO.getCustomerNum());
			if("-1".equals(ecifCustNum)){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F00301,"获取ECIF客户信息失败！");
			}
		}else{//,03 统一信用代码
			CsmCorporation csmCorporation=iCsmCorporationService.creditOrganCodeGetCustomerNum(loanCreditDetailRequestDTO.getCustomerNum());
			if(csmCorporation==null){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F00301,"获取ECIF客户信息失败！");
			}
			ecifCustNum=csmCorporation.getCustomerNum();
		}
		loanCreditQueryResponseDTO.setEcifCustNum(ecifCustNum);
		loanCreditQueryResponseDTO.setCrdDetailPrd(loanCreditDetailRequestDTO.getCrdDetailPrd());
		/**
		 * 获取二级额度产品
		 */
		Crd crd = crdService.getById(loanCreditDetailRequestDTO.getCrdDetailPrd());
		if(crd==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F00303,"获取产品"+loanCreditDetailRequestDTO.getCrdDetailPrd()+"信息失败！");
		}
		/**通过机构号获取省联社机构号*/

		String crdGrantOrgNum=loanCreditDetailRequestDTO.getCrdGrantOrgNum();

		Dept dept=deptService.selectProvincialCooperative();
		if(dept==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F00302,"获取省联社机构失败！");
		}
		CrdMain param=new CrdMain();
		param.setCustomerNum(ecifCustNum);
		param.setCrdMainPrd(crd.getSuperCrdNum());
		//查看二级额度组
		List<CrdMain>  crdMains=iCrdMainService.list(Wrappers.query(param));
		List<CrdMainInfoResponseDTO> crdMainInfoResponseDTOS = new ArrayList<>();
		if(crdMains!=null){
			for (CrdMain crdMain:crdMains) {
				crdMainInfoResponseDTOS.add(crdMainToResponseDTO(crdMain));

			}
			loanCreditQueryResponseDTO.setCrdMainInfos(crdMainInfoResponseDTOS);
		}
		param.setCrdGrantOrgNum(crdGrantOrgNum);
		//当看当前机构二级额度
		CrdMain thisCrdMain=iCrdMainService.getOne(Wrappers.query(param));
		if(thisCrdMain!=null){
			loanCreditQueryResponseDTO.setThisCrdMain(crdMainToResponseDTO(thisCrdMain));
		}
		String SLSorgNum= dept.getId();
		param.setCrdGrantOrgNum(SLSorgNum);
		//查看省联社二级额度
		CrdMain provideCrdMain=iCrdMainService.getOne(Wrappers.query(param));
		if(provideCrdMain!=null) {
			loanCreditQueryResponseDTO.setProvinceCrdMain(crdMainToResponseDTO(provideCrdMain));
		}
		return loanCreditQueryResponseDTO;
	}


	/**
	 * 获取额度主表数据，set到返回对象
	 * */
	public CrdMainInfoResponseDTO crdMainToResponseDTO(CrdMain crdMain){
		CrdMainInfoResponseDTO crdMainInfoResponseDTO=new CrdMainInfoResponseDTO();
		BeanUtils.copyProperties(crdMain, crdMainInfoResponseDTO);
		crdMainInfoResponseDTO.setLimitCredit(crdMain.getLimitCredit()==null?null:crdMain.getLimitCredit().toString());
		crdMainInfoResponseDTO.setLimitUsed(crdMain.getLimitUsed()==null?null:crdMain.getLimitUsed().toString());
		crdMainInfoResponseDTO.setLimitAvi(crdMain.getLimitAvi()==null?null:crdMain.getLimitAvi().toString());
		crdMainInfoResponseDTO.setExpCredit(crdMain.getExpCredit()==null?null:crdMain.getExpCredit().toString());
		crdMainInfoResponseDTO.setExpUsed(crdMain.getExpUsed()==null?null:crdMain.getExpUsed().toString());
		crdMainInfoResponseDTO.setExpAvi(crdMain.getExpAvi()==null?null:crdMain.getExpAvi().toString());
		crdMainInfoResponseDTO.setExpPre(crdMain.getExpPre()==null?null:crdMain.getExpPre().toString());
		crdMainInfoResponseDTO.setLimitFrozen(crdMain.getLimitFrozen()==null?null:crdMain.getLimitFrozen().toString());//冻结额度
		crdMainInfoResponseDTO.setExpFrozen(crdMain.getExpFrozen()==null?null:crdMain.getExpFrozen().toString());//冻结敞口
		crdMainInfoResponseDTO.setLimitEarmark(crdMain.getLimitEarmark()==null?"0":crdMain.getLimitEarmark().toString());
		crdMainInfoResponseDTO.setLimitEarmarkUsed(crdMain.getLimitEarmarkUsed()==null?"0":crdMain.getLimitEarmarkUsed().toString());
		//资金需求：圈存null转0
		return crdMainInfoResponseDTO;
	}



}
