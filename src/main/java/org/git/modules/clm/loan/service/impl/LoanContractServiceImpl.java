package org.git.modules.clm.loan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.constant.DictMappingConstant;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.entity.*;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.customer.service.IECIFCustomerInfoService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.loan.*;
import org.git.modules.clm.loan.service.ILoanContractService;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.ICrdProductService;
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
 * 合同申请接口实现类
 *
 * @author chenchuan
 */
@Slf4j
@Service
@Transactional
public class LoanContractServiceImpl implements ILoanContractService {

	@Autowired
	ITbCrdContractEventService iTbCrdContractEventService;
	@Autowired
	ITbCrdContractService iTbCrdContractService;
	@Autowired
	ITbCrdSubcontractConService iTbCrdSubcontractConService;
	@Autowired
	ITbCrdSubcontractService iTbCrdSubcontractService;
	@Autowired
	ITbCrdSubcontractSuretyService iTbCrdSubcontractSuretyService;
	@Autowired
	ITbCrdSuretyService iTbCrdSuretyService;
	@Autowired
	ITbCrdApproveService iTbCrdApproveService;
	@Autowired
	ICommonService iCommonService;
	@Autowired
	ICrdProductService iCrdProductService;
	@Autowired
	ICsmPartyService iCsmPartyService;
	@Autowired
	ICrdApplySerialService iCrdApplySerialService;
	@Autowired
	IProductService iProductService;
	@Autowired
	RcmQuotaCheckService rcmQuotaCheckService;
	@Autowired
	IECIFCustomerInfoService iecifCustomerInfoService;

	String msg = null;
	String crdDetailPrd = null;//三级额度品种
	String crdMainPrd = null;//二级额度品种
	TbCrdApprove tbCrdApprove = new TbCrdApprove();
	CsmParty party = new CsmParty();
	Product product = new Product();
	LoanContractInfoResponseDTO rs;

