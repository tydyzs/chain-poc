package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.service.ICrdSumService;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.service.impl.CrdSumServiceImpl;
import org.git.modules.clm.credit.service.impl.TbCrdApproveServiceImpl;
import org.git.modules.clm.credit.vo.CrdSumVO;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.common.*;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.loan.service.impl.CrdApplySerialServiceImpl;
import org.git.modules.clm.loan.service.impl.CrdDetailServiceImpl;
import org.git.modules.clm.loan.service.impl.CrdMainServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用额度查询接口 CLM002
 * @author caohaijie
 */
@Slf4j
@Transactional
public class CreditQueryAPI extends JxrcbAPI {

	private ITbCrdApproveService crdApproveService = SpringUtil.getBean(TbCrdApproveServiceImpl.class);
	private ICrdMainService crdMainService = SpringUtil.getBean(CrdMainServiceImpl.class);
	private ICrdDetailService crdDetailService = SpringUtil.getBean(CrdDetailServiceImpl.class);
	private ICrdApplySerialService crdApplySerialService = SpringUtil.getBean(CrdApplySerialServiceImpl.class);
	private ICrdSumService crdSumService = SpringUtil.getBean(CrdSumServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		if (request.getRequest() instanceof CreditQueryRequestDTO) {

			//查询结果
			CreditQueryResponseDTO creditQueryResponseDTO = new CreditQueryResponseDTO();

			//额度信息List
			List<CreditInfoResponseDTO> creditInfoResponseList = new ArrayList<>();
			List<CreditDetailInfoResponseDTO> creditDetailInfoList = new ArrayList<>();//额度明细数组
			List<ApproveInfoResponseDTO> approveInfoList = new ArrayList<>();//额度批复信息
			List<WaterInfoResponseDTO> waterInfoList = new ArrayList<>();//占用恢复流水信息


			//开始查询
			CreditQueryRequestDTO creditQueryRequestDTO = (CreditQueryRequestDTO) request.getRequest();
			String customerNum = creditQueryRequestDTO.getCustomerNum();
			String orgNum = creditQueryRequestDTO.getOrgNum();

			//客户总授信额度信息 ( 1 授信额度 2 担保额度 3 第三方额度)
			CrdSumVO crdSumQuery = new CrdSumVO();
			crdSumQuery.setCustomerNum(customerNum);
			crdSumQuery.setOrgNum(orgNum);

			crdSumQuery.setCrdProductType("1");
			CrdSumVO crdSum = crdSumService.queryCrdSum(crdSumQuery);//授信额度
			crdSumQuery.setCrdProductType("2");
			CrdSumVO guaranteeCrdSum = crdSumService.queryCrdSum(crdSumQuery);//担保额度
			crdSumQuery.setCrdProductType("3");
			CrdSumVO thirdCrdSum = crdSumService.queryCrdSum(crdSumQuery);//第三方额度

			Assert.notNull(crdSum,"授信额度不存在！");
			creditQueryResponseDTO.setCustomerNum(crdSum.getCustomerNum());//ECIF客户号
			creditQueryResponseDTO.setCurrencyCd(JxrcbBizConstant.CURRENCY_CNY);//币种
			creditQueryResponseDTO.setLimitCredit(crdSum.getLimitCredit());//总授信额度
			creditQueryResponseDTO.setLimitUsed(crdSum.getLimitUsed());//总已用额度
			creditQueryResponseDTO.setLimitAvi(crdSum.getLimitAvi());//总可用额度
			creditQueryResponseDTO.setOpenCredit(crdSum.getExpCredit());//总授信敞口
			creditQueryResponseDTO.setOpenUsed(crdSum.getExpUsed());//总已用敞口
			creditQueryResponseDTO.setOpenAvi(crdSum.getExpAvi());//总可用敞口

			Assert.notNull(guaranteeCrdSum,"担保额度不存在！");
			creditQueryResponseDTO.setGuaranteeLimit(guaranteeCrdSum.getLimitCredit());//总担保额度
			creditQueryResponseDTO.setGuaranteeUsed(guaranteeCrdSum.getLimitUsed());//担保额度已用
			creditQueryResponseDTO.setGuaranteeAvi(guaranteeCrdSum.getLimitAvi());//担保额度可用

			Assert.notNull(thirdCrdSum,"第三方额度不存在！");
			creditQueryResponseDTO.setThirdLimit(thirdCrdSum.getLimitCredit());//总第三方额度
			creditQueryResponseDTO.setThirdUsed(thirdCrdSum.getLimitUsed());//担保额度已用
			creditQueryResponseDTO.setThirdAvi(thirdCrdSum.getLimitAvi());//担保额度可用


			//查询额度信息
			CrdMain params = new CrdMain();
			params.setCustomerNum(customerNum);
			params.setCreditStatus("01");//生效状态 TODO
			List<CrdMain> crdMainList = crdMainService.list(Condition.getQueryWrapper(params));
			for (CrdMain crdMain : crdMainList) {
				CreditInfoResponseDTO creditInfoResponseDTO = new CreditInfoResponseDTO();
				creditInfoResponseDTO.setCreditMainNum(crdMain.getCrdMainNum());//二级额度编号
				creditInfoResponseDTO.setCreditMainPrd(crdMain.getCrdMainPrd());//二级额度产品
				creditInfoResponseDTO.setCurrencyCd(crdMain.getCurrencyCd());//币种
				creditInfoResponseDTO.setLimitCredit(crdMain.getLimitCredit().toString());//授信额度
				creditInfoResponseDTO.setLimitUsed(crdMain.getLimitUsed().toString());//已用额度
				creditInfoResponseDTO.setLimitAvi(crdMain.getLimitAvi().toString());//可用额度
				creditInfoResponseDTO.setOpenCredit(crdMain.getExpCredit().toString());//授信敞口
				creditInfoResponseDTO.setOpenUsed(crdMain.getExpUsed().toString());//已用敞口
				creditInfoResponseDTO.setOpenAvi(crdMain.getExpAvi().toString());//可用敞口
				creditInfoResponseDTO.setLimitPreUsed(crdMain.getLimitPre().toString());//预占用额度
				creditInfoResponseDTO.setOpenPreUsed(crdMain.getExpPre().toString());//预占用敞口
				creditInfoResponseDTO.setLimitEarmark("");//圈存额度
				creditInfoResponseDTO.setLimitEarmarkUsed("");//圈存已用额度
				creditInfoResponseDTO.setLimitEarmarkAvi("");//圈存可用额度
				creditInfoResponseDTO.setLimitFrozen(crdMain.getLimitFrozen().toString());//冻结额度
				creditInfoResponseDTO.setOpenFrozen(crdMain.getExpFrozen().toString());//冻结敞口
				creditInfoResponseDTO.setOrgNum(crdMain.getOrgNum());//机构号

				//添加到集合中
				creditInfoResponseList.add(creditInfoResponseDTO);


				//额度明细List
				CrdDetail params2 = new CrdDetail();
				params2.setCrdMainNum(crdMain.getCrdMainNum());
				List<CrdDetail> crdDetailList = crdDetailService.list(Condition.getQueryWrapper(params2));
				for (CrdDetail crdDetail : crdDetailList) {
					CreditDetailInfoResponseDTO creditDetailInfoResponseDTO = new CreditDetailInfoResponseDTO();
					creditDetailInfoResponseDTO.setCreditMainNum(crdDetail.getCrdMainNum());//二级额度编号
					creditDetailInfoResponseDTO.setCreditDetailNum(crdDetail.getCrdDetailNum());//三级额度编号
					creditDetailInfoResponseDTO.setCreditDetailPrd(crdDetail.getCrdDetailPrd());//三级额度品种
					creditDetailInfoResponseDTO.setCurrencyCd(crdDetail.getCurrencyCd());//币种
					creditDetailInfoResponseDTO.setLimitCredit(crdDetail.getLimitCredit().toString());//授信额度
					creditDetailInfoResponseDTO.setLimitUsed(crdDetail.getLimitUsed().toString());//已用额度
					creditDetailInfoResponseDTO.setLimitAvi(crdDetail.getLimitAvi().toString());//可用额度
					creditDetailInfoResponseDTO.setExpCredit(crdDetail.getExpCredit().toString());//授信敞口
					creditDetailInfoResponseDTO.setExpUsed(crdDetail.getExpUsed().toString());//已用敞口
					creditDetailInfoResponseDTO.setExpAvi(crdDetail.getExpAvi().toString());//可用敞口
					creditDetailInfoResponseDTO.setLimitPreUsed(crdDetail.getLimitPre().toString());//预占用额度
					creditDetailInfoResponseDTO.setExpPreUsed(crdDetail.getExpPre().toString());//预占用敞口
					creditDetailInfoResponseDTO.setLimitEarmark(crdDetail.getLimitEarmark().toString());//圈存额度
					//creditDetailInfoResponseDTO.setLimitEarmarkUsed(crdDetail.getLimitEarmarkUsed());//圈存已用额度
					//creditDetailInfoResponseDTO.setLimitEarmarkAvi(crdDetail.getLimitEarmarkAvi());//圈存可用额度
					creditDetailInfoResponseDTO.setLimitFrozen(crdDetail.getLimitFrozen()==null?null:crdDetail.getLimitFrozen().toString());//冻结额度
					creditDetailInfoResponseDTO.setExpFrozen(crdDetail.getExpFrozen()==null?null:crdDetail.getExpFrozen().toString());//冻结敞口
					creditDetailInfoResponseDTO.setOrgNum(crdDetail.getOrgNum());//机构号

					//添加到明细集合
					creditDetailInfoList.add(creditDetailInfoResponseDTO);

					CrdApplySerial params4 = new CrdApplySerial();
					params4.setCrdDetailNum(crdDetail.getCrdDetailNum());
					List<CrdApplySerial> crdApplySerialList = crdApplySerialService.list(Condition.getQueryWrapper(params4));

					for (CrdApplySerial crdApplySerial : crdApplySerialList) {
						//查询占用恢复流水信息
						WaterInfoResponseDTO waterInfoResponseDTO = new WaterInfoResponseDTO();
						waterInfoResponseDTO.setTranSeqSn(crdApplySerial.getTranSeqSn());//交易流水号
						waterInfoResponseDTO.setCreditMainNum(crdMain.getCrdMainNum());//额度编号
						waterInfoResponseDTO.setCreditDetailNum(crdApplySerial.getCrdDetailNum());//额度明细编号
						waterInfoResponseDTO.setCreditDetailPrd(crdApplySerial.getCrdDetailPrd());//额度产品编号（三级）
						waterInfoResponseDTO.setTranTypeCd(crdApplySerial.getTranTypeCd());//交易类型
						waterInfoResponseDTO.setAmt(crdApplySerial.getLimitCreditAmt().toString());//发生额
						waterInfoResponseDTO.setLimitCreditAmt(crdApplySerial.getLimitCreditAmt().toString());//占用/恢复额度金额
						waterInfoResponseDTO.setLimitOpenAmt(crdApplySerial.getExpCreditAmt().toString());//占用/恢复敞口金额
						waterInfoResponseDTO.setCurrencyCd(crdApplySerial.getCurrencyCd());//币种
						waterInfoResponseDTO.setTranDate(crdApplySerial.getTranDate());//交易日期
						waterInfoResponseDTO.setUserNum(crdApplySerial.getUserNum());//客户经理
						waterInfoResponseDTO.setOrgNum(crdApplySerial.getOrgNum());//机构号

						//添加到流水集合
						waterInfoList.add(waterInfoResponseDTO);
					}

				}


				//查询客户批复信息
				TbCrdApprove params3 = new TbCrdApprove();
				params2.setCrdMainNum(crdMain.getCrdMainNum());
				List<TbCrdApprove> crdApproveList = crdApproveService.list(Condition.getQueryWrapper(params3));

				for (TbCrdApprove crdApprove : crdApproveList) {
					//批复信息List
					ApproveInfoResponseDTO approveInfoResponseDTO = new ApproveInfoResponseDTO();
					approveInfoResponseDTO.setApproveNum(crdApprove.getApproveNum());//批复编号
					approveInfoResponseDTO.setCurrencyCd(crdApprove.getCurrencyCd());//币种
					approveInfoResponseDTO.setApproveAmt(crdApprove.getApproveAmt().toString());//批复明细金额
					approveInfoResponseDTO.setApproveUsed(crdApprove.getApproveUsed().toString());//批复明细已用
					approveInfoResponseDTO.setApproveAvi(crdApprove.getApproveAvi().toString());//批复明细可用
					approveInfoResponseDTO.setApproveExpAmt(crdApprove.getApproveExpAmt().toString());//批复敞口金额
					approveInfoResponseDTO.setApproveExpUsed(crdApprove.getApproveExpUsed().toString());//批复敞口已用
					approveInfoResponseDTO.setApproveExpAvi(crdApprove.getApproveExpAvi().toString());//批复敞口可用
					approveInfoResponseDTO.setProductNum(crdApprove.getProductNum());//业务品种
					approveInfoResponseDTO.setProductType(crdApprove.getProductType());//业务种类
					approveInfoResponseDTO.setIsCycle(crdApprove.getIsCycle());//额度循环标志
					approveInfoResponseDTO.setIndustry(crdApprove.getIndustry());//行业投向
					approveInfoResponseDTO.setGuaranteeType(crdApprove.getGuaranteeType());//担保方式
					approveInfoResponseDTO.setMainGuaranteeType(crdApprove.getMainGuaranteeType());//主担保方式
					approveInfoResponseDTO.setTerm(crdApprove.getTerm().toString());//申请期限
					approveInfoResponseDTO.setTermUnit(crdApprove.getTermUnit());//申请期限单位
					approveInfoResponseDTO.setProjectNum(crdApprove.getProjectNum());//项目协议号

					//添加到批复集合
					approveInfoList.add(approveInfoResponseDTO);

				}
			}

			//将集合添加响应体中
			creditQueryResponseDTO.setCreditDetailInfo(creditDetailInfoList);
			creditQueryResponseDTO.setCreditInfo(creditInfoResponseList);
			creditQueryResponseDTO.setApproveInfo(approveInfoList);
			creditQueryResponseDTO.setWaterInfo(waterInfoList);

			return creditQueryResponseDTO;
		}
		return null;
	}
}
