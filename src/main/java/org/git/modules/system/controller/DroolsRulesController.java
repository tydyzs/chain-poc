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
package org.git.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.git.common.constant.AppConstant;
import org.git.core.boot.ctrl.ChainController;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.utils.Func;
import org.git.modules.drools.annotation.Drools;
import org.git.modules.drools.annotation.DroolsField;
import org.git.modules.drools.annotation.DroolsMethod;
import org.git.modules.drools.common.ClassUtils;
import org.git.modules.drools.common.MyApplicationListener;
import org.git.modules.drools.entity.CreditApproveParam;
import org.git.modules.drools.entity.QueryParam;
import org.git.modules.drools.entity.RuleResult;
import org.git.modules.drools.service.RuleEngineService;
import org.git.modules.system.entity.DroolsConstant;
import org.git.modules.system.entity.DroolsDetail;
import org.git.modules.system.entity.DroolsRules;
import org.git.modules.system.entity.Role;
import org.git.modules.system.service.IDroolsConstantService;
import org.git.modules.system.service.IDroolsDetailService;
import org.git.modules.system.service.IDroolsRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/rules")
@Api(value = "参数管理", tags = "参数管理")
public class DroolsRulesController extends ChainController {

	private IDroolsRulesService droolsRulesService;

	@Resource
	private RuleEngineService ruleEngineService ;

	private IDroolsDetailService droolsDetailService;

	private IDroolsConstantService droolsConstantService;

