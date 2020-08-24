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
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.mapper.TbCrdApproveMapper;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 批复信息表 服务实现类
 *
 * @author git
 * @since 2019-11-14
 */
@Service
public class TbCrdApproveServiceImpl extends ServiceImpl<TbCrdApproveMapper, TbCrdApprove> implements ITbCrdApproveService {

	@Override
	public IPage<TbCrdApproveVO> selectTbCrdApprovePage(IPage<TbCrdApproveVO> page, TbCrdApproveVO tbCrdApprove) {
		return page.setRecords(baseMapper.selectTbCrdApprovePage(page, tbCrdApprove));
	}

	/**
	 * 查询批复详情，含有客户名称
	 *
	 * @param approveNum 批复ID
	 * @return
	 */
	@Override
	public TbCrdApproveVO selectApproveDetailByCusNum(String approveNum) {
		//字典翻译
		TbCrdApproveVO tbCrdApproveVO = transVO(baseMapper.selectApproveDetailByCusNum(approveNum));
		return tbCrdApproveVO;
	}

	/**
	 * 查询第三方额度台账批复信息
	 *
	 * @param page
	 * @return
	 */
	@Override
	public IPage<TbCrdApproveVO> findThirdPartyCrdApprovePage(IPage page, TbCrdApproveVO tbCrdApproveVO) {
		//字典翻译
		List<TbCrdApproveVO> list = transVO(baseMapper.findThirdPartyCrdApprovePage(page, tbCrdApproveVO));
		return page.setRecords(list);
	}

	/**
	 * 查询批复信息列表
	 *
	 * @param approveNum
	 * @return
	 */
	@Override
	public IPage<TbCrdApproveVO> findListCrdApprove(IPage<TbCrdApproveVO> page, String approveNum) {
		//字典翻译
		List<TbCrdApproveVO> list = transVO(baseMapper.findListCrdApprove(page, approveNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将TbCrdApproveVO进行字典翻译
	 *
	 * @param tbCrdApproveVOList
	 * @return
	 */
	public List<TbCrdApproveVO> transVO(List<TbCrdApproveVO> tbCrdApproveVOList) {
		List<TbCrdApproveVO> list = new ArrayList<>();
		for (TbCrdApproveVO tbCrdApproveVO : tbCrdApproveVOList) {
			//币种
			tbCrdApproveVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdApproveVO.getCurrencyCd()));
			//机构名称
			tbCrdApproveVO.setOrgNumName(SysCache.getDeptName(tbCrdApproveVO.getOrgNum()));
			//经办人
			tbCrdApproveVO.setUserNumName(UserCache.getUserName(tbCrdApproveVO.getUserNum()));
			//业务种类
			tbCrdApproveVO.setProductTypeName(DictCache.getValue("CD000061", tbCrdApproveVO.getProductType()));
			//主担保方式
			tbCrdApproveVO.setMainGuaranteeTypeName(DictCache.getValue("CD000100", tbCrdApproveVO.getMainGuaranteeType()));
			//期限单位
			tbCrdApproveVO.setTermUnitName(DictCache.getValue("CD000169", tbCrdApproveVO.getTermUnit()));
			list.add(tbCrdApproveVO);
		}
		return list;
	}

	/**
	 * 转换类，将TbCrdApproveVO进行字典翻译
	 *
	 * @param tbCrdApproveVO
	 * @return
	 */
	public TbCrdApproveVO transVO(TbCrdApproveVO tbCrdApproveVO) {
		//币种
		tbCrdApproveVO.setCurrencyCdName(DictCache.getValue("CD000019", tbCrdApproveVO.getCurrencyCd()));
		//机构名称
		tbCrdApproveVO.setOrgNumName(SysCache.getDeptName(tbCrdApproveVO.getOrgNum()));
		//经办人
		tbCrdApproveVO.setUserNumName(UserCache.getUserName(tbCrdApproveVO.getUserNum()));
		//业务类型
		tbCrdApproveVO.setBizTypeName(DictCache.getValue("CD000170", tbCrdApproveVO.getBizType()));
		//是否低风险
		tbCrdApproveVO.setIsLowRiskName(DictCache.getValue("CD000167", tbCrdApproveVO.getIsLowRisk()));
		//低风险业务类别
		tbCrdApproveVO.setLowRiskTypeName(DictCache.getValue("CD000186", tbCrdApproveVO.getLowRiskType()));
		//批复状态
		tbCrdApproveVO.setApproveStatusName(DictCache.getValue("CD000109", tbCrdApproveVO.getApproveStatus()));

		return tbCrdApproveVO;
	}
}
