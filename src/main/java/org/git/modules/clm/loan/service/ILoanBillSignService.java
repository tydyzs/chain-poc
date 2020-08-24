package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.loan.LoanBillSignRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBillSignResponseDTO;

public interface ILoanBillSignService {

	/**
	 * 票据签发接口逻辑处理
	 *
	 * @param rq
	 * @return
	 */
	LoanBillSignResponseDTO checkLoanBillSign(LoanBillSignRequestDTO rq);


	/**
	 * 票据签发接口校验
	 *
	 * @param rq
	 * @return
	 */
	LoanBillSignResponseDTO dealLoanBillSign(LoanBillSignRequestDTO rq);

}
