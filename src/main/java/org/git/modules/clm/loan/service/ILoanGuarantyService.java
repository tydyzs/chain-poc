package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.loan.LoanGuarantyInfoRequestDTO;

/**
 * 押品信息同步  服务类
 *
 * @author zhouweijie
 */
public interface ILoanGuarantyService {


	/**
	 * 检查客户信息
	 *
	 * @param requestDTO 请求实体
	 * @return 错误信息
	 */
	String checkCustomerInfo(LoanGuarantyInfoRequestDTO requestDTO);

	/**
	 * 检查押品信息
	 *
	 * @param requestDTO 请求实体
	 * @return 错误信息
	 */
	String checkPledgeInfo(LoanGuarantyInfoRequestDTO requestDTO);

	/**
	 * 同步押品信息
	 * @param requestDTO 请求实体
	 * @return 错误信息
	 */
	String mergeGuarantyInfo(LoanGuarantyInfoRequestDTO requestDTO);

}
