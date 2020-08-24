package org.git.modules.clm.credit.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.star.bridge.oleautomation.Decimal;
import org.git.common.exception.AssertUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.FundEventDetail;
import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.CertInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdApplyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.system.constant.DeptConstant;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FundCreaditUseServiceImpl implements IFundCreditUseService {

	@Autowired
	private ICrdDetailService crdDetailService;
	@Autowired
	private ICrdBusiCertInfoService crdBusiCertInfoService;
	@Autowired
	private ICrdMainService crdMainService;
	@Autowired
	private ICrdApplySerialService crdApplySerialService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IFundEventMainService fundEventMainService;
	@Autowired
	private IFundEventDetailService fundEventDetailService;
	@Autowired
	private IFundCheckService fundCheckService;
	@Autowired
	private ICsmPartyService csmPartyService;
	@Autowired
	private IParCrdRuleCtrlService parCrdRuleCtrlService;


	/**
	 * 进行事件用信金额与凭证数组汇总金额平衡检查
	 * @param fundCreditUse
	 */
	public void checkBalance(FundCreditUseRequestDTO fundCreditUse){
		List<CrdApplyInfoRequestDTO> CrdApplyInfos = fundCreditUse.getCrdApplyInfo();
		if(CrdApplyInfos==null || CrdApplyInfos.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20509,"业务用信数组不能为空！");
		}
		BigDecimal totalCertInfo = new BigDecimal("0");
		for (CrdApplyInfoRequestDTO certApplyInfo: CrdApplyInfos) {
			List<CertInfoRequestDTO> certInfos = certApplyInfo.getCertInfo();
			if(certInfos==null || certInfos.isEmpty()){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20510,"凭证数组不能为空！");
			}
			for (CertInfoRequestDTO certInfo: certInfos ) {
				totalCertInfo = totalCertInfo.add(new BigDecimal(certInfo.getCertApplyAmt()));
			}
		}
		if(new BigDecimal(fundCreditUse.getCrdApplyAmt()).compareTo(totalCertInfo)!=0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20511,"凭证用信总金额与用信金额不一致！");
		}
	}

	public void checkTranTypeAndCrd(String tranTypeCd,String productNum){
		if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd) || JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd)){
			if(!JxrcbBizConstant.CRD_DETAIL_PRD_03019999.equals(productNum)){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20518,productNum+"不能做直接占用、直接占用撤销操作！");
			}
		}
		if(JxrcbBizConstant.CRD_DETAIL_PRD_03019999.equals(productNum)){
			if(!JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd) &&
				!JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd) &&
				!JxrcbBizConstant.TRAN_TYPE_RESUME.equals(tranTypeCd) &&
				!JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd)
				){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20518,productNum+"只能做直接占用、直接占用撤销、恢复、恢复撤销操作！");
			}
		}
	}

	/**
	 * 防重检查
	 */
	public void checkRepeatEvent(FundCreditUseRequestDTO fundCreditUse, ExtAttributes extAttributes){
		FundEventMain fundEventMain = new FundEventMain();
		fundEventMain.setTranDate(extAttributes.getOriReqDate());//交易日期
		fundEventMain.setBusiDealNum(fundCreditUse.getBusiDealNum());//业务编号
		fundEventMain.setTranTypeCd(fundCreditUse.getTranTypeCd());//交易类型
		fundEventMain.setCrdApplyAmt(new BigDecimal(fundCreditUse.getCrdApplyAmt()));//用信金额
		fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		int count = fundEventMainService.count(Condition.getQueryWrapper(fundEventMain));
		if(count>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20512,"该交易已处理！");
		}
	}

	/**
	 * 事件落地
	 * @param fundCreditUse
	 */
	@Override
	@Transactional
	public FundEventMain registerBusiEvent(FundCreditUseRequestDTO fundCreditUse, ExtAttributes extAttributes){
		//防重检查
		checkRepeatEvent(fundCreditUse,extAttributes);
		//进行事件用信金额与凭证数组汇总金额平衡检查
		checkBalance(fundCreditUse);
		//插入事件主表及详细表
		FundEventMain fundEventMain = new FundEventMain();//
		fundEventMain.setTranSeqSn(extAttributes.getOriReqSn());//交易流水号
		fundEventMain.setTranDate(extAttributes.getOriReqDate());//交易日期
		fundEventMain.setTranSystem(extAttributes.getRequesterId());//经办系统
		fundEventMain.setUserNum(extAttributes.getOperatorId());//经办人
		fundEventMain.setBusiDealNum(fundCreditUse.getBusiDealNum());//业务编号
		fundEventMain.setTranTypeCd(fundCreditUse.getTranTypeCd());//交易类型
		fundEventMain.setCrdApplyAmt(new BigDecimal(fundCreditUse.getCrdApplyAmt()));//用信金额
		fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);//本方处理状态
		fundEventMain.setTranAcctStatus(JxrcbBizConstant.TRAN_ACCT_STATUS_UNPROCES);//对方处理状态
		fundEventMain.setEventMainId(UUID.randomUUID().toString().replaceAll("-",""));//事件主表ID
		List<CrdApplyInfoRequestDTO> CrdApplyInfos = fundCreditUse.getCrdApplyInfo();
		List<FundEventDetail> toAdd = new ArrayList<>();
		for (CrdApplyInfoRequestDTO certApplyInfo: CrdApplyInfos) {
			checkTranTypeAndCrd(fundCreditUse.getTranTypeCd(),certApplyInfo.getCrdDetailPrd());
			FundEventDetail fundEventDetail = new FundEventDetail();
			fundEventDetail.setEventMainId(fundEventMain.getEventMainId());//事件主表ID
			fundEventDetail.setTranSeqSn(extAttributes.getOriReqSn());//交易流水号
			fundEventDetail.setTranDate(extAttributes.getOriReqDate());	//	交易日期
			fundEventDetail.setCrdGrantOrgNum(certApplyInfo.getCrdGrantOrgNum());//授信机构
			fundEventDetail.setCustomerNum(certApplyInfo.getCustomerNum());//用信客户号
			fundEventDetail.setCrdDetailPrd(certApplyInfo.getCrdDetailPrd());//明细额度产品
			fundEventDetail.setBusiDealNum(fundCreditUse.getBusiDealNum());//业务编号
			fundEventDetail.setBusiPrdNum(certApplyInfo.getBusiPrdNum());//业务产品编号
			fundEventDetail.setBusiDealDesc(certApplyInfo.getBusiDealDesc());	//业务交易描述
			fundEventDetail.setBusiDealOrgNum(certApplyInfo.getBusiDealOrgNum());//本方机构
			fundEventDetail.setBusiDealOrgName(certApplyInfo.getBusiDealOrgName());//	本方机构名称
			fundEventDetail.setBusiOpptOrgNum(certApplyInfo.getBusiOpptOrgNum());//对手机构
			fundEventDetail.setBusiOpptOrgName(certApplyInfo.getBusiOpptOrgName());//	对手机构名称
			fundEventDetail.setBusiSumAmt(CommonUtil.stringToBigDecimal(certApplyInfo.getBusiSumAmt() ));//交易总金额
			fundEventDetail.setBusiCertCnt(CommonUtil.stringToBigDecimal(certApplyInfo.getBusiCertCnt()));//	凭证张数

			for (CertInfoRequestDTO certInfo: certApplyInfo.getCertInfo()) {
				fundCheckService.checkCertInfo(fundEventMain.getTranTypeCd(),certInfo);
				FundEventDetail toAddDetail = new FundEventDetail();
				BeanUtils.copyProperties(fundEventDetail,toAddDetail);
				toAddDetail.setEventDetailedId(UUID.randomUUID().toString().replaceAll("-",""));//事件详情细ID
				toAddDetail.setCertNum(certInfo.getCertNum());//凭证编号  cert_num
				toAddDetail.setCertTypeCd(certInfo.getCertTypeCd());	//	凭证品种  cert_type_cd
				toAddDetail.setCertPptCd(certInfo.getCertPptCd());	//凭证性质  cert_ppt_cd
				toAddDetail.setCertInterestPeriod(certInfo.getCertInterestPeriod());	//	凭证计息期限  cert_interest_period
				toAddDetail.setCertInterestPeriType(certInfo.getCertInterestPeriType());	//	凭证计息期限类弄
				toAddDetail.setCertInterestRate(CommonUtil.stringToBigDecimal(certInfo.getCertInterestRate()));	//收益率/利率  cert_interest_rate
				toAddDetail.setCertInterestRateType(certInfo.getCertInterestRateType());	//收益率/利率类型
				toAddDetail.setCertCurrencyCd(certInfo.getCertCurrencyCd());	//币种  cert_currency_cd
				toAddDetail.setCertSeqAmt(CommonUtil.stringToBigDecimal(certInfo.getCertSeqAmt() ));	//	凭证原始金额  cert_seq_amt
				toAddDetail.setCertApplyAmt(CommonUtil.stringToBigDecimal(certInfo.getCertApplyAmt() ));	//凭证用信金额  cert_apply_amt
				toAddDetail.setCertApplyBalance(CommonUtil.stringToBigDecimal(certInfo.getCertApplyBalance() ));	//	凭证用信余额  cert_apply_balance

				toAddDetail.setCertBeginDate(certInfo.getCertBeginDate());	//	凭证起期  cert_begin_date
				toAddDetail.setCertEndDate(certInfo.getCertEndDate());	//凭证止期  cert_end_date
				toAddDetail.setCertFinishDate(certInfo.getCertFinishDate());	//	凭证结清日期  cert_finish_date
				toAddDetail.setCertDrawerCustNum(certInfo.getCertDrawerCustNum());	//发行人客户号  cert_drawer_cust_num
				toAddDetail.setCertDrawerName(certInfo.getCertDrawerName());	//	发行人客户名称  cert_drawer_name
				toAddDetail.setCertDrawerBankNum(certInfo.getCertDrawerBankNum());//发行人代理/承兑行号  cert_drawer_bank_num
				toAddDetail.setCertDrawerBankName(certInfo.getCertDrawerBankName());	//发行人代理/承兑行名  cert_drawer_bank_name
				toAddDetail.setCertGuarantyType(certInfo.getCertGuarantyType());	//担保方式  cert_guaranty_type
				toAddDetail.setCertGuarantyPerson(certInfo.getCertGuarantyPerson());	//	担保人  cert_guaranty_person
				toAddDetail.setCertBusiRemark(certInfo.getCertBusiRemark());	//备注信息  cert_busi_remark
				toAdd.add(toAddDetail);
			}
		}
		fundEventMainService.save(fundEventMain);
		fundEventDetailService.saveBatch(toAdd);
		return fundEventMain;
	}


	@Transactional(rollbackFor = Exception.class)
	public void middleHandle(FundEventMain fundEventMain, ExtAttributes extAttributes,String eventTypeCd){
		//检查方法
		ParCrdRuleCtrl param = new ParCrdRuleCtrl();
		param.setEventTypeCd(eventTypeCd);
		param.setTranTypeCd(fundEventMain.getTranTypeCd());
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
		String tranTypeCd = fundEventMain.getTranTypeCd();
		List<FundEventDetail> fundEventDetails = fundEventDetailService.selectToSerial(fundEventMain.getEventMainId());
		for (FundEventDetail fundEventDetail:fundEventDetails) {
			eventSplit(fundEventMain,fundEventDetail,tranTypeCd,extAttributes,notCertRules,certRules);
		}
	}


	public void eventSplit(FundEventMain fundEventMain,FundEventDetail fundEventDetail,String tranTypeCd, ExtAttributes extAttributes,List<ParCrdRuleCtrl> notCertRules,List<ParCrdRuleCtrl> certRules){
		Timestamp now = CommonUtil.getWorkDateTime();
		CrdDetail params = new CrdDetail();
		params.setCrdGrantOrgNum(fundEventDetail.getCrdGrantOrgNum());
		params.setCustomerNum(fundEventDetail.getCustomerNum());
		params.setCrdDetailPrd(fundEventDetail.getCrdDetailPrd());
		//查询当前机构的明细额度
		CrdDetail crdDetail = crdDetailService.getOne(Condition.getQueryWrapper(params));
		//插入流水
		CrdApplySerial crdApplySerial = addSerial(fundEventDetail,tranTypeCd,crdDetail==null ? JxrcbBizConstant.CRD_DETAIL_NUM_BLANK:crdDetail.getCrdDetailNum(),now,extAttributes);
		CrdMain crdMain = null;
		if(crdDetail!=null){
			crdMain = crdMainService.getById(crdDetail.getCrdMainNum());//查询当前机构的综合额度
		}
		//业务凭证信息登记
		List<FundEventDetail> fundEventDetails = fundEventDetailService.list(Condition.getQueryWrapper(fundEventDetail));
		for (FundEventDetail eventDetail:fundEventDetails) {
			saveOrupdateBusiCertInfo(crdApplySerial,fundEventMain,eventDetail,crdMain,certRules);
		}

		if(JxrcbBizConstant.CRD_DETAIL_PRD_03019999.equals(fundEventDetail.getCrdDetailPrd())){
			return;
		}
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(fundEventDetail.getCrdGrantOrgNum(), DeptConstant.PROVINCIAL_COOPERATIVE);
		if(provideDept==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20514,fundEventDetail.getCrdGrantOrgNum()+"的省联社机构不存在");
		}
		params.setCrdGrantOrgNum(provideDept.getId().toString());
		//查询当前机构的省联社的明细额度
		CrdDetail provideCrdDetail = crdDetailService.getOne(Condition.getQueryWrapper(params));
		if(provideCrdDetail==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20515,
				fundEventDetail.getCrdGrantOrgNum()+"机构的省联社机构未对客户"+fundEventDetail.getCustomerNum()+"进行产品"+fundEventDetail.getCrdDetailPrd()+"额度切分");
		}
		//查询当前机构的省联社的综合额度
		CrdMain provideCrdMain = crdMainService.getById(provideCrdDetail.getCrdMainNum());
		if(provideCrdMain==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20517,
				fundEventDetail.getCrdGrantOrgNum()+"机构的省联社机构未对客户"+fundEventDetail.getCustomerNum()+"授信");
		}
		fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
		localProcess(crdApplySerial,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain,now);
	}

	public CrdApplySerial addSerial(FundEventDetail fundEventDetail,String tranTypeCd,String crdDetailNum,Timestamp now, ExtAttributes extAttributes){
		CrdApplySerial crdApplySerial = new CrdApplySerial();
		crdApplySerial.setSerialId(UUID.randomUUID().toString().replaceAll("-",""));
		crdApplySerial.setTranSeqSn(fundEventDetail.getTranSeqSn());
		crdApplySerial.setTranDate(fundEventDetail.getTranDate());
		crdApplySerial.setBusiDealNum(fundEventDetail.getBusiDealNum());
		crdApplySerial.setTranTypeCd(tranTypeCd);
		crdApplySerial.setCrdDetailNum(crdDetailNum);
		crdApplySerial.setCrdGrantOrgNum(fundEventDetail.getCrdGrantOrgNum());
		crdApplySerial.setCustomerNum(fundEventDetail.getCustomerNum());
		crdApplySerial.setCrdDetailPrd(fundEventDetail.getCrdDetailPrd());
		crdApplySerial.setLimitCreditAmt(fundEventDetail.getBusiSumAmt());
		crdApplySerial.setCurrencyCd(fundEventDetail.getCertCurrencyCd());
		crdApplySerial.setTranSystem(extAttributes.getRequesterId());
		crdApplySerial.setOrgNum(extAttributes.getBranchId());
		crdApplySerial.setUserNum(extAttributes.getOperatorId());
		crdApplySerial.setCreateTime(now);
		crdApplySerial.setUpdateTime(now);
		crdApplySerialService.save(crdApplySerial);
		return crdApplySerial;
	}


	public void saveOrupdateBusiCertInfo(CrdApplySerial crdApplySerial,FundEventMain fundEventMain,FundEventDetail fundEventDetail,CrdMain crdMain,List<ParCrdRuleCtrl> certRules){
		CrdBusiCertInfo param = new CrdBusiCertInfo();
		param.setCrdGrantOrgNum(fundEventDetail.getCrdGrantOrgNum());
		param.setCrdDetailPrd(fundEventDetail.getCrdDetailPrd());
		param.setBusiDealNum(fundEventDetail.getBusiDealNum());
		param.setCertNum(fundEventDetail.getCertNum());
		CrdBusiCertInfo crdBusiCertInfo=crdBusiCertInfoService.getOne(Condition.getQueryWrapper(param));
		CrdBusiCertInfo newCertInfo = new CrdBusiCertInfo();
		if(crdBusiCertInfo==null){
			copyProperties(fundEventMain,fundEventDetail,newCertInfo);
			newCertInfo.setCretInfoId(UUID.randomUUID().toString().replaceAll("-",""));
			newCertInfo.setCrdDetailNum(crdApplySerial.getCrdDetailNum());
		}else {
			newCertInfo = crdBusiCertInfo;
			newCertInfo.setCretInfoId(crdBusiCertInfo.getCretInfoId());
			newCertInfo.setCrdDetailNum(crdBusiCertInfo.getCrdDetailNum());
			newCertInfo.setLastSummaryBal(crdBusiCertInfo.getLastSummaryBal());
		}
		fundCheckService.midCheck(certRules,null,crdApplySerial,crdMain,null,newCertInfo,null,null);

		setCertStatusAndAmt(crdApplySerial.getTranTypeCd(),newCertInfo,crdBusiCertInfo);
		crdBusiCertInfoService.saveOrUpdate(newCertInfo);
	}

	public void setCertStatusAndAmt(String tranTypeCd,CrdBusiCertInfo newCertInfo,CrdBusiCertInfo crdBusiCertInfo){
		if(JxrcbBizConstant.TRAN_TYPE_PRE.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_PRE);
			if(crdBusiCertInfo!=null){
				newCertInfo.setCertApplyAmt(newCertInfo.getCertApplyAmt().add(crdBusiCertInfo.getCertApplyAmt()));
			}
		}else if(JxrcbBizConstant.TRAN_TYPE_PRE_CANCEL.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_INVALID);
			if(crdBusiCertInfo!=null){
				newCertInfo.setCertApplyAmt(crdBusiCertInfo.getCertApplyAmt().subtract(newCertInfo.getCertApplyAmt()));
			}
		}else if(JxrcbBizConstant.TRAN_TYPE_USE.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
		}else if(JxrcbBizConstant.TRAN_TYPE_USE_CANCEL.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_PRE);
		}else if(JxrcbBizConstant.TRAN_TYPE_RESUME.equals(tranTypeCd)){
			if(crdBusiCertInfo!=null){
				newCertInfo.setCertApplyAmt(crdBusiCertInfo.getCertApplyAmt().subtract(newCertInfo.getCertApplyAmt()));
				if(crdBusiCertInfo.getCertApplyAmt().compareTo(newCertInfo.getCertApplyAmt())==0){
					newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_SETTLED);
				}else{
					newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
				}
			}
		}else if(JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL.equals(tranTypeCd)){
			if(crdBusiCertInfo!=null) {
				newCertInfo.setCertApplyAmt(crdBusiCertInfo.getCertApplyAmt().add(newCertInfo.getCertApplyAmt()));
				if (crdBusiCertInfo.getCertApplyAmt().compareTo(newCertInfo.getCertApplyAmt()) == 0) {
					newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_SETTLED);
				} else {
					newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
				}
			}
		}else if(JxrcbBizConstant.TRAN_TYPE_OTHER.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
		}else if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
		}else if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL.equals(tranTypeCd)){
			newCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);

		}

	}

	public void copyProperties(FundEventMain fundEventMain,FundEventDetail source,CrdBusiCertInfo target){
		target.setTranSystem(fundEventMain.getTranSystem());
		target.setCrdDetailPrd(source.getCrdDetailPrd());
		target.setCrdGrantOrgNum(source.getCrdGrantOrgNum());
		target.setCustomerNum(source.getCustomerNum());
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
		target.setCertGuarantyType(source.getCertGuarantyType());
		target.setCertGuarantyPerson(source.getCertGuarantyPerson());
		target.setCertBusiRemark(source.getCertBusiRemark());
		target.setUserNum(fundEventMain.getUserNum());
	}

	public void localProcess(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		changeNullToDecimal(crdMain);
		changeNullToDecimal(crdDetail);
		changeNullToDecimal(provideCrdDetail);
		changeNullToDecimal(provideCrdMain);
		switch(crdApplySerial.getTranTypeCd()){
			case JxrcbBizConstant.TRAN_TYPE_PRE:
				updateForPre(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			case JxrcbBizConstant.TRAN_TYPE_PRE_CANCEL:
				updateForPreCancel(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			case JxrcbBizConstant.TRAN_TYPE_USE:
				updateForUse(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			case JxrcbBizConstant.TRAN_TYPE_USE_CANCEL:
				updateForUseCancel(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			case JxrcbBizConstant.TRAN_TYPE_RESUME:
				updateForResume(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			case JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL:
				updateForResumeCancel(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				break;
			default:break;
		}

	}

	/**
	 * 预占用 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForPre(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 预占用变更：
		 * 针对省联社串用交易：
		 * IF 可用 >= 0 THEN (没发生串用)
		 *   IF 省联社用信金额 > 可用
		 *   THEN 串用已用=用信金额-可用
		 * ELSE IF 可用 < 0 THEN (已发生串用,继续串用)
		 *   串用已用=原串用已用+用信金额
		 *
		 * 预占用=原预占用+用信金额；
		 * 已用不变---------------------------------------------->已用=已用+用信金额；
		 * 可用=授信额度-预占用-已用------------------------------>可用=授信额度-已用；
		 * IF 圈存金额>0 THEN
		 *   圈存已用=圈存已用+用信金额；
		 *   IF 圈存已用 > 圈存金额 THEN 圈存已用 = 圈存金额
		 */
		changeMixUsedForPre(crdDetail,crdApplySerial);
		changeLimitEarmarkUsedForPre(crdDetail,crdApplySerial);
		//修改额度主表预占用、可用
		crdMain.setLimitPre(crdMain.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitUsed((crdMain.getLimitUsed()==null ? new BigDecimal("0"):crdMain.getLimitUsed()).add(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、可用
		crdDetail.setLimitPre(crdDetail.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitUsed((crdDetail.getLimitUsed()==null ? new BigDecimal("0"):crdDetail.getLimitUsed()).add(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){//省联社用信
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		changeLimitEarmarkUsedForPre(provideCrdDetail,crdApplySerial);
		//修改额度主表省联社预占用、可用
		provideCrdMain.setLimitPre(provideCrdMain.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitUsed((provideCrdMain.getLimitUsed()==null ? new BigDecimal("0"):provideCrdMain.getLimitUsed()).add(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、可用
		provideCrdDetail.setLimitPre(provideCrdDetail.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitUsed((provideCrdDetail.getLimitUsed()==null ? new BigDecimal("0"):provideCrdDetail.getLimitUsed()).add(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 预占用圈存已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeLimitEarmarkUsedForPre(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		BigDecimal limitEarmark = crdDetail.getLimitEarmark()==null ? new BigDecimal("0"):crdDetail.getLimitEarmark();
		if(limitEarmark.compareTo(new BigDecimal(0))>0){
			BigDecimal limitEarmarkUsed = crdDetail.getLimitEarmarkUsed()==null ? new BigDecimal("0"):crdDetail.getLimitEarmarkUsed();
			limitEarmarkUsed = limitEarmarkUsed.add(crdApplySerial.getLimitCreditAmt());
			if(limitEarmarkUsed.compareTo(limitEarmark)>0){
				crdDetail.setLimitEarmarkUsed(limitEarmark);
			}else{
				crdDetail.setLimitEarmarkUsed(limitEarmarkUsed);
			}
		}
	}

	/**
	 * 预占用串用已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeMixUsedForPre(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		if(crdDetail.getLimitAvi().compareTo(new BigDecimal(0))>=0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getLimitAvi())>0){
				crdDetail.setMixUsed(crdApplySerial.getLimitCreditAmt().subtract(crdDetail.getLimitAvi()));
			}else{
				BigDecimal mxiUsed = crdDetail.getMixUsed()==null ? new BigDecimal(0):crdDetail.getMixUsed();
				crdDetail.setMixUsed(mxiUsed.add(crdApplySerial.getLimitCreditAmt()));
			}
		}else{
			crdDetail.setMixUsed(crdDetail.getMixUsed().add(crdApplySerial.getLimitCreditAmt()));
		}
	}

	/**
	 * 预占用撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForPreCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 预占用撤销变更：
		 * IF 串用已用>0 THEN
		 *   IF 用信金额<=串用已用
		 *      THEN 串用已用=串用已用-用信金额
		 *   IF 用信金额>串用已用 THEN 串用已用=0
		 * 其他：
		 * 预占用=原预占用-用信金额；
		 * 已用不变----------------------------------------->已用=已用-用信金额；
		 * 可用=授信额度-预占用-已用------------------------->可用=授信额度-已用；
		 *
		 * IF 圈存金额>0 THEN
		 *   圈存已用=圈存已用+用信金额；
		 *   IF 圈存已用 > 圈存金额 THEN 圈存已用 = 圈存金额
		 */
		changeMixUsedForPreCancel(crdDetail,crdApplySerial);
		changeLimitEarmarkUsedForPreCancel(crdDetail,crdApplySerial);
		//修改额度主表预占用、可用
		crdMain.setLimitPre(crdMain.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitUsed((crdMain.getLimitUsed()==null ? new BigDecimal("0"):crdMain.getLimitUsed()).subtract(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、可用
		crdDetail.setLimitPre(crdDetail.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitUsed((crdDetail.getLimitUsed()==null ? new BigDecimal("0"):crdDetail.getLimitUsed()).subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		changeLimitEarmarkUsedForPreCancel(provideCrdDetail,crdApplySerial);
		//修改额度主表省联社预占用、可用
		provideCrdMain.setLimitPre(provideCrdMain.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitUsed((provideCrdMain.getLimitUsed()==null ? new BigDecimal("0"):provideCrdMain.getLimitUsed()).subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、可用
		provideCrdDetail.setLimitPre(provideCrdDetail.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitUsed((provideCrdDetail.getLimitUsed()==null ? new BigDecimal("0"):provideCrdDetail.getLimitUsed()).subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 预占用撤销串用已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeMixUsedForPreCancel(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		if(crdDetail.getMixUsed()!=null && crdDetail.getMixUsed().compareTo(new BigDecimal(0))>0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getMixUsed())>0){
				crdDetail.setMixUsed(new BigDecimal(0));
			}else{
				crdDetail.setMixUsed(crdDetail.getMixUsed().subtract(crdApplySerial.getLimitCreditAmt()));
			}
		}
	}

	/**
	 * 预占用撤销圈存已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeLimitEarmarkUsedForPreCancel(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		BigDecimal limitEarmark = crdDetail.getLimitEarmark()==null ? new BigDecimal("0"):crdDetail.getLimitEarmark();
		if(limitEarmark.compareTo(new BigDecimal(0))>0){
			BigDecimal limitEarmarkUsed = crdDetail.getLimitEarmarkUsed()==null ? new BigDecimal("0"):crdDetail.getLimitEarmarkUsed();
			limitEarmarkUsed = limitEarmarkUsed.subtract(crdApplySerial.getLimitCreditAmt());
			if(limitEarmarkUsed.compareTo(new BigDecimal("0"))>0){
				crdDetail.setLimitEarmarkUsed(limitEarmarkUsed);
			}else{
				crdDetail.setLimitEarmarkUsed(new BigDecimal("0"));
			}
		}
	}

	/**
	 * 占用 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForUse(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		//占用金额<预占用金额时，预占用金额需要进行恢复
		//修改额度主表预占用、已用
		//crdMain.setLimitPre(crdMain.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		//票据占用额度可以比预占用额度小，此时做占用时需对多余预占用进行恢复
		//资金占用额度与预占用额度相同
		crdMain.setLimitPre(crdMain.getLimitPre().subtract(crdDetail.getLimitPre()));
		crdMain.setLimitUsed(crdMain.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、已用
		//crdDetail.setLimitPre(crdDetail.getLimitPre().subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitPre(new BigDecimal("0"));
		crdDetail.setLimitUsed(crdDetail.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitUsed()));
		changeMixUsedForUse(crdDetail);
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		//修改额度主表省联社预占用、已用
		provideCrdMain.setLimitPre(provideCrdMain.getLimitPre().subtract(provideCrdDetail.getLimitPre()));
		provideCrdMain.setLimitUsed(provideCrdMain.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、已用
		provideCrdDetail.setLimitPre(new BigDecimal("0"));
		provideCrdDetail.setLimitUsed(provideCrdDetail.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitUsed()));
		changeMixUsedForUse(provideCrdDetail);
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 占用串用已用修改
	 * @param crdDetail
	 */
	private void changeMixUsedForUse(CrdDetail crdDetail){
		if(crdDetail.getLimitEarmarkUsed()!=null){
			crdDetail.getLimitEarmarkUsed().subtract(crdDetail.getLimitUsed()).add(crdDetail.getLimitUsed());
		}
	}

	/**
	 * 占用撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForUseCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		//修改额度主表预占用、已用
		crdMain.setLimitPre(crdMain.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		//crdMain.setLimitUsed(crdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、已用
		crdDetail.setLimitPre(crdDetail.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		//crdDetail.setLimitUsed(crdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		//修改额度主表省联社预占用、已用
		provideCrdMain.setLimitPre(provideCrdMain.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		//provideCrdMain.setLimitUsed(provideCrdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、已用
		provideCrdDetail.setLimitPre(provideCrdDetail.getLimitPre().add(crdApplySerial.getLimitCreditAmt()));
		//provideCrdDetail.setLimitUsed(provideCrdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 恢复 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForResume(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 恢复变更
		 * IF 串用已用>0 THEN (已发生串用)
		 *   IF 用信金额<=串用已用
		 *      THEN 串用已用=串用已用-用信金额
		 *   IF 用信金额>串用已用 THEN 串用已用=0
		 * 其他：
		 * 预占用不变；
		 * 已用=已用-用信金额；
		 * 可用=授信额度-预占用-已用；
		 * IF 圈存已用>0 THEN
		 *    圈存已用=圈存已用-用信金额
		 *    IF 圈存已用 < 0 THEN 圈存已用=0
		 */
		changeMixUsedForResume(crdDetail,crdApplySerial);
		changeLimitEarmarkUsedForResume(crdDetail,crdApplySerial);
		//修改额度主表可用、已用
		crdMain.setLimitUsed(crdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitPre()).subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表可用、已用
		crdDetail.setLimitUsed(crdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitPre()).subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		changeLimitEarmarkUsedForResume(provideCrdDetail,crdApplySerial);
		//修改额度主表省联社可用、已用
		provideCrdMain.setLimitUsed(provideCrdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitPre()).subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社可用、已用
		provideCrdDetail.setLimitUsed(provideCrdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitPre()).subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 恢复圈存已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeLimitEarmarkUsedForResume(CrdDetail crdDetail,CrdApplySerial crdApplySerial) {
		changeLimitEarmarkUsedForPreCancel( crdDetail,crdApplySerial);
	}

	/**
	 * 恢复串用已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeMixUsedForResume(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		if(crdDetail.getMixUsed()!=null && crdDetail.getMixUsed().compareTo(new BigDecimal(0))>0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getMixUsed())>0){
				crdDetail.setMixUsed(new BigDecimal(0));
			}else{
				crdDetail.setMixUsed(crdDetail.getMixUsed().subtract(crdApplySerial.getLimitCreditAmt()));
			}
		}
	}

	/**
	 * 恢复撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	public void updateForResumeCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 恢复撤销变更：
		 * IF 可用 >= 0 THEN (没发生串用)
		 *   IF 省联社用信金额 > 可用
		 *   THEN 串用已用=用信金额-可用
		 * ELSE IF 可用 < 0 THEN (已发生串用,继续串用)
		 *   串用已用=原串用已用+用信金额
		 *
		 * 预占用不变；
		 * 已用=已用+用信金额；
		 * 可用=授信额度-预占用-已用；
		 */
		changeMixUsedForResumeCancel( crdDetail, crdApplySerial);
		changeLimitEarmarkUsedForResumeCancle( crdDetail, crdApplySerial);
		//修改额度主表可用、已用
		crdMain.setLimitUsed(crdMain.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitPre()).subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表可用、已用
		crdDetail.setLimitUsed(crdDetail.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitPre()).subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		changeLimitEarmarkUsedForResumeCancle( provideCrdDetail, crdApplySerial);
		//修改额度主表省联社可用、已用
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitAvi().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitPre()).subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社可用、已用
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitAvi().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitPre()).subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	/**
	 * 恢复撤销圈存已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeLimitEarmarkUsedForResumeCancle(CrdDetail crdDetail,CrdApplySerial crdApplySerial) {
		changeLimitEarmarkUsedForPre( crdDetail,crdApplySerial);
	}

	/**
	 * 恢复撤销串用已用修改
	 * @param crdDetail
	 * @param crdApplySerial
	 */
	private void changeMixUsedForResumeCancel(CrdDetail crdDetail,CrdApplySerial crdApplySerial){
		if(crdDetail.getLimitAvi().compareTo(new BigDecimal(0))>=0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getLimitAvi())>0){
				crdDetail.setMixUsed(crdApplySerial.getLimitCreditAmt().subtract(crdDetail.getLimitAvi()));
			}else{
				BigDecimal mxiUsed = crdDetail.getMixUsed()==null ? new BigDecimal(0):crdDetail.getMixUsed();
				crdDetail.setMixUsed(mxiUsed.add(crdApplySerial.getLimitCreditAmt()));
			}
		}else{
			crdDetail.setMixUsed(crdDetail.getMixUsed().add(crdApplySerial.getLimitCreditAmt()));
		}
	}

	public void changeNullToDecimal(CrdMain crdMain){
		crdMain.setLimitUsed(crdMain.getLimitUsed()==null ? new BigDecimal(0):crdMain.getLimitUsed());
		crdMain.setLimitPre(crdMain.getLimitPre()==null ? new BigDecimal(0):crdMain.getLimitPre());
	}

	public void changeNullToDecimal(CrdDetail crdDetail){
		crdDetail.setLimitUsed(crdDetail.getLimitUsed()==null ? new BigDecimal(0):crdDetail.getLimitUsed());
		crdDetail.setLimitPre(crdDetail.getLimitPre()==null ? new BigDecimal(0):crdDetail.getLimitPre());
	}

	@Override
	public void updateForDirectUse(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 占用变更：
		 * 针对省联社串用交易：
		 * IF 可用 >= 0 THEN (没发生串用)
		 *   IF 省联社用信金额 > 可用
		 *   THEN 串用已用=用信金额-可用
		 * ELSE IF 可用 < 0 THEN (已发生串用,继续串用)
		 *   串用已用=原串用已用+用信金额
		 *
		 * 已用=原占用+用信金额；
		 * 可用=授信额度-预占用-已用；
		 */
		if(crdMain.getLimitPre()==null){
			crdMain.setLimitPre(new BigDecimal("0"));
		}
		if(crdDetail.getLimitPre()==null){
			crdDetail.setLimitPre(new BigDecimal("0"));
		}
		if(crdDetail.getLimitAvi().compareTo(new BigDecimal(0))>=0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getLimitAvi())>0){
				crdDetail.setMixUsed(crdApplySerial.getLimitCreditAmt().subtract(crdDetail.getLimitAvi()));
			}else{
				BigDecimal mxiUsed = crdDetail.getMixUsed()==null ? new BigDecimal(0):crdDetail.getMixUsed();
				crdDetail.setMixUsed(mxiUsed.add(crdApplySerial.getLimitCreditAmt()));
			}
		}else{
			crdDetail.setMixUsed(crdDetail.getMixUsed().add(crdApplySerial.getLimitCreditAmt()));
		}
		//修改额度主表预占用、可用
		crdMain.setLimitUsed(crdMain.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitPre()).subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、可用
		crdDetail.setLimitUsed(crdDetail.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitPre()).subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){//省联社用信
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		if(provideCrdDetail.getLimitPre()==null){
			provideCrdDetail.setLimitPre(new BigDecimal("0"));
		}
		if(provideCrdMain.getLimitPre()==null){
			provideCrdMain.setLimitPre(new BigDecimal("0"));
		}
		//修改额度主表省联社预占用、可用
		provideCrdMain.setLimitUsed(provideCrdMain.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitPre()).subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、可用
		provideCrdDetail.setLimitUsed(provideCrdDetail.getLimitUsed().add(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitPre()).subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	@Override
	public void updateForDirectUseCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now){
		/**
		 * 占用撤销变更：
		 * 针对省联社串用交易：
		 * IF 串用已用>0 THEN (已发生串用)
		 *   IF 用信金额<=串用已用
		 *      THEN 串用已用=串用已用-用信金额
		 *   IF 用信金额>串用已用 THEN 串用已用=0
		 * 其他：
		 * 已用=原已用-用信金额；
		 * 可用=授信额度-预占用-已用；
		 */
		if(crdDetail.getMixUsed()!=null && crdDetail.getMixUsed().compareTo(new BigDecimal(0))>0){
			if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getMixUsed())>0){
				crdDetail.setMixUsed(new BigDecimal(0));
			}else{
				crdDetail.setMixUsed(crdDetail.getMixUsed().subtract(crdApplySerial.getLimitCreditAmt()));
			}
		}
		//修改额度主表预占用、可用
		crdMain.setLimitUsed(crdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdMain.setLimitAvi(crdMain.getLimitCredit().subtract(crdMain.getLimitPre()).subtract(crdMain.getLimitUsed()));
		crdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(crdMain);
		//修改额度明细表预占用、可用
		crdDetail.setLimitUsed(crdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		crdDetail.setLimitAvi(crdDetail.getLimitCredit().subtract(crdDetail.getLimitPre()).subtract(crdDetail.getLimitUsed()));
		crdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(crdDetail);
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//省联社用信时，防止重复修改省联社用信数据
		}
		//修改额度主表省联社预占用、可用
		provideCrdMain.setLimitUsed(provideCrdMain.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdMain.setLimitAvi(provideCrdMain.getLimitCredit().subtract(provideCrdMain.getLimitPre()).subtract(provideCrdMain.getLimitUsed()));
		provideCrdMain.setUpdateTime(now);
		crdMainService.saveOrUpdate(provideCrdMain);
		//修改额度明细表省联社预占用、可用
		provideCrdDetail.setLimitUsed(provideCrdDetail.getLimitUsed().subtract(crdApplySerial.getLimitCreditAmt()));
		provideCrdDetail.setLimitAvi(provideCrdDetail.getLimitCredit().subtract(provideCrdDetail.getLimitPre()).subtract(provideCrdDetail.getLimitUsed()));
		provideCrdDetail.setUpdateTime(now);
		crdDetailService.saveOrUpdate(provideCrdDetail);
	}

	@Override
	@Async
	public void customerHanle(List<CrdApplyInfoRequestDTO> crdApplyInfos){
		for (CrdApplyInfoRequestDTO crdApplyInfo:crdApplyInfos) {
			//客户信息查看同步
			CsmParty tbCsmParty = csmPartyService.getById(crdApplyInfo.getCustomerNum());
			//TODO 调用ecif
			if(tbCsmParty == null){

			}
		}
	}
}
