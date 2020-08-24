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
package org.git.modules.clm.credit.service.impl;

import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.modules.clm.credit.entity.CrdCcInfo;
import org.git.modules.clm.credit.vo.CrdCcInfoVO;
import org.git.modules.clm.credit.mapper.CrdCcInfoMapper;
import org.git.modules.clm.credit.service.ICrdCcInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡系统信息 服务实现类
 *
 * @author git
 * @since 2019-12-27
 */
@Service
public class CrdCcInfoServiceImpl extends ServiceImpl<CrdCcInfoMapper, CrdCcInfo> implements ICrdCcInfoService {

	@Override
	public IPage<CrdCcInfoVO> selectCrdCcInfoPage(IPage<CrdCcInfoVO> page, CrdCcInfoVO crdCcInfo) {
		return page.setRecords(baseMapper.selectCrdCcInfoPage(page, crdCcInfo));
	}

	/**
	 * 信用卡信息（带有客户名称）
	 *
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdCcInfoVO> selectCrdCcInfoAndCustomerNamePage(IPage<CrdCcInfoVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<CrdCcInfoVO> list = transVO(baseMapper.selectCrdCcInfoAndCustomerNamePage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CrdCcInfoVO进行字典翻译
	 *
	 * @param crdCcInfoVOList
	 * @return
	 */
	public List<CrdCcInfoVO> transVO(List<CrdCcInfoVO> crdCcInfoVOList) {
		List<CrdCcInfoVO> list = new ArrayList<>();
		for (CrdCcInfoVO crdCcInfoVO : crdCcInfoVOList) {
			// 币种
			crdCcInfoVO.setCurrencyCdName(DictCache.getValue("CD000019", crdCcInfoVO.getCurrencyCd()));
			// 机构
			crdCcInfoVO.setOrgNumName(SysCache.getDeptName(crdCcInfoVO.getOrgNum()));

			list.add(crdCcInfoVO);
		}
		return list;
	}
}
