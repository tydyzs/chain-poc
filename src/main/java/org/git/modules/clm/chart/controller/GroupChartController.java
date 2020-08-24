package org.git.modules.clm.chart.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.chart.service.IGroupChartService;
import org.git.modules.clm.chart.vo.CrdGroupTreeVO;
import org.git.modules.clm.chart.vo.CrdGroupVO;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;
import org.git.modules.clm.credit.service.ITbCrdStatisCsmService;
import org.git.modules.clm.credit.vo.TbCrdStatisCsmVO;
import org.git.modules.system.vo.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.List;


/**
 * 查询集团客户视图
 */
@RestController
@AllArgsConstructor
@RequestMapping( "git-chart/group-chart")
@Api(value = "集团360视图", tags = "集团360视图")
public class GroupChartController extends ChainController {

	private IGroupChartService iGroupChartService;

	private ITbCrdStatisCsmService iTbCrdStatisCsmService;

	/**
	 * 集团客户360视图 成员结构
	 */
	@GetMapping("/findGroupMemberDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询集团客户视图成员结构", notes = "传入customerNum,orgNum")
	public R<TbCrdStatisCsmVO> findGroupMemberDetail(@ApiParam(value = "客户编号", required = true) String customerNum,
													 @ApiParam(value = "经办机构", required = true) String orgNum
	) {
		TbCrdStatisCsmVO detail = iTbCrdStatisCsmService.findGroupMemberDetail(orgNum, customerNum);
		return R.data(detail);
	}
	/**
	 * 按照机构类型为省联社查询额度明细 .
	 */
	@GetMapping("/queryGroupMemberList")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询集团客户视图组织结构", notes = "传入customerNum")
	public R<List<CrdGroupVO>> queryGroupMemberList(@ApiParam(value = "成员客户编号", required = true) String memberCustomerNum,
													@ApiParam(value = "经办机构", required = true) String orgNum) {
		List<CrdGroupVO> List = iGroupChartService.queryGroupMemberList(orgNum,memberCustomerNum);
		return R.data(List);
	}

	/**
	 * 集团组织树形结构
	 */
	@GetMapping("/queryGroupMemberByCsNum")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "集团组织树形结构", notes = "集团组织树形结构")
	public R<List<CrdGroupTreeVO>> queryGroupMemberByCsNum(@ApiParam(value = "成员编号", required = true) String customerNum) {
		List<CrdGroupTreeVO> tree = iGroupChartService.queryGroupMemberByCsNum(customerNum);
		return R.data(tree);
	}
}
