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
package org.git.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.git.common.constant.AppConstant;
import org.git.common.utils.CommonUtil;
import org.git.core.cache.constant.CacheConstant;
import org.git.core.cache.utils.CacheUtil;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.system.entity.SysDate;
import org.git.modules.system.vo.SysDateVO;
import org.git.modules.system.service.ISysDateService;
import org.git.core.boot.ctrl.ChainController;

import java.util.HashMap;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 营业日期 控制器
 *
 * @author git
 * @since 2019-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/sysdate")
@Api(value = "营业日期", tags = "营业日期接口")
public class SysDateController extends ChainController {

	private ISysDateService sysDateService;


	/**
	 * 获取营业日期
	 */
	@GetMapping("/workDate")
	@ApiOperation(value = "获取营业日期")
	public String getWorkDate() {
		CacheUtil.clear(SYS_CACHE);
		return CommonUtil.getWorkDate();
	}


	/**
	 * 获取营业日期时间
	 */
	@GetMapping("/workDateTime")
	@ApiOperation(value = "获取营业日期")
	public String getWorkDateTime() {
		return CommonUtil.getWorkDateTime().toString();
	}

	/**
	 * 获取当前年份和月份
	 */
	@GetMapping("/getWorkDateMap")
	@ApiOperation(value = "获取当前年份")
	public R<Map<String, String>> getWorkDateMap() {
		Map<String, String> workDate = new HashMap<>();
		workDate.put("year", CommonUtil.getWorkYear());
		workDate.put("month", CommonUtil.getWorkMonth());
		return R.data(workDate);
	}

	/**
	 * 获取当前年份 yyyy
	 */
	@GetMapping("/getWorkYear")
	@ApiOperation(value = "获取当前年份")
	public R<String> getWorkYear() {
		return R.data(CommonUtil.getWorkYear());
	}

	/**
	 * 获取当前月份 mm
	 */
	@GetMapping("/getWorkMonth")
	@ApiOperation(value = "获取当前月份")
	public R<String> getWorkMonth() {
		return R.data(CommonUtil.getWorkMonth());
	}

	/**
	 * 获取批量日期
	 */
	@GetMapping("/batchDate")
	@ApiOperation(value = "获取批量日期")
	public String getBatDate() {
		CacheUtil.clear(SYS_CACHE);
		return CommonUtil.getBatchDate();
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sysDate")
	public R<SysDate> detail(SysDate sysDate) {
		SysDate detail = sysDateService.getOne(Condition.getQueryWrapper(sysDate));
		return R.data(detail);
	}

	/**
	 * 分页 营业日期
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入sysDate")
	public R<IPage<SysDate>> list(SysDate sysDate, Query query) {
		IPage<SysDate> pages = sysDateService.page(Condition.getPage(query), Condition.getQueryWrapper(sysDate));
		return R.data(pages);
	}

	/**
	 * 自定义分页 营业日期
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入sysDate")
	public R<IPage<SysDateVO>> page(SysDateVO sysDate, Query query) {
		IPage<SysDateVO> pages = sysDateService.selectSysDatePage(Condition.getPage(query), sysDate);
		return R.data(pages);
	}

	/**
	 * 新增 营业日期
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入sysDate")
	public R save(@Valid @RequestBody SysDate sysDate) {
		return R.status(sysDateService.save(sysDate));
	}

	/**
	 * 修改 营业日期
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入sysDate")
	public R update(@Valid @RequestBody SysDate sysDate) {
		return R.status(sysDateService.updateById(sysDate));
	}

	/**
	 * 新增或修改 营业日期
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入sysDate")
	public R submit(@Valid @RequestBody SysDate sysDate) {
		return R.status(sysDateService.saveOrUpdate(sysDate));
	}


	/**
	 * 删除 营业日期
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(sysDateService.removeByIds(Func.toLongList(ids)));
	}


}
