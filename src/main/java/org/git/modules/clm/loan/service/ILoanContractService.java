package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanContractInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanContractInfoResponseDTO;

public interface ILoanContractService {

	/**
	 * 合同申请接口校验
	 *
	 * @param rq
	 * @return rs
	 */
	LoanContractInfoResponseDTO checkContractCredit(LoanContractInfoRequestDTO rq);

	/**
	 * 合同申请接口逻辑处理
	 *
	 * @param request
	 * @return rs
	 */
	LoanContractInfoResponseDTO dealContractCredit(LoanContractInfoRequestDTO request);
}
