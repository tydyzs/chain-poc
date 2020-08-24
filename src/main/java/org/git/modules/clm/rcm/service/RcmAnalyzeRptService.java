package org.git.modules.clm.rcm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.rcm.entity.*;
import org.git.modules.clm.rcm.mapper.RcmAnalyzeRptMapper;
import org.git.modules.clm.rcm.vo.RcmConfigTotalVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Chain-Boot
 * 限额分析报表数据生成（日终调用）
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2019/12/25
 * @since 1.8
 */
@AllArgsConstructor
@Slf4j
@Service
public class RcmAnalyzeRptService {

	private RcmAnalyzeRptMapper rcmAnalyzeRptMapper;
	private RcmLedgerService rcmLedgerService;
	private IRcmConfigTotalService rcmConfigTotalService;
	private static boolean ALWAYS_ON = true;//是否总是执行

	/**
	 * 1 生成历史限额数据tb_rcm_config_total
	 * 每月末执行
	 */
	public int insertRcmConfigTotal() {
		//如果是月末
		if (CommonUtil.isLastDayOfMonth() || ALWAYS_ON) {
			List currList = rcmLedgerService.queryCurrentTotal(null);//RcmConfigTotalVO
//			for(Object total : currList){
//				RcmConfigTotal entity = (RcmConfigTotal)total;
//				System.out.println(entity.getQuotaNum()+"********");
//				rcmConfigTotalService.save(entity);
//			}
			rcmConfigTotalService.saveBatch(currList);
		}
		return 0;
	}


	/**
	 * 2 客户授信集中度简表（日报） tb_rcm_cus_quota_rpt
	 * 跑批时间：每日
	 */
	public int insertRcmCusQuotaRpt() {
		List<RcmCusQuotaRpt> rcmCusQuotaRpts = rcmAnalyzeRptMapper.selectRcmCusQuotaRpt(null);
		for (RcmCusQuotaRpt item : rcmCusQuotaRpts) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(rcmCusQuotaRpts);
		//如果是月末
		if (CommonUtil.isLastDayOfMonth()) {
			// TODO
		}
		return rcmCusQuotaRpts.size();
	}

	/**
	 * 3 最大十家客户贷款集中度及最大十家单一客户明细表 tb_rcm_loan_ten_cus_rpt
	 */
	public int insertRcmLoanTenCusRpt() {
		//报表类型rptType:1贷款 2授信
		List<RcmLoanTenCusRpt> rcmLoanTenCusRpts = rcmAnalyzeRptMapper.selectRcmLoanTenCusRpt(1);
		for (RcmLoanTenCusRpt item : rcmLoanTenCusRpts) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(rcmLoanTenCusRpts);
		return rcmLoanTenCusRpts.size();
	}

	/**
	 * 4最大十家客户授信集中度及最大十家单一客户明细表 tb_rcm_credit_ten_cus_rpt
	 */
	public int insertRcmCreditTenCusRpt() {
		//报表类型rptType:1贷款 2授信
		List<RcmCreditTenCusRpt> rcmCreditTenCusRpts = rcmAnalyzeRptMapper.selectRcmCreditTenCusRpt(2);
		for (RcmCreditTenCusRpt item : rcmCreditTenCusRpts) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			rcmCreditTenCusRpts.add(item);
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(rcmCreditTenCusRpts);
		return rcmCreditTenCusRpts.size();
	}

	/**
	 * 5	最大十家集团客户授信集中度及最大十家集团客户明细表 TB_RCM_CREDIT_TEN_GROUP_CUS
	 */
	public int insertRcmCreditTenGroupCusRpt() {
		//报表类型rptType:1贷款 2授信
		List<RcmCreditTenGroupCusRpt> list = rcmAnalyzeRptMapper.selectRcmCreditTenGroupCusRpt(2);
		for (RcmCreditTenGroupCusRpt item : list) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			list.add(item);
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(list);
		return list.size();
	}

	/**
	 * 6关联客户授信集中度简表<暂缓> tb_rcm_relation_quota_base_rpt
	 */
	public int insertRcmRelationQuotaBaseRpt() {
		return 0;
	}

	/**
	 * 7关联客户授信集中度明细表<暂缓> TB_RCM_RELATION_QUOTA_DETAIL
	 */
	public int insertRcmRelationQuotaDetailRpt() {
		return 0;
	}

