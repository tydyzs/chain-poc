package org.git.modules.clm.loan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.entity.*;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.loan.*;
import org.git.modules.clm.loan.service.ILoanSummaryService;
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
import java.util.List;


/**
 * 借据信息同步接口-实现类
 *
 * @author chenchuan
 */
@Slf4j
@Service
@Transactional
public class LoanSummaryServiceImpl implements ILoanSummaryService {

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

	TbCrdContract tbCrdContract;
	TbCrdApprove tbCrdApprove;
	Product product;
	CsmParty party;
	String summaryId;
	LoanSummaryInfoResponseDTO rs;

	@Override
	public LoanSummaryInfoResponseDTO checkLoanSummary(LoanSummaryInfoRequestDTO rq) {
		System.out.println("--------进入借据同步接口处理-----------");
		rs = new LoanSummaryInfoResponseDTO();
		String msg = null;

		//判断操作类型
		msg = checkOpType(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//获取合同信息、批复信息、品种信息（tbCrdContract，tbCrdApprove，product）
		msg = checkConApprove(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//校验额度信息
		msg = checkCrd(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//校验客户信息
		msg = checkCsm(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//校验限额
		msg = checkQuota(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		rs.setBizScene(rq.getBizScene());
		rs.setBizAction(rq.getBizAction());
		rs.setContractNum(rq.getContractNum());
		rs.setSummaryNum(rq.getSummaryNum());
		return rs;
	}


	@Override
	public LoanSummaryInfoResponseDTO dealLoanSummary(LoanSummaryInfoRequestDTO rq) {

		System.out.println("--------进入借据同步接口处理-----------");
		String msg = null;

		//保存借据事件信息
		msg = saveSummaryEvent(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//保存借据信息
		msg = saveSummary(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//保存用信流水信息
		msg = saveApplySerialForCrd(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		//授信额度重算、额度统计
		msg = iCommonService.creditRecountAStatis(tbCrdContract.getCustomerNum());
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10301, msg);

		return rs;
	}

	//校验操作标志
	public String checkOpType(LoanSummaryInfoRequestDTO rq) {
		TbCrdSummary tbCrdSummary = new TbCrdSummary();
		//如果借据编号为空，根据合同号和借据状态00查现有的借据
		if (StringUtil.isEmpty(rq.getSummaryNum())) {
			tbCrdSummary.setContractNum(rq.getContractNum());
			tbCrdSummary.setSummaryStatus(JxrcbBizConstant.SUMMARY_STATUS_00);//未放款
		} else {
			tbCrdSummary.setSummaryNum(rq.getSummaryNum());
		}

		List<TbCrdSummary> summaryList = iTbCrdSummaryService.list(Condition.getQueryWrapper(tbCrdSummary));
		if (summaryList == null || summaryList.size() == 0) {//不存在，生成新的summaryId
			summaryId = BizNumUtil.getBizNumWithDate(JxrcbBizConstant.BIZ_TYPE_JJ);
		} else {//如果已存在，使用已有的summaryId
			if (summaryList.size() > 1) {
				return "当前合同：" + rq.getContractNum() + "有正在出账的借据";
			} else {
				summaryId = summaryList.get(0).getSummaryId();
			}
		}
		return null;
	}

	//获取合同信息、批复信息、品种信息（tbCrdContract，tbCrdApprove，product）
	public String checkConApprove(LoanSummaryInfoRequestDTO rq) {
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
	public String checkCrd(LoanSummaryInfoRequestDTO rq) {
		//合同占用额度，不处理信息
		if (JxrcbBizConstant.LIMIT_USED_TYPE_HT.equals(product.getLimitUsedType())) {
			return null;
		}

		//放款后不检查额度
		if (JxrcbBizConstant.OP_TYPE_FK.equals(rq.getOpType())) {
			return null;
		}

		//检验批复额度
		if (CommonUtil.stringToBigDecimal(rq.getSummaryAmt()).compareTo(tbCrdApprove.getApproveAvi()) > 0) {
			return "借据金额【" + CommonUtil.stringToBigDecimal(rq.getSummaryAmt()) + "】大于批复可用金额【" + tbCrdApprove.getApproveAvi() + "】";
		}
		return null;
	}

	//保存借据事件信息
	public String saveSummaryEvent(LoanSummaryInfoRequestDTO rq) {
		TbCrdSummaryEvent tbCrdSummaryEvent = new TbCrdSummaryEvent();
		tbCrdSummaryEvent.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		tbCrdSummaryEvent.setOpType(rq.getOpType());//操作类型
		tbCrdSummaryEvent.setBizAction(rq.getBizAction());//业务场景
		tbCrdSummaryEvent.setBizScene(rq.getBizScene());//流程节点
		tbCrdSummaryEvent.setSummaryId(summaryId);
		tbCrdSummaryEvent.setSummaryNum(rq.getSummaryNum());//借据编号
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
		tbCrdSummaryEvent.setExchangeRate(CommonUtil.stringToBigDecimal(rq.getExchangeRate()));//汇率
		tbCrdSummaryEvent.setSummaryAmt(CommonUtil.stringToBigDecimal(rq.getSummaryAmt()));//借据金额
		tbCrdSummaryEvent.setSummaryBal(CommonUtil.stringToBigDecimal(rq.getSummaryBal()));//借据余额
		tbCrdSummaryEvent.setBeginDate(rq.getBeginDate());//借据起期
		tbCrdSummaryEvent.setEndDate(rq.getEndDate());//借据止期
		tbCrdSummaryEvent.setSummaryStatus(rq.getSummaryStatus());//借据状态
		tbCrdSummaryEvent.setGuaranteeType(rq.getGuaranteeType());//担保方式
		tbCrdSummaryEvent.setMainGuaranteeType(rq.getMainGuaranteeType());//主担保方式
		tbCrdSummaryEvent.setDepositRatio(CommonUtil.stringToBigDecimal(rq.getDepositRatio()));//保证金比例
		tbCrdSummaryEvent.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		tbCrdSummaryEvent.setTranDate(CommonUtil.getWorkDate());//交易日期
		tbCrdSummaryEvent.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		tbCrdSummaryEvent.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdSummaryEventService.save(tbCrdSummaryEvent);
		return null;
	}

	//保存借据信息
	public String saveSummary(LoanSummaryInfoRequestDTO rq) {
		TbCrdSummary tbCrdSummary = new TbCrdSummary();
		tbCrdSummary.setSummaryId(summaryId);
		tbCrdSummary.setSummaryNum(rq.getSummaryNum());//借据编号
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
		tbCrdSummary.setExchangeRate(CommonUtil.stringToBigDecimal(rq.getExchangeRate()));//汇率
		tbCrdSummary.setSummaryAmt(CommonUtil.stringToBigDecimal(rq.getSummaryAmt()));//借据金额
		tbCrdSummary.setSummaryBal(CommonUtil.stringToBigDecimal(rq.getSummaryBal()));//借据余额
		tbCrdSummary.setBeginDate(rq.getBeginDate());//借据起期
		tbCrdSummary.setEndDate(rq.getEndDate());//借据止期
		tbCrdSummary.setSummaryStatus(rq.getSummaryStatus());//借据状态
		tbCrdSummary.setGuaranteeType(rq.getGuaranteeType());//担保方式
		tbCrdSummary.setMainGuaranteeType(rq.getMainGuaranteeType());//主担保方式
		tbCrdSummary.setDepositRatio(CommonUtil.stringToBigDecimal(rq.getDepositRatio()));//保证金比例
		tbCrdSummary.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		tbCrdSummary.setTranDate(CommonUtil.getWorkDate());//交易日期
		tbCrdSummary.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		tbCrdSummary.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdSummaryService.saveOrUpdate(tbCrdSummary);
		return null;
	}

	//保存授信额度使用、恢复流水信息
	public String saveApplySerialForCrd(LoanSummaryInfoRequestDTO rq) {
		//业务品种为合同占用时，不处理
		if (JxrcbBizConstant.LIMIT_USED_TYPE_HT.equals(product.getLimitUsedType())) {
			return null;
		}
		CrdApplySerial applySerial = new CrdApplySerial();
		applySerial.setBusiDealNum(rq.getSummaryNum());//借据编号
		applySerial.setCrdDetailPrd(tbCrdApprove.getCrdDetailPrd());//额度品种编号
		//根据借据状态设置交易类型：01	预占用  02预占用撤销  03占用  04占用撤销  05恢复  06恢复撤销  09其他
		//借据状态：CD000175 01正常  02逾期  03部分逾期  07核销  08销户  09票据置换  10资产置换  11股权置换
		if (JxrcbBizConstant.SUMMARY_STATUS_01.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_02.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_03.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_07.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_09.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_10.equals(rq.getSummaryStatus()) ||
			JxrcbBizConstant.SUMMARY_STATUS_11.equals(rq.getSummaryStatus())) {
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_03);//01占用
		} else if (JxrcbBizConstant.SUMMARY_STATUS_08.equals(rq.getSummaryStatus())) {//借据状态为：04结清 05失效 06核销
			if (JxrcbBizConstant.NO.equals(tbCrdApprove.getIsCycle()) ||
				JxrcbBizConstant.YES.equals(tbCrdContract.getIsCycle())) {//如果额度是非循环或者合同是循环的，则不登记额度恢复信息
				return null;
			}
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_05);//05恢复
		}

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
		BigDecimal summaryAmt = CommonUtil.stringToBigDecimal(rq.getSummaryAmt());//借据金额
		BigDecimal depositRatio = CommonUtil.stringToBigDecimal(rq.getDepositRatio());
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
	public String checkCsm(LoanSummaryInfoRequestDTO rq) {
		party = iCommonService.getCsmParty(tbCrdContract.getCustomerNum());
		return null;
	}

	//校验限额
	public String checkQuota(LoanSummaryInfoRequestDTO rq) {
		//放款后不检查限额
		if (JxrcbBizConstant.OP_TYPE_FK.equals(rq.getOpType())) {
			return null;
		}

		try {
			CheckBizInfoVO checkBizInfoVO = new CheckBizInfoVO();
			checkBizInfoVO.setTranSeqSn(rq.getTranSeqSn());
			checkBizInfoVO.setBizType(RcmConstant.QUOTA_BIZ_TYPE_JJ);//业务类型
			if (JxrcbBizConstant.SUMMARY_STATUS_00.equals(rq.getSummaryStatus())) {
				checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_JJ_ING);//控制节点
			} else {
				checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_JJ_OVER);//控制节点
			}
			checkBizInfoVO.setUserNum(rq.getUserNum());//业务客户经理
			checkBizInfoVO.setOrgNum(iCommonService.getCorpOrgCode(rq.getOrgNum()));//业务网点机构
			checkBizInfoVO.setCustomerNum(tbCrdContract.getCustomerNum());//客户编号
			checkBizInfoVO.setCustomerName(party.getCustomerName());//客户名称
			checkBizInfoVO.setProductNum(tbCrdContract.getProductNum());//业务品种
			checkBizInfoVO.setAmt(CommonUtil.stringToBigDecimal(rq.getSummaryAmt()));//金额
			checkBizInfoVO.setCurrencyCd(rq.getCurrencyCd());//币种
			checkBizInfoVO.setCustomerType(party.getCustomerType());//客户类型
			checkBizInfoVO.setRangeCountry(JxrcbBizConstant.COUNTRY_CHN);//国别
			checkBizInfoVO.setRangeRegion(RcmConstant.QUOTA_REGION_IN);//区域
			checkBizInfoVO.setRangeTerm(tbCrdApprove.getTermType());//期限范围
			checkBizInfoVO.setRangerRiskMitigation(rq.getMainGuaranteeType());//风险缓释
			CheckResultVO checkResultVO = rcmQuotaCheckService.check(checkBizInfoVO);
			List<RcmWarnInfo> warnInfos = checkResultVO.getWarnInfos();
			List<QuotaInfoResponseDTO> quotaInfos = new ArrayList<QuotaInfoResponseDTO>();
			if (warnInfos != null && warnInfos.size() > 0) {
				for (RcmWarnInfo warnInfo : warnInfos) {
					QuotaInfoResponseDTO quotaInfo = new QuotaInfoResponseDTO();
					BeanUtils.copyProperties(warnInfo, quotaInfo);
					quotaInfos.add(quotaInfo);
				}
			}
			rs.setControlType(checkResultVO.getControlType());
			rs.setQuotaInfo(quotaInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return "限额校验出现异常：" + e.getMessage();
		}
		return null;
	}


}
