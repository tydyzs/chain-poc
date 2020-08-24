package org.git.modules.clm.front.api.jxrcb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.loan.service.ILoanBillService;

/**
 * 票据贴现，再贴，转贴接口
 */
@Slf4j
@AllArgsConstructor
public class LoanBillAPI extends JxrcbAPI {
	private ILoanBillService iLoanBillService= SpringUtil.getBean(ILoanBillService.class);
	private ICommonService iCommonService= SpringUtil.getBean(ICommonService.class);
	public LoanBillAPI(){
	}
	@Override
	public Response run(ServiceBody request) {
		/**创建返回对象*/
		Response response = new Response();
		BillDiscountResponseDTO billDiscountResDTO = new BillDiscountResponseDTO();
		/**创建请求对象开始解析报文*/
		BillDiscountRequestDTO billDiscountReqDTO = new BillDiscountRequestDTO();
		ExtAttributes extAttributes=request.getExtAttributes();
		billDiscountResDTO.setTranSeqSn(extAttributes.getOriReqSn());
		if (request.getRequest() instanceof BillDiscountRequestDTO) {
			billDiscountReqDTO = (BillDiscountRequestDTO) request.getRequest();
		} else {
			String desc = "报文解析失败！数据不符合此接口！";
			log.error(desc);
			return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30201, desc);
		}
		/**1.校验并事件落地。插入事件主表和明细表的数据,并提交事务。若校验失败直接返回错误*/
		String zt=iLoanBillService.insertEvent(extAttributes,billDiscountReqDTO);
		if("0".equals(zt)){
			String desc="凭证用信金额累加与用信金额不相等！交易结束！";
			log.error(desc);
			return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30201, desc);
		}
		if("-1".equals(zt)){
			String desc="数据重复校验不通过！交易结束！";
			log.error(desc);
			return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30202, desc);
		}
		if("-2".equals(zt)){
			String desc="部分人行清算行号未找到客户编号！交易结束！";
			log.error(desc);
			return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30202, desc);
		}
		/**2.额度的检查，拆分，本地更新处理.先检查*/
		String desc= iLoanBillService.check(extAttributes,billDiscountReqDTO,billDiscountResDTO);
		if(!"0".equals(desc)){
			log.error(desc);
			/**事件主表改为失败*/
			iLoanBillService.updateEventStatus(zt, JxrcbBizConstant.event_status_2);
			return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F30203, desc);
		}else{
			//crdSplit(billDiscountReqDTO,billDiscountResDTO);
			iLoanBillService.crdSplitUp(extAttributes,billDiscountReqDTO,billDiscountResDTO);
		}
		/**3.额度更新本地处理*/
		iLoanBillService.crdUpdate(zt);
		/**4.通知信贷*/
		log.debug("执行成功！");
		return iCommonService.getResponse(billDiscountResDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS,"执行成功！");
	}
}
