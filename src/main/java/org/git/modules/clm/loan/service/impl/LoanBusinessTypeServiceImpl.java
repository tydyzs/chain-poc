package org.git.modules.clm.loan.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBusinessTypeRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBusinessTypeResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.ProductInfoDTO;
import org.git.modules.clm.front.service.IBMMQService;
import org.git.modules.clm.loan.service.ILoanBusinessTypeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 信贷业务品种查询 服务实现类
 *
 * @author zhouweijie
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoanBusinessTypeServiceImpl implements ILoanBusinessTypeService {

	IBMMQService ibmmqService;

	/**
	 * 信贷业务品种查询
	 * @param productNum 业务品种编号
	 * @return 响应体实体类
	 */
	@Override
	public List<ProductInfoDTO> dealLoanBusinessType(String productNum) {
		String serviceId = JxrcbConstant.LOAN_SERVICE_ID_B01;//服务代码
		LoanBusinessTypeRequestDTO requestDTO = new LoanBusinessTypeRequestDTO();
		requestDTO.setI1PRODUCTID(productNum);//业务品种编号
		requestDTO.setI1BAKUP1("0");
		requestDTO.setI1BAKUP2("0");
		requestDTO.setI1BAKUP3(new BigDecimal("0"));
		Response response;
		try {
			/*发送请求获取响应*/
			response = ibmmqService.request(serviceId, requestDTO);
			LoanBusinessTypeResponseDTO responseDTO = (LoanBusinessTypeResponseDTO)response;
			return responseDTO.getItems();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
