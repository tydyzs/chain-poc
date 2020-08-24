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
package org.git.modules.clm.customer.service.impl;

import org.git.common.cache.DictCache;
import org.git.modules.clm.customer.entity.CsmManageTeam;
import org.git.modules.clm.customer.vo.CsmManageTeamVO;
import org.git.modules.clm.customer.mapper.CsmManageTeamMapper;
import org.git.modules.clm.customer.service.ICsmManageTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理团队 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CsmManageTeamServiceImpl extends ServiceImpl<CsmManageTeamMapper, CsmManageTeam> implements ICsmManageTeamService {

	@Override
	public IPage<CsmManageTeamVO> selectCsmManageTeamPage(IPage<CsmManageTeamVO> page, CsmManageTeamVO csmManageTeam) {
		return page.setRecords(baseMapper.selectCsmManageTeamPage(page, csmManageTeam));
	}

	/**
	 * 查询我行管理团队
	 *
	 * @param page
	 * @param customerNum
	 * @return
	 */
	@Override
	public IPage<CsmManageTeamVO> selectCsmManageTeamPageByCusNum(IPage<CsmManageTeamVO> page, String customerNum) {
		//字典翻译
		List<CsmManageTeamVO> list = transVO(baseMapper.selectCsmManageTeamPageByCusNum(page, customerNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CsmManageTeamVO进行字典翻译
	 *
	 * @param csmManageTeamVOList
	 * @return
	 */
	public List<CsmManageTeamVO> transVO(List<CsmManageTeamVO> csmManageTeamVOList) {
		List<CsmManageTeamVO> list = new ArrayList<>();
		for (CsmManageTeamVO csmManageTeamVO : csmManageTeamVOList) {
			//机构地区
			csmManageTeamVO.setAeraCodeName(DictCache.getValue("CD000002", csmManageTeamVO.getAeraCode()));
			//权限类型
			csmManageTeamVO.setUserPlacingCdName(DictCache.getValue("CD000185", csmManageTeamVO.getUserPlacingCd()));
			list.add(csmManageTeamVO);
		}
		return list;
	}

}
