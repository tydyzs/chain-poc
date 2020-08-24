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
import org.git.modules.clm.credit.entity.TbCrdContract;
import org.git.modules.clm.credit.vo.TbCrdContractVO;
import org.git.modules.clm.credit.mapper.TbCrdContractMapper;
import org.git.modules.clm.credit.service.ITbCrdContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同信息表 服务实现类
 *
 * @author git
 * @since 2019-11-14
 */
@Service
public class TbCrdContractServiceImpl extends ServiceImpl<TbCrdContractMapper, TbCrdContract> implements ITbCrdContractService {

	@Override
	public IPage<TbCrdContractVO> selectTbCrdContractPage(IPage<TbCrdContractVO> page, TbCrdContractVO tbCrdContract) {
		return page.setRecords(baseMapper.selectTbCrdContractPage(page, tbCrdContract));
	}

	/**
	 * 合同明细（带有客户名称）
	 *
	 * @param contractNum
	 * @return
	 */
	@Override
	public TbCrdContractVO selectContractDetailByCusNum(String contractNum) {
		return baseMapper.selectContractDetailByCusNum(contractNum);
	}

	/**
	 * 查询合同信息（从 额度台账 过来）
	 *
	 * @param page
	 * @param customerNum
	 * @return
	 */
	@Override
	public IPage<TbCrdContractVO> selectContractListByCusNum(IPage<TbCrdContractVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<TbCrdContractVO> list = transVO(baseMapper.selectContractListByCusNum(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	@Override
	public TbCrdContractVO queryCrdContractDetail(String contractNum) {
		//字典翻译
		TbCrdContractVO tbCrdContractVO = transVO(baseMapper.queryCrdContractDetail(contractNum));
		return tbCrdContractVO;
	}

	/**
	 * 转换类，将TbCrdContractVO进行字典翻译
	 *
	 * @param tbCrdContractVOList
	 * @return
	 */
	public List<TbCrdContractVO> transVO(List<TbCrdContractVO> tbCrdContractVOList) {
		List<TbCrdContractVO> list = new ArrayList<>();
		for (TbCrdContractVO tbCrdContractVO : tbCrdContractVOList) {
			//币种
			tbCrdContractVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdContractVO.getCurrencyCd()));
			//合同状态
			tbCrdContractVO.setContractStatusName(DictCache.getValue("CD000094", tbCrdContractVO.getContractStatus()));
			//机构名称
			tbCrdContractVO.setOrgNumName(SysCache.getDeptName(tbCrdContractVO.getOrgNum()));
			//经办人
			tbCrdContractVO.setUserNumName(UserCache.getUserName(tbCrdContractVO.getUserNum()));
			list.add(tbCrdContractVO);
		}
		return list;
	}

	/**
	 * 转换类，将TbCrdContractVO进行字典翻译
	 *
	 * @param tbCrdContractVO
	 * @return
	 */
	public TbCrdContractVO transVO(TbCrdContractVO tbCrdContractVO) {
		//币种
		tbCrdContractVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdContractVO.getCurrencyCd()));
		//机构名称
		tbCrdContractVO.setOrgNumName(SysCache.getDeptName(tbCrdContractVO.getOrgNum()));
		//经办人
		tbCrdContractVO.setUserNumName(UserCache.getUserName(tbCrdContractVO.getUserNum()));
		//业务种类分类
		tbCrdContractVO.setProductTypeName(DictCache.getValue("CD000061", tbCrdContractVO.getProductType()));

		return tbCrdContractVO;
	}

}
