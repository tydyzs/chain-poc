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
package org.git.modules.clm.rcm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.*;
import org.git.modules.clm.rcm.mapper.RcmQuotaManagerMapper;
import org.git.modules.clm.rcm.service.*;
import org.git.modules.clm.rcm.vo.*;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.support.Condition;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 服务实现类
 *
 * @author liuye
 * @since 2019-10-28
 */
@Service
public class RcmConfigManagerServiceImpl extends ServiceImpl<RcmQuotaManagerMapper, RcmConfig> implements IRcmConfigManagerService {

	@Autowired
	private IRcmIndexFullService rcmIndexFullService;
	@Autowired
	private IRcmConfigParaService rcmConfigParaService;
	@Autowired
	private IRcmConfigService rcmConfigService;
	@Autowired
	private IRcmConfigHisService rcmConfigHisService;
	@Autowired
	private IRcmConfigParaHisService rcmConfigParaHisService;
	@Autowired
	private IRcmWarnInfoService rcmWarnInfoService;

	@Override
	public IPage<RcmQuotaManagerVO> selectRcmConfigurationManager(IPage<RcmQuotaManagerVO> page, RcmQuotaManagerVO rcmConfigurationManager) {
		return page.setRecords(baseMapper.selectRcmConfigurationManagerPage(page, rcmConfigurationManager));
	}

