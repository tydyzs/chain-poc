package org.git.modules.clm.credit.service.impl;

import org.git.common.exception.AssertUtil;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.entity.FundGrantDetail;
import org.git.modules.clm.credit.entity.FundGrantMain;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.credit.service.*;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdSegmInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditExtensionRequestDTO;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.system.constant.DeptConstant;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author liuy
 */
@Service
public class FundCreditExtensionServiceImpl implements IFundCreditExtensionService {
	@Autowired
	private IFundGrantMainService fundGrantMainService;
	@Autowired
	private IFundGrantDetailService fundGrantDetailService;
	@Autowired
	private ICrdGrantingSerialService crdGrantingSerialService;
	@Autowired
	private ICrdMainService crdMainService;
	@Autowired
	private ICrdDetailService crdDetailService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private ICrdService crdService;
	@Autowired
	private IFundCheckService fundCheckService;
	@Autowired
	private IParCrdRuleCtrlService parCrdRuleCtrlService;
	@Autowired
	private ICsmPartyService csmPartyService;

	/**
	 * 综合授信防重检查
	 * @param fundCreditExtension
	 * @return
	 */
	@Override
	public boolean compositeIsRepeatEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes){
		FundGrantMain fundGrantMain = new FundGrantMain();
		fundGrantMain.setTranDate(extAttributes.getOriReqDate());
		fundGrantMain.setBusiDealNum(fundCreditExtension.getBusiDealNum());
		fundGrantMain.setTranTypeCd(fundCreditExtension.getTranTypeCd());
		fundGrantMain.setCrdCurrencyCd(fundCreditExtension.getCrdCurrencyCd());
		fundGrantMain.setCrdApplyAmt(new BigDecimal(fundCreditExtension.getCrdSumAmt()));

		fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		//查询交易日期、业务编号、交易类型、币种，且状态成功的数据
		int count = fundGrantMainService.count(Condition.getQueryWrapper(fundGrantMain));
		if(count == 1){
			return true;
		}
		return false;
	}

	/**
	 * 切分授信防重检查
	 * @param fundCreditExtension
	 * @return
	 */
	@Override
	public boolean splitIsRepeatEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes){
		FundGrantMain fundGrantMain = new FundGrantMain();
		fundGrantMain.setTranDate(extAttributes.getOriReqDate());
		fundGrantMain.setBusiDealNum(fundCreditExtension.getBusiDealNum());
		fundGrantMain.setCrdCurrencyCd(fundCreditExtension.getCrdCurrencyCd());
		fundGrantMain.setCrdApplyAmt(new BigDecimal(fundCreditExtension.getBusiSegmAmt()));
		fundGrantMain.setTranTypeCd(fundCreditExtension.getTranTypeCd());
		fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
		int count = fundGrantMainService.count(Condition.getQueryWrapper(fundGrantMain));
		//查询交易日期、业务编号、交易类型、币种，且状态成功的数据
		if(count == 1){
			return true;
		}
		return false;
	}



	/**
	 * 事件落地
	 * @param fundCreditExtension
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public FundGrantMain registerBusiEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes){
		//防重检查
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(fundCreditExtension.getTranTypeCd()) && compositeIsRepeatEvent(fundCreditExtension,extAttributes)){
			throw new ServiceException("此交易已处理！");
		}else if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(fundCreditExtension.getTranTypeCd()) && splitIsRepeatEvent(fundCreditExtension,extAttributes)){
			throw new ServiceException("此交易已处理！");
		}
		//进行事件金额总分平衡检查
		if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(fundCreditExtension.getTranTypeCd()) && !checkSplitAmt(fundCreditExtension)){
			throw new ServiceException("切分总额与明细额度总和不一致！");
		}
		//TODO 进行交易开关判断，若关闭则直接返回成功（暂不定）。
		FundGrantMain fundGrantMain = new FundGrantMain();
		BeanUtils.copyProperties(fundCreditExtension,fundGrantMain);

		fundGrantMain.setTranDate(extAttributes.getOriReqDate());
		fundGrantMain.setEventMainId(UUID.randomUUID().toString().replaceAll("-",""));
		fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_UNPROCES);
		fundGrantMain.setTranAcctStatus(JxrcbBizConstant.TRAN_ACCT_STATUS_UNPROCES);
		fundGrantMain.setTranSeqSn(extAttributes.getOriReqSn());
		fundGrantMain.setTranDate(extAttributes.getOriReqDate());

		FundGrantDetail fundGrantDetail = new FundGrantDetail();
		fundGrantDetail.setTranSeqSn(extAttributes.getOriReqSn());
		fundGrantDetail.setTranDate(extAttributes.getOriReqDate());
		fundGrantDetail.setCrdGrantOrgNum(fundCreditExtension.getCrdGrantOrgNum());
		fundGrantDetail.setCustomerNum(fundCreditExtension.getCustomerNum());
		fundGrantDetail.setCrdCurrencyCd(fundCreditExtension.getCrdCurrencyCd());
		fundGrantDetail.setCrdBeginDate(fundCreditExtension.getCrdBeginDate());
		fundGrantDetail.setCrdEndDate(fundCreditExtension.getCrdEndDate());
		fundGrantDetail.setEventMainId(fundGrantMain.getEventMainId());
		//资金授信事件明细表存入数据
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(fundCreditExtension.getTranTypeCd())){//综合额度
			fundGrantMain.setCrdApplyAmt(new BigDecimal(fundCreditExtension.getCrdSumAmt()));
			fundGrantDetail.setCrdMainPrd(fundCreditExtension.getCrdMainPrd());
			fundGrantDetail.setCrdSumAmt(new BigDecimal(fundCreditExtension.getCrdSumAmt()));
			fundGrantDetail.setEventDetailId(UUID.randomUUID().toString().replaceAll("-",""));
			fundGrantDetailService.save(fundGrantDetail);
		}else if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(fundCreditExtension.getTranTypeCd())){//切分额度
			fundGrantMain.setCrdApplyAmt(new BigDecimal(fundCreditExtension.getBusiSegmAmt()));
			fundGrantDetail.setBusiSegmAmt(new BigDecimal(fundCreditExtension.getBusiSegmAmt()));
			fundGrantDetail.setBusiSegmCnt(new BigDecimal(fundCreditExtension.getBusiSegmCnt()));
			List<CrdSegmInfoRequestDTO> crdSegmInfos = fundCreditExtension.getCrdSegmInfo();
			List<FundGrantDetail> addList = new ArrayList<>();
			if(crdSegmInfos!=null && !crdSegmInfos.isEmpty()) {
				for (CrdSegmInfoRequestDTO crdSegmInfo : crdSegmInfos) {
					fundGrantDetail.setEventDetailId(UUID.randomUUID().toString().replaceAll("-",""));
					fundGrantDetail.setCrdDetailPrd(crdSegmInfo.getCrdDetailPrd());
					fundGrantDetail.setCrdDetailAmt(new BigDecimal(crdSegmInfo.getCrdDetailAmt()));
					FundGrantDetail toAdd = new FundGrantDetail();
					BeanUtils.copyProperties(fundGrantDetail,toAdd);
					addList.add(toAdd);
				}
			}else{
				fundGrantDetail.setEventDetailId(UUID.randomUUID().toString().replaceAll("-",""));
				addList.add(fundGrantDetail);
			}
			fundGrantDetailService.saveBatch(addList);
		}
		//资金授信主事件表插入数据
		fundGrantMainService.save(fundGrantMain);
		return fundGrantMain;
	}


	/**
	 * 进行事件金额总分平衡检查
	 */
	@Override
	public boolean checkSplitAmt(FundCreditExtensionRequestDTO fundCreditExtension){
		List<CrdSegmInfoRequestDTO> crdSegmInfos = fundCreditExtension.getCrdSegmInfo();//
		if(crdSegmInfos==null || crdSegmInfos.isEmpty()) {
			return false;
		}
		BigDecimal total = new BigDecimal(0);
		for (CrdSegmInfoRequestDTO crdSegmInfo : crdSegmInfos) {
			total = total.add(new BigDecimal(crdSegmInfo.getCrdDetailAmt()));
		}
		if(total.compareTo(new BigDecimal(fundCreditExtension.getBusiSegmAmt())) == 0){
			return true;
		}
		return false;
	}

	/**
	 * 获取额度主表二级额度编号
	 * @param crdGrantOrgNum
	 * @param customerNum
	 * @return
	 */
	public CrdMain getCrdMain(String crdGrantOrgNum,String customerNum,String crdMainPrd){
		CrdMain crdMain = new CrdMain();
		crdMain.setCrdGrantOrgNum(crdGrantOrgNum);
		crdMain.setCustomerNum(customerNum);
		crdMain.setCrdMainPrd(crdMainPrd);
		CrdMain result = crdMainService.getOne(Condition.getQueryWrapper(crdMain));
		return result;
	}
	/**
	 * 获取额度明细表三级额度编号
	 * @param crdGrantOrgNum
	 * @param customerNum
	 * @return
	 */
	public CrdDetail getCrdDetail(String crdGrantOrgNum,String customerNum,String crdDetailPrd){
		CrdDetail crdDetail = new CrdDetail();
		crdDetail.setCrdGrantOrgNum(crdGrantOrgNum);
		crdDetail.setCustomerNum(customerNum);
		crdDetail.setCrdDetailPrd(crdDetailPrd);
		CrdDetail result = crdDetailService.getOne(Condition.getQueryWrapper(crdDetail));
		return result;
	}

	/**
	 * 成员行单业务切分额度<=省联社切分额度
	 * @param fundCreditExtension
	 * @return
	 */
	public void checkSplitCrd(FundCreditExtensionRequestDTO fundCreditExtension){
		//报文切分额度
		List<CrdSegmInfoRequestDTO> splitDetails = fundCreditExtension.getCrdSegmInfo();
		List<String> crdDetailPrds = new ArrayList<>();
		//将报文中的切分额度产品号放入list中
		for (CrdSegmInfoRequestDTO crdSegmInfo:fundCreditExtension.getCrdSegmInfo()) {
			crdDetailPrds.add(crdSegmInfo.getCrdDetailPrd());
		}
		//查询报文切分产品对应的省联社切分产品并排序
		List<CrdDetail> crdDetails =
			provideSplitCrdNum(fundCreditExtension.getCrdGrantOrgNum(),fundCreditExtension.getCustomerNum(),crdDetailPrds);
		//查询出的省联社切分产品数小于报文切分产品数时，说明报文切分的某产品在省联社切分中不存在
		if(crdDetails.size()<splitDetails.size()){
			throw new ServiceException("成员行切分额度产品未被省联社切分");
		}
		for (int i=0;i<crdDetails.size();i++) {
			if(crdDetails.get(i).getLimitCredit().compareTo(
				new BigDecimal(splitDetails.get(i).getCrdDetailAmt()))<0) {
				throw new ServiceException("成员行单业务切分额度大于省联社切分额度");
			}
		}
	}

	/**
	 * 查看是否超过省联社综合授信
	 * @param fundCredit
	 * @return
	 */
	public void chekCompositeCrd(FundCreditExtensionRequestDTO fundCredit){
		//查看省联行综合授信额度
		CrdMain crdMain =
			provideCompositeCrdNum(fundCredit.getCrdGrantOrgNum(),fundCredit.getCustomerNum(),fundCredit.getCrdMainPrd());
		//大于省联社综合授信额度
		if(crdMain.getLimitCredit().compareTo(new BigDecimal(fundCredit.getCrdSumAmt()))<0){
			throw new ServiceException("成员行综合授信额度大于省联社授信额度");
		}
		if(crdMain.getBeginDate().compareTo(fundCredit.getCrdBeginDate())>0
			||crdMain.getEndDate().compareTo(fundCredit.getCrdEndDate())<0){
			throw new ServiceException("成员行综合授信生效时间应在省联社授信生效时间范围内");
		}
	}

	/**
	 * 查询当前机构的省联社对某客户的综合授信
	 * @param deptId
	 * @param custormerNum
	 * @return
	 */
	public CrdMain provideCompositeCrdNum(String deptId,String custormerNum,String crdMainPrd){
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(deptId,DeptConstant.PROVINCIAL_COOPERATIVE);

		CrdMain crdMain = new CrdMain();
		crdMain.setCustomerNum(custormerNum);
		crdMain.setCrdGrantOrgNum(provideDept.getId().toString());
		crdMain.setCrdMainPrd(crdMainPrd);
		return crdMainService.getOne(Condition.getQueryWrapper(crdMain));
	}

	/**
	 * 查询当前机构的省联社对某客户的切分授信
	 * @param deptId
	 * @param custormerNum
	 * @return
	 */
	public List<CrdDetail> provideSplitCrdNum(String deptId,String custormerNum,List<String> crdDetailPrds){
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(deptId,DeptConstant.PROVINCIAL_COOPERATIVE);
		//当为切分额度时，查询省联社是否已进行额度授信
		return crdDetailService.listCrdDetailOrder(provideDept.getId().toString(),custormerNum,crdDetailPrds);
	}


	/**
	 * 查询当前机构的省联社对某客户的综合授信数（综合授信或切分授信）
	 * @param deptId
	 * @param tranTypeCd
	 * @return
	 */
	public int countProvideCrdNum(String deptId,String tranTypeCd,String custormerNum){
		//当前机构的省联社授信数count ,count>0时说明省联社已进行授信
		int count = 0;
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(deptId,DeptConstant.PROVINCIAL_COOPERATIVE);
		if(provideDept==null){
			throw new ServiceException("未查到此授信机构的省联社机构");
		}
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(tranTypeCd)){
			//当为综合授信时，查询省联社是否已进行额度授信
			CrdMain crdMain = new CrdMain();
			crdMain.setCustomerNum(custormerNum);
			crdMain.setCrdGrantOrgNum(provideDept.getId().toString());
			//查询省联社对此用户的综合授信
			count = crdMainService.count(Condition.getQueryWrapper(crdMain));
		}else if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(tranTypeCd)){
			//当为切分额度时，查询省联社是否已进行额度授信
			CrdDetail crdDetail = new CrdDetail();
			crdDetail.setCustomerNum(custormerNum);
			crdDetail.setCrdGrantOrgNum(provideDept.getId().toString());
			//查询省联社对此用户的切分授信数
			count = crdDetailService.count(Condition.getQueryWrapper(crdDetail));
		}
		return count;
	}

	/**
	 * 查询用户是否进行综合授信
	 * @param custormerNum
	 * @return
	 */
	public int countCompositeCrd(String custormerNum,String crdGrantOrgNum,String crdMainPrd){
		CrdMain crdMain = new CrdMain();
		crdMain.setCustomerNum(custormerNum);
		crdMain.setCrdGrantOrgNum(crdGrantOrgNum);
		crdMain.setCrdMainPrd(crdMainPrd);
		//查询此用户是否进行综合授信
		return crdMainService.count(Condition.getQueryWrapper(crdMain));
	}

	@Transactional
	@Override
	public void middleHandle(FundGrantMain fundGrantMain, ExtAttributes extAttributes,String customerNum,String eventTypeCd){
		ParCrdRuleCtrl param = new ParCrdRuleCtrl();
		param.setEventTypeCd(eventTypeCd);
		param.setTranTypeCd(fundGrantMain.getTranTypeCd());
		param.setCheckFlag(JxrcbBizConstant.CHECK_FLAG_YES);
		List<ParCrdRuleCtrl> rules = parCrdRuleCtrlService.list(Condition.getQueryWrapper(param));
		creditSeqSplit(fundGrantMain,extAttributes,rules);
		customerHandle(customerNum);
	}

	public void customerHandle(String customerNum){
		//客户信息查看同步
		CsmParty tbCsmParty = csmPartyService.getById(customerNum);
		//TODO 调用ecif 如果还是未获取到客户信息抛错，不让此业务通过
		if(tbCsmParty == null){

		}
	}


	public void creditSeqSplit(FundGrantMain fundGrantMain, ExtAttributes extAttributes,List<ParCrdRuleCtrl> rules){
		FundGrantDetail detailParams = new FundGrantDetail();
		detailParams.setEventMainId(fundGrantMain.getEventMainId());
		List<FundGrantDetail> fundGrantDetails = fundGrantDetailService.list(Condition.getQueryWrapper(detailParams));
		List<String> grantingSerialIds = new ArrayList<>();
		Timestamp now = CommonUtil.getWorkDateTime();
		CrdGrantingSerial crdGrantingSerial = new CrdGrantingSerial();//
		crdGrantingSerial.setTranSeqSn(extAttributes.getOriReqSn());//交易流水号
		crdGrantingSerial.setTranDate(extAttributes.getOriReqDate());//交易日期
		crdGrantingSerial.setBusiDealNum(fundGrantMain.getBusiDealNum());//业务编号
		crdGrantingSerial.setTranTypeCd(fundGrantMain.getTranTypeCd());//交易类型
		crdGrantingSerial.setCreateTime(now);//创建时间
		crdGrantingSerial.setUpdateTime(now);//修改时间
		crdGrantingSerial.setTranSystem(extAttributes.getRequesterId());//经办系统
		crdGrantingSerial.setUserNum(extAttributes.getOperatorId());//经办人
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(fundGrantMain.getTranTypeCd())){//综合额度
			FundGrantDetail fundGrantDetail = fundGrantDetails.get(0);
			crdGrantingSerial.setCrdGrantOrgNum(fundGrantDetail.getCrdGrantOrgNum());//授信机构
			crdGrantingSerial.setCustomerNum(fundGrantDetail.getCustomerNum());//授信客户
			crdGrantingSerial.setCrdCurrencyCd(fundGrantDetail.getCrdCurrencyCd());//币种
			crdGrantingSerial.setGrantingSerialId(UUID.randomUUID().toString().replaceAll("-",""));//授信流水id
			crdGrantingSerial.setCrdDetailPrd(fundGrantDetail.getCrdMainPrd());//额度产品
			//从额度主表查询额度号，若没有此额度则生成新的额度（客户+机构可以确认唯一额度）
			CrdMain crdMain = getCrdMain(fundGrantDetail.getCrdGrantOrgNum(),fundGrantDetail.getCustomerNum(),fundGrantDetail.getCrdMainPrd());
			if(crdMain == null){
				//生成二级额度编号
				crdGrantingSerial.setCrdDetailNum(BizNumUtil.getBizNumWithDate(JxrcbBizConstant.SECOND_CREDIT));//额度编号
			}else{
				crdGrantingSerial.setCrdDetailNum(crdMain.getCrdMainNum());
			}
			grantingSerialIds.add(crdGrantingSerial.getGrantingSerialId());
			crdGrantingSerial.setCrdBeginDate(fundGrantDetail.getCrdBeginDate());//额度起期
			crdGrantingSerial.setCrdEndDate(fundGrantDetail.getCrdEndDate());
			crdGrantingSerial.setCrdDetailAmt(fundGrantMain.getCrdApplyAmt());//授信额度
			localProcess(fundGrantMain,fundGrantDetail,crdGrantingSerial,crdMain,null,now,extAttributes,rules);
			crdGrantingSerialService.save(crdGrantingSerial);
		}else if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(fundGrantMain.getTranTypeCd())){//切分额度
			List<CrdGrantingSerial> addList = new ArrayList<>();
			if(fundGrantDetails==null || fundGrantDetails.isEmpty()) {
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20108,"额度切分信息不能为空！");
			}
			for (FundGrantDetail fundGrantDetail : fundGrantDetails) {
				crdGrantingSerial.setCrdGrantOrgNum(fundGrantDetail.getCrdGrantOrgNum());//授信机构
				crdGrantingSerial.setCustomerNum(fundGrantDetail.getCustomerNum());//授信客户
				crdGrantingSerial.setCrdCurrencyCd(fundGrantDetail.getCrdCurrencyCd());//币种
				crdGrantingSerial.setGrantingSerialId(UUID.randomUUID().toString().replaceAll("-",""));//授信流水id
				crdGrantingSerial.setCrdDetailPrd(fundGrantDetail.getCrdDetailPrd());//额度产品
				CrdDetail crdDetail = getCrdDetail(fundGrantDetail.getCrdGrantOrgNum(),fundGrantDetail.getCustomerNum(),fundGrantDetail.getCrdDetailPrd());
				if(crdDetail == null){
					//生成三级额度编号
					crdGrantingSerial.setCrdDetailNum(BizNumUtil.getBizNumWithDate(JxrcbBizConstant.THIRD_CREDIT));//额度编号
				}else{
					crdGrantingSerial.setCrdDetailNum(crdDetail.getCrdDetailNum());
				}
				grantingSerialIds.add(crdGrantingSerial.getGrantingSerialId());
				crdGrantingSerial.setCrdDetailAmt(fundGrantDetail.getCrdDetailAmt());//授信额度
				CrdGrantingSerial toAdd = new CrdGrantingSerial();
				BeanUtils.copyProperties(crdGrantingSerial,toAdd);
				localProcess(fundGrantMain,fundGrantDetail,toAdd,null,crdDetail,now,extAttributes,rules);
				addList.add(toAdd);
			}
			crdGrantingSerialService.saveBatch(addList);
		}
	}

	public void localProcess(FundGrantMain fundGrantMain,FundGrantDetail fundGrantDetail, CrdGrantingSerial crdGrantingSerial, CrdMain crdMain, CrdDetail crdDetail, Timestamp now, ExtAttributes extAttributes,List<ParCrdRuleCtrl> rules){
		String tranType = fundGrantMain.getTranTypeCd();
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(tranType)){//本地处理-综合额度
			processComposite(crdGrantingSerial,crdMain,now,extAttributes,rules);
		}else if(JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(tranType)){//本地处理-拆分额度
			Crd crd = crdService.getById(fundGrantDetail.getCrdDetailPrd());
			if(crd==null || StringUtil.isBlank(crd.getSuperCrdNum())){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20107,"获取授信产品信息失败！");
			}
			processSplit(crdGrantingSerial,crdDetail,now,crd,extAttributes,rules);
		}
	}

	/**
	 * 本地处理-综合额度
	 */
	public void processComposite(CrdGrantingSerial crdGrantingSerial,CrdMain crdMain,Timestamp now, ExtAttributes extAttributes,List<ParCrdRuleCtrl> rules){
		if(crdMain!=null){//已用授信
			BigDecimal limitUsed = crdMain.getLimitUsed()==null?new BigDecimal(0):crdMain.getLimitUsed();
			//可用授信
			BigDecimal limitAvi = crdGrantingSerial.getCrdDetailAmt().subtract(limitUsed);
			crdMain.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());//授信金额
			crdMain.setLimitAvi(limitAvi);
			crdMain.setUpdateTime(now);
		}else{
			crdMain = new CrdMain();
			crdMain.setCrdMainNum(crdGrantingSerial.getCrdDetailNum());
			crdMain.setCrdMainPrd(crdGrantingSerial.getCrdDetailPrd());
			crdMain.setCrdGrantOrgNum(crdGrantingSerial.getCrdGrantOrgNum());
			crdMain.setCustomerNum(crdGrantingSerial.getCustomerNum());
			crdMain.setCurrencyCd(crdGrantingSerial.getCrdCurrencyCd());
			crdMain.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());
			crdMain.setLimitUsed(new BigDecimal(0));
			crdMain.setLimitAvi(crdGrantingSerial.getCrdDetailAmt());
			crdMain.setLimitPre(new BigDecimal(0));
			crdMain.setBeginDate(crdGrantingSerial.getCrdBeginDate());
			crdMain.setEndDate(crdGrantingSerial.getCrdEndDate());
			crdMain.setUpdateTime(now);
			crdMain.setCreateTime(now);
			crdMain.setOrgNum(extAttributes.getBranchId());
			crdMain.setUserNum(extAttributes.getOperatorId());
			crdMain.setTranSystem(extAttributes.getRequesterId());//经办系统
			crdMain.setCreditStatus(JxrcbBizConstant.CREDIT_STATUS_FROZEN_ALL);
		}
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(crdMain.getCrdGrantOrgNum(),DeptConstant.PROVINCIAL_COOPERATIVE);
		if(provideDept==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20110,"未查询到当前机构的省联社");
		}
		//查看省联行综合授信额度
		CrdMain provideCrdMain = provideCompositeCrd(crdMain,provideDept);
		//如果是资金中心，则直接生成一条相同的省联社额度数据
		if(JxrcbBizConstant.ZJXT_SNS.equals(crdGrantingSerial.getCrdGrantOrgNum())){
			if(provideCrdMain!=null){
				provideCrdMain.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());//授信金额
				provideCrdMain.setLimitAvi(crdMain.getLimitAvi());
				crdMain.setUpdateTime(now);
			}else{
				provideCrdMain = new CrdMain();
				BeanUtils.copyProperties(crdMain,provideCrdMain);
				provideCrdMain.setCrdMainNum(BizNumUtil.getBizNumWithDate(JxrcbBizConstant.SECOND_CREDIT));
				provideCrdMain.setCrdGrantOrgNum(provideDept.getId());
			}
			provideCrdMain.setExpCredit(provideCrdMain.getLimitCredit());
			provideCrdMain.setExpAvi(provideCrdMain.getLimitAvi());
			provideCrdMain.setExpUsed(provideCrdMain.getExpUsed());
			crdMainService.saveOrUpdate(provideCrdMain);
		}
		//授信管控检查
		//fundCheckService.middleCheck(eventTypeCd,crdGrantingSerial,null,crdMain,null,null,null,provideCrdMain);
		fundCheckService.midCheck(rules,crdGrantingSerial,null,crdMain,null,null,null,provideCrdMain);
		//本地处理范围
		crdMain.setExpCredit(provideCrdMain.getLimitCredit());
		crdMain.setExpAvi(provideCrdMain.getLimitAvi());
		crdMain.setExpUsed(provideCrdMain.getExpUsed());
		crdMainService.saveOrUpdate(crdMain);
	}

	/**
	 * 查询当前机构的省联社对某客户的综合授信
	 * @param crdMain
	 * @return
	 */
	public CrdMain provideCompositeCrd(CrdMain crdMain,Dept provideDept){
		if(crdMain==null){
			return null;
		}
		if(provideDept.getId().equals(crdMain.getCrdGrantOrgNum())){
			return crdMain;
		}
		CrdMain params = new CrdMain();
		params.setCustomerNum(crdMain.getCustomerNum());
		params.setCrdGrantOrgNum(provideDept.getId());
		params.setCrdMainPrd(crdMain.getCrdMainPrd());
		return crdMainService.getOne(Condition.getQueryWrapper(params));
	}

	/**
	 * 查询成员行的切分授信对应的综合授信
	 * @param crdDetail
	 * @return
	 */
	public CrdDetail provideSplitCrd(CrdDetail crdDetail,Dept provideDept){
		if(provideDept.getId().equals(crdDetail.getCrdGrantOrgNum())){
			return crdDetail;
		}
		CrdDetail params = new CrdDetail();
		params.setCustomerNum(crdDetail.getCustomerNum());
		params.setCrdGrantOrgNum(provideDept.getId().toString());
		params.setCrdDetailPrd(crdDetail.getCrdDetailPrd());
		return crdDetailService.getOne(Condition.getQueryWrapper(params));
	}

	public void processSplit(CrdGrantingSerial crdGrantingSerial,CrdDetail crdDetail,Timestamp now,Crd crd, ExtAttributes extAttributes,List<ParCrdRuleCtrl> rules){
		CrdMain params = new CrdMain();
		params.setCrdGrantOrgNum(crdGrantingSerial.getCrdGrantOrgNum());
		params.setCrdMainPrd(crd.getSuperCrdNum());
		params.setCustomerNum(crdGrantingSerial.getCustomerNum());
		CrdMain crdMain = crdMainService.getOne(Condition.getQueryWrapper(params));
		if(crdMain==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20112,"获取二级额度信息失败!");
		}
		if(crdDetail!=null){
			//已用授信
			BigDecimal limitUsed = crdDetail.getLimitUsed()==null?new BigDecimal(0):crdDetail.getLimitUsed();
			//可用授信
			BigDecimal limitAvi = crdGrantingSerial.getCrdDetailAmt().subtract(limitUsed);
			crdDetail.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());//授信金额
			crdDetail.setLimitAvi(limitAvi);
			crdDetail.setUpdateTime(now);
		}else{
			crdDetail = new CrdDetail();
			crdDetail.setCrdDetailNum(crdGrantingSerial.getCrdDetailNum());
			crdDetail.setCrdMainNum(crdMain==null ? null:crdMain.getCrdMainNum());
			crdDetail.setCrdDetailPrd(crdGrantingSerial.getCrdDetailPrd());
			crdDetail.setCrdGrantOrgNum(crdGrantingSerial.getCrdGrantOrgNum());
			crdDetail.setCustomerNum(crdGrantingSerial.getCustomerNum());
			crdDetail.setCrdAdmitFlag(JxrcbBizConstant.CUSTOMER_ADMIT_FLAG_OK);
			crdDetail.setCurrencyCd(crdGrantingSerial.getCrdCurrencyCd());
			crdDetail.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());
			crdDetail.setLimitUsed(new BigDecimal(0));
			crdDetail.setLimitAvi(crdGrantingSerial.getCrdDetailAmt());
			crdDetail.setLimitPre(new BigDecimal(0));
			crdDetail.setBeginDate(crdGrantingSerial.getCrdBeginDate());
			crdDetail.setEndDate(crdGrantingSerial.getCrdEndDate());
			crdDetail.setUpdateTime(now);
			crdDetail.setCreateTime(now);
			crdDetail.setOrgNum(extAttributes.getBranchId());
			crdDetail.setUserNum(extAttributes.getOperatorId());
			crdDetail.setTranSystem(extAttributes.getRequesterId());//经办系统
		}
		//查询当前机构的省联社
		Dept provideDept = deptService.upperDeptByType(crdDetail.getCrdGrantOrgNum(),DeptConstant.PROVINCIAL_COOPERATIVE);
		if(provideDept==null){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20110,"未查询到当前机构的省联社");
		}
		//查看省联社切分授信额度
		CrdDetail provideCrdDetail = provideSplitCrd(crdDetail,provideDept);
		//查看省联行综合授信额度
		CrdMain provideCrdMain = provideCompositeCrd(crdMain,provideDept);
		//如是是资金中心，直接插入一条省联社授信数据
		if(JxrcbBizConstant.ZJXT_SNS.equals(crdGrantingSerial.getCrdGrantOrgNum())){
			if(provideCrdDetail!=null){
				provideCrdDetail.setLimitCredit(crdGrantingSerial.getCrdDetailAmt());//授信金额
				provideCrdDetail.setLimitAvi(crdDetail.getLimitAvi());
				provideCrdDetail.setUpdateTime(now);
			}else{
				provideCrdDetail = new CrdDetail();
				BeanUtils.copyProperties(crdDetail,provideCrdDetail);
				provideCrdDetail.setCrdDetailNum(BizNumUtil.getBizNumWithDate(JxrcbBizConstant.THIRD_CREDIT));
				provideCrdDetail.setCrdMainNum(provideCrdMain.getCrdMainNum());
				provideCrdDetail.setCrdGrantOrgNum(provideDept.getId());
			}
			provideCrdDetail.setExpCredit(provideCrdMain.getLimitCredit());
			provideCrdDetail.setExpAvi(provideCrdMain.getLimitAvi());
			provideCrdDetail.setExpUsed(provideCrdMain.getExpUsed());
			provideCrdDetail.setCrdProductType(JxrcbBizConstant.CRD_TYPE_SX);
			crdDetailService.saveOrUpdate(provideCrdDetail);
		}
		//授信管控检查
		//fundCheckService.middleCheck(eventTypeCd,crdGrantingSerial,null,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
		fundCheckService.midCheck(rules,crdGrantingSerial,null,crdMain,crdDetail,null,provideCrdDetail,provideCrdMain);
		//本地处理范围
		crdDetail.setCrdProductType(JxrcbBizConstant.CRD_TYPE_SX);
		crdMain.setCreditStatus(JxrcbBizConstant.CREDIT_STATUS_NORMAL);
		provideCrdMain.setCreditStatus(JxrcbBizConstant.CREDIT_STATUS_NORMAL);
		crdMainService.saveOrUpdate(crdMain);
		crdMainService.saveOrUpdate(provideCrdMain);
		crdDetail.setExpCredit(provideCrdMain.getLimitCredit());
		crdDetail.setExpAvi(provideCrdMain.getLimitAvi());
		crdDetail.setExpUsed(provideCrdMain.getExpUsed());
		crdDetailService.saveOrUpdate(crdDetail);
	}

}
