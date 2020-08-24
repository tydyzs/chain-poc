package org.git.modules.clm.rcm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.modules.clm.rcm.service.RcmAnalyzeRptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * chain-boot
 * 限额日终任务
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/1/17
 * @since 1.8
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/batch")
@Api(value = "限额日终任务", tags = "限额日终任务")
public class RcmBatchController {
	private RcmAnalyzeRptService rcmAnalyzeRptService;

	/**
	 * 1 生成历史限额数据			git-rcm/batch/BT70100001
	 * 2 客户授信集中度简表			git-rcm/batch/BT70100002
	 * 3 最大十家客户贷款集中度及最大十家单一客户明细表			git-rcm/batch/BT70100003
	 * 4 最大十家客户授信集中度及最大十家单一客户明细表			git-rcm/batch/BT70100004
	 * 5 最大十家集团客户授信集中度及最大十家集团客户明细表			git-rcm/batch/BT70100005
	 * 6 关联客户授信集中度简表<暂缓>			git-rcm/batch/BT70100006
	 * 7 关联客户授信集中度明细表<暂缓>			git-rcm/batch/BT70100007
	 * 8 行业授信集中度简表			git-rcm/batch/BT70100008
	 * 9 行业授信集中度明细表			git-rcm/batch/BT70100009
	 * 10 风险暴露情况简表			git-rcm/batch/BT70100010
	 * 11 同业客户风险暴露明细表（暂缓）			git-rcm/batch/BT70100011
	 * 12 匿名客户风险暴露金额变动图（暂缓）			git-rcm/batch/BT70100012
	 * 13 风险暴露超过1亿元（含）的单一客户业务规则
	 * 14 风险暴露超过一级资本净额2.5%的集团客户用信明细表业务规则
	 * 15 全客户（单一客户）明细表
	 * 16 全客户（集团客户）明细表
	 *
	 * @return
	 */

	@GetMapping("/BT70100001")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmConfigTotal() {
		return rcmAnalyzeRptService.insertRcmConfigTotal();
	}


	@GetMapping("/BT70100002")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmCusQuotaRpt() {
		return rcmAnalyzeRptService.insertRcmCusQuotaRpt();
	}


	@GetMapping("/BT70100003")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmLoanTenCusRpt() {
		return rcmAnalyzeRptService.insertRcmLoanTenCusRpt();
	}

	@GetMapping("/BT70100004")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmCreditTenCusRpt() {
		return rcmAnalyzeRptService.insertRcmCreditTenCusRpt();
	}

	@GetMapping("/BT70100005")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmCreditTenGroupCusRpt() {
		return rcmAnalyzeRptService.insertRcmCreditTenGroupCusRpt();
	}

	@GetMapping("/BT70100006")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmRelationQuotaBaseRpt() {
		return rcmAnalyzeRptService.insertRcmRelationQuotaBaseRpt();
	}


	@GetMapping("/BT70100007")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmRelationQuotaDetailRpt() {
		return rcmAnalyzeRptService.insertRcmRelationQuotaDetailRpt();
	}

	@GetMapping("/BT70100008")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmIndustryQuotaBaseRpt() {
		return rcmAnalyzeRptService.insertRcmIndustryQuotaBaseRpt();
	}

	@GetMapping("/BT70100009")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmIndustryQuotaDetailRpt() {
		return rcmAnalyzeRptService.insertRcmIndustryQuotaDetailRpt();
	}

	@GetMapping("/BT70100010")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int insertRcmRiskExposureRpt() {
		return rcmAnalyzeRptService.insertRcmRiskExposureRpt();
	}


	@GetMapping("/BT70100011")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100002() {
		return rcmAnalyzeRptService.insertRcmBankRiskExposureRpt();
	}

	@GetMapping("/BT70100012")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100001() {
		return rcmAnalyzeRptService.insertRcmAnonymityRiskExposureRpt();
	}


	@GetMapping("/BT70100013")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100013() {
		return 0;
	}

	@GetMapping("/BT70100014")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100014() {
		return 0;
	}


	@GetMapping("/BT70100015")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100015() {
		return 0;
	}

	@GetMapping("/BT70100016")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "限额日终")
	public int BT70100016() {
		return 0;
	}


}
