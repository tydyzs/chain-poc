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
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.vo.CrdDetailVO;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 额度明细表（客户+三级额度产品+机构） 控制器
 *
 * @author git
 * @since 2019-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("git-demo/crdDetail")
@Api(value = "额度明细表（客户+三级额度产品+机构）", tags = "额度明细表（客户+三级额度产品+机构）接口")
public class CrdDetailController extends ChainController {

	private ICrdDetailService crdDetailService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入crdDetail")
	public R<CrdDetail> detail(CrdDetail crdDetail) {
		CrdDetail detail = crdDetailService.getOne(Condition.getQueryWrapper(crdDetail));
		return R.data(detail);
	}

	/**
	 * 分页 额度明细表（客户+三级额度产品+机构）
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入crdDetail")
	public R<IPage<CrdDetail>> list(CrdDetail crdDetail, Query query) {
		IPage<CrdDetail> pages = crdDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(crdDetail));
		return R.data(pages);
	}

	/**
	 * 自定义分页 额度明细表（客户+三级额度产品+机构）
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入crdDetail")
	public R<IPage<CrdDetailVO>> page(CrdDetailVO crdDetail, Query query) {
		IPage<CrdDetailVO> pages = crdDetailService.selectCrdDetailPage(Condition.getPage(query), crdDetail);
		return R.data(pages);
	}

	/**
	 * 新增 额度明细表（客户+三级额度产品+机构）
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入crdDetail")
	public R save(@Valid @RequestBody CrdDetail crdDetail) {
		return R.status(crdDetailService.save(crdDetail));
	}

	/**
	 * 修改 额度明细表（客户+三级额度产品+机构）
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入crdDetail")
	public R update(@Valid @RequestBody CrdDetail crdDetail) {
		return R.status(crdDetailService.updateById(crdDetail));
	}

	/**
	 * 新增或修改 额度明细表（客户+三级额度产品+机构）
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入crdDetail")
	public R submit(@Valid @RequestBody CrdDetail crdDetail) {
		return R.status(crdDetailService.saveOrUpdate(crdDetail));
	}


	/**
	 * 删除 额度明细表（客户+三级额度产品+机构）
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(crdDetailService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 自定义分页 额度明细表（客户+三级额度产品+机构）
	 */
	@GetMapping("/findCrdDetailPage")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "分页", notes = "传入crdDetail")
	public R<IPage<CrdDetailVO>> findCrdDetailPage(CrdDetailVO crdDetailVO, Query query) {
		IPage<CrdDetailVO> pages = crdDetailService.findCrdDetailPage(Condition.getPage(query), crdDetailVO);
		return R.data(pages);
	}

	/**
	 * 通过二级额度产品编号查询额度明细
	 *
	 * @param crdMainNum
	 * @param customerNum
	 * @param orgNum
	 * @param query
	 * @return
	 */
	@GetMapping("/findCrdDetailFromCrdMainNum")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "通过二级额度编号查询额度明细", notes = "传入二级额度产品编号crdMainNum")
	public R<IPage<CrdDetailVO>> findCrdDetailFromCrdMainNum(String crdMainNum,
															 @ApiParam(required = true) String customerNum,
															 @ApiParam(required = true) String orgNum,
															 Query query) {
		IPage<CrdDetailVO> pages = crdDetailService.selectCrdDetailFromCrdMainNum(Condition.getPage(query), customerNum, orgNum, crdMainNum);
		return R.data(pages);
	}

	/**
	 * 通过二级额度产品编号查询额度明细
	 * 担保台账：担保额度明细
	 * @param query
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@GetMapping("/getGuaranteeCrdDetail")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "通过二级额度编号查询额度明细", notes = "传入二级额度产品编号crdMainNum")
	public R<IPage<CrdDetailVO>> getGuaranteeCrdDetail(@ApiParam(required = true) String customerNum,
															 @ApiParam(required = true) String orgNum,
															 Query query) {
		IPage<CrdDetailVO> pages = crdDetailService.selectGuaranteeCrdDetail(Condition.getPage(query), customerNum, orgNum);
		return R.data(pages);
	}

	/**
	 * 查询第三方额度台账额度明细信息
	 * @param customerNum
	 * @param orgNum
	 * @param query
	 * @return
	 */
	@GetMapping("/findThirdPartyCrdDetailPage")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询第三方额度台账额度明细信息", notes = "传入crdDetailVO")
	public R<IPage<CrdDetailVO>> findThirdPartyCrdDetailPage(@ApiParam(value = "客户编号",required = true) String customerNum,
															 @ApiParam(value = "经办机构",required = true) String orgNum, Query query) {
		IPage<CrdDetailVO> pages = crdDetailService.findThirdPartyCrdDetailPage(Condition.getPage(query),customerNum,orgNum);
		return R.data(pages);
	}
}
