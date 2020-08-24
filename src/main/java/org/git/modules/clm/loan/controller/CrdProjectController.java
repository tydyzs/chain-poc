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

import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.loan.entity.CrdProject;
import org.git.modules.clm.loan.vo.CrdProjectVO;
import org.git.modules.clm.loan.service.ICrdProjectService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 项目协议表 控制器
 *
 * @author git
 * @since 2019-11-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-loan/crdproject")
@Api(value = "项目协议表", tags = "项目协议表接口")
public class CrdProjectController extends ChainController {

	private ICrdProjectService crdProjectService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdProject")
	public R<CrdProject> detail(CrdProject crdProject) {
		CrdProject detail = crdProjectService.getOne(Condition.getQueryWrapper(crdProject));
		return R.data(detail);
	}

	/**
	 * 分页 项目协议表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdProject")
	public R<IPage<CrdProject>> list(CrdProject crdProject, Query query) {
		IPage<CrdProject> pages = crdProjectService.page(Condition.getPage(query), Condition.getQueryWrapper(crdProject));
		return R.data(pages);
	}

	/**
	 * 自定义分页 项目协议表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdProject")
	public R<IPage<CrdProjectVO>> page(CrdProjectVO crdProject, Query query) {
		IPage<CrdProjectVO> pages = crdProjectService.selectCrdProjectPage(Condition.getPage(query), crdProject);
		return R.data(pages);
	}

	/**
	 * 新增 项目协议表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdProject")
	public R save(@Valid @RequestBody CrdProject crdProject) {
		return R.status(crdProjectService.save(crdProject));
	}

	/**
	 * 修改 项目协议表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdProject")
	public R update(@Valid @RequestBody CrdProject crdProject) {
		return R.status(crdProjectService.updateById(crdProject));
	}

	/**
	 * 新增或修改 项目协议表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdProject")
	public R submit(@Valid @RequestBody CrdProject crdProject) {
		return R.status(crdProjectService.saveOrUpdate(crdProject));
	}

	
	/**
	 * 删除 项目协议表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdProjectService.removeByIds(Func.toLongList(ids)));
	}

	
}
