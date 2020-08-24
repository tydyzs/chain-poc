package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.entity.FundEventMain;
import org.git.modules.clm.credit.service.IFundEventMainService;
import org.git.modules.clm.credit.service.impl.FundEventMainServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.*;
import org.git.modules.clm.credit.service.IFundCreditBatchUseService;
import org.git.modules.clm.credit.service.impl.FundCreditBatchUseServiceImpl;

import java.util.List;


@Slf4j
public class FundCreditBatchUseAPI extends JxrcbAPI {

	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);
	IFundEventMainService fundEventMainService = SpringUtil.getBean(FundEventMainServiceImpl.class);
	IFundCreditBatchUseService fundCreditBatchUseService = SpringUtil.getBean(FundCreditBatchUseServiceImpl.class);


	@Override
	public Response run(ServiceBody request) {

		//Response是所有响应体的父类，需要创建子类对象进行封装。
		log.debug("--------------------------------------------------------------------------------------接收到CLM205请求");
		FundBatchRequestDTO fundBatchRequestDTO = new FundBatchRequestDTO();
		FundBatchResponseDTO fundBatchResponseDTO = new FundBatchResponseDTO();
		List<FundEventMain> fundEventMains = null;//事件主表ID
		try{
			fundBatchRequestDTO = (FundBatchRequestDTO) request.getRequest();
			ExtAttributes extAttributes = request.getExtAttributes();
			fundBatchResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
			fundEventMains = fundCreditBatchUseService.registerBusiEvent(fundBatchRequestDTO,extAttributes);
			fundCreditBatchUseService.middleHandle(fundEventMains,extAttributes,JxrcbBizConstant.CLM206);
			for (FundEventMain fundEventMain:fundEventMains) {
				//交易后处理
				fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
				fundEventMainService.updateById(fundEventMain);
			}

		}catch(ServiceException e){
			log.error(e.getMessage());
			if(fundEventMains != null){
				for (FundEventMain fundEventMain:fundEventMains) {
					fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
					fundEventMain.setTranEventInfo(e.getMessage());
					fundEventMainService.updateById(fundEventMain);
				}
			}

			return commonService.getResponse(fundBatchResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "日间批量用信申请处理异常:"+e.getMessage());

		}catch(Exception e){
			log.error(e.getMessage());
			if(fundEventMains != null) {
				for (FundEventMain fundEventMain : fundEventMains) {
					fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
					fundEventMain.setTranEventInfo("系统错误！");
					fundEventMainService.updateById(fundEventMain);
				}
			}
			return commonService.getResponse(fundBatchResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20500, "日间批量用信申请处理异常！");

		}

		return commonService.getResponse(fundBatchResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "日间批量用信申请处理成功！");
	}



}
