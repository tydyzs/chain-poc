package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.front.dto.jxrcb.fund.CertInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;

import java.util.List;

/**
 * @author liuy
 */
public interface IFundCheckService {


	/**
	 * 业务准入检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkAccess(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 凭证依赖状态检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkCertStatus(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 额度状检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkCrdStatus(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 凭证起始日检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void countDateValid( CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 成员行明细额度可用余额检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkMemberSplitAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 成员行总额度可用余额检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkMemberAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 省联社明细额度可用余额检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkProviceSplitAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 省联社明细额度可用余额检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkProviceAviValid(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 省联社是否进行综合授信检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkProviceCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 省联社是否进行切分授信检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkProviceSplitCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 客户是否综合授信检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkCredit(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 综合授信额度检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkCreditAmt(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 切分授信额度检查
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkSplitCreditAmt(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 成员行综合授信生效日期区间校验
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void checkCreditDate(CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 中间检查
	 * @param eventTypeCd
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void middleCheck(String eventTypeCd,CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo,CrdDetail provideCrdDetail,CrdMain provideCrdMain);

	/**
	 * 中间检查
	 * @param rules
	 * @param crdGrantingSerial
	 * @param crdApplySerial
	 * @param crdMain
	 * @param crdDetail
	 * @param crdBusiCertInfo
	 */
	void midCheck(List<ParCrdRuleCtrl> rules, CrdGrantingSerial crdGrantingSerial, CrdApplySerial crdApplySerial, CrdMain crdMain, CrdDetail crdDetail, CrdBusiCertInfo crdBusiCertInfo, CrdDetail provideCrdDetail, CrdMain provideCrdMain);

	/**
	 * 检查凭证必输字段
	 * @param tranTypeCd
	 * @param certInfo
	 */
	void checkCertInfo(String tranTypeCd, CertInfoRequestDTO certInfo);
}
