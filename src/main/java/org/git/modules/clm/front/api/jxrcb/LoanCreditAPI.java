package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanCreditApplyResponseDTO;
import org.git.modules.clm.loan.service.ILoanCreditService;
import org.git.modules.clm.loan.service.impl.LoanCreditServiceImpl;
import org.git.modules.clm.rcm.constant.RcmConstant;


/**
 * 江西农信信贷系统-授信额度接口
 *
 * @author chenchuan
 */
@Slf4j
public class LoanCreditAPI extends JxrcbAPI {
	ILoanCreditService iLoanCreditService = SpringUtil.getBean(LoanCreditServiceImpl.class);
	ICommonService iCommonService = SpringUtil.getBean(CommonServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		log.info("--------------------进入程序--------------------");
		LoanCreditApplyResponseDTO rs = new LoanCreditApplyResponseDTO();
		try {
			//创建请求对象开始解析报文
			LoanCreditApplyRequestDTO rq = (LoanCreditApplyRequestDTO) request.getRequest();

			//校验业务信息
			rs = iLoanCreditService.checkLoanCredit(rq);
			if (RcmConstant.QUOTA_NODE_MEASURE_FORBID.equals(rs.getControlType())) {//限额校验不通过
				return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
			}

			//处理业务信息
			rs = iLoanCreditService.dealLoanCredit(rq);
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "交易成功");
		} catch (ServiceException e) {
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), e.getResultCode().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return iCommonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F10101, "额度申请处理异常");
		}
	}


}
