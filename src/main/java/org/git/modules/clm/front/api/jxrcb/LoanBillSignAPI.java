package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBillSignRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanBillSignResponseDTO;
import org.git.modules.clm.loan.service.ILoanBillSignService;
import org.git.modules.clm.loan.service.impl.LoanBillSignServiceImpl;

/**
 * 江西农信信贷系统-借据处理接口
 */
@Slf4j
public class LoanBillSignAPI extends JxrcbAPI {

	ICommonService iCommonService = SpringUtil.getBean(CommonServiceImpl.class);
	ILoanBillSignService iLoanBillSignService = SpringUtil.getBean(LoanBillSignServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		LoanBillSignResponseDTO rs = new LoanBillSignResponseDTO();
		try {
			LoanBillSignRequestDTO rq = (LoanBillSignRequestDTO) request.getRequest();
			//校验数据信息
			rs = iLoanBillSignService.checkLoanBillSign(rq);

			//处理数据信息
			rs = iLoanBillSignService.dealLoanBillSign(rq);

			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
		} catch (ServiceException e) {
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), e.getResultCode().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("银票签发接口错误信息：" + e.getMessage());
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F10601, e.getMessage());
		}
	}


}
