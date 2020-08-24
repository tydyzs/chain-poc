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
import org.git.modules.clm.credit.entity.CrdStatisCsmHs;
import org.git.modules.clm.credit.vo.CrdStatisCsmHsVO;
import org.git.modules.clm.credit.service.ICrdStatisCsmHsService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 额度统计表-客户-历史 控制器
 *
 * @author git
 * @since 2019-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-crd/crdstatiscsmhs")
@Api(value = "额度统计表-客户-历史", tags = "额度统计表-客户-历史接口")
public class CrdStatisCsmHsController extends ChainController {

	private ICrdStatisCsmHsService crdStatisCsmHsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdStatisCsmHs")
	public R<CrdStatisCsmHs> detail(CrdStatisCsmHs crdStatisCsmHs) {
		CrdStatisCsmHs detail = crdStatisCsmHsService.getOne(Condition.getQueryWrapper(crdStatisCsmHs));
		return R.data(detail);
	}

	/**
	 * 分页 额度统计表-客户-历史
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdStatisCsmHs")
	public R<IPage<CrdStatisCsmHs>> list(CrdStatisCsmHs crdStatisCsmHs, Query query) {
		IPage<CrdStatisCsmHs> pages = crdStatisCsmHsService.page(Condition.getPage(query), Condition.getQueryWrapper(crdStatisCsmHs));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度统计表-客户-历史
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdStatisCsmHs")
	public R<IPage<CrdStatisCsmHsVO>> page(CrdStatisCsmHsVO crdStatisCsmHs, Query query) {
		IPage<CrdStatisCsmHsVO> pages = crdStatisCsmHsService.selectCrdStatisCsmHsPage(Condition.getPage(query), crdStatisCsmHs);
		return R.data(pages);
	}

	/**
	 * 新增 额度统计表-客户-历史
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdStatisCsmHs")
	public R save(@Valid @RequestBody CrdStatisCsmHs crdStatisCsmHs) {
		return R.status(crdStatisCsmHsService.save(crdStatisCsmHs));
	}

	/**
	 * 修改 额度统计表-客户-历史
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdStatisCsmHs")
	public R update(@Valid @RequestBody CrdStatisCsmHs crdStatisCsmHs) {
		return R.status(crdStatisCsmHsService.updateById(crdStatisCsmHs));
	}

	/**
	 * 新增或修改 额度统计表-客户-历史
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdStatisCsmHs")
	public R submit(@Valid @RequestBody CrdStatisCsmHs crdStatisCsmHs) {
		return R.status(crdStatisCsmHsService.saveOrUpdate(crdStatisCsmHs));
	}

	
	/**
	 * 删除 额度统计表-客户-历史
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdStatisCsmHsService.removeByIds(Func.toLongList(ids)));
	}

	
}
