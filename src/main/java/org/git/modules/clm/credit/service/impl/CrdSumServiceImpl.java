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
import org.git.modules.clm.credit.entity.CrdSum;
import org.git.modules.clm.credit.vo.CrdSumVO;
import org.git.modules.clm.credit.mapper.CrdSumMapper;
import org.git.modules.clm.credit.service.ICrdSumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 额度汇总表（实时） 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CrdSumServiceImpl extends ServiceImpl<CrdSumMapper, CrdSum> implements ICrdSumService {

	@Override
	public IPage<CrdSumVO> selectCrdSumPage(IPage<CrdSumVO> page, CrdSumVO crdSum) {
		return page.setRecords(baseMapper.selectCrdSumPage(page, crdSum));
	}

	@Override
	public CrdSumVO queryCrdSum(CrdSumVO crdSum) {
		return baseMapper.queryCrdSum(crdSum);
	}

	/**
	 * 查询第三方额度台账汇总
	 * @param page
	 * @param crdSumVO
	 * @return
	 */
	@Override
	public IPage<CrdSumVO> findThirdPartyCrdSumPage(IPage page, CrdSumVO crdSumVO) {
		//字典翻译
		List<CrdSumVO> list = transVO(baseMapper.findThirdPartyCrdSumPage(page, crdSumVO));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CrdSumVO进行字典翻译
	 * @param crdSumVOList
	 * @return
	 */
	public List<CrdSumVO> transVO(List<CrdSumVO> crdSumVOList) {
		List<CrdSumVO> list = new ArrayList<>();
		for (CrdSumVO crdSumVO : crdSumVOList) {
			// 证件类型
			crdSumVO.setCertTypeName(DictCache.getValue("CD000003", crdSumVO.getCertType()));
			// 币种
			crdSumVO.setCurrencyCdName(DictCache.getValue("CD000019", crdSumVO.getCurrencyCd()));
			// 机构
			crdSumVO.setOrgNumName(SysCache.getDeptName(crdSumVO.getOrgNum()));

			list.add(crdSumVO);
		}
		return list;
	}
}
