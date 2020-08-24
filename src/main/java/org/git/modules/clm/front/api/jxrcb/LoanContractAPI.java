package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanContractInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanContractInfoResponseDTO;
import org.git.modules.clm.loan.service.ILoanContractService;
import org.git.modules.clm.loan.service.impl.LoanContractServiceImpl;
import org.git.modules.clm.rcm.constant.RcmConstant;

/**
 * 江西农信信贷系统-合同处理接口
 *
 * @author chenchuan
 */
@Slf4j
public class LoanContractAPI extends JxrcbAPI {
	ICommonService iCommonService = SpringUtil.getBean(ICommonService.class);
	ILoanContractService iLoanContractService = SpringUtil.getBean(LoanContractServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		log.info("--------------------进入程序--------------------");
		LoanContractInfoResponseDTO rs = new LoanContractInfoResponseDTO();
		try {
			LoanContractInfoRequestDTO rq = (LoanContractInfoRequestDTO) request.getRequest();

			//校验业务信息
			rs = iLoanContractService.checkContractCredit(rq);
			if (RcmConstant.QUOTA_NODE_MEASURE_FORBID.equals(rs.getControlType())) {//限额校验不通过
				return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
			}

			//处理合同信息
			rs = iLoanContractService.dealContractCredit(rq);

			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
		} catch (ServiceException e) {
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), e.getResultCode().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F10201, e.getMessage());
		}
	}
}
