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
import org.git.modules.clm.rcm.entity.RcmConfigPara;
import org.git.modules.clm.rcm.service.IRcmConfigParaService;
import org.git.modules.clm.rcm.vo.RcmConfigParaVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 限额参数配置信息表 控制器
 *
 * @author liuye
 * @since 2019-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-rcm/rcmConfigurationPara")
@Api(value = "限额参数配置信息表", tags = "限额参数配置信息表接口")
public class RcmConfigParaController extends ChainController {

	private IRcmConfigParaService rcmConfigurationParaService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rcmConfigurationPara")
	public R<RcmConfigPara> detail(RcmConfigPara rcmConfigurationPara) {
		RcmConfigPara detail = rcmConfigurationParaService.getOne(Condition.getQueryWrapper(rcmConfigurationPara));
		return R.data(detail);
	}

	/**
	 * 分页 限额参数配置信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationPara")
	public R<IPage<RcmConfigPara>> list(RcmConfigPara rcmConfigurationPara, Query query) {
		IPage<RcmConfigPara> pages = rcmConfigurationParaService.page(Condition.getPage(query), Condition.getQueryWrapper(rcmConfigurationPara));
		return R.data(pages);
	}

	/**
	 * 自定义分页 限额参数配置信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rcmConfigurationPara")
	public R<IPage<RcmConfigParaVO>> page(RcmConfigParaVO rcmConfigurationPara, Query query) {
		IPage<RcmConfigParaVO> pages = rcmConfigurationParaService.selectRcmControlPage(Condition.getPage(query), rcmConfigurationPara);
		return R.data(pages);
	}

	/**
	 * 新增 限额参数配置信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rcmConfigurationPara")
	public R save(@Valid @RequestBody RcmConfigPara rcmConfigurationPara) {
		return R.status(rcmConfigurationParaService.save(rcmConfigurationPara));
	}

	/**
	 * 修改 限额参数配置信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rcmConfigurationPara")
	public R update(@Valid @RequestBody RcmConfigPara rcmConfigurationPara) {
		return R.status(rcmConfigurationParaService.updateById(rcmConfigurationPara));
	}

	/**
	 * 新增或修改 限额参数配置信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rcmConfigurationPara")
	public R submit(@Valid @RequestBody RcmConfigPara rcmConfigurationPara) {
		return R.status(rcmConfigurationParaService.saveOrUpdate(rcmConfigurationPara));
	}


	/**
	 * 删除 限额参数配置信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(rcmConfigurationParaService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 按格式固定查询三条（有合并）
	 * @param quotaNum
	 * @return
	 */
	@GetMapping("/paraDetail")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "按格式固定查询三条（有合并）", notes = "传入quotaNum")
	public R<List<RcmConfigParaVO>> detail(String quotaNum) {
		List<RcmConfigParaVO> detail = rcmConfigurationParaService.selectOneRcmControl(quotaNum);
		return R.data(detail);
	}
}
