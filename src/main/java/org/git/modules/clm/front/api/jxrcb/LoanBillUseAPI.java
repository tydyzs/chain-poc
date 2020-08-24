package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.exception.AssertUtil;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.loan.dto.LoanBillUseEventDTO;
import org.git.modules.clm.loan.entity.BillEventMain;
import org.git.modules.clm.loan.service.IBillEventMainService;
import org.git.modules.clm.loan.service.ILoanBillUseService;
import org.git.modules.clm.loan.service.impl.BillEventMainServiceImpl;
import org.git.modules.clm.loan.service.impl.LoanBillUseServiceImpl;

/**
 * 票据贴现，再贴，转贴接口
 */
@Slf4j
public class LoanBillUseAPI extends JxrcbAPI {
	private ILoanBillUseService loanBillUseService= SpringUtil.getBean(LoanBillUseServiceImpl.class);
	private ICommonService iCommonService= SpringUtil.getBean(CommonServiceImpl.class);
	private IBillEventMainService billEventMainService= SpringUtil.getBean(BillEventMainServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		BillDiscountResponseDTO billDiscountResDTO = new BillDiscountResponseDTO();
		/**创建请求对象开始解析报文*/
		BillDiscountRequestDTO billDiscountReqDTO = new BillDiscountRequestDTO();
		ExtAttributes extAttributes=request.getExtAttributes();
		billDiscountResDTO.setTranSeqSn(extAttributes.getOriReqSn());
		if (request.getRequest() instanceof BillDiscountRequestDTO) {
			billDiscountReqDTO = (BillDiscountRequestDTO) request.getRequest();
		}
		BillEventMain billEventMain = null;
		try{
			LoanBillUseEventDTO loanBillUseEventDTO=loanBillUseService.insertEvent(extAttributes,billDiscountReqDTO);
			billEventMain=loanBillUseEventDTO.getBillEventMain();
			if(loanBillUseEventDTO.getCustomerNumStatus()==1){
				AssertUtil.throwServiceException(JxrcbConstant.ESB_CODE_FAIL_F30204,"部分人行清算行号未找到客户编号！交易结束！");
			}
			loanBillUseService.middleHandle(loanBillUseEventDTO,extAttributes,JxrcbBizConstant.CLM301);
			if(billEventMain!=null){
				billEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_SUCCEED);
				billEventMain.setTranEventInfo("执行成功！");
				billEventMainService.updateById(billEventMain);
			}
		}catch(ServiceException e){
			log.error(e.getMessage());
			if(billEventMain!=null){
				billEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				billEventMain.setTranEventInfo("系统错误！");
				billEventMainService.updateById(billEventMain);
			}
			return commonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, e.getResultCode().getCode(), "票据额度用信（占用、恢复、撤销）处理异常:"+e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			if(billEventMain!=null){
				billEventMain.setTranEventStatus(JxrcbBizConstant.TRAN_EVENT_STATUS_FAILED);
				billEventMain.setTranEventInfo("系统错误！");
				billEventMainService.updateById(billEventMain);
			}
			return commonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F20500, "额度预占用、占用、恢复申请处理异常！");
		}
		return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"票据额度用信（占用、恢复、撤销）成功！");
	}
}
