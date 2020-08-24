package org.git.modules.clm.loan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.constant.DictMappingConstant;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.entity.TbCrdContract;
import org.git.modules.clm.credit.entity.TbCrdSummary;
import org.git.modules.clm.credit.entity.TbCrdSummaryEvent;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.service.ITbCrdContractService;
import org.git.modules.clm.credit.service.ITbCrdSummaryEventService;
import org.git.modules.clm.credit.service.ITbCrdSummaryService;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.loan.*;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBillSignRequestDTO;
import org.git.modules.clm.loan.service.ILoanBillSignService;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.IProductService;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.RcmWarnInfo;
import org.git.modules.clm.rcm.service.RcmQuotaCheckService;
import org.git.modules.clm.rcm.vo.CheckBizInfoVO;
import org.git.modules.clm.rcm.vo.CheckResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 银票签发接口-实现类
 *
 * @author 'n '
 */
@Slf4j
@Service
@Transactional
public class LoanBillSignServiceImpl implements ILoanBillSignService {

	@Autowired
	ICommonService iCommonService;
	@Autowired
	ITbCrdContractService iTbCrdContractService;
	@Autowired
	ITbCrdApproveService iTbCrdApproveService;
	@Autowired
	ITbCrdSummaryEventService iTbCrdSummaryEventService;
	@Autowired
	ITbCrdSummaryService iTbCrdSummaryService;
	@Autowired
	IProductService iProductService;
	@Autowired
	ICrdApplySerialService iCrdApplySerialService;
	@Autowired
	RcmQuotaCheckService rcmQuotaCheckService;

	LoanBillSignResponseDTO rs;
	TbCrdContract tbCrdContract;
	TbCrdApprove tbCrdApprove;
	CsmParty party;
	Product product;
	String summaryId;

