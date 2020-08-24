package org.git.modules.clm.rcm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.dto.RcmCreditTenCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmCreditTenGroupCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmLoanTenCusRptDTO;
import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.entity.RcmIndustryQuotaBaseRpt;
import org.git.modules.clm.rcm.mapper.RcmAnalyzeQueryRptMapper;
import org.git.modules.clm.rcm.vo.*;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 限额分析报表查询
 *
 * @author zhouweijie
 */
@AllArgsConstructor
@Slf4j
@Service
public class RcmAnalyzeQueryRptService {

	private RcmAnalyzeQueryRptMapper rcmAnalyzeQueryRptMapper;
	private ICommonService commonService;
	private IDeptService deptService;
	private IRcmConfigTotalService configTotalService;

	/**
	 * 查询客户授信集中度
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmCusQuotaRptVO> selectRcmCusQuotaRptPage(IPage<RcmCusQuotaRptVO> page, RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectRcmCusQuotaRptPage(page, queryVO));
	}

	/**
	 * 查询客户授信集中度
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public List<RcmCusQuotaRptVO> selectRcmCusQuotaRptList(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		return rcmAnalyzeQueryRptMapper.selectRcmCusQuotaRptPage(queryVO);
	}

	/**
	 * 查询最大十家客户(贷款)集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public RcmLoanTenCusRptDTO selectRcmLoanTenCusRptPage(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		RcmLoanTenCusRptDTO loanTenCusRptDTO = new RcmLoanTenCusRptDTO();
		loanTenCusRptDTO.setLoanTenCusRptVOList(rcmAnalyzeQueryRptMapper.selectRcmLoanTenCusRptPage(queryVO));
		//限额要查询历史数据
		RcmConfigTotal configTotal = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), RcmConstant.LOAN_TEN_CUS_QUOTA_NUM);
		RcmQuotaInfoVO rcmQuotaInfoVO = new RcmQuotaInfoVO();
		if (configTotal != null) {
			BeanUtils.copyProperties(configTotal, rcmQuotaInfoVO);
		}
		rcmQuotaInfoVO.setUnitFlag(queryVO.getAmtUnit());
		loanTenCusRptDTO.setQuotaInfoVO(rcmQuotaInfoVO);
		//rcmAnalyzeQueryRptMapper.selectQuotaInfo(queryVO, RcmConstant.LOAN_TEN_CUS_QUOTA_NUM)
		return loanTenCusRptDTO;
	}

	/**
	 * 查询最大十家客户(授信)集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public RcmCreditTenCusRptDTO selectRcmCreditTenCusRptPage(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		RcmCreditTenCusRptDTO creditTenCusRptDTO = new RcmCreditTenCusRptDTO();
		creditTenCusRptDTO.setCreditTenCusRptVOList(rcmAnalyzeQueryRptMapper.selectRcmCreditTenCusRptPage(queryVO));
		//限额要查询历史数据
		RcmConfigTotal configTotal = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), RcmConstant.CREDIT_TEN_CUS_QUOTA_NUM);
		RcmQuotaInfoVO rcmQuotaInfoVO = new RcmQuotaInfoVO();
		if (configTotal != null) {
			BeanUtils.copyProperties(configTotal, rcmQuotaInfoVO);
		}
		rcmQuotaInfoVO.setUnitFlag(queryVO.getAmtUnit());
		creditTenCusRptDTO.setQuotaInfoVO(rcmQuotaInfoVO);
//		creditTenCusRptDTO.setQuotaInfoVO(rcmAnalyzeQueryRptMapper.selectQuotaInfo(queryVO, RcmConstant.CREDIT_TEN_CUS_QUOTA_NUM));
		return creditTenCusRptDTO;
	}

	/**
	 * 查询最大十家集团客户授信集中度明细表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public RcmCreditTenGroupCusRptDTO selectRcmCreditTenGroupCusRptPage(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		RcmCreditTenGroupCusRptDTO creditTenGroupCusRptDTO = new RcmCreditTenGroupCusRptDTO();
		creditTenGroupCusRptDTO.setCreditTenGroupCusRptVOList(rcmAnalyzeQueryRptMapper.selectRcmCreditTenGroupCusRptPage(queryVO));
		//限额要查询历史数据
		RcmConfigTotal configTotal = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), RcmConstant.CREDIT_TEN_GROUP_CUS_QUOTA_NUM);
		RcmQuotaInfoVO rcmQuotaInfoVO = new RcmQuotaInfoVO();
		if (configTotal != null) {
			BeanUtils.copyProperties(configTotal, rcmQuotaInfoVO);
		}
		rcmQuotaInfoVO.setUnitFlag(queryVO.getAmtUnit());
		creditTenGroupCusRptDTO.setQuotaInfoVO(rcmQuotaInfoVO);
//		creditTenGroupCusRptDTO.setQuotaInfoVO(rcmAnalyzeQueryRptMapper.selectQuotaInfo(queryVO, RcmConstant.CREDIT_TEN_GROUP_CUS_QUOTA_NUM));
		return creditTenGroupCusRptDTO;
	}

	/**
	 * 关联客户授信集中度简表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public List<RcmRelationQuotaBaseRptVO> selectRelationQuotaBaseRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		//关联客户授信集中度
		// 全部关联度指标(CB111+CB222)
		List<String> quotaIndexNums = new ArrayList<>();
		quotaIndexNums.add("CB111");// CB111（待定）:单一关联客户集中度
		quotaIndexNums.add("CB222");// CB222（待定）:单一关联方所在集团客户集中度
		queryVO.setQuotaIndexNums(quotaIndexNums);
		//通过QueryWrapper查询
//		List<RcmConfigTotal> list = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), "CB111", "CB222");
		//通过SQL查询（带单位换算和机构权限）
		List<RcmCusQuotaRptVO> list = rcmAnalyzeQueryRptMapper.selectRcmCusQuotaRptPage(queryVO);

		List<RcmRelationQuotaBaseRptVO> rptList = new ArrayList<>();
		for (RcmCusQuotaRptVO total : list) {
			RcmRelationQuotaBaseRptVO rpt = new RcmRelationQuotaBaseRptVO();
			BeanUtils.copyProperties(total, rpt);
			if ("CB111".equals(rpt.getQuotaIndexNum())) {
				rpt.setCustomerName("");//取最大一家关联方 TODO
				rpt.setCustomerNum("");
			} else if ("CB222".equals(rpt.getQuotaIndexNum())) {
				rpt.setCustomerName("");//取最大一家关联方所在集团 TODO
				rpt.setCustomerNum("");
			}
			rptList.add(rpt);
		}
		return rptList;
	}

	/**
	 * 查询行业授信集中度简表
	 *
	 * @param queryVO 查询条件
	 * @return
	 */
	public RcmIndustryQuotaBaseRptVO selectRcmIndustryQuotaBaseRpt(RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		//限额要查询历史数据
		//对公房地产授信(CB20191226013525:贷款 + CB20191226386204:债券 + CB20191226750353:结构化融资)
		RcmQuotaInfoVO root11 = new RcmQuotaInfoVO();
		root11.setQuotaNum("11");
		root11.setQuotaName("对公房地产业授信");
		root11.setTotalMonth(queryVO.getTotalMonth());
		root11.setTotalYear(queryVO.getTotalYear());
		List<RcmQuotaInfoVO> root11Cildren = new ArrayList<>();//root11子节点
		List<RcmConfigTotal> root11List = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), "CB20191226013525", "CB20191226750353", "CB20191226386204");
		for (RcmConfigTotal total : root11List) {
			//累加子节点
			root11.setQuotaUsedAmt(root11.getQuotaUsedAmt().add(total.getQuotaUsedAmt()));
			root11.setQuotaUsedRatio(root11.getQuotaUsedRatio().add(total.getQuotaUsedRatio()));
			root11.setObserveValue(root11.getObserveValue().add(total.getObserveValue()));
			root11.setWarnValue(root11.getWarnValue().add(total.getWarnValue()));
			root11.setControlValue(root11.getControlValue().add(total.getControlValue()));
			//添加子节点
			RcmQuotaInfoVO quotaInfoVO = new RcmQuotaInfoVO();
			BeanUtils.copyProperties(total, quotaInfoVO);
			if ("CB20191226013525".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("贷款");
			} else if ("CB20191226386204".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("债券");
			} else if ("CB20191226750353".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("结构化融资");
			}
			root11Cildren.add(quotaInfoVO);
		}
		root11.setChildren(root11Cildren);

		//个人住房贷款(CB20191226707411:个人住房按揭 + CB20191226873823:个人商用房按揭)
		RcmQuotaInfoVO root12 = new RcmQuotaInfoVO();
		root12.setQuotaNum("12");
		root12.setQuotaName("个人住房/商用房按揭贷款");
		root12.setTotalMonth(queryVO.getTotalMonth());
		root12.setTotalYear(queryVO.getTotalYear());
		List<RcmQuotaInfoVO> root12Cildren = new ArrayList<>();//root12子节点
		List<RcmConfigTotal> root12List = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), "CB20191226707411", "CB20191226873823");
		for (RcmConfigTotal total : root12List) {
			//累加子节点
			root12.setQuotaUsedAmt(root12.getQuotaUsedAmt().add(total.getQuotaUsedAmt()));
			root12.setQuotaUsedRatio(root12.getQuotaUsedRatio().add(total.getQuotaUsedRatio()));
			root12.setObserveValue(root12.getObserveValue().add(total.getObserveValue()));
			root12.setWarnValue(root12.getWarnValue().add(total.getWarnValue()));
			root12.setControlValue(root12.getControlValue().add(total.getControlValue()));
			//添加子节点
			RcmQuotaInfoVO quotaInfoVO = new RcmQuotaInfoVO();
			BeanUtils.copyProperties(total, quotaInfoVO);
			if ("CB20191226707411".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("个人住房按揭");
			} else if ("CB20191226873823".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("个人商用房按揭");
			}
			root12Cildren.add(quotaInfoVO);
		}
		root12.setChildren(root12Cildren);

		//房地产行业授信集中度(root11对公房地产授信 + root12个人住房贷款)
		RcmQuotaInfoVO root1 = new RcmQuotaInfoVO();
		root1.setQuotaNum("1");
		root1.setQuotaName("房地产行业授信余额");
		root1.setTotalMonth(queryVO.getTotalMonth());
		root1.setTotalYear(queryVO.getTotalYear());
		//累加子节点 root11 + root12
		root1.setQuotaUsedAmt(root11.getQuotaUsedAmt().add(root12.getQuotaUsedAmt()));
		root1.setQuotaUsedRatio(root11.getQuotaUsedRatio().add(root12.getQuotaUsedRatio()));
		root1.setObserveValue(root11.getObserveValue().add(root12.getObserveValue()));
		root1.setWarnValue(root11.getWarnValue().add(root12.getWarnValue()));
		root1.setControlValue(root11.getControlValue().add(root12.getControlValue()));
		List<RcmQuotaInfoVO> root1Cildren = new ArrayList<>();//root1子节点
		root1Cildren.add(root11);
		root1Cildren.add(root12);
		root1.setChildren(root1Cildren);

		//产能过剩行业贷款(CB20191226049888:产能过剩行业贷款,CB000(待定):产能过剩行业非标)
		RcmQuotaInfoVO root2 = new RcmQuotaInfoVO();
		root2.setQuotaNum("2");
		root2.setQuotaName("产能过剩行业授信余额");
		root2.setTotalMonth(queryVO.getTotalMonth());
		root2.setTotalYear(queryVO.getTotalYear());
		List<RcmQuotaInfoVO> root2Cildren = new ArrayList<>();//root2子节点
		List<RcmConfigTotal> root2List = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), "CB20191226049888", "CB000");
		for (RcmConfigTotal total : root2List) {
			//累加子节点
			root2.setQuotaUsedAmt(root2.getQuotaUsedAmt().add(total.getQuotaUsedAmt()));
			root2.setQuotaUsedRatio(root2.getQuotaUsedRatio().add(total.getQuotaUsedRatio()));
			root2.setObserveValue(root2.getObserveValue().add(total.getObserveValue()));
			root2.setWarnValue(root2.getWarnValue().add(total.getWarnValue()));
			root2.setControlValue(root2.getControlValue().add(total.getControlValue()));
			//添加子节点
			RcmQuotaInfoVO quotaInfoVO = new RcmQuotaInfoVO();
			BeanUtils.copyProperties(total, quotaInfoVO);
			if ("CB20191226049888".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("产能过剩行业贷款");
			} else if ("CB000".equals(quotaInfoVO.getQuotaIndexNum())) {
				quotaInfoVO.setQuotaName("产能过剩行业非标");
			}
			root2Cildren.add(quotaInfoVO);
		}
		root2.setChildren(root2Cildren);

