package org.git.modules.clm.rcm.mapper;

import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.rcm.entity.*;
import org.git.modules.clm.rcm.vo.RcmConfigTotalVO;

import java.util.List;

public interface RcmAnalyzeRptMapper {


	/**
	 * 查询当前限额数据
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	List<RcmConfigTotal> selectRcmConfigMonthData(String year, String month);


	/**
	 * 查询客户[授信]集中度限额
	 *
	 * @return
	 */
	List<RcmCusQuotaRpt> selectRcmCusQuotaRpt(@Param(value = "quotaIndexNum") String[] quotaIndexNum);

	/**
	 * 最大十家客户[贷款]集中度
	 *
	 * @return
	 */
	List<RcmLoanTenCusRpt> selectRcmLoanTenCusRpt(int rptType);

	/**
	 * 最大十家客户[授信]集中度
	 *
	 * @return
	 */
	List<RcmCreditTenCusRpt> selectRcmCreditTenCusRpt(int rptType);

	/**
	 * 最大十家集团客户[授信]集中度
	 *
	 * @return
	 */
	List<RcmCreditTenGroupCusRpt> selectRcmCreditTenGroupCusRpt(int rptType);


	/**
	 * 风险暴露情况简表
	 *
	 * @return
	 */
	List<RcmRiskExposureRpt> selectRcmRiskExposureRpt(int rptType);

	/**
	 * 行业授信集中度简表
	 *
	 * @return
	 */
	List<RcmIndustryQuotaBaseRpt> selectRcmIndustryQuotaBaseRpt(@Param(value = "quotaIndexNum") String[] quotaIndexNum);

	/**
	 * 行业授信集中度简表
	 *
	 * @return
	 */
	List<RcmIndustryQuotaDetailRpt> selectRcmIndustryQuotaDetailRpt(@Param(value = "quotaIndexNum") String[] quotaIndexNum);

}
