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
import org.git.modules.clm.front.dto.jxrcb.fund.CrdApplyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditUseResponseDTO;
import org.git.modules.clm.credit.service.IFundCreditUseService;
import org.git.modules.clm.credit.service.impl.FundCreaditUseServiceImpl;

import java.util.List;


@Slf4j
public class FundCreditUseAPI extends JxrcbAPI {

	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);
	IFundEventMainService fundEventMainService = SpringUtil.getBean(FundEventMainServiceImpl.class);
	IFundCreditUseService fundCreditUseService = SpringUtil.getBean(FundCreaditUseServiceImpl.class);


	@Override
	public Response run(ServiceBody request) {

		//Response是所有响应体的父类，需要创建子类对象进行封装。
		log.debug("--------------------------------------------------------------------------------------接收到CLM205请求");
		FundCreditUseRequestDTO fundCreditUseRequestDTO = new FundCreditUseRequestDTO();
		FundCreditUseResponseDTO fundCreditUseResponseDTO = new FundCreditUseResponseDTO();
		FundEventMain fundEventMain = null;//事件主表ID
		try{
			fundCreditUseRequestDTO = (FundCreditUseRequestDTO) request.getRequest();
			ExtAttributes extAttributes = request.getExtAttributes();
			fundCreditUseResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
			fundEventMain = fundCreditUseService.registerBusiEvent(fundCreditUseRequestDTO,extAttributes);
			fundCreditUseService.middleHandle(fundEventMain,extAttributes,JxrcbBizConstant.CLM205);
			//交易后处理
			fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
			fundEventMainService.updateById(fundEventMain);
			List<CrdApplyInfoRequestDTO> crdApplyInfos = fundCreditUseRequestDTO.getCrdApplyInfo();
			//fundCreditUseService.customerHanle(crdApplyInfos);//授信处理之后用信不再处理

		}catch(ServiceException e){
			if(fundEventMain!=null){
				fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundEventMain.setTranEventInfo(e.getMessage());
				fundEventMainService.updateById(fundEventMain);
			}
			return commonService.getResponse(fundCreditUseResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "额度预占用、占用、恢复申请处理异常:"+e.getMessage());

		}catch(Exception e){
			log.error(e.getMessage()+fundEventMain);
			if(fundEventMain!=null){
				fundEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundEventMain.setTranEventInfo("系统错误！");
				fundEventMainService.updateById(fundEventMain);
			}
			return commonService.getResponse(fundCreditUseResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20500, "额度预占用、占用、恢复申请处理异常！");

		}

		return commonService.getResponse(fundCreditUseResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "额度预占用、占用、恢复申请处理成功");
	}



}
