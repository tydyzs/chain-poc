package org.git.modules.clm.loan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.constant.DictMappingConstant;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.entity.TbCrdApproveEvent;
import org.git.modules.clm.credit.service.ITbCrdApproveEventService;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.customer.service.IECIFCustomerInfoService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.loan.LimitDetailInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.QuotaInfoResponseDTO;
import org.git.modules.clm.loan.service.ILoanCreditService;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdProject;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdProjectService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
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
 * 额度申请接口逻辑处理实现类
 *
 * @author chenchuan
 */
@Slf4j
@Service
@Transactional
public class LoanCreditServiceImpl implements ILoanCreditService {
	@Autowired
	ITbCrdApproveEventService iTbCrdApproveEventService;
	@Autowired
	ITbCrdApproveService iTbCrdApproveService;
	@Autowired
	ICommonService iCommonService;
	@Autowired
	ICrdProductService iCrdProductService;
	@Autowired
	ICrdApplySerialService iCrdApplySerialService;
	@Autowired
	ICrdProjectService iCrdProjectService;
	@Autowired
	ICsmPartyService iCsmPartyService;
	@Autowired
	RcmQuotaCheckService rcmQuotaCheckService;
	@Autowired
	IECIFCustomerInfoService iecifCustomerInfoService;

	private String msg = null;
	private String approveId = null;//批复ID
	private String crdDetailPrd = null;//三级额度品种
	private String crdMainPrd = null;//二级额度品种
	CrdProject crdProject;//项目额度信息实体
	TbCrdApproveEvent tbCrdApproveEvent;
	LoanCreditApplyResponseDTO rs;