	@Override
	public LoanBillSignResponseDTO checkLoanBillSign(LoanBillSignRequestDTO rq) {
		rs = new LoanBillSignResponseDTO();
		String msg = null;

		//获取合同信息、批复信息、品种信息（tbCrdContract，tbCrdApprove，product）
		msg = checkConApprove(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

		//校验客户信息
		msg = checkCsm(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

		//校验限额
		msg = checkQuota(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

		rs.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		rs.setContractNum(rq.getContractNum());
		rs.setBizScene(rq.getBizScene());//业务场景
		rs.setBizAction(rq.getBizAction());//流程节点
		return rs;
	}

	@Override
	public LoanBillSignResponseDTO dealLoanBillSign(LoanBillSignRequestDTO rq) {
		String msg = null;
		//编辑时删除已有的票据信息
//		msg = delSummary(rq);
//		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);
		List<BillInfoRequestDTO> billInfoList = rq.getBillInfo();
		for (BillInfoRequestDTO billInfo : billInfoList) {
			msg = checkCrd(rq, billInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

			msg = getSummary(rq, billInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

			//保存借据事件信息
			msg = saveSummaryEvent(rq, billInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

			//保存借据信息
			msg = saveSummary(rq, billInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

			//保存用信流水信息
			msg = saveApplySerialForCrd(rq, billInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);

			//授信额度重算
			msg = iCommonService.creditRecountAStatis(tbCrdContract.getCustomerNum());
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10601, msg);
		}
		return rs;
	}


	//校验额度信息
	public String checkCrd(LoanBillSignRequestDTO rq, BillInfoRequestDTO billInfo) {
		//合同占用额度，不处理信息
		if (JxrcbBizConstant.LIMIT_USED_TYPE_HT.equals(product.getLimitUsedType())) {
			return null;
		}
		//票据状态： CD000078 0 待兑付 1 提示付款 2 未用注销 3 实时清算 4 未用退回 5 待签发 9 提示付款（选择销登记簿）  E 结清
		if ("2,4,E".contains(billInfo.getBillStatus())) {//票据状态为无效时，不校验额度
			return null;
		}
		//检验批复额度
		if (billInfo.getSummaryAmt().compareTo(tbCrdApprove.getApproveAvi()) > 0) {
			return "借据金额【" + billInfo.getSummaryAmt() + "】大于批复可用金额【" + tbCrdApprove.getApproveAvi() + "】";
		}
		return null;
	}

	//获取合同信息、批复信息、品种信息（tbCrdContract，tbCrdApprove，product）
	public String checkConApprove(LoanBillSignRequestDTO rq) {
		tbCrdContract = iTbCrdContractService.getById(rq.getContractNum());
		if (tbCrdContract == null || StringUtil.isEmpty(tbCrdContract.getContractNum())) {
			return "根据合同号，没有找到对应的合同信息：" + rq.getContractNum();
		}

		//查询业务品种信息
		product = iProductService.getById(tbCrdContract.getProductNum());
		if (product == null || StringUtil.isEmpty(product.getLimitUsedType())) {
			return "未配置业务品种：" + product.getLimitUsedType();
		}

		//获取批复信息
		tbCrdApprove = iTbCrdApproveService.getById(tbCrdContract.getApproveId());
		if (tbCrdApprove == null || StringUtil.isEmpty(tbCrdApprove.getApproveId())) {
			return "根据合同号，没有找到对应的批复信息：" + rq.getContractNum();
		}

		return null;
	}

	//校验额度信息
	public String getSummary(LoanBillSignRequestDTO rq, BillInfoRequestDTO billInfo) {
		TbCrdSummary tbCrdSummaryT = new TbCrdSummary();
		tbCrdSummaryT.setBillNum(billInfo.getBillNum());
		tbCrdSummaryT = iTbCrdSummaryService.getOne(Condition.getQueryWrapper(tbCrdSummaryT));
		if (tbCrdSummaryT == null || StringUtil.isBlank(tbCrdSummaryT.getSummaryId())) {//不存在，生成新的summaryId
			summaryId = BizNumUtil.getBizNumWithDate(JxrcbBizConstant.BIZ_TYPE_PJ);
		} else {//如果已存在，使用已有的借据ID
			summaryId = tbCrdSummaryT.getSummaryId();
		}
		return null;
	}

	//保存借据事件信息
	public String saveSummaryEvent(LoanBillSignRequestDTO rq, BillInfoRequestDTO billInfo) {
		TbCrdSummaryEvent tbCrdSummaryEvent = new TbCrdSummaryEvent();
		tbCrdSummaryEvent.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		tbCrdSummaryEvent.setOpType(rq.getOpType());//操作类型
		tbCrdSummaryEvent.setBizAction(rq.getBizAction());//业务场景
		tbCrdSummaryEvent.setBizScene(rq.getBizScene());//流程节点
		tbCrdSummaryEvent.setSummaryId(summaryId);
		tbCrdSummaryEvent.setSummaryNum(billInfo.getSummaryNum());//借据编号
		tbCrdSummaryEvent.setContractNum(rq.getContractNum());//合同编号
		tbCrdSummaryEvent.setApproveId(tbCrdContract.getApproveId());//批复ID
		tbCrdSummaryEvent.setCustomerNum(tbCrdContract.getCustomerNum());//客户编号
		tbCrdSummaryEvent.setProductNum(tbCrdContract.getProductNum());//业务品种
		tbCrdSummaryEvent.setIndustry(tbCrdContract.getIndustry());//行业投向
		tbCrdSummaryEvent.setGuaranteeType(tbCrdContract.getGuaranteeType());//担保方式
		tbCrdSummaryEvent.setMainGuaranteeType(tbCrdContract.getMainGuaranteeType());//主担保方式
		tbCrdSummaryEvent.setLoanOrg(rq.getOrgNum());//出账机构
		tbCrdSummaryEvent.setUserNum(rq.getUserNum());//经办人
		tbCrdSummaryEvent.setOrgNum(rq.getOrgNum());//经办机构
		tbCrdSummaryEvent.setCurrencyCd(rq.getCurrencyCd());//币种
		tbCrdSummaryEvent.setExchangeRate(rq.getExchangeRate());//汇率
		tbCrdSummaryEvent.setSummaryAmt(billInfo.getSummaryAmt());//借据金额
		tbCrdSummaryEvent.setSummaryBal(billInfo.getSummaryAmt());//借据余额
		tbCrdSummaryEvent.setBeginDate(billInfo.getBeginDate());//借据起期
		tbCrdSummaryEvent.setEndDate(billInfo.getEndDate());//借据止期
		tbCrdSummaryEvent.setSummaryStatus(DictMappingConstant.summaryStatusMap.get(billInfo.getBillStatus()));//借据状态
		tbCrdSummaryEvent.setGuaranteeType(billInfo.getGuaranteeType());//担保方式
		tbCrdSummaryEvent.setMainGuaranteeType(billInfo.getMainGuaranteeType());//主担保方式
		tbCrdSummaryEvent.setDepositRatio(billInfo.getDepositRatio());//保证金比例

		//票据信息
		tbCrdSummaryEvent.setBillStatus(billInfo.getBillStatus());//票据状态
		tbCrdSummaryEvent.setBillNum(billInfo.getBillNum());//票据号码
		tbCrdSummaryEvent.setBillType(billInfo.getBillType());//票据类型
		tbCrdSummaryEvent.setAcceptorEcifNum(billInfo.getAcceptorEcifNum());//承兑人ecif客户号
		tbCrdSummaryEvent.setDrawerName(billInfo.getDrawerName());//出票人名称
		tbCrdSummaryEvent.setDrawerAcct(billInfo.getDrawerAcct());//出票人账号
		tbCrdSummaryEvent.setDrawerBankNum(billInfo.getDrawerBankNum());//出票人开户行行号
		tbCrdSummaryEvent.setDrawerBankName(billInfo.getDrawerBankName());//出票人开户行行名
		tbCrdSummaryEvent.setPayName(billInfo.getPayName());//付款人开户行行名
		tbCrdSummaryEvent.setPayAcct(billInfo.getPayAcct());//付款行行号
		tbCrdSummaryEvent.setPayeeAcct(billInfo.getPayeeAcct());//收款人名称
		tbCrdSummaryEvent.setPayeeName(billInfo.getPayeeName());//收款人名称
		tbCrdSummaryEvent.setPayeeBankNum(billInfo.getPayeeBankNum());//收款人开户行行号
		tbCrdSummaryEvent.setPayeeBankName(billInfo.getPayeeBankName());//收款人开户行行号
		tbCrdSummaryEvent.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		tbCrdSummaryEvent.setTranDate(CommonUtil.getWorkDate());//交易日期
		tbCrdSummaryEvent.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		tbCrdSummaryEvent.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdSummaryEventService.save(tbCrdSummaryEvent);
		return null;
	}

	//删除合同下目前现有的借据信息
	public String delSummary(LoanBillSignRequestDTO rq) {
		//修改时，先删除合同下，所有的票据信息
		if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(rq.getOpType())) {
			TbCrdSummary tbCrdSummaryT = new TbCrdSummary();
			tbCrdSummaryT.setContractNum(rq.getContractNum());
			iTbCrdSummaryService.remove(Condition.getQueryWrapper(tbCrdSummaryT));
		}
		return null;
	}

	//保存借据信息
	public String saveSummary(LoanBillSignRequestDTO rq, BillInfoRequestDTO billInfo) {
		//保存票据信息
		TbCrdSummary tbCrdSummary = new TbCrdSummary();
		tbCrdSummary.setSummaryId(summaryId);
		tbCrdSummary.setSummaryNum(billInfo.getSummaryNum());//借据编号
		tbCrdSummary.setContractNum(rq.getContractNum());//合同编号
		tbCrdSummary.setApproveId(tbCrdContract.getApproveId());//批复ID
		tbCrdSummary.setCustomerNum(tbCrdContract.getCustomerNum());//客户编号
		tbCrdSummary.setProductNum(tbCrdContract.getProductNum());//业务品种
		tbCrdSummary.setIndustry(tbCrdContract.getIndustry());//行业投向
		tbCrdSummary.setGuaranteeType(tbCrdContract.getGuaranteeType());//担保方式
		tbCrdSummary.setMainGuaranteeType(tbCrdContract.getMainGuaranteeType());//主担保方式
		tbCrdSummary.setLoanOrg(rq.getOrgNum());//出账机构
		tbCrdSummary.setUserNum(rq.getUserNum());//经办人
		tbCrdSummary.setOrgNum(rq.getOrgNum());//经办机构
		tbCrdSummary.setCurrencyCd(rq.getCurrencyCd());//币种
		tbCrdSummary.setExchangeRate(rq.getExchangeRate());//汇率
		tbCrdSummary.setSummaryAmt(billInfo.getSummaryAmt());//借据金额
		tbCrdSummary.setSummaryBal(billInfo.getSummaryAmt());//借据余额
		tbCrdSummary.setBeginDate(billInfo.getBeginDate());//借据起期
		tbCrdSummary.setEndDate(billInfo.getEndDate());//借据止期
		tbCrdSummary.setSummaryStatus(DictMappingConstant.summaryStatusMap.get(billInfo.getBillStatus()));//借据状态
		tbCrdSummary.setGuaranteeType(billInfo.getGuaranteeType());//担保方式
		tbCrdSummary.setMainGuaranteeType(billInfo.getMainGuaranteeType());//主担保方式
		tbCrdSummary.setDepositRatio(billInfo.getDepositRatio());//保证金比例

		//票据信息
		tbCrdSummary.setBillStatus(billInfo.getBillStatus());//票据状态
		tbCrdSummary.setBillNum(billInfo.getBillNum());//票据号码
		tbCrdSummary.setBillType(billInfo.getBillType());//票据类型
		tbCrdSummary.setAcceptorEcifNum(billInfo.getAcceptorEcifNum());//承兑人ecif客户号
		tbCrdSummary.setDrawerName(billInfo.getDrawerName());//出票人名称
		tbCrdSummary.setDrawerAcct(billInfo.getDrawerAcct());//出票人账号
		tbCrdSummary.setDrawerBankNum(billInfo.getDrawerBankNum());//出票人开户行行号
		tbCrdSummary.setDrawerBankName(billInfo.getDrawerBankName());//出票人开户行行名
		tbCrdSummary.setPayName(billInfo.getPayName());//付款人开户行行名
		tbCrdSummary.setPayAcct(billInfo.getPayAcct());//付款行行号
		tbCrdSummary.setPayeeAcct(billInfo.getPayeeAcct());//收款人名称
		tbCrdSummary.setPayeeName(billInfo.getPayeeName());//收款人名称
		tbCrdSummary.setPayeeBankNum(billInfo.getPayeeBankNum());//收款人开户行行号
		tbCrdSummary.setPayeeBankName(billInfo.getPayeeBankName());//收款人开户行行号
		tbCrdSummary.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		tbCrdSummary.setTranDate(CommonUtil.getWorkDate());//交易日期
		tbCrdSummary.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		tbCrdSummary.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdSummaryService.saveOrUpdate(tbCrdSummary);
		return null;
	}


	//保存授信额度使用、恢复流水信息
	public String saveApplySerialForCrd(LoanBillSignRequestDTO rq, BillInfoRequestDTO billInfo) {
		//业务品种为借据占用时，不处理
		if (JxrcbBizConstant.LIMIT_USED_TYPE_HT.equals(product.getLimitUsedType())) {
			return null;
		}
		CrdApplySerial applySerial = new CrdApplySerial();
		String summaryStatus = DictMappingConstant.summaryStatusMap.get(billInfo.getBillStatus());//根据票据状态获取借据状态
		//根据借据状态设置交易类型：01	预占用  02预占用撤销  03占用  04占用撤销  05恢复  06恢复撤销  09其他
		//借据状态：CD000175 01正常  02逾期  03部分逾期  07核销  08销户  09票据置换  10资产置换  11股权置换
		if (JxrcbBizConstant.SUMMARY_STATUS_01.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_02.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_03.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_07.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_09.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_10.equals(summaryStatus) ||
			JxrcbBizConstant.SUMMARY_STATUS_11.equals(summaryStatus)) {
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_03);//01占用
		} else if (JxrcbBizConstant.SUMMARY_STATUS_08.equals(summaryStatus)) {//借据状态为：04结清 05失效 06核销
			if (JxrcbBizConstant.NO.equals(tbCrdApprove.getIsCycle()) ||
				JxrcbBizConstant.YES.equals(tbCrdContract.getIsCycle())) {//如果额度是非循环或者合同是循环的，则不登记额度恢复信息
				return null;
			}
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_05);//05恢复
		}

		applySerial.setBusiDealNum(billInfo.getSummaryNum());//借据编号
		applySerial.setCrdDetailPrd(tbCrdApprove.getCrdDetailPrd());//额度品种编号

		//修改时，先删除已有的占用恢复记录
		if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(rq.getOpType())) {
			iCrdApplySerialService.remove(Condition.getQueryWrapper(applySerial));
		}

		applySerial.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		applySerial.setTranDate(CommonUtil.getWorkDate());//交易日期
		applySerial.setBusiDealNum(rq.getContractNum());//业务编号
		applySerial.setCrdDetailNum(tbCrdApprove.getCrdDetailNum());//三级额度编号
		applySerial.setCrdDetailPrd(tbCrdApprove.getCrdDetailPrd());//三级额度品种
		applySerial.setCrdGrantOrgNum(tbCrdApprove.getOrgNum());//授信机构
		applySerial.setCustomerNum(tbCrdApprove.getCustomerNum());//用信客户号
		BigDecimal summaryAmt = billInfo.getSummaryAmt();//借据金额
		BigDecimal depositRatio = billInfo.getDepositRatio();
		applySerial.setLimitCreditAmt(summaryAmt);//占用/恢复金额
		applySerial.setExpCreditAmt(CommonUtil.getExpAmt(summaryAmt, depositRatio));//占用/恢复敞口
		applySerial.setCurrencyCd(rq.getCurrencyCd());
		applySerial.setIsMix(JxrcbBizConstant.NO);//是否串用
		applySerial.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		applySerial.setOrgNum(rq.getOrgNum());//经办机构
		applySerial.setUserNum(rq.getUserNum());//经办人
		applySerial.setCreateTime(CommonUtil.getWorkDateTime());
		applySerial.setUpdateTime(CommonUtil.getWorkDateTime());
		iCrdApplySerialService.save(applySerial);
		return null;
	}

	//获取客户信息
	public String checkCsm(LoanBillSignRequestDTO rq) {
		party = iCommonService.getCsmParty(tbCrdContract.getCustomerNum());
		return null;
	}

	//校验限额
	public String checkQuota(LoanBillSignRequestDTO rq) {
		try {
			List<Integer> controlTypes = new ArrayList<Integer>();
			List<QuotaInfoResponseDTO> quotaInfos = new ArrayList<QuotaInfoResponseDTO>();
			for (BillInfoRequestDTO billInfo : rq.getBillInfo()) {//循环每笔额度信息校验限额
				if ("2,4,E".contains(billInfo.getBillStatus())) {//票据状态为无效时，不校验限额
					continue;
				}
				CheckBizInfoVO checkBizInfoVO = new CheckBizInfoVO();
				checkBizInfoVO.setTranSeqSn(rq.getTranSeqSn());
				checkBizInfoVO.setBizType(RcmConstant.QUOTA_BIZ_TYPE_JJ);//业务类型
				checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_JJ_OVER);//控制节点
				checkBizInfoVO.setUserNum(rq.getUserNum());//业务客户经理
				checkBizInfoVO.setOrgNum(iCommonService.getCorpOrgCode(rq.getOrgNum()));//业务网点机构
				checkBizInfoVO.setCustomerNum(tbCrdContract.getCustomerNum());//客户编号
				checkBizInfoVO.setCustomerName(party.getCustomerName());//客户名称
				checkBizInfoVO.setProductNum(tbCrdContract.getProductNum());//业务品种
				checkBizInfoVO.setAmt(billInfo.getSummaryAmt());//金额
				checkBizInfoVO.setCurrencyCd(rq.getCurrencyCd());//币种
				checkBizInfoVO.setCustomerType(party.getCustomerType());//客户类型
				checkBizInfoVO.setRangeCountry(JxrcbBizConstant.COUNTRY_CHN);//国别
				checkBizInfoVO.setRangeRegion(RcmConstant.QUOTA_REGION_IN);//区域
				checkBizInfoVO.setRangeTerm(tbCrdApprove.getTermType());//期限范围
				checkBizInfoVO.setRangerRiskMitigation(tbCrdContract.getMainGuaranteeType());//风险缓释
				CheckResultVO checkResultVO = rcmQuotaCheckService.check(checkBizInfoVO);
				List<RcmWarnInfo> warnInfos = checkResultVO.getWarnInfos();
				controlTypes.add(Integer.valueOf(checkResultVO.getControlType()));
				if (warnInfos == null || warnInfos.size() == 0) {
					continue;
				}
				for (RcmWarnInfo warnInfo : warnInfos) {
					QuotaInfoResponseDTO quotaInfo = new QuotaInfoResponseDTO();
					BeanUtils.copyProperties(warnInfo, quotaInfo);
					quotaInfos.add(quotaInfo);
				}
			}
			if (controlTypes != null && controlTypes.size() > 0) {
				rs.setControlType(String.valueOf(Collections.max(controlTypes)));//管控类型取数组中级别最大的
			} else {
				rs.setControlType(RcmConstant.QUOTA_NODE_MEASURE_PASS);//管控类型为通过
			}
			rs.setQuotaInfo(quotaInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return "限额校验出现异常：" + e.getMessage();
		}
		return null;
	}

}
