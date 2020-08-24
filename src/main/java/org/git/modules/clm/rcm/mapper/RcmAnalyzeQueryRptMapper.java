package org.git.modules.clm.rcm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.rcm.vo.*;

import java.util.List;

/**
 * 限额分析  mapper接口
 *
 * @author zhouweijie
 */
public interface RcmAnalyzeQueryRptMapper {

	/**
	 * 查询客户授信集中度
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmCusQuotaRptVO> selectRcmCusQuotaRptPage(@Param(value = "page") IPage page,
													@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 查询客户授信集中度
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmCusQuotaRptVO> selectRcmCusQuotaRptPage(@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 最大十家客户贷款集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmLoanTenCusRptVO> selectRcmLoanTenCusRptPage(@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 查询限额管控参数信息
	 *
	 * @param queryVO  查询条件
	 * @param quotaNum 限额指标编号
	 * @return
	 */
	RcmQuotaInfoVO selectQuotaInfo(@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO,
								   @Param(value = "quotaNum") String quotaNum);

	/**
	 * 最大十家客户授信集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmCreditTenCusRptVO> selectRcmCreditTenCusRptPage(@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);


	/**
	 * 查询最大十家集团客户授信集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmCreditTenGroupCusRptVO> selectRcmCreditTenGroupCusRptPage(@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);


	/**
	 * 查询行业授信集中度简表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmIndustryQuotaBaseRptVO> selectRcmIndustryQuotaBaseRptPage(@Param(value = "page") IPage page,
																	  @Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 查询行业授信集中度明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmIndustryQuotaDetailRptVO> selectRcmIndustryQuotaDetailRptPage(@Param(value = "page") IPage page,
																		  @Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 查询风险暴露情况简表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmRiskExposureRptVO> selectRcmRiskExposureRptPage(@Param(value = "page") IPage page,
															@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);


	/**
	 * 查询同业客户风险暴露明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmBankRiskExposureRptVO> selectRcmBankRiskExposureRptPage(@Param(value = "page") IPage page,
															@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 查询匿名客户风险暴露明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmAnonymityRiskExposureRptVO> selectAnonymityRiskExposureRptPage(@Param(value = "page") IPage page,
																	@Param(value = "queryVO") RcmQuotaAnalysisRptQueryVO queryVO);


	/**
	 * 查询风险暴露超过1亿元（含）的单一客户
	 *
	 * @param page
	 * @param queryVO   查询条件
	 * @return
	 */
	List<RcmRiskOverOneHundredMillionRptVO> selectOverOneHundredMillion(@Param(value = "page") IPage page,
																		@Param(value = "queryVO") RcmFullCusDetailQueryVO queryVO);


	/**
	 * 查询风险暴露超过一级资本净额2.5%的集团客户
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	List<RcmRiskOverTwoPointFivePercentRptVO> selectOverTwoPointFivePercent(@Param(value = "page") IPage page,
																			@Param(value = "queryVO") RcmFullCusDetailQueryVO queryVO);


//	/**
//	 * 查询业务信息
//	 *
//	 * @param page
//	 * @param customerNum 客户编号
//	 * @param userOrgType 登录用户机构类型
//	 * @param userOrgNum  登录用户机构号
//	 * @return
//	 */
//	List<RcmRiskBusinessInfoVO> selectBusinessInfo(@Param(value = "page") IPage page,
//												   @Param(value = "customerNum") String customerNum,
//												   @Param(value = "userOrgType") String userOrgType,
//												   @Param(value = "userOrgNum") String userOrgNum);
//
//
//	/**
//	 * 查询押品信息
//	 *
//	 * @param page
//	 * @param businessNum 业务编号（此处暂为借据号）
//	 * @return
//	 */
//	List<TbCrdSuretyVO> selectSuretyInfo(@Param(value = "page") IPage page,
//										 @Param(value = "businessNum") String businessNum);


	/**
	 * 全客户(单一客户)明细
	 *
	 * @param page
	 * @param queryVO   查询条件
	 * @param year      当前营业年份
	 * @param lastMonth 上月月份
	 * @return
	 */
	List<RcmFullCusDetailRptVO> selectSingleCusDetailRptPage(@Param(value = "page") IPage page,
															 @Param(value = "queryVO") RcmFullCusDetailQueryVO queryVO,
															 @Param(value = "year") String year,
															 @Param(value = "lastMonth") String lastMonth);


	/**
	 * 全客户(集团客户)明细
	 *
	 * @param page
	 * @param queryVO   查询条件
	 * @param year      当前营业年份
	 * @param lastMonth 上月月份
	 * @return
	 */
	List<RcmFullCusDetailRptVO> selectGroupCusDetailRptPage(@Param(value = "page") IPage page,
															@Param(value = "queryVO") RcmFullCusDetailQueryVO queryVO,
															@Param(value = "year") String year,
															@Param(value = "lastMonth") String lastMonth);
}
