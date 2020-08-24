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
package org.git.modules.clm.loan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.loan.vo.CrdDetailVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 业务凭证信息表 控制器
 *
 * @author git
 * @since 2019-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/crdbusicertinfo")
@Api(value = "业务凭证信息表", tags = "业务凭证信息表接口")
public class CrdBusiCertInfoController extends ChainController {

	private ICrdBusiCertInfoService crdBusiCertInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdBusiCertInfo")
	public R<CrdBusiCertInfo> detail(CrdBusiCertInfo crdBusiCertInfo) {
		CrdBusiCertInfo detail = crdBusiCertInfoService.getOne(Condition.getQueryWrapper(crdBusiCertInfo));
		return R.data(detail);
	}

	/**
	 * 分页 业务凭证信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdBusiCertInfo")
	public R<IPage<CrdBusiCertInfo>> list(CrdBusiCertInfo crdBusiCertInfo, Query query) {
		IPage<CrdBusiCertInfo> pages = crdBusiCertInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(crdBusiCertInfo));
		return R.data(pages);
	}

	/**
	 * 自定义分页 业务凭证信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdBusiCertInfo")
	public R<IPage<CrdBusiCertInfoVO>> page(CrdBusiCertInfoVO crdBusiCertInfo, Query query) {
		IPage<CrdBusiCertInfoVO> pages = crdBusiCertInfoService.selectCrdBusiCertInfoPage(Condition.getPage(query), crdBusiCertInfo);
		return R.data(pages);
	}

	/**
	 * 新增 业务凭证信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdBusiCertInfo")
	public R save(@Valid @RequestBody CrdBusiCertInfo crdBusiCertInfo) {
		return R.status(crdBusiCertInfoService.save(crdBusiCertInfo));
	}

	/**
	 * 修改 业务凭证信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdBusiCertInfo")
	public R update(@Valid @RequestBody CrdBusiCertInfo crdBusiCertInfo) {
		return R.status(crdBusiCertInfoService.updateById(crdBusiCertInfo));
	}

	/**
	 * 新增或修改 业务凭证信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdBusiCertInfo")
	public R submit(@Valid @RequestBody CrdBusiCertInfo crdBusiCertInfo) {
		return R.status(crdBusiCertInfoService.saveOrUpdate(crdBusiCertInfo));
	}

	
	/**
	 * 删除 业务凭证信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdBusiCertInfoService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询同业客户额度台账中的业务产品信息
	 * @param query
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/findLedgerCrdBusiCertInfoPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询同业客户额度台账中的业务产品信息", notes = "传入crdBusiCertInfo")
	public R<IPage<CrdBusiCertInfoVO>> findLedgerCrdBusiCertInfoPage(@ApiParam(value = "客户编号", required = true) String customerNum,
																	 @ApiParam(value = "经办机构", required = true) String orgNum,
																	 Query query) {
		IPage<CrdBusiCertInfoVO> pages = crdBusiCertInfoService.findLedgerCrdBusiCertInfoPage(Condition.getPage(query),customerNum,orgNum);
		return R.data(pages);
	}


	/**
	 * 详情(客户名称)
	 */
	@GetMapping("/findCrdBusiCertInfoDetailByCusNum")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "详情(客户名称)", notes = "传入cretInfoId")
	public R<CrdBusiCertInfoVO> findCrdBusiCertInfoDetailByCusNum(@ApiParam(value = "凭证信息id",required = true) String cretInfoId) {
		CrdBusiCertInfoVO detail = crdBusiCertInfoService.findCrdBusiCertInfoDetailByCusNum(cretInfoId);
		return R.data(detail);
	}
}
