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
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.front.dto.jxrcb.bank.*;
import org.git.modules.clm.credit.service.IBankCreditDetailService;
import org.git.modules.clm.loan.entity.*;
import org.git.modules.clm.loan.service.*;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  S00870000259003同业实时-明细额度详细信息查询
 * SHC
 */
@Service
@AllArgsConstructor
@Slf4j
public class BankCreditDetailImpl implements IBankCreditDetailService {
	private ICrdDetailService iCrdDetailService;
	private ICrdMainService iCrdMainService;
	private IDeptService deptService;
	private ICsmCorporationService iCsmCorporationService;
	private ICrdService crdService;

	@Override
	public BankCreditDetailResponseDTO getResponse(BankCreditDetailRequestDTO bankCreditDetailRequestDTO){
		BankCreditDetailResponseDTO bankCreditDetailResponseDTO=new BankCreditDetailResponseDTO();

		String crdCustKind=bankCreditDetailRequestDTO.getCrdCustKind();
		String ecifCustNum="";
		if("01".equals(crdCustKind)){//ecif客户号
			ecifCustNum=bankCreditDetailRequestDTO.getCustomerNum();
		}else if("02".equals(crdCustKind)){//02 清算行号
			ecifCustNum=iCsmCorporationService.getCustomerNum(bankCreditDetailRequestDTO.getCustomerNum());
			if("-1".equals(ecifCustNum)){
				return null;
			}
		}else{//,03 统一信用代码
			ecifCustNum=iCsmCorporationService.creditOrganCodeGetCustomerNum(bankCreditDetailRequestDTO.getCustomerNum())
				.getCustomerNum();
		}
		bankCreditDetailResponseDTO.setEcifCustNum(ecifCustNum);
		bankCreditDetailResponseDTO.setCrdDetailPrd(bankCreditDetailRequestDTO.getCrdDetailPrd());
		/**通过机构号获取省联社机构号*/
		String crdGrantOrgNum=bankCreditDetailRequestDTO.getCrdOrgInfo().get(0).getCrdGrantOrgNum();
		String SLSorgNum=deptService.selectProvincialCooperative().getId().toString();

		/**根据ecif客户编号，额度三级产品号，币种。机构编号。查询额度明细数据
		 * 根据ecif客户编号，机构编号查额度主表数据
		 * 1.省联社机构号
		 * */
		CrdDetail crdDetail=new CrdDetail();
		crdDetail.setCustomerNum(ecifCustNum);
		crdDetail.setCrdDetailPrd(bankCreditDetailRequestDTO.getCrdDetailPrd());
		crdDetail.setCurrencyCd(bankCreditDetailRequestDTO.getCurrencyCd());
		crdDetail.setCrdGrantOrgNum(SLSorgNum);
		CrdDetail crdDetailRes=iCrdDetailService.getOne(Wrappers.query(crdDetail));

		CrdMain crdMain=new CrdMain();
		crdMain.setCustomerNum(ecifCustNum);
		crdMain.setCrdGrantOrgNum(SLSorgNum);
		Crd crd = crdService.getById(bankCreditDetailRequestDTO.getCrdDetailPrd());
		crdMain.setCrdMainPrd(crd==null?null:crd.getSuperCrdNum());
		CrdMain crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
		if(crdMainRs!=null) {
			bankCreditDetailResponseDTO.setProvinceCrdMain(crdMainToResponseDTO(crdMainRs));
		}
		if(crdDetailRes!=null) {
			bankCreditDetailResponseDTO.setProvinceCrdDetail(crdDetailToResponseDTO(crdDetailRes));
		}


		/**根据ecif客户编号，额度三级产品号，币种。机构编号。查询额度明细数据
		 * 根据ecif客户编号，机构编号查额度主表数据
		 * 2.成员行机构号
		 * */
		List<CrdDetailInfoResponseDTO> crdDetailInfo=new ArrayList<CrdDetailInfoResponseDTO>();
		List<CrdMainInfoResponseDTO> crdMainInfo=new ArrayList<CrdMainInfoResponseDTO>();
		for(CrdOrgInfoRequestDTO crdOrgInfo:bankCreditDetailRequestDTO.getCrdOrgInfo()){
			crdGrantOrgNum=crdOrgInfo.getCrdGrantOrgNum();
			crdDetail.setCrdGrantOrgNum(crdGrantOrgNum);
			crdDetailRes=iCrdDetailService.getOne(Wrappers.query(crdDetail));

			crdMain=new CrdMain();
			crdMain.setCustomerNum(ecifCustNum);
			crdMain.setCrdGrantOrgNum(crdGrantOrgNum);
			crdMain.setCrdMainPrd(crd==null?null:crd.getSuperCrdNum());
			crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
			if(crdMainRs!=null) {
				crdMainInfo.add(crdMainToResponseDTO(crdMainRs));
			}
			if(crdDetailRes!=null) {
				crdDetailInfo.add(crdDetailToResponseDTO(crdDetailRes));
			}
		}
		bankCreditDetailResponseDTO.setCrdMainInfo(crdMainInfo);
		bankCreditDetailResponseDTO.setCrdDetailInfo(crdDetailInfo);
		return bankCreditDetailResponseDTO;
	}

