package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanSummaryInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanSummaryInfoResponseDTO;

public interface ILoanSummaryService {


	/**
	 * 借据同步接口校验
	 *
	 * @param rq
	 * @return
	 */
	LoanSummaryInfoResponseDTO checkLoanSummary(LoanSummaryInfoRequestDTO rq);

	/**
	 * 借据同步接口逻辑处理
	 *
	 * @param rq
	 * @return
	 */
	LoanSummaryInfoResponseDTO dealLoanSummary(LoanSummaryInfoRequestDTO rq);
}
