/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: 高伟达武汉事业部
 */
package org.git.modules.clm.credit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.FundEarmarkAllot;
import org.git.modules.clm.credit.entity.FundEarmarkMain;
import org.git.modules.clm.credit.service.IFundEarmarkAllotService;
import org.git.modules.clm.credit.service.IFundEarmarkMainService;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.fund.CrdEarkInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FundCreditDepositRequestDTO;
import org.git.modules.clm.credit.service.IFundEarmarkService;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CLM202额度圈存、分配、调整
 * @author SHC
 * @since 2019-12-03
 */
@Service
@AllArgsConstructor
@Slf4j
public class FundEarmarkImpl implements IFundEarmarkService {
	private IFundEarmarkMainService iFundEarmarkMainService;
	private IDeptService deptService;
	private ICrdService crdService;
	private ICrdMainService iCrdMainService;
	private ICrdDetailService iCrdDetailService;
	private ICrdApplySerialService iCrdApplySerialService;
	private IFundEarmarkAllotService iFundEarmarkAllotService;
	/**
	 * 事件落地前的检查
	 * */
	@Override
	public String checkEventStatus(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 金额平衡检查
		 *
		 * */
		BigDecimal crdEarkAmt=CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt());
		BigDecimal crdAllocAmtSum=new BigDecimal("0.00");

