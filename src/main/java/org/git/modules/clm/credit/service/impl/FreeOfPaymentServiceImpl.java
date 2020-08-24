package org.git.modules.clm.credit.service.impl;



import org.git.common.exception.AssertUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.dto.FreeOfPaymentEventVO;
import org.git.modules.clm.credit.dto.FreeOfPaymentOutAndInEventVO;
import org.git.modules.clm.credit.entity.FundAssignedIn;
import org.git.modules.clm.credit.entity.FundAssignedMain;
import org.git.modules.clm.credit.entity.FundAssignedOut;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.BondTranInInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.BondTranInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.BondTranOutInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FreeOfPaymentRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class FreeOfPaymentServiceImpl implements IFreeOfPaymentService {
	@Autowired
	private IFundAssignedMainService fundAssignedMainService;
	@Autowired
	private IFundAssignedInService fundAssignedInService;
	@Autowired
	private IFundAssignedOutService fundAssignedOutService;
	@Autowired
	private ICrdBusiCertInfoService crdBusiCertInfoService;
	@Autowired
	private ICrdApplySerialService crdApplySerialService;

	/**
	 * 事件落地
	 * @param freeOfPaymentRequestDTO
	 * @param extAttributes
	 */
	@Transactional(rollbackFor = Exception.class)
	public FreeOfPaymentEventVO registerEvent(FreeOfPaymentRequestDTO freeOfPaymentRequestDTO, ExtAttributes extAttributes){
		String tranTypeCd = freeOfPaymentRequestDTO.getTranTypeCd();
		String tranDirection = freeOfPaymentRequestDTO.getTranDirection();
		String busiDealNum = freeOfPaymentRequestDTO.getBusiDealNum();
		String tranDate = extAttributes.getOriReqDate();
		//防重检查
		checkRepeat(tranTypeCd,tranDirection,busiDealNum,tranDate);
		List<BondTranInfoRequestDTO> bondTranInfos = freeOfPaymentRequestDTO.getBondTranInfo();
		if(bondTranInfos==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20702,"纯券过户数组为空！");
		}
		FreeOfPaymentEventVO freeOfPaymentEventVO = new FreeOfPaymentEventVO();
		FundAssignedMain fundAssignedMain = new FundAssignedMain();
		fundAssignedMain.setEventMainId(StringUtil.randomUUID());
		fundAssignedMain.setTranSeqSn(extAttributes.getOriReqSn());
		fundAssignedMain.setTranDate(tranDate);
		fundAssignedMain.setTranTypeCd(tranTypeCd);
		fundAssignedMain.setBusiDealNum(busiDealNum);
		fundAssignedMain.setTranDirection(tranDirection);
		fundAssignedMain.setTranSystem(extAttributes.getRequesterId());//经办系统
		fundAssignedMain.setUserNum(extAttributes.getOperatorId());//经办人
		fundAssignedMain.setTranAcctStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);
		fundAssignedMain.setTranEventStatus(JxrcbBizConstant.TRAN_ACCT_STATUS_UNPROCES);
		freeOfPaymentEventVO.setFundAssignedMain(fundAssignedMain);
		List<FreeOfPaymentOutAndInEventVO> freeOfPaymentOutAndInEventVOs = new ArrayList<>();
		for (BondTranInfoRequestDTO bondTranInfo: bondTranInfos) {
			List<FundAssignedOut> fundAssignedOuts = new ArrayList<>();
			List<BondTranOutInfoRequestDTO> bondTranOutInfos = bondTranInfo.getBondOutInfo();
			if(bondTranOutInfos==null){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20703,"纯券过户-转出数组不能为空！");
			}
			for (BondTranOutInfoRequestDTO bondTranOutInfo: bondTranOutInfos) {
				FundAssignedOut fundAssignedOut = toFundAssignedMain(fundAssignedMain,bondTranOutInfo);
				fundAssignedOuts.add(fundAssignedOut);
			}
			BondTranInInfoRequestDTO bondTranInInfo = bondTranInfo.getBondInInfo();
			if(bondTranInInfo==null){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20704,"纯券过户-转入项不能为空！");
			}
			FundAssignedIn fundAssignedIn = toFundAssignedMain(fundAssignedMain,bondTranInInfo);
			fundAssignedOutService.saveBatch(fundAssignedOuts);
			fundAssignedInService.save(fundAssignedIn);
			FreeOfPaymentOutAndInEventVO freeOfPaymentOutAndInEventVO = new FreeOfPaymentOutAndInEventVO();
			freeOfPaymentOutAndInEventVO.setFundAssignedOut(fundAssignedOuts);
			freeOfPaymentOutAndInEventVO.setFundAssignedIn(fundAssignedIn);
			freeOfPaymentOutAndInEventVOs.add(freeOfPaymentOutAndInEventVO);
		}
		freeOfPaymentEventVO.setFreeOrPaymentOutAndInEvent(freeOfPaymentOutAndInEventVOs);
		fundAssignedMainService.save(fundAssignedMain);
		return freeOfPaymentEventVO;
	}

	/**
	 * 生成转出事件
	 * @param fundAssignedMain
	 * @param bondTranOutInfo
	 * @return
	 */
	private FundAssignedOut toFundAssignedMain(FundAssignedMain fundAssignedMain, BondTranOutInfoRequestDTO bondTranOutInfo){
		FundAssignedOut fundAssignedOut = new FundAssignedOut();
		fundAssignedOut.setTransferOutId(StringUtil.randomUUID());
		fundAssignedOut.setEventMainId(fundAssignedMain.getEventMainId());
		fundAssignedOut.setTranSeqSn(fundAssignedMain.getTranSeqSn());
		fundAssignedOut.setTranDate(fundAssignedMain.getTranDate());
		fundAssignedOut.setCrdOutOrgNum(bondTranOutInfo.getCrdOutOrgNum());
		fundAssignedOut.setBusiSourceReqNum(bondTranOutInfo.getBusiSourceReqNum());
		return fundAssignedOut;
	}

	/**
	 * 生成转入事件
	 * @param fundAssignedMain
	 * @param bondTranInInfo
	 * @return
	 */
	private FundAssignedIn toFundAssignedMain(FundAssignedMain fundAssignedMain, BondTranInInfoRequestDTO bondTranInInfo){
		FundAssignedIn fundAssignedIn = new FundAssignedIn();
		fundAssignedIn.setTransferInId(StringUtil.randomUUID());
		fundAssignedIn.setEventMainId(fundAssignedMain.getEventMainId());
		fundAssignedIn.setTranSeqSn(fundAssignedMain.getTranSeqSn());
		fundAssignedIn.setTranDate(fundAssignedMain.getTranDate());
		fundAssignedIn.setCrdInOrgNum(bondTranInInfo.getCrdInOrgNum());
		fundAssignedIn.setBusiNewlReqNum(bondTranInInfo.getBusiNewReqNum());
		return fundAssignedIn;
	}

	/**
	 * 防重检查
	 * @param tranTypeCd
	 * @param tranDirection
	 * @param busiDealNum
	 * @param tranDate
	 */
	private void checkRepeat(String tranTypeCd,String tranDirection,String busiDealNum,String tranDate){
		FundAssignedMain params = new FundAssignedMain();
		params.setTranTypeCd(tranTypeCd);
		params.setBusiDealNum(busiDealNum);
		params.setTranDirection(tranDirection);
		params.setTranDate(tranDate);
		params.setTranAcctStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		int count = fundAssignedMainService.count(Condition.getQueryWrapper(params));
		if(count>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20701,"此业务已处理！");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void middleHandle(FreeOfPaymentEventVO freeOfPaymentEventVO){
		FundAssignedMain fundAssignedMain = freeOfPaymentEventVO.getFundAssignedMain();
		List<FreeOfPaymentOutAndInEventVO> freeOfPaymentOutAndInEvents = freeOfPaymentEventVO.getFreeOrPaymentOutAndInEvent();
		for (FreeOfPaymentOutAndInEventVO freeOfPaymentOutAndInEventVO: freeOfPaymentOutAndInEvents) {
			List<FundAssignedOut> fundAssignedOuts = freeOfPaymentOutAndInEventVO.getFundAssignedOut();
			FundAssignedIn fundAssignedIn = freeOfPaymentOutAndInEventVO.getFundAssignedIn();
			eventSplit(fundAssignedMain,fundAssignedOuts,fundAssignedIn);
		}
	}

	public void eventSplit(FundAssignedMain fundAssignedMain,List<FundAssignedOut> fundAssignedOuts,FundAssignedIn fundAssignedIn){
		for (FundAssignedOut fundAssignedOut: fundAssignedOuts) {
			String busiSourceReqNum = fundAssignedOut.getBusiSourceReqNum();
			String crdOutOrgNum = fundAssignedOut.getCrdOutOrgNum();
			String tranDirection = fundAssignedMain.getTranDirection();
			if(JxrcbBizConstant.TRAN_DIRECTION_FORWARD.equals(tranDirection)){//纯券过户-正向交易
				CrdBusiCertInfo params = new CrdBusiCertInfo();
				params.setBusiDealNum(busiSourceReqNum);
				params.setCrdGrantOrgNum(crdOutOrgNum);
				List<CrdBusiCertInfo> crdBusiCertInfos = crdBusiCertInfoService.list(Condition.getQueryWrapper(params));
				if(crdBusiCertInfos==null){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20705,"未找到业务"+busiSourceReqNum+"对应的凭证信息！");
				}
				for (int i=0;i<crdBusiCertInfos.size();i++) {
					crdBusiCertInfos.get(i).setSourceBusiNum(crdBusiCertInfos.get(i).getBusiDealNum());
					crdBusiCertInfos.get(i).setBusiDealNum(fundAssignedIn.getBusiNewlReqNum());
					crdBusiCertInfos.get(i).setSourceOrgNum(crdBusiCertInfos.get(i).getCrdGrantOrgNum());
					crdBusiCertInfos.get(i).setCrdGrantOrgNum(fundAssignedIn.getCrdInOrgNum());
				}
			}else if(JxrcbBizConstant.TRAN_DIRECTION_OPPOSITE.equals(tranDirection)){
				CrdBusiCertInfo params = new CrdBusiCertInfo();
				params.setBusiDealNum(fundAssignedIn.getBusiNewlReqNum());
				params.setCrdGrantOrgNum(fundAssignedIn.getCrdInOrgNum());
				List<CrdBusiCertInfo> crdBusiCertInfos = crdBusiCertInfoService.list(Condition.getQueryWrapper(params));
				if(crdBusiCertInfos==null){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20706,"未找到业务"+busiSourceReqNum+"对应的凭证信息！");
				}
				for (int i=0;i<crdBusiCertInfos.size();i++) {
					if(crdBusiCertInfos.get(i).getSourceBusiNum()==null){
						AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20707,"获取凭证"+crdBusiCertInfos.get(i).getCertNum()+"原编号信息失败！");
					}
					crdBusiCertInfos.get(i).setBusiDealNum(crdBusiCertInfos.get(i).getSourceBusiNum());
					crdBusiCertInfos.get(i).setCrdGrantOrgNum(crdBusiCertInfos.get(i).getSourceOrgNum());
					crdBusiCertInfos.get(i).setSourceOrgNum(null);
					crdBusiCertInfos.get(i).setSourceBusiNum(null);

				}
			}
		}
	}


}
