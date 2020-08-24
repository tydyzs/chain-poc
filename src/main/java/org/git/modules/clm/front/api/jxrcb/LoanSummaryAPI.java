package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanSummaryInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanSummaryInfoResponseDTO;
import org.git.modules.clm.loan.service.ILoanSummaryService;
import org.git.modules.clm.loan.service.impl.LoanSummaryServiceImpl;
import org.git.modules.clm.rcm.constant.RcmConstant;

/**
 * 江西农信信贷系统-借据处理接口
 */
@Slf4j
public class LoanSummaryAPI extends JxrcbAPI {

	ICommonService iCommonService = SpringUtil.getBean(CommonServiceImpl.class);
	ILoanSummaryService iLoanSummaryService = SpringUtil.getBean(LoanSummaryServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		LoanSummaryInfoResponseDTO rs = new LoanSummaryInfoResponseDTO();
		try {
			LoanSummaryInfoRequestDTO rq = (LoanSummaryInfoRequestDTO) request.getRequest();

			//校验数据信息
			rs = iLoanSummaryService.checkLoanSummary(rq);
			if (RcmConstant.QUOTA_NODE_MEASURE_FORBID.equals(rs.getControlType())) {//限额校验不通过
				return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
			}

			//处理数据信息
			rs = iLoanSummaryService.dealLoanSummary(rq);

			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
		} catch (ServiceException e) {
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), e.getResultCode().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("借据申请接口错误信息：" + e.getMessage());
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F10301, e.getMessage());
		}
	}
}
