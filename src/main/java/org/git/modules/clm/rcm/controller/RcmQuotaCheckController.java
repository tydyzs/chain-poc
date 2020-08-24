package org.git.modules.clm.rcm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.tool.api.R;
import org.git.modules.clm.rcm.service.RcmQuotaCheckService;
import org.git.modules.clm.rcm.vo.CheckBizInfoVO;
import org.git.modules.clm.rcm.vo.CheckResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chain-Boot
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2019/12/23
 * @since 1.8
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/git-rcm/quota")
@Api("限额检查")
public class RcmQuotaCheckController {
	private RcmQuotaCheckService rcmQuotaCheckService;

	@ApiOperation("检查业务限额情况")
	@ApiOperationSupport(order = 1)
	@PostMapping("/check")
	public R<CheckResultVO> checkQuota(CheckBizInfoVO bizInfo) {
		CheckResultVO resultVO = rcmQuotaCheckService.check(bizInfo);
		return R.data(resultVO);
	}
}
