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
import org.git.common.exception.AssertUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.credit.service.IFundCheckService;
import org.git.modules.clm.credit.service.IFundCreditUseService;
import org.git.modules.clm.credit.service.IParCrdRuleCtrlService;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.CertInfoRequestDTO;
import org.git.modules.clm.loan.dto.LoanBillUseEventDTO;
import org.git.modules.clm.loan.entity.*;
import org.git.modules.clm.loan.service.*;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.service.IProductService;
import org.git.modules.system.constant.DeptConstant;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * CLM301票据贴现，再贴，转贴
 * @author liuye
 * @since 2019-12-30
 */
@Service
@AllArgsConstructor
@Slf4j
public class LoanBillUseServiceImpl implements ILoanBillUseService {
	@Autowired
	private IBillEventMainService billEventMainServicei;
	@Autowired
	private IBillEventDetailService billEventDetailService;
	@Autowired
	private ICrdApplySerialService crdApplySerialService;
	@Autowired
	private ICrdBusiCertInfoService crdBusiCertInfoService;
	@Autowired
	private ICrdDetailService crdDetailService;
	@Autowired
	private ICrdMainService crdMainService;
	@Autowired
	private ICrdProductService crdProductService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private ICsmCorporationService csmCorporationService;
	@Autowired
	private IFundCreditUseService fundCreditUseService;
	@Autowired
	private IFundCheckService fundCheckService;
	@Autowired
	private IParCrdRuleCtrlService parCrdRuleCtrlService;

	/**插入事件主表和明细表数据数据*/
	@Transactional
	@Override
	public LoanBillUseEventDTO insertEvent(ExtAttributes extAttributes, BillDiscountRequestDTO billDiscountReqDTO){
		LoanBillUseEventDTO loanBillUseEventDTO = new LoanBillUseEventDTO();
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		//事件表校验重复
		checkRepeat(tranDate,billDiscountReqDTO);
		/**
		 * 获取用信金额，用于第一步落地前的金额校验
		 * */
		BigDecimal crdApplyAmt=billDiscountReqDTO.getCrdApplyAmt();
		/**
		 * 事件主表数据获取
		 * */
		BillEventMain billEventMain=new BillEventMain();
		String eventMainUUID= StringUtil.randomUUID();
		billEventMain.setEventMainId(eventMainUUID);
		billEventMain.setTranSeqSn(tranSeqSn);
		billEventMain.setTranDate(tranDate);
		billEventMain.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		billEventMain.setTranTypeCd(billDiscountReqDTO.getTranTypeCd());
		billEventMain.setCrdApplyAmt(billDiscountReqDTO.getCrdApplyAmt());
		billEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);
		billEventMain.setTranSystem(extAttributes.getRequesterId());
		billEventMain.setUserNum(extAttributes.getOperatorId());
		billEventMain.setTranAcctStatus("0");
		/**事件明细表数据获取*/
		BillEventDetail billEventDetail=new BillEventDetail();
		billEventDetail.setEventMainId(eventMainUUID);
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
		BigDecimal certApplyAmtSum=new BigDecimal("0");
		int customerNumStatus=0;
		if(certInfo!=null) {
			for (CertInfoRequestDTO certInfoDTO : certInfo) {
				checkCertInfo(billEventMain.getTranTypeCd(),certInfoDTO);
				if ("0".equals(certInfoDTO.getCertIsMyBank())) {
					String customerNum = csmCorporationService.getCustomerNum(certInfoDTO.getCertDrawerBankNum());
					if ("-1".equals(customerNum)) {
						/**当有人行清算行号找不到客户编号时，事件落地后就结束所有操作*/
						customerNumStatus = 1;
					}
					billEventDetail.setCertDrawerBankLegal(customerNum);
				}
				BillEventDetail toAddDetail = new BillEventDetail();
				BeanUtils.copyProperties(billEventDetail, toAddDetail);
				/**
				 * 凭证用信金额合算，用于比较
				 * */
				certApplyAmtSum = certApplyAmtSum.add(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
				toAddDetail.setCertIsMyBank(certInfoDTO.getCertIsMyBank());
				toAddDetail.setEventDetailedId(StringUtil.randomUUID());
				toAddDetail.setCertNum(certInfoDTO.getCertNum());
				toAddDetail.setCertTypeCd(certInfoDTO.getCertTypeCd());
				toAddDetail.setCertPptCd(certInfoDTO.getCertPptCd());
				toAddDetail.setCertInterestPeriType(certInfoDTO.getCertInterestPeriType());
				toAddDetail.setCertInterestPeriod(certInfoDTO.getCertInterestPeriod());
				toAddDetail.setCertInterestRateType(certInfoDTO.getCertInterestRateType());
				toAddDetail.setCertInterestRate(CommonUtil.stringToBigDecimal(certInfoDTO.getCertInterestRate()));
				toAddDetail.setCertCurrencyCd(certInfoDTO.getCertCurrencyCd());
				toAddDetail.setCertSeqAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertSeqAmt()));
				toAddDetail.setCertApplyAmt(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyAmt()));
				toAddDetail.setCertApplyBalance(CommonUtil.stringToBigDecimal(certInfoDTO.getCertApplyBalance()));
				toAddDetail.setCertStatus(certInfoDTO.getCertStatus());
				toAddDetail.setCertBeginDate(certInfoDTO.getCertBeginDate());
				toAddDetail.setCertEndDate(certInfoDTO.getCertEndDate());
				toAddDetail.setCertFinishDate(certInfoDTO.getCertFinishDate());
				toAddDetail.setCertDrawerCustNum(certInfoDTO.getCertDrawerCustNum());
				toAddDetail.setCertDrawerName(certInfoDTO.getCertDrawerName());
				toAddDetail.setCertDrawerBankNum(certInfoDTO.getCertDrawerBankNum());
				toAddDetail.setCertGuarantyType(certInfoDTO.getCertGuarantyType());
				toAddDetail.setCertGuarantyPerson(certInfoDTO.getCertGuarantyPerson());
				toAddDetail.setCertBusiRemark(certInfoDTO.getCertBusiRemark());
				toAddDetail.setEventDetailedId(StringUtil.randomUUID());

