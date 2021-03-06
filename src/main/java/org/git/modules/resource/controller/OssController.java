/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.modules.resource.entity.Oss;
import org.git.modules.resource.entity.OssVO;
import org.git.modules.resource.wrapper.OssWrapper;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.cache.utils.CacheUtil;
import org.git.common.constant.AppConstant;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.annotation.PreAuth;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.RoleConstant;
import org.git.core.tool.utils.Func;
import org.git.modules.resource.builder.OssBuilder;
import org.git.modules.resource.service.IOssService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 控制器
 *
 * @author Chain
 * @since 2019-05-26
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_RESOURCE_NAME + "/oss")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@ApiIgnore
@Api(value = "对象存储接口", tags = "对象存储接口")
public class OssController extends ChainController {

	private IOssService ossService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入oss")
	public R<OssVO> detail(Oss oss) {
		Oss detail = ossService.getOne(Condition.getQueryWrapper(oss));
		return R.data(OssWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入oss")
	public R<IPage<OssVO>> list(Oss oss, Query query) {
		IPage<Oss> pages = ossService.page(Condition.getPage(query), Condition.getQueryWrapper(oss));
		return R.data(OssWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入oss")
	public R<IPage<OssVO>> page(OssVO oss, Query query) {
		IPage<OssVO> pages = ossService.selectOssPage(Condition.getPage(query), oss);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入oss")
	public R save(@Valid @RequestBody Oss oss) {
		return R.status(ossService.save(oss));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入oss")
	public R update(@Valid @RequestBody Oss oss) {
		return R.status(ossService.updateById(oss));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入oss")
	public R submit(@Valid @RequestBody Oss oss) {
		return R.status(ossService.saveOrUpdate(oss));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(ossService.deleteLogic(Func.toStrList(ids)));
	}


	/**
	 * 启用
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "配置启用", notes = "传入id")
	public R enable(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		CacheUtil.evict(SYS_CACHE, OssBuilder.OSS_CODE, SecureUtil.getTenantId());
		return R.status(ossService.enable(id));
	}

}
