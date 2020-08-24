package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.entity.FundGrantMain;
import org.git.modules.clm.credit.service.IFundGrantMainService;
import org.git.modules.clm.credit.service.impl.FundGrantMainServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditExtensionRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditExtensionResponseDTO;
import org.git.modules.clm.credit.service.IFundCreditExtensionService;
import org.git.modules.clm.credit.service.impl.FundCreditExtensionServiceImpl;
/**
 * 00870000259201
 * */

@Slf4j
public class FundCreditExtensionAPI extends JxrcbAPI {

	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);
	IFundCreditExtensionService fundCreditExtensionService = SpringUtil.getBean(FundCreditExtensionServiceImpl.class);
	IFundGrantMainService fundGrantMainService = SpringUtil.getBean(FundGrantMainServiceImpl.class);
	@Override
	public Response run(ServiceBody request) {
		ExtAttributes extAttributes = request.getExtAttributes();
		//Response是所有响应体的父类，需要创建子类对象进行封装。
		log.debug("--------------------------------------------------------------------------------------接收到CLM201请求开监听");
		FundCreditExtensionRequestDTO fundCreditExtensionRequestDTO = new FundCreditExtensionRequestDTO();
		FundCreditExtensionResponseDTO fundCreditDepositResponseDTO = new FundCreditExtensionResponseDTO();
		FundGrantMain fundGrantMain = null;
		try{
			if (!(request.getRequest() instanceof FundCreditExtensionRequestDTO)) {
				return commonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_FAIL_F20111, "额度综合授信、切分调整申请接口报文解析失败！数据不符合此接口规范！");
			}
			fundCreditExtensionRequestDTO = (FundCreditExtensionRequestDTO) request.getRequest();
			fundCreditDepositResponseDTO.setTranSeqSn(extAttributes.getOriReqSn());
			//登记业务事件
			fundGrantMain = fundCreditExtensionService.registerBusiEvent(fundCreditExtensionRequestDTO,extAttributes);
			//中间处理（授信流水拆分、授信管控检查、本地处理）
			fundCreditExtensionService.middleHandle(fundGrantMain,extAttributes,fundCreditExtensionRequestDTO.getCustomerNum(),JxrcbBizConstant.CLM201);
			//交易后处理
			fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
			fundGrantMainService.updateById(fundGrantMain);
			//客户信息查看同步(在middleHandle处理)
			//CsmParty tbCsmParty = csmPartyService.getById(fundCreditExtensionRequestDTO.getCustomerNum());
			//
			//if(tbCsmParty == null)
		}catch(ServiceException e){
			log.error(e.getMessage());
			if(fundGrantMain!=null){
				fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundGrantMain.setTranEventInfo(e.getMessage());
				fundGrantMainService.updateById(fundGrantMain);
			}
			return commonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "额度综合授信、切分调整申请处理异常:"+e.getMessage());

		}catch(Exception e){
			log.error(e.getMessage());
			if(fundGrantMain!=null){
				fundGrantMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				fundGrantMain.setTranEventInfo("系统错误！");
				fundGrantMainService.updateById(fundGrantMain);
			}
			return commonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20100, "额度综合授信、切分调整申请处理异常");

		}

		return commonService.getResponse(fundCreditDepositResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "额度综合授信、切分调整申请处理成功");
	}



}
