package org.git.modules.clm.batch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.tool.api.R;
import org.git.modules.clm.batch.service.IBatchDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 批量处理
 *
 * @author lijing
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_BATCH_NAME + "/batch-data")
@Api(value = "日终批量", tags = "日终批量")
public class BatchDataController extends ChainController {
	IBatchDataService  IBatchDataService;

	@GetMapping("/contractDataHandle")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "合同信息处理", notes = "合同信息处理")
	public R contractDataHandle(){
		String msg=IBatchDataService.contractDataHandle();
		return R.success(msg);
	}

	@GetMapping("/approveDataHandle")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "批复信息处理", notes = "批复信息处理")
	public R approveDataHandle(){
		String msg=IBatchDataService.approveDataHandle();
		return R.success(msg);
	}

	@GetMapping("/summaryDataHandle")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "借据信息处理", notes = "借据信息处理")
	public R summaryDataHandle(){
		String msg=IBatchDataService.summaryDataHandle();
		return R.success(msg);
	}

	@GetMapping("/projectDataHandle")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "项目协议信息处理", notes = "项目协议信息处理")
	public R projectDataHandle(){
		String msg=IBatchDataService.projectDataHandle();
		return R.success(msg);
	}

	@GetMapping("/recoveryEventDataHandle")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "实时还款信息处理", notes = "实时还款信息处理")
	public R recoveryEventDataHandle(){
		String msg=IBatchDataService.recoveryEventDataHandle();
		return R.success(msg);
	}

	@GetMapping("/billDataHandle")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "银票签发信息处理", notes = "银票签发信息处理")
	public R billDataHandle(){
		String msg=IBatchDataService.billDataHandle();
		return R.success(msg);
	}

	@GetMapping("/ccDataHandle")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "信用卡信息处理", notes = "信用卡信息处理")
	public R ccDataHandle(){
		String msg=IBatchDataService.ccDataHandle();
		return R.success(msg);
	}
}
