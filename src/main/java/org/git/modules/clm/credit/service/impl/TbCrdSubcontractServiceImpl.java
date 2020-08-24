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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;
import org.git.modules.clm.credit.mapper.TbCrdSubcontractMapper;
import org.git.modules.clm.credit.service.ITbCrdSubcontractService;
import org.git.modules.clm.credit.vo.TbCrdSubcontractVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 担保合同信息表 服务实现类
 *
 * @author git
 * @since 2019-11-14
 */
@Service
public class TbCrdSubcontractServiceImpl extends ServiceImpl<TbCrdSubcontractMapper, TbCrdSubcontract> implements ITbCrdSubcontractService {

	@Override
	public IPage<TbCrdSubcontractVO> selectTbCrdSubcontractPage(IPage<TbCrdSubcontractVO> page, TbCrdSubcontractVO tbCrdSubcontract) {
		return page.setRecords(baseMapper.selectTbCrdSubcontractPage(page, tbCrdSubcontract));
	}

	/**
	 * 限额台账  对外担保信息数据
	 * @param page
	 * @param customerNum
	 * @return
	 */
	@Override
	public IPage<TbCrdSubcontractVO> selectTbCrdSubcontractList(IPage<TbCrdSubcontractVO> page, String customerNum,String orgNum) {
		return page.setRecords(baseMapper.selectTbCrdSubcontractList(page, customerNum,orgNum));
	}

	@Override
	public IPage<TbCrdSubcontractVO> queryCrdSubcontractPage(IPage page, String contractNum) {
		//字典翻译
		List<TbCrdSubcontractVO> list = transVO(baseMapper.queryCrdSubcontractPage(page, contractNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将TbCrdSubcontractVO进行字典翻译
	 *
	 * @param tbCrdSubcontractVOList
	 * @return
	 */
	public List<TbCrdSubcontractVO> transVO(List<TbCrdSubcontractVO> tbCrdSubcontractVOList) {
		List<TbCrdSubcontractVO> list = new ArrayList<>();
		for (TbCrdSubcontractVO tbCrdSubcontractVO : tbCrdSubcontractVOList) {
			//币种
			tbCrdSubcontractVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdSubcontractVO.getCurrencyCd()));
			//担保合同类型
			tbCrdSubcontractVO.setSubcontractTypeName(DictCache.getValue("CD000102", tbCrdSubcontractVO.getSubcontractStaus()));
			//是否最高额
			tbCrdSubcontractVO.setIsTopName(DictCache.getValue("CD000167", tbCrdSubcontractVO.getIsTop()));
			//担保合同状态
			tbCrdSubcontractVO.setSubcontractStausName(DictCache.getValue("CD000103", tbCrdSubcontractVO.getSubcontractStaus()));
			list.add(tbCrdSubcontractVO);
		}
		return list;
	}

}
