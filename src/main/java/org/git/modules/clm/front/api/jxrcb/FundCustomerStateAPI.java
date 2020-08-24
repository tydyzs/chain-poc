package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCustomerStateRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCustomerStateResponseDTO;
import org.git.modules.clm.credit.service.IFundCustomerStateService;

/**
 * CLM203资金实时-资金客户状态维护
 */
@Slf4j
@AllArgsConstructor
public class FundCustomerStateAPI extends JxrcbAPI {
	private IFundCustomerStateService service= SpringUtil.getBean(IFundCustomerStateService.class);
	private ICommonService iCommonService= SpringUtil.getBean(ICommonService.class);
	public FundCustomerStateAPI(){
	}
	@Override
	public Response run(ServiceBody request) {
		/**创建返回对象*/
		Response response = new Response();
		FundCustomerStateResponseDTO fundCustomerStateResponseDTO = new FundCustomerStateResponseDTO();
		String desc = "";
		/**创建请求对象开始解析报文*/
		FundCustomerStateRequestDTO fundCustomerStateRequestDTO = new FundCustomerStateRequestDTO();
		ExtAttributes extAttributes=request.getExtAttributes();
		fundCustomerStateResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
		if (request.getRequest() instanceof FundCustomerStateRequestDTO) {
			fundCustomerStateRequestDTO = (FundCustomerStateRequestDTO) request.getRequest();
		} else {
			desc = "报文解析失败！数据不符合此接口！";
			log.error(desc);
			return iCommonService.getResponse(fundCustomerStateResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30201, desc);
		}
		/**事件落地前校验*/
		if("1".equals(service.checkEventStatus(extAttributes,fundCustomerStateRequestDTO))){
			desc = "交易已存在！校验失败！";
			return iCommonService.getResponse(fundCustomerStateResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20301, desc);
		}
		/**2.事件落地*/
		String eventId=service.saveEventStatus(extAttributes,fundCustomerStateRequestDTO);
		/**3.管控*/
		//2019-12-19，需求变更，不做管控
		/*desc=service.control(fundCustomerStateRequestDTO);
		if(!"0".equals(desc)){
			//检查失败:事件状态置为失败；返回错误
			service.updateEventStatus(eventId, JxrcbBizConstant.event_status_2);
			return iCommonService.getResponse(fundCustomerStateResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20301, desc);
		}*/
		/**4.本地处理*/
		service.localHandle(extAttributes,fundCustomerStateRequestDTO,eventId);
		return iCommonService.getResponse(fundCustomerStateResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"执行成功！");
	}
}
