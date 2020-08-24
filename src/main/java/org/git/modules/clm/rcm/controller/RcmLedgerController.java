package org.git.modules.clm.rcm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.common.utils.CommonUtil;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.tool.api.R;
import org.git.modules.clm.rcm.entity.RcmConfig;
import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.service.IRcmConfigService;
import org.git.modules.clm.rcm.service.IRcmConfigTotalService;
import org.git.modules.clm.rcm.service.RcmLedgerService;
import org.git.modules.clm.rcm.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Chain-Boot
 * 限额台账
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/1/3
 * @since 1.8
 */
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("git-rcm/ledger")
@Api(value = "限额台账", tags = "限额台账接口")
public class RcmLedgerController extends ChainController {

	private RcmLedgerService rcmLedgerService;
	private IRcmConfigService rcmConfigService;

	/**
	 * 查询机构限额清单
	 * caohaijie
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询机构限额清单", notes = "")
	public R<List<RcmLedgerOrgQuotaVO>> queryOrgQuotaList(String useOrgNum) {
		List<RcmLedgerOrgQuotaVO> newList = new ArrayList<>();
		RcmConfig rcmConfig = new RcmConfig();
		rcmConfig.setUseOrgNum(useOrgNum);
		List<RcmConfig> list = rcmConfigService.list(Condition.getQueryWrapper(rcmConfig));
		for (RcmConfig item : list) {
			RcmLedgerOrgQuotaVO newItem = new RcmLedgerOrgQuotaVO();
			newItem.setUseOrgNum(item.getUseOrgNum());
			newItem.setQuotaNum(item.getQuotaNum());
			newItem.setQuotaName(item.getQuotaName());
			newItem.setQuotaIndexNum(item.getQuotaIndexNum());
			newItem.setQuotaFreeAmt(item.getQuotaFreeAmt());
			newItem.setQuotaUsedAmt(item.getQuotaUsedAmt());
			newItem.setQuotaTotalSum(item.getQuotaTotalSum());
			newItem.setQuotaUsedRatio(item.getQuotaUsedRatio());
			newItem.setQuotaFreeRatio(item.getQuotaFreeRatio());
			newList.add(newItem);
		}
		return R.data(newList);
	}

	/**
	 * 查询首页限额信息
	 * caohaijie
	 */
	@GetMapping("/home")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询首页限额信息", notes = "")
	public R<RcmLedgerQuotaHomeVO> queryOrgQuotaHome(@NotBlank(message = "查询机构不能为空") String useOrgNum) {
		RcmConfig rcmConfig = new RcmConfig();
		rcmConfig.setUseOrgNum(useOrgNum);
		//CB20191226604691	单一客户授信集中度
		//CB20191226053358	最大十家单一客户授信集中度
		//CB20191226559982	最大十家集团客户授信集中度
		//CB20191226910211	单一集团客户授信集中度
		LambdaQueryWrapper<RcmConfig> wrapper = Condition.getQueryWrapper(rcmConfig).lambda()
			.in(RcmConfig::getQuotaIndexNum, "CB20191226604691", "CB20191226053358", "CB20191226559982");
		List<RcmConfig> list = rcmConfigService.list(wrapper);
		RcmLedgerQuotaHomeVO home = new RcmLedgerQuotaHomeVO();
		for (RcmConfig item : list) {
			home.setUseOrgNum(item.getUseOrgNum());
			if ("CB20191226604691".equals(item.getQuotaIndexNum())) {
				home.setQuotaNumA(item.getQuotaNum());
				home.setQuotaNameA(item.getQuotaName());
				home.setQuotaIndexNumA(item.getQuotaIndexNum());
				home.setQuotaUsedRatioA(CommonUtil.nullToZero(item.getQuotaUsedRatio()));
				home.setQuotaFreeRatioA(CommonUtil.nullToZero(item.getQuotaFreeRatio()));
				home.setQuotaFreeAmtA(CommonUtil.nullToZero(item.getQuotaFreeAmt()));
				home.setQuotaUsedAmtA(CommonUtil.nullToZero(item.getQuotaUsedAmt()));
				home.setQuotaTotalSumA(CommonUtil.nullToZero(item.getQuotaTotalSum()));
			} else if ("CB20191226053358".equals(item.getQuotaIndexNum())) {
				home.setQuotaNumB(item.getQuotaNum());
				home.setQuotaNameB(item.getQuotaName());
				home.setQuotaIndexNumB(item.getQuotaIndexNum());
				home.setQuotaUsedRatioB(CommonUtil.nullToZero(item.getQuotaUsedRatio()));
				home.setQuotaFreeRatioB(CommonUtil.nullToZero(item.getQuotaFreeRatio()));
				home.setQuotaFreeAmtB(CommonUtil.nullToZero(item.getQuotaFreeAmt()));
				home.setQuotaUsedAmtB(CommonUtil.nullToZero(item.getQuotaUsedAmt()));
				home.setQuotaTotalSumB(CommonUtil.nullToZero(item.getQuotaTotalSum()));
			} else if ("CB20191226559982".equals(item.getQuotaIndexNum())) {
				home.setQuotaNumC(item.getQuotaNum());
				home.setQuotaNameC(item.getQuotaName());
				home.setQuotaIndexNumC(item.getQuotaIndexNum());
				home.setQuotaUsedRatioC(CommonUtil.nullToZero(item.getQuotaUsedRatio()));
				home.setQuotaFreeRatioC(CommonUtil.nullToZero(item.getQuotaFreeRatio()));
				home.setQuotaFreeAmtC(CommonUtil.nullToZero(item.getQuotaFreeAmt()));
				home.setQuotaUsedAmtC(CommonUtil.nullToZero(item.getQuotaUsedAmt()));
				home.setQuotaTotalSumC(CommonUtil.nullToZero(item.getQuotaTotalSum()));
			}

		}
		return R.data(home);
	}


