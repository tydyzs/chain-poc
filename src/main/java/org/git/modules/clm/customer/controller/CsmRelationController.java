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
package org.git.modules.clm.customer.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.customer.entity.CsmRelation;
import org.git.modules.clm.customer.service.ICsmRelationService;
import org.git.modules.clm.customer.vo.CsmRelationVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 关系信息表 控制器
 *
 * @author git
 * @since 2019-12-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmrelation")
@Api(value = "关系信息表", tags = "关系信息表接口")
public class CsmRelationController extends ChainController {

	private ICsmRelationService csmRelationService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmRelation")
	public R<CsmRelation> detail(CsmRelation csmRelation) {
		CsmRelation detail = csmRelationService.getOne(Condition.getQueryWrapper(csmRelation));
		return R.data(detail);
	}

	/**
	 * 分页 关系信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmRelation")
	public R<IPage<CsmRelation>> list(CsmRelation csmRelation, Query query) {
		IPage<CsmRelation> pages = csmRelationService.page(Condition.getPage(query), Condition.getQueryWrapper(csmRelation));
		return R.data(pages);
	}

	/**
	 * 自定义分页 关系信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmRelation")
	public R<IPage<CsmRelationVO>> page(CsmRelationVO csmRelation, Query query) {
		IPage<CsmRelationVO> pages = csmRelationService.selectCsmRelationPage(Condition.getPage(query), csmRelation);
		return R.data(pages);
	}

	/**
	 * 新增 关系信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmRelation")
	public R save(@Valid @RequestBody CsmRelation csmRelation) {
		return R.status(csmRelationService.save(csmRelation));
	}

	/**
	 * 修改 关系信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmRelation")
	public R update(@Valid @RequestBody CsmRelation csmRelation) {
		return R.status(csmRelationService.updateById(csmRelation));
	}

	/**
	 * 新增或修改 关系信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmRelation")
	public R submit(@Valid @RequestBody CsmRelation csmRelation) {
		return R.status(csmRelationService.saveOrUpdate(csmRelation));
	}


	/**
	 * 删除 关系信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmRelationService.removeByIds(Func.toLongList(ids)));
	}


}
