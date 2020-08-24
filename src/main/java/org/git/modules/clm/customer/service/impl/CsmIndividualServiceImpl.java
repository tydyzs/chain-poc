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
import org.git.modules.clm.customer.entity.CsmIndividual;
import org.git.modules.clm.customer.vo.CsmIndividualVO;
import org.git.modules.clm.customer.mapper.CsmIndividualMapper;
import org.git.modules.clm.customer.service.ICsmIndividualService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 个人客户基本信息 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CsmIndividualServiceImpl extends ServiceImpl<CsmIndividualMapper, CsmIndividual> implements ICsmIndividualService {

	@Override
	public IPage<CsmIndividualVO> selectCsmIndividualPage(IPage<CsmIndividualVO> page, CsmIndividualVO csmIndividual) {
		return page.setRecords(baseMapper.selectCsmIndividualPage(page, csmIndividual));
	}

	/**
	 * 通过客户号查询客户信息
	 * @param customerNum
	 * @return
	 */
	@Override
	public CsmIndividualVO selectCsmIndividualPageByCustNum(String customerNum){
		//字典翻译
		CsmIndividualVO csmIndividualVO = transVO(baseMapper.selectCsmIndividualPageByCustNum(customerNum));
		return csmIndividualVO;
	}

	public CsmIndividualVO transVO(CsmIndividualVO csmIndividualVO){
		//证件类型
		csmIndividualVO.setCertTypeName(DictCache.getValue("CD000003", csmIndividualVO.getCertType()));
		//性别
		csmIndividualVO.setGenderName(DictCache.getValue("CD000004", csmIndividualVO.getGender()));
		//国籍
		csmIndividualVO.setNationName(DictCache.getValue("CD000001", csmIndividualVO.getNation()));
		//民族
		csmIndividualVO.setRaceName(DictCache.getValue("CD000005", csmIndividualVO.getRace()));
		//最高学位
		csmIndividualVO.setHighAcadeDegreeName(DictCache.getValue("CD000010", csmIndividualVO.getHighAcadeDegree()));
		//最高学历
		csmIndividualVO.setEducationName(DictCache.getValue("CD000011", csmIndividualVO.getEducation()));
		//是否涉农
		csmIndividualVO.setAgriRelatedIndName(DictCache.getValue("CD000167", csmIndividualVO.getAgriRelatedInd()));
		//职业
		csmIndividualVO.setOccupation3Name(DictCache.getValue("CD000012", csmIndividualVO.getOccupation3()));
		//婚姻状况
		csmIndividualVO.setMarrStatusName(DictCache.getValue("CD000007", csmIndividualVO.getMarrStatus()));
		//健康状况
		csmIndividualVO.setHealthyStatusName(DictCache.getValue("CD000009", csmIndividualVO.getHealthyStatus()));
		//客户等级
		csmIndividualVO.setCustGradeName(DictCache.getValue("CD000216", csmIndividualVO.getCustGrade()));

		return csmIndividualVO;
	}
}
