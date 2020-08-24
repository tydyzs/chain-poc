package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdApplyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundBatchRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseRequestDTO;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;

import java.util.List;

/**
 * @author liuy
 */
public interface IFundCreditBatchUseService {


	/**
	 * 事件落地
	 * @param fundBatchRequestDTO
	 * @param extAttributes
	 */
	List<FundEventMain> registerBusiEvent(FundBatchRequestDTO fundBatchRequestDTO, ExtAttributes extAttributes);

	/**
	 * 中间处理
	 * @param fundEventMains
	 * @param extAttributes
	 */
	void middleHandle(List<FundEventMain> fundEventMains, ExtAttributes extAttributes,String eventTypeCd);

}
