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
package org.git.flow.business.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.AllArgsConstructor;
import org.git.common.cache.UserCache;
import org.git.common.constant.AppConstant;
import org.git.core.tool.api.R;
import org.git.flow.business.entity.FlowBiz;
import org.git.flow.business.service.IBizService;
import org.git.flow.business.service.impl.BizServiceImpl;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 业务数据控制器
 *
 * @author caohaijie
 */
@ApiIgnore
@RestController
@RequestMapping(AppConstant.APPLICATION_FLOW_NAME + "/process/biz")
@AllArgsConstructor
public class BizController {

	private IBizService bizService;

	/**
	 * 业务详情
	 *
	 * @param businessId 业务主键
	 */
	@GetMapping("detail")
	public R<FlowBiz> detail(String businessId) {
		FlowBiz detail = bizService.getById(businessId);
		if (detail != null) {
			detail.getFlow().setAssigneeName(UserCache.getUser(detail.getCreateUser()).getName());
		}
		return R.data(detail);

	}


	/**
	 * 新增或修改
	 *
	 * @param flowBiz 业务信息
	 */
	@PostMapping("start-process")
	public R startProcess(@RequestBody FlowBiz flowBiz) {
		return R.status(bizService.startProcess(flowBiz));
	}

}
