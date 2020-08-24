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
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.credit.entity.FundAdmitDetail;
import org.git.modules.clm.credit.entity.FundAdmitMain;
import org.git.modules.clm.credit.service.IFundAdmitDetailService;
import org.git.modules.clm.credit.service.IFundAdmitMainService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdAdmitInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCustomerStateRequestDTO;
import org.git.modules.clm.credit.service.IFundCustomerStateService;
import org.git.modules.clm.loan.entity.*;
import org.git.modules.clm.loan.service.*;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.system.service.IDeptService;
import org.git.core.tool.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * CLM203资金实时-资金客户状态维护
 * @author SHC
 * @since 2019-11-25
 */
@Service
@AllArgsConstructor
@Slf4j
public class FundCustomerStateServiceImpl implements IFundCustomerStateService {
	private IFundAdmitMainService iFundAdmitMainService;
	private IDeptService deptService;
	private ICrdMainService iCrdMainService;
	private ICrdDetailService iCrdDetailService;
	private ICrdApplySerialService iCrdApplySerialService;
	private IFundAdmitDetailService iFundAdmitDetailService;
	/**
	 * 事件落地前的检查
	 * */
	@Override
	public String checkEventStatus(ExtAttributes extAttributes, FundCustomerStateRequestDTO fundCustomerStateRequestDTO){
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 数据重复校验
		 * */
		/*
		//需求变更：不做重复校验。
		FundAdmitMain fundAdmitMain=new FundAdmitMain();
		fundAdmitMain.setTranDate(tranDate);
		fundAdmitMain.setBusiDealNum(fundCustomerStateRequestDTO.getBusiDealNum());
		fundAdmitMain.setTranTypeCd(fundCustomerStateRequestDTO.getTranTypeCd());
		fundAdmitMain.setTranEventStatus(JxrcbBizConstant.event_status_1);
		FundAdmitMain fundAdmitMainRs=iFundAdmitMainService.getOne(Wrappers.query(fundAdmitMain));
		if(fundAdmitMainRs!=null){
			return "1";
		}*/
		return "0";
	}
	/**
	 * 事件落地
	 * */
	@Transactional
	@Override
	public String saveEventStatus(ExtAttributes extAttributes,FundCustomerStateRequestDTO fundCustomerStateRequestDTO){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		String tranTypeCd=fundCustomerStateRequestDTO.getTranTypeCd();
		/**
		 * 事件主表
		 * 事件明细表
		 * */
		FundAdmitMain fundAdmitMain=new FundAdmitMain();
		String EventMainUUID= StringUtil.randomUUID();
		fundAdmitMain.setEventMainId(EventMainUUID);
		fundAdmitMain.setTranSeqSn(tranSeqSn);
		fundAdmitMain.setTranDate(tranDate);
		fundAdmitMain.setBusiDealNum(fundCustomerStateRequestDTO.getBusiDealNum());
		fundAdmitMain.setTranTypeCd(fundCustomerStateRequestDTO.getTranTypeCd());
		fundAdmitMain.setTranEventStatus(JxrcbBizConstant.event_status_0);
		iFundAdmitMainService.save(fundAdmitMain);

		String customerNum=fundCustomerStateRequestDTO.getCustomerNum();
		String orgNum=deptService.selectProvincialCooperative().getId().toString();;
		CrdMain crdMain=new CrdMain();
		crdMain.setCustomerNum(customerNum);
		crdMain.setCrdGrantOrgNum(orgNum);
		crdMain.setCrdMainPrd(fundCustomerStateRequestDTO.getCrdMainPrd());
		CrdMain crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
		List<CrdAdmitInfoRequestDTO> crdAdmitInfo=fundCustomerStateRequestDTO.getCrdAdmitInfo();
		for(CrdAdmitInfoRequestDTO crdAdmitInfoRequestDTO:crdAdmitInfo){
			FundAdmitDetail fundAdmitDetail=new FundAdmitDetail();
			fundAdmitDetail.setEventMainId(EventMainUUID);
			BeanUtils.copyProperties(crdAdmitInfoRequestDTO, fundAdmitDetail);
			BeanUtils.copyProperties(fundCustomerStateRequestDTO, fundAdmitDetail);
			if(crdMainRs!=null){
				fundAdmitDetail.setCrdStatus(crdMainRs.getCreditStatus());
			}
			fundAdmitDetail.setTranSeqSn(tranSeqSn);
			fundAdmitDetail.setTranDate(tranDate);
			fundAdmitDetail.setCrdGrantOrgNum(orgNum);
			iFundAdmitDetailService.save(fundAdmitDetail);
		}
		return EventMainUUID;
	}
	/***
	 * 客户管控检查
	 */
	@Override
	public String control(FundCustomerStateRequestDTO fundCustomerStateRequestDTO){
		/**
		 * 获取报文数据：
		 * 交易状态；ecif客户编号;省联社机构号;额度状态;数组数据
		 * */
		String tranTypeCd=fundCustomerStateRequestDTO.getTranTypeCd();
		String customerNum=fundCustomerStateRequestDTO.getCustomerNum();
		String orgNum=deptService.selectProvincialCooperative().getId().toString();;
		String crdStatus=fundCustomerStateRequestDTO.getCrdStatus();
		List<CrdAdmitInfoRequestDTO> crdAdmitInfo=fundCustomerStateRequestDTO.getCrdAdmitInfo();
		if(JxrcbBizConstant.TRAN_TYPE_CD_05.equals(tranTypeCd)){
			/**
			 * 05 客户额度冻结解冻
			 * */
			CrdMain crdMain=new CrdMain();
			crdMain.setCustomerNum(customerNum);
			crdMain.setCrdGrantOrgNum(orgNum);
			crdMain.setCrdMainPrd(fundCustomerStateRequestDTO.getCrdMainPrd());
			CrdMain crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
			String crdStatusRs=crdMainRs.getCreditStatus();
			if(JxrcbBizConstant.CRD_STATUS_03.equals(crdStatus)&&!JxrcbBizConstant.CRD_STATUS_01.equals(crdStatusRs)){
				return "额度状态不是01（正常），冻结失败";
			}
			if(JxrcbBizConstant.CRD_STATUS_01.equals(crdStatus)&&!JxrcbBizConstant.CRD_STATUS_03.equals(crdStatusRs)){
				return "额度状态不是03（冻结），解冻失败";
			}
		}else{
			/**
			 * 06 客户准入调整
			 * */
			for(CrdAdmitInfoRequestDTO crdAdmitInfoRequestDTO:crdAdmitInfo){
				String crdAdmitFlag=crdAdmitInfoRequestDTO.getCrdAdmitFlag();
				CrdDetail crdDetail=new CrdDetail();
				crdDetail.setCustomerNum(customerNum);
				crdDetail.setCrdGrantOrgNum(orgNum);
				crdDetail.setCrdDetailPrd(crdAdmitInfoRequestDTO.getCrdDetailPrd());
				CrdDetail crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));
				if(crdDetailRs==null){
					continue;//如果未授信，则在准入状态调整时直接返回成功
				}
				String crdAdmitFlagRs=crdDetailRs.getCrdAdmitFlag();
				if(JxrcbBizConstant.CRD_ADMIT_FLAG_0.equals(crdAdmitFlag)&&!JxrcbBizConstant.CRD_ADMIT_FLAG_1.equals(crdAdmitFlagRs)){
					return "准入状态不是准入，无法改为禁止";
				}
				if(JxrcbBizConstant.CRD_ADMIT_FLAG_1.equals(crdAdmitFlag)&&!JxrcbBizConstant.CRD_ADMIT_FLAG_0.equals(crdAdmitFlagRs)){
					return "准入状态不是禁止，无法改为准入";
				}
			}

		}
		return "0";
	}
	/**
	 * 本地处理范围
	 * */
	@Transactional
	@Override
	public void localHandle(ExtAttributes extAttributes,FundCustomerStateRequestDTO fundCustomerStateRequestDTO,String EventMainUUID){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 获取报文数据：
		 * 交易状态；ecif客户编号;省联社机构号;额度状态;数组数据
		 * */
		String tranTypeCd=fundCustomerStateRequestDTO.getTranTypeCd();
		String customerNum=fundCustomerStateRequestDTO.getCustomerNum();
		String orgNum=deptService.selectProvincialCooperative().getId().toString();;
		String crdStatus=fundCustomerStateRequestDTO.getCrdStatus();
		List<CrdAdmitInfoRequestDTO> crdAdmitInfo=fundCustomerStateRequestDTO.getCrdAdmitInfo();
		if(JxrcbBizConstant.TRAN_TYPE_CD_05.equals(tranTypeCd)){
			/**
			 * 05 客户额度冻结解冻
			 * */
			CrdMain crdMain=new CrdMain();
			crdMain.setCustomerNum(customerNum);
			crdMain.setCrdGrantOrgNum(orgNum);
			crdMain.setCrdMainPrd(fundCustomerStateRequestDTO.getCrdMainPrd());
			CrdMain crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
			crdMainRs.setCreditStatus(crdStatus);
			iCrdMainService.updateById(crdMainRs);
			/**2.流水插入*/
			CrdApplySerial crdApplySerial=new CrdApplySerial();
			crdApplySerial.setTranSeqSn(tranSeqSn);
			crdApplySerial.setTranDate(tranDate);
			BeanUtils.copyProperties(fundCustomerStateRequestDTO, crdApplySerial);
			BeanUtils.copyProperties(crdMainRs, crdApplySerial);
			crdApplySerial.setCrdGrantOrgNum(orgNum);
			crdApplySerial.setCreateTime(CommonUtil.getWorkDateTime());
			crdApplySerial.setUpdateTime(CommonUtil.getWorkDateTime());
			iCrdApplySerialService.save(crdApplySerial);
		}else{
			/**
			 * 06 客户准入调整
			 * */
			for(CrdAdmitInfoRequestDTO crdAdmitInfoRequestDTO:crdAdmitInfo){
				/**1.准入状态修改*/
				String crdAdmitFlag=crdAdmitInfoRequestDTO.getCrdAdmitFlag();
				CrdDetail crdDetail=new CrdDetail();
				crdDetail.setCustomerNum(customerNum);
				crdDetail.setCrdGrantOrgNum(orgNum);
				crdDetail.setCrdDetailPrd(crdAdmitInfoRequestDTO.getCrdDetailPrd());
				CrdDetail crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));
				if(crdDetailRs==null){
					continue;//如果未授信，则在准入状态调整时直接返回成功
				}
				crdDetailRs.setCrdAdmitFlag(crdAdmitFlag);
				iCrdDetailService.updateById(crdDetailRs);
				/**2.流水插入*/
				CrdApplySerial crdApplySerial=new CrdApplySerial();
				crdApplySerial.setTranSeqSn(tranSeqSn);
				crdApplySerial.setTranDate(tranDate);
				BeanUtils.copyProperties(fundCustomerStateRequestDTO, crdApplySerial);
				BeanUtils.copyProperties(crdAdmitInfoRequestDTO, crdApplySerial);
				BeanUtils.copyProperties(crdDetailRs, crdApplySerial);
				crdApplySerial.setCrdGrantOrgNum(orgNum);
				crdApplySerial.setCreateTime(CommonUtil.getWorkDateTime());
				crdApplySerial.setUpdateTime(CommonUtil.getWorkDateTime());
				iCrdApplySerialService.save(crdApplySerial);
			}
		}
		updateEventStatus(EventMainUUID,JxrcbBizConstant.event_status_1);
	}
	/**
	 * 更新事件表
	 * */
	@Override
	public void updateEventStatus(String EventMainUUID,String eventStatus){
		FundAdmitMain fundAdmitMain=new FundAdmitMain();
		fundAdmitMain.setEventMainId(EventMainUUID);
		fundAdmitMain.setTranEventStatus(eventStatus);
		iFundAdmitMainService.updateById(fundAdmitMain);
	}

}