	/**
	 * 查询当前月限额统计详情
	 * caohaijie
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询当前月限额统计详情", notes = "")
	public R<RcmQuotaManagerVO> queryBase(String quotaNum) {
		return R.data(rcmLedgerService.queryQuotaDetail(quotaNum));
	}

	/**
	 * 查询限额和预警次数最近12月的趋势变化
	 * caohaijie
	 */
	@GetMapping("/trend")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询限额和预警次数最近12月的趋势变化", notes = "")
	public R<List<RcmLedgerQuotaVO>> queryHistoryTotal(String quotaNum) {
		return R.data(rcmLedgerService.queryHistoryTotal(quotaNum));
	}

	/**
	 * 查询限额分析和累计触发次数
	 * caohaijie
	 */
	@GetMapping("/analyze")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询限额分析和累计触发次数", notes = "")
	public R<RcmLedgerTotalVO> queryAnalyze(String quotaNum) {
		List<RcmConfigTotalVO> list = rcmLedgerService.queryCurrentTotal(quotaNum);
		if (list != null && list.size() > 0) {
			RcmConfigTotalVO vo = list.get(0);
			RcmLedgerTotalVO ledgerTotalVO = new RcmLedgerTotalVO();
			ledgerTotalVO.setQuotaNum(vo.getQuotaNum());
			ledgerTotalVO.setLastQuotaUsedAmt(vo.getLastQuotaUsedAmt());
			ledgerTotalVO.setLastYearAmt(vo.getLastYearAmt());
			ledgerTotalVO.setLastYearRatio(vo.getLastYearRatio());
			ledgerTotalVO.setMonthToMonthAmt(vo.getMonthToMonthAmt());
			ledgerTotalVO.setMonthToMonthRatio(vo.getMonthToMonthRatio());
			ledgerTotalVO.setQuotaUsedAmt(vo.getQuotaUsedAmt());
			ledgerTotalVO.setYearToYearAmt(vo.getYearToYearAmt());
			ledgerTotalVO.setYearToYearRatio(vo.getYearToYearRatio());

			//累计预警次数
			ledgerTotalVO.setHisFrequency(vo.getHisFrequency());
			ledgerTotalVO.setHisFrequencyA(vo.getHisFrequencyA());
			ledgerTotalVO.setHisFrequencyB(vo.getHisFrequencyB());
			ledgerTotalVO.setHisFrequencyC(vo.getHisFrequencyC());

			//本年预警次数
			ledgerTotalVO.setHisFrequencyA2(vo.getHisFrequencyA2());
			ledgerTotalVO.setHisFrequencyB2(vo.getHisFrequencyB2());
			ledgerTotalVO.setHisFrequencyC2(vo.getHisFrequencyC2());
			ledgerTotalVO.setHisFrequencyYear(vo.getHisFrequencyYear());

			//本月预警次数
			ledgerTotalVO.setHisFrequencyA3(vo.getHisFrequencyA3());
			ledgerTotalVO.setHisFrequencyB3(vo.getHisFrequencyB3());
			ledgerTotalVO.setHisFrequencyC3(vo.getHisFrequencyC3());
			ledgerTotalVO.setHisFrequencyMonth(vo.getHisFrequencyMonth());

			return R.data(ledgerTotalVO);
		}
		return R.data(null);
	}
}
