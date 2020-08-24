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

import org.git.common.utils.CommonUtil;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.vo.RcmIndexVO;
import org.git.modules.clm.rcm.mapper.RcmIndexMapper;
import org.git.modules.clm.rcm.service.IRcmIndexService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.core.secure.utils.SecureUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Date;

/**
 *  服务实现类
 *
 * @author git
 * @since 2019-10-28
 */
@Service
public class RcmIndexServiceImpl extends ServiceImpl<RcmIndexMapper, RcmIndex> implements IRcmIndexService {

	@Override
	public IPage<RcmIndexVO> selectRcmConfigurationBasePage(IPage<RcmIndexVO> page, RcmIndexVO rcmConfigurationBase) {
		return page.setRecords(baseMapper.selectRcmConfigurationBasePage(page, rcmConfigurationBase));
	}

	@Override
	public  boolean updateToUsable(String quotaIndexNum){
		RcmIndexVO rcmConfigurationBaseVO = new RcmIndexVO();
		rcmConfigurationBaseVO.setQuotaIndexNum(quotaIndexNum);
		rcmConfigurationBaseVO.setUpdateTime(new Date(CommonUtil.getWorkDateTime().getTime()));
		rcmConfigurationBaseVO.setUserNum(SecureUtil.getUser().getUserId());
		rcmConfigurationBaseVO.setOrgNum(SecureUtil.getUser().getDeptId());
		rcmConfigurationBaseVO.setQuotaIndexState(RcmConstant.QUOTA_INDEX_STATE_USING);
		return baseMapper.updateState(rcmConfigurationBaseVO);
	}

	@Override
	public  boolean updateToUnUsable(String quotaIndexNum){
		RcmIndexVO rcmConfigurationBaseVO = new RcmIndexVO();
		rcmConfigurationBaseVO.setQuotaIndexNum(quotaIndexNum);
		rcmConfigurationBaseVO.setUpdateTime(new Date(CommonUtil.getWorkDateTime().getTime()));
		rcmConfigurationBaseVO.setUserNum(SecureUtil.getUser().getUserId());
		rcmConfigurationBaseVO.setOrgNum(SecureUtil.getUser().getDeptId());
		rcmConfigurationBaseVO.setQuotaIndexState(RcmConstant.QUOTA_INDEX_STATE_UNSING);
		return baseMapper.updateState(rcmConfigurationBaseVO);
	}
}
