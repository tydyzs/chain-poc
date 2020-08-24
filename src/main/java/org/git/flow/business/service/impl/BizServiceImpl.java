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
package org.git.flow.business.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.base.BaseServiceImpl;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.support.Kv;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.Func;
import org.git.flow.business.entity.FlowBiz;
import org.git.flow.business.mapper.BizMapper;
import org.git.flow.business.service.IBizService;
import org.git.flow.business.service.IFlowService;
import org.git.flow.core.constant.ProcessConstant;
import org.git.flow.core.entity.ChainFlow;
import org.git.flow.core.utils.FlowUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class BizServiceImpl extends BaseServiceImpl<BizMapper, FlowBiz> implements IBizService {

	private IFlowService flowService;
	private RepositoryService repositoryService;
//	private ProcessDefinitionEntityManager processDefinitionEntityManager;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean startProcess(FlowBiz flowBiz) {
		String businessTable = FlowUtil.getBusinessTable(ProcessConstant.COMMON_KEY);
//		ProcessDefinitionEntity processDefinitionEntity = processDefinitionEntityManager.findLatestProcessDefinitionByKey(flowBiz.getProcessDefinitionKey());
//		String id1 = processDefinitionEntity.getId();
		String key = flowBiz.getProcessDefinitionKey();
		String id = flowBiz.getProcessDefinitionId();
		if (Func.isEmpty(id)) {
			List<ProcessDefinition> definitionsList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().list();
			if (definitionsList.size() > 0) {
				id = definitionsList.get(0).getId();
				flowBiz.setProcessDefinitionId(id);
			} else {
				throw new ServiceException(key + "：该流程定义没有找到！");
			}
		}
		System.out.println("将要发起的流程定义ID:" + id);
		if (Func.isEmpty(flowBiz.getId())) {
			// 保存leave
			flowBiz.setApplyTime(DateUtil.now());
			save(flowBiz);
			// 启动流程
			Kv variables = Kv.create()
				.set(ProcessConstant.TASK_VARIABLE_CREATE_USER, SecureUtil.getUserName())
				.set(ProcessConstant.TASK_VARIABLE_START_USER, SecureUtil.getUserId())
//				.set(ProcessConstant.TASK_VARIABLE_TASK_USER, TaskUtil.getTaskUser(flowBiz.getTaskUser()))
				.set(ProcessConstant.TASK_VARIABLE_TASK_USER, SecureUtil.getUserId())
				.set("amount", flowBiz.getAmount());
			ChainFlow flow = flowService.startProcessInstanceById(id, FlowUtil.getBusinessKey(businessTable, String.valueOf(flowBiz.getId())), variables);
			if (Func.isNotEmpty(flow)) {
				log.debug("流程已启动,流程ID:" + flow.getProcessInstanceId());
				// 返回流程id写入leave
				flowBiz.setProcessInstanceId(flow.getProcessInstanceId());
				updateById(flowBiz);
			} else {
				throw new ServiceException("开启流程失败");
			}
		} else {

			updateById(flowBiz);
		}
		return true;
	}

}
