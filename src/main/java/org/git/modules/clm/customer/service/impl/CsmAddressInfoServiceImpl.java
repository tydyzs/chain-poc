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
import org.git.modules.clm.customer.entity.CsmAddressInfo;
import org.git.modules.clm.customer.vo.CsmAddressInfoVO;
import org.git.modules.clm.customer.mapper.CsmAddressInfoMapper;
import org.git.modules.clm.customer.service.ICsmAddressInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址信息 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CsmAddressInfoServiceImpl extends ServiceImpl<CsmAddressInfoMapper, CsmAddressInfo> implements ICsmAddressInfoService {

	@Override
	public IPage<CsmAddressInfoVO> selectCsmAddressInfoPage(IPage<CsmAddressInfoVO> page, CsmAddressInfoVO csmAddressInfo) {
		//字典翻译
		List<CsmAddressInfoVO> list = transVO(baseMapper.selectCsmAddressInfoPage(page, csmAddressInfo));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CsmAddressInfoVO进行字典翻译
	 *
	 * @param csmAddressInfoVOList
	 * @return
	 */
	public List<CsmAddressInfoVO> transVO(List<CsmAddressInfoVO> csmAddressInfoVOList) {
		List<CsmAddressInfoVO> list = new ArrayList<>();
		for (CsmAddressInfoVO csmAddressInfoVO : csmAddressInfoVOList) {
			// 联系类型
			csmAddressInfoVO.setConnTypeName(DictCache.getValue("CD000031", csmAddressInfoVO.getConnType()));
			// 联系地址国家/地区
			csmAddressInfoVO.setCounRegiName(DictCache.getValue("CD000001", csmAddressInfoVO.getCounRegi()));
			// 省代码
			csmAddressInfoVO.setProvinceName(DictCache.getValue("CD000002", csmAddressInfoVO.getProvince()));
			// 币种
			csmAddressInfoVO.setCityName(DictCache.getValue("CD000002", csmAddressInfoVO.getCity()));
			// 币种
			csmAddressInfoVO.setCountyName(DictCache.getValue("CD000002", csmAddressInfoVO.getCountyName()));

			list.add(csmAddressInfoVO);
		}
		return list;
	}
}
