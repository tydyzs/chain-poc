package org.git.modules.clm.batch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.SpringUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.batch.service.IBatchService;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.system.entity.Dept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 批量处理
 *
 * @author chenchuan
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_BATCH_NAME + "/credit")
@Api(value = "日终批量", tags = "日终批量")
public class BatchController extends ChainController {

	IBatchService iBatchService;
	ICommonService iCommonService;

	/**
	 * 日终批量处理-授信额度重算
	 *
	 * @author chenchuan
	 */
		@GetMapping("/btCreditRecount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "授信额度重算", notes = "重算所有授信客户的额度")
	public R btCreditRecount() {
		String msg = iBatchService.btCreditRecount();
		return R.success(msg);
	}

	/**
	 * 日终批量处理-担保额度重算
	 *
	 * @author chenchuan
	 */
	@GetMapping("/btGuaranteeRecount")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "担保额度重算", notes = "重算所有担保客户的额度")
	public R btGuaranteeRecount() {
		String msg = iBatchService.btGuaranteeRecount();
		return R.success(msg);
	}

	/**
	 * 日终批量处理-第三方额度重算
	 *
	 * @author chenchuan
	 */
	@GetMapping("/btThirdRecount")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "第三方额度重算", notes = "重算所有第三方客户的额度")
	public R btThirdRecount() {
		String msg = iBatchService.btThirdRecount();
		return R.success(msg);
	}

	/**
	 * 日终批量处理-额度统计
	 *
	 * @author chenchuan
	 */
	@GetMapping("/btCreditStatis")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "额度统计", notes = "额度统计")
	public R btCreditStatis() {
		String msg = iBatchService.btCreditStatis();
		return R.success(msg);
	}

	/**
	 * 日终批量处理-历史额度统计
	 *
	 * @author chenchuan
	 */
	@GetMapping("/btCreditStatisHs")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "历史额度统计", notes = "历史额度统计")
	public R btCreditStatisHs() {
		String msg = iCommonService.creditStatisHs();
		return R.success(msg);
	}

	/**
	 * 日终批量处理-历史额度统计
	 *
	 * @author chenchuan
	 */
	@GetMapping("/btDealCc")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "信用卡额度处理", notes = "信用卡额度处理")
	public R btDealCc() {
		String msg = iBatchService.btCcStatis();
		return R.success(msg);
	}

}
