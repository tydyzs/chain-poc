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
import org.git.modules.clm.customer.entity.CsmRelation;
import org.git.modules.clm.customer.vo.CsmRelationVO;
import org.git.modules.clm.customer.mapper.CsmRelationMapper;
import org.git.modules.clm.customer.service.ICsmRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 关系信息表 服务实现类
 *
 * @author git
 * @since 2019-12-05
 */
@Service
public class CsmRelationServiceImpl extends ServiceImpl<CsmRelationMapper, CsmRelation> implements ICsmRelationService {

	@Override
	public IPage<CsmRelationVO> selectCsmRelationPage(IPage<CsmRelationVO> page, CsmRelationVO csmRelation) {
		//字典翻译
		List<CsmRelationVO> list = transVO(baseMapper.selectCsmRelationPage(page, csmRelation));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CsmRelationVO进行字典翻译
	 *
	 * @param csmRelationVOList
	 * @return
	 */
	public List<CsmRelationVO> transVO(List<CsmRelationVO> csmRelationVOList) {
		List<CsmRelationVO> list = new ArrayList<>();
		for (CsmRelationVO csmRelationVO : csmRelationVOList) {
			// 关系客户类型
			csmRelationVO.setRelCustomerTypeName(DictCache.getValue("CD000016", csmRelationVO.getRelCustomerType()));
			// 证件类型
			csmRelationVO.setCertTypeName(DictCache.getValue("CD000003", csmRelationVO.getCertType()));

			list.add(csmRelationVO);
		}
		return list;
	}

}