	/**
	 * 获取额度明细数据.set到返回对象CrdDetailInfoResponseDTO
	 * */
	public CrdDetailInfoResponseDTO crdDetailToResponseDTO(CrdDetail crdDetailRes){
		CrdDetailInfoResponseDTO crdDetailInfoResponseDTO=new CrdDetailInfoResponseDTO();
		BeanUtils.copyProperties(crdDetailRes, crdDetailInfoResponseDTO);
		crdDetailInfoResponseDTO.setLimitCredit(crdDetailRes.getLimitCredit()==null?null:crdDetailRes.getLimitCredit().toString());
		crdDetailInfoResponseDTO.setLimitUsed(crdDetailRes.getLimitUsed()==null?null:crdDetailRes.getLimitUsed().toString());
		crdDetailInfoResponseDTO.setLimitAvi(crdDetailRes.getLimitAvi()==null?null:crdDetailRes.getLimitAvi().toString());
		crdDetailInfoResponseDTO.setExpCredit(crdDetailRes.getExpCredit()==null?null:crdDetailRes.getExpCredit().toString());
		crdDetailInfoResponseDTO.setExpUsed(crdDetailRes.getExpUsed()==null?null:crdDetailRes.getExpUsed().toString());
		crdDetailInfoResponseDTO.setExpAvi(crdDetailRes.getExpAvi()==null?null:crdDetailRes.getExpAvi().toString());
		crdDetailInfoResponseDTO.setLimitPre(crdDetailRes.getLimitPre()==null?null:crdDetailRes.getLimitPre().toString());
		crdDetailInfoResponseDTO.setExpPre(crdDetailRes.getExpPre()==null?null:crdDetailRes.getExpPre().toString());
		crdDetailInfoResponseDTO.setLimitEarmark(crdDetailRes.getLimitEarmark()==null?"0":crdDetailRes.getLimitEarmark().toString());
		crdDetailInfoResponseDTO.setLimitEarmarkUsed(crdDetailRes.getLimitEarmarkUsed()==null?"0":crdDetailRes.getLimitEarmarkUsed().toString());
		crdDetailInfoResponseDTO.setLimitFrozen(crdDetailRes.getLimitFrozen()==null?null:crdDetailRes.getLimitFrozen().toString());//冻结额度
		crdDetailInfoResponseDTO.setExpFrozen(crdDetailRes.getExpFrozen()==null?null:crdDetailRes.getExpFrozen().toString());//冻结敞口
		crdDetailInfoResponseDTO.setMixCredit(crdDetailRes.getMixCredit()==null?null:crdDetailRes.getMixCredit().toString());
		crdDetailInfoResponseDTO.setMixUsed(crdDetailRes.getMixUsed()==null?null:crdDetailRes.getMixUsed().toString());
		return crdDetailInfoResponseDTO;
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
	/**String转BigDecimal*/
	public  BigDecimal turnBigDecimal(String strData) {
		if(strData==null||"".equals(strData)){
			return null;
		}
		BigDecimal b = new BigDecimal(strData);
		b=b.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
		return b;
	}
	/**BigDecimal转Double*/
	public Double getDouble(BigDecimal b){
		if(b==null){
			return 0.00;
		}
		return b.doubleValue();
	}


}
