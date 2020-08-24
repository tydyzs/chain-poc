package org.git.modules.clm.loan.service;

import org.git.modules.clm.front.dto.jxrcb.loan.ProductInfoDTO;

import java.util.List;

/**
 * 信贷业务品种查询 服务类
 *
 * @author zhouweijie
 */
public interface ILoanBusinessTypeService {

	/**
	 * 信贷业务品种查询
	 * @param productNum 业务品种编号
	 * @return 响应体实体类
	 */
	List<ProductInfoDTO> dealLoanBusinessType(String productNum);

}
