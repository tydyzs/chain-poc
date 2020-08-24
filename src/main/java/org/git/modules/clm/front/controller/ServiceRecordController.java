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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.git.core.tool.utils.FileUtil;
import org.git.core.tool.utils.Func;
import org.git.modules.clm.front.constant.TradeConstant;
import org.git.modules.clm.front.dto.jxrcb.ecif.ECIFBankCustomerRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.ecif.ECIFCompanyCustomerRequestDTO;
import org.git.modules.clm.front.entity.ServiceConfig;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.service.*;
import org.git.modules.clm.front.vo.ServiceRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;

/**
 * 前置服务记录表 控制器
 *
 * @author caohaijie
 * @since 2019-09-25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_FRONT_NAME + "/servicerecord")
@Api(value = "前置服务记录表", tags = "前置服务记录表接口")
public class ServiceRecordController extends ChainController {

	private IServiceRecordService serviceRecordService;
	private IServiceConfigService serviceConfigService;
	//	private ITerminalConfigService terminalConfigService;
	private IBMMQService ibmmqService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入serviceRecord")
	public R<ServiceRecord> detail(ServiceRecord serviceRecord) {
		ServiceRecord detail = serviceRecordService.getOne(Condition.getQueryWrapper(serviceRecord));
		return R.data(detail);
	}

	/**
	 * 分页 前置服务记录表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入serviceRecord")
	public R<IPage<ServiceRecord>> list(ServiceRecordVO serviceRecord, Query query) {
		QueryWrapper queryWrapper = Condition.getQueryWrapper(serviceRecord);
		if (serviceRecord.getStartCreateTime() != null && serviceRecord.getEndCreateTime() != null) {
			queryWrapper.between("create_time", serviceRecord.getStartCreateTime(), serviceRecord.getEndCreateTime());
		}
		IPage<ServiceRecord> pages = serviceRecordService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 自定义分页 前置服务记录表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入serviceRecord")
	public R<IPage<ServiceRecordVO>> page(ServiceRecordVO serviceRecord, Query query) {
		IPage<ServiceRecordVO> pages = serviceRecordService.selectServiceRecordPage(Condition.getPage(query), serviceRecord);
		return R.data(pages);
	}

	/**
	 * 新增 前置服务记录表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入serviceRecord")
	public R save(@Valid @RequestBody ServiceRecord serviceRecord) {
		return R.status(serviceRecordService.save(serviceRecord));
	}

	/**
	 * 修改 前置服务记录表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入serviceRecord")
	public R update(@Valid @RequestBody ServiceRecord serviceRecord) {
		return R.status(serviceRecordService.updateById(serviceRecord));
	}

	/**
	 * 新增或修改 前置服务记录表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入serviceRecord")
	public R submit(@Valid @RequestBody ServiceRecord serviceRecord) {
		return R.status(serviceRecordService.saveOrUpdate(serviceRecord));
	}


	/**
	 * 删除 前置服务记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceRecordService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 记录处理重试
	 *
	 * @param serviceRecord
	 * @return
	 * @throws JMSException
	 */
	@PostMapping("/retry")
	public R retryRecord(@RequestBody ServiceRecordVO serviceRecord) {
		String msg = "重试";
		try {
			ServiceConfig serviceConfig = serviceConfigService.getOneByCode(serviceRecord.getServiceCode());//服务配置
			if (serviceConfig == null) {
				return R.fail("【" + serviceRecord.getServiceCode() + "】该服务不存在！");
			}
			if ("requestor".equalsIgnoreCase(serviceConfig.getServiceRole())) {//作为请求方
				Object responseObject = ibmmqService.request(ibmmqService.getRequester().getSendQueue(), ibmmqService.getRequester().getReceiveQueue(), serviceRecord.getRequestMessage());
				return R.data(responseObject);
			} else if ("responder".equalsIgnoreCase(serviceConfig.getServiceRole())) {//作为响应（服务）方
				//重试说明已收到消息，但我们没有处理成功，因此需要再次调用处理程序
				String responseMessage = serviceRecord.getResponseMessage();
				log.warn("即将再次处理的消息报文：" + responseMessage);
				// 执行处理程序
				ServiceHandler serviceHandler = new ServiceHandler();
				Object handleRet = serviceHandler.handleMessage(responseMessage.getBytes());
				log.warn("再次处理的消息报文结果：" + handleRet.toString());
				return R.data(handleRet);
			}

			//更新记录表
			ServiceRecord params = new ServiceRecord();
			params.setId(serviceRecord.getId());
			params.setRetryCount(serviceRecord.getRetryCount() + 1);//重试次数+1
			serviceRecordService.updateById(params);

		} catch (Exception e) {
			e.printStackTrace();
			return R.fail(msg + "失败！" + e.getMessage());
		}
		return R.success(msg + "成功");
	}

	@GetMapping("/sendMessage")
	public R sendMessage(@RequestParam String terminalId, @RequestParam String serviceId, @RequestParam String custNo, String resolveType) {
		ECIFCompanyCustomerRequestDTO request = new ECIFCompanyCustomerRequestDTO();
		request.setCustNo(custNo);
//		request.setCustName("zhangsan");
//		request.setCertNum("420190199009091234");
//		request.setCertType("2");
		request.setResolveType(resolveType);//"2"
		Object response = ibmmqService.request(ibmmqService.getRequester().getSendQueue(), ibmmqService.getRequester().getReceiveQueue(), terminalId, serviceId, request);
		return R.data(response);
	}


	@ApiOperation(value = "处理请求报文", notes = "处理请求本系统的报文，传入报文字符串")
	@PostMapping(value = "/serviceHandleByMsg")
	public R serviceHandleByMsg(String message) {
		message = message.replace("[", "<");
		message = message.replace("]", ">");
		ServiceHandler serviceHandler = new ServiceHandler();
		String handleRet = new String((byte[]) serviceHandler.handleMessage(message.getBytes()));
		return R.data(handleRet);
	}

	@ApiOperation(value = "处理请求报文", notes = "处理请求本系统的报文，传入文件名（含文件路径）")
	@PostMapping(value = "/serviceHandleByFileName")
	public R serviceHandleByFileName(String fileName) {
		//fileName = "src\\test\\java\\xml\\00870000259101.xml";
		String xmlStr = FileUtil.readToString(new File(fileName));
		ServiceHandler serviceHandler = new ServiceHandler();
		String handleRet = new String((byte[]) serviceHandler.handleMessage(xmlStr.getBytes()));
		return R.data(handleRet);
	}

	@ApiOperation(value = "处理请求报文", notes = "处理请求本系统的报文，传入接口码")
	@PostMapping(value = "/serviceHandleByTradeCode")
	public R serviceHandleByTradeode(String tradeCode) {
		//fileName = "src\\test\\java\\xml\\00870000259101.xml";
		String xmlStr = TradeConstant.tradeMap.get(tradeCode);

		ServiceHandler serviceHandler = new ServiceHandler();
		String handleRet = new String((byte[]) serviceHandler.handleMessage(xmlStr.getBytes()));
		return R.data(handleRet);
	}

}
