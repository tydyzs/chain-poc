package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.entity.FundTransferMain;
import org.git.modules.clm.credit.service.IFundTransferMainService;
import org.git.modules.clm.credit.service.impl.FundTransferMainServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditTransferDTO;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditOccupancyRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditOccupancyResponseDTO;
import org.git.modules.clm.credit.service.IFundCreditTransfer;
import org.git.modules.clm.credit.service.impl.FundCreditTransferImpl;


@Slf4j
public class FundCreditTransferAPI extends JxrcbAPI {

	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);
    IFundCreditTransfer fundCreditTransfer = SpringUtil.getBean(FundCreditTransferImpl.class);
	IFundTransferMainService fundTransferInService = SpringUtil.getBean(FundTransferMainServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {

		//Response是所有响应体的父类，需要创建子类对象进行封装。
		log.debug("--------------------------------------------------------------------------------------接收到CLM205请求");
		FundCreditOccupancyRequestDTO fundCreditOccupancyRequestDTO = new FundCreditOccupancyRequestDTO();
		FundCreditOccupancyResponseDTO fundCreditOccupancyResponseDTO = new FundCreditOccupancyResponseDTO();
		FundCreditTransferDTO fundCreditTransferDTO = null;
		try{
			fundCreditOccupancyRequestDTO = (FundCreditOccupancyRequestDTO) request.getRequest();
			ExtAttributes extAttributes = request.getExtAttributes();
			fundCreditOccupancyResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
			fundCreditTransferDTO =fundCreditTransfer.registerBusiEvent(fundCreditOccupancyRequestDTO,extAttributes);
			fundCreditTransfer.middleHandle(fundCreditTransferDTO,extAttributes,JxrcbBizConstant.CLM204);
			FundTransferMain fundTransferMain = fundCreditTransferDTO.getFundTransferMain();
			fundTransferMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
			fundTransferInService.updateById(fundTransferMain);
		}catch(ServiceException e){
			log.error(e.getMessage());
			if(fundCreditTransferDTO!=null) {
				FundTransferMain fundTransferMain = fundCreditTransferDTO.getFundTransferMain();
				fundTransferMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
				fundTransferMain.setTranEventInfo(e.getMessage());
				fundTransferInService.updateById(fundTransferMain);
			}
			return commonService.getResponse(fundCreditOccupancyResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "额度转让申请处理异常:"+e.getMessage());

		}catch(Exception e){
			log.error(e.getMessage());
			if(fundCreditTransferDTO!=null) {
				FundTransferMain fundTransferMain = fundCreditTransferDTO.getFundTransferMain();
				fundTransferMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
				fundTransferMain.setTranEventInfo(e.getMessage());
				fundTransferInService.updateById(fundTransferMain);
			}
			return commonService.getResponse(fundCreditOccupancyResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20600, "额度转让申请处理异常！");

		}

		return commonService.getResponse(fundCreditOccupancyResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "额度转让申请处理成功");
	}



}
