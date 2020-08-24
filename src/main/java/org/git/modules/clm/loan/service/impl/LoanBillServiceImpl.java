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
package org.git.modules.clm.loan.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.CertInfoRequestDTO;
import org.git.modules.clm.loan.service.ILoanBillService;
import org.git.modules.clm.loan.entity.*;
import org.git.modules.clm.loan.service.*;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.system.service.IDeptService;
import org.git.core.tool.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CLM302票据贴现，再贴，转贴
 * @author SHC
 * @since 2019-11-25
 */
@Service
@AllArgsConstructor
@Slf4j
public class LoanBillServiceImpl implements ILoanBillService {
	private IBillEventMainService iBillEventMainServicei;
	private IBillEventDetailService iBillEventDetailService;
	private ICrdApplySerialService iCrdApplySerialService;
	private ICrdBusiCertInfoService iCrdBusiCertInfoService;
	private ICrdDetailService iCrdDetailService;
	private ICrdMainService iCrdMainService;
	private ICrdProductService iCrdProductService;
	private IDeptService deptService;
	private ICsmCorporationService iCsmCorporationService;


	/**插入事件主表和明细表数据数据*/
	@Transactional
	@Override
	public String insertEvent(ExtAttributes extAttributes, BillDiscountRequestDTO billDiscountReqDTO){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 获取用信金额，用于第一步落地前的金额校验
		 * */
		Double crdApplyAmt=billDiscountReqDTO.getCrdApplyAmt().doubleValue();
		/**
		 * 事件主表数据获取
		 * */
		BillEventMain billEventMain=new BillEventMain();
		String EventMainUUID= StringUtil.randomUUID();
		billEventMain.setEventMainId(EventMainUUID);
		billEventMain.setTranSeqSn(tranSeqSn);
		billEventMain.setTranDate(tranDate);
		billEventMain.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		billEventMain.setTranTypeCd(billDiscountReqDTO.getTranTypeCd());
		billEventMain.setCrdApplyAmt(billDiscountReqDTO.getCrdApplyAmt());
		billEventMain.setTranEventStatus(JxrcbBizConstant.event_status_0);
		billEventMain.setTranAcctStatus("0");
		/**事件明细表数据获取*/
		BillEventDetail billEventDetail=new BillEventDetail();
		billEventDetail.setTranSeqSn(tranSeqSn);
		billEventDetail.setTranDate(tranDate);
		billEventDetail.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		billEventDetail.setBusiPrdNum(billDiscountReqDTO.getBusiPrdNum());
		billEventDetail.setBusiDealDesc(billDiscountReqDTO.getBusiDealDesc());
		billEventDetail.setBusiDealOrgNum(billDiscountReqDTO.getBusiDealOrgNum());
		billEventDetail.setBusiDealOrgName(billDiscountReqDTO.getBusiDealOrgName());
		billEventDetail.setBusiOpptOrgNum(billDiscountReqDTO.getBusiOpptOrgNum());
		billEventDetail.setBusiOpptOrgName(billDiscountReqDTO.getBusiOpptOrgName());
		billEventDetail.setBusiSumAmt(billDiscountReqDTO.getBusiSumAmt());
		billEventDetail.setBusiCertCnt(billDiscountReqDTO.getBusiCertCnt());
		/**
		 * 获取数组并循环
		 * */
		List<CertInfoRequestDTO> certInfo=billDiscountReqDTO.getCertInfo();
		List<BillEventDetail> billEventDetailList=new ArrayList<BillEventDetail>();
		Double certApplyAmtSum=0.00;
		int customerNumStatus=0;
		for(CertInfoRequestDTO certInfoDTO:certInfo){
			if("0".equals(certInfoDTO.getCertIsMyBank())){
				String customerNum=iCsmCorporationService.getCustomerNum(certInfoDTO.getCertDrawerBankNum());
				if("-1".equals(customerNum)){
					/**当有人行清算行号找不到客户编号时，事件落地后就结束所有操作*/
					customerNumStatus=1;
				}
				billEventDetail.setCertDrawerBankLegal(customerNum);
			}

			/**
			 * 凭证用信金额合算，用于比较
			 * */
			certApplyAmtSum+=getDouble(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
			billEventDetail.setCertIsMyBank(certInfoDTO.getCertIsMyBank());
			billEventDetail.setEventDetailedId(StringUtil.randomUUID());
			billEventDetail.setCertNum(certInfoDTO.getCertNum());
			billEventDetail.setCertTypeCd(certInfoDTO.getCertTypeCd());
			billEventDetail.setCertPptCd(certInfoDTO.getCertPptCd());
			billEventDetail.setCertInterestPeriod(certInfoDTO.getCertInterestPeriod());
			billEventDetail.setCertInterestRate(CommonUtil.stringToBigDecimal(certInfoDTO.getCertInterestRate()));
			billEventDetail.setCertCurrencyCd(certInfoDTO.getCertCurrencyCd());
			billEventDetail.setCertSeqAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()));
			billEventDetail.setCertApplyAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
			billEventDetail.setCertApplyBalance(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyBalance()));
			billEventDetail.setCertStatus(certInfoDTO.getCertStatus());
			billEventDetail.setCertBeginDate(certInfoDTO.getCertBeginDate());
			billEventDetail.setCertEndDate(certInfoDTO.getCertEndDate());
			billEventDetail.setCertFinishDate(certInfoDTO.getCertFinishDate());
			billEventDetail.setCertDrawerCustNum(certInfoDTO.getCertDrawerCustNum());
			billEventDetail.setCertDrawerName(certInfoDTO.getCertDrawerName());
			billEventDetail.setCertDrawerBankNum(certInfoDTO.getCertDrawerBankNum());
			billEventDetail.setCertGuarantyType(certInfoDTO.getCertGuarantyType());
			billEventDetail.setCertGuarantyPerson(certInfoDTO.getCertGuarantyPerson());
			billEventDetail.setCertBusiRemark(certInfoDTO.getCertBusiRemark());
			billEventDetail.setEventDetailedId(StringUtil.randomUUID());
			iBillEventDetailService.save(billEventDetail);
			billEventDetailList.add(billEventDetail);
		}
		iBillEventMainServicei.save(billEventMain);
		//iBillEventDetailService.saveBatch(billEventDetailList);
		/**校验金额是否相等。不等，直接回滚*/
		if(!crdApplyAmt.equals(certApplyAmtSum)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "0";
		}

		/**事件表校验重复
		 * 交易日期、业务编号、交易类型、币种，金额
		 * 暂时放开（本方机构、对手机构、业务产品，）
		 * */
		BillEventMain billEventMain1=new BillEventMain();
		billEventMain1.setTranDate(tranDate);
		billEventMain1.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		billEventMain1.setTranTypeCd(billDiscountReqDTO.getTranTypeCd());
		billEventMain1.setCrdApplyAmt(billDiscountReqDTO.getCrdApplyAmt());
		List<Object> list=iBillEventMainServicei.listObjs(Wrappers.query(billEventMain1));
		if(list.size()>0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "-1";
		}
		if(customerNumStatus==1){
			return "-2";
		}
		return EventMainUUID;
	}

	/**
	 *额度拆分，本地更新处理
	 * */
	@Transactional
	@Override
	public String crdSplitUp(ExtAttributes extAttributes,BillDiscountRequestDTO billDiscountReqDTO,BillDiscountResponseDTO billDiscountResDTO){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 先初始化公用变量
		 * 流水表：
		 * 		授信机构；用信客户；额度产品编号；额度明细编号；
		 * 		用信金额
		 * 业务凭证：
		 * */
		String crdGrantOrgNum="", customerNum="", crdProductNum="", crdDetailNum="";
		Double crdApplyAmt=0.00;

		/**
		 * 获取报文数据
		 *	业务产品编号;交易类型;对手机构;本方机构;流水号
		 */
		String busiPrdNum=billDiscountReqDTO.getBusiPrdNum();
		String tranTypeCd=billDiscountReqDTO.getTranTypeCd();
		String busiOpptOrgNum=billDiscountReqDTO.getBusiOpptOrgNum();
		crdGrantOrgNum=billDiscountReqDTO.getBusiDealOrgNum();

		/**根据"业务品种"查询额度产品编码(同业),所有情况公用*/
		CrdProduct crdProductParam=new CrdProduct();
		crdProductParam.setProductNum(busiPrdNum);
		crdProductParam.setCustType("02");
		crdProductNum=iCrdProductService.selectCrd(crdProductParam).getCrdProductNum();

		/**初始化用信流水表和凭证表*/
		CrdApplySerial crdApplySerial=new CrdApplySerial();
		BeanUtils.copyProperties(billDiscountReqDTO, crdApplySerial);
		crdApplySerial.setTranSeqSn(tranSeqSn);
		crdApplySerial.setTranDate(tranDate);
		crdApplySerial.setCrdGrantOrgNum(billDiscountReqDTO.getBusiDealOrgNum());
		crdApplySerial.setCrdDetailPrd(crdProductNum);
		CrdBusiCertInfo crdBusiCertInfo=new CrdBusiCertInfo();
		BeanUtils.copyProperties(billDiscountReqDTO, crdBusiCertInfo);
		crdBusiCertInfo.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());

		Map crdupdate=new HashMap();
		/**
		 * 校验并插入
		 * 一，拆分登记直贴企业占信
		 * */
		/**到期；*/
		CrdDetail crdDetail=new CrdDetail();
		if(JxrcbBizConstant.BUSI_PRD_NUM_5.equals(busiPrdNum)){
			//TODO 203 ：5到期，此情况暂不处理
			/**到期，此情况暂不处理*/
			return "0";
		}
		if(JxrcbBizConstant.BUSI_PRD_NUM_1.equals(busiPrdNum)&&(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd))){
			crdApplyAmt=getDouble(billDiscountReqDTO.getCrdApplyAmt());
			BigDecimal isNegative=billDiscountReqDTO.getCrdApplyAmt();
			if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd)||
				JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd)
			){
				isNegative=new BigDecimal("-"+crdApplyAmt.toString());
			}
			crdApplySerial.setLimitCreditAmt(isNegative);
			customerNum=busiOpptOrgNum;
			crdApplySerial.setCustomerNum(customerNum);

			/**
			 * 用于额度更新本地处理
			 * */
			crdUpdateMap=new ArrayList<>();
			crdupdate.put("crdGrantOrgNum",crdGrantOrgNum);
			crdupdate.put("customerNum",customerNum);
			crdupdate.put("isNegative",isNegative);
			crdupdate.put("crdProductNum",crdProductNum);
			crdUpdateMap.add(crdupdate);

			/**查询额度明细编号;客户准入状态（需要分情况：“customerNum用信客户”会不同）*/
			crdDetail=iCrdDetailService.findCrdDetail(crdGrantOrgNum,customerNum,crdProductNum);
			crdDetailNum=crdDetail.getCrdDetailNum();
			crdApplySerial.setCrdDetailNum(crdDetailNum);
			crdBusiCertInfo.setCrdDetailNum(crdDetailNum);
			/**
			 * 根据额度主表获取额度生效日;额度到期日（需要分情况：“customerNum用信客户”会不同）
			 * */
			crdApplySerial.setSerialId(null);
			iCrdApplySerialService.save(crdApplySerial);
			/**
			 * 获取数组并循环，set到凭证信息对象集合中
			 * */
			List<CertInfoRequestDTO> certInfo=billDiscountReqDTO.getCertInfo();
			for(CertInfoRequestDTO certInfoDTO:certInfo){
				BeanUtils.copyProperties(certInfoDTO, crdBusiCertInfo);
				crdBusiCertInfo.setCertNum(certInfoDTO.getCertNum());
				crdBusiCertInfo.setCertSeqAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()));
				crdBusiCertInfo.setCertApplyAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
				crdBusiCertInfo.setCertApplyBalance(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()).subtract(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt())));

				String zt=inspectState(tranTypeCd,crdDetailNum,billDiscountReqDTO,certInfoDTO);
				if("0".equals(zt)){
					log.debug("检查通过，插入操作！");
					crdBusiCertInfo.setCretInfoId(StringUtil.randomUUID());
					iCrdBusiCertInfoService.save(crdBusiCertInfo);
				}else if("-1".equals(zt)){
					log.error("没有找到“业务凭证信息”数据，检查失败，无法操作！");
				}else if("-2".equals(zt)){
					log.error("暂用时凭证状态存在且不是“无效（00），检查不通过！”");
				}else if("-3".equals(zt)){
					log.error("占用撤销时不是03占用状态，检查不通过！");
				}else{
					log.debug("检查通过，更新操作！");
					crdBusiCertInfo.setCretInfoId(zt);
					CrdBusiCertInfo crdBusiCertInfo1=iCrdBusiCertInfoService.getById(zt);
					crdBusiCertInfo.setCertApplyAmt(crdBusiCertInfo1.getCertApplyAmt().add(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt())));
					crdBusiCertInfo.setCertApplyBalance(crdBusiCertInfo1.getCertApplyBalance().add(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyBalance())));
					iCrdBusiCertInfoService.updateById(crdBusiCertInfo);
				}

			}
		}

		/**
		 *二，拆分登记票据中的承兑行占信1/n
		 * */
		List<Map> GroupCertApplyAmt = iBillEventDetailService.selectGroupCertApplyAmt(tranSeqSn);
		List<CrdApplySerial> crdApplySerialList=new ArrayList<CrdApplySerial>();
		/**
		 * 额度数据(额度明细编号,额度生效日;额度到期日)map
		 * 行号和额度数据关系map
		 * */
		Map<String,Object> mapBankCrd=new HashMap<String,Object>();
		Map<String,Map> mapCrd=new HashMap<String,Map>();
		for (Map map : GroupCertApplyAmt) {
			String certDrawerCustLegal = (String) map.get("CERTDRAWERBANKLEGAL");
			BigDecimal sumCertApplyAmt = (BigDecimal) map.get("SUMCERTAPPLYAMT");
			if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd)){
				sumCertApplyAmt=new BigDecimal(-getDouble(sumCertApplyAmt));
			}
			crdApplySerial.setLimitCreditAmt(sumCertApplyAmt);
			customerNum = certDrawerCustLegal;
			crdApplySerial.setCustomerNum(customerNum);
			/**
			 * 查询额度明细编号;客户准入状态（需要分情况：“customerNum用信客户”会不同）
			 * */
			crdDetail = iCrdDetailService.findCrdDetail(crdGrantOrgNum, customerNum, crdProductNum);
			crdDetailNum = crdDetail.getCrdDetailNum();
			mapBankCrd.put("crdDetailNum",crdDetailNum);
			crdApplySerial.setCrdDetailNum(crdDetailNum);
			mapCrd.put(certDrawerCustLegal,mapBankCrd);
			crdApplySerial.setSerialId(null);
			crdApplySerialList.add(crdApplySerial);
			//iCrdApplySerialService.save(crdApplySerial);

			/**
			 * 用于额度更新本地处理
			 * */
			crdupdate.put("crdGrantOrgNum",crdGrantOrgNum);
			crdupdate.put("customerNum",customerNum);
			crdupdate.put("isNegative",sumCertApplyAmt);
			crdupdate.put("crdProductNum",crdProductNum);
			crdUpdateMap.add(crdupdate);
		}
		iCrdApplySerialService.saveBatch(crdApplySerialList);

		/**
		 * 获取数组并循环，set到凭证信息对象集合中
		 * */
		List<CertInfoRequestDTO> certInfo=billDiscountReqDTO.getCertInfo();
		List<BillEventDetail> billEventDetailList=new ArrayList<BillEventDetail>();
		List<CrdBusiCertInfo> crdBusiCertInfoList2=new ArrayList<CrdBusiCertInfo>();
		for(CertInfoRequestDTO certInfoDTO:certInfo){
			BeanUtils.copyProperties(certInfoDTO, crdBusiCertInfo);
			crdBusiCertInfo.setCertNum(certInfoDTO.getCertNum());
			crdBusiCertInfo.setCertSeqAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()));
			crdBusiCertInfo.setCertApplyAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
			crdBusiCertInfo.setCertApplyBalance(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()).subtract(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt())));
			crdBusiCertInfo.setCrdDetailNum((String)mapCrd.get(certInfoDTO.getCertDrawerBankNum()).get("crdDetailNum"));

			String zt=inspectState(tranTypeCd,crdDetailNum,billDiscountReqDTO,certInfoDTO);
			if("0".equals(zt)){
				log.debug("检查通过，插入操作！");
				crdBusiCertInfo.setCretInfoId(StringUtil.randomUUID());
				iCrdBusiCertInfoService.save(crdBusiCertInfo);
			}else if("-1".equals(zt)){
				log.error("没有找到“业务凭证信息”数据，检查失败，无法操作！");
			}else if("-2".equals(zt)){
				log.error("暂用时凭证状态存在且不是“无效（00），检查不通过！”");
			}else if("-3".equals(zt)){
				log.error("占用撤销时不是03占用状态，检查不通过！");
			}else{
				log.debug("检查通过，更新操作！");
				crdBusiCertInfo.setCretInfoId(zt);
				CrdBusiCertInfo crdBusiCertInfo1=iCrdBusiCertInfoService.getById(zt);
				crdBusiCertInfo.setCertApplyAmt(crdBusiCertInfo1.getCertApplyAmt().add(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt())));
				crdBusiCertInfo.setCertApplyBalance(crdBusiCertInfo1.getCertApplyBalance().add(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyBalance())));
				iCrdBusiCertInfoService.updateById(crdBusiCertInfo);
			}

		}
		return "0";
	}

	/**
	 * 用于占用凭证依赖状态检查，额度明细编号，业务编号，凭证编号
	 * */
	public String inspectState(String tranTypeCd,String crdDetailNum,BillDiscountRequestDTO billDiscountReqDTO,CertInfoRequestDTO certInfoDTO){
		CrdBusiCertInfo crdBusiCertInfo1=new CrdBusiCertInfo();
		crdBusiCertInfo1.setCrdDetailNum(crdDetailNum);
		crdBusiCertInfo1.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		crdBusiCertInfo1.setCertNum(certInfoDTO.getCertNum());
		CrdBusiCertInfo CrdBusiCertInfoRes=iCrdBusiCertInfoService.getOne(Wrappers.query(crdBusiCertInfo1));
		if(CrdBusiCertInfoRes==null){
			if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)) {
				log.debug("没查到，检查通过，直接插入！");
				return "0";
			}else{
				log.debug("没有找到“业务凭证信息”数据，检查失败，无法操作！");
				return "-1";
			}
		}
		String certStatus=CrdBusiCertInfoRes.getCertStatus();
		String id=CrdBusiCertInfoRes.getCretInfoId();
		if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd)){
			if("00".equals(certStatus)){
				log.debug("占用时已存在并且是00，校验通过，更新数据！");
				return id;
			}else{
				log.debug("暂用时凭证状态存在且不是“无效（00），检查不通过！”");
				return "-2";
			}
		}
		if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME.equals(tranTypeCd)){
			if(!JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(certStatus)){
				log.debug("占用撤销时不是03占用状态，检查不通过！");
				return "-3";
			}
		}
		return id;
	}

	/**
	 *额度拆分，本地更新处理前的检查
	 * */
	@Override
	public String check(ExtAttributes extAttributes,BillDiscountRequestDTO billDiscountReqDTO,BillDiscountResponseDTO billDiscountResDTO){
		/**
		 * 先初始化公用变量
		 * 流水表：
		 * 		授信机构；用信客户；额度产品编号；额度明细编号；
		 * 		用信金额
		 * 业务凭证：
		 * */
		String crdGrantOrgNum="", customerNum="", crdProductNum="", crdDetailNum="";
		Double crdApplyAmt=0.00;
		/**
		 * 获取报文数据
		 *	业务产品编号;交易类型;对手机构;本方机构;流水号
		 */
		String busiPrdNum=billDiscountReqDTO.getBusiPrdNum();
		String tranTypeCd=billDiscountReqDTO.getTranTypeCd();
		String busiOpptOrgNum=billDiscountReqDTO.getBusiOpptOrgNum();
		crdGrantOrgNum=billDiscountReqDTO.getBusiDealOrgNum();

		/**根据"业务品种"查询额度产品编码(同业),所有情况公用*/
		CrdProduct crdProductParam=new CrdProduct();
		crdProductParam.setProductNum(busiPrdNum);
		crdProductParam.setCustType("02");
		crdProductNum=iCrdProductService.selectCrd(crdProductParam).getCrdProductNum();
		Map crdupdate=new HashMap();
		/**
		 * 校验
		 * 一，拆分登记直贴企业占信
		 * */
		/**到期；*/
		CrdDetail crdDetail=new CrdDetail();
		if(JxrcbBizConstant.BUSI_PRD_NUM_5.equals(busiPrdNum)){
			/**到期，此情况暂不处理*/
			return "0";
		}
		String crdAdmitFlag;String creditStatu;
		if(JxrcbBizConstant.BUSI_PRD_NUM_1.equals(busiPrdNum)&&(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd))){
			customerNum=busiOpptOrgNum;
			/**查询额度明细编号;客户准入状态（需要分情况：“customerNum用信客户”会不同）*/
			crdDetail=iCrdDetailService.findCrdDetail(crdGrantOrgNum,customerNum,crdProductNum);
			crdDetailNum=crdDetail.getCrdDetailNum();
			crdAdmitFlag=crdDetail.getCrdAdmitFlag();
			CrdMain crdMainRs=iCrdMainService.findCrdMain(customerNum,crdGrantOrgNum);
			creditStatu=crdMainRs.getCreditStatus();

			if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)&&"0".equals(crdAdmitFlag)){
				log.error("业务准入检查不通过！");
				return "业务准入检查不通过！额度明细编号为："+crdDetail.getCrdDetailNum();
			}
			if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)&&!"01".equals(creditStatu)){
				log.error("额度状态检查不通过！");
				return "额度状态检查不通过！额度编码为："+crdMainRs.getCrdMainNum();
			}
			/**
			 * 根据额度主表获取额度生效日;额度到期日（需要分情况：“customerNum用信客户”会不同）
			 * */
			Date beginDate=turnDate(crdMainRs.getBeginDate());
			Date endDate=turnDate(crdMainRs.getEndDate());
			/**
			 * 获取数组并循环，set到凭证信息对象集合中
			 * */
			List<CertInfoRequestDTO> certInfo=billDiscountReqDTO.getCertInfo();
			for(CertInfoRequestDTO certInfoDTO:certInfo){
				String zt=inspectState(tranTypeCd,crdDetailNum,billDiscountReqDTO,certInfoDTO);
				if("0".equals(zt)){
					log.debug("检查通过，插入操作！");
				}else if("-1".equals(zt)){
					log.error("没有找到“业务凭证信息”数据，检查失败，无法操作！");
					return "没有找到“业务凭证信息”数据，检查失败，无法操作";
				}else if("-2".equals(zt)){
					log.error("暂用时凭证状态存在且不是“无效（00），检查不通过！”");
					return "暂用时凭证状态存在且不是“无效（00），检查不通过！”";
				}else if("-3".equals(zt)){
					log.error("占用撤销时不是03占用状态，检查不通过！");
					return "占用撤销时不是03占用状态，检查不通过！";
				}else{
					log.debug("检查通过，更新操作！");
				}
				if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)&&!(turnDate(certInfoDTO.getCertBeginDate())).after(beginDate)){
					log.error("凭证起期不在额度生效日之后，检查不通过！");
					return "凭证起期不在额度生效日之后，检查不通过！";
				}
				if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)&&!(turnDate(certInfoDTO.getCertEndDate())).before(endDate)){
					log.error("凭证止期不在额度到期日之前，检查不通过！");
					return "凭证止期不在额度到期日之前，检查不通过！";
				}
			}
		}
		/**
		 *二，检查：登记票据中的承兑行占信1/n
		 * */
		String tranSeqSn=extAttributes.getOriReqSn();
		List<Map> GroupCertApplyAmt = iBillEventDetailService.selectGroupCertApplyAmt(tranSeqSn);
		/**
		 * 额度数据(额度明细编号,额度生效日;额度到期日)map
		 * 行号和额度数据关系map
		 **/
		Map<String,Object> mapBankCrd=new HashMap<String,Object>();
		Map<String,Map> mapCrd=new HashMap<String,Map>();
		for (Map map : GroupCertApplyAmt) {
			String certDrawerCustLegal = (String) map.get("CERTDRAWERBANKLEGAL");
			BigDecimal sumCertApplyAmt = (BigDecimal) map.get("SUMCERTAPPLYAMT");
			customerNum = certDrawerCustLegal;
			/**
			 * 查询额度明细编号;客户准入状态（需要分情况：“customerNum用信客户”会不同）
			 * */
			crdDetail = iCrdDetailService.findCrdDetail(crdGrantOrgNum, customerNum, crdProductNum);
			crdAdmitFlag = crdDetail.getCrdAdmitFlag();
			crdDetailNum = crdDetail.getCrdDetailNum();
			CrdMain crdMainRs=iCrdMainService.findCrdMain(customerNum,crdGrantOrgNum);
			creditStatu=crdMainRs.getCreditStatus();
			if((JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd))&&"0".equals(crdAdmitFlag)){
				log.error("业务准入检查不通过！");
				return "业务准入检查不通过！额度明细编号为："+crdDetail.getCrdDetailNum();
			}
			if((JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd))&&!"01".equals(creditStatu)){
				log.error("额度状态检查不通过！");
				return "额度状态检查不通过！额度编码为："+crdMainRs.getCrdMainNum();
			}
			/**
			 * 根据额度主表获取额度生效日;额度到期日（需要分情况：“customerNum用信客户”会不同）
			 * */
			Date beginDate=turnDate(crdMainRs.getBeginDate());
			Date endDate=turnDate(crdMainRs.getEndDate());
			mapBankCrd.put("beginDate",beginDate);
			mapBankCrd.put("endDate",endDate);
			mapCrd.put(certDrawerCustLegal,mapBankCrd);
		}
		/**
		 * 获取数组并循环，set到凭证信息对象集合中
		 * */
		List<CertInfoRequestDTO> certInfo=billDiscountReqDTO.getCertInfo();
		for(CertInfoRequestDTO certInfoDTO:certInfo){
			if((JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd))&&!(turnDate(certInfoDTO.getCertBeginDate())).after((Date)mapCrd.get(certInfoDTO.getCertDrawerBankNum()).get("beginDate"))){
				log.error("凭证起期不在额度生效日之后，检查不通过！");
				return "凭证起期不在额度生效日之后，检查不通过！";
			}
			if((JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)||JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd))&&!(turnDate(certInfoDTO.getCertBeginDate())).before((Date)mapCrd.get(certInfoDTO.getCertDrawerBankNum()).get("endDate"))){
				log.error("凭证止期不在额度到期日之前，检查不通过！");
				return "凭证止期不在额度到期日之前，检查不通过！";
			}
			String zt=inspectState(tranTypeCd,crdDetailNum,billDiscountReqDTO,certInfoDTO);
			if("0".equals(zt)){
				log.debug("检查通过，插入操作！");
			}else if("-1".equals(zt)){
				log.error("没有找到“业务凭证信息”数据，检查失败，无法操作！");
				return "没有找到“业务凭证信息”数据，检查失败，无法操作";
			}else if("-2".equals(zt)){
				log.error("暂用时凭证状态存在且不是“无效（00），检查不通过！”");
				return "暂用时凭证状态存在且不是“无效（00），检查不通过！”";
			}else if("-3".equals(zt)){
				log.error("占用撤销时不是03占用状态，检查不通过！");
				return "占用撤销时不是03占用状态，检查不通过！";
			}else{
				log.debug("检查通过，更新操作！");
			}
		}
		return "0";
	}

	/**
	 * 额度更新本地处理范围
	 * crdUpdateMap
	 * */
	private List<Map> crdUpdateMap=new ArrayList<Map>();
	@Transactional
	@Override
	public  void crdUpdate(String EventId){
		/**事件主表改为成功*/
		updateEventStatus(EventId, JxrcbBizConstant.event_status_1);
		String slsOrg=deptService.selectProvincialCooperative().getId()+"";
		CrdMain crdMain=new CrdMain();
		CrdDetail crdDetail=new CrdDetail();
		for(Map map:crdUpdateMap){
			BigDecimal amt=(BigDecimal)map.get("isNegative");
			/**额度主表更新
			 * 本方机构；省联社
			 **/
			crdMain.setCrdGrantOrgNum((String)map.get("crdGrantOrgNum"));
			crdMain.setCustomerNum((String)map.get("customerNum"));
			CrdMain crdMainUp=(CrdMain)iCrdMainService.list(Wrappers.query(crdMain)).get(0);
			crdMain.setCrdGrantOrgNum((String)map.get(slsOrg));
			CrdMain crdMainUp1=(CrdMain)iCrdMainService.list(Wrappers.query(crdMain)).get(0);
			//crdMainUp.setLimitUsed(new BigDecimal(getDouble(crdMainUp.getLimitUsed())+getDouble(amt)));
			//crdMainUp.setLimitAvi(new BigDecimal(getDouble(crdMainUp.getLimitAvi())-getDouble(amt)));
			crdMainUp.setLimitUsed(crdMainUp.getLimitUsed().add(amt));
			crdMainUp.setLimitAvi(crdMainUp.getLimitAvi().subtract(amt));
			iCrdMainService.updateById(crdMainUp);

			//crdMainUp1.setLimitUsed(new BigDecimal(getDouble(crdMainUp.getLimitUsed())+getDouble(amt)));
			//crdMainUp1.setLimitAvi(new BigDecimal(getDouble(crdMainUp.getLimitAvi())-getDouble(amt)));
			crdMainUp1.setLimitUsed(crdMainUp.getLimitUsed().add(amt));
			crdMainUp1.setLimitAvi(crdMainUp.getLimitAvi().subtract(amt));
			iCrdMainService.updateById(crdMainUp1);

			/**额度明细更新
			 * 本方机构；省联社
			 * */
			crdDetail.setCrdGrantOrgNum((String)map.get("crdGrantOrgNum"));
			crdDetail.setCustomerNum((String)map.get("customerNum"));
			crdDetail.setCrdDetailPrd((String)map.get("crdProductNum"));
			CrdDetail crdDetailUp=iCrdDetailService.list(Wrappers.query(crdDetail)).get(0);
			crdDetail.setCrdGrantOrgNum((String)map.get(slsOrg));
			CrdDetail crdDetailUp1=iCrdDetailService.list(Wrappers.query(crdDetail)).get(0);

			//crdDetailUp.setLimitUsed(new BigDecimal(getDouble(crdDetailUp.getLimitUsed())+getDouble(amt)));
			//crdDetailUp.setLimitAvi(new BigDecimal(getDouble(crdDetailUp.getLimitAvi())-getDouble(amt)));
			crdDetailUp.setLimitUsed(crdDetailUp.getLimitUsed().add(amt));
			crdDetailUp.setLimitAvi(crdDetailUp.getLimitAvi().add(amt));
			iCrdDetailService.updateById(crdDetailUp);

			//crdDetailUp1.setLimitUsed(new BigDecimal(getDouble(crdDetailUp.getLimitUsed())+getDouble(amt)));
			//crdDetailUp1.setLimitAvi(new BigDecimal(getDouble(crdDetailUp.getLimitAvi())-getDouble(amt)-getDouble(crdDetailUp.getLimitEarmark())));
			crdDetailUp1.setLimitUsed(crdDetailUp.getLimitUsed().add(amt));
			crdDetailUp1.setLimitAvi(crdDetailUp.getLimitAvi().subtract(amt).subtract(crdDetailUp.getLimitEarmark()));
			iCrdDetailService.updateById(crdDetailUp1);
		}
	}
	/***
	 * 更新事件表状态
	 */
	@Override
	public void updateEventStatus(String EventMainUUID,String eventStatus){
		BillEventMain billEventMain=new BillEventMain();
		billEventMain.setEventMainId(EventMainUUID);
		billEventMain.setTranEventStatus(eventStatus);
		iBillEventMainServicei.updateById(billEventMain);
	}

	/**String转Date*/
	public  Date turnDate(String strData) {
		if(strData==null||"".equals(strData)){return null;}
		String str = "yyyy-MM-dd HH:mm:ss";
		str = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(str);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strData, pos);
		return strtodate;
	}
	/**BigDecimal转Double*/
	public Double getDouble(BigDecimal b){
		if(b==null){
			return 0.00;
		}
		return b.doubleValue();
	}


}
