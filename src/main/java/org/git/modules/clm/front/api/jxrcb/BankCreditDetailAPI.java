package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.bank.BankCreditDetailRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bank.BankCreditDetailResponseDTO;
import org.git.modules.clm.credit.service.IBankCreditDetailService;

/**
 * S00870000259003同业实时-明细额度详细信息查询
 */
@Slf4j
@AllArgsConstructor
public class BankCreditDetailAPI extends JxrcbAPI {
	private IBankCreditDetailService iService= SpringUtil.getBean(IBankCreditDetailService.class);
	private ICommonService iCommonService= SpringUtil.getBean(ICommonService.class);
	public BankCreditDetailAPI(){
	}
	@Override
	public Response run(ServiceBody request) {
		/**创建返回对象*/
		Response response = new Response();
		BankCreditDetailResponseDTO bankCreditDetailResponseDTO = new BankCreditDetailResponseDTO();
		/**创建请求对象开始解析报文*/
		BankCreditDetailRequestDTO bankCreditDetailRequestDTO = new BankCreditDetailRequestDTO();
		if (request.getRequest() instanceof BankCreditDetailRequestDTO) {
			bankCreditDetailRequestDTO = (BankCreditDetailRequestDTO) request.getRequest();
		} else {
			String desc = "报文解析失败！数据不符合此接口！";
			log.error(desc);
			return iCommonService.getResponse(bankCreditDetailResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL, desc);
		}
		bankCreditDetailResponseDTO=iService.getResponse(bankCreditDetailRequestDTO);
		if(bankCreditDetailResponseDTO==null){
			String desc="未查到数据！";
			log.error(desc);
			return iCommonService.getResponse(bankCreditDetailResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30202, desc);
		}
		log.debug("执行成功！");
		return iCommonService.getResponse(bankCreditDetailResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"执行成功！");
	}
}
