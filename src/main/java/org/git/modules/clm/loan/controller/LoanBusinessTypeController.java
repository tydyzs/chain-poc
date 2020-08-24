package org.git.modules.clm.loan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.core.tool.api.R;
import org.git.modules.clm.front.dto.jxrcb.loan.ProductInfoDTO;
import org.git.modules.clm.loan.service.ILoanBusinessTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 信贷业务品种查询 控制器
 *
 * @author zhouweijie
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-loan/business-type")
@Api(value = "信贷业务品种查询", tags = "信贷业务品种查询接口")
public class LoanBusinessTypeController {

	private ILoanBusinessTypeService loanBusinessTypeService;

	/**
	 * 查询
	 */
	@PostMapping("/query")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询业务品种", notes = "传入productNum")
	public R<List<ProductInfoDTO>> query(@RequestParam String productNum) {
		List<ProductInfoDTO> productInfoList =
			loanBusinessTypeService.dealLoanBusinessType(productNum);
		if (null == productInfoList) {
			return R.fail("查询业务品种编号为'" + productNum + "'的业务品种异常！");
		}
		return R.data(productInfoList);
	}
}
