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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.git.modules.clm.loan.entity.CrdSurety;
import org.git.modules.clm.loan.vo.CrdSuretyVO;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.loan.service.ICrdSuretyService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 抵质押物/保证人信息表 控制器
 *
 * @author git
 * @since 2019-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-crd/crdsurety")
@Api(value = "抵质押物/保证人信息表", tags = "抵质押物/保证人信息表接口")
public class CrdSuretyController extends ChainController {

	private ICrdSuretyService crdSuretyService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdSurety")
	public R<CrdSurety> detail(CrdSurety crdSurety) {
		CrdSurety detail = crdSuretyService.getOne(Condition.getQueryWrapper(crdSurety));
		return R.data(detail);
	}

	/**
	 * 分页 抵质押物/保证人信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdSurety")
	public R<IPage<CrdSurety>> list(CrdSurety crdSurety, Query query) {
		IPage<CrdSurety> pages = crdSuretyService.page(Condition.getPage(query), Condition.getQueryWrapper(crdSurety));
		return R.data(pages);
	}

	/**
	 * 自定义分页 抵质押物/保证人信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdSurety")
	public R<IPage<CrdSuretyVO>> page(CrdSuretyVO crdSurety, Query query) {
		IPage<CrdSuretyVO> pages = crdSuretyService.selectCrdSuretyPage(Condition.getPage(query), crdSurety);
		return R.data(pages);
	}

	/**
	 * 新增 抵质押物/保证人信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdSurety")
	public R save(@Valid @RequestBody CrdSurety crdSurety) {
		return R.status(crdSuretyService.save(crdSurety));
	}

	/**
	 * 修改 抵质押物/保证人信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdSurety")
	public R update(@Valid @RequestBody CrdSurety crdSurety) {
		return R.status(crdSuretyService.updateById(crdSurety));
	}

	/**
	 * 新增或修改 抵质押物/保证人信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdSurety")
	public R submit(@Valid @RequestBody CrdSurety crdSurety) {
		return R.status(crdSuretyService.saveOrUpdate(crdSurety));
	}

	
	/**
	 * 删除 抵质押物/保证人信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdSuretyService.removeByIds(Func.toLongList(ids)));
	}

	
}
