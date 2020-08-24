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
package org.git.modules.clm.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.front.entity.TerminalConfig;
import org.git.modules.clm.front.service.ActiveMQService;
import org.git.modules.clm.front.service.IBMMQService;
import org.git.modules.clm.front.service.ITerminalConfigService;
import org.git.modules.clm.front.vo.TerminalConfigVO;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.validation.Valid;
import java.util.List;

/**
 * 前置终端配置表 控制器
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_FRONT_NAME + "/terminalconfig")
@Api(value = "前置终端配置表", tags = "前置终端配置表接口")
public class TerminalConfigController extends ChainController {

	private ITerminalConfigService terminalConfigService;

	private IBMMQService ibmmqService;
	private ActiveMQService activeMQService;


	/**
	 * 校验重复
	 */
	@GetMapping("/validateRepeat")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "校验重复", notes = "传入terminalCode")
	public R validateRepeat(@ApiParam(value = "终端代码", required = true) @RequestParam String terminalCode) {
		TerminalConfig query = new TerminalConfig();
		query.setTerminalCode(terminalCode);
		TerminalConfig detail = terminalConfigService.getOne(Condition.getQueryWrapper(query));
		if (detail != null) {
//			return R.fail("");
			return R.data(false);
		}
		return R.data(true);
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入terminalConfig")
	public R<TerminalConfig> detail(TerminalConfig terminalConfig) {
		TerminalConfig detail = terminalConfigService.getOne(Condition.getQueryWrapper(terminalConfig));
		return R.data(detail);
	}

	/**
	 * 分页 前置终端配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入terminalConfig")
	public R<IPage<TerminalConfig>> list(TerminalConfig terminalConfig, Query query) {
		IPage<TerminalConfig> pages = terminalConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(terminalConfig).orderByAsc("terminal_short_name"));
		return R.data(pages);
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<TerminalConfigVO>> tree() {
		List<TerminalConfigVO> tree = terminalConfigService.tree();
		return R.data(tree);
	}

	/**
	 * 自定义分页 前置终端配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入terminalConfig")
	public R<IPage<TerminalConfigVO>> page(TerminalConfigVO terminalConfig, Query query) {
		IPage<TerminalConfigVO> pages = terminalConfigService.selectTerminalConfigPage(Condition.getPage(query), terminalConfig);
		return R.data(pages);
	}

	/**
	 * 新增 前置终端配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入terminalConfig")
	public R save(@Valid @RequestBody TerminalConfig terminalConfig) {
		R r = validateRepeat(terminalConfig.getTerminalCode());
		if (!r.isSuccess()) {
			return r;
		}
		return R.status(terminalConfigService.save(terminalConfig));
	}

	/**
	 * 修改 前置终端配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入terminalConfig")
	public R update(@Valid @RequestBody TerminalConfig terminalConfig) {
		return R.status(terminalConfigService.updateById(terminalConfig));
	}

	/**
	 * 新增或修改 前置终端配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入terminalConfig")
	public R submit(@Valid @RequestBody TerminalConfig terminalConfig) {
		return R.status(terminalConfigService.saveOrUpdate(terminalConfig));
	}


	/**
	 * 删除 前置终端配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(terminalConfigService.deleteLogic(Func.toStrList(ids)));
	}

	/**
	 * 获取来源系统（去重）
	 */
	@GetMapping("/listSystemDistinct")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取来源系统（去重）", notes = "获取来源系统（去重）")
	public R<List<TerminalConfig>> listSystemDistinct() {
		List<TerminalConfig> configList = terminalConfigService.listTerminalConfig();
		return R.data(configList);
	}

	/**
	 * 启动服务
	 */
	@PostMapping("/start")
	public R start() throws JMSException {
		log.info("--准备启动监听，监听队列");
		ibmmqService.startListener();
		return R.success("启动监听队列成功");
	}

	/**
	 * 停止服务
	 */
	@PostMapping("/stop")
	public R stop() {
		log.info("--准备停止监听，监听队列");
		ibmmqService.stopListener();
		return R.success("停止监听队列成功!");
	}

	/**
	 * 启动ActiveMQ服务
	 */
	@PostMapping("/active/mq/start")
	public R startActiveMQ() {
		String error;
		String queueName = ibmmqService.getProvider()[0].getReceiveQueue();//消息通道名称（暂不记录连接地址）
		String replyQueueName = ibmmqService.getProvider()[0].getSendQueue();//响应回复地址

		if (Func.isBlank(queueName)) {
			error = "启动监听失败，接收地址配置为空!";
			log.error(error);
			return R.fail(error);
		}
		if (Func.isBlank(replyQueueName)) {
			error = "启动监听失败，回复地址配置为空!";
			log.error(error);
			return R.fail(error);
		}
		if (activeMQService.isStarted(queueName)) {
			error = "【" + queueName + "】该消息队列已监听！";
			boolean test = activeMQService.testConnected();
			String testResult = test ? "" : "，但测试连接地址 " + activeMQService.getUrl() + " 失败！";
			log.warn(error + testResult);
			return R.fail(error + testResult);
		}
		log.info("--准备启动监听，监听队列" + queueName);
		activeMQService.startListenerContainer(queueName, replyQueueName);
		boolean test = activeMQService.testConnected();
		String testResult = test ? "" : "，但测试连接地址 " + activeMQService.getUrl() + " 失败！";
		log.info("--成功启动监听，监听队列" + queueName);
		return R.success("启动监听队列【" + queueName + "】成功" + testResult);
	}

	/**
	 * 停止ActiveMQ服务
	 */
	@PostMapping("/active/mq/stop")
	public R stopActiveMQ() {
		if (activeMQService.isStarted()) {
			activeMQService.stop();
		} else {
			return R.fail("服务没有启动！");
		}

		return R.success("停止成功!");
	}


}
