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
package org.git.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.modules.system.entity.Dict;
import org.git.modules.system.service.IDictService;
import org.git.modules.system.vo.DictVO;
import org.git.modules.system.wrapper.DictWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends ChainController {

	private IDictService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dict")
	public R<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return R.data(DictWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dict")
	public R<IPage<DictVO>> list(@ApiIgnore @RequestParam Map<String, Object> dict, Query query) {
		IPage<Dict> pages = dictService.page(Condition.getPage(query), Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		return R.data(DictWrapper.build().pageVO(pages));
	}

	/**
	 * 获取字典树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R submit(@Valid @RequestBody Dict dict) {
		return R.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	@CacheEvict(cacheNames = {SYS_CACHE}, allEntries = true)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(dictService.removeDict(ids));
	}

	/**
	 * 获取字典
	 *
	 * @return
	 */
	@GetMapping("/dictionary")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取字典", notes = "获取字典")
	public R<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(tree);
	}


	/**
	 * 字典翻译器，可以翻译字典、用户、机构等
	 *
	 * @Author chenchuan
	 */
	@GetMapping("/dictTranslate")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "字典翻译器", notes = "返回字典key、字典value")
	public R<Map<String, String>> dictTranslate(
		@ApiParam(value = "字典码，传入：user（用户）、org（机构）、product（业务品种）、crd（额度品种）、" +
			"CD******（字典）、system（来源系统）", required = true) @RequestParam
			String code) {
		//TODO 缓存处理
		Map<String, String> map = dictService.dictTranslate(code);
		return R.data(map);
	}


	/**
	 * 字典翻译器，可以翻译字典、用户、机构等
	 *
	 * @Author chenchuan
	 */
	@GetMapping("/dictionarys")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "字典翻译器", notes = "返回字典key、字典value")
	public R<Map<String, List<Dict>>> dictionarys(@RequestParam	String codes) {
		//TODO 缓存处理
		String[]  codeArr = codes.split(",");
		Map<String,List<Dict>> resMap = new HashMap<>();
		for (String code : codeArr ) {
			if(StringUtils.isNotBlank(code)){
				List<Dict> list = dictService.getList(code);
				resMap.put(code,list);
			}
		}
		return R.data(resMap);
	}



	/**
	 * 根据CODE获取字典树形结构
	 *
	 * @return
	 */
	@GetMapping("/treeByCode")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictVO>> treeByCode(String code) {
		List<DictVO> tree = dictService.treeByCode(code);
		return R.data(tree);
	}


	/**
	 * 根据CODE获取字典树形结构,,其中value值为dict_key
	 *
	 * @return
	 */
	@GetMapping("/treeAndKeyAsValue")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictVO>> keyAsValuesTree(String code) {
		List<DictVO> tree = dictService.keyAsValuesTree(code);
		return R.data(tree);
	}
}
