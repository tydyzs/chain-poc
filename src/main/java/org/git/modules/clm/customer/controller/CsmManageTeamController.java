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
import org.git.modules.clm.customer.entity.CsmManageTeam;
import org.git.modules.clm.customer.service.ICsmManageTeamService;
import org.git.modules.clm.customer.vo.CsmManageTeamVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 管理团队 控制器
 *
 * @author git
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-csm/csmmanageteam")
@Api(value = "管理团队", tags = "管理团队接口")
public class CsmManageTeamController extends ChainController {

	private ICsmManageTeamService csmManageTeamService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入csmManageTeam")
	public R<CsmManageTeam> detail(CsmManageTeam csmManageTeam) {
		CsmManageTeam detail = csmManageTeamService.getOne(Condition.getQueryWrapper(csmManageTeam));
		return R.data(detail);
	}

	/**
	 * 分页 管理团队
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入csmManageTeam")
	public R<IPage<CsmManageTeam>> list(CsmManageTeam csmManageTeam, Query query) {
		IPage<CsmManageTeam> pages = csmManageTeamService.page(Condition.getPage(query), Condition.getQueryWrapper(csmManageTeam));
		return R.data(pages);
	}

	/**
	 * 自定义分页 管理团队
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入csmManageTeam")
	public R<IPage<CsmManageTeamVO>> page(CsmManageTeamVO csmManageTeam, Query query) {
		IPage<CsmManageTeamVO> pages = csmManageTeamService.selectCsmManageTeamPage(Condition.getPage(query), csmManageTeam);
		return R.data(pages);
	}

	/**
	 * 新增 管理团队
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入csmManageTeam")
	public R save(@Valid @RequestBody CsmManageTeam csmManageTeam) {
		return R.status(csmManageTeamService.save(csmManageTeam));
	}

	/**
	 * 修改 管理团队
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入csmManageTeam")
	public R update(@Valid @RequestBody CsmManageTeam csmManageTeam) {
		return R.status(csmManageTeamService.updateById(csmManageTeam));
	}

	/**
	 * 新增或修改 管理团队
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入csmManageTeam")
	public R submit(@Valid @RequestBody CsmManageTeam csmManageTeam) {
		return R.status(csmManageTeamService.saveOrUpdate(csmManageTeam));
	}


	/**
	 * 删除 管理团队
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(csmManageTeamService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * 查询我行管理团队
	 * @param query
	 * @param customerNum
	 * @return
	 */
	@GetMapping("/getCsmManageTeamPageByCusNum")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询我行管理团队", notes = "传入customerNum")
	public R<IPage<CsmManageTeamVO>> selectCsmManageTeamPageByCusNum(@ApiParam(required = true) String customerNum, Query query) {
		IPage<CsmManageTeamVO> pages = csmManageTeamService.selectCsmManageTeamPageByCusNum(Condition.getPage(query), customerNum);
		return R.data(pages);
	}

}
