package org.git.modules.clm.credit.service;

import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditTransferDTO;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditOccupancyRequestDTO;

public interface IFundCreditTransfer {
	/**
	 * 事件落地
	 * @param fundCreditOccupancy
	 * @param extAttributes
	 * @return
	 */
	FundCreditTransferDTO registerBusiEvent(FundCreditOccupancyRequestDTO fundCreditOccupancy, ExtAttributes extAttributes);

	/**
	 * 中间处理
	 * @param fundCreditTransferDTO
	 */
	void middleHandle(FundCreditTransferDTO fundCreditTransferDTO, ExtAttributes extAttributes,String eventTypeCd);
}