	/**
	 * 8行业授信集中度简表 tb_rcm_industry_quota_base_rpt
	 */
	public int insertRcmIndustryQuotaBaseRpt() {
		//对公房地产授信(传入属于对公房地产授信的限额指标编号)
		List<RcmIndustryQuotaBaseRpt> listCorporation = rcmAnalyzeRptMapper.selectRcmIndustryQuotaBaseRpt(new String[]{"CB20191226013525", "CB20191226750353", "CB20191226386204"});
		//个人房地产授信(传入属于个人房地产授信的限额指标编号)
		List<RcmIndustryQuotaBaseRpt> listIndividual = rcmAnalyzeRptMapper.selectRcmIndustryQuotaBaseRpt(new String[]{"CB20191226707411", "CB20191226873823"});
		//产能过剩(传入属于产能过剩的限额指标编号)
		List<RcmIndustryQuotaBaseRpt> listSurplus = rcmAnalyzeRptMapper.selectRcmIndustryQuotaBaseRpt(new String[]{"CB20191226049888"});
		//房地产授信(传入属于房地产授信的限额指标编号)
		List<RcmIndustryQuotaBaseRpt> listEstate = rcmAnalyzeRptMapper.selectRcmIndustryQuotaBaseRpt(new String[]{"CB20191226013525", "CB20191226750353", "CB20191226386204", "CB20191226707411", "CB20191226873823"});
		List<RcmIndustryQuotaBaseRpt> list = new ArrayList<>();
		for (RcmIndustryQuotaBaseRpt item : listCorporation) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setIndustry("A1");//字典CD000271
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			list.add(item);
		}
		for (RcmIndustryQuotaBaseRpt item : listIndividual) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setIndustry("A2");//字典CD000271
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			list.add(item);
		}
		for (RcmIndustryQuotaBaseRpt item : listSurplus) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setIndustry("B");//字典CD000271
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			list.add(item);
		}
		for (RcmIndustryQuotaBaseRpt item : listEstate) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setIndustry("A");//字典CD000271
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			list.add(item);
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(list);
		return list.size();
	}

	/**
	 * 9行业授信集中度明细表 TB_RCM_INDUSTRY_QUOTA_DETAIL
	 */
	public int insertRcmIndustryQuotaDetailRpt() {
		//房地产行业授信集中度及产能过剩行业 限额指标编号
		String[] quotaIndexNums = {"CB20191226013525", "CB20191226750353", "CB20191226386204", "CB20191226707411", "CB20191226873823", "CB20191226049888"};
		List<RcmIndustryQuotaDetailRpt> industryQuotaDetailRpts = rcmAnalyzeRptMapper.selectRcmIndustryQuotaDetailRpt(quotaIndexNums);
		List<RcmIndustryQuotaDetailRpt> detailRptList = new ArrayList<>();
		for (RcmIndustryQuotaDetailRpt item : industryQuotaDetailRpts) {
			item.setInfoNum(StringUtil.randomUUID());
			item.setTotalMonth(CommonUtil.getWorkMonth());
			item.setTotalYear(CommonUtil.getWorkYear());
			item.setCreateTime(CommonUtil.getWorkDateTime());
			item.setUpdateTime(CommonUtil.getWorkDateTime());
			item.setUserNum("batch");
			item.setOrgNum("batch");
			detailRptList.add(item);
		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(detailRptList);
		return detailRptList.size();
	}


	/**
	 * 10风险暴露情况简表 tb_rcm_risk_exposure_rpt
	 */
	public int insertRcmRiskExposureRpt() {
		List<RcmRiskExposureRpt> list = new ArrayList<>();
		String[] quotaIndexNums = {"", "", ""};//风险暴露的指标编号
		List<RcmCusQuotaRpt> rcmCusQuotaRpts = rcmAnalyzeRptMapper.selectRcmCusQuotaRpt(quotaIndexNums);
		for (RcmCusQuotaRpt item : rcmCusQuotaRpts) {
			RcmRiskExposureRpt rpt = new RcmRiskExposureRpt();
			BeanUtils.copyProperties(item, rpt);
			rpt.setInfoNum(StringUtil.randomUUID());
			rpt.setTotalMonth(CommonUtil.getWorkMonth());
			rpt.setTotalYear(CommonUtil.getWorkYear());
			rpt.setCreateTime(CommonUtil.getWorkDateTime());
			rpt.setUpdateTime(CommonUtil.getWorkDateTime());
			rpt.setUserNum("batch");
			rpt.setOrgNum("batch");
			list.add(rpt);

		}
		ServiceImpl service = new ServiceImpl();
		service.saveBatch(list);
		return 0;
	}

	/**
	 * 11 同业客户风险暴露明细表（暂缓） tb_rcm_bank_risk_exposure_rpt
	 */
	public int insertRcmBankRiskExposureRpt() {
		return 0;
	}

	/**
	 * 12 匿名客户风险暴露金额变动图（暂缓） TB_RCM_ANONYMITY_RISKEXPOSURE
	 */
	public int insertRcmAnonymityRiskExposureRpt() {
		return 0;
	}

	/**13 风险暴露超过1亿元（含）的单一客户业务规则*/
	/**14 风险暴露超过一级资本净额2.5%的集团客户用信明细表业务规则*/
	/**15 全客户（单一客户）明细表*/
	/**16 全客户（集团客户）明细表*/


}
