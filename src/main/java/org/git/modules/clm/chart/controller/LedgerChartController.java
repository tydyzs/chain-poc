package org.git.modules.clm.chart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.core.mp.support.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.tool.api.R;
import org.git.modules.clm.chart.service.ILedgerChartService;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 同业额度视图
 *
 * @author chenchuan
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-chart/ledger-chart")
@Api(value = "同业额度视图", tags = "同业额度视图")
public class LedgerChartController extends ChainController {

	ILedgerChartService iledgerChartService;
	/**
	 * 查询同业额度明细列表
	 */
	@GetMapping("/ledgerDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询客户明细列表", notes = "传入客户编号（customerNum）和机构号（orgNum）和customerType")
	public R<List<CrdQueryVO>> ledgerDetail(
		@ApiParam(value = "客户编号", required = true) String customerNum,
		@ApiParam(value = "经办机构号", required = true) String orgNum,
		@ApiParam(value = "客户类型(CD000033)", required = true) String customerType) {
		CrdQueryVO crdQueryVO = new CrdQueryVO();
		crdQueryVO.setCustomerNum(customerNum);
		crdQueryVO.setOrgNum(orgNum);
		crdQueryVO.setCustomerType(customerType);
		List<CrdQueryVO> list = iledgerChartService.ledgerDetail(crdQueryVO);
		return R.data(list);
	}


	/**
	 * 按照机构类型为省联社查询额度明细
	 */
	@GetMapping("/queryLedgerDeatilByOrgType")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "按照机构类型为省联社查询额度明细", notes = "传入customerNum")
	public R<List<CrdQueryVO>> queryLedgerDeatilByOrgType(@ApiParam(value = "客户编号", required = true) String customerNum) {
		List<CrdQueryVO> List = iledgerChartService.queryLedgerDeatilByOrgType(customerNum);
		return R.data(List);
	}

	/**
	 * 按照机构类型为省联社查询额度明细
	 */
	@GetMapping("/queryLedgerDeatilByOrgTypeThree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "按照机构类型为成员行查询额度明细", notes = "传入customerNum,crdGrantOrgNum")
	public R<List<CrdQueryVO>> queryLedgerDeatilByOrgTypeThree(@ApiParam(value = "客户编号", required = true) String customerNum,
															   @ApiParam(value = "经办机构", required = true) String crdGrantOrgNum
															   ) {
		List<CrdQueryVO> List = iledgerChartService.queryLedgerDeatilByOrgTypeThree(crdGrantOrgNum,customerNum);
		return R.data(List);
	}
}
