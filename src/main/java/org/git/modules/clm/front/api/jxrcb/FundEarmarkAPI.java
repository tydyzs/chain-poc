package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditDepositRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditDepositResponseDTO;
import org.git.modules.clm.credit.service.IFundEarmarkService;

/**
 * CLM202额度圈存、分配、调整
 *
 * @author SHC
 * @since 2019-12-03
 */
@Slf4j
@AllArgsConstructor
public class FundEarmarkAPI extends JxrcbAPI {
	private IFundEarmarkService service= SpringUtil.getBean(IFundEarmarkService.class);
	private ICommonService iCommonService= SpringUtil.getBean(ICommonService.class);
	public FundEarmarkAPI(){
	}
	@Override
	public Response run(ServiceBody request) {
		/**创建返回对象*/
		Response response = new Response();
		FundCreditDepositResponseDTO fundCreditDepositResponseDTO = new FundCreditDepositResponseDTO();
		ExtAttributes extAttributes=request.getExtAttributes();
		String tranSeqSn=extAttributes.getOriReqSn();
		String branchId=extAttributes.getBranchId();
		fundCreditDepositResponseDTO.setTranSeqSn(tranSeqSn);
		String desc = "";
		/**创建请求对象开始解析报文*/
		FundCreditDepositRequestDTO fundCreditDepositRequestDTO = new FundCreditDepositRequestDTO();
		if (request.getRequest() instanceof FundCreditDepositRequestDTO) {
			fundCreditDepositRequestDTO = (FundCreditDepositRequestDTO) request.getRequest();
		} else {
			desc = "报文解析失败！数据不符合此接口！";
			log.error(desc);
			return iCommonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30201, desc);
		}
		/**事件落地前校验*/
		String checkEventStatus=service.checkEventStatus(extAttributes,fundCreditDepositRequestDTO);
		if(!"0".equals(checkEventStatus)){
			return iCommonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20301, checkEventStatus);
		}
		/**2.事件落地*/
		String eventId=service.saveEventStatus(extAttributes,fundCreditDepositRequestDTO);
		/**3.管控*/
		desc=service.control(branchId,fundCreditDepositRequestDTO);
		if(!"0".equals(desc)){
			/**检查失败:事件状态置为失败；返回错误*/
			service.updateEventStatus(eventId, JxrcbBizConstant.event_status_2);
			return iCommonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20301, desc);
		}
		/**4.本地处理*/
		service.localHandle(extAttributes,fundCreditDepositRequestDTO,eventId);
		return iCommonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"执行成功！");
	}
}
