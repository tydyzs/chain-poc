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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.git.common.utils.CommonUtil;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 批复信息表 控制器
 *
 * @author git
 * @since 2019-11-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/tbcrdapprove")
@Api(value = "批复信息表", tags = "批复信息表接口")
public class TbCrdApproveController extends ChainController {

	private ITbCrdApproveService tbCrdApproveService;



	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tbCrdApprove")
	public R<TbCrdApprove> detail(TbCrdApprove tbCrdApprove) {
		TbCrdApprove detail = tbCrdApproveService.getOne(Condition.getQueryWrapper(tbCrdApprove));
		return R.data(detail);
	}

	/**
	 * 分页 批复信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tbCrdApprove")
	public R<IPage<TbCrdApprove>> list(TbCrdApprove tbCrdApprove, Query query) {
		IPage<TbCrdApprove> pages = tbCrdApproveService.page(Condition.getPage(query), Condition.getQueryWrapper(tbCrdApprove));
		return R.data(pages);
	}

	/**
	 * 自定义分页 批复信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tbCrdApprove")
	public R<IPage<TbCrdApproveVO>> page(TbCrdApproveVO tbCrdApprove, Query query) {
		IPage<TbCrdApproveVO> pages = tbCrdApproveService.selectTbCrdApprovePage(Condition.getPage(query), tbCrdApprove);
		return R.data(pages);
	}

	/**
	 * 新增 批复信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tbCrdApprove")
	public R save(@Valid @RequestBody TbCrdApprove tbCrdApprove) {
		return R.status(tbCrdApproveService.save(tbCrdApprove));
	}

	/**
	 * 修改 批复信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tbCrdApprove")
	public R update(@Valid @RequestBody TbCrdApprove tbCrdApprove) {
		return R.status(tbCrdApproveService.updateById(tbCrdApprove));
	}

	/**
	 * 新增或修改 批复信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tbCrdApprove")
	public R submit(@Valid @RequestBody TbCrdApprove tbCrdApprove) {
		return R.status(tbCrdApproveService.saveOrUpdate(tbCrdApprove));
	}


	/**
	 * 删除 批复信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tbCrdApproveService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 详情(客户名称)
	 */
	@GetMapping("/approveDetail")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "详情(客户名称)", notes = "传入approveNum")
	public R<TbCrdApproveVO> approveDetail(@ApiParam(value = "批复编号",required = true) String approveNum) {
		TbCrdApproveVO detail = tbCrdApproveService.selectApproveDetailByCusNum(approveNum);
		return R.data(detail);
	}

	/**
	 * 查询第三方额度台账批复信息（分页）
	 */
	@GetMapping("/findThirdPartyCrdApprovePage")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询第三方额度台账批复信息（分页）", notes = "传入TbCrdApproveVO")
	public R<IPage<TbCrdApproveVO>> findThirdPartyCrdApprovePage(@ApiParam(value = "客户编号", required = true) String customerNum,
																 @ApiParam(value = "经办机构", required = true) String orgNum,
																 @ApiParam(value = "项目协议编号", required = false) String projectNum,Query query) {
		TbCrdApproveVO tbCrdApproveVO = new TbCrdApproveVO();

		/*if(tbCrdApproveVO.getProjectNum()!=null && !"".equals(tbCrdApproveVO.getProjectNum())){
			tbCrdApproveVO.setProjectNum(tbCrdApproveVO.getProjectNum());
		}*/
		if(StringUtils.isBlank(orgNum)){
			orgNum = CommonUtil.getCurrentOrgId();
		}
		tbCrdApproveVO.setProjectNum(projectNum);
		tbCrdApproveVO.setCustomerNum(customerNum);
		tbCrdApproveVO.setOrgNum(orgNum);
		IPage<TbCrdApproveVO> pages = tbCrdApproveService.findThirdPartyCrdApprovePage(Condition.getPage(query),tbCrdApproveVO);
		return R.data(pages);
	}

	/**
	 * 查询批复信息列表
	 */
	@GetMapping("/findListCrdApprove")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "批复信息列表", notes = "传入approveNum")
	public R<IPage<TbCrdApproveVO>> findListCrdApprove(@ApiParam(value = "批复编号", required = true) String approveNum,Query query) {

		IPage<TbCrdApproveVO> pages = tbCrdApproveService.findListCrdApprove(Condition.getPage(query), approveNum);
		return R.data(pages);
	}
}
