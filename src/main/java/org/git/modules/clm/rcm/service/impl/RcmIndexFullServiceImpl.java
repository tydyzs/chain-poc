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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.*;
import org.git.modules.clm.rcm.mapper.RcmIndexFullMapper;
import org.git.modules.clm.rcm.service.*;
import org.git.modules.clm.rcm.vo.RcmIndexFullVO;
import org.git.core.log.exception.ServiceException;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 全部指标CRUD服务类
 *
 * @author git
 * @since 2019-10-28
 */
@AllArgsConstructor
@Service
public class RcmIndexFullServiceImpl extends ServiceImpl<RcmIndexFullMapper, RcmIndex> implements IRcmIndexFullService {

	private IRcmIndexService rcmConfigurationBaseService;
	private IRcmIndexBankService rcmConfigurationBankService;
	private IRcmIndexCreditService rcmConfigurationCreditService;
	private IRcmConfigService rcmConfigurationInfoService;


	@Override
	public RcmIndexFullVO selectRcmConfigDetail(String quotaIndexNum) {
		return baseMapper.selectRcmIndexFullDetail(quotaIndexNum);
	}

	@Override
	@Transactional
	public boolean addRcmConfig(RcmIndexFullVO rcmIndexFullVO) {
		rcmIndexFullVO.setQuotaIndexNum(BizNumUtil.getBizNumWithDate(RcmConstant.RCM_CONFIGURATION_BASE_TYPE));
		rcmIndexFullVO.setCreateTime(CommonUtil.getWorkDateTime());
		rcmIndexFullVO.setUpdateTime(CommonUtil.getWorkDateTime());
		rcmIndexFullVO.setUserNum(SecureUtil.getUser().getUserId());
		rcmIndexFullVO.setOrgNum(SecureUtil.getUser().getDeptId());
		//保存限额基础指标库
		RcmIndex rcmIndex = new RcmIndex();
		BeanUtils.copyProperties(rcmIndexFullVO, rcmIndex);
		try {
			rcmConfigurationBaseService.save(rcmIndex);
		} catch (DuplicateKeyException e) {
			throw new ServiceException("已存在同名的限额指标！");
		}
		if (RcmConstant.QUOTA_INDEX_TYPE_BANK.equals(rcmIndexFullVO.getQuotaIndexType())) {
			//保存同业业务限额基础指标库
			RcmIndexBank rcmIndexBank = new RcmIndexBank();
			BeanUtils.copyProperties(rcmIndexFullVO, rcmIndexBank);
			return rcmConfigurationBankService.save(rcmIndexBank);
		} else if (RcmConstant.QUOTA_INDEX_TYPE_CREDIT.equals(rcmIndexFullVO.getQuotaIndexType())) {
			//保存信贷业务限额基础指标
			RcmIndexCredit rcmIndexCredit = new RcmIndexCredit();
			BeanUtils.copyProperties(rcmIndexFullVO, rcmIndexCredit);
			return rcmConfigurationCreditService.save(rcmIndexCredit);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateRcmConfig(RcmIndexFullVO rcmIndexFullVO) {
		rcmIndexFullVO.setUpdateTime(DateUtil.now());
		rcmIndexFullVO.setUserNum(SecureUtil.getUser().getUserId());
		rcmIndexFullVO.setOrgNum(SecureUtil.getUser().getDeptId());
		//保存限额基础指标库
		RcmIndex rcmIndex = new RcmIndex();
		BeanUtils.copyProperties(rcmIndexFullVO, rcmIndex);
		try {
			rcmConfigurationBaseService.updateById(rcmIndex);
		} catch (DuplicateKeyException e) {
			throw new ServiceException("已存在同名的限额指标！");
		}
		if (RcmConstant.QUOTA_INDEX_TYPE_BANK.equals(rcmIndexFullVO.getQuotaIndexType())) {
			//保存同业业务限额基础指标库
			RcmIndexBank rcmIndexBank = new RcmIndexBank();
			BeanUtils.copyProperties(rcmIndexFullVO, rcmIndexBank);
			return rcmConfigurationBankService.updateById(rcmIndexBank);
		} else if (RcmConstant.QUOTA_INDEX_TYPE_CREDIT.equals(rcmIndexFullVO.getQuotaIndexType())) {
			//保存信贷业务限额基础指标
			RcmIndexCredit rcmIndexCredit = new RcmIndexCredit();
			BeanUtils.copyProperties(rcmIndexFullVO, rcmIndexCredit);
			return rcmConfigurationCreditService.updateById(rcmIndexCredit);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean removeRcmConfigById(String quotaIndexNum) {
		RcmConfig input = new RcmConfig();
		input.setQuotaIndexNum(quotaIndexNum);
		//删除限额指标需要检查此限额指标是否已经被限额设定所引用，如产生过引用的限额指标不可以做删除操作
		int count = rcmConfigurationInfoService.count(Condition.getQueryWrapper(input));
		if (count > 0) {
			throw new ServiceException("此限额指标是否已经被限额设定所引用，不可删除");
		}
		rcmConfigurationBankService.removeById(quotaIndexNum);
		rcmConfigurationCreditService.removeById(quotaIndexNum);
		return rcmConfigurationBaseService.removeById(quotaIndexNum);
	}
}
