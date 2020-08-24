package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.FundGrantMain;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditExtensionRequestDTO;
import org.git.modules.clm.param.entity.Crd;

import java.util.List;

/**
 * @author liuy
 */
public interface IFundCreditExtensionService {
	/**
	 * 综合授信防重检查 查询交易事件表，若存在交易日期、业务编号、交易类型、币种，金额相同，且状态成功
	 * @param fundCreditExtension
	 * @param extAttributes
	 * @return
	 */
	boolean compositeIsRepeatEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes);

	/**
	 * 切分授信防重检查 查询交易事件表，若存在交易日期、业务编号、交易类型、币种，金额相同，且状态成功
	 * @param fundCreditExtension
	 * @param extAttributes
	 * @return
	 */
	boolean splitIsRepeatEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes);

	/**
	 * 事件落地
	 * @param fundCreditExtension
	 * @param extAttributes
	 */
	FundGrantMain registerBusiEvent(FundCreditExtensionRequestDTO fundCreditExtension, ExtAttributes extAttributes);

	/**
	 * 进行事件金额总分平衡检查
	 * @param fundCreditExtension
	 * @return
	 */
	boolean checkSplitAmt(FundCreditExtensionRequestDTO fundCreditExtension);

	/**
	 * 中间处理（授信流水拆分、授信管控检查、本地处理）
	 * @param fundGrantMain
	 * @param extAttributes
	 * @param customerNum
	 * @param eventTypeCd
	 */
	void middleHandle(FundGrantMain fundGrantMain, ExtAttributes extAttributes,String customerNum,String eventTypeCd);
}