		for(CrdEarkInfoRequestDTO crdEarkInfoRequestDTO:fundCreditDepositRequestDTO.getCrdEarkInfo()){
			crdAllocAmtSum=crdAllocAmtSum.add(CommonUtil.stringToBigDecimal(crdEarkInfoRequestDTO.getCrdAllocAmt()));
		}
		if(!crdEarkAmt.equals(crdAllocAmtSum)){
			return "圈存金额与分配额度之和不匹配！";
		}
		/**
		 * 数据重复校验
		 * */
		FundEarmarkMain fundEarmarkMain=new FundEarmarkMain();
		fundEarmarkMain.setTranDate(tranDate);
		fundEarmarkMain.setCrdCurrencyCd(fundCreditDepositRequestDTO.getCrdCurrencyCd());
		fundEarmarkMain.setBusiDealNum(fundCreditDepositRequestDTO.getBusiDealNum());
		fundEarmarkMain.setCrdEarkAmt(CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt()));
		fundEarmarkMain.setTranEventStatus(JxrcbBizConstant.event_status_1);
		//fundEarmarkMain.setTranTypeCd(fundCreditDepositRequestDTO.getTranTypeCd());
		FundEarmarkMain fundEarmarkMainRs=iFundEarmarkMainService.getOne(Wrappers.query(fundEarmarkMain));
		if(fundEarmarkMainRs!=null){
			return "交易已存在！";
		}
		return "0";
	}
	/**
	 * 事件落地
	 * */
	@Transactional
	@Override
	public String saveEventStatus(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		/**
		 * 1.事件主表
		 * 2.事件明细表
		 * */
		FundEarmarkMain fundEarmarkMain=new FundEarmarkMain();
		fundEarmarkMain.setTranSeqSn(tranSeqSn);
		fundEarmarkMain.setTranDate(tranDate);
		String EventMainUUID= StringUtil.randomUUID();
		fundEarmarkMain.setEventMainId(EventMainUUID);
		BeanUtils.copyProperties(fundCreditDepositRequestDTO,fundEarmarkMain);
		fundEarmarkMain.setTranEventStatus(JxrcbBizConstant.event_status_0);
		fundEarmarkMain.setCrdEarkAmt(CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt()));
		iFundEarmarkMainService.save(fundEarmarkMain);

		List<CrdEarkInfoRequestDTO> crdAdmitInfo=fundCreditDepositRequestDTO.getCrdEarkInfo();
		for(CrdEarkInfoRequestDTO crdEarkInfoRequestDTO:crdAdmitInfo){
			FundEarmarkAllot fundEarmarkAllot=new FundEarmarkAllot();
			fundEarmarkAllot.setTranSeqSn(tranSeqSn);
			fundEarmarkAllot.setTranDate(tranDate);
			fundEarmarkAllot.setEventMainId(EventMainUUID);
			BeanUtils.copyProperties(crdEarkInfoRequestDTO, fundEarmarkAllot);
			BeanUtils.copyProperties(fundCreditDepositRequestDTO, fundEarmarkAllot);
			iFundEarmarkAllotService.save(fundEarmarkAllot);
		}
		return EventMainUUID;
	}
	/***
	 * 管控检查
	 */
	@Override
	public String control(String branchId,FundCreditDepositRequestDTO fundCreditDepositRequestDTO){
		/**1.校验机构是否是省联社*/
		String orgNum=deptService.selectProvincialCooperative().getId().toString();
		String zjSNS=JxrcbBizConstant.ZJXT_SNS;//资金系统省联社
		if(!branchId.equals(orgNum)&&!branchId.equals(zjSNS)){
			return "本交易的交易机构必须是省联社！";
		}
		/**
		 * 2.圈存客户、圈存额度、分配机构。圈存额度不能超过成员行此业务授信额度
		 * */
		String crdDetailPrd=fundCreditDepositRequestDTO.getCrdDetailPrd();
		String customerNum=fundCreditDepositRequestDTO.getCustomerNum();
		List<CrdEarkInfoRequestDTO> crdAdmitInfo=fundCreditDepositRequestDTO.getCrdEarkInfo();

		//本次交易数据库原有额度之和/本地交易圈存金额之和
		BigDecimal limitEarmarkSum=new BigDecimal("0");
		BigDecimal crdAllocAmtSum=new BigDecimal("0");
		for(CrdEarkInfoRequestDTO crdEarkInfoRequestDTO:crdAdmitInfo){
			String crdAllotOrgNum=crdEarkInfoRequestDTO.getCrdAllotOrgNum();
			BigDecimal crdAllocAmt=CommonUtil.stringToBigDecimal(crdEarkInfoRequestDTO.getCrdAllocAmt());
			crdAllocAmtSum.add(crdAllocAmt);
			CrdDetail crdDetail=new CrdDetail();
			crdDetail.setCustomerNum(customerNum);
			crdDetail.setCrdGrantOrgNum(crdAllotOrgNum);
			crdDetail.setCrdDetailPrd(crdDetailPrd);
			CrdDetail crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));
			limitEarmarkSum.add(crdDetailRs.getLimitEarmark());
			if(crdAllocAmt.compareTo(crdDetailRs.getLimitCredit())==1){
				return "圈存额度大于授信额度，交易失败！";
			}
			if(crdAllocAmt.compareTo(crdDetailRs.getLimitUsed()==null?new BigDecimal("0"):crdDetailRs.getLimitUsed())==-1){
				return "圈存额度小于已用额度，交易失败！";
			}
		}
		/**
		 * 3.
		 *圈存开始日期和到期日期，必须在额度主表的授信期起止之间
		 * */
		CrdMain crdMain=new CrdMain();
		crdMain.setCustomerNum(customerNum);
		crdMain.setCrdGrantOrgNum(orgNum);
		Crd crd = crdService.getById(fundCreditDepositRequestDTO.getCrdDetailPrd());
		crdMain.setCrdMainPrd(crd==null?null:crd.getSuperCrdNum());
		CrdMain crdMainRs=iCrdMainService.getOne(Wrappers.query(crdMain));
		String beginDate=crdMainRs.getBeginDate();
		String endDate=crdMainRs.getEndDate();
		String crdBeginDate=fundCreditDepositRequestDTO.getEarmarkBeginDate();
		String crdEndDate=fundCreditDepositRequestDTO.getEarmarkEndDate();
		if(compare_date(crdBeginDate,beginDate,"yyyy-MM-dd")==-1
			|| compare_date(crdEndDate,beginDate,"yyyy-MM-dd")==-1
			||compare_date(crdBeginDate,endDate,"yyyy-MM-dd")==1
			|| compare_date(crdEndDate,endDate,"yyyy-MM-dd")==1
		) {
			return "圈存生效日和圈存到期日不在授信期起止之间，交易失败！";
		}

		/**
		 * 4.圈存额度校验(新增需求）
		 *
		 *  4.1三级额度圈存不能大于二级额度可用且不能大于三级额度可用
		 *  4.2如果是三级额度：三级额度圈存额度之和不能大于二级圈存额度
		 *
		 * */
		String crd_type=fundCreditDepositRequestDTO.getCrdType();
		if(JxrcbBizConstant.CRD_TYPE_2.equals(crd_type)){
			//二级额度
				//1.省联社校验
			BigDecimal crd_eark_amt=CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt());
			BigDecimal limitCredit=crdMainRs.getLimitCredit();
			if(crd_eark_amt.compareTo(limitCredit)==1){
				return "省联社圈存时，二级圈存额度不能大于授信额度！交易失败！";
			}
			if(crd_eark_amt.compareTo(crdMainRs.getLimitUsed()==null?new BigDecimal("0"):crdMainRs.getLimitUsed())==-1){
				return "存圈额度小于已用额度！交易失败！";
			}
				//2.成员行校验
			for(CrdEarkInfoRequestDTO crdEarkInfoRequestDTO:crdAdmitInfo){
				String crdAllotOrgNum=crdEarkInfoRequestDTO.getCrdAllotOrgNum();
				BigDecimal crdAllocAmt=CommonUtil.stringToBigDecimal(crdEarkInfoRequestDTO.getCrdAllocAmt());
				CrdDetail crdDetail=new CrdDetail();
				crdDetail.setCustomerNum(customerNum);
				crdDetail.setCrdGrantOrgNum(crdAllotOrgNum);
				crdDetail.setCrdDetailPrd(crdDetailPrd);
				CrdDetail crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));
				if(crdAllocAmt.compareTo(crdDetailRs.getLimitCredit())==1){
					return "成员行圈存时，圈存额度不能大于授信额度，交易失败！";
				}
			}
		}else{
			//三级额度
			/*	//1.省联社校验
					//省联社三级额度之和
				CrdDetail crdDetailCheck=new CrdDetail();
				crdDetailCheck.setCustomerNum(customerNum);
				crdDetailCheck.setCustomerNum(orgNum);
				List<CrdDetail> crdDetailRsList=iCrdDetailService.list(Wrappers.query(crdDetailCheck));
				BigDecimal limitEarmarkSumDetail=new BigDecimal("0");
				for(CrdDetail crdDetails : crdDetailRsList){
					limitEarmarkSumDetail.add(crdDetails.getLimitEarmark());
				}
				BigDecimal crdSLS_3=limitEarmarkSumDetail-
					//省联社二级额度
				BigDecimal crdSLS_2=crdMainRs.getLimitEarmark();
				if(limitEarmarkSumDetail.compareTo(crdSLS_2)==1){
					return "省联社三级额度圈存时，三级额度圈存额度之和不能大于二级圈存额度！";
				}

*/

			/*CrdDetail crdDetailCheck=new CrdDetail();
			crdDetailCheck.setCustomerNum(customerNum);
			List<CrdDetail> crdDetailRsList=iCrdDetailService.list(Wrappers.query(crdDetailCheck));
			BigDecimal limitEarmarkSumDetail=new BigDecimal("0");
			for(CrdDetail crdDetails : crdDetailRsList){
				limitEarmarkSumDetail.add(crdDetails.getLimitEarmark());
			}
				//省联社三级额度
			crdMain.setCrdGrantOrgNum(orgNum);
			CrdDetail crdDetailSLS=iCrdDetailService.getOne(Wrappers.query(crdDetailCheck));
				//现有的值减去修改之前的省联社和成员行的值加上修改之后的省联社和成员行的值。
			BigDecimal sumCrd_3=limitEarmarkSumDetail.subtract(limitEarmarkSum).subtract(crdDetailSLS.getLimitEarmark())
				.add(crdAllocAmtSum).add(CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt()));//三级额度之和
			//统计该客户所有二级额度
			CrdMain crdMainCheck=new CrdMain();
			crdMainCheck.setCustomerNum(customerNum);
			List<CrdMain> crdCrdMainRsList=iCrdMainService.list(Wrappers.query(crdMainCheck));
			BigDecimal limitEarmarkSumMain=new BigDecimal("0");
			for(CrdMain crdMains : crdCrdMainRsList){
				limitEarmarkSumMain.add(crdMains.getLimitEarmark());
			}
			BigDecimal sumCrd_2=limitEarmarkSumMain;
			if(sumCrd_3.compareTo(sumCrd_2)==1){
				return "三级额度圈存额度之和不能大于二级圈存额度！";
			}*/
		}
		return "0";
	}
	/**
	 * 本地处理范围
	 * */
	@Transactional
	@Override
	public void localHandle(ExtAttributes extAttributes, FundCreditDepositRequestDTO fundCreditDepositRequestDTO,String EventMainUUID){
		String tranSeqSn=extAttributes.getOriReqSn();
		String tranDate=extAttributes.getOriReqDate();
		String orgNum=deptService.selectProvincialCooperative().getId().toString();;
		String crdDetailPrd=fundCreditDepositRequestDTO.getCrdDetailPrd();
		String customerNum=fundCreditDepositRequestDTO.getCustomerNum();
		String zjSNS=JxrcbBizConstant.ZJXT_SNS;//资金系统省联社
		/**
		 * 1.省联社修改额度圈存
		 * */
		CrdDetail crdDetail=new CrdDetail();
		crdDetail.setCustomerNum(customerNum);
		crdDetail.setCrdGrantOrgNum(orgNum);
		crdDetail.setCrdDetailPrd(crdDetailPrd);
		CrdDetail crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));
		CrdDetail crdDetailUp=new CrdDetail();
		crdDetailUp.setCrdDetailNum(crdDetailRs.getCrdDetailNum());
		crdDetailUp.setLimitEarmark(CommonUtil.stringToBigDecimal(fundCreditDepositRequestDTO.getCrdEarkAmt()));
		iCrdDetailService.updateById(crdDetailUp);
		/**2.流水插入*/
		CrdApplySerial crdApplySerial=new CrdApplySerial();
		crdApplySerial.setTranSeqSn(tranSeqSn);
		crdApplySerial.setTranDate(tranDate);
		BeanUtils.copyProperties(fundCreditDepositRequestDTO, crdApplySerial);
		BeanUtils.copyProperties(crdDetailRs, crdApplySerial);
		crdApplySerial.setCrdGrantOrgNum(orgNum);
		crdApplySerial.setCreateTime(CommonUtil.getWorkDateTime());
		crdApplySerial.setUpdateTime(CommonUtil.getWorkDateTime());
		iCrdApplySerialService.save(crdApplySerial);

		List<CrdEarkInfoRequestDTO> crdAdmitInfo=fundCreditDepositRequestDTO.getCrdEarkInfo();
		for(CrdEarkInfoRequestDTO crdEarkInfoRequestDTO:crdAdmitInfo){
			String crdAllotOrgNum=crdEarkInfoRequestDTO.getCrdAllotOrgNum();
			crdDetail=new CrdDetail();
			crdDetail.setCustomerNum(customerNum);
			crdDetail.setCrdGrantOrgNum(crdAllotOrgNum);
			crdDetail.setCrdDetailPrd(crdDetailPrd);
			crdDetailRs=iCrdDetailService.getOne(Wrappers.query(crdDetail));

			/**
			 * 1.成员行修改额度圈存
			 * */
			crdDetailUp.setCrdDetailNum(crdDetailRs.getCrdDetailNum());
			crdDetailUp.setLimitEarmark(CommonUtil.stringToBigDecimal(crdEarkInfoRequestDTO.getCrdAllocAmt()));
			iCrdDetailService.updateById(crdDetailUp);
			/**2.流水插入*/
			crdApplySerial=new CrdApplySerial();
			BeanUtils.copyProperties(fundCreditDepositRequestDTO, crdApplySerial);
			BeanUtils.copyProperties(crdEarkInfoRequestDTO, crdApplySerial);
			BeanUtils.copyProperties(crdDetailRs, crdApplySerial);
			crdApplySerial.setCrdGrantOrgNum(orgNum);
			crdApplySerial.setCreateTime(CommonUtil.getWorkDateTime());
			crdApplySerial.setUpdateTime(CommonUtil.getWorkDateTime());
			crdApplySerial.setTranSeqSn(tranSeqSn);
			crdApplySerial.setTranDate(tranDate);
			iCrdApplySerialService.save(crdApplySerial);
		}
		/**事件改为成功*/
		updateEventStatus(EventMainUUID,JxrcbBizConstant.event_status_1);
	}
	/**
	 * 更新事件表
	 * */
	@Override
	public void updateEventStatus(String EventMainUUID,String eventStatus){
		FundEarmarkMain fundEarmarkMain=new FundEarmarkMain();
		fundEarmarkMain.setEventMainId(EventMainUUID);
		fundEarmarkMain.setTranEventStatus(eventStatus);
		iFundEarmarkMainService.updateById(fundEarmarkMain);
	}
	/**BigDecimal转Double*/
	public Double getDouble(BigDecimal b){
		if(b==null){
			return 0.00;
		}
		return b.doubleValue();
	}
	/**
	 * 比较日期大小
	 * format:"yyyy-MM-dd"
	 * */
	public int compare_date(String DATE1, String DATE2,String format){
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				log.debug("dt1在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				log.debug("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			log.error(exception.toString());
		}
		return 0;
	}

}
