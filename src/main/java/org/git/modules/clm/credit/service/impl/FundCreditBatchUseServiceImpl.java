package org.git.modules.clm.credit.service.impl;

import org.git.common.exception.AssertUtil;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.credit.entity.FundEventDetail;
import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.BusiInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.CertInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdApplyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundBatchRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FundCreditBatchUseServiceImpl implements IFundCreditBatchUseService {
	@Autowired
	private IFundCreditUseService fundCreditUseService;
	@Autowired
	private IFundEventMainService fundEventMainService;
	@Autowired
	private IFundEventDetailService fundEventDetailService;
	@Autowired
	private IFundCheckService fundCheckService;

	@Override
	@Transactional
	public List<FundEventMain> registerBusiEvent(FundBatchRequestDTO fundBatchRequestDTO, ExtAttributes extAttributes){
		List<BusiInfoRequestDTO> busiInfos=fundBatchRequestDTO.getBusiInfo();
		if(busiInfos==null || busiInfos.isEmpty()){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20601,"用信数组不能为空");
		}
		List<FundEventDetail> toAddDetails = new ArrayList<>();
		List<FundEventMain> toAddMains = new ArrayList<>();
		List<String> eventMainIds = new ArrayList<>();
		for (BusiInfoRequestDTO busiInfo: busiInfos) {
			//插入事件主表及详细表
			FundEventMain fundEventMain = new FundEventMain();//
			fundEventMain.setTranSeqSn(extAttributes.getOriReqSn());//交易流水号
			fundEventMain.setTranDate(extAttributes.getOriReqDate());//交易日期
			fundEventMain.setBusiDealNum(busiInfo.getBusiDealNum());//业务编号
			fundEventMain.setTranTypeCd(busiInfo.getTranTypeCd());//交易类型
			fundEventMain.setCrdApplyAmt(CommonUtil.stringToBigDecimal(busiInfo.getCrdApplyAmt()));//用信金额
			fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);//本方处理状态
			fundEventMain.setTranAcctStatus(JxrcbBizConstant.TRAN_ACCT_STATUS_UNPROCES);//对方处理状态
			fundEventMain.setEventMainId(UUID.randomUUID().toString().replaceAll("-",""));//事件主表ID
			eventMainIds.add(fundEventMain.getEventMainId());
			toAddMains.add(fundEventMain);
			List<CrdApplyInfoRequestDTO> CrdApplyInfos = busiInfo.getCrdApplyInfo();
			for (CrdApplyInfoRequestDTO certApplyInfo: CrdApplyInfos) {
				FundEventDetail fundEventDetail = new FundEventDetail();
				fundEventDetail.setEventMainId(fundEventMain.getEventMainId());//事件主表ID
				fundEventDetail.setTranSeqSn(extAttributes.getOriReqSn());//交易流水号
				fundEventDetail.setTranDate(extAttributes.getOriReqDate());	//	交易日期
				fundEventDetail.setCrdGrantOrgNum(certApplyInfo.getCrdGrantOrgNum());//授信机构
				fundEventDetail.setCustomerNum(certApplyInfo.getCustomerNum());//用信客户号
				fundEventDetail.setCrdDetailPrd(certApplyInfo.getCrdDetailPrd());//明细额度产品
				fundEventDetail.setBusiDealNum(busiInfo.getBusiDealNum());//业务编号
				fundEventDetail.setBusiPrdNum(certApplyInfo.getBusiPrdNum());//业务产品编号
				fundEventDetail.setBusiDealDesc(certApplyInfo.getBusiDealDesc());	//业务交易描述
				fundEventDetail.setBusiDealOrgNum(certApplyInfo.getBusiDealOrgNum());//本方机构
				fundEventDetail.setBusiDealOrgName(certApplyInfo.getBusiDealOrgName());//	本方机构名称
				fundEventDetail.setBusiOpptOrgNum(certApplyInfo.getBusiOpptOrgNum());//对手机构
				fundEventDetail.setBusiDealOrgName(certApplyInfo.getBusiDealOrgName());//	对手机构名称
				fundEventDetail.setBusiSumAmt(CommonUtil.stringToBigDecimal(certApplyInfo.getBusiSumAmt() ));//交易总金额
				fundEventDetail.setBusiCertCnt(CommonUtil.stringToBigDecimal(certApplyInfo.getBusiCertCnt()));//
				for (CertInfoRequestDTO certInfo: certApplyInfo.getCertInfo()) {
					fundCheckService.checkCertInfo(fundEventMain.getTranTypeCd(),certInfo);
					FundEventDetail toAddDetail = new FundEventDetail();
					BeanUtils.copyProperties(fundEventDetail,toAddDetail);
					toAddDetail.setEventDetailedId(UUID.randomUUID().toString().replaceAll("-",""));//事件详情细ID
					toAddDetail.setCertNum(certInfo.getCertNum());//凭证编号  cert_num
					toAddDetail.setCertTypeCd(certInfo.getCertTypeCd());	//	凭证品种  cert_type_cd
					toAddDetail.setCertPptCd(certInfo.getCertPptCd());	//凭证性质  cert_ppt_cd
					toAddDetail.setCertInterestPeriod(certInfo.getCertInterestPeriod());	//	凭证计息期限  cert_interest_period
					toAddDetail.setCertInterestRate(CommonUtil.stringToBigDecimal(certInfo.getCertInterestRate() ));	//收益率/利率  cert_interest_rate
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
					toAddDetails.add(toAddDetail);
				}
			}

		}
		fundEventMainService.saveBatch(toAddMains);
		fundEventDetailService.saveBatch(toAddDetails);
		return toAddMains;
	}

	@Override
	@Transactional
	public void middleHandle(List<FundEventMain> fundEventMains, ExtAttributes extAttributes,String eventTypeCd){
		for (FundEventMain fundEventMain:fundEventMains) {
			fundCreditUseService.middleHandle(fundEventMain,extAttributes,eventTypeCd);
		}
	}
}
