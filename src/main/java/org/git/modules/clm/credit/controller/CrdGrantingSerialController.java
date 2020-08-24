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
package org.git.modules.clm.credit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.vo.CrdGrantingSerialVO;
import org.git.modules.clm.credit.service.ICrdGrantingSerialService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 额度授信流水 控制器
 *
 * @author liuye
 * @since 2019-11-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-crd/crdgrantingserial")
@Api(value = "额度授信流水", tags = "额度授信流水接口")
public class CrdGrantingSerialController extends ChainController {

	private ICrdGrantingSerialService crdGrantingSerialService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdGrantingSerial")
	public R<CrdGrantingSerial> detail(CrdGrantingSerial crdGrantingSerial) {
		CrdGrantingSerial detail = crdGrantingSerialService.getOne(Condition.getQueryWrapper(crdGrantingSerial));
		return R.data(detail);
	}

	/**
	 * 分页 额度授信流水
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdGrantingSerial")
	public R<IPage<CrdGrantingSerial>> list(CrdGrantingSerial crdGrantingSerial, Query query) {
		IPage<CrdGrantingSerial> pages = crdGrantingSerialService.page(Condition.getPage(query), Condition.getQueryWrapper(crdGrantingSerial));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度授信流水
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdGrantingSerial")
	public R<IPage<CrdGrantingSerialVO>> page(CrdGrantingSerialVO crdGrantingSerial, Query query) {
		IPage<CrdGrantingSerialVO> pages = crdGrantingSerialService.selectCrdGrantingSerialPage(Condition.getPage(query), crdGrantingSerial);
		return R.data(pages);
	}

	/**
	 * 新增 额度授信流水
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdGrantingSerial")
	public R save(@Valid @RequestBody CrdGrantingSerial crdGrantingSerial) {
		return R.status(crdGrantingSerialService.save(crdGrantingSerial));
	}

	/**
	 * 修改 额度授信流水
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdGrantingSerial")
	public R update(@Valid @RequestBody CrdGrantingSerial crdGrantingSerial) {
		return R.status(crdGrantingSerialService.updateById(crdGrantingSerial));
	}

	/**
	 * 新增或修改 额度授信流水
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdGrantingSerial")
	public R submit(@Valid @RequestBody CrdGrantingSerial crdGrantingSerial) {
		return R.status(crdGrantingSerialService.saveOrUpdate(crdGrantingSerial));
	}

	
	/**
	 * 删除 额度授信流水
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdGrantingSerialService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询同业客户额度台账额度授信情况
	 * @param query
	 * @param customerNum
	 * @return
	 */
	@GetMapping("/findLedgerCrdGrantingSerialPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询同业客户额度台账额度授信", notes = "传入orgNum,customerNum")
	public R<IPage<CrdGrantingSerialVO>> findLedgerCrdGrantingSerialPage(@ApiParam(value = "客户编号", required = true) String customerNum,
																		 @ApiParam(value = "经办机构", required = true) String orgNum,
																		 Query query) {
		IPage<CrdGrantingSerialVO> pages = crdGrantingSerialService.findLedgerCrdGrantingSerialPage(Condition.getPage(query),customerNum,orgNum);
		return R.data(pages);
	}
}
