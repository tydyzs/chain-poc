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
import org.git.modules.clm.credit.entity.TbCrdSubcontractCon;
import org.git.modules.clm.credit.vo.TbCrdSubcontractConVO;
import org.git.modules.clm.credit.service.ITbCrdSubcontractConService;
import org.git.core.boot.ctrl.ChainController;

/**
 * 担保合同与业务合同关联关系 控制器
 *
 * @author git
 * @since 2019-11-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdsubcontractcon")
@Api(value = "担保合同与业务合同关联关系", tags = "担保合同与业务合同关联关系接口")
public class TbCrdSubcontractConController extends ChainController {

	private ITbCrdSubcontractConService tbCrdSubcontractConService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdSubcontractCon")
	public R<TbCrdSubcontractCon> detail(TbCrdSubcontractCon tbCrdSubcontractCon) {
		TbCrdSubcontractCon detail = tbCrdSubcontractConService.getOne(Condition.getQueryWrapper(tbCrdSubcontractCon));
		return R.data(detail);
	}

	/**
	 * 分页 担保合同与业务合同关联关系
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdSubcontractCon")
	public R<IPage<TbCrdSubcontractCon>> list(TbCrdSubcontractCon tbCrdSubcontractCon, Query query) {
		IPage<TbCrdSubcontractCon> pages = tbCrdSubcontractConService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdSubcontractCon));
		return R.data(pages);
	}

	/**
	 * 自定义分页 担保合同与业务合同关联关系
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdSubcontractCon")
	public R<IPage<TbCrdSubcontractConVO>> page(TbCrdSubcontractConVO tbCrdSubcontractCon, Query query) {
		IPage<TbCrdSubcontractConVO> pages = tbCrdSubcontractConService.selectTbCrdSubcontractConPage(Condition.getPage(query), tbCrdSubcontractCon);
		return R.data(pages);
	}

	/**
	 * 新增 担保合同与业务合同关联关系
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdSubcontractCon")
	public R save(@Valid @RequestBody TbCrdSubcontractCon tbCrdSubcontractCon) {
		return R.status(tbCrdSubcontractConService.save(tbCrdSubcontractCon));
	}

	/**
	 * 修改 担保合同与业务合同关联关系
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdSubcontractCon")
	public R update(@Valid @RequestBody TbCrdSubcontractCon tbCrdSubcontractCon) {
		return R.status(tbCrdSubcontractConService.updateById(tbCrdSubcontractCon));
	}

	/**
	 * 新增或修改 担保合同与业务合同关联关系
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdSubcontractCon")
	public R submit(@Valid @RequestBody TbCrdSubcontractCon tbCrdSubcontractCon) {
		return R.status(tbCrdSubcontractConService.saveOrUpdate(tbCrdSubcontractCon));
	}

	
	/**
	 * 删除 担保合同与业务合同关联关系
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdSubcontractConService.removeByIds(Func.toLongList(ids)));
	}

	
}
