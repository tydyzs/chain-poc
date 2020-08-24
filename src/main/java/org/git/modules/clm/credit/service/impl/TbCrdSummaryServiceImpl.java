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
import org.git.common.cache.UserCache;
import org.git.modules.clm.credit.entity.TbCrdSummary;
import org.git.modules.clm.credit.vo.TbCrdSummaryVO;
import org.git.modules.clm.credit.mapper.TbCrdSummaryMapper;
import org.git.modules.clm.credit.service.ITbCrdSummaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 借据信息表 服务实现类
 *
 * @author git
 * @since 2019-11-22
 */
@Service
public class TbCrdSummaryServiceImpl extends ServiceImpl<TbCrdSummaryMapper, TbCrdSummary> implements ITbCrdSummaryService {

	@Override
	public IPage<TbCrdSummaryVO> selectTbCrdSummaryPage(IPage<TbCrdSummaryVO> page, TbCrdSummaryVO tbCrdSummary) {
		return page.setRecords(baseMapper.selectTbCrdSummaryPage(page, tbCrdSummary));
	}

	/**
	 * 借据详细（带有客户名称）
	 *
	 * @param summaryNum
	 * @return
	 */
	@Override
	public TbCrdSummaryVO selectSummaryDetailByCusNum(String summaryNum) {
		//字典翻译
		TbCrdSummaryVO tbCrdSummaryVO = transVO(baseMapper.selectSummaryDetailByCusNum(summaryNum));
		return tbCrdSummaryVO;
	}

	/**
	 * 借据详情 （从 额度台账 来）
	 *
	 * @param customerNum
	 * @return
	 */
	@Override
	public IPage<TbCrdSummaryVO> selectSummaryListFromCusNum(IPage<TbCrdSummaryVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<TbCrdSummaryVO> list = transVO(baseMapper.selectSummaryListFromCusNum(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将TbCrdSummaryVO进行字典翻译
	 *
	 * @param tbCrdSummaryVOList
	 * @return
	 */
	public List<TbCrdSummaryVO> transVO(List<TbCrdSummaryVO> tbCrdSummaryVOList) {
		List<TbCrdSummaryVO> list = new ArrayList<>();
		for (TbCrdSummaryVO tbCrdSummaryVO : tbCrdSummaryVOList) {
			//币种
			tbCrdSummaryVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdSummaryVO.getCurrencyCd()));
			//借据状态
			tbCrdSummaryVO.setSummaryStatusName(DictCache.getValue("CD000175", tbCrdSummaryVO.getSummaryStatus()));
			//机构名称
			tbCrdSummaryVO.setOrgNumName(SysCache.getDeptName(tbCrdSummaryVO.getOrgNum()));
			//经办人
			tbCrdSummaryVO.setUserNumName(UserCache.getUserName(tbCrdSummaryVO.getUserNum()));
			list.add(tbCrdSummaryVO);
		}
		return list;
	}

	/**
	 * 转换类，将TbCrdSummaryVO进行字典翻译
	 *
	 * @param tbCrdSummaryVO
	 * @return
	 */
	public TbCrdSummaryVO transVO(TbCrdSummaryVO tbCrdSummaryVO){
		//币种
		tbCrdSummaryVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdSummaryVO.getCurrencyCd()));
		//机构名称
		tbCrdSummaryVO.setOrgNumName(SysCache.getDeptName(tbCrdSummaryVO.getOrgNum()));
		//经办人
		tbCrdSummaryVO.setUserNumName(UserCache.getUserName(tbCrdSummaryVO.getUserNum()));
		//业务种类分类
		String productTypeName = "";
		String productTypeStr = tbCrdSummaryVO.getProductType();
		if(productTypeStr != null && !"".equals(productTypeStr)){
			String[] array = productTypeStr.split(",");
			for(String str : array){
				str = DictCache.getValue("CD000061", str);
				productTypeName = productTypeName + "," + str;
			}
			tbCrdSummaryVO.setProductTypeName(productTypeName.substring(1));
		}

		//担保方式
		tbCrdSummaryVO.setGuaranteeTypeName(DictCache.getValue("CD000101", tbCrdSummaryVO.getGuaranteeType()));

		return tbCrdSummaryVO;
	}
}
