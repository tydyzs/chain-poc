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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.flowable.engine.FormService;
import org.flowable.engine.TaskService;
import org.git.common.constant.AppConstant;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.git.core.tool.support.Kv;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.Func;
import org.git.flow.business.entity.FlowBiz;
import org.git.flow.business.service.FlowBusinessService;
import org.git.flow.business.service.IFlowService;
import org.git.flow.core.constant.ProcessConstant;
import org.git.flow.core.entity.ChainFlow;
import org.git.flow.core.utils.FlowUtil;
import org.git.flow.core.utils.TaskUtil;
import org.git.flow.demo.leave.entity.ProcessLeave;
import org.git.flow.demo.leave.service.ILeaveService;
import org.git.flow.engine.entity.FlowProcess;
import org.git.flow.engine.service.FlowEngineService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 流程事务通用接口
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_FLOW_NAME + "/work")
@Api(value = "流程事务通用接口", tags = "流程事务通用接口")
public class WorkController {

	private TaskService taskService;
	private FlowEngineService flowEngineService;
	private FlowBusinessService flowBusinessService;


	/**
	 * 发起事务列表页
	 */
	@GetMapping("start-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发起事务列表页", notes = "传入流程类型")
	public R<IPage<FlowProcess>> startList(@ApiParam("流程类型") String category, Query query) {
		IPage<FlowProcess> pages = flowEngineService.selectProcessPage(Condition.getPage(query), category);
		return R.data(pages);
	}

	/**
	 * 待签事务列表页
	 */
	@GetMapping("claim-list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "待签事务列表页", notes = "传入流程信息")
	public R<IPage<ChainFlow>> claimList(@ApiParam("流程信息") ChainFlow chainFlow, Query query) {
		IPage<ChainFlow> pages = flowBusinessService.selectClaimPage(Condition.getPage(query), chainFlow);
		return R.data(pages);
	}

	/**
	 * 待办事务列表页
	 */
	@GetMapping("todo-list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "待办事务列表页", notes = "传入流程信息")
	public R<IPage<ChainFlow>> todoList(@ApiParam("流程信息") ChainFlow chainFlow, Query query) {
		IPage<ChainFlow> pages = flowBusinessService.selectTodoPage(Condition.getPage(query), chainFlow);
		return R.data(pages);
	}

	/**
	 * 已发事务列表页
	 */
	@GetMapping("send-list")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "已发事务列表页", notes = "传入流程信息")
	public R<IPage<ChainFlow>> sendList(@ApiParam("流程信息") ChainFlow chainFlow, Query query) {
		IPage<ChainFlow> pages = flowBusinessService.selectSendPage(Condition.getPage(query), chainFlow);
		return R.data(pages);
	}

	/**
	 * 办结事务列表页
	 */
	@GetMapping("done-list")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "办结事务列表页", notes = "传入流程信息")
	public R<IPage<ChainFlow>> doneList(@ApiParam("流程信息") ChainFlow chainFlow, Query query) {
		IPage<ChainFlow> pages = flowBusinessService.selectDonePage(Condition.getPage(query), chainFlow);
		return R.data(pages);
	}

	/**
	 * 签收事务
	 *
	 * @param taskId 任务id
	 */
	@PostMapping("claim-task")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "签收事务", notes = "传入流程信息")
	public R claimTask(@ApiParam("任务id") String taskId) {
		taskService.claim(taskId, TaskUtil.getTaskUser());
		return R.success("签收事务成功");
	}

	/**
	 * 完成任务
	 *
	 * @param flow 请假信息
	 */
	@PostMapping("complete-task")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "完成任务", notes = "传入流程信息")
	public R completeTask(@ApiParam("任务信息") @RequestBody ChainFlow flow) {
		return R.status(flowBusinessService.completeTask(flow));
	}

	/**
	 * 删除任务
	 *
	 * @param taskId 任务id
	 * @param reason 删除原因
	 */
	@PostMapping("delete-task")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除任务", notes = "传入流程信息")
	public R deleteTask(@ApiParam("任务id") String taskId, @ApiParam("删除原因") String reason) {
		taskService.deleteTask(taskId, reason);
		return R.success("删除任务成功");
	}

}
