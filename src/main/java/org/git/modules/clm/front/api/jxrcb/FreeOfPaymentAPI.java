package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.dto.FreeOfPaymentEventVO;
import org.git.modules.clm.credit.entity.FundAssignedMain;
import org.git.modules.clm.credit.service.IFreeOfPaymentService;
import org.git.modules.clm.credit.service.IFundAssignedMainService;
import org.git.modules.clm.credit.service.impl.FreeOfPaymentServiceImpl;
import org.git.modules.clm.credit.service.impl.FundAssignedMainServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FreeOfPaymentRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FreeOfPaymentResponseDTO;


/**
 *
 */
@Slf4j
public class FreeOfPaymentAPI extends JxrcbAPI {

	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);
	IFreeOfPaymentService freeOfPaymentService = SpringUtil.getBean(FreeOfPaymentServiceImpl.class);
	IFundAssignedMainService fundAssignedMainService = SpringUtil.getBean(FundAssignedMainServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {

		//Response是所有响应体的父类，需要创建子类对象进行封装。
		log.debug("--------------------------------------------------------------------------------------接收到CLM205请求");
		FreeOfPaymentRequestDTO freeOfPaymentRequestDTO = new FreeOfPaymentRequestDTO();
		FreeOfPaymentResponseDTO freeOfPaymentResponseDTO = new FreeOfPaymentResponseDTO();
		FundAssignedMain fundAssignedMain=null;
		try{
			freeOfPaymentRequestDTO = (FreeOfPaymentRequestDTO) request.getRequest();
			ExtAttributes extAttributes = request.getExtAttributes();
			freeOfPaymentResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
			FreeOfPaymentEventVO freeOfPaymentEventVO =  freeOfPaymentService.registerEvent(freeOfPaymentRequestDTO,extAttributes);
			freeOfPaymentService.middleHandle(freeOfPaymentEventVO);
			fundAssignedMain=freeOfPaymentEventVO.getFundAssignedMain();
			fundAssignedMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
			fundAssignedMainService.updateById(fundAssignedMain);
		}catch(ServiceException e){
			if(fundAssignedMain!=null){
				fundAssignedMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundAssignedMain.setTranEventInfo(e.getMessage());
				fundAssignedMainService.updateById(fundAssignedMain);
			}
			return commonService.getResponse(freeOfPaymentResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "纯券转让申请处理异常:"+e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			if(fundAssignedMain!=null){
				fundAssignedMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundAssignedMain.setTranEventInfo("系统错误！");
				fundAssignedMainService.updateById(fundAssignedMain);
			}
			return commonService.getResponse(freeOfPaymentResponseDTO, JxrcbConstant.ESB_STATUS_FAIL,JxrcbConstant.ESB_CODE_FAIL_F20700, "纯券转让申请处理异常！");
		}

		return commonService.getResponse(freeOfPaymentResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "纯券转让申请处理成功！");
	}



}