//		List<RcmConfigTotal> root = configTotalService.selectRcmConfigTotal(queryVO.getUserOrgNum(), queryVO.getTotalYear(), queryVO.getTotalMonth(), "CB20191226013525", "CB20191226750353", "CB20191226386204", "CB20191226707411", "CB20191226873823");
//		return page.setRecords(rcmAnalyzeQueryRptMapper.selectRcmIndustryQuotaBaseRptPage(page, queryVO));

		RcmIndustryQuotaBaseRptVO rpt = new RcmIndustryQuotaBaseRptVO();
		rpt.setRoot1(root1);
		rpt.setRoot2(root2);

		return rpt;
	}

	/**
	 * 查询行业授信集中度明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmIndustryQuotaDetailRptVO> selectRcmIndustryQuotaDetailRptPage(IPage<RcmIndustryQuotaDetailRptVO> page, RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectRcmIndustryQuotaDetailRptPage(page, queryVO));
	}


	/**
	 * 查询全客户（单一客户）明细
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmFullCusDetailRptVO> selectSingleCusDetailRptPage(IPage<RcmFullCusDetailRptVO> page, RcmFullCusDetailQueryVO queryVO) {
		/*查询条件视图对象赋值*/
		setQueryVO(queryVO);
		/*营业年份*/
		String year = CommonUtil.getWorkYear();
		/*上月营业月份*/
		String lastMonth = String.valueOf(Integer.parseInt(CommonUtil.getWorkMonth()) - 1);
		if ("0".equals(lastMonth)) {
			/*日期查询条件为本年一月份时，上月为去年十二月份*/
			lastMonth = "12";
			year = String.valueOf(Integer.parseInt(year) - 1);
		}
		if (lastMonth.length() == 1) {
			lastMonth = "0" + lastMonth;
		}
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectSingleCusDetailRptPage(page, queryVO, year, lastMonth));
	}


	/**
	 * 查询全客户（集团客户）明细
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmFullCusDetailRptVO> selectGroupCusDetailRptPage(IPage<RcmFullCusDetailRptVO> page, RcmFullCusDetailQueryVO queryVO) {
		/*查询条件视图对象赋值*/
		setQueryVO(queryVO);
		/*营业年份*/
		String year = CommonUtil.getWorkYear();
		/*上月营业月份*/
		String lastMonth = String.valueOf(Integer.parseInt(CommonUtil.getWorkMonth()) - 1);
		if ("0".equals(lastMonth)) {
			/*日期查询条件为本年一月份时，上月为去年十二月份*/
			lastMonth = "12";
			year = String.valueOf(Integer.parseInt(year) - 1);
		}
		if (lastMonth.length() == 1) {
			lastMonth = "0" + lastMonth;
		}
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectGroupCusDetailRptPage(page, queryVO, year, lastMonth));
	}

	/**
	 * 限额分析-全客户明细-查询条件对象赋值
	 *
	 * @param queryVO
	 */
	private void setQueryVO(RcmFullCusDetailQueryVO queryVO) {
		/*获取登录用户机构信息*/
		Dept dept = deptService.getById(CommonUtil.getCurrentOrgId());
		/*登录用户机构类型赋值*/
		queryVO.setUserOrgType(dept.getOrgType());
		/*登录用户机构号赋值*/
		queryVO.setUserOrgNum(CommonUtil.getCurrentOrgId());
		if (queryVO.getOrgNum() != null && !"".equals(queryVO.getOrgNum())) {
			/*若页面传入了机构,获取机构类型及法人机构*/
			dept = deptService.getById(queryVO.getOrgNum());
			if (dept != null) {
				/*机构类型赋值*/
				queryVO.setOrgType(dept.getOrgType());
				/*机构号赋值*/
				queryVO.setOrgNum(queryVO.getOrgNum());
			}
		}
		if (RcmConstant.AMT_UNIT_TEN_THOUSAND.equals(queryVO.getAmtUnit())) {
			queryVO.setAmtUnit("10000");
		} else if (RcmConstant.AMT_UNIT_ONE_HUNDRED_MILLION.equals(queryVO.getAmtUnit())) {
			queryVO.setAmtUnit("100000000");
		}
	}

	/**
	 * 查询风险暴露情况简表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public List<RcmCusQuotaRptVO> selectRcmRiskExposureRptPage(IPage<RcmRiskExposureRptVO> page, RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		List<String> quotaIndexNums = new ArrayList<>();
		quotaIndexNums.add("CB555");// （待定）:最大非同业单一客户贷款余额
		quotaIndexNums.add("CB666");// （待定）:最大非同业单一客户风险暴露
		quotaIndexNums.add("CB333");// （待定）:最大非同业关联客户风险暴露
		quotaIndexNums.add("CB444");// （待定）:最大单一同业客户风险暴露
		queryVO.setQuotaIndexNums(quotaIndexNums);
		//通过SQL查询（带单位换算和机构权限）
		return rcmAnalyzeQueryRptMapper.selectRcmCusQuotaRptPage(queryVO);
	}

	/**
	 * 查询同业客户风险暴露明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public List<RcmBankRiskExposureRptVO> selectRcmBankRiskExposureRptPage(IPage<RcmRiskExposureRptVO> page, RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		return rcmAnalyzeQueryRptMapper.selectRcmBankRiskExposureRptPage(page, queryVO);
	}

	/**
	 * 查询匿名客户风险暴露明细表
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public List<RcmAnonymityRiskExposureRptVO> selectAnonymityRiskExposureRptPage(IPage<RcmRiskExposureRptVO> page, RcmQuotaAnalysisRptQueryVO queryVO) {
		commonService.setQueryRequirement(queryVO);
		page.setSize(20);//每页大小
		return rcmAnalyzeQueryRptMapper.selectAnonymityRiskExposureRptPage(page, queryVO);
	}

	/**
	 * 查询风险暴露超过1亿元（含）的单一客户
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmRiskOverOneHundredMillionRptVO> selectOverOneHundredMillion(IPage<RcmRiskOverOneHundredMillionRptVO> page, RcmFullCusDetailQueryVO queryVO) {
		setQueryVO(queryVO);
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectOverOneHundredMillion(page, queryVO));
	}

	/**
	 * 查询风险暴露超过一级资本净额2.5%的集团客户
	 *
	 * @param page
	 * @param queryVO 查询条件
	 * @return
	 */
	public IPage<RcmRiskOverTwoPointFivePercentRptVO> selectOverTwoPointFivePercent(IPage<RcmRiskOverTwoPointFivePercentRptVO> page, RcmFullCusDetailQueryVO queryVO) {
		/*查询条件视图对象赋值*/
		setQueryVO(queryVO);
		return page.setRecords(rcmAnalyzeQueryRptMapper.selectOverTwoPointFivePercent(page, queryVO));
	}

//	/**
//	 * 查询业务信息
//	 *
//	 * @param page
//	 * @param customerNum 客户编号
//	 * @return
//	 */
//	public IPage<RcmRiskBusinessInfoVO> selectBusinessInfo(IPage<RcmRiskBusinessInfoVO> page, String customerNum) {
//		/*获取登录用户机构信息*/
//		Dept dept = deptService.getById(CommonUtil.getCurrentOrgId());
//		return page.setRecords(rcmAnalyzeQueryRptMapper.selectBusinessInfo(page, customerNum, dept.getOrgType(), CommonUtil.getCurrentOrgId()));
//	}
//
//	/**
//	 * 查询押品信息
//	 *
//	 * @param page
//	 * @param businessNum 业务编号（此处暂为借据编号）
//	 * @return
//	 */
//	public IPage<TbCrdSuretyVO> selectSuretyInfo(IPage<TbCrdSuretyVO> page, String businessNum) {
//		return page.setRecords(rcmAnalyzeQueryRptMapper.selectSuretyInfo(page, businessNum));
//	}

}
