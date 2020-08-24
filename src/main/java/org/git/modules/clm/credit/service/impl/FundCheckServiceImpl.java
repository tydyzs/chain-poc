package org.git.modules.clm.credit.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.service.IFundEventDetailService;
import org.git.modules.clm.credit.service.IFundEventMainService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.credit.service.IFundCheckService;
import org.git.modules.clm.credit.service.IParCrdRuleCtrlService;
import org.git.modules.clm.front.dto.jxrcb.fund.CertInfoRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
@Slf4j
public class FundCheckServiceImpl implements IFundCheckService {
	@Autowired
	private IParCrdRuleCtrlService parCrdRuleCtrlService;


	//错误代码-----------------------------------------------------------------------


	/**
	 * 业务准入检查
	 * @param crdDetail
	 */
	@Override
	public void checkAccess(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(!JxrcbBizConstant.CUSTOMER_ADMIT_FLAG_OK.equals(provideCrdDetail.getCrdAdmitFlag())){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20501,"额度"+provideCrdDetail.getCrdDetailNum()+"准入状态为非准入的额度！");
		}
	}

	/**
	 * 凭证依赖状态检查
	 * @param crdBusiCertInfo
	 */
	@Override
	public void checkCertStatus(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		switch(crdApplySerial.getTranTypeCd()){
			case JxrcbBizConstant.TRAN_TYPE_PRE:
				if(crdBusiCertInfo.getCertStatus()!=null && !JxrcbBizConstant.CERT_STATUS_INVALID.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为无效状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_PRE_CANCEL:
				if(!JxrcbBizConstant.CERT_STATUS_PRE.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为预占用状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_USE:
				if(!JxrcbBizConstant.CERT_STATUS_PRE.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为预占用状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_USE_CANCEL:
				if(!JxrcbBizConstant.CERT_STATUS_USED.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为占用状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_RESUME:
				if(!JxrcbBizConstant.CERT_STATUS_USED.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为占用状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_RESUME_CANCEL:
				if(!JxrcbBizConstant.CERT_STATUS_SETTLED.equals(crdBusiCertInfo.getCertStatus()) && !JxrcbBizConstant.CERT_STATUS_USED.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为占用状态或结清状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_DIRECT_USE:
				if(crdBusiCertInfo.getCertStatus()!=null
					&& !JxrcbBizConstant.CERT_STATUS_INVALID.equals(crdBusiCertInfo.getCertStatus())
						&&!JxrcbBizConstant.CERT_STATUS_SETTLED.equals(crdBusiCertInfo.getCertStatus())
					){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为无效状态或结清状态");
				}
				break;
			case JxrcbBizConstant.TRAN_TYPE_DIRECT_USE_CANCEL:
				if(!JxrcbBizConstant.CERT_STATUS_USED.equals(crdBusiCertInfo.getCertStatus())){
					AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20502,"凭证状态不为占用状态");
				}
				break;
			default:break;
		}

	}


	@Override
	public void checkCrdStatus(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(!JxrcbBizConstant.CERT_STATUS_EFFECT.equals(provideCrdMain.getCreditStatus())){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20503,provideCrdMain.getCrdMainNum()+"额度状态为非生效！");
		}
	}


	@Override
	public void countDateValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(crdMain==null){//不涉额交易，不校验
			return;
		}
		//额度生效日<=凭证起期<=额度失效日
		if(crdBusiCertInfo.getCertBeginDate().compareTo(crdMain.getBeginDate())<0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20504,"凭证开始日期小于额度开始日期！");
		}
		if(crdBusiCertInfo.getCertBeginDate().compareTo(crdMain.getEndDate())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20504,"凭证开始日期大于额度结束日期！");
		}
	}

	@Override
	public void checkMemberSplitAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(crdMain.getCrdGrantOrgNum().equals(provideCrdMain.getCrdGrantOrgNum())){
			return;//用信机构为省联社
		}
		if(JxrcbBizConstant.CONTACT_USE_YES.equals(crdDetail.getIsMix())){
			return;
		}
		if(crdApplySerial.getLimitCreditAmt().compareTo(crdDetail.getLimitAvi())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20505,crdDetail.getCrdGrantOrgNum()+"成员行切分额度剩余额度不足！");
		}
	}

	@Override
	public void checkMemberAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(crdApplySerial.getLimitCreditAmt().compareTo(crdMain.getLimitAvi())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20506,"成员行授信额度剩余额度不足！");
		}
	}

	@Override
	public void checkProviceSplitAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(JxrcbBizConstant.CONTACT_USE_YES.equals(crdDetail.getIsMix())){
			return;
		}else{
			if(crdApplySerial.getLimitCreditAmt().compareTo(provideCrdDetail.getLimitAvi())>0){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20507,crdDetail.getCrdGrantOrgNum()+"省联社切分额度剩余额度不足！");
			}
		}
	}


	@Override
	public void checkProviceAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(crdApplySerial.getLimitCreditAmt().compareTo(provideCrdMain.getLimitAvi())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20508,"省联社授信额度剩余额度不足！");
		}
	}

	@Override
	public void checkProviceCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(provideCrdMain==null
			&& JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(crdGrantingSerial.getTranTypeCd())){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20101,"省联社未对客户"+crdGrantingSerial.getCustomerNum()+"进行综合授信！");
		}
	}

	@Override
	public void checkProviceSplitCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(provideCrdDetail==null
			&& JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(crdGrantingSerial.getTranTypeCd())){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20103,"省联社未对客户"+crdGrantingSerial.getCustomerNum()+"进行产品"+crdGrantingSerial.getCrdDetailPrd()+"切分授信！");
		}
	}

	@Override
	public void checkCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(crdMain==null
			&& JxrcbBizConstant.TRAN_TYPE_SPLIT_CREDIT.equals(crdGrantingSerial.getTranTypeCd())){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20104,crdGrantingSerial.getCrdGrantOrgNum()+"机构未对客户"+crdGrantingSerial.getCustomerNum()+"进行综合授信！");
		}
	}

	@Override
	public void checkCreditAmt(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(crdGrantingSerial.getTranTypeCd())
			&& crdMain.getLimitCredit().compareTo(provideCrdMain.getLimitCredit())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20105,crdMain.getCrdGrantOrgNum()+"机构对客户"+crdMain.getCustomerNum()+"进行综合授信额度大于省联社！");
		}
	}

	@Override
	public void checkSplitCreditAmt(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(crdGrantingSerial.getTranTypeCd())
			&& crdDetail.getLimitCredit().compareTo(provideCrdDetail.getLimitCredit())>0){
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20105,crdMain.getCrdGrantOrgNum()+"机构对客户"+crdMain.getCustomerNum()+"进行产品"+crdDetail.getCrdDetailPrd()+"切分授信额度大于省联社！");
		}
	}

	@Override
	public void checkCreditDate(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(JxrcbBizConstant.TRAN_TYPE_COMPOSITE_CREDIT.equals(crdGrantingSerial.getTranTypeCd())
		  && !JxrcbBizConstant.ZJXT_SNS.equals(crdMain.getCrdGrantOrgNum())){
			if(crdMain.getBeginDate().compareTo(provideCrdMain.getBeginDate())<0){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20109,"成员行综合授信有效期起始日期小于省联社综合授信起始日期");
			}
			if(crdMain.getEndDate().compareTo(provideCrdMain.getEndDate())>0){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20109,"成员行综合授信有效期结束日期大于省联社综合授信结束日期");
			}
		}
	}

	@Override
	public void middleCheck(String eventTypeCd,CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		ParCrdRuleCtrl param = new ParCrdRuleCtrl();
		param.setEventTypeCd(eventTypeCd);
		if(crdGrantingSerial!=null){
			param.setTranTypeCd(crdGrantingSerial.getTranTypeCd());
		}else if(crdApplySerial!=null){
			param.setTranTypeCd(crdApplySerial.getTranTypeCd());
		}
		param.setCheckFlag(JxrcbBizConstant.CHECK_FLAG_YES);
		List<ParCrdRuleCtrl> rules = parCrdRuleCtrlService.list(Condition.getQueryWrapper(param));
		try {
			Class<?> clazz = this.getClass();
			for (ParCrdRuleCtrl rule : rules) {
				Method method = clazz.getMethod(rule.getCheckMethod(), CrdGrantingSerial.class, CrdApplySerial.class, CrdMain.class, CrdDetail.class, CrdBusiCertInfo.class, CrdDetail.class, CrdMain.class);
				method.invoke(clazz.newInstance(), crdGrantingSerial, crdApplySerial, crdMain, crdDetail, crdBusiCertInfo, provideCrdDetail, provideCrdMain);
			}
		} catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof ServiceException){
				ServiceException serviceException = (ServiceException) e.getTargetException();
				AssertUtil.throwServiceException(serviceException.getResultCode().getCode(),serviceException.getMessage());
			}
			log.error(e.getMessage());
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20500,"接口方法调用异常");
		}  catch (Exception e) {
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20500,"接口方法调用异常");
		}
	}

	@Override
	public void midCheck(List<ParCrdRuleCtrl> rules,CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain){
		if(rules==null || rules.isEmpty()){
			return;
		}
		try {
			Class<?> clazz = this.getClass();
			for (ParCrdRuleCtrl rule : rules) {
				Method method = clazz.getMethod(rule.getCheckMethod(), CrdGrantingSerial.class, CrdApplySerial.class, CrdMain.class, CrdDetail.class, CrdBusiCertInfo.class, CrdDetail.class, CrdMain.class);
				method.invoke(clazz.newInstance(), crdGrantingSerial, crdApplySerial, crdMain, crdDetail, crdBusiCertInfo, provideCrdDetail, provideCrdMain);
			}
		} catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof ServiceException){
				ServiceException serviceException = (ServiceException) e.getTargetException();
				AssertUtil.throwServiceException(serviceException.getResultCode().getCode(),serviceException.getMessage());
			}
			log.error(e.getMessage());
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20500,"接口方法调用异常");
		}  catch (Exception e) {
			AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20500,"接口方法调用异常");
		}
	}

	public void checkCertInfo(String tranTypeCd,CertInfoRequestDTO certInfo){
		if(JxrcbBizConstant.TRAN_TYPE_DIRECT_USE.equals(tranTypeCd)
			||JxrcbBizConstant.TRAN_TYPE_PRE.equals(tranTypeCd)){
			boolean flag = false;
			StringBuffer sb = new StringBuffer();
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
			if(StringUtil.isEmpty(certInfo.getCertSeqAmt())){
				flag = true;
				sb.append("cert_begin_date凭证起期不能为空;");
			}
			if(flag){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F20519,sb.toString());
			}
		}
	}
}
