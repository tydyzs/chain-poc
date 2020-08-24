package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.service.ILoanCreditDetailService;
import org.git.modules.clm.credit.service.impl.LoanCreditDetailServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.bank.LoanCreditDetailRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bank.LoanCreditQueryResponseDTO;

/**
 * S00870000259004 票据额度查询
 */
@Slf4j
public class LoanCreditQueryAPI extends JxrcbAPI {
	private ICommonService iCommonService= SpringUtil.getBean(ICommonService.class);
	private ILoanCreditDetailService loanCreditDetailService = SpringUtil.getBean(LoanCreditDetailServiceImpl.class);
	@Override
	public Response run(ServiceBody request) {
		LoanCreditDetailRequestDTO loanCreditDetailRequestDTO = new LoanCreditDetailRequestDTO();
		LoanCreditQueryResponseDTO loanCreditQueryResponseDTO = new LoanCreditQueryResponseDTO();
		try{
			loanCreditDetailRequestDTO = (LoanCreditDetailRequestDTO)request.getRequest();
			loanCreditQueryResponseDTO=loanCreditDetailService.getResponse(loanCreditDetailRequestDTO);
		}catch(ServiceException e){
			return commonService.getResponse(loanCreditQueryResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "票据额度查询处理异常:"+e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			return commonService.getResponse(loanCreditQueryResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F00300, "票据额度查询处理异常！");
		}
		return iCommonService.getResponse(loanCreditQueryResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"执行成功！");
	}
}
