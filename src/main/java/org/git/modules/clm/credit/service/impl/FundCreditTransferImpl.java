package org.git.modules.clm.credit.service.impl;

import org.git.common.exception.AssertUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.credit.entity.FundTransferIn;
import org.git.modules.clm.credit.entity.FundTransferMain;
import org.git.modules.clm.credit.entity.FundTransferOut;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditTransferDTO;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdInInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdOutInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditOccupancyRequestDTO;
import org.git.modules.clm.credit.vo.CustomerAndPrdVO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FundCreditTransferImpl implements IFundCreditTransfer {
	@Autowired
	private IFundTransferMainService fundTransferMainService;
	@Autowired
	private IFundTransferOutService fundTransferOutService;
	@Autowired
	private IFundTransferInService fundTransferInService;
	@Autowired
	private ICrdApplySerialService crdApplySerialService;
	@Autowired
	private ICrdDetailService crdDetailService;
	@Autowired
	private ICrdMainService crdMainService;
	@Autowired
	private ICrdBusiCertInfoService crdBusiCertInfoService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IFundCheckService fundCheckService;
	@Autowired
	private IFundCreditUseService fundCreditUseService;
	@Autowired
	private IParCrdRuleCtrlService parCrdRuleCtrlService;

	@Transactional
	@Override
	public FundCreditTransferDTO registerBusiEvent(FundCreditOccupancyRequestDTO fundCreditOccupancy, ExtAttributes extAttributes){
		//金额平衡检查
		checkBalance(fundCreditOccupancy);
		//重复交易事件检查
		checkRepeatEvent(fundCreditOccupancy,extAttributes);
		//主事件登记
		FundTransferMain fundTransferMain = new FundTransferMain();
		fundTransferMain.setEventMainId(UUID.randomUUID().toString().replaceAll("-",""));
		fundTransferMain.setTranDirection(fundCreditOccupancy.getTranDirection());
		fundTransferMain.setTranSeqSn(extAttributes.getOriReqSn());
		fundTransferMain.setTranDate(extAttributes.getOriReqDate());
		fundTransferMain.setBusiDealNum(fundCreditOccupancy.getBusiDealNum());
		fundTransferMain.setTranTypeCd(fundCreditOccupancy.getTranTypeCd());
		fundTransferMain.setCrdApplyAmt(CommonUtil.stringToBigDecimal(fundCreditOccupancy.getBusiSumAmt()));
		fundTransferMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);
		fundTransferMain.setTranAcctStatus(JxrcbBizConstant.TRAN_ACCT_STATUS_UNPROCES);
		fundTransferMain.setCrdCurrencyCd(fundCreditOccupancy.getTranTypeCd());
		fundTransferMain.setTranSystem(extAttributes.getRequesterId());
		fundTransferMain.setUserNum(extAttributes.getOperatorId());
		List<CrdOutInfoRequestDTO> crdOutInfos = fundCreditOccupancy.getCrdOutInfo();
		List<FundTransferOut> fundTransferOuts = new ArrayList<>();
		//转出事件登记
		for (CrdOutInfoRequestDTO crdOutInfo:crdOutInfos) {
			FundTransferOut fundTransferOut = new FundTransferOut();
			fundTransferOut.setTransferOutId(UUID.randomUUID().toString().replaceAll("-",""));
			fundTransferOut.setEventMainId(fundTransferMain.getEventMainId());
			fundTransferOut.setTranSeqSn(fundTransferMain.getTranSeqSn());
			fundTransferOut.setTranDate(fundTransferMain.getTranDate());
			fundTransferOut.setCrdOutOrgNum(crdOutInfo.getCrdOutOrgNum());
			fundTransferOut.setBusiSourceReqNum(crdOutInfo.getBusiSourceReqNum());
			fundTransferOut.setCurrencyCd(crdOutInfo.getCurrencyCd());
			fundTransferOut.setCrdApplyOutAmt(CommonUtil.stringToBigDecimal(crdOutInfo.getCrdApplyOutAmt()));
			fundTransferOuts.add(fundTransferOut);
		}
		//转入事件登记
		List<CrdInInfoRequestDTO> crdInInfos = fundCreditOccupancy.getCrdInInfo();
		List<FundTransferIn> fundTransferIns = new ArrayList<>();
		for (CrdInInfoRequestDTO crdInInfo:crdInInfos) {
			FundTransferIn fundTransferIn = new FundTransferIn();
			fundTransferIn.setTransferInId(UUID.randomUUID().toString().replaceAll("-",""));
			fundTransferIn.setEventMainId(fundTransferMain.getEventMainId());
			fundTransferIn.setTranSeqSn(fundTransferMain.getTranSeqSn());
			fundTransferIn.setTranDate(fundTransferMain.getTranDate());
			fundTransferIn.setCrdInOrgNum(crdInInfo.getCrdInOrgNum());
			fundTransferIn.setBusiNewlReqNum(crdInInfo.getBusiNewReqNum());
			fundTransferIn.setCurrencyCd(crdInInfo.getCurrencyCd());
			fundTransferIn.setCrdApplyInAmt(CommonUtil.stringToBigDecimal(crdInInfo.getCrdApplyInAmt()));
			fundTransferIns.add(fundTransferIn);
		}
		fundTransferMainService.save(fundTransferMain);
		fundTransferOutService.saveBatch(fundTransferOuts);
		fundTransferInService.saveBatch(fundTransferIns);
		FundCreditTransferDTO fundCreditTransferDTO = new FundCreditTransferDTO();
		fundCreditTransferDTO.setFundTransferMain(fundTransferMain);
		fundCreditTransferDTO.setFundTransferIns(fundTransferIns);
		fundCreditTransferDTO.setFundTransferOuts(fundTransferOuts);
		return fundCreditTransferDTO;
	}

	public void checkBalance(FundCreditOccupancyRequestDTO fundCreditOccupancy){
		List<CrdOutInfoRequestDTO> crdOutInfos = fundCreditOccupancy.getCrdOutInfo();
		if(crdOutInfos==null || crdOutInfos.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20401,"转出额度不能为空！");
		}
		List<CrdInInfoRequestDTO> crdInInfos = fundCreditOccupancy.getCrdInInfo();
		if(crdInInfos==null || crdInInfos.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20402,"转入额度不能为空！");
		}
		BigDecimal outAmt = new BigDecimal(0);
		BigDecimal inAmt = new BigDecimal(0);
		for (CrdOutInfoRequestDTO crdOutInfo:crdOutInfos) {
			outAmt = outAmt.add(CommonUtil.stringToBigDecimal(crdOutInfo.getCrdApplyOutAmt()));
		}
		for (CrdInInfoRequestDTO crdInInfo:crdInInfos) {
			inAmt = inAmt.add(CommonUtil.stringToBigDecimal(crdInInfo.getCrdApplyInAmt()));
		}
		if(outAmt.compareTo(CommonUtil.stringToBigDecimal(fundCreditOccupancy.getBusiSumAmt()))!=0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20403,"交易金额与转出金额不相等！");
		}
		if(outAmt.compareTo(inAmt)!=0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20404,"转入金额与转出金额不相等！");
		}
	}

	/**
	 * 防重检查
	 */
	public void checkRepeatEvent(FundCreditOccupancyRequestDTO fundCreditOccupancy, ExtAttributes extAttributes){
		FundTransferMain fundTransferMain = new FundTransferMain();
		fundTransferMain.setTranDate(extAttributes.getOriReqDate());//交易日期
		fundTransferMain.setBusiDealNum(fundCreditOccupancy.getBusiDealNum());//业务编号
		fundTransferMain.setTranTypeCd(fundCreditOccupancy.getTranTypeCd());//交易类型
		fundTransferMain.setCrdApplyAmt(new BigDecimal(fundCreditOccupancy.getBusiSumAmt()));//交易金额
		fundTransferMain.setTranAcctStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		int count = fundTransferMainService.count(Condition.getQueryWrapper(fundTransferMain));
		if(count>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20405,"该交易已处理！");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void middleHandle(FundCreditTransferDTO fundCreditTransferDTO, ExtAttributes extAttributes,String eventTypeCd){
		ParCrdRuleCtrl param = new ParCrdRuleCtrl();
		param.setEventTypeCd(eventTypeCd);
		param.setTranTypeCd(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE);
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
		param.setTranTypeCd(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL);
		List<ParCrdRuleCtrl> oppositeRules = parCrdRuleCtrlService.list(Condition.getQueryWrapper(param));
		List<ParCrdRuleCtrl> oppositeCertRules = new ArrayList<>();//凭证信息规则
		List<ParCrdRuleCtrl> oppositeNotCertRules = new ArrayList<>();//非凭证信息规则
		if(rules!=null && !oppositeRules.isEmpty()){
			for (int i=0;i<oppositeRules.size();i++) {
				if(oppositeRules.get(i).getCheckMethod().equals(JxrcbBizConstant.METHOD_CHECKCERTSTATUS)
					|| oppositeRules.get(i).getCheckMethod().equals(JxrcbBizConstant.METHOD_COUNTDATEVALID)
				){
					oppositeCertRules.add(oppositeRules.get(i));
				}else{
					oppositeNotCertRules.add(oppositeRules.get(i));
				}
			}
		}
		Timestamp now = CommonUtil.getWorkDateTime();
		String tranDirection = fundCreditTransferDTO.getFundTransferMain().getTranDirection();
		List<FundTransferOut> fundTransferOuts = fundCreditTransferDTO.getFundTransferOuts();
		List<FundTransferIn> fundTransferIns = fundCreditTransferDTO.getFundTransferIns();
		if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
			List<CustomerAndPrdVO> customerAndPrds = outEventSplit(certRules,notCertRules,fundTransferOuts,extAttributes,tranDirection,now);

			inEventSplit(oppositeCertRules,oppositeNotCertRules,fundTransferIns,customerAndPrds,extAttributes,tranDirection,now);
		}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
			List<CustomerAndPrdVO> customerAndPrds = outEventSplit(oppositeCertRules,oppositeNotCertRules,fundTransferOuts,extAttributes,tranDirection,now);
			inEventSplit(certRules,notCertRules,fundTransferIns,customerAndPrds,extAttributes,tranDirection,now);
		}
	}

	public void inEventSplit(List<ParCrdRuleCtrl> certRules,List<ParCrdRuleCtrl> notCertRules,List<FundTransferIn> fundTransferIns,List<CustomerAndPrdVO> customerAndPrds , ExtAttributes extAttributes,String tranDirection,Timestamp now){
		if(fundTransferIns==null || fundTransferIns.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20407,"额度转让-转入数组不能为空！");
		}
		for (FundTransferIn fundTransferIn: fundTransferIns) {
			for (CustomerAndPrdVO customerAndPrd:customerAndPrds) {
				CrdBusiCertInfo outCrdBusiCertInfo=customerAndPrd.getCrdBusiCertInfo();
				CrdBusiCertInfo infoParams = new CrdBusiCertInfo();
				infoParams.setCertNum(outCrdBusiCertInfo.getCertNum());
				infoParams.setCustomerNum(outCrdBusiCertInfo.getCustomerNum());
				infoParams.setCrdDetailPrd(outCrdBusiCertInfo.getCrdDetailPrd());
				infoParams.setCrdGrantOrgNum(fundTransferIn.getCrdInOrgNum());
				infoParams.setBusiDealNum(fundTransferIn.getBusiNewlReqNum());
				CrdBusiCertInfo crdBusiCertInfo = crdBusiCertInfoService.getOne(Condition.getQueryWrapper(infoParams));
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
					if(crdBusiCertInfo==null){
						crdBusiCertInfo = new CrdBusiCertInfo();
						crdBusiCertInfo = outCrdBusiCertInfo;
						crdBusiCertInfo.setCretInfoId(UUID.randomUUID().toString().replaceAll("-",""));
						crdBusiCertInfo.setBusiDealNum(fundTransferIn.getBusiNewlReqNum());
						crdBusiCertInfo.setCrdGrantOrgNum(fundTransferIn.getCrdInOrgNum());
						crdBusiCertInfo.setCustomerNum(customerAndPrd.getCustomerNum());
						crdBusiCertInfo.setCrdDetailPrd(customerAndPrd.getCrdDetailPrd());
						crdBusiCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_INVALID);
					}
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					if(crdBusiCertInfo==null){
						AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20409,"未获取到相应的凭证信息！");
					}else{
						crdBusiCertInfo.setCertApplyAmt(crdBusiCertInfo.getCertApplyAmt().add(fundTransferIn.getCrdApplyInAmt()));
					}
				}
				String tran_type = null;
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
					tran_type = JxrcbBizConstant.TRAN_TYPE_DIRECT_USE;
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					tran_type = JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL;
				}
				//插入流水
				CrdApplySerial crdApplySerial = addInSerial(fundTransferIn, crdBusiCertInfo, tran_type, now, extAttributes);
				fundCheckService.midCheck(certRules,null,crdApplySerial,null,null,crdBusiCertInfo,null,null);
				crdApplySerialService.save(crdApplySerial);
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
					crdBusiCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
					crdBusiCertInfoService.saveOrUpdate(crdBusiCertInfo);
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					crdBusiCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_SETTLED);
					crdBusiCertInfoService.saveOrUpdate(crdBusiCertInfo);
				}
				if(JxrcbBizConstant.CRD_DETAIL_PRD_03019999.equals(crdBusiCertInfo.getCrdDetailPrd())){
					continue;
				}
				CrdDetail params = new CrdDetail();
				params.setCustomerNum(customerAndPrd.getCustomerNum());
				params.setCrdGrantOrgNum(fundTransferIn.getCrdInOrgNum());
				params.setCrdDetailPrd(customerAndPrd.getCrdDetailPrd());
				//查询当前机构的明细额度
				CrdDetail crdDetail = crdDetailService.getOne(Condition.getQueryWrapper(params));
				if (crdDetail == null) {
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20406,
						fundTransferIn.getCrdInOrgNum() + "机构未对客户" + customerAndPrd.getCustomerNum() + "进行产品额度切分");
				}
				CrdMain crdMain = crdMainService.getById(crdDetail.getCrdMainNum());
				Dept provideDept = deptService.upperDeptByType(fundTransferIn.getCrdInOrgNum(), DeptConstant.PROVINCIAL_COOPERATIVE);
				if(provideDept == null){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20411,"未查询到"+fundTransferIn.getCrdInOrgNum()+"的省联社机构");
				}
				CrdMain provideCrdMain = null;
				CrdDetail provideCrdDetail = null;
				if(provideDept.getId().equals(fundTransferIn.getCrdInOrgNum())){
					provideCrdMain = crdMain;
					provideCrdDetail = crdDetail;
				}else{
					CrdDetail provideDetailParams = new CrdDetail();
					provideDetailParams.setCrdGrantOrgNum(provideDept.getId());
					provideDetailParams.setCrdDetailPrd(crdDetail.getCrdDetailPrd());
					provideDetailParams.setCustomerNum(crdDetail.getCustomerNum());
					provideCrdDetail = crdDetailService.getOne(Condition.getQueryWrapper(provideDetailParams));
					provideCrdMain = crdMainService.getById(provideCrdDetail.getCrdMainNum());
				}
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain);
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
					fundCreditUseService.updateForDirectUse(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					fundCreditUseService.updateForDirectUseCancel(crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain,now);
				}

			}
		}
	}

	public CrdApplySerial addInSerial(FundTransferIn fundTransferIn,CrdBusiCertInfo crdBusiCertInfo,String tranTypeCd,Timestamp now, ExtAttributes extAttributes){
		CrdApplySerial crdApplySerial = new CrdApplySerial();
		crdApplySerial.setSerialId(UUID.randomUUID().toString().replaceAll("-",""));
		crdApplySerial.setTranSeqSn(fundTransferIn.getTranSeqSn());
		crdApplySerial.setTranDate(fundTransferIn.getTranDate());
		crdApplySerial.setBusiDealNum(fundTransferIn.getBusiNewlReqNum());
		crdApplySerial.setTranTypeCd(tranTypeCd);
		crdApplySerial.setCrdDetailNum(crdBusiCertInfo.getCrdDetailNum());
		crdApplySerial.setCrdGrantOrgNum(fundTransferIn.getCrdInOrgNum());
		crdApplySerial.setCustomerNum(crdBusiCertInfo.getCustomerNum());
		crdApplySerial.setCrdDetailPrd(crdBusiCertInfo.getCrdDetailPrd());
		crdApplySerial.setLimitCreditAmt(fundTransferIn.getCrdApplyInAmt());
		crdApplySerial.setCurrencyCd(crdBusiCertInfo.getCertCurrencyCd());
		crdApplySerial.setTranSystem(extAttributes.getRequesterId());
		crdApplySerial.setOrgNum(extAttributes.getBranchId());
		crdApplySerial.setUserNum(extAttributes.getOperatorId());
		crdApplySerial.setCreateTime(now);
		crdApplySerial.setUpdateTime(now);
		return crdApplySerial;
	}

	public List<CustomerAndPrdVO> outEventSplit(List<ParCrdRuleCtrl> certRules,List<ParCrdRuleCtrl> notCertRules,List<FundTransferOut> fundTransferOuts, ExtAttributes extAttributes,String tranDirection,Timestamp now){
		if(fundTransferOuts==null || fundTransferOuts.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20408,"额度转让-转出数组不能为空！");
		}
		List<CustomerAndPrdVO> customerAndPrdVOS = new ArrayList<>();
		for (int i=0;i<fundTransferOuts.size();i++) {
			FundTransferOut fundTransferOut = fundTransferOuts.get(i);
			CrdBusiCertInfo certParams = new CrdBusiCertInfo();
			certParams.setBusiDealNum(fundTransferOut.getBusiSourceReqNum());
			List<CrdBusiCertInfo> crdBusiCertInfos = crdBusiCertInfoService.list(Condition.getQueryWrapper(certParams));
			if(crdBusiCertInfos==null || crdBusiCertInfos.isEmpty()){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20409,"未获取到业务编号为"+fundTransferOut.getBusiSourceReqNum()+"的凭证信息！");
			}
			//保证多次转出方的客户、产品一致
			for (CrdBusiCertInfo crdBusiCertInfo:crdBusiCertInfos) {
				if(i==0){//第一个业务编号对应的客户和产品
					CustomerAndPrdVO customerAndPrdVO = new CustomerAndPrdVO();
					customerAndPrdVO.setCrdDetailPrd(crdBusiCertInfo.getCrdDetailPrd());
					customerAndPrdVO.setCustomerNum(crdBusiCertInfo.getCustomerNum());
					customerAndPrdVO.setCrdBusiCertInfo(crdBusiCertInfo);
					customerAndPrdVOS.add(customerAndPrdVO);
				}else{//判断第二个业务编号及后面的业务编号所对应的客户、产品是否一致
					CustomerAndPrdVO customerAndPrdVO = new CustomerAndPrdVO();
					customerAndPrdVO.setCrdDetailPrd(crdBusiCertInfo.getCrdDetailPrd());
					customerAndPrdVO.setCustomerNum(crdBusiCertInfo.getCustomerNum());
					boolean flag = true;
					for (CustomerAndPrdVO customerAndPrd:customerAndPrdVOS) {
						if(customerAndPrd.equals(customerAndPrdVO)){
							flag = false;
						}
					}
					if(flag){
						AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20410,"额度转让数组转让客户产品不一致");
					}
				}
				String tran_type = null;
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){
					tran_type = JxrcbBizConstant.TRAN_TYPE_RESUME;
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					tran_type = JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL;
				}

				//插入流水
				CrdApplySerial crdApplySerial=addOutSerial(fundTransferOut,crdBusiCertInfo, tran_type, now, extAttributes);
				crdApplySerialService.save(crdApplySerial);

				fundCheckService.midCheck(certRules,null,crdApplySerial,null,null,crdBusiCertInfo,null,null);
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)) {
					BigDecimal certApplyAmt = crdBusiCertInfo.getCertApplyAmt()==null ? new BigDecimal("0"):crdBusiCertInfo.getCertApplyAmt();
					BigDecimal applyOutAmt = fundTransferOut.getCrdApplyOutAmt()==null ? new BigDecimal("0"):fundTransferOut.getCrdApplyOutAmt();
					if(certApplyAmt.compareTo(applyOutAmt)==0){
						crdBusiCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_SETTLED);
					}
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					crdBusiCertInfo.setCertStatus(JxrcbBizConstant.CERT_STATUS_USED);
				}
				crdBusiCertInfoService.saveOrUpdate(crdBusiCertInfo);
				if(JxrcbBizConstant.CRD_DETAIL_PRD_03019999.equals(crdBusiCertInfo.getCrdDetailPrd())){
					continue;
				}
				CrdDetail crdDetail = crdDetailService.getById(crdBusiCertInfo.getCrdDetailNum());
				CrdMain crdMain = crdMainService.getById(crdDetail.getCrdMainNum());
				Dept provideDept = deptService.upperDeptByType(fundTransferOut.getCrdOutOrgNum(), DeptConstant.PROVINCIAL_COOPERATIVE);
				if(provideDept == null){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20411,"未查询到"+fundTransferOut.getCrdOutOrgNum()+"的省联社机构");
				}
				CrdMain provideCrdMain = null;
				CrdDetail provideCrdDetail = null;
				if(provideDept.getId().equals(fundTransferOut.getCrdOutOrgNum())){
					provideCrdMain = crdMain;
					provideCrdDetail = crdDetail;
				}else{
					CrdDetail provideDetailParams = new CrdDetail();
					provideDetailParams.setCrdGrantOrgNum(provideDept.getId());
					provideDetailParams.setCrdDetailPrd(crdDetail.getCrdDetailPrd());
					provideDetailParams.setCustomerNum(crdDetail.getCustomerNum());
					provideCrdDetail = crdDetailService.getOne(Condition.getQueryWrapper(provideDetailParams));
					provideCrdMain = crdMainService.getById(provideCrdDetail.getCrdMainNum());
				}
				fundCheckService.midCheck(notCertRules,null,crdApplySerial,crdMain,crdDetail,crdBusiCertInfo,provideCrdDetail,provideCrdMain);
				if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)) {
					fundCreditUseService.updateForResume(crdApplySerial, crdMain, crdDetail, crdBusiCertInfo, provideCrdDetail, provideCrdMain, now);
				}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
					fundCreditUseService.updateForResumeCancel(crdApplySerial, crdMain, crdDetail, crdBusiCertInfo, provideCrdDetail, provideCrdMain, now);
				}
			}
		}
		return customerAndPrdVOS;
	}

	public CrdApplySerial addOutSerial(FundTransferOut fundTransferOut,CrdBusiCertInfo crdBusiCertInfo,String tranTypeCd,Timestamp now, ExtAttributes extAttributes){
		CrdApplySerial crdApplySerial = new CrdApplySerial();
		crdApplySerial.setSerialId(UUID.randomUUID().toString().replaceAll("-",""));
		crdApplySerial.setTranSeqSn(fundTransferOut.getTranSeqSn());
		crdApplySerial.setTranDate(fundTransferOut.getTranDate());
		crdApplySerial.setBusiDealNum(fundTransferOut.getBusiSourceReqNum());
		crdApplySerial.setTranTypeCd(tranTypeCd);
		crdApplySerial.setCrdDetailNum(crdBusiCertInfo.getCrdDetailNum());
		crdApplySerial.setCrdGrantOrgNum(fundTransferOut.getCrdOutOrgNum());
		crdApplySerial.setCustomerNum(crdBusiCertInfo.getCustomerNum());
		crdApplySerial.setCrdDetailPrd(crdBusiCertInfo.getCrdDetailPrd());
		crdApplySerial.setLimitCreditAmt(fundTransferOut.getCrdApplyOutAmt());
		crdApplySerial.setCurrencyCd(crdBusiCertInfo.getCertCurrencyCd());
		crdApplySerial.setTranSystem(extAttributes.getRequesterId());
		crdApplySerial.setOrgNum(extAttributes.getBranchId());
		crdApplySerial.setUserNum(extAttributes.getOperatorId());
		crdApplySerial.setCreateTime(now);
		crdApplySerial.setUpdateTime(now);
		return crdApplySerial;
	}
}
