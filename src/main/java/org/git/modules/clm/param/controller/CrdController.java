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
package org.git.modules.clm.param.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.service.ICrdService;
import org.git.modules.clm.param.vo.CrdVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author git
 * @since 2019-10-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-param/crd")
@Api(value = "额度产品管理", tags = "接口")
@Slf4j
public class CrdController extends ChainController {

	private ICrdService crdService;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crd")
	public R<Crd> detail(Crd crd) {
		Crd detail = crdService.getOne(Condition.getQueryWrapper(crd));
		return R.data(detail);
	}

	/**
	 *获取所有数据（前端生成树）
	 */
	@GetMapping("/query")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询所有额度")
	public R<List<Map<String,Object>>> query() {
		log.info("本次额度树未从缓存获取");
		return crdService.queryTree();
	}
	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crd")
	public R<IPage<Crd>> list(Crd crd, Query query) {
		IPage<Crd> pages = crdService.page(Condition.getPage(query), Condition.getQueryWrapper(crd));
		return R.data(pages);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分页", notes = "传入crd")
	public R<IPage<CrdVO>> page(CrdVO crd, Query query) {
		IPage<CrdVO> pages = crdService.selectCrdPage(Condition.getPage(query), crd);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增", notes = "传入crd")
	public R save(@Valid @RequestBody Crd crd, ChainUser chainUser) {
		return crdService.saveCrd(crd,chainUser);
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改", notes = "传入crd")
	public R update(@Valid @RequestBody Crd crd, ChainUser chainUser) {
		return crdService.updateCrd(crd,chainUser);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增或修改", notes = "传入crd")
	public R submit(@Valid @RequestBody Crd crd) {
		return R.status(crdService.saveOrUpdate(crd));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String crdProductNum) {
		return crdService.deletecrd(crdProductNum);
	}

	/**
	 * 自定义删除
	 */
	@PostMapping("/delete")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "删除", notes = "传入crdProductNum")
	public R delete(@ApiParam(value = "主键", required = true) @RequestParam String crdProductNum) {
		return crdService.deletecrd(crdProductNum);
	}
	/**
	 * 后台自动生成额度主键
	 */
	@PostMapping("/getCrdId")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "后台自动生成额度主键")
	public R getCrdId(String crdId) {
		return R.data(crdService.getCrdId(crdId));
	}

	/**
	 * 根据额度编码查询额度数据
	 */
	@PostMapping("/getCrd")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据额度编码查询额度数据")
	public R getCrd(String crdId) {
		return R.data(crdService.getById(crdId));
	}

	/**
	 * 详情
	 */
	@GetMapping("/detailByName")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "详情", notes = "传入crdProductName")
	public Crd detailByName(String crdProductName) {
		Crd detail = crdService.getCrdByName(crdProductName);
		return detail;
	}
}
