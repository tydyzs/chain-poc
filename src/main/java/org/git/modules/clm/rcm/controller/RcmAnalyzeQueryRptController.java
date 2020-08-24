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
package org.git.modules.clm.rcm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.modules.clm.rcm.dto.RcmCreditTenCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmCreditTenGroupCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmLoanTenCusRptDTO;
import org.git.modules.clm.rcm.entity.RcmBankRiskExposureRpt;
import org.git.modules.clm.rcm.entity.RcmRelationQuotaBaseRpt;
import org.git.modules.clm.rcm.service.RcmAnalyzeQueryRptService;
import org.git.modules.clm.rcm.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 限额分析 控制器
 *
 * @author 周伟杰
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rpt")
@Api(value = "限额分析报表查询", tags = "限额分析报表查询")
public class RcmAnalyzeQueryRptController extends ChainController {

	private RcmAnalyzeQueryRptService rcmAnalyzeQueryRptService;

	/**
	 * 查询客户授信集中度简表
	 * write by zhouweijie
	 */
	@GetMapping("/queryRcmCusQuotaRpt")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询客户授信集中度", notes = "传入查询条件")
	public R<IPage<RcmCusQuotaRptVO>> queryRcmCusQuotaRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		IPage<RcmCusQuotaRptVO> pages = rcmAnalyzeQueryRptService.selectRcmCusQuotaRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 最大十家客户贷款集中度及最大十家单一客户明细表
	 * write by zhouweijie
	 */
	@GetMapping("/queryLoanTenCusRpt")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "最大十家客户贷款集中度及最大十家单一客户明细表", notes = "传入查询条件\n" +
		"根据结果中quotaControlType字段的值选择展示不同的控制值、预警值、观察值。quotaControlType为1时，阈值类型为金额；为2时阈值类型为占比\n" +
		"(金额类型的值，受金额单位的控制)")
	public R<RcmLoanTenCusRptDTO> queryLoanTenCusRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		RcmLoanTenCusRptDTO pages = rcmAnalyzeQueryRptService.selectRcmLoanTenCusRptPage(queryVO);
		return R.data(pages);
	}

	/**
	 * 查询最大十家客户授信集中度明细表
	 * write by zhouweijie
	 */
	@GetMapping("/queryCreditTenCusRpt")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询最大十家客户授信集中度明细表", notes = "传入查询条件")
	public R<RcmCreditTenCusRptDTO> queryCreditTenCusRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		RcmCreditTenCusRptDTO pages = rcmAnalyzeQueryRptService.selectRcmCreditTenCusRptPage(queryVO);
		return R.data(pages);
	}

	/**
	 * 查询最大十家集团客户授信集中度明细表
	 * write by zhouweijie
	 */
	@GetMapping("/queryCreditTenGroupCusRpt")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询最大十家集团客户授信集中度明细表", notes = "传入查询条件")
	public R<RcmCreditTenGroupCusRptDTO> queryCreditTenGroupCusRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		RcmCreditTenGroupCusRptDTO pages = rcmAnalyzeQueryRptService.selectRcmCreditTenGroupCusRptPage(queryVO);
		return R.data(pages);
	}

	/**
	 * 关联客户授信集中度简表
	 * caohaijie
	 */
	@GetMapping("/queryRelationQuotaBaseRpt")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询关联客户授信集中度简表", notes = "")
	public R<List<RcmRelationQuotaBaseRptVO>> queryRelationQuotaBaseRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		List<RcmRelationQuotaBaseRptVO> pages = rcmAnalyzeQueryRptService.selectRelationQuotaBaseRpt(queryVO);
		return R.data(pages);
	}

	/**
	 * 查询行业授信集中度简表
	 * write by zhouweijie
	 */
	@GetMapping("/queryIndustryQuotaBaseRpt")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询行业授信集中度简表", notes = "传入RcmQuotaAnalysisRptQueryVO\n" +
		"指标包括房地产行业授信敞口余额、产能过剩行业授信敞口余额。其中，房地产行业授信敞口余额细分为对公房地产授信（贷款、结构化融资、债券）、个人住房按揭、个人商用房按揭，产能过剩行业细分为产能过剩行业贷款和产能过剩行业非标。" +
		"（行业字段industry的值可根据字典CD000271反显）")
	public R<RcmIndustryQuotaBaseRptVO> queryIndustryQuotaBaseRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		RcmIndustryQuotaBaseRptVO pages = rcmAnalyzeQueryRptService.selectRcmIndustryQuotaBaseRpt(queryVO);
		return R.data(pages);
	}

	/**
	 * 查询行业授信集中度明细表（去掉）
	 * write by zhouweijie
	 */
	@GetMapping("/queryIndustryQuotaDetailRpt")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查询行业授信集中度明细表", notes = "传入RcmQuotaAnalysisRptQueryVO\n" +
		"指标包括房地产行业授信敞口余额、产能过剩行业授信敞口余额。其中，房地产行业授信敞口余额细分为对公房地产授信（贷款、结构化融资、债券）、个人住房按揭、个人商用房按揭，产能过剩行业细分为产能过剩行业贷款和产能过剩行业非标。")
	public R<IPage<RcmIndustryQuotaDetailRptVO>> queryIndustryQuotaDetailRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		IPage<RcmIndustryQuotaDetailRptVO> pages = rcmAnalyzeQueryRptService.selectRcmIndustryQuotaDetailRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询风险暴露情况简表
	 * write by zhouweijie
	 */
	@GetMapping("/queryRiskExposureRpt")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询风险暴露情况简表", notes = "传入RcmQuotaAnalysisRptQueryVO")
	public R<List<RcmCusQuotaRptVO>> queryRiskExposureRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		List<RcmCusQuotaRptVO> pages = rcmAnalyzeQueryRptService.selectRcmRiskExposureRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询同业客户风险暴露明细
	 * caohaijie
	 */
	@GetMapping("/queryBankRiskExposureRpt")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询同业客户风险暴露明细", notes = "传入RcmQuotaAnalysisRptQueryVO")
	public R<List<RcmBankRiskExposureRptVO>> queryBankRiskExposureRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		List<RcmBankRiskExposureRptVO> pages = rcmAnalyzeQueryRptService.selectRcmBankRiskExposureRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询匿名客户风险暴露明细
	 * caohaijie
	 */
	@GetMapping("/queryAnonymityRiskExposureRpt")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询同业客户风险暴露明细", notes = "传入RcmQuotaAnalysisRptQueryVO")
	public R<List<RcmAnonymityRiskExposureRptVO>> queryAnonymityRiskExposureRpt(RcmQuotaAnalysisRptQueryVO queryVO, Query query) {
		List<RcmAnonymityRiskExposureRptVO> pages = rcmAnalyzeQueryRptService.selectAnonymityRiskExposureRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}


	/**
	 * 查询风险暴露超过1亿元（含）的单一客户
	 * write by zhouweijie
	 */
	@GetMapping("/queryOverOneHundredMillion")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询风险暴露超过1亿元（含）的单一客户", notes = "传入查询条件（机构、客户名称（模糊查询）、客户经理（业务经办人）、管护机构）")
	public R<IPage<RcmRiskOverOneHundredMillionRptVO>> queryOverOneHundredMillion(RcmFullCusDetailQueryVO queryVO, Query query) {
		IPage<RcmRiskOverOneHundredMillionRptVO> pages = rcmAnalyzeQueryRptService.selectOverOneHundredMillion(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询风险暴露超过一级资本净额2.5%的集团客户
	 * write by zhouweijie
	 */
	@GetMapping("/queryOverTwoPointFivePercent")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询风险暴露超过一级资本净额2.5%的集团客户", notes = "传入查询条件（机构、客户名称（模糊查询）、客户经理（业务经办人）、管护机构）")
	public R<IPage<RcmRiskOverTwoPointFivePercentRptVO>> queryOverTwoPointFivePercent(RcmFullCusDetailQueryVO queryVO, Query query) {
		IPage<RcmRiskOverTwoPointFivePercentRptVO> pages = rcmAnalyzeQueryRptService.selectOverTwoPointFivePercent(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询全客户（单一客户）明细
	 */
	@GetMapping(value = "/querySingleCustomer", produces = "application/json;charset=utf-8")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "查询全客户（单一客户）明细", notes = "传入RcmFullCusDetailQueryVO")
	public R<IPage<RcmFullCusDetailRptVO>> querySingleCustomer(RcmFullCusDetailQueryVO queryVO, Query query) {
		IPage<RcmFullCusDetailRptVO> pages = rcmAnalyzeQueryRptService.selectSingleCusDetailRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}

	/**
	 * 查询全客户（集团客户）明细
	 */
	@GetMapping(value = "/queryGroupCustomer", produces = "application/json;charset=utf-8")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "查询全客户（集团客户）明细", notes = "传入RcmFullCusDetailQueryVO")
	public R<IPage<RcmFullCusDetailRptVO>> queryGroupCustomer(RcmFullCusDetailQueryVO queryVO, Query query) {
		IPage<RcmFullCusDetailRptVO> pages = rcmAnalyzeQueryRptService.selectGroupCusDetailRptPage(Condition.getPage(query), queryVO);
		return R.data(pages);
	}


}