				billEventDetailList.add(toAddDetail);
			}
			/**校验金额是否相等。不等，直接回滚*/
			checkBlance(crdApplyAmt, certApplyAmtSum);
		}else{
			billEventDetailList.add(billEventDetail);
		}
		loanBillUseEventDTO.setCustomerNumStatus(customerNumStatus);
		loanBillUseEventDTO.setBillEventMain(billEventMain);
		loanBillUseEventDTO.setBillEventDetail(billEventDetailList);
		billEventMainServicei.save(billEventMain);
		billEventDetailService.saveBatch(billEventDetailList);
		return loanBillUseEventDTO;
	}

	/**校验金额是否相等。不等，直接回滚*/
	private void checkBlance(BigDecimal crdApplyAmt,BigDecimal certApplyAmtSum){
		if(crdApplyAmt.compareTo(certApplyAmtSum)!=0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30201,"凭证用信金额累加与用信金额不相等！交易结束！");
		}
	}

	/**事件表校验重复
	 * 交易日期、业务编号、交易类型、币种，金额
	 * 暂时放开（本方机构、对手机构、业务产品，）
	 * */
	private void checkRepeat(String tranDate,BillDiscountRequestDTO billDiscountReqDTO){
		BillEventMain billEventMain1=new BillEventMain();
		billEventMain1.setTranDate(tranDate);
		billEventMain1.setBusiDealNum(billDiscountReqDTO.getBusiDealNum());
		billEventMain1.setTranTypeCd(billDiscountReqDTO.getTranTypeCd());
		billEventMain1.setCrdApplyAmt(billDiscountReqDTO.getCrdApplyAmt());
		billEventMain1.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		List<Object> list=billEventMainServicei.listObjs(Wrappers.query(billEventMain1));
		if(list.size()>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30202,"数据重复校验不通过！交易结束！");
		}

	}

	@Override
	@Transactional
	public void middleHandle(LoanBillUseEventDTO loanBillUseEventDTO,ExtAttributes extAttributes,String eventTypeCd){
		//检查方法
		ParCrdRuleCtrl param = new ParCrdRuleCtrl();
		param.setEventTypeCd(eventTypeCd);
		param.setTranTypeCd(loanBillUseEventDTO.getBillEventMain().getTranTypeCd());
		param.setCheckFlag(JxrcbBizConstant.CHECK_FLAG_YES);
		List<ParCrdRuleCtrl> rules = parCrdRuleCtrlService.list(Condition.getQueryWrapper(param));
		List<ParCrdRuleCtrl> certRules = new ArrayList<>();//凭证信息规则
		List<ParCrdRuleCtrl> notCertRules = new ArrayList<>();//非凭证信息规则
		if(rules!=null && !rules.isEmpty()){
			for (int i=0;i<rules.size();i++) {
				if(rules.get(i).getCheckMethod().equals(JxrcbBizConstant.METHOD_CHECKCERTSTATUS)
					|| rules.get(i).getCheckMethod().equals(JxrcbBizConstant.METHOD_COUNTDATEVALID)
				){
					certRules.add(rules.get(i));
				}else{
					notCertRules.add(rules.get(i));
				}
			}
		}
		String busiPrdNum=loanBillUseEventDTO.getBillEventDetail().get(0).getBusiPrdNum();
		if(JxrcbBizConstant.BUSI_PRD_NUM_5.equals(busiPrdNum)){
			//TODO /**到期，此情况暂不处理*/
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30205,"此接口不支持票据到期交易!");
		}
		Timestamp now = CommonUtil.getWorkDateTime();
		eventSplit(loanBillUseEventDTO,extAttributes,now,certRules,notCertRules);
	}

	private void eventSplit(LoanBillUseEventDTO loanBillUseEventDTO,ExtAttributes extAttributes,Timestamp now,List<ParCrdRuleCtrl> certRules,List<ParCrdRuleCtrl> notCertRules){
		BillEventMain billEventMain=loanBillUseEventDTO.getBillEventMain();
		List<BillEventDetail> billEventDetails= loanBillUseEventDTO.getBillEventDetail();
		String busiPrdNum=billEventDetails.get(0).getBusiPrdNum();
		String crdGrantOrgNum=billEventDetails.get(0).getBusiDealOrgNum();
		String customerNum=billEventDetails.get(0).getBusiOpptOrgNum();
		/**根据"业务品种"查询额度产品编码(同业),所有情况公用*/
		CrdProduct crdProductParam=new CrdProduct();
		crdProductParam.setProductNum(busiPrdNum);
		// 直贴入 客户是企业 其它是同业
		if(JxrcbBizConstant.BUSI_PRD_NUM_1.equals(busiPrdNum)){
			crdProductParam.setCustType(JxrcbBizConstant.CUST_TYPE_GS);
		}else{
			crdProductParam.setCustType(JxrcbBizConstant.CUST_TYPE_TY);
		}
		crdProductParam.setCrdProductType(JxrcbBizConstant.CRD_TYPE_SX);
		Crd crd = crdProductService.selectCrd(crdProductParam);
		if(crd==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30206,busiPrdNum+"对应额度产品不存在！");
		}
		String crdProductNum=crd.getCrdProductNum();
		Product proParams= new Product();
		proParams.setProductNum(busiPrdNum);
		Product product=productService.getOne(Condition.getQueryWrapper(proParams));
		if(product==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30208,busiPrdNum+"业务品种信息不存在！");
		}
		/**
		 * 校验并插入
		 * 一，拆分登记直贴企业占信
		 * */
		CrdDetail crdDetail=crdDetailService.findCrdDetail(crdGrantOrgNum,customerNum,crdProductNum);
		if(crdDetail==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30207,crdGrantOrgNum+"机构未对客户"+customerNum+"进行产品"+crdProductNum+"额度切分");
		}
		//初始化用信流水表和凭证表
		CrdApplySerial crdApplySerial=tranToSerial(loanBillUseEventDTO,crdDetail,extAttributes,now);

		String tranTypeCd = loanBillUseEventDTO.getBillEventMain().getTranTypeCd();
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(crdDetail.getCrdGrantOrgNum(), DeptConstant.PROVINCIAL_COOPERATIVE);
		if(provideDept==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20110,"未查询到当前机构的省联社");
		}
		if(JxrcbBizConstant.BUSI_PRD_NUM_1.equals(busiPrdNum)
			&&(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)
			||JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd))){
			crdApplySerialService.save(crdApplySerial);
			CrdMain crdMain = crdMainService.getById(crdDetail.getCrdMainNum());
			for (BillEventDetail billEventDetail: billEventDetails) {
				//凭证信息处理
				saveOrupdateBusiCertInfo(crdApplySerial,billEventMain,billEventDetail,crdProductNum,crdMain,certRules);
			}
			if(JxrcbBizConstant.PRODUCT_TARGER_1.equals(product.getProductTarger())){
				CrdDetail provideCrdDetail = crdDetail;
				CrdMain provideCrdMain = crdMain;
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
				fundCreditUseService.localProcess(crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain,now);
			}else{
				CrdDetail provideCrdDetail = crdDetailService.findCrdDetail(provideDept.getId(),customerNum,crdProductNum);
				CrdMain provideCrdMain = crdMainService.getById(provideCrdDetail.getCrdMainNum());
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
				fundCreditUseService.localProcess(crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain,now);
			}

		}
		/**
		 *二，检查：登记票据中的承兑行占信1/n
		 * */
		String tranSeqSn=extAttributes.getOriReqSn();
		List<Map> GroupCertApplyAmt = billEventDetailService.selectGroupCertApplyAmt(tranSeqSn);
		for (Map map : GroupCertApplyAmt) {
			customerNum = (String) map.get("CERTDRAWERBANKLEGAL");
			BigDecimal sumCertApplyAmt = (BigDecimal) map.get("SUMCERTAPPLYAMT");
			crdDetail = crdDetailService.findCrdDetail(crdGrantOrgNum, customerNum, crdProductNum);
			crdApplySerial.setLimitCreditAmt(sumCertApplyAmt);
			crdApplySerial.setCustomerNum(customerNum);
			crdApplySerial.setCrdDetailNum(crdDetail.getCrdDetailNum());
			crdApplySerial.setSerialId(StringUtil.randomUUID());
			crdApplySerialService.save(crdApplySerial);
			CrdMain crdMain = crdMainService.getById(crdDetail.getCrdMainNum());

			for (BillEventDetail billEventDetail: billEventDetails) {
				//凭证信息处理
				saveOrupdateBusiCertInfo(crdApplySerial,billEventMain,billEventDetail,crdProductNum,crdMain,certRules);
			}
			if(JxrcbBizConstant.PRODUCT_TARGER_1.equals(product.getProductTarger())){
				CrdDetail provideCrdDetail = crdDetail;
				CrdMain provideCrdMain = crdMain;
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
				fundCreditUseService.localProcess(crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain,now);
			}else{
				CrdDetail provideCrdDetail = crdDetailService.findCrdDetail(provideDept.getId(),customerNum,crdProductNum);
				CrdMain provideCrdMain = crdMainService.getById(provideCrdDetail.getCrdMainNum());
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
				fundCreditUseService.localProcess(crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain,now);
			}
		}


	}

	private void saveOrupdateBusiCertInfo(CrdApplySerial crdApplySerial, BillEventMain billEventMain, BillEventDetail billEventDetail,String crdProductNum, CrdMain crdMain, List<ParCrdRuleCtrl> certRules){
		String tranTypeCd=billEventMain.getTranTypeCd();
		if(JxrcbBizConstant.TRAN_TYPE_PRE.equals(tranTypeCd)
			||JxrcbBizConstant.TRAN_TYPE_PRE_CANCEL.equals(tranTypeCd)){
			return;//票据系统预占用时不传凭证信息
		}
		CrdBusiCertInfo param = new CrdBusiCertInfo();
		param.setCrdGrantOrgNum(billEventDetail.getBusiDealOrgNum());
		param.setCrdDetailPrd(crdProductNum);
		param.setBusiDealNum(billEventDetail.getBusiDealNum());
		param.setCertNum(billEventDetail.getCertNum());
		CrdBusiCertInfo crdBusiCertInfo=crdBusiCertInfoService.getOne(Condition.getQueryWrapper(param));
		CrdBusiCertInfo newCertInfo = new CrdBusiCertInfo();
		copyProperties(billEventMain,billEventDetail,newCertInfo,crdProductNum);
		newCertInfo.setUserNum(billEventMain.getUserNum());
		if(crdBusiCertInfo==null){
			newCertInfo.setCretInfoId(UUID.randomUUID().toString().replaceAll("-",""));
			newCertInfo.setCrdDetailNum(crdApplySerial.getCrdDetailNum());
		}else{
			newCertInfo.setCretInfoId(crdBusiCertInfo.getCretInfoId());
			newCertInfo.setCrdDetailNum(crdBusiCertInfo.getCrdDetailNum());
			newCertInfo.setLastSummaryBal(crdBusiCertInfo.getLastSummaryBal());
			fundCheckService.midCheck(certRules,null,crdApplySerial,crdMain,null,crdBusiCertInfo,null,null);
		}
		//根据交易类型和交易金额改变凭证状态和用信金额
		fundCreditUseService.setCertStatusAndAmt(crdApplySerial.getTranTypeCd(),newCertInfo,crdBusiCertInfo);
		crdBusiCertInfoService.saveOrUpdate(newCertInfo);
	}

	public void copyProperties(BillEventMain billEventMain,BillEventDetail source,CrdBusiCertInfo target,String crdProductNum){
		target.setCrdDetailPrd(crdProductNum);
		target.setCrdGrantOrgNum(source.getBusiDealOrgNum());
		target.setCustomerNum(source.getBusiOpptOrgNum());
		target.setBusiDealNum(source.getBusiDealNum());
		target.setBusiPrdNum(source.getBusiPrdNum());
		target.setBusiDealDesc(source.getBusiDealDesc());
		target.setBusiDealOrgNum(source.getBusiDealOrgNum());
		target.setBusiDealOrgName(source.getBusiDealOrgName());
		target.setBusiOpptOrgNum(source.getBusiOpptOrgNum());
		target.setBusiOpptOrgName(source.getBusiOpptOrgName());
		target.setBusiSumAmt(source.getBusiSumAmt());
		target.setBusiCertCnt(source.getBusiCertCnt());
		target.setCertNum(source.getCertNum());
		target.setCertTypeCd(source.getCertTypeCd());
		target.setCertPptCd(source.getCertPptCd());
		target.setCertInterestPeriType(source.getCertInterestPeriType());
		target.setCertInterestPeriod(source.getCertInterestPeriod());
		target.setCertInterestRateType(source.getCertInterestRateType());
		target.setCertInterestRate(source.getCertInterestRate());
		target.setCertCurrencyCd(source.getCertCurrencyCd());
		target.setCertApplyAmt(source.getCertApplyAmt());
		target.setCertSeqAmt(source.getCertSeqAmt());
		target.setCertApplyBalance(source.getCertApplyBalance());
		target.setCertStatus(source.getCertStatus());
		target.setCertBeginDate(source.getCertBeginDate());
		target.setCertEndDate(source.getCertEndDate());
		target.setCertFinishDate(source.getCertFinishDate());
		target.setCertDrawerCustNum(source.getCertDrawerCustNum());
		target.setCertDrawerName(source.getCertDrawerName());
		target.setCertDrawerBankNum(source.getCertDrawerBankNum());
		target.setCertDrawerBankName(source.getCertDrawerBankName());
		target.setCertDrawerBankLegal(source.getCertDrawerBankLegal());
		target.setCertGuarantyType(source.getCertGuarantyType());
		target.setCertGuarantyPerson(source.getCertGuarantyPerson());
		target.setCertBusiRemark(source.getCertBusiRemark());
		target.setUserNum(billEventMain.getUserNum());
	}

	private CrdApplySerial tranToSerial(LoanBillUseEventDTO loanBillUseEventDTO,CrdDetail crdDetail,ExtAttributes extAttributes,Timestamp now){
		BillEventMain billEventMain = loanBillUseEventDTO.getBillEventMain();
		CrdApplySerial crdApplySerial = new CrdApplySerial();
		crdApplySerial.setTranSeqSn(billEventMain.getTranSeqSn());
		crdApplySerial.setTranDate(billEventMain.getTranDate());
		crdApplySerial.setBusiDealNum(billEventMain.getBusiDealNum());
		crdApplySerial.setTranTypeCd(billEventMain.getTranTypeCd());
		crdApplySerial.setCrdDetailNum(crdDetail.getCrdDetailNum());
		crdApplySerial.setCrdGrantOrgNum(crdDetail.getCrdGrantOrgNum());
		crdApplySerial.setCustomerNum(crdDetail.getCustomerNum());
		crdApplySerial.setCrdDetailPrd(crdDetail.getCrdDetailPrd());
		crdApplySerial.setLimitCreditAmt(billEventMain.getCrdApplyAmt());
		crdApplySerial.setCurrencyCd(billEventMain.getCertCurrencyCd());
		crdApplySerial.setTranSystem(extAttributes.getRequesterId());
		crdApplySerial.setOrgNum(extAttributes.getBranchId());
		crdApplySerial.setUserNum(extAttributes.getOperatorId());
		crdApplySerial.setCreateTime(now);
		crdApplySerial.setUpdateTime(now);
		return crdApplySerial;
	}


	private void checkCertInfo(String tranTypeCd, CertInfoRequestDTO certInfo){
		if(JxrcbBizConstant.TRAN_TYPE_PRE.equals(tranTypeCd)
			||JxrcbBizConstant.TRAN_TYPE_PRE_CANCEL.equals(tranTypeCd)){
			return;
		}
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		if(StringUtil.isEmpty(certInfo.getCertNum())){
			flag = true;
			sb.append("cert_num凭证编号不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertTypeCd())){
			flag = true;
			sb.append("cert_type_cd凭证品种不能为空;");
		}
		//	凭证性质分类覆盖债券和存单，其他未涉及限额性质暂空

//			if(StringUtil.isEmpty(certInfo.getCertPptCd())){
//				flag = true;
//				sb.append("cert_ppt_cd凭证性质不能为空;");
//			}
		if(StringUtil.isEmpty(certInfo.getCertInterestPeriType())){
			flag = true;
			sb.append("cert_interest_peri_type计息期限类型不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertInterestPeriod())){
			flag = true;
			sb.append("cert_interest_period计息期限不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertInterestRateType())){
			flag = true;
			sb.append("cert_interest_rate_type利率类型不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertInterestRate())){
			flag = true;
			sb.append("cert_interest_rate收益率/利率不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertCurrencyCd())){
			flag = true;
			sb.append("cert_currency_cd币种不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertSeqAmt())){
			flag = true;
			sb.append("cert_seq_amt凭证原始金额不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertApplyAmt())){
			flag = true;
			sb.append("cert_apply_amt凭证用信金额不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertSeqAmt())){
			flag = true;
			sb.append("cert_begin_date凭证起期不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertIsMyBank())){
			flag = true;
			sb.append("cert_is_my_bank是否本法人行不能为空;");
		}
		if(StringUtil.isEmpty(certInfo.getCertDrawerBankNum())){
			flag = true;
			sb.append("cert_drawer_bank_num发行人代理/承兑行号;");
		}
		if(StringUtil.isEmpty(certInfo.getCertDrawerBankLegal())){
			flag = true;
			sb.append("cert_drawer_bank_legal发行人代理/承兑行法人客户号;");
		}
		if(flag){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20519,sb.toString());
		}

	}
}
