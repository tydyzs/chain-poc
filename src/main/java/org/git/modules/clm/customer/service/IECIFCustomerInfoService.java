package org.git.modules.clm.customer.service;

import org.git.modules.clm.customer.entity.CsmParty;

/**
 * 从ECIF获取客户信息同步 服务类
 * @author zhouweijie
 */
public interface IECIFCustomerInfoService {

	/**
	 * 同步客户信息
	 * @param customerNum 客户编号
	 * @param customerType 客户类型
	 * @return TbCsmParty 客户主表实体类
	 */
	CsmParty dealCustomerInfo(String customerNum, String customerType);

	/**
	 * 异步处理客户信息同步
	 * @param customerNum 客户编号
	 * @param customerType 客户类型
	 */
	void asyncDealCustomerInfo(String customerNum, String customerType);
}
