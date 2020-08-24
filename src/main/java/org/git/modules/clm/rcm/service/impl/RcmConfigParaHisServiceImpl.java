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

import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.RcmConfigParaHis;
import org.git.modules.clm.rcm.vo.RcmConfigParaHisVO;
import org.git.modules.clm.rcm.mapper.RcmConfigParaHisMapper;
import org.git.modules.clm.rcm.service.IRcmConfigParaHisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 限额参数配置历史信息表 服务实现类
 *
 * @author git
 * @since 2019-11-05
 */
@Service
public class RcmConfigParaHisServiceImpl extends ServiceImpl<RcmConfigParaHisMapper, RcmConfigParaHis> implements IRcmConfigParaHisService {

	@Override
	public IPage<RcmConfigParaHisVO> selectRcmControlHisPage(IPage<RcmConfigParaHisVO> page, RcmConfigParaHisVO rcmConfigurationParaHis) {
		return page.setRecords(baseMapper.selectRcmControlHisPage(page, rcmConfigurationParaHis));
	}

	@Override
	public boolean moveToHis(RcmConfigParaHis rcmConfigurationParaHis) {
		int m = baseMapper.moveToHis(rcmConfigurationParaHis);
		if (m > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<RcmConfigParaHisVO> selectOneRcmConfigParaHis(RcmConfigParaHisVO rcmConfigurationParaHisVO) {
		List<RcmConfigParaHisVO> hiss = baseMapper.selectOneRcmControl(rcmConfigurationParaHisVO);
		if (hiss != null && !hiss.isEmpty()) {
			for (RcmConfigParaHisVO his : hiss) {
				String quotaLevel = his.getQuotaLevel();
				if (RcmConstant.QUOTA_LEVEL_OBSERVE.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlRatio());
					}
				} else if (RcmConstant.QUOTA_LEVEL_WARNING.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlRatio());
					}
				} else if (RcmConstant.QUOTA_LEVEL_CONTROLLER.equals(quotaLevel)) {
					if (RcmConstant.QUOTA_CONTROL_TYPE_BALANCE.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlAmt());
					} else if (RcmConstant.QUOTA_LEVEL_WARNING_PERC.equals(his.getQuotaControlType())) {
						his.setAmtOrRatio(his.getQuotaControlRatio());
					}
				}
			}
		}
		return hiss;
	}
}
