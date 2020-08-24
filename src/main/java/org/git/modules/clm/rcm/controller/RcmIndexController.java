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
package org.git.modules.clm.rcm.controller;

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
import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.vo.RcmIndexVO;
import org.git.modules.clm.rcm.service.IRcmIndexService;
import org.git.core.boot.ctrl.ChainController;

import java.util.List;

/**
 * 限额基础指标库 控制器
 *
 * @author liuye
 * @since 2019-11-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmConfigurationBase")
@Api(value = "限额基础指标库", tags = "限额基础指标库接口")
public class RcmIndexController extends ChainController {

	private IRcmIndexService rcmConfigurationBaseService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmConfigurationBase")
	public R<RcmIndex> detail(RcmIndex rcmIndex) {
		RcmIndex detail = rcmConfigurationBaseService.getOne(Condition.getQueryWrapper(rcmIndex));
		return R.data(detail);
		
	}

	/**
	 * 限额基础指标库
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationBase")
	public R<List<RcmIndex>> list(RcmIndex rcmIndex) {
		List<RcmIndex> rcmIndices = rcmConfigurationBaseService.list(Condition.getQueryWrapper(rcmIndex));
		return R.data(rcmIndices);
	}

	/**
	 * 自定义分页 限额基础指标库
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationBase")
	public R<IPage<RcmIndexVO>> page(RcmIndexVO rcmConfigurationBase, Query query) {
		IPage<RcmIndexVO> pages = rcmConfigurationBaseService.selectRcmConfigurationBasePage(Condition.getPage(query), rcmConfigurationBase);
		return R.data(pages);
	}

	/**
	 * 新增 限额基础指标库
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmConfigurationBase")
	public R save(@Valid @RequestBody RcmIndex rcmIndex) {
		return R.status(rcmConfigurationBaseService.save(rcmIndex));
	}

	/**
	 * 修改 限额基础指标库
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmConfigurationBase")
	public R update(@Valid @RequestBody RcmIndex rcmIndex) {
		return R.status(rcmConfigurationBaseService.updateById(rcmIndex));
	}

	/**
	 * 新增或修改 限额基础指标库
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmConfigurationBase")
	public R submit(@Valid @RequestBody RcmIndex rcmIndex) {
		return R.status(rcmConfigurationBaseService.saveOrUpdate(rcmIndex));
	}


	/**
	 * 删除 限额基础指标库
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmConfigurationBaseService.removeByIds(Func.toLongList(ids)));
	}


}
