package org.git.modules.clm.chart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.modules.clm.chart.service.ICustomerChartService;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.system.entity.Dept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;

import java.util.List;

/**
 * 客户额度视图
 *
 * @author chenchuan
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_CHART_NAME + "/customer-chart")
@Api(value = "客户额度视图", tags = "客户额度视图")
public class CustomerChartController extends ChainController {

	ICustomerChartService iCustomerChartService;

	/**
	 * 根据客户号获取客户下有额度的机构
	 */
	@GetMapping("/getOrgByCust")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据客户号获取客户下有额度的机构", notes = "传入客户编号customerNum")
	public R<List<Dept>> getOrgByCust(@ApiParam(value = "客户编号", required = true) String customerNum) {
		System.out.println("客户编号：" + customerNum);
		List<Dept> depts = iCustomerChartService.listOrgByCust(customerNum);
		return R.data(depts);
	}

	/**
	 * 分页查询客户列表（汇总额度）
	 */
	@GetMapping("/listCrdSumPage")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页查询客户列表（汇总额度）", notes = "传入CrdQueryVO")
	public R<IPage<CrdQueryVO>> listCrdSumPage(CrdQueryVO crdQueryVO, Query query) {
		IPage<CrdQueryVO> pages = iCustomerChartService.listCrdSumPage(Condition.getPage(query), crdQueryVO);
		return R.data(pages);
	}

	/**
	 * 查询额度明细列表
	 */
	@GetMapping("/listCrdDetail")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询客户额度明细列表", notes = "单一客户视图左上角echar、左下角列表、右下角echar，传入客户编号（customerNum）和机构号（orgNum）")
	public R<List<CrdQueryVO>> listCrdDetail(
		@ApiParam(value = "客户编号", required = true) String customerNum,
		@ApiParam(value = "经办机构号", required = true) String orgNum) {
		CrdQueryVO crdQueryVO = new CrdQueryVO();
		crdQueryVO.setCustomerNum(customerNum);
		crdQueryVO.setOrgNum(orgNum);
		List<CrdQueryVO> list = iCustomerChartService.listCrdDetail(crdQueryVO);
		return R.data(list);
	}


	/**
	 * 查询担保额度列表
	 */
	@GetMapping("/listGuaranteeCrd")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询担保额度列表，担保客户视图左上echar、右上角表格", notes = "传入客户编号（customerNum）和机构号（orgNum）")
	public R<List<CrdQueryVO>> listGuaranteeCrd(
		@ApiParam(value = "客户编号", required = true) String customerNum,
		@ApiParam(value = "结构号", required = true) String orgNum) {
		CrdQueryVO crdQueryVO = new CrdQueryVO();
		crdQueryVO.setCustomerNum(customerNum);
		crdQueryVO.setOrgNum(orgNum);
		List<CrdQueryVO> list = iCustomerChartService.listGuaranteeCrd(crdQueryVO);
		return R.data(list);
	}

	/**
	 * 查询对外担保列表（分页）
	 */
	@GetMapping("/listGrtPage")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询对外担保列表（分页）,担保客户视图下方列表", notes = "传入客户编号（customerNum）和机构号（orgNum）")
	public R<IPage<GrtVO>> listGrtPage(
		@ApiParam(value = "客户编号", required = true) String customerNum,
		@ApiParam(value = "经办机构", required = true) String orgNum,
		Query query) {
		IPage<GrtVO> pages = iCustomerChartService.listGrtPage(Condition.getPage(query), customerNum, orgNum);
		return R.data(pages);
	}

	/**
	 * 按客户及机构号查询额度汇总信息
	 * 1、客户号必输
	 * 2、机构号非必输，输入时如果为总行级机构，不带条件查全部汇总，否则仅按机构号查询机构及下属机构的数据汇总
	 * （考虑到数据量较大时，按照递归算法对系统消耗过大，不在此处使用递归算法）
	 * （如果有精简后的统计专用机构表，可以使用递归算法实现查询所有下级机构的数据汇总）
	 */
	@GetMapping("/sumCsmCrd")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查询客户额度汇总信息", notes = "单一客户额度视图右上角table表格，传入客户编号（customerNum）和机构号（orgNum）")
	public R<TbCrdStatisCsm> sumCsmCrd(
		@ApiParam(value = "客户编号", required = true) String customerNum,
		@ApiParam(value = "经办机构", required = false) String orgNum) {
		TbCrdStatisCsm tbCrdStatisCsm = new TbCrdStatisCsm();
		tbCrdStatisCsm.setCustomerNum(customerNum);
		tbCrdStatisCsm.setOrgNum(orgNum);
		tbCrdStatisCsm = iCustomerChartService.sumCsmCrdInfo(tbCrdStatisCsm);
		return R.data(tbCrdStatisCsm);
	}

	/**
	 * 分页查询客户列表（汇总额度）
	 */

	@GetMapping(value = "/getCrdListSumPage",produces = "application/json;charset=utf-8")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "分页查询客户列表（汇总额度）", notes = "传入CrdQueryVO")
	public R<IPage<CrdQueryVO>> getCrdListSumPage(CrdQueryVO crdQueryVO, Query query,@ApiParam(required = true) String customerType) {
		IPage<CrdQueryVO> pages = iCustomerChartService.getCrdListSumPage(Condition.getPage(query), crdQueryVO,customerType);
		return R.data(pages);
	}

	/**
	 * 分页查询同业客户列表（汇总额度）
	 */
	@GetMapping(value = "/queryLedgerCrdSumPage", produces = "application/json;charset=utf-8")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "分页查询同业客户列表（汇总额度）", notes = "传入CrdQueryVO")
	public R<IPage<CrdQueryVO>> queryLedgerCrdSumPage(CrdQueryVO crdQueryVO, Query query,@ApiParam(required = true) String customerType) {
		IPage<CrdQueryVO> pages = iCustomerChartService.queryLedgerCrdSumPage(Condition.getPage(query), crdQueryVO,customerType);
		return R.data(pages);
	}
}
