package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyResponseDTO;

public interface ILoanCreditService {


	/**
	 * 额度申请接口逻辑处理
	 *
	 * @param rq
	 * @return rs
	 */
	LoanCreditApplyResponseDTO checkLoanCredit(LoanCreditApplyRequestDTO rq);


	/**
	 * 额度申请接口逻辑处理
	 *
	 * @param rq
	 * @return rs
	 */
	LoanCreditApplyResponseDTO dealLoanCredit(LoanCreditApplyRequestDTO rq);


}
