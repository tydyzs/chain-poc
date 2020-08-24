package org.git.modules.clm.chart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.common.utils.CommonUtil;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.modules.clm.chart.service.ICustomerChartService;
import org.git.modules.clm.chart.service.IOrgChartService;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.CrdStatisAreaVO;
import org.git.modules.clm.chart.vo.CrdStatisVO;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.clm.credit.service.ITbCrdStatisOrgService;
import org.git.modules.system.entity.Dept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 客户额度视图
 *
 * @author chenchuan
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_CHART_NAME + "/org-chart")
@Api(value = "机构额度视图", tags = "机构额度视图")
public class OrgChartController extends ChainController {

	IOrgChartService iOrgChartService;

	@GetMapping("/getStatisOrg")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取该机构的基础统计数据", notes = "传入机构号orgNum")
	public R<TbCrdStatisOrg> getStatisOrg(
		@ApiParam(value = "机构号", required = false) String orgNum) {
		System.out.println("机构号：" + orgNum);
		TbCrdStatisOrg tbCrdStatisOrg = iOrgChartService.getStatisOrg(orgNum).get(0);
		return R.data(tbCrdStatisOrg);
	}

	@GetMapping("/listStatis")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取该机构的统计数据", notes = "传入机构号orgNum和维度dimension")
	public R<List<CrdStatisVO>> listStatis(
		@NotBlank(message = "机构号不能为空") @ApiParam(value = "机构号", required = true) String orgNum,
		@NotBlank(message = "维度不能为空") @ApiParam(value = "维度", required = true) String dimension) {
		List<CrdStatisVO> list = new ArrayList<CrdStatisVO>();
		if ("1".equals(dimension)) {//行业
			list = iOrgChartService.listStatisIndustry(orgNum);
		} else if ("2".equals(dimension)) {//额度品种
			list = iOrgChartService.listStatisCrdpt(orgNum);
		} else if ("3".equals(dimension)) {//客户类型
			list = iOrgChartService.listStatisCustype(orgNum);
		} else if ("4".equals(dimension)) {//担保方式
			list = iOrgChartService.listStatisGtype(orgNum);
		} else if ("5".equals(dimension)) {//企业规模
			list = iOrgChartService.listStatisUscale(orgNum);
		} else if ("6".equals(dimension)) {//业务品种
			list = iOrgChartService.listStatisProduct(orgNum);
		} else {
			return R.fail("无效的维度类型：" + dimension);
		}
		return R.data(list);
	}

	@PostMapping("/listStatisArea")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "按地区获取全行的统计数据", notes = "按地区获取全行的统计数据，无参数")
	public R<List<CrdStatisAreaVO>> listStatisArea() {
		List<CrdStatisAreaVO> list = iOrgChartService.getStatisArea();
		Collections.sort(list, new Comparator<CrdStatisAreaVO>() {
			public int compare(CrdStatisAreaVO o1, CrdStatisAreaVO o2) {
				//按照金额大小进行降序排列，02>01表示升序
				return CommonUtil.nullToZero(o2.getLimitCredit()).compareTo(CommonUtil.nullToZero(o1.getLimitCredit()));
			}
		});
		return R.data(ForestNodeMerger.merge(list));
	}

}