	@Override
	public RcmQuotaManagerVO detail(RcmQuotaManagerVO rcmConfigurationManager) {
		RcmQuotaManagerVO detail = new RcmQuotaManagerVO();
		//查询限额详细信息表
		RcmConfig rcmConfigurationInfo = rcmConfigService.getOne(Condition.getQueryWrapper(rcmConfigurationManager));
		if (rcmConfigurationInfo == null) {
			return null;
		}
		//查询限额指标配置
		RcmIndexFullVO rcmIndexFullVO = rcmIndexFullService.selectRcmConfigDetail(rcmConfigurationInfo.getQuotaIndexNum());
		if (rcmIndexFullVO != null) {
			detail.setQuotaIndexName(rcmIndexFullVO.getQuotaIndexName());
			detail.setQuotaIndexType(rcmIndexFullVO.getQuotaIndexType());
		}
		//查询限额参数配置信息表
		List<RcmConfigParaVO> rcmConfigurationParas = rcmConfigParaService.selectOneRcmControl(rcmConfigurationManager.getQuotaNum());
		BeanUtils.copyProperties(rcmConfigurationInfo, detail);
		detail.setRcmIndex(rcmIndexFullVO);
		for (RcmConfigParaVO rcmConfigParaVO : rcmConfigurationParas) {
			String quotaLevel = rcmConfigParaVO.getQuotaLevel();
			if (RcmConstant.QUOTA_LEVEL_OBSERVE.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setObserveValue(rcmConfigParaVO.getQuotaControlAmt());
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setObserveValue(rcmConfigParaVO.getQuotaControlRatio());
				}

				detail.setObserveNode(rcmConfigParaVO.getControlNode());
				detail.setObserveNodeMeasure(rcmConfigParaVO.getNodeMeasure());
				detail.setObserveValueType(rcmConfigParaVO.getQuotaControlType());
			} else if (RcmConstant.QUOTA_LEVEL_WARNING.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setWarnValue(rcmConfigParaVO.getQuotaControlAmt());
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setWarnValue(rcmConfigParaVO.getQuotaControlRatio());
				}
				detail.setWarnNode(rcmConfigParaVO.getControlNode());
				detail.setWarnNodeMeasure(rcmConfigParaVO.getNodeMeasure());
				detail.setWarnValueType(rcmConfigParaVO.getQuotaControlType());
			} else if (RcmConstant.QUOTA_LEVEL_CONTROLLER.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setControlValue(rcmConfigParaVO.getQuotaControlAmt());
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigParaVO.getQuotaControlType())) {
					detail.setControlValue(rcmConfigParaVO.getQuotaControlRatio());
				}
				detail.setControlNode(rcmConfigParaVO.getControlNode());
				detail.setControlNodeMeasure(rcmConfigParaVO.getNodeMeasure());
				detail.setControlValueType(rcmConfigParaVO.getQuotaControlType());
			}
		}
		if (StringUtils.isNotBlank(detail.getUseOrgNum())) {
			detail.setUseOrgName(SysCache.getDeptName(detail.getUseOrgNum()));
		}
		if (detail != null) {
			if (StringUtil.isNotBlank(detail.getUserNum())) {
				detail.setUserName(UserCache.getUser(detail.getUserNum()).getName());
			}
			if (StringUtil.isNotBlank(detail.getOrgNum())) {
				detail.setOrgName(SysCache.getDeptName(detail.getOrgNum()));
			}
		}
		return detail;
	}

	@Override
	@Transactional
	public boolean addRcmConfigInfoAndPara(RcmQuotaManagerVO rcmConfigurationManager) {
		//同一个限额指标不能被同一个机构多次配置
		QueryWrapper<RcmConfig> queryWrapper = new QueryWrapper();
		queryWrapper.ne("quota_state", RcmConstant.QUOTA_STATE_CANCEL);
		queryWrapper.eq("use_org_num", rcmConfigurationManager.getUseOrgNum());
		queryWrapper.eq("quota_index_num", rcmConfigurationManager.getQuotaIndexNum());
		int count = rcmConfigService.count(queryWrapper);
		if (count >= 1) {
			throw new ServiceException("此机构已配置过此限额指标");
		}

		QueryWrapper<RcmConfig> queryWrapper2 = new QueryWrapper();
		queryWrapper2.ne("quota_num", rcmConfigurationManager.getQuotaNum());
		queryWrapper2.ne("quota_state", RcmConstant.QUOTA_STATE_CANCEL);
		queryWrapper2.eq("use_org_num", rcmConfigurationManager.getUseOrgNum());
		queryWrapper2.eq("quota_name", rcmConfigurationManager.getQuotaIndexName());
		int count2 = rcmConfigService.count(queryWrapper2);
		if (count2 >= 1) {
			throw new ServiceException("此机构已配置过同名限额");
		}

		Date modifyTime = new Date(CommonUtil.getWorkDateTime().getTime());
		String modUserNum = SecureUtil.getUser().getUserId();
		String modUserOrgNum = SecureUtil.getUser().getDeptId();
		String quotaNum = BizNumUtil.getBizNumWithDate(RcmConstant.RCM_CONFIGURATION_INFO_TYPE);
		rcmConfigurationManager.setQuotaState(RcmConstant.QUOTA_STATE_INVALID);
		rcmConfigurationManager.setQuotaNum(quotaNum);
		rcmConfigurationManager.setUserNum(modUserNum);
		rcmConfigurationManager.setOrgNum(modUserOrgNum);
		rcmConfigurationManager.setCreateTime(modifyTime);
		rcmConfigurationManager.setUpdateTime(modifyTime);
		RcmConfig rcmConfigurationInfo = new RcmConfig();
		BeanUtils.copyProperties(rcmConfigurationManager, rcmConfigurationInfo);
		//保存限额详细信息表
		rcmConfigService.save(rcmConfigurationInfo);

		List<RcmConfigPara> rcmConfigurationParas = new ArrayList<>();
		//添加控制值阈值
		List<String> controllerNodes = Func.toStrList(rcmConfigurationManager.getControlNode());
		addList(controllerNodes, RcmConstant.QUOTA_LEVEL_CONTROLLER, rcmConfigurationManager, rcmConfigurationParas);
		//添加预警值阈值
		List<String> warningNodes = Func.toStrList(rcmConfigurationManager.getWarnNode());
		addList(warningNodes, RcmConstant.QUOTA_LEVEL_WARNING, rcmConfigurationManager, rcmConfigurationParas);
		//添加观察值阈值
		List<String> observeNodes = Func.toStrList(rcmConfigurationManager.getObserveNode());
		addList(observeNodes, RcmConstant.QUOTA_LEVEL_OBSERVE, rcmConfigurationManager, rcmConfigurationParas);
		if (rcmConfigurationParas == null || rcmConfigurationParas.size() == 0) {
			return true;
		}
		//限额参数配置信息保存
		return rcmConfigParaService.addRcmControl(rcmConfigurationParas);
	}

	@Override
	@Transactional
	public boolean updateRcmConfigInfoAndPara(RcmQuotaManagerVO rcmQuotaManagerVO) {
		//已逻辑删除的不能修改
		RcmConfig info = rcmConfigService.getById(rcmQuotaManagerVO.getQuotaNum());
		if (RcmConstant.QUOTA_STATE_CANCEL.equals(info.getQuotaState())) {
			throw new ServiceException("已取消的数据不能进行修改");
		}
		QueryWrapper<RcmConfig> queryWrapper2 = new QueryWrapper<RcmConfig>();
		queryWrapper2.ne("quota_num", rcmQuotaManagerVO.getQuotaNum());
		queryWrapper2.ne("quota_state", RcmConstant.QUOTA_STATE_CANCEL);
		queryWrapper2.eq("use_org_num", rcmQuotaManagerVO.getUseOrgNum());
		queryWrapper2.eq("quota_name", rcmQuotaManagerVO.getQuotaIndexName());
		int count2 = rcmConfigService.count(queryWrapper2);
		if (count2 >= 1) {
			throw new ServiceException("此机构已配置过同名限额");
		}

		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Date modifyTime = DateUtil.now();
		String modUserNum = SecureUtil.getUser().getUserId();
		String modUserOrgNum = SecureUtil.getUser().getDeptId();
		rcmQuotaManagerVO.setUserNum(modUserNum);
		rcmQuotaManagerVO.setOrgNum(modUserOrgNum);
		rcmQuotaManagerVO.setUpdateTime(modifyTime);

		RcmConfig rcmConfigurationInfo = new RcmConfig();
		BeanUtils.copyProperties(rcmQuotaManagerVO, rcmConfigurationInfo);
		RcmConfigHis rcmConfigHis = new RcmConfigHis();
		BeanUtils.copyProperties(rcmQuotaManagerVO, rcmConfigHis);
		rcmConfigHis.setHisId(id);
		//注意操作顺序（外键约束）
		//将限额详细信息表数据移动至历史表
		rcmConfigHisService.moveToHis(rcmConfigHis);
		//修改限额详细信息表
		rcmConfigService.updateRcmConfigNameAndState(rcmConfigurationInfo);
		//将限额参数配置信息表数据移动至历史表
		RcmConfigParaHis rcmConfigParaHis = new RcmConfigParaHis();
		rcmConfigParaHis.setUserNum(modUserNum);
		rcmConfigParaHis.setOrgNum(modUserOrgNum);
		rcmConfigParaHis.setCreateTime(modifyTime);
		rcmConfigParaHis.setUpdateTime(modifyTime);
		rcmConfigParaHis.setMainHisId(id);
		rcmConfigParaHis.setQuotaNum(rcmQuotaManagerVO.getQuotaNum());

		rcmConfigParaHisService.moveToHis(rcmConfigParaHis);

		//删除限额参数配置信息表数据
		Map<String, Object> params = new HashMap<>();
		params.put("quota_num", rcmQuotaManagerVO.getQuotaNum());
		rcmConfigParaService.removeByMap(params);

		List<RcmConfigPara> rcmConfigurationParas = new ArrayList<>();
		//添加控制值阈值
		List<String> controllerNodes = Func.toStrList(rcmQuotaManagerVO.getControlNode());
		addList(controllerNodes, RcmConstant.QUOTA_LEVEL_CONTROLLER, rcmQuotaManagerVO, rcmConfigurationParas);
		//添加预警值阈值
		List<String> warningNodes = Func.toStrList(rcmQuotaManagerVO.getWarnNode());
		addList(warningNodes, RcmConstant.QUOTA_LEVEL_WARNING, rcmQuotaManagerVO, rcmConfigurationParas);
		//添加观察值阈值
		List<String> observeNodes = Func.toStrList(rcmQuotaManagerVO.getObserveNode());
		addList(observeNodes, RcmConstant.QUOTA_LEVEL_OBSERVE, rcmQuotaManagerVO, rcmConfigurationParas);
		if (rcmConfigurationParas == null || rcmConfigurationParas.size() == 0) {
			return true;
		}
		//限额参数配置信息保存
		return rcmConfigParaService.addRcmControl(rcmConfigurationParas);
	}

	@Override
	public RcmQuotaManagerHisVO hisList(IPage<RcmConfigParaHis> page, String quatoNum) {
		RcmQuotaManagerHisVO hisList = new RcmQuotaManagerHisVO();
		/*//查询限额详细信息表
		RcmConfig rcmConfigurationInfo = rcmConfigService.getById(quatoNum);
		if (rcmConfigurationInfo == null) {
			return null;
		}
		BeanUtils.copyProperties(rcmConfigurationInfo, hisList);
		//查询限额参数配置信息表
		List<RcmConfigParaVO> rcmConfigurationParas = rcmConfigParaService.selectOneRcmControl(quatoNum);
		hisList.setRcmConfigPara(rcmConfigurationParas);
		if (rcmConfigurationParas != null && !rcmConfigurationParas.isEmpty()) {
			for (RcmConfigParaVO rcmConfigurationParaVO : rcmConfigurationParas) {
				String quotaLevel = rcmConfigurationParaVO.getQuotaLevel();
				if (RcmConstant.QUOTA_LEVEL_OBSERVE.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlRatio());
					}
				} else if (RcmConstant.QUOTA_LEVEL_WARNING.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlRatio());
					}
				} else if (RcmConstant.QUOTA_LEVEL_CONTROLLER.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationParaVO.getQuotaControlType())) {
						rcmConfigurationParaVO.setAmtOrRatio(rcmConfigurationParaVO.getQuotaControlRatio());
					}
				}
			}
		}*/
		return hisList;
	}

	@Override
	@Transactional
	public boolean removeById(String quatoNum) {
		int count = rcmWarnInfoService.countByQuotaNum(quatoNum);
		if (count > 0) {
			throw new ServiceException("已有限额预警数据，此配置无法删除！");
		}
		//删除操作
		RcmConfigHis rcmConfigurationInfoHis = new RcmConfigHis();
		rcmConfigurationInfoHis.setQuotaNum(quatoNum);
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		rcmConfigurationInfoHis.setHisId(id);
		//移动限额参数配置信息表数据到历史表
		rcmConfigHisService.moveToHis(rcmConfigurationInfoHis);
		RcmConfigParaHis rcmConfigParaHis = new RcmConfigParaHis();
		rcmConfigParaHis.setQuotaNum(quatoNum);
		rcmConfigParaHis.setMainHisId(id);
		//移动限额详细信息表数据到历史表
		rcmConfigParaHisService.moveToHis(rcmConfigParaHis);


		//逻辑删除限额详细信息表数据
		UpdateWrapper<RcmConfig> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("quota_state", RcmConstant.QUOTA_STATE_CANCEL);
		updateWrapper.set("user_num", SecureUtil.getUser().getUserId());
		updateWrapper.set("org_num", SecureUtil.getUser().getDeptId());
		updateWrapper.set("update_time", DateUtil.now());
		updateWrapper.eq("quota_num", quatoNum);
		return rcmConfigService.update(updateWrapper);
	}

	/**
	 * 将要存入数据库的限额参数配置信息加到list
	 */
	private void addList(List<String> nodes, String quotaLevel, RcmQuotaManagerVO rcmConfigurationManager, List<RcmConfigPara> rcmConfigurationParas) {
		for (String node : nodes) {
			RcmConfigPara rcmConfigurationPara = new RcmConfigPara();
			String subParaNum = UUID.randomUUID().toString().replaceAll("-", "");
			rcmConfigurationPara.setSubParaNum(subParaNum);
			rcmConfigurationPara.setUserNum(rcmConfigurationManager.getUserNum());
			rcmConfigurationPara.setOrgNum(rcmConfigurationManager.getOrgNum());
			rcmConfigurationPara.setCreateTime(rcmConfigurationManager.getCreateTime());
			rcmConfigurationPara.setUpdateTime(rcmConfigurationManager.getUpdateTime());
			rcmConfigurationPara.setQuotaIndexNum(rcmConfigurationManager.getQuotaIndexNum());
			rcmConfigurationPara.setControlNode(node);
			rcmConfigurationPara.setQuotaLevel(quotaLevel);
			rcmConfigurationPara.setUseOrgNum(rcmConfigurationManager.getUseOrgNum());
			rcmConfigurationPara.setQuotaNum(rcmConfigurationManager.getQuotaNum());
			if (RcmConstant.QUOTA_LEVEL_OBSERVE.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationManager.getObserveValueType())) {
					rcmConfigurationPara.setQuotaControlAmt(rcmConfigurationManager.getObserveValue());
					rcmConfigurationPara.setQuotaControlRatio(null);
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationManager.getObserveValueType())) {
					rcmConfigurationPara.setQuotaControlRatio(rcmConfigurationManager.getObserveValue());
					rcmConfigurationPara.setQuotaControlAmt(null);
				} else {
					throw new ServiceException("[观察值]请选择阈值类型");
				}
				rcmConfigurationPara.setNodeMeasure(rcmConfigurationManager.getObserveNodeMeasure());
				rcmConfigurationPara.setQuotaControlType(rcmConfigurationManager.getObserveValueType());
				rcmConfigurationParas.add(rcmConfigurationPara);
			} else if (RcmConstant.QUOTA_LEVEL_WARNING.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationManager.getWarnValueType())) {
					rcmConfigurationPara.setQuotaControlAmt(rcmConfigurationManager.getWarnValue());
					rcmConfigurationPara.setQuotaControlRatio(null);
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationManager.getWarnValueType())) {
					rcmConfigurationPara.setQuotaControlRatio(rcmConfigurationManager.getWarnValue());
					rcmConfigurationPara.setQuotaControlAmt(null);
				} else {
					throw new ServiceException("[预警值]请选择阈值类型");
				}
				rcmConfigurationPara.setNodeMeasure(rcmConfigurationManager.getWarnNodeMeasure());
				rcmConfigurationPara.setQuotaControlType(rcmConfigurationManager.getWarnValueType());
				rcmConfigurationParas.add(rcmConfigurationPara);
			} else if (RcmConstant.QUOTA_LEVEL_CONTROLLER.equals(quotaLevel)) {
				if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(rcmConfigurationManager.getControlValueType())) {
					rcmConfigurationPara.setQuotaControlAmt(rcmConfigurationManager.getControlValue());
					rcmConfigurationPara.setQuotaControlRatio(null);
				} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(rcmConfigurationManager.getControlValueType())) {
					rcmConfigurationPara.setQuotaControlRatio(rcmConfigurationManager.getControlValue());
					rcmConfigurationPara.setQuotaControlAmt(null);
				} else {
					throw new ServiceException("[控制值]请选择阈值类型");
				}
				rcmConfigurationPara.setNodeMeasure(rcmConfigurationManager.getControlNodeMeasure());
				rcmConfigurationPara.setQuotaControlType(rcmConfigurationManager.getControlValueType());
				rcmConfigurationParas.add(rcmConfigurationPara);
			}
		}
	}

}