	@Override
	public LoanContractInfoResponseDTO checkContractCredit(LoanContractInfoRequestDTO rq) {
		log.info("--------------------进入合同申请接口程序--------------------");
		rs = new LoanContractInfoResponseDTO();
		rs.setBizScene(rq.getBizScene());
		rs.setBizAction(rq.getBizAction());
		rs.setContractNum(rq.getContractNum());
		//获取批复信息、品种信息（product）
		msg = checkApprove(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//判断操作类型
		msg = checkOpType(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//如果操作类型是删除，不进行校验
		if (JxrcbBizConstant.OP_TYPE_DELETE.equals(rq.getOpType())) {
			return rs;
		}

		//获取批复信息,并校验批复额度，获取tbCrdApprove
		msg = checkCrd(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);


		//校验客户信息
		msg = checkCsm(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//校验限额
		msg = checkQuota(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		return rs;
	}

	@Override
	public LoanContractInfoResponseDTO dealContractCredit(LoanContractInfoRequestDTO rq) {
		log.info("--------------------进入合同申请接口程序--------------------");

		//保存合同事件信息
		msg = saveConEvent(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//保存合同信息
		msg = saveCon(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//修改和删除时，删除现有的担保合同和合同的关联关系
		if (JxrcbBizConstant.OP_TYPE_DELETE.equals(rq.getOpType())
			|| JxrcbBizConstant.OP_TYPE_UPDATE.equals(rq.getOpType())) {
			delSubcontractCon(rq);
		}

		//保存担保合同信息
		msg = saveSubcontract(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//保存押品信息,获取抵押额度产品编号、额度编号
		msg = savePledge(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//保证人信息分组，获取保证额度产品编号、额度编号
		msg = saveSurety(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//保存授信额度流水表
		msg = saveApplySerialForCrd(rq);
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		//调用授信额度重算、统计
		msg = iCommonService.creditRecountAStatis(tbCrdApprove.getCustomerNum());
		AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10201, msg);

		return rs;
	}

	public String checkApprove(LoanContractInfoRequestDTO rq) {
		//删除时，根据合同号查询批复信息
		if (JxrcbBizConstant.OP_TYPE_DELETE.equals(rq.getOpType())) {
			TbCrdContract tbCrdContract = iTbCrdContractService.getById(rq.getContractNum());
			if (tbCrdContract == null) {
				return "没有找到对应的合同信息，合同编号：" + rq.getContractNum();
			}
			//根据批复编号和品种查询批复ID
			tbCrdApprove = iTbCrdApproveService.getById(tbCrdContract.getApproveId());
			if (tbCrdApprove == null || StringUtil.isEmpty(tbCrdApprove.getApproveId())) {
				return "没有找到对应的批复信息，申请编号：" + rq.getApproveNum() + "，业务品种：" + rq.getProductNum() +
					"，担保方式：" + rq.getMainGuaranteeType() + "，业务种类：" + rq.getProductType();
			}
			return null;
		}

		//查询业务品种信息
		product = iProductService.getById(rq.getProductNum());
		if (product == null || StringUtil.isEmpty(product.getLimitUsedType())) {
			return "未配置业务品种：" + product.getLimitUsedType();
		}

		//根据批复编号和品种查询批复ID
		TbCrdApprove tbCrdApproveT = new TbCrdApprove();
		tbCrdApproveT.setApproveNum(rq.getApproveNum());
		tbCrdApproveT.setProductNum(rq.getProductNum());
		tbCrdApproveT.setProductType(rq.getProductType());
		tbCrdApproveT.setMainGuaranteeType(rq.getMainGuaranteeType());
		tbCrdApprove = iTbCrdApproveService.getOne(Condition.getQueryWrapper(tbCrdApproveT));
		if (tbCrdApprove == null || StringUtil.isEmpty(tbCrdApprove.getApproveId())) {
			return "没有找到对应的批复信息，申请编号：" + rq.getApproveNum() + "，业务品种：" + rq.getProductNum() +
				"，担保方式：" + rq.getMainGuaranteeType() + "，业务种类：" + rq.getProductType();
		}

		return null;
	}

	//校验额度信息
	public String checkCrd(LoanContractInfoRequestDTO rq) {
		//借据占用额度，不处理信息
		if (JxrcbBizConstant.LIMIT_USED_TYPE_JJ.equals(product.getLimitUsedType())) {
			return null;
		}
		System.out.println("批复时间戳：" + tbCrdApprove.getUpdateTime());

		BigDecimal approveAvi = tbCrdApprove.getApproveAvi();//批复可用
		if (approveAvi == null) {
			approveAvi = BigDecimal.ZERO;
		}
		TbCrdContract contract = iTbCrdContractService.getById(rq.getContractNum());
		if (contract != null && !StringUtil.isEmpty(contract.getContractNum())) {//合同已存在
			approveAvi = approveAvi.add(contract.getContractAmt());//修改时，批复可用先加上修改前的合同金额
		}

		if (rq.getContractAmt().compareTo(approveAvi) > 0) {
			return "合同金额：" + rq.getContractAmt() + "，大于批复可用金额：" + approveAvi;
		}
		return null;
	}

	//判断操作类型
	public String checkOpType(LoanContractInfoRequestDTO rq) {
		TbCrdContractEvent TbCrdContractEvent = iTbCrdContractEventService.getById(rq.getTranSeqSn());//根据合同号查询合同信息
		if (TbCrdContractEvent != null) {
			return "交易流水号重复：" + rq.getTranSeqSn();
		}
		return null;
	}

	/**
	 * 保存合同事件信息
	 *
	 * @param rq
	 */
	public String saveConEvent(LoanContractInfoRequestDTO rq) {
		try {
			TbCrdContractEvent tbCrdContractEvent = new TbCrdContractEvent();
			tbCrdContractEvent.setTranSeqSn(rq.getTranSeqSn());//交易流水号
			tbCrdContractEvent.setOpType(rq.getOpType());//操作类型
			tbCrdContractEvent.setBizAction(rq.getBizAction());//业务场景
			tbCrdContractEvent.setBizScene(rq.getBizScene());//流程节点
			tbCrdContractEvent.setContractNum(rq.getContractNum());//合同编号
			tbCrdContractEvent.setApproveId(tbCrdApprove.getApproveId());//批复ID
			tbCrdContractEvent.setCustomerNum(tbCrdApprove.getCustomerNum());
			tbCrdContractEvent.setProductNum(rq.getProductNum());//业务品种
			tbCrdContractEvent.setCurrencyCd(rq.getCurrencyCd());//币种
			tbCrdContractEvent.setExchangeRate(rq.getExchangeRate());//汇率
			tbCrdContractEvent.setContractAmt(rq.getContractAmt());//合同金额
			tbCrdContractEvent.setBeginDate(rq.getBeginDate());//合同起期
			tbCrdContractEvent.setEndDate(rq.getEndDate());//合同止期
			tbCrdContractEvent.setIsCycle(rq.getIsCycle());//合同循环标志
			tbCrdContractEvent.setIndustry(rq.getIndustry());//行业投向
			tbCrdContractEvent.setGuaranteeType(rq.getGuaranteeType());//担保方式
			tbCrdContractEvent.setMainGuaranteeType(rq.getMainGuaranteeType());//主担保方式
			tbCrdContractEvent.setGuaranteeTypeDetail(rq.getGuaranteeTypeDetail());//担保方式分类
			tbCrdContractEvent.setDepositRatio(rq.getDepositRatio());//保证金比例
			tbCrdContractEvent.setContractStatus(rq.getContractStatus());//合同状态
			tbCrdContractEvent.setUserNum(rq.getUserNum());//用户编号
			tbCrdContractEvent.setOrgNum(rq.getOrgNum());//机构号
			tbCrdContractEvent.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
			tbCrdContractEvent.setTranDate(CommonUtil.getWorkDate());
			tbCrdContractEvent.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdContractEvent.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			iTbCrdContractEventService.save(tbCrdContractEvent);
		} catch (Exception e) {
			e.printStackTrace();
			return "保存合同信息异常";
		}
		return null;
	}

	/**
	 * 保存合同信息
	 *
	 * @param rq
	 */
	public String saveCon(LoanContractInfoRequestDTO rq) {
		//保存合同信息
		TbCrdContract tbCrdContract = new TbCrdContract();
		tbCrdContract.setContractNum(rq.getContractNum());//合同编号，主键
		tbCrdContract.setApproveId(tbCrdApprove.getApproveId());//批复ID
		tbCrdContract.setCustomerNum(tbCrdApprove.getCustomerNum());//客户编号
		tbCrdContract.setProductNum(rq.getProductNum());//业务品种
		tbCrdContract.setCurrencyCd(rq.getCurrencyCd());//币种
		tbCrdContract.setExchangeRate(rq.getExchangeRate());//汇率
		tbCrdContract.setContractAmt(rq.getContractAmt());//合同金额
		tbCrdContract.setBeginDate(rq.getBeginDate());//合同起期
		tbCrdContract.setEndDate(rq.getEndDate());//合同止期
		tbCrdContract.setIsCycle(rq.getIsCycle());//合同循环标志
		tbCrdContract.setIndustry(rq.getIndustry());//行业投向
		tbCrdContract.setGuaranteeType(rq.getGuaranteeType());//担保方式
		tbCrdContract.setMainGuaranteeType(rq.getMainGuaranteeType());//主担保方式
		tbCrdContract.setGuaranteeTypeDetail(rq.getGuaranteeTypeDetail());//担保方式分类
		tbCrdContract.setDepositRatio(rq.getDepositRatio());//保证金比例
		//如果操作类型是删除，将合同置为注销
		if (JxrcbBizConstant.OP_TYPE_DELETE.equals(rq.getOpType())) {
			tbCrdContract.setContractStatus(JxrcbBizConstant.CONTRACT_STATUS_3);//合同状态
		} else {
			tbCrdContract.setContractStatus(rq.getContractStatus());//合同状态
		}
		tbCrdContract.setUserNum(rq.getUserNum());//用户编号
		tbCrdContract.setOrgNum(rq.getOrgNum());//机构号
		tbCrdContract.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		tbCrdContract.setTranDate(CommonUtil.getWorkDate());//交易日期
		if (JxrcbBizConstant.OP_TYPE_ADD.equals(rq.getOpType())) {
			tbCrdContract.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
		}
		tbCrdContract.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
		iTbCrdContractService.saveOrUpdate(tbCrdContract);
		return null;
	}

	/**
	 * 保存担保合同信息
	 *
	 * @param rq
	 */
	public String saveSubcontract(LoanContractInfoRequestDTO rq) {
		//保存担保合同信息
		List<TbCrdSubcontract> tbCrdSubcontractList = new ArrayList<TbCrdSubcontract>();
		List<TbCrdSubcontractCon> tbCrdSubcontractConList = new ArrayList<TbCrdSubcontractCon>();

		List<SubContractInfoRequestDTO> subcontractInfoList = rq.getSubcontractInfo();
		if (subcontractInfoList == null) {
			return null;
		}
		for (SubContractInfoRequestDTO subcontractInfo : subcontractInfoList) {
			//担保合同信息
			TbCrdSubcontract tbCrdSubcontract = new TbCrdSubcontract();
			tbCrdSubcontract.setSubcontractNum(subcontractInfo.getSubcontractNum());//担保合同编号
			tbCrdSubcontract.setSubcontractType(subcontractInfo.getSubcontractType());//担保合同类型
			tbCrdSubcontract.setSubcontractAmt((subcontractInfo.getSubcontractAmt()));//担保合同类型
			tbCrdSubcontract.setConCustomerNum(tbCrdApprove.getCustomerNum());
			tbCrdSubcontract.setIsTop(subcontractInfo.getIsTop());//是否最高额担保合同
			tbCrdSubcontract.setCurrencyCd(subcontractInfo.getCurrencyCd());//币种
			tbCrdSubcontract.setExchangeRate(subcontractInfo.getExchangeRate());//汇率
			tbCrdSubcontract.setBeginDate(subcontractInfo.getBeginDate());//担保合同起期
			tbCrdSubcontract.setEndDate(subcontractInfo.getEndDate());//担保合同止期
			tbCrdSubcontract.setUserNum(rq.getUserNum());//经办人
			tbCrdSubcontract.setOrgNum(rq.getOrgNum());//经办机构
			tbCrdSubcontract.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdSubcontract.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			tbCrdSubcontractList.add(tbCrdSubcontract);

			//担保合同与业务合同关联关系
			TbCrdSubcontractCon tbCrdSubcontractCon = new TbCrdSubcontractCon();
			tbCrdSubcontractCon.setContractNum(rq.getContractNum());//合同编号
			tbCrdSubcontractCon.setSubcontractNum(subcontractInfo.getSubcontractNum());//担保合同编号
			tbCrdSubcontractCon.setSuretyAmt(subcontractInfo.getSuretyAmt());//本粗担保金额
			tbCrdSubcontractCon.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdSubcontractCon.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			tbCrdSubcontractConList.add(tbCrdSubcontractCon);

			//删除担保合同下已关联的担保物
			delSubcontractSurety(subcontractInfo.getSubcontractNum());
		}

		iTbCrdSubcontractService.saveOrUpdateBatch(tbCrdSubcontractList);
		iTbCrdSubcontractConService.saveBatch(tbCrdSubcontractConList);
		return null;
	}

	/**
	 * 保存押品信息
	 *
	 * @param rq
	 */
	public String savePledge(LoanContractInfoRequestDTO rq) {
		//保存押品信息分组
		List<PledgeInfoRequestDTO> pledgeInfoList = rq.getPledgeInfo();
		if (pledgeInfoList == null) {
			return null;
		}
		for (PledgeInfoRequestDTO pledgeInfo : pledgeInfoList) {
			//获取押品对应的额度品种
			String msg = getCrdProduct(pledgeInfo.getCustomerType(), pledgeInfo.getGuaranteeType());//获取额度品种
			if (StringUtil.isNotBlank(msg)) {
				return msg;
			}
			TbCrdSurety tbCrdSurety = new TbCrdSurety();
			tbCrdSurety.setSuretyNum(pledgeInfo.getSuretyNum());
			tbCrdSurety.setCrdMainPrd(crdMainPrd);
			tbCrdSurety.setCrdDetailPrd(crdDetailPrd);
			tbCrdSurety.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdSurety.setSuretyNum(pledgeInfo.getSuretyNum());//押品编号
			tbCrdSurety.setPledgeName(pledgeInfo.getPledgeName());//押品名称
			tbCrdSurety.setGuaranteeType(pledgeInfo.getGuaranteeType());//担保方式
			tbCrdSurety.setPledgeType(pledgeInfo.getPledgeType());//押品类型
			tbCrdSurety.setCustomerNum(pledgeInfo.getCustomerNum());//押品持有人ECIF客户号
			tbCrdSurety.setPledgeRate(pledgeInfo.getPledgeRate());//抵质押率
			tbCrdSurety.setAmtAsses(pledgeInfo.getAmtAsses());//评估价值
			tbCrdSurety.setAmtActual(pledgeInfo.getAmtActual());//权利价值
			tbCrdSurety.setCurrencyCd(JxrcbBizConstant.CURRENCY_CNY);
			tbCrdSurety.setExchangeRate(BigDecimal.ONE);
			tbCrdSurety.setUserNum(rq.getUserNum());//经办人
			tbCrdSurety.setOrgNum(rq.getOrgNum());//经办机构
			tbCrdSurety.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			iTbCrdSuretyService.saveOrUpdate(tbCrdSurety);

			//保存押品与担保合同关联关系
			TbCrdSubcontractSurety tbCrdSubcontractSurety = new TbCrdSubcontractSurety();
			tbCrdSubcontractSurety.setSubcontractNum(pledgeInfo.getSubcontractNum());//担保合同编号
			tbCrdSubcontractSurety.setSuretyNum(pledgeInfo.getSuretyNum());//押品编号
			tbCrdSubcontractSurety.setSuretyAmt(pledgeInfo.getSuretyAmt());//本次担保金额
			tbCrdSubcontractSurety.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdSubcontractSurety.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			iTbCrdSubcontractSuretyService.save(tbCrdSubcontractSurety);

			//重算担保额度
			iCommonService.guaranteeRecount(pledgeInfo.getCustomerNum());
		}
		return null;
	}


	/**
	 * 保存保证人信息
	 *
	 * @param rq
	 */
	public String saveSurety(LoanContractInfoRequestDTO rq) {

		List<SuretyInfoRequestDTO> suretyInfoList = rq.getSuretyInfo();
		if (suretyInfoList == null) {
			return null;
		}
		for (SuretyInfoRequestDTO suretyInfo : suretyInfoList) {
			String msg = getCrdProduct(suretyInfo.getCustomerType(), JxrcbBizConstant.GUARANTEE_TYPE_BZ);//获取额度品种
			if (StringUtil.isNotBlank(msg)) {
				return msg;
			}
			//查询保证人是否存在
			TbCrdSurety tbCrdSurety = new TbCrdSurety();
			tbCrdSurety.setCustomerNum(suretyInfo.getCustomerNum());
			tbCrdSurety.setGuaranteeType(JxrcbBizConstant.GUARANTEE_TYPE_BZ);
			tbCrdSurety = iTbCrdSuretyService.getOne(Condition.getQueryWrapper(tbCrdSurety));

			if (tbCrdSurety == null || StringUtil.isEmpty(tbCrdSurety.getSuretyNum())) {//保证人此前不存在
				tbCrdSurety = new TbCrdSurety();
				tbCrdSurety.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
				tbCrdSurety.setSuretyNum(BizNumUtil.getBizNumWithDate(JxrcbBizConstant.BIZ_TYPE_BZ));
			}
			tbCrdSurety.setCrdMainPrd(crdMainPrd);
			tbCrdSurety.setCrdDetailPrd(crdDetailPrd);

			tbCrdSurety.setGuaranteeType(JxrcbBizConstant.GUARANTEE_TYPE_BZ);//担保方式
			tbCrdSurety.setCustomerNum(suretyInfo.getCustomerNum());//保证人客户号
			tbCrdSurety.setAmtAsses(suretyInfo.getSuretyAmt());//权利价值
			tbCrdSurety.setAmtActual(suretyInfo.getSuretyAmt());//权利价值
			tbCrdSurety.setCurrencyCd(JxrcbBizConstant.CURRENCY_CNY);
			tbCrdSurety.setExchangeRate(BigDecimal.ONE);
			tbCrdSurety.setUserNum(rq.getUserNum());//经办人
			tbCrdSurety.setOrgNum(rq.getOrgNum());//经办机构
			tbCrdSurety.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			iTbCrdSuretyService.saveOrUpdate(tbCrdSurety);

			//保存保证人与担保合同关联关系
			TbCrdSubcontractSurety tbCrdSubcontractSurety = new TbCrdSubcontractSurety();
			tbCrdSubcontractSurety.setSubcontractNum(suretyInfo.getSubcontractNum());//担保合同编号
			tbCrdSubcontractSurety.setSuretyNum(tbCrdSurety.getSuretyNum());//押品ID
			tbCrdSubcontractSurety.setSuretyAmt(suretyInfo.getSuretyAmt());//本次担保金额
			tbCrdSubcontractSurety.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
			tbCrdSubcontractSurety.setUpdateTime(CommonUtil.getWorkDateTime());//更新时间
			iTbCrdSubcontractSuretyService.save(tbCrdSubcontractSurety);

			//重算担保额度
			iCommonService.guaranteeRecount(suretyInfo.getCustomerNum());
		}
		return null;
	}


	/**
	 * @param custType
	 * @param guaranteeType
	 * @return
	 */
	public String getCrdProduct(String custType, String guaranteeType) {
		try {
			CrdProduct crdProduct = new CrdProduct();
			crdProduct.setCustType(DictMappingConstant.customerTypeMap.get(custType));//客户类型
			crdProduct.setCrdProductType(JxrcbBizConstant.CRD_TYPE_DB);//担保额度
			if (JxrcbBizConstant.YES.equals(tbCrdApprove.getIsJointGuarantee())) {//如果是联保业务
				crdProduct.setMainGuaranteeType(JxrcbBizConstant.GUARANTEE_TYPE_LB);//担保方式--联保
			} else {
				crdProduct.setMainGuaranteeType(guaranteeType);//担保方式--联保
			}
			Crd crd = iCrdProductService.selectCrd(crdProduct);
			if (crd == null || StringUtil.isEmpty(crd.getCrdProductNum())) {
				return "没有找到对应的担保额度品种，担保方式：" + crdProduct.getMainGuaranteeType() + "，客户类型：" +
					crdProduct.getCustType();
			}
			crdDetailPrd = crd.getCrdProductNum();//三级额度品种
			crdMainPrd = crd.getSuperCrdNum();//二级额度品种
			System.out.println("获取到额度品种：" + crd.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return "查询额度品种出现异常：" + e.getMessage();
		}
		return null;
	}


	/**
	 * 删除合同下所有的担保合同关联关系
	 *
	 * @param rq
	 * @return
	 */
	public String delSubcontractCon(LoanContractInfoRequestDTO rq) {
		TbCrdSubcontractCon tbCrdSubcontractCon = new TbCrdSubcontractCon();
		tbCrdSubcontractCon.setContractNum(rq.getContractNum());
		iTbCrdSubcontractConService.remove(Condition.getQueryWrapper(tbCrdSubcontractCon));
		return null;
	}


	/**
	 * 删除担保合同下所有的担保物关联关系
	 *
	 * @param subcontractNum
	 * @return
	 */
	public String delSubcontractSurety(String subcontractNum) {
		TbCrdSubcontractSurety tbCrdSubcontractSurety = new TbCrdSubcontractSurety();
		tbCrdSubcontractSurety.setSubcontractNum(subcontractNum);
		iTbCrdSubcontractSuretyService.remove(Condition.getQueryWrapper(tbCrdSubcontractSurety));
		return null;
	}


	//保存授信额度使用、恢复流水信息
	public String saveApplySerialForCrd(LoanContractInfoRequestDTO rq) {
		//业务品种为借据占用时，不处理
		if (JxrcbBizConstant.LIMIT_USED_TYPE_JJ.equals(product.getLimitUsedType())) {
			return null;
		}

		CrdApplySerial applySerial = new CrdApplySerial();
		applySerial.setBusiDealNum(rq.getContractNum());//合同编号
		applySerial.setCrdDetailPrd(tbCrdApprove.getCrdDetailPrd());//额度品种编号
		//根据合同状态设置交易类型：01	预占用  02预占用撤销  03占用  04占用撤销  05恢复  06恢复撤销  09其他
		if (JxrcbBizConstant.CONTRACT_STATUS_0.equals(rq.getContractStatus()) ||
			JxrcbBizConstant.CONTRACT_STATUS_1.equals(rq.getContractStatus())) {//合同状态为：0待审核 ,1已审核
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_01);//01预占用
		} else if (JxrcbBizConstant.CONTRACT_STATUS_2.equals(rq.getContractStatus())) {//合同状态为：2已生效
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_03);//03占用
		} else if (JxrcbBizConstant.CONTRACT_STATUS_3.equals(rq.getContractStatus()) ||
			JxrcbBizConstant.CONTRACT_STATUS_4.equals(rq.getContractStatus())) {//合同状态为：3已注销 ,4已失效
			if (JxrcbBizConstant.NO.equals(tbCrdApprove.getIsCycle())) {//如果额度是非循环的，则不登记额度恢复信息
				return null;
			}
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_05);//05恢复
		} else {
			applySerial.setTranTypeCd(JxrcbBizConstant.SERIAL_TRAN_TYPE_05);//恢复
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
		applySerial.setLimitCreditAmt(rq.getContractAmt());//占用/恢复金额
		applySerial.setExpCreditAmt(CommonUtil.getExpAmt(rq.getContractAmt(), rq.getDepositRatio()));//占用/恢复敞口
		applySerial.setCurrencyCd(rq.getCurrencyCd());
		applySerial.setIsMix(JxrcbBizConstant.NO);//是否串用
		applySerial.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		applySerial.setOrgNum(rq.getOrgNum());//经办机构
		applySerial.setUserNum(rq.getUserNum());//经办人
		applySerial.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
		applySerial.setCreateTime(CommonUtil.getWorkDateTime());
		applySerial.setUpdateTime(CommonUtil.getWorkDateTime());
		iCrdApplySerialService.save(applySerial);
		return null;
	}

	//校验限额
	public String checkQuota(LoanContractInfoRequestDTO rq) {
		//合同注销、合同失效不校验限额
		if (JxrcbBizConstant.CONTRACT_STATUS_3.equals(rq.getContractStatus()) ||
			JxrcbBizConstant.CONTRACT_STATUS_4.equals(rq.getContractStatus())) {
			rs.setControlType(RcmConstant.QUOTA_NODE_MEASURE_PASS);//管控类型为通过
			return null;
		}

		try {
			CheckBizInfoVO checkBizInfoVO = new CheckBizInfoVO();
			checkBizInfoVO.setTranSeqSn(rq.getTranSeqSn());
			checkBizInfoVO.setBizType(RcmConstant.QUOTA_BIZ_TYPE_HT);//业务类型
			if (JxrcbBizConstant.CONTRACT_STATUS_0.equals(rq.getContractStatus()) ||
				JxrcbBizConstant.CONTRACT_STATUS_1.equals(rq.getContractStatus())) {
				checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_HT_ING);//控制节点
			} else {
				checkBizInfoVO.setControlNode(RcmConstant.QUOTA_CONTROL_NODE_HT_OVER);//控制节点
			}
			checkBizInfoVO.setUserNum(rq.getUserNum());//业务客户经理
			checkBizInfoVO.setOrgNum(iCommonService.getCorpOrgCode(rq.getOrgNum()));//业务网点机构
			checkBizInfoVO.setCustomerNum(tbCrdApprove.getCustomerNum());//客户编号
			checkBizInfoVO.setCustomerName(party.getCustomerName());//客户名称
			checkBizInfoVO.setProductNum(rq.getProductNum());//业务品种
			checkBizInfoVO.setAmt(CommonUtil.stringToBigDecimal(rq.getContractNum()));//金额
			checkBizInfoVO.setCurrencyCd(rq.getCurrencyCd());//币种
			checkBizInfoVO.setCustomerType(party.getCustomerType());//客户类型
			checkBizInfoVO.setRangeCountry(JxrcbBizConstant.COUNTRY_CHN);//国别
			checkBizInfoVO.setRangeRegion("1");//区域
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

	//获取客户信息
	public String checkCsm(LoanContractInfoRequestDTO rq) {
		party = iCommonService.getCsmParty(tbCrdApprove.getCustomerNum());
		return null;
	}
}
