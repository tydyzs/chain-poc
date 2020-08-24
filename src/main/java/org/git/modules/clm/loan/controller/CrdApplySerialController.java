/*
 *      Copyright (c) 2018-2028, Global InfoTech All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: 高伟达武汉事业部
 */
package org.git.modules.clm.loan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.vo.CrdApplySerialVO;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 额度使用流水 控制器
 *
 * @author git
 * @since 2019-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/crdapplyserial")
@Api(value = "额度使用流水", tags = "额度使用流水接口")
public class CrdApplySerialController extends ChainController {

	private ICrdApplySerialService crdApplySerialService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdApplySerial")
	public R<CrdApplySerial> detail(CrdApplySerial crdApplySerial) {
		CrdApplySerial detail = crdApplySerialService.getOne(Condition.getQueryWrapper(crdApplySerial));
		return R.data(detail);
	}

	/**
	 * 分页 额度使用流水
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdApplySerial")
	public R<IPage<CrdApplySerial>> list(CrdApplySerial crdApplySerial, Query query) {
		IPage<CrdApplySerial> pages = crdApplySerialService.page(Condition.getPage(query), Condition.getQueryWrapper(crdApplySerial));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度使用流水
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdApplySerial")
	public R<IPage<CrdApplySerialVO>> page(CrdApplySerialVO crdApplySerial, Query query) {
		IPage<CrdApplySerialVO> pages = crdApplySerialService.selectCrdApplySerialPage(Condition.getPage(query), crdApplySerial);
		return R.data(pages);
	}

	/**
	 * 新增 额度使用流水
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdApplySerial")
	public R save(@Valid @RequestBody CrdApplySerial crdApplySerial) {
		return R.status(crdApplySerialService.save(crdApplySerial));
	}

	/**
	 * 修改 额度使用流水
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdApplySerial")
	public R update(@Valid @RequestBody CrdApplySerial crdApplySerial) {
		return R.status(crdApplySerialService.updateById(crdApplySerial));
	}

	/**
	 * 新增或修改 额度使用流水
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdApplySerial")
	public R submit(@Valid @RequestBody CrdApplySerial crdApplySerial) {
		return R.status(crdApplySerialService.saveOrUpdate(crdApplySerial));
	}


	/**
	 * 删除 额度使用流水
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdApplySerialService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询同业客户额度台账占用恢复信息
	 * @param query
	 * @param customerNum
	 * @return
	 */
	@GetMapping("/findLedgerCrdApplySerialPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询同业客户额度占用恢复信息", notes = "传入crdApplySerialVO")
	public R<IPage<CrdApplySerialVO>> findLedgerCrdApplySerialPage(@ApiParam(value = "客户编号", required = true) String customerNum,
																	@ApiParam(value = "经办机构", required = true) String orgNum,
																   Query query) {
		IPage<CrdApplySerialVO> pages = crdApplySerialService.findLedgerCrdApplySerialPage(Condition.getPage(query),customerNum,orgNum);
		return R.data(pages);
	}
	/**
	 * 查询额度台账占用恢复信息
	 *
	 * @param query
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/getApplySerialPage")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询额度台账占用恢复信息", notes = "传入customerNum,orgNum")
	public R<IPage<CrdApplySerialVO>> getApplySerialPage(@ApiParam(value = "客户编号", required = true) String customerNum, @ApiParam(value = "机构编号", required = true) String orgNum, Query query) {
		IPage<CrdApplySerialVO> pages = crdApplySerialService.getApplySerialPage(Condition.getPage(query), customerNum, orgNum);
		return R.data(pages);
	}

}