	@Override
	public LoanCreditApplyResponseDTO checkLoanCredit(LoanCreditApplyRequestDTO rq) {
		System.out.println("--------------------进入额度申请程序服务处理--------------------");

		rs = new LoanCreditApplyResponseDTO();

		List<LimitDetailInfoRequestDTO> limitDetailInfoList = rq.getLimitDetailInfo();
		//校验限额信息
		msg = checkQuota(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

		rs.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		rs.setBizScene(rq.getBizScene());//业务场景
		rs.setBizAction(rq.getBizAction());//流程节点
		return rs;
	}

	@Override
	public LoanCreditApplyResponseDTO dealLoanCredit(LoanCreditApplyRequestDTO rq) {
		log.info("--------------------进入额度申请程序服务处理--------------------");
		//Response是所有响应体的父类，需要创建子类对象进行封装。

		//删除已有的批复明细数据
		/*msg = delApprove(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);*/

		List<LimitDetailInfoRequestDTO> limitDetailInfoList = rq.getLimitDetailInfo();
		for (LimitDetailInfoRequestDTO limitDetailInfo : limitDetailInfoList) {
			//校验操作标志和批复信息，获取approveId
			msg = checkApprove(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//校验项目额度信息，获取crdProject
			msg = checkProject(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//校验客户信息
			msg = checkCsm(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//校验额度品种是否存在，获取crdDetailNum，crdDetailPrd,crdMainNum,crdMainPrd
			msg = getCrdProduct(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//保存批复事件信息
			msg = saveApproveEvent(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//保存批复信息
			msg = saveApprove(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//如果有项目协议需要，保存项目额度使用流水，重算项目额度
			msg = saveApplySerialForPrj(rq, limitDetailInfo);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

			//授信额度重算、统计
			msg = iCommonService.creditRecountAStatis(limitDetailInfo.getCustomerNum());
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10101, msg);

		}

		return rs;
	}


	/**
	 * 保存批复事件
	 *
	 * @param rq
	 * @param limitDetailInfo
	 * @return msg
	 */
	public String saveApproveEvent(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		//保存数据到批复事件表
		//BeanUtils.copyProperties(rq, crdApproveEvent);
		//crdApproveEvent.setEventId();mybatis-plus自动生成主键
		tbCrdApproveEvent = new TbCrdApproveEvent();
		tbCrdApproveEvent.setApproveId(approveId);//批复ID
		tbCrdApproveEvent.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		tbCrdApproveEvent.setOpType(limitDetailInfo.getOpType());//操作类型
		tbCrdApproveEvent.setBizScene(rq.getBizScene());//业务场景
		tbCrdApproveEvent.setBizAction(rq.getBizAction());//流程节点
		tbCrdApproveEvent.setApproveNum(limitDetailInfo.getApproveNum());//批复编号
		tbCrdApproveEvent.setCustomerNum(limitDetailInfo.getCustomerNum());//客户编号
		tbCrdApproveEvent.setIsJointGuarantee(limitDetailInfo.getIsJointGuarantee());//是否联保
		tbCrdApproveEvent.setTotalAmt(limitDetailInfo.getTotalAmt());//批复金额
		tbCrdApproveEvent.setBizType(limitDetailInfo.getBizType());//业务类型
		tbCrdApproveEvent.setIsLowRisk(limitDetailInfo.getIsLowRisk());//是否低风险业务
		tbCrdApproveEvent.setOldSummaryNum(limitDetailInfo.getOldSummaryNum());//借新还旧借据号
		tbCrdApproveEvent.setTranDate(limitDetailInfo.getTranDate());//申请日期
		tbCrdApproveEvent.setApproveStatus(limitDetailInfo.getApproveStatus());//申请状态
		tbCrdApproveEvent.setUserNum(limitDetailInfo.getUserNum());//客户经理
		tbCrdApproveEvent.setOrgNum(limitDetailInfo.getOrgNum());//经办机构
		tbCrdApproveEvent.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);

		//批复明细
		BigDecimal approveAmt = limitDetailInfo.getApproveAmt();//批复明细金额
		BigDecimal depositRatio = limitDetailInfo.getDepositRatio();//保证金比例
		if (depositRatio == null) {
			depositRatio = BigDecimal.ZERO;
		}
		tbCrdApproveEvent.setApproveAmt(limitDetailInfo.getApproveAmt());//批复明细金额
		tbCrdApproveEvent.setDepositRatio(limitDetailInfo.getDepositRatio());//保证金比例
		if (JxrcbBizConstant.YES.equals(limitDetailInfo.getIsLowRisk())) {//低风险敞口额度为0
			tbCrdApproveEvent.setApproveExpAmt(BigDecimal.ZERO);
		} else {
			tbCrdApproveEvent.setApproveExpAmt(CommonUtil.getExpAmt(approveAmt, depositRatio));//敞口额度
		}
		tbCrdApproveEvent.setBizHappenType(limitDetailInfo.getBizHappenType());//业务发生方式
		tbCrdApproveEvent.setCurrencyCd(limitDetailInfo.getCurrencyCd());//币种
		tbCrdApproveEvent.setExchangeRate(limitDetailInfo.getExchangeRate());//汇率
		tbCrdApproveEvent.setProductNum(limitDetailInfo.getProductNum());//产品
		tbCrdApproveEvent.setProductType(limitDetailInfo.getProductType());//业务种类
		tbCrdApproveEvent.setIsCycle(limitDetailInfo.getIsCycle());//额度循环标志
		tbCrdApproveEvent.setIndustry(limitDetailInfo.getIndustry());//行业投向
		tbCrdApproveEvent.setGuaranteeType(limitDetailInfo.getGuaranteeType());//担保方式
		tbCrdApproveEvent.setMainGuaranteeType(limitDetailInfo.getMainGuaranteeType());//主担保方式
		tbCrdApproveEvent.setGuaranteeTypeDetail(limitDetailInfo.getGuaranteeTypeDetail());//担保方式分类

		BigDecimal term = limitDetailInfo.getTerm();//申请期限
		tbCrdApproveEvent.setTerm(term);//申请期限
		tbCrdApproveEvent.setTermUnit(limitDetailInfo.getTermUnit());//申请期限单位
		tbCrdApproveEvent.setTermType(CommonUtil.getLoanTermType(term, limitDetailInfo.getTermUnit()));//申请期限单位

		tbCrdApproveEvent.setProjectNum(limitDetailInfo.getProjectNum());//项目协议号
		tbCrdApproveEvent.setCrdMainPrd(crdMainPrd);//二级额度品种
		tbCrdApproveEvent.setCrdDetailPrd(crdDetailPrd);//三级额度品种
		tbCrdApproveEvent.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		tbCrdApproveEvent.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdApproveEventService.save(tbCrdApproveEvent);
		return null;

	}

/*	//修改时，先删除批复下所有的明细
	public String delApprove(LoanCreditApplyRequestDTO rq) {
		//修改时，先删除批复下所有的明细
		if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(rq.getOpType())) {
			TbCrdApprove tbCrdApproveT = new TbCrdApprove();
			tbCrdApproveT.setApproveNum(rq.getApproveNum());
			iTbCrdApproveService.remove(Condition.getQueryWrapper(tbCrdApproveT));
		}
		return null;
	}*/

	/**
	 * 保存批复信息
	 *
	 * @param rq
	 * @param limitDetailInfo
	 * @return msg
	 */
	public String saveApprove(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		//保存批复信息
		TbCrdApprove tbCrdApprove = new TbCrdApprove();
		tbCrdApprove.setApproveId(approveId);//批复ID
		tbCrdApprove.setApproveNum(limitDetailInfo.getApproveNum());//批复编号
		tbCrdApprove.setCustomerNum(limitDetailInfo.getCustomerNum());//客户编号
		tbCrdApprove.setIsJointGuarantee(limitDetailInfo.getIsJointGuarantee());//是否联保
		tbCrdApprove.setTotalAmt(limitDetailInfo.getTotalAmt());//批复金额
		tbCrdApprove.setBizType(limitDetailInfo.getBizType());//业务类型
		tbCrdApprove.setIsLowRisk(limitDetailInfo.getIsLowRisk());//是否低风险业务
		tbCrdApprove.setOldSummaryNum(limitDetailInfo.getOldSummaryNum());//借新还旧借据号
		tbCrdApprove.setTranDate(limitDetailInfo.getTranDate());//申请日期
		tbCrdApprove.setApproveStatus(limitDetailInfo.getApproveStatus());//申请状态
		tbCrdApprove.setUserNum(limitDetailInfo.getUserNum());//客户经理
		tbCrdApprove.setOrgNum(limitDetailInfo.getOrgNum());//机构号
		tbCrdApprove.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//系统号

		tbCrdApprove.setApproveAmt(tbCrdApproveEvent.getApproveAmt());//批复明细金额
		tbCrdApprove.setDepositRatio(tbCrdApproveEvent.getDepositRatio());//保证金比例
		tbCrdApprove.setApproveExpAmt(tbCrdApproveEvent.getApproveExpAmt());//敞口额度
		tbCrdApprove.setBizHappenType(limitDetailInfo.getBizHappenType());//业务发生方式
		tbCrdApprove.setCurrencyCd(limitDetailInfo.getCurrencyCd());//币种
		tbCrdApprove.setExchangeRate(limitDetailInfo.getExchangeRate());//汇率
		tbCrdApprove.setProductNum(limitDetailInfo.getProductNum());//产品
		tbCrdApprove.setProductType(limitDetailInfo.getProductType());//业务种类
		tbCrdApprove.setIsCycle(limitDetailInfo.getIsCycle());//额度循环标志
		tbCrdApprove.setIndustry(limitDetailInfo.getIndustry());//行业投向
		tbCrdApprove.setGuaranteeType(limitDetailInfo.getGuaranteeType());//担保方式
		tbCrdApprove.setMainGuaranteeType(limitDetailInfo.getMainGuaranteeType());//主担保方式
		tbCrdApprove.setGuaranteeTypeDetail(limitDetailInfo.getGuaranteeTypeDetail());//担保方式分类

		BigDecimal term = limitDetailInfo.getTerm();//申请期限
		tbCrdApprove.setTerm(term);//申请期限
		tbCrdApprove.setTermUnit(limitDetailInfo.getTermUnit());//申请期限单位
		tbCrdApprove.setTermType(CommonUtil.getLoanTermType(term, limitDetailInfo.getTermUnit()));//申请期限类型
		tbCrdApprove.setProjectNum(limitDetailInfo.getProjectNum());//项目协议号
		tbCrdApprove.setCrdMainPrd(crdMainPrd);//二级额度品种
		tbCrdApprove.setCrdDetailPrd(crdDetailPrd);//三级额度品种
		tbCrdApprove.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		if (JxrcbBizConstant.OP_TYPE_ADD.equals(limitDetailInfo.getOpType())) {//新增
			tbCrdApprove.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		}
		iTbCrdApproveService.saveOrUpdate(tbCrdApprove);
		return null;
	}

	/**
	 * 获取额度品种
	 *
	 * @param rq
	 * @param limitDetailInfo
	 * @return
	 */
	public String getCrdProduct(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		CrdProduct crdProduct = new CrdProduct();
		String customerType = DictMappingConstant.customerTypeMap.get(limitDetailInfo.getCustomerType());
		crdProduct.setCustType(customerType);//客户类型
		crdProduct.setCrdProductType(JxrcbBizConstant.CRD_TYPE_SX);//额度类型
		crdProduct.setIsLowRisk(limitDetailInfo.getIsLowRisk());//是否低风险
		crdProduct.setProductNum(limitDetailInfo.getProductNum());//业务品种
		crdProduct.setProductType(limitDetailInfo.getProductType());//业务种类
		crdProduct.setMainGuaranteeType(limitDetailInfo.getMainGuaranteeType());//担保方式

		Crd crd = iCrdProductService.selectCrd(crdProduct);
		if (crd == null || StringUtil.isBlank(crd.getCrdProductNum())) {
			return "没有找到对应的额度品种，业务品种编号：" + limitDetailInfo.getProductNum();
		}
		crdDetailPrd = crd.getCrdProductNum();//三级额度品种
		crdMainPrd = crd.getSuperCrdNum();//二级额度品种
		System.out.println("获取到额度品种：" + crd.toString());

		return null;
	}

	//获取批复信息
	public String checkApprove(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		TbCrdApprove tbCrdApprove = new TbCrdApprove();
		tbCrdApprove.setApproveNum(limitDetailInfo.getApproveNum());
		tbCrdApprove.setProductNum(limitDetailInfo.getProductNum());
		tbCrdApprove.setProductType(limitDetailInfo.getProductType());
		tbCrdApprove.setMainGuaranteeType(limitDetailInfo.getMainGuaranteeType());
		tbCrdApprove = iTbCrdApproveService.getOne(Condition.getQueryWrapper(tbCrdApprove));

		if (tbCrdApprove == null || StringUtil.isBlank(tbCrdApprove.getApproveId())) {//不存在，生成新的approveId
			approveId = BizNumUtil.getBizNumWithDate(JxrcbBizConstant.BIZ_TYPE_YW);
		} else {//如果已存在，使用已有的批复ID
			approveId = tbCrdApprove.getApproveId();
		}
		return null;
	}

	//校验项目额度信息
	public String checkProject(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		//没有项目额度
		if (StringUtil.isEmpty(limitDetailInfo.getProjectNum())) {
			return null;
		}

		System.out.println("项目额度编号：" + limitDetailInfo.getProjectNum());
		crdProject = iCrdProjectService.getById(limitDetailInfo.getProjectNum());
		if (crdProject == null || StringUtil.isEmpty(crdProject.getProjectNum())) {
			return "没有找到对应的项目额度信息，项目编号：" + limitDetailInfo.getProjectNum();
		}

		//批复金额大于项目额度可用金额
		if ((limitDetailInfo.getApproveAmt()).compareTo(crdProject.getAviAmt()) > 0) {
			return "批复金额【" + limitDetailInfo.getApproveAmt() + "】大于项目额度可用金额【" + crdProject.getAviAmt() + "】";
		}
		return null;
	}

	//保存项目额度使用流水信息
	public String saveApplySerialForPrj(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		//批复没有关联项目额度，不处理
		if (StringUtil.isEmpty(limitDetailInfo.getProjectNum())) {
			return null;
		}
		//批复是审批中，不处理
		if (JxrcbBizConstant.APPROVE_STATUS_0.equals(limitDetailInfo.getApproveStatus())) {
			return null;
		}

		CrdApplySerial applySerial = new CrdApplySerial();
		applySerial.setBusiDealNum(approveId);//业务编号
		applySerial.setCrdDetailPrd(crdProject.getCrdDetailPrd());//额度品种编号
		//根据批复状态设置交易类型：01	预占用  02预占用撤销  03占用  04占用撤销  05恢复  06恢复撤销  09其他
		if (JxrcbBizConstant.APPROVE_STATUS_1.equals(limitDetailInfo.getApproveStatus())) {//批复状态是：1失效
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_04);//04占用撤销
		} else if (JxrcbBizConstant.APPROVE_STATUS_2.equals(limitDetailInfo.getApproveStatus())) {//批复状态是：2生效
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_03);//03占用
		}

		//修改时，先删除已有的占用恢复记录
		if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(limitDetailInfo.getOpType())) {
			iCrdApplySerialService.remove(Condition.getQueryWrapper(applySerial));
		}

		applySerial.setTranSeqSn(rq.getTranSeqSn());//交易流水号
		applySerial.setTranDate(CommonUtil.getWorkDate());//交易日期
		applySerial.setBusiDealNum(approveId);//业务编号
		applySerial.setCrdDetailNum(crdProject.getCrdDetailNum());//三级额度编号
		applySerial.setCrdDetailPrd(crdProject.getCrdDetailPrd());//三级额度品种
		applySerial.setCrdGrantOrgNum(crdProject.getOrgNum());//授信机构
		applySerial.setCustomerNum(limitDetailInfo.getCustomerNum());//用信客户号
		applySerial.setLimitCreditAmt(limitDetailInfo.getApproveAmt());//占用/恢复金额
		applySerial.setExpCreditAmt(limitDetailInfo.getApproveAmt());//占用/恢复敞口
		applySerial.setCurrencyCd(limitDetailInfo.getCurrencyCd());
		applySerial.setIsMix(JxrcbBizConstant.NO);//是否串用
		applySerial.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		applySerial.setOrgNum(limitDetailInfo.getOrgNum());//经办机构
		applySerial.setUserNum(limitDetailInfo.getUserNum());//经办人
		applySerial.setCreateTime(CommonUtil.getWorkDateTime());
		applySerial.setUpdateTime(CommonUtil.getWorkDateTime());
		iCrdApplySerialService.save(applySerial);

		//重算项目额度
		msg = iCommonService.thirdRecount(crdProject.getCustomerNum());
		return null;
	}

	//获取客户信息
	public String checkCsm(LoanCreditApplyRequestDTO rq, LimitDetailInfoRequestDTO limitDetailInfo) {
		iCommonService.getCsmParty(limitDetailInfo.getCustomerNum());
		return null;
	}

	//校验限额
	public String checkQuota(LoanCreditApplyRequestDTO rq) {

		try {
			List<Integer> controlTypes = new ArrayList<Integer>();
			List<QuotaInfoResponseDTO> quotaInfos = new ArrayList<QuotaInfoResponseDTO>();
			for (LimitDetailInfoRequestDTO limitDetailInfo : rq.getLimitDetailInfo()) {//循环每笔额度信息校验限额
				if (JxrcbBizConstant.APPROVE_STATUS_1.equals(limitDetailInfo.getApproveStatus())) {//批复失效
					continue;
				}
				CheckBizInfoVO checkBizInfoVO = new CheckBizInfoVO();
				checkBizInfoVO.setTranSeqSn(rq.getTranSeqSn());
				checkBizInfoVO.setBizType(RcmConstant.QUOTA_BIZ_TYPE_PF);//业务类型
				if (JxrcbBizConstant.APPROVE_STATUS_0.equals(limitDetailInfo.getApproveStatus())) {
					checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_PF_ING);//控制节点
				} else {
					checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_PF_OVER);//控制节点
				}
				checkBizInfoVO.setUserNum(limitDetailInfo.getUserNum());//业务客户经理
				checkBizInfoVO.setOrgNum(iCommonService.getCorpOrgCode(limitDetailInfo.getOrgNum()));//业务网点机构
				checkBizInfoVO.setCustomerNum(limitDetailInfo.getCustomerNum());//客户编号
				//checkBizInfoVO.setCustomerName(party.getCustomerName());//客户名称
				checkBizInfoVO.setProductNum(limitDetailInfo.getProductNum());//业务品种
				checkBizInfoVO.setAmt(limitDetailInfo.getApproveAmt());//金额
				checkBizInfoVO.setCurrencyCd(limitDetailInfo.getCurrencyCd());//币种
				checkBizInfoVO.setCustomerType(DictMappingConstant.customerTypeMap.get(limitDetailInfo.getCustomerType()));//客户类型
				checkBizInfoVO.setRangeCountry(JxrcbBizConstant.COUNTRY_CHN);//国别
				checkBizInfoVO.setRangeRegion(RcmConstant.QUOTA_REGION_IN);//区域
				checkBizInfoVO.setRangeTerm(CommonUtil.getLoanTermType(limitDetailInfo.getTerm(),
					limitDetailInfo.getTermUnit()));//期限范围
				checkBizInfoVO.setRangerRiskMitigation(limitDetailInfo.getMainGuaranteeType());//风险缓释
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
