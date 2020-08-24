package org.git.modules.clm.batch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.common.utils.ValidateUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.batch.controller.BatchController;
import org.git.modules.clm.batch.mapper.BatchDataMapper;
import org.git.modules.clm.batch.service.IBatDataRecordService;
import org.git.modules.clm.batch.service.IBatchDataService;
import org.git.modules.clm.batch.service.IBatchService;
import org.git.modules.clm.batch.vo.TbBatDataRecord;
import org.git.modules.clm.batch.vo.TtCrdBillInfoSourceVO;
import org.git.modules.clm.front.api.jxrcb.*;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BatchDataServiceImpl extends ServiceImpl<BatchDataMapper, T> implements IBatchDataService {

	@Autowired
	IBatDataRecordService iBatDataRecordService;
	@Autowired
	IBatchService iBatchService;

	/**
	 * 合同信息接口
	 *
	 */
	@Override
	public String contractDataHandle() {
		String msg=null;
		String contractNum="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";
		try {
			List<HashMap> list = baseMapper.listContractNum();
			for (HashMap map : list) {
				LoanContractAPI api = new LoanContractAPI();
				ServiceBody sb = new ServiceBody();
				LoanContractInfoRequestDTO dto = new LoanContractInfoRequestDTO();
				contractNum = (map.get("CONTRACT_NUM") == null) ? "" : map.get("CONTRACT_NUM").toString();
				dto.setTranSeqSn(BizNumUtil.getBizNum("BTHT"));
				dto.setBizScene("BT");
				dto.setBizAction("HT");
				dto.setApproveNum((map.get("APPROVE_NUM") == null) ? "" : map.get("APPROVE_NUM").toString());
				dto.setContractNum((map.get("CONTRACT_NUM") == null) ? "" : map.get("CONTRACT_NUM").toString());
				dto.setProductNum((map.get("PRODUCT_NUM") == null) ? "" : map.get("PRODUCT_NUM").toString());
				dto.setProductType((map.get("PRODUCT_TYPE") == null) ? "" : map.get("PRODUCT_TYPE").toString());
				dto.setCurrencyCd((map.get("CURRENCY_CD") == null) ? "" : map.get("CURRENCY_CD").toString());
				dto.setExchangeRate((map.get("EXCHANGE_RATE") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("EXCHANGE_RATE").toString()));
				dto.setContractAmt((map.get("CONTRACT_AMT") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("CONTRACT_AMT").toString()));
				dto.setBeginDate((map.get("BEGIN_DATE") == null) ? "" : map.get("BEGIN_DATE").toString());
				dto.setEndDate((map.get("END_DATE") == null) ? "" : map.get("END_DATE").toString());
				dto.setIsCycle((map.get("IS_CYCLE") == null) ? "" : map.get("IS_CYCLE").toString());
				dto.setIndustry((map.get("INDUSTRY") == null) ? "" : map.get("INDUSTRY").toString());
				dto.setGuaranteeType((map.get("GUARANTEE_TYPE") == null) ? "" : map.get("GUARANTEE_TYPE").toString());
				dto.setMainGuaranteeType((map.get("MAIN_GUARANTEE_TYPE") == null) ? "" : map.get("MAIN_GUARANTEE_TYPE").toString());
				dto.setGuaranteeTypeDetail((map.get("GUARANTEE_TYPE_DETAIL") == null) ? "" : map.get("GUARANTEE_TYPE_DETAIL").toString());
				dto.setDepositRatio((map.get("DEPOSIT_RATIO") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("DEPOSIT_RATIO").toString()));
				dto.setContractStatus((map.get("CONTRACT_STATUS") == null) ? "" : map.get("CONTRACT_STATUS").toString());
				dto.setUserNum((map.get("USER_NUM") == null) ? "" : map.get("USER_NUM").toString());
				dto.setOrgNum((map.get("ORG_NUM") == null) ? "" : map.get("ORG_NUM").toString());

				List<SubContractInfoRequestDTO> scdtolist = baseMapper.listSubContractInfo(contractNum);
				System.out.println("------------ scdtolist info ------------");
				System.out.println(scdtolist.toString());
				dto.setSubcontractInfo(scdtolist);

				for (SubContractInfoRequestDTO ss : scdtolist) {
					String subcontractNum = ss.getSubcontractNum();
					List<PledgeInfoRequestDTO> pledgelist = baseMapper.listSuretyInfo(subcontractNum);
					System.out.println("------------ pledgelist info ------------");
					System.out.println(pledgelist.toString());
					dto.setPledgeInfo(pledgelist);

					List<SuretyInfoRequestDTO> personlist = baseMapper.listSuretyPersonInfo(subcontractNum);
					System.out.println("------------ personlist info ------------");
					System.out.println(personlist.toString());
					dto.setSuretyInfo(personlist);
				}

				if (StringUtil.isEmpty((map.get("s_contract_num") == null) ? "" : map.get("s_contract_num").toString())) {
					//新增
					dto.setOpType(JxrcbBizConstant.OP_TYPE_ADD);
				} else {
					//维护
					dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
				}

				ValidateUtil.validate(dto);
				sb.setRequest(dto);
				LoanContractInfoResponseDTO rs= (LoanContractInfoResponseDTO) api.run(sb);
				opType=dto.getOpType();
				responseStatus=rs.getServiceResponse().getStatus();
				responseCode=rs.getServiceResponse().getCode();
				responseMsg=rs.getServiceResponse().getDesc();



				if(!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())){
					msg="0";
					TbBatDataRecord ddr=new TbBatDataRecord();
					ddr.setBizNum(contractNum);
					ddr.setBizType("HTXX");
					ddr.setOpType(opType);
					ddr.setStatus(responseStatus);
					ddr.setCode(responseCode);
					ddr.setMsg(responseMsg);
					ddr.setTranDate(CommonUtil.getWorkDate());
					ddr.setCreateTime(CommonUtil.getWorkDateTime());
					ddr.setUpdateTime(CommonUtil.getWorkDateTime());
					iBatDataRecordService.save(ddr);
				}
			}
		}catch (Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(contractNum);
			ddr.setBizType("HTXX");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			System.out.println("ddr.toString:::"+ddr.toString());
			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 批复信息接口
	 *
	 */
	@Override
	public String approveDataHandle() {
		String msg="1";
		String approveNum="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";
		try{
			List<HashMap> list =baseMapper.listApproveNum();
			for (HashMap map : list) {
				LoanCreditAPI api=new LoanCreditAPI();
				ServiceBody sb = new ServiceBody();
				LoanCreditApplyRequestDTO dto = new LoanCreditApplyRequestDTO();
				approveNum = (map.get("APPROVE_NUM") == null) ? "" : map.get("APPROVE_NUM").toString();

/*				String opType=null;
				if (StringUtil.isEmpty((map.get("S_APPROVE_NUM") == null) ? "" : map.get("S_APPROVE_NUM").toString())) {
					//新增
					opType=JxrcbBizConstant.OP_TYPE_ADD;
				} else {
					//维护
					opType=JxrcbBizConstant.OP_TYPE_UPDATE;
				}*/


				dto.setTranSeqSn(BizNumUtil.getBizNum("BTAP"));
				dto.setBizScene("BT");
				dto.setBizAction("PF");


/*
				dto.setApproveNum(approveNum);
				dto.setCustomerNum((map.get("CUSTOMER_NUM") == null) ? "" : map.get("CUSTOMER_NUM").toString());
				dto.setCustomerType((map.get("CUSTOMER_TYPE") == null) ? "" : map.get("CUSTOMER_TYPE").toString());
				dto.setIsBankRel((map.get("IS_BANK_REL") == null) ? "" : map.get("IS_BANK_REL").toString());
				dto.setIsJointGuarantee((map.get("IS_JOINT_GUANANTEE") == null) ? "" : map.get("IS_JOINT_GUANANTEE").toString());
				dto.setUserNum((map.get("USER_NUM") == null) ? "" : map.get("USER_NUM").toString());
				dto.setOrgNum((map.get("ORG_NUM") == null) ? "" : map.get("ORG_NUM").toString());
				dto.setTotalAmt((map.get("TOTAL_AMT") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("TOTAL_AMT").toString()));
				dto.setBizType((map.get("BIZ_TYPE") == null) ? "" : map.get("BIZ_TYPE").toString());
				dto.setTranDate((map.get("TRAN_DATE") == null) ? "" : map.get("TRAN_DATE").toString());
				dto.setApproveStatus((map.get("APPROVE_STATUS") == null) ? "" : map.get("APPROVE_STATUS").toString());
*/

				List<LimitDetailInfoRequestDTO> detaillist=baseMapper.listApproveDetailInfo(approveNum);

				for(LimitDetailInfoRequestDTO dd:detaillist){
					dd.setOpType(JxrcbBizConstant.OP_TYPE_MERGE);
				}
				System.out.println("------------ detaillist info ------------");
				System.out.println(detaillist.toString());
				dto.setLimitDetailInfo(detaillist);

				ValidateUtil.validate(dto);
				sb.setRequest(dto);
				LoanCreditApplyResponseDTO rs= (LoanCreditApplyResponseDTO) api.run(sb);
				opType=JxrcbBizConstant.OP_TYPE_MERGE;
				responseStatus=rs.getServiceResponse().getStatus();
				responseCode=rs.getServiceResponse().getCode();
				responseMsg=rs.getServiceResponse().getDesc();



				if(!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())){
					msg="0";
					TbBatDataRecord ddr=new TbBatDataRecord();
					ddr.setBizNum(approveNum);
					ddr.setBizType("PFXX");
					ddr.setOpType(opType);
					ddr.setStatus(responseStatus);
					ddr.setCode(responseCode);
					ddr.setMsg(responseMsg);
					ddr.setTranDate(CommonUtil.getWorkDate());
					ddr.setCreateTime(CommonUtil.getWorkDateTime());
					ddr.setUpdateTime(CommonUtil.getWorkDateTime());
					iBatDataRecordService.save(ddr);
				}
			}

		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(approveNum);
			ddr.setBizType("PFXX");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 借据信息接口
	 *
	 */
	@Override
	public String summaryDataHandle() {
		String msg="1";
		String summaryNum="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";
		try{
			List<HashMap> list =baseMapper.listSummaryNum();
			for (HashMap map : list) {
				LoanSummaryAPI api=new LoanSummaryAPI();
				ServiceBody sb = new ServiceBody();
				LoanSummaryInfoRequestDTO dto = new LoanSummaryInfoRequestDTO();
				summaryNum = (map.get("SUMMARY_NUM") == null) ? "" : map.get("SUMMARY_NUM").toString();
				dto.setTranSeqSn(BizNumUtil.getBizNum("BTJJ"));
				dto.setBizScene("BT");
				dto.setBizAction("JJ");
				dto.setSummaryNum(summaryNum);
				dto.setContractNum((map.get("CONTRACT_NUM") == null) ? "" : map.get("CONTRACT_NUM").toString());
				dto.setUserNum((map.get("USER_NUM") == null) ? "" : map.get("USER_NUM").toString());
				dto.setOrgNum((map.get("ORG_NUM") == null) ? "" : map.get("ORG_NUM").toString());
				dto.setCurrencyCd((map.get("CURRENCY_CD") == null) ? "" : map.get("CURRENCY_CD").toString());
				dto.setExchangeRate((map.get("EXCHANGE_RATE") == null) ? "" : map.get("EXCHANGE_RATE").toString());
				dto.setSummaryAmt((map.get("SUMMARY_AMT") == null) ? "" : map.get("SUMMARY_AMT").toString());
				dto.setSummaryBal((map.get("SUMMARY_BAL") == null) ? "" : map.get("SUMMARY_BAL").toString());
				dto.setBeginDate((map.get("BEGIN_DATE") == null) ? "" : map.get("BEGIN_DATE").toString());
				dto.setEndDate((map.get("END_DATE") == null) ? "" : map.get("END_DATE").toString());
				dto.setSummaryStatus((map.get("SUMMARY_STATUS") == null) ? "" : map.get("SUMMARY_STATUS").toString());
				dto.setGuaranteeType((map.get("GUARANTEE_TYPE") == null) ? "" : map.get("GUARANTEE_TYPE").toString());
				dto.setMainGuaranteeType((map.get("MAIN_GUARANTEE_TYPE") == null) ? "" : map.get("MAIN_GUARANTEE_TYPE").toString());


				if (StringUtil.isEmpty((map.get("S_SUMMARY_NUM") == null) ? "" : map.get("S_SUMMARY_NUM").toString())) {
					//新增
					dto.setOpType(JxrcbBizConstant.OP_TYPE_ADD);
				} else {
					//维护
					dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
				}
				ValidateUtil.validate(dto);
				sb.setRequest(dto);
				LoanSummaryInfoResponseDTO rs= (LoanSummaryInfoResponseDTO) api.run(sb);
				opType=dto.getOpType();
				responseStatus=rs.getServiceResponse().getStatus();
				responseCode=rs.getServiceResponse().getCode();
				responseMsg=rs.getServiceResponse().getDesc();



				if(!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())){
					msg="0";
					TbBatDataRecord ddr=new TbBatDataRecord();
					ddr.setBizNum(summaryNum);
					ddr.setBizType("JJXX");
					ddr.setOpType(opType);
					ddr.setStatus(responseStatus);
					ddr.setCode(responseCode);
					ddr.setMsg(responseMsg);
					ddr.setTranDate(CommonUtil.getWorkDate());
					ddr.setCreateTime(CommonUtil.getWorkDateTime());
					ddr.setUpdateTime(CommonUtil.getWorkDateTime());
					System.out.println("ddr.toString:::"+ddr.toString());
					iBatDataRecordService.save(ddr);
				}
			}

		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(summaryNum);
			ddr.setBizType("JJXX");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			System.out.println("ddr.toString:::"+ddr.toString());
			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 项目协议信息接口
	 *
	 */
	@Override
	public String projectDataHandle() {
		String msg="1";
		String projectNum="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";

		try{
			List<HashMap> list =baseMapper.listProjectNum();
			for (HashMap map : list) {
				LoanProjectAPI api = new LoanProjectAPI();

				ServiceBody sb = new ServiceBody();
				LoanProjectRequestDTO dto = new LoanProjectRequestDTO();
				projectNum = (map.get("PROJECT_NUM") == null) ? "" : map.get("PROJECT_NUM").toString();
				dto.setTranSeqSn(BizNumUtil.getBizNum("BTPRJ"));

				dto.setBizScene("BT");
				dto.setBizAction("XM");
				dto.setCustomerNum((map.get("CUSTOMER_NUM") == null) ? "" : map.get("CUSTOMER_NUM").toString());

				dto.setCustomerType((map.get("CUSTOMER_TYPE") == null) ? "" : map.get("CUSTOMER_TYPE").toString());
				dto.setProjectNum(projectNum);

				dto.setProjectName((map.get("PROJECT_NAME") == null) ? "" : map.get("PROJECT_NAME").toString());
				dto.setProjectType((map.get("PROJECT_TYPE") == null) ? "" : map.get("PROJECT_TYPE").toString());
				dto.setCurrencyCd((map.get("CURRENCY_CD") == null) ? "" : map.get("CURRENCY_CD").toString());
				dto.setTotalAmt((map.get("TOTAL_AMT") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("TOTAL_AMT").toString()));

				dto.setLimitControlType((map.get("LIMIT_CONTROL_TYPE") == null) ? "" : map.get("LIMIT_CONTROL_TYPE").toString());
				dto.setApplyDate((map.get("APPLY_DATE") == null) ? "" : map.get("APPLY_DATE").toString());
				dto.setAgreementTerm((map.get("AGREEMENT_TERM") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("AGREEMENT_TERM").toString()));

				dto.setAgreementTermUnit((map.get("AGREEMENT_TERM_UNIT") == null) ? "" : map.get("AGREEMENT_TERM_UNIT").toString());
				dto.setProjectStatus((map.get("PROJECT_STATUS") == null) ? "" : map.get("PROJECT_STATUS").toString());
				dto.setUserNum((map.get("USER_NUM") == null) ? "" : map.get("USER_NUM").toString());
				dto.setOrgNum((map.get("ORG_NUM") == null) ? "" : map.get("ORG_NUM").toString());



				if (StringUtil.isEmpty((map.get("S_PROJECT_NUM") == null) ? "" : map.get("S_PROJECT_NUM").toString())) {
					//新增
					dto.setOpType(JxrcbBizConstant.OP_TYPE_ADD);
				} else {
					//维护
					dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
				}
				System.out.println("---- dto info ----");
				System.out.println(dto.toString());
				System.out.println("------------------");
				ValidateUtil.validate(dto);
				sb.setRequest(dto);
				LoanProjectResponseDTO rs= (LoanProjectResponseDTO) api.run(sb);

				opType=dto.getOpType();
				responseStatus=rs.getServiceResponse().getStatus();
				responseCode=rs.getServiceResponse().getCode();
				responseMsg=rs.getServiceResponse().getDesc();



				if(!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())){
					msg="0";
					TbBatDataRecord ddr=new TbBatDataRecord();
					ddr.setBizNum(projectNum);
					ddr.setBizType("XMXY");
					ddr.setOpType(dto.getOpType());
					ddr.setStatus(rs.getServiceResponse().getStatus());
					ddr.setCode(rs.getServiceResponse().getCode());
					ddr.setMsg(rs.getServiceResponse().getDesc());
					ddr.setTranDate(CommonUtil.getWorkDate());
					ddr.setCreateTime(CommonUtil.getWorkDateTime());
					ddr.setUpdateTime(CommonUtil.getWorkDateTime());
					iBatDataRecordService.save(ddr);
				}
			}

		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(projectNum);
			ddr.setBizType("XMXY");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	/*
	 *  实时还款信息接口
	 */
	@Override
	public String recoveryEventDataHandle() {
		String msg="1";
		String summary_num="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";

		try{
			List<HashMap> list =baseMapper.listrecoveryEventNum();
			for (HashMap map : list) {
				LoanRepayAPI api = new LoanRepayAPI();
				ServiceBody sb = new ServiceBody();
				LoanRepayRequestDTO dto = new LoanRepayRequestDTO();
				String tran_seq_sn = (map.get("TRAN_SEQ_SN") == null) ? "" : map.get("TRAN_SEQ_SN").toString();
				dto.setTranSeqNum(tran_seq_sn);

				dto.setBizScene("BT");
				dto.setBizAction("HK");
				dto.setDateSource((map.get("DATE_SOURCE") == null) ? "" : map.get("DATE_SOURCE").toString());
				dto.setIsSettle((map.get("IS_SETTLE") == null) ? "" : map.get("IS_SETTLE").toString());
				summary_num=(map.get("SUMMARY_NUM") == null) ? "" : map.get("SUMMARY_NUM").toString();
				dto.setSummaryNum(summary_num);
				//dto.setCurrencyCd((map.get("CURRENCY_CD") == null) ? "" : map.get("CURRENCY_CD").toString());

				dto.setRepayAmt((map.get("REPAY_AMT") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(map.get("REPAY_AMT").toString()));

				dto.setRepayDate((map.get("REPAY_DATE") == null) ? "" : map.get("REPAY_DATE").toString());
				dto.setSummaryBal((map.get("SUMMARY_BAL") == null) ? "" : map.get("SUMMARY_BAL").toString());

				dto.setUserNum((map.get("USER_NUM") == null) ? "" : map.get("USER_NUM").toString());
				dto.setOrgNum((map.get("ORG_NUM") == null) ? "" : map.get("ORG_NUM").toString());



				if (StringUtil.isEmpty((map.get("S_TRAN_SEQ_SN") == null) ? "" : map.get("S_TRAN_SEQ_SN").toString())) {
					//新增
					dto.setOpType(JxrcbBizConstant.OP_TYPE_ADD);
				} else {
					//维护
					dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
				}
				System.out.println("---- dto info ----");
				System.out.println(dto.toString());
				System.out.println("------------------");
				ValidateUtil.validate(dto);
				sb.setRequest(dto);
				LoanRepayResponseDTO rs= (LoanRepayResponseDTO) api.run(sb);


				opType=dto.getOpType();
				responseStatus=rs.getServiceResponse().getStatus();
				responseCode=rs.getServiceResponse().getCode();
				responseMsg=rs.getServiceResponse().getDesc();



				if(!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())){
					msg="0";
					TbBatDataRecord ddr=new TbBatDataRecord();
					ddr.setBizNum(summary_num);
					ddr.setBizType("SSHK");
					ddr.setOpType(opType);
					ddr.setStatus(responseStatus);
					ddr.setCode(responseCode);
					ddr.setMsg(responseMsg);
					ddr.setTranDate(CommonUtil.getWorkDate());
					ddr.setCreateTime(CommonUtil.getWorkDateTime());
					ddr.setUpdateTime(CommonUtil.getWorkDateTime());
					iBatDataRecordService.save(ddr);
				}
			}

		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(summary_num);
			ddr.setBizType("SSHK");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public String billDataHandle() {
		String msg="1";
		String contractNum="";
		String opType="";
		String responseStatus="";
		String responseCode="";
		String responseMsg="";
		try{
			int i=0;
			List<HashMap> contractnumlist =baseMapper.listContractNumOfBill();
			for (HashMap map : contractnumlist) {
				i++;
				LoanBillSignAPI api = new LoanBillSignAPI();
				ServiceBody sb = new ServiceBody();
				LoanBillSignRequestDTO dto = new LoanBillSignRequestDTO();
				TtCrdBillInfoSourceVO vo=new TtCrdBillInfoSourceVO();

				contractNum = (map.get("CONTRACT_NUM") == null) ? "" : map.get("CONTRACT_NUM").toString();
				dto.setTranSeqSn(BizNumUtil.getBizNum("BTBILL"));
				dto.setContractNum(contractNum);
				vo.setContractNum(contractNum);
				dto.setBizScene("BT");
				dto.setBizAction("BT");

				List<HashMap> headlist=baseMapper.getHeadInfo(contractNum);
				HashMap mm=headlist.get(0);

				dto.setCurrencyCd((mm.get("CURRENCY_CD") == null) ? "" : mm.get("CURRENCY_CD").toString());
				vo.setCurrencyCd((mm.get("CURRENCY_CD") == null) ? "" : mm.get("CURRENCY_CD").toString());
				dto.setExchangeRate((mm.get("EXCHANGE_RATE") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(mm.get("EXCHANGE_RATE").toString()));
				vo.setExchangeRate((mm.get("EXCHANGE_RATE") == null) ? CommonUtil.stringToBigDecimal("") : CommonUtil.stringToBigDecimal(mm.get("EXCHANGE_RATE").toString()));

				dto.setUserNum((mm.get("USER_NUM") == null) ? "" : mm.get("USER_NUM").toString());
				vo.setUserNum((mm.get("USER_NUM") == null) ? "" : mm.get("USER_NUM").toString());

				dto.setOrgNum((mm.get("ORG_NUM") == null) ? "" : mm.get("ORG_NUM").toString());
				vo.setOrgNum((mm.get("ORG_NUM") == null) ? "" : mm.get("ORG_NUM").toString());

				List<BillInfoRequestDTO> billlist=baseMapper.listBillInfo(contractNum);
				System.out.println("billlist.size:::"+billlist.size());
				System.out.println("------------ billlist info ------------");
				System.out.println(billlist.toString());
				dto.setBillInfo(billlist);


				if (StringUtil.isEmpty((map.get("S_CONTRACT_NUM") == null) ? "" : map.get("S_CONTRACT_NUM").toString())) {
					//新增
					dto.setOpType(JxrcbBizConstant.OP_TYPE_ADD);
				} else {
					//维护
					String updateflag="0";
					for(BillInfoRequestDTO requstdto:billlist){
						vo.setBillNum(requstdto.getBillNum());
						vo.setSummaryNum(requstdto.getSummaryNum());
						vo.setBillType(requstdto.getBillType());
						vo.setBillStatus(requstdto.getBillStatus());
						vo.setSummaryAmt(requstdto.getSummaryAmt());
						vo.setBeginDate(requstdto.getBeginDate());
						vo.setEndDate(requstdto.getEndDate());
						vo.setDepositRatio(requstdto.getDepositRatio());
						vo.setGuaranteeType(requstdto.getGuaranteeType());
						vo.setMainGuaranteeType(requstdto.getMainGuaranteeType());
						vo.setAcceptorEcifNum(requstdto.getAcceptorEcifNum());
						vo.setDrawerName(requstdto.getDrawerName());
						vo.setDrawerAcct(requstdto.getDrawerAcct());
						vo.setDrawerBankNum(requstdto.getDrawerBankNum());
						vo.setDrawerBankName(requstdto.getDrawerBankName());
						vo.setPayName(requstdto.getPayName());
						vo.setPayAcct(requstdto.getPayAcct());
						vo.setPayeeName(requstdto.getPayeeName());
						vo.setPayeeAcct(requstdto.getPayeeAcct());
						vo.setPayeeBankNum(requstdto.getPayeeBankNum());
						vo.setPayeeBankName(requstdto.getPayeeBankName());

						String temp=baseMapper.isEqualsBillInfo(vo);
						if(temp=="0"||temp.equals("0")){
							updateflag="1";
						}
					}
					if(updateflag=="1"||updateflag.equals("1")) {
						dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
					}else{
						dto.setOpType("");
					}
					//dto.setOpType(JxrcbBizConstant.OP_TYPE_UPDATE);
				}
				System.out.println("---- dto info ----");
				System.out.println(dto.toString());
				System.out.println("------------------");
				System.out.println("--- dto.getOpType==="+dto.getOpType());

				if(dto.getOpType()=="" || dto.getOpType().equals("")){
					System.out.println("--- 操作类型为空");
				}else {
					ValidateUtil.validate(dto);
					sb.setRequest(dto);
					LoanBillSignResponseDTO rs = (LoanBillSignResponseDTO) api.run(sb);
					/*
					rs.getServiceResponse().getStatus()//
					rs.getServiceResponse().getCode();
					rs.getServiceResponse().getDesc();
					CommonUtil.getWorkDateTime(); //chuangjian
					CommonUtil.getWorkDate();//
					//业务编号，类似于合同号，借据号
					//业务类型，合同，借据
					*/
					opType=dto.getOpType();
					responseStatus=rs.getServiceResponse().getStatus();
					responseCode=rs.getServiceResponse().getCode();
					responseMsg=rs.getServiceResponse().getDesc();



					if (!JxrcbConstant.ESB_STATUS_COMPLETE.equals(rs.getServiceResponse().getStatus())) {
						msg = "0";
						TbBatDataRecord ddr=new TbBatDataRecord();
						ddr.setBizNum(contractNum);
						ddr.setBizType("YPQF");
						ddr.setOpType(opType);
						ddr.setStatus(responseStatus);
						ddr.setCode(responseCode);
						ddr.setMsg(responseMsg);
						ddr.setTranDate(CommonUtil.getWorkDate());
						ddr.setCreateTime(CommonUtil.getWorkDateTime());
						ddr.setUpdateTime(CommonUtil.getWorkDateTime());
						iBatDataRecordService.save(ddr);
					}
				}
			}

			System.out.println("总共执行了"+i+"次");
		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum(contractNum);
			ddr.setBizType("YPQF");
			ddr.setOpType(opType);
			ddr.setStatus(responseStatus);
			ddr.setCode(responseCode);
			ddr.setMsg(responseMsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());

			iBatDataRecordService.save(ddr);
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public String ccDataHandle() {
		String msg="1";
		String rtmsg="";
		try{
			baseMapper.ccDataInsertOrUpdate(CommonUtil.getWorkDateTime().toString());

			rtmsg=iBatchService.btCcStatis();
			if(!StringUtil.isEmpty(rtmsg)){
				TbBatDataRecord ddr=new TbBatDataRecord();
				ddr.setBizNum("");
				ddr.setBizType("CC");
				ddr.setOpType(JxrcbBizConstant.OP_TYPE_MERGE);
				ddr.setStatus(JxrcbConstant.ESB_STATUS_FAIL);
				ddr.setCode(String.valueOf(JxrcbConstant.ESB_CODE_FAIL));
				ddr.setMsg(rtmsg);
				ddr.setTranDate(CommonUtil.getWorkDate());
				ddr.setCreateTime(CommonUtil.getWorkDateTime());
				ddr.setUpdateTime(CommonUtil.getWorkDateTime());
				msg="0";
			}else{
				msg="1";
				TbBatDataRecord ddr=new TbBatDataRecord();
				ddr.setBizNum("");
				ddr.setBizType("CC");
				ddr.setOpType(JxrcbBizConstant.OP_TYPE_MERGE);
				ddr.setStatus(JxrcbConstant.ESB_STATUS_COMPLETE);
				ddr.setCode(String.valueOf(JxrcbConstant.ESB_CODE_SUCCESS));
				ddr.setMsg(rtmsg);
				ddr.setTranDate(CommonUtil.getWorkDate());
				ddr.setCreateTime(CommonUtil.getWorkDateTime());
				ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			}
		}catch(Exception e){
			msg="0";
			TbBatDataRecord ddr=new TbBatDataRecord();
			ddr.setBizNum("");
			ddr.setBizType("CC");
			ddr.setOpType(JxrcbBizConstant.OP_TYPE_MERGE);
			ddr.setStatus(JxrcbConstant.ESB_STATUS_FAIL);
			ddr.setCode(String.valueOf(JxrcbConstant.ESB_CODE_FAIL));
			ddr.setMsg(rtmsg);
			ddr.setTranDate(CommonUtil.getWorkDate());
			ddr.setCreateTime(CommonUtil.getWorkDateTime());
			ddr.setUpdateTime(CommonUtil.getWorkDateTime());
			e.printStackTrace();
		}
		return msg;
	}
}
