package org.git.modules.clm.front.api.jxrcb;

import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.entity.TbCrdContract;
import org.git.modules.clm.credit.entity.TbCrdSummary;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.service.ITbCrdContractService;
import org.git.modules.clm.credit.service.ITbCrdSummaryService;
import org.git.modules.clm.credit.service.impl.TbCrdApproveServiceImpl;
import org.git.modules.clm.credit.service.impl.TbCrdContractServiceImpl;
import org.git.modules.clm.credit.service.impl.TbCrdSummaryServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanRepayRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanRepayResponseDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdRecoveryEvent;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdRecoveryEventService;
import org.git.modules.clm.loan.service.impl.CrdApplySerialServiceImpl;
import org.git.modules.clm.loan.service.impl.CrdRecoveryEventServiceImpl;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.IProductService;
import org.git.modules.clm.param.service.impl.ProductServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 江西农信信贷系统-还款处理接口 CLM105
 *
 * @author caohaijie
 */
@Transactional
public class LoanRepayAPI extends JxrcbAPI {

	ITbCrdSummaryService crdSummaryService = SpringUtil.getBean(TbCrdSummaryServiceImpl.class);
	ITbCrdContractService crdContractService = SpringUtil.getBean(TbCrdContractServiceImpl.class);
	ITbCrdApproveService crdApproveService = SpringUtil.getBean(TbCrdApproveServiceImpl.class);
	ICrdRecoveryEventService crdRecoveryEventService = SpringUtil.getBean(CrdRecoveryEventServiceImpl.class);
	IProductService productService = SpringUtil.getBean(ProductServiceImpl.class);
	ICrdApplySerialService crdApplySerialService = SpringUtil.getBean(CrdApplySerialServiceImpl.class);
	ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		LoanRepayResponseDTO loanRepayResponseDTO = new LoanRepayResponseDTO();
		if (request.getRequest() instanceof LoanRepayRequestDTO) {
			LoanRepayRequestDTO loanRepayRequestDTO = (LoanRepayRequestDTO) request.getRequest();

			String summaryNum = loanRepayRequestDTO.getSummaryNum();
			TbCrdSummary tbCrdSummaryT = new TbCrdSummary();
			tbCrdSummaryT.setSummaryNum(summaryNum);

			//校验借据是否生效存在（可能要去掉）
			TbCrdSummary crdSummary = crdSummaryService.getOne(Condition.getQueryWrapper(tbCrdSummaryT));
			Assert.state(crdSummary != null, "没有找到对应生效的借据信息，借据号：" + summaryNum);

			//校验合同是否生效存在（可能要去掉）
			TbCrdContract crdContract = crdContractService.getById(crdSummary.getContractNum());
			Assert.state(crdContract != null, "没有找到对应生效的合同信息，合同号：" + crdSummary.getContractNum());

			//登记tb_crd_recovery_event（信贷实时-额度恢复交易流水）
			CrdRecoveryEvent crdRecoveryEvent = new CrdRecoveryEvent();
			crdRecoveryEvent.setOpType(loanRepayRequestDTO.getOpType());
			crdRecoveryEvent.setTranSeqSn(loanRepayRequestDTO.getTranSeqNum());//交易流水号
			crdRecoveryEvent.setSummaryNum(loanRepayRequestDTO.getSummaryNum());//借据编号
			//获取额度明细编号，三级品种编号
			TbCrdApprove crdApprove = crdApproveService.getById(crdSummary.getApproveId());
			String crdDetailPrd = crdApprove != null ? crdApprove.getCrdDetailPrd() : "";
			String crdDetailNum = crdApprove != null ? crdApprove.getCrdDetailNum() : "";
			crdRecoveryEvent.setBizAction(loanRepayRequestDTO.getBizAction());//业务场景
			crdRecoveryEvent.setBizScene(loanRepayRequestDTO.getBizScene());//流程节点
			crdRecoveryEvent.setDateSource(loanRepayRequestDTO.getDateSource());//数据来源 1 实时 2 日终
			crdRecoveryEvent.setRepayDate(loanRepayRequestDTO.getRepayDate());//还款日期
			crdRecoveryEvent.setCurrencyCd(crdSummary.getCurrencyCd());//币种
			crdRecoveryEvent.setRepayAmt(loanRepayRequestDTO.getRepayAmt());//还款本金
			crdRecoveryEvent.setUserNum(loanRepayRequestDTO.getUserNum());//经办人
			crdRecoveryEvent.setOrgNum(loanRepayRequestDTO.getOrgNum());//经办机构
			crdRecoveryEvent.setCreateTime(CommonUtil.getWorkDateTime());
			crdRecoveryEvent.setUpdateTime(CommonUtil.getWorkDateTime());
			crdRecoveryEventService.save(crdRecoveryEvent);//新增事件表


			//如果业务品种是借据占用额度，并且批复是循环的，并且是合同是非循环的情况，登记tb_crd_apply_serial（额度使用流水）
			Product product = productService.getById(crdApprove.getProductNum());
			String limitUsedType = product.getLimitUsedType();//额度占用类型
			boolean approveIsCycle = crdApprove != null && "1".equals(crdApprove.getIsCycle()) ? true : false;//批复是否循环
			boolean contractIsCycle = crdContract != null && "1".equals(crdContract.getIsCycle()) ? true : false;//合同是否循环
			if ("1".equals(limitUsedType) && approveIsCycle && !contractIsCycle) { //1合同占用 2借据占用
				CrdApplySerial crdApplySerial = new CrdApplySerial();
//				crdApplySerial.setSerialId("");//主键
				crdApplySerial.setTranSeqSn(crdRecoveryEvent.getTranSeqSn());//交易流水号
				crdApplySerial.setTranDate(CommonUtil.getWorkDate());//交易日期
				crdApplySerial.setBusiDealNum("1".equals(limitUsedType) ? crdSummary.getContractNum() : crdRecoveryEvent.getSummaryNum());//业务编号（存放不同的业务场景下的业务编号）
				crdApplySerial.setTranTypeCd("HK");//交易类型
				crdApplySerial.setCrdDetailNum(crdDetailNum);//额度明细编号
				crdApplySerial.setCrdGrantOrgNum(crdRecoveryEvent.getOrgNum());//授信机构
				crdApplySerial.setCustomerNum(crdSummary.getCustomerNum());//用信客户号
				crdApplySerial.setCrdDetailPrd(crdDetailPrd);//额度产品编号
				crdApplySerial.setLimitCreditAmt(crdRecoveryEvent.getRepayAmt());//占用/恢复额度金额

				//计算敞口金额 = 金额 * (100 - 保证金比例) / 100
				BigDecimal repayAmt = crdRecoveryEvent.getRepayAmt();//批复明细金额
				BigDecimal depositRatio = crdSummary.getDepositRatio();//保证金比例
				BigDecimal expCreditAmt = repayAmt.multiply(new BigDecimal(100).subtract(depositRatio)).divide(new BigDecimal(100));//敞口额度
				crdApplySerial.setExpCreditAmt(expCreditAmt);//占用/恢复敞口金额
//				crdApplySerial.setSerialFlag("2");//占用/恢复标志（1占用 2恢复）
				crdApplySerial.setCurrencyCd(crdRecoveryEvent.getCurrencyCd());//币种
				crdApplySerial.setIsMix("0");//是否串用
				crdApplySerial.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//经办系统
				crdApplySerial.setOrgNum(crdRecoveryEvent.getOrgNum());//经办机构
				crdApplySerial.setUserNum(crdRecoveryEvent.getUserNum());//经办人

				//01 新增 03 撤销
				if ("01".equals(loanRepayRequestDTO.getOpType())) {
					crdApplySerial.setCreateTime(CommonUtil.getWorkDateTime());//创建时间
//					crdApplySerial.setDealStatus("");//交易状态(未定) //TODO
				} else if ("03".equals(loanRepayRequestDTO.getOpType())) {
					crdApplySerial.setUpdateTime(CommonUtil.getWorkDateTime());//修改时间
//					crdApplySerial.setDealStatus("");//交易状态(未定) //TODO
				}
				crdApplySerialService.saveOrUpdate(crdApplySerial);
			}

			//登记tb_crd_summary（借据信息表），交易类型为还款、结清、冲账、呆账核销时，需要更新借据表的信息，如借据余额、借据状态、更新日期、结清日期字段
			//01 未出账 02 正常 03 逾期 04 结清 05 失效
			TbCrdSummary crdSummary1 = new TbCrdSummary();
			crdSummary1.setSummaryNum(loanRepayRequestDTO.getSummaryNum());
			crdSummary1.setSummaryBal(new BigDecimal(loanRepayRequestDTO.getSummaryBal()));
			crdSummary1.setSummaryStatus("1".equals(loanRepayRequestDTO.getIsSettle()) ? "04" : "02");//IsSettle是否结清
			if ("03".equals(loanRepayRequestDTO.getOpType())) {
				crdSummary1.setSummaryStatus("05");//借据状态失效
			}
			crdSummary1.setUpdateTime(CommonUtil.getWorkDateTime());
			crdSummary1.setTranDate(CommonUtil.getWorkDate());
			crdSummaryService.updateById(crdSummary1);

			//额度重算
			commonService.creditRecountAStatis(crdSummary.getCustomerNum());//使用借据表的客户编号

			//返回报文

			loanRepayResponseDTO.setSummaryNum(loanRepayRequestDTO.getSummaryNum());
//			loanRepayResponseDTO.setTranSeqSn(loanRepayRequestDTO.getTranSeqNum());
			loanRepayResponseDTO.setBizAction(loanRepayRequestDTO.getBizAction());
			loanRepayResponseDTO.setBizScene(loanRepayRequestDTO.getBizScene());
			commonService.getResponse(loanRepayResponseDTO, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "处理成功");
			return loanRepayResponseDTO;
		} else {

			return commonService.getResponse(loanRepayResponseDTO, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_SUCCESS, "类型非法");

		}

	}
}
