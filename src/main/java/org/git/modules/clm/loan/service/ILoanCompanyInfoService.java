package org.git.modules.clm.loan.service;

import org.git.modules.clm.customer.entity.CsmParty;

/**
 * 信贷系统-对公客户信息同步 服务类
 *
 * @author zhouweijie
 */
public interface ILoanCompanyInfoService {

	/**
	 * 同步对公客户信息
	 *
	 * @param customer 客户编号
	 * @return 对公客户信息表实体类
	 */
	CsmParty dealCompanyCustomerInfo(String customer);
}
