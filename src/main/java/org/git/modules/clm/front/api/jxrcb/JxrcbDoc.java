package org.git.modules.clm.front.api.jxrcb;

import io.swagger.annotations.*;
import org.git.modules.clm.front.dto.jxrcb.common.CreditQueryRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.common.CreditQueryResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanRepayRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanRepayResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 江西农信接口文档
 */
@RequestMapping("/jxrcbAPI")
@Api(tags = "接口文档")
@RestController
public class JxrcbDoc {

	@PostMapping("/loanRepay")
	@ApiOperation(value = "还款接口", notes = "请按照要求发送数据")
	@ApiOperationSupport(order = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "request", value = "请求实体", paramType = "LoanRepayRequestDTO", dataType = "LoanRepayRequestDTO")
	})
	public LoanRepayResponseDTO run(LoanRepayRequestDTO request) {
		return null;
	}

	@PostMapping("/creditQuery")
	@ApiOperation(value = "额度查询接口", notes = "请按照要求发送数据")
	@ApiOperationSupport(order = 2)
	public CreditQueryResponseDTO run(CreditQueryRequestDTO request) {
		return null;
	}


}
