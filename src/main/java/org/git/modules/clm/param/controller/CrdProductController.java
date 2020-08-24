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
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.vo.CrdProductVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author git
 * @since 2019-10-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-param/crdProduct")
@Api(value = "额度产品-业务产品关联关系", tags = "接口")
public class CrdProductController extends ChainController {

	private ICrdProductService crdProductService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdProduct")
	public R<CrdProduct> detail(CrdProduct crdProduct) {
		CrdProduct detail = crdProductService.getOne(Condition.getQueryWrapper(crdProduct));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdProduct")
	public R<IPage<CrdProduct>> list(CrdProduct crdProduct, Query query) {
		IPage<CrdProduct> pages = crdProductService.page(Condition.getPage(query), Condition.getQueryWrapper(crdProduct));
		return R.data(pages);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdProduct")
	public R<IPage<CrdProductVO>> page(CrdProductVO crdProduct, Query query) {
		IPage<CrdProductVO> pages = crdProductService.selectCrdProductPage(Condition.getPage(query), crdProduct);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdProduct")
	public R save(@Valid @RequestBody CrdProduct crdProduct,ChainUser chainUser) {
		return crdProductService.saveCrdProduct(crdProduct,chainUser);
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdProduct")
	public R update(@Valid @RequestBody CrdProduct crdProduct) {
		return R.status(crdProductService.updateById(crdProduct));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdProduct")
	public R submit(@Valid @RequestBody CrdProduct crdProduct) {
		return R.status(crdProductService.saveOrUpdate(crdProduct));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdProductService.removeByIds(Func.toStrList(ids)));
	}


}
