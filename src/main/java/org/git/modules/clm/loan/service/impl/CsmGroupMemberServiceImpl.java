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
package org.git.modules.clm.loan.service.impl;

import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.credit.vo.CrdSumVO;
import org.git.modules.clm.loan.entity.CsmGroupMember;
import org.git.modules.clm.loan.vo.CsmGroupMemberVO;
import org.git.modules.clm.loan.mapper.CsmGroupMemberMapper;
import org.git.modules.clm.loan.service.ICsmGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 集团成员表 服务实现类
 *
 * @author git
 * @since 2019-12-22
 */
@Service
public class CsmGroupMemberServiceImpl extends ServiceImpl<CsmGroupMemberMapper, CsmGroupMember> implements ICsmGroupMemberService {

	/*@Autowired
	private IDeptService deptService;*/

	@Override
	public IPage<CsmGroupMemberVO> selectCsmGroupMemberPage(IPage<CsmGroupMemberVO> page, CsmGroupMemberVO csmGroupMember) {
		return page.setRecords(baseMapper.selectCsmGroupMemberPage(page, csmGroupMember));
	}

	/**
	 * 查询集团成员信息
	 * @param page
	 * @param csmGroupMemberVO
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CsmGroupMemberVO> findCsmGroupMemberPage(IPage<CsmGroupMemberVO> page,CsmGroupMemberVO csmGroupMemberVO, String orgNum){
		List<CsmGroupMemberVO> list = transVO(baseMapper.findCsmGroupMemberPage(page, csmGroupMemberVO,orgNum));
		return page.setRecords(list);
	}

	/**
	 * 查询集团客户信息
	 * @param page
	 * @param csmGroupMemberVO
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CsmGroupMemberVO> listCsmGroupMemberPage(IPage<CsmGroupMemberVO> page,CsmGroupMemberVO csmGroupMemberVO, String orgNum){
		/*String SLSorgNum=deptService.selectProvincialCooperative().getId()+"";
		csmGroupMemberVO.setCustomerNum(SLSorgNum);*/
		List<CsmGroupMemberVO> list = transVO(baseMapper.listCsmGroupMemberPage(page, csmGroupMemberVO,orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CrdSumVO进行字典翻译
	 * @param csmGroupMemberVOList
	 * @return
	 */
	public List<CsmGroupMemberVO> transVO(List<CsmGroupMemberVO> csmGroupMemberVOList) {
		List<CsmGroupMemberVO> list = new ArrayList<>();
		for (CsmGroupMemberVO csmGroupMemberVO : csmGroupMemberVOList) {
			// 证件类型
//			csmGroupMemberVO.setCertTypeName(DictCache.getValue("CD000003", crdSumVO.getCertType()));
			// 币种
			csmGroupMemberVO.setCurrencyCdName(DictCache.getValue("CD000019", csmGroupMemberVO.getCurrencyCd()));
			// 机构
			csmGroupMemberVO.setOrgNumName(SysCache.getDeptName(csmGroupMemberVO.getOrgNum()));
			//经办人
			csmGroupMemberVO.setUserNumName(UserCache.getUserName(csmGroupMemberVO.getUserNum()));
			list.add(csmGroupMemberVO);
		}
		return list;
	}
}
