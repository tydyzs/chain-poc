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
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.credit.entity.TbCrdStatisIndustry;
import org.git.modules.clm.credit.vo.TbCrdStatisIndustryVO;
import org.git.modules.clm.credit.service.ITbCrdStatisIndustryService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 额度统计表-行业（历史+实时） 控制器
 *
 * @author git
 * @since 2019-12-04
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdstatisindustry")
@Api(value = "额度统计表-行业（历史+实时）", tags = "额度统计表-行业（历史+实时）接口")
public class TbCrdStatisIndustryController extends ChainController {

	private ITbCrdStatisIndustryService tbCrdStatisIndustryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdStatisIndustry")
	public R<TbCrdStatisIndustry> detail(TbCrdStatisIndustry tbCrdStatisIndustry) {
		TbCrdStatisIndustry detail = tbCrdStatisIndustryService.getOne(Condition.getQueryWrapper(tbCrdStatisIndustry));
		return R.data(detail);
	}

	/**
	 * 分页 额度统计表-行业（历史+实时）
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdStatisIndustry")
	public R<IPage<TbCrdStatisIndustry>> list(TbCrdStatisIndustry tbCrdStatisIndustry, Query query) {
		IPage<TbCrdStatisIndustry> pages = tbCrdStatisIndustryService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdStatisIndustry));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度统计表-行业（历史+实时）
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdStatisIndustry")
	public R<IPage<TbCrdStatisIndustryVO>> page(TbCrdStatisIndustryVO tbCrdStatisIndustry, Query query) {
		IPage<TbCrdStatisIndustryVO> pages = tbCrdStatisIndustryService.selectTbCrdStatisIndustryPage(Condition.getPage(query), tbCrdStatisIndustry);
		return R.data(pages);
	}

	/**
	 * 新增 额度统计表-行业（历史+实时）
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdStatisIndustry")
	public R save(@Valid @RequestBody TbCrdStatisIndustry tbCrdStatisIndustry) {
		return R.status(tbCrdStatisIndustryService.save(tbCrdStatisIndustry));
	}

	/**
	 * 修改 额度统计表-行业（历史+实时）
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdStatisIndustry")
	public R update(@Valid @RequestBody TbCrdStatisIndustry tbCrdStatisIndustry) {
		return R.status(tbCrdStatisIndustryService.updateById(tbCrdStatisIndustry));
	}

	/**
	 * 新增或修改 额度统计表-行业（历史+实时）
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdStatisIndustry")
	public R submit(@Valid @RequestBody TbCrdStatisIndustry tbCrdStatisIndustry) {
		return R.status(tbCrdStatisIndustryService.saveOrUpdate(tbCrdStatisIndustry));
	}

	
	/**
	 * 删除 额度统计表-行业（历史+实时）
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdStatisIndustryService.removeByIds(Func.toLongList(ids)));
	}

	
}
