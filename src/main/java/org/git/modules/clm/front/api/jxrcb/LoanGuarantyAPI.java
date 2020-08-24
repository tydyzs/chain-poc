package org.git.modules.clm.front.api.jxrcb;


import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanGuarantyInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanGuarantyInfoResponseDTO;
import org.git.modules.clm.loan.service.ILoanGuarantyService;
import org.git.modules.clm.loan.service.impl.LoanGuarantyServiceImpl;

/**
 * 江西农信信贷系统-担保物处理接口
 *
 * @author zhouweijie
 */
@Slf4j
public class LoanGuarantyAPI extends JxrcbAPI {

	ILoanGuarantyService loanGuarantyService = SpringUtil.getBean(LoanGuarantyServiceImpl.class);
	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		String msg = "";//错误信息
		LoanGuarantyInfoResponseDTO responseDTO = new LoanGuarantyInfoResponseDTO();
		LoanGuarantyInfoRequestDTO requestDTO;
		try {
			/*转换请求实体类型*/
			requestDTO = (LoanGuarantyInfoRequestDTO) request.getRequest();

			/*检查客户信息*/
			msg = loanGuarantyService.checkCustomerInfo(requestDTO);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10701, msg);

			/*检查押品信息*/
			msg = loanGuarantyService.checkPledgeInfo(requestDTO);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10701, msg);

			/*同步押品信息*/
			msg = loanGuarantyService.mergeGuarantyInfo(requestDTO);
			AssertUtil.isNull(msg, JxrcbConstant.ESB_CODE_FAIL_F10701, msg);


		} catch (ServiceException e) {
			log.error(e.getMessage());
			return commonService.getResponse(responseDTO, JxrcbConstant.ESB_STATUS_FAIL,
				e.getResultCode().getCode(), e.getResultCode().getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
			return commonService.getResponse(responseDTO, JxrcbConstant.ESB_STATUS_FAIL,
				JxrcbConstant.ESB_CODE_FAIL, "押品信息同步异常！");
		}

		/*同步完成，返回响应*/
		responseDTO.setSuretyNum(requestDTO.getSuretyNum());//设置响应实体
		responseDTO.setBizScene(requestDTO.getBizScene());//业务场景
		responseDTO.setBizAction(requestDTO.getBizAction());//流程节点
		return commonService.getResponse(responseDTO, JxrcbConstant.ESB_STATUS_COMPLETE,
			JxrcbConstant.ESB_CODE_SUCCESS, "押品信息同步成功。");
	}

}
