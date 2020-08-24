package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdApplyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author liuy
 */
public interface IFundCreditUseService {


	/**
	 * 进行事件用信金额与凭证数组汇总金额平衡检查
	 * @param fundCreditUse
	 */
	void checkBalance(FundCreditUseRequestDTO fundCreditUse);

	/**
	 * 防重检查
	 * @param fundCreditUse
	 */
	void checkRepeatEvent(FundCreditUseRequestDTO fundCreditUse, ExtAttributes extAttributes);

	/**
	 * 事件落地
	 * @param fundCreditUse
	 */
	FundEventMain registerBusiEvent(FundCreditUseRequestDTO fundCreditUse, ExtAttributes extAttributes);

	/**
	 * 根据交易类型，修改凭证状态和金额
	 * @param tranTypeCd
	 * @param newCertInfo
	 * @param crdBusiCertInfo
	 */
	void setCertStatusAndAmt(String tranTypeCd,CrdBusiCertInfo newCertInfo,CrdBusiCertInfo crdBusiCertInfo);

	/**
	 * 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 * @param now
	 */
	void localProcess(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 中间处理
	 * @param fundEventMain
	 * @param extAttributes
	 * @param eventTypeCd
	 */
	void middleHandle(FundEventMain fundEventMain,ExtAttributes extAttributes,String eventTypeCd);


	/**
	 * 客户信息处理
	 * @param crdApplyInfos
	 */
	void customerHanle(List<CrdApplyInfoRequestDTO> crdApplyInfos);


	/**
	 * 预占用 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForPre(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo, CrdDetail provideCrdDetail, CrdMain provideCrdMain, Timestamp now);


	/**
	 * 预占用撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForPreCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 占用 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForUse(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 占用撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForUseCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 恢复 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForResume(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);


	/**
	 * 恢复撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForResumeCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 直接占用 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForDirectUse(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

	/**
	 * 直接占用撤销 额度处理
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 * @param provideCrdDetail
	 * @param provideCrdMain
	 */
	void updateForDirectUseCancel(CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain,Timestamp now);

}