	@Autowired
	private MyApplicationListener myApplicationListener;
	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入param")
	public R<DroolsRules> detail(DroolsRules  param) {
		DroolsRules detail = droolsRulesService.getOne(Condition.getQueryWrapper(param));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paramName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramKey", value = "参数键名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramValue", value = "参数键值", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入param")
	public R<IPage<DroolsRules>> list(DroolsRules rules, Query query) {
		QueryWrapper<DroolsRules> qw =Condition.getQueryWrapper(rules);
		IPage<DroolsRules> pages = droolsRulesService.getDroolsRulesPage(Condition.getPage(query),rules);
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入param")
	public R submit(@Valid @RequestBody DroolsRules param) {
		if(StringUtils.isBlank(param.getId())){
			DroolsRules dr =  droolsRulesService.getDroolsRulesByCode(param.getCode());
			if(dr != null &&  StringUtils.isNotBlank(dr.getId() )){
				throw new ServiceException("当前业务已定义业务规则！");
			}
		}
		return R.status(droolsRulesService.saveOrUpdate(param));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(droolsRulesService.removeByIds(Func.toStrList(ids)));
	}


	@PostMapping("/getDroolConstantByRuleId")
	@ApiOperation(value = "获取规则常量定义", notes = "取规则常量定义")
	public R getDroolConstantByRuleId (DroolsConstant droolsConstant){

			QueryWrapper<DroolsConstant> queryWrapper = Condition.getQueryWrapper(droolsConstant);
			List<DroolsConstant> list = droolsConstantService.list(queryWrapper.lambda().eq(DroolsConstant::getRuleId, droolsConstant.getRuleId()));
			return R.data(list);
	}


	@PostMapping("/getDroolConstantById")
	@ApiOperation(value = "通过ID获取规则常量定义", notes = "通过ID获取规则常量定义")
	public R getDroolConstantById (String id){
		 DroolsConstant droolsConstant = droolsConstantService.getById(id);
		 return R.data(droolsConstant);
	}


	@PostMapping("/saveDroolConstant")
	@ApiOperation(value = "保存规则常量定义", notes = "保存规则常量定义")
	public R saveDroolConstant (@RequestBody DroolsConstant droolsConstant){
		droolsConstant.setIsDeleted(0);
		boolean state = droolsConstantService.saveOrUpdate(droolsConstant);
		return R.data(state);
	}

	@PostMapping("/removeDroolConstant")
	@ApiOperation(value = "删除规则常量定义", notes = "删除规则常量定义")
	public R removeDroolConstant (String id){

		boolean state = droolsConstantService.removeById(id);
		return R.data(state);
	}

	@PostMapping("/getDroolDetailById")
	@ApiOperation(value = "删除规则定义明细", notes = "删除规则定义明细")
	public R getDroolDetailById (String id){
		DroolsDetail droolsDetail = droolsDetailService.getById(id);
		return R.data(droolsDetail);
	}

	@PostMapping("/saveDroolDetail")
	@ApiOperation(value = "保存规则定义明细", notes = "保存规则定义明细")
	public R saveDroolDetail (@RequestBody DroolsDetail droolsDetail) throws UnsupportedEncodingException {
		droolsDetail.setIsDeleted(0);
		droolsDetail.setConditionCn(URLDecoder.decode(droolsDetail.getConditionCn(),"UTF-8"));
		droolsDetail.setRuleCondition(URLDecoder.decode(droolsDetail.getRuleCondition(),"UTF-8"));
		boolean state = droolsDetailService.saveOrUpdate(droolsDetail);
		return R.data(state);
	}

	@PostMapping("/removeDroolDetail")
	@ApiOperation(value = "删除规则定义明细", notes = "删除规则定义明细")
	public R removeDroolDetail (String id){

		boolean state = droolsDetailService.removeById(id);
		return R.data(state);
	}


	@PostMapping("/getDroolDetailByRuleId")
	@ApiOperation(value = "获取规则常量定义", notes = "取规则常量定义")
	public R getDroolDetailByRuleId (DroolsDetail droolsDetail){

		QueryWrapper<DroolsDetail> queryWrapper = Condition.getQueryWrapper(droolsDetail);
		List<DroolsDetail> list = droolsDetailService.list(queryWrapper.lambda().eq(DroolsDetail::getRuleId, droolsDetail.getRuleId()).orderByDesc(DroolsDetail::getRulePriority));
		return R.data(list);

	}


	@PostMapping("/createRules")
	@ApiOperation(value = "获取规则常量定义", notes = "取规则常量定义")
	public R createRules (String id){

		DroolsRules droolsRules =droolsRulesService.createRuleDrl(id);

		return R.data(droolsRules);
	}


	@PostMapping("/test")
	@ApiOperation(value = "测试", notes = "测试")
	public R test (@RequestBody QueryParam queryParam, @RequestBody CreditApproveParam creditApproveParam) throws IOException {
		try {
			if(queryParam.getCode().equals("rule003")){
				ruleEngineService.executeRules(creditApproveParam);
				return  R.data(creditApproveParam.getResult());
			}else{
				ruleEngineService.executeRules(queryParam);
				return  R.data(queryParam.getResult());
			}
		} catch (IOException e) {
			throw e;
		}
	}





	@PostMapping("/droolsBusinessObject")
	@ApiOperation(value = "获取定义的规则对象", notes = "获取定义的规则对象")
	public  R<List> droolsBusinessObject (){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,Class<?>> clazzs =  myApplicationListener.getClassCache();
		//List<Class<?>> clazzs = ClassUtils.getClasssFromPackage("org.git.modules.drools.entity", Drools.class);
		System.out.println("********************************");
		System.out.println(clazzs.size());
		System.out.println("********************************");
		for (Class cls : clazzs.values()){
			Map map  = new HashMap();
			Drools drools = (Drools)cls.getAnnotation(Drools.class);
			if(drools == null){
				continue;
			}
			list.add(map);
			map.put("name",drools.name());
			map.put("code",drools.code());
			map.put("explain",drools.explain());
			map.put("classPath",cls.getName());
			map.put("className",cls.getSimpleName());
			List<Map<String,String>> fieldList = new ArrayList<>();
			map.put("fields",fieldList);
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields ) {
				Map<String,String> fieldMap  = new HashMap<String,String>();
				DroolsField df = field.getAnnotation(DroolsField.class);
				if(df!=null){
					fieldMap.put("field",field.getName());
					fieldMap.put("type",field.getType().getName());
					fieldMap.put("name",df.name());
					fieldMap.put("explain",df.explain());
					fieldList.add(fieldMap);
				}
			}

			List<Map<String,String>> methodList = new ArrayList<>();
			map.put("busiActions",methodList);
			Method[] methods = cls.getDeclaredMethods();
			for (Method mths : methods ) {
				Map<String,String> methodMap  = new HashMap<String,String>();
				DroolsMethod df = mths.getAnnotation(DroolsMethod.class);
				if(df!=null) {
					methodMap.put("method", mths.getName());
					methodMap.put("name", df.name());
					methodMap.put("explain", df.explain());
					methodList.add(methodMap);
				}
			}
		}
		return R.data(list);
	}

}
