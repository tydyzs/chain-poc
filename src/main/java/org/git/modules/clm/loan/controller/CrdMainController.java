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
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.clm.credit.service.ICrdGrantingSerialService;
import org.git.modules.clm.credit.vo.CrdGrantingSerialVO;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.loan.vo.*;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 额度主表（客户+二级额度产品+机构） 控制器
 *
 * @author git
 * @since 2019-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/crdMain")
@Api(value = "额度主表（客户+二级额度产品+机构）", tags = "额度主表（客户+二级额度产品+机构）接口")
public class CrdMainController extends ChainController {

	private ICrdMainService crdMainService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdMain")
	public R<CrdMain> detail(CrdMain crdMain) {
		CrdMain detail = crdMainService.getOne(Condition.getQueryWrapper(crdMain));
		return R.data(detail);
	}

	/**
	 * 分页 额度主表（客户+二级额度产品+机构）
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdMain")
	public R<IPage<CrdMain>> list(CrdMain crdMain, Query query) {
		IPage<CrdMain> pages = crdMainService.page(Condition.getPage(query), Condition.getQueryWrapper(crdMain));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度主表（客户+二级额度产品+机构）
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdMain")
	public R<IPage<CrdMainVO>> page(CrdMainVO crdMain, Query query) {
		IPage<CrdMainVO> pages = crdMainService.selectCrdMainPage(Condition.getPage(query), crdMain);
		return R.data(pages);
	}

	/**
	 * 新增 额度主表（客户+二级额度产品+机构）
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdMain")
	public R save(@Valid @RequestBody CrdMain crdMain) {
		return R.status(crdMainService.save(crdMain));
	}

	/**
	 * 修改 额度主表（客户+二级额度产品+机构）
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdMain")
	public R update(@Valid @RequestBody CrdMain crdMain) {
		return R.status(crdMainService.updateById(crdMain));
	}

	/**
	 * 新增或修改 额度主表（客户+二级额度产品+机构）
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdMain")
	public R submit(@Valid @RequestBody CrdMain crdMain) {
		return R.status(crdMainService.saveOrUpdate(crdMain));
	}


	/**
	 * 删除 额度主表（客户+二级额度产品+机构）
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdMainService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询同业额度信息
	 * @param query
	 * @return
	 */
	@GetMapping("/findLedgerCrdMainPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询同业客户额度信息", notes = "传入crdMain")
	public R<IPage<CrdMainVO>> findLedgerCrdMainPage(@ApiParam(value = "客户编号", required = true) String customerNum,
													 @ApiParam(value = "经办机构", required = true) String orgNum,
	Query query) {
		IPage<CrdMainVO> pages = crdMainService.findLedgerCrdMainPage(Condition.getPage(query), customerNum, orgNum);
		return R.data(pages);
	}

	/**
	 * 查询客户额度台账的额度信息
	 * @param query
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/getCorporateList")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询客户额度台账的额度信息", notes = "传入customerNum")
	public R<IPage<CrdMainVO>> getCorporateList(@ApiParam(required = true)String customerNum, @ApiParam(required = true)String orgNum,Query query) {
		IPage<CrdMainVO> pages = crdMainService.selectCorporateCrdList(Condition.getPage(query),customerNum,orgNum);
		return R.data(pages);
	}

}
