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

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.git.modules.clm.param.vo.ProductVO;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.secure.annotation.PreAuth;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.RoleConstant;
import org.git.core.tool.utils.Func;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.IProductService;
import org.git.core.boot.ctrl.ChainController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 *  控制器
 *
 * @author git
 * @since 2019-10-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-param/product")
@Api(value = "业务品种管理", tags = "接口")
public class ProductController extends ChainController {

	private IProductService productService;

	/**
	 * 新增
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增或修改", notes = "传入product")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R submit(@Valid @RequestBody Product product, ChainUser chainUser) {
		return productService.saveProduct(product,chainUser);
	}
	/**
	 * 新增或修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改", notes = "传入product")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R update(@Valid @RequestBody Product product,ChainUser chainUser) {
		return productService.updateProduct(product,chainUser);
	}
	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ProductName", value = "业务品种名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "ProductNo", value = "业务品种编号", paramType = "query", dataType = "string"),
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入Product")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R<IPage<Product>> list(@ApiIgnore @RequestParam Map<String, Object> product, Query query, ChainUser chainUser) {
		IPage<Product> pages = productService.page(Condition.getPage(query), Condition.getQueryWrapper(product, Product.class));
		return R.data(pages);
	}
	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分页", notes = "传入crd")
	public R<IPage<ProductVO>> page(ProductVO product, Query query) {
		IPage<ProductVO> pages = productService.selectProductPage(Condition.getPage(query), product);
		return R.data(pages);
	}
	/**
	 * 查询所有业务品种
	 */
	@GetMapping("/query")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询所有业务品种", notes = "无需参数")
	public R<List<Product>> query() {
		List<Product> pages = productService.list();
		return R.data(pages);
	}
	/**
	 * @Description: 删除
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/remove")
	@ResponseBody
	@ApiOperation(value = "删除业务品种", notes = "传入业务品种编码")
	public R remove(String ids){
		return productService.deleteproduct(ids);
	}

	/**
	 * @Description:根据id查询业务品种
	 * @author shenhuancheng
	 *
	 */
	@GetMapping("/query_product")
	@ApiOperation(value = "业务品种查询", notes = "传入productNum")
	public R<Product> fin1(String productNum) {
		Product Product = productService.getById(productNum);
		return R.data(Product);
	}


	/**
	 * 不分页
	 */
	@GetMapping("/listByProTarger")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "不分页", notes = "传入proTarger")
	public R<List<Product>> listByProTarger(String proTarger) {
		List<Product> list = productService.listByProTarger(proTarger);
		return R.data(list);
	}
}
