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
package org.git.modules.demo.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.demo.entity.Demo;
import org.git.modules.demo.vo.DemoVO;
import org.git.modules.demo.service.IDemoService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 演示表 控制器
 *
 * @author git
 * @since 2019-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/demo")
@Api(value = "演示表", tags = "演示表接口")
public class DemoController extends ChainController {

	private IDemoService demoService;
	private IDemoService demoService2;
	private IDemoService demoService3;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入demo")
	public R<Demo> detail(Demo demo) {
		Demo detail = demoService.getOne(Condition.getQueryWrapper(demo));
		return R.data(detail);
	}

	/**
	 * 分页 演示表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入demo")
	public R<IPage<Demo>> list(Demo demo, Query query) {
		Condition.getQueryWrapper(demo).like("user_name",demo.getAddress());
		IPage<Demo> pages = demoService.page(Condition.getPage(query), Condition.getQueryWrapper(demo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 演示表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入demo")
	public R<IPage<DemoVO>> page(DemoVO demo, Query query) {
		IPage<DemoVO> pages = demoService.selectDemoPage(Condition.getPage(query), demo);
		return R.data(pages);
	}

	/**
	 * 新增 演示表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入demo")
	public R save(@Valid @RequestBody Demo demo) {
		return R.status(demoService.save(demo));
	}

	/**
	 * 修改 演示表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入demo")
	public R update(@Valid @RequestBody Demo demo) {
		return R.status(demoService.updateById(demo));
	}

	/**
	 * 新增或修改 演示表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入demo")
	public R submit(@Valid @RequestBody Demo demo) {
		return R.status(demoService.saveOrUpdate(demo));
	}


	/**
	 * 删除 演示表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(demoService.removeByIds(Func.toLongList(ids)));
	}


}
