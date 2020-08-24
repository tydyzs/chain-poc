/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.clm.loan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.mapper.CrdApplySerialMapper;
import org.git.modules.clm.loan.service.ICrdApplySerialService;
import org.git.modules.clm.loan.vo.CrdApplySerialVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 额度使用流水 服务实现类
 *
 * @author git
 * @since 2019-11-12
 */
@Service
public class CrdApplySerialServiceImpl extends ServiceImpl<CrdApplySerialMapper, CrdApplySerial> implements ICrdApplySerialService {

	@Override
	public IPage<CrdApplySerialVO> selectCrdApplySerialPage(IPage<CrdApplySerialVO> page, CrdApplySerialVO crdApplySerial) {
		return page.setRecords(baseMapper.selectCrdApplySerialPage(page, crdApplySerial));
	}

	/**
	 * 查询同业客户额度台账占用恢复信息
	 *
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdApplySerialVO> findLedgerCrdApplySerialPage(IPage<CrdApplySerialVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<CrdApplySerialVO> list = transVO(baseMapper.getApplySerialPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 查询额度台账占用恢复信息
	 *
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdApplySerialVO> getApplySerialPage(IPage<CrdApplySerialVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<CrdApplySerialVO> list = transVO(baseMapper.getApplySerialPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，CrdApplySerialVO
	 *
	 * @param crdApplySerialVOList
	 * @return
	 */
	public List<CrdApplySerialVO> transVO(List<CrdApplySerialVO> crdApplySerialVOList) {
		List<CrdApplySerialVO> list = new ArrayList<>();
		for (CrdApplySerialVO crdApplySerialVO : crdApplySerialVOList) {
			//币种
			crdApplySerialVO.setCurrencyCdName(DictCache.getValue("CD000019", crdApplySerialVO.getCurrencyCd()));
			//交易类型
			crdApplySerialVO.setTranTypeCdName(DictCache.getValue("CD000087", crdApplySerialVO.getTranTypeCd()));
			//机构名称
			crdApplySerialVO.setOrgNumName(SysCache.getDeptName(crdApplySerialVO.getOrgNum()));
			//经办人
			crdApplySerialVO.setUserNumName(UserCache.getUserName(crdApplySerialVO.getUserNum()));
			//是否串用
			crdApplySerialVO.setIsMixName(DictCache.getValue("CD000161",crdApplySerialVO.getIsMix()));
			list.add(crdApplySerialVO);
		}
		return list;
	}

	@Override
	public int countMemberSplitAviValid(List<String> certInfoIds) {
		return baseMapper.countMemberSplitAviValid(certInfoIds);
	}

	@Override
	public int countMemberAviValid(List<String> certInfoIds) {
		return baseMapper.countMemberAviValid(certInfoIds);
	}

	@Override
	public int countProviceSplitAviValid(List<String> certInfoIds, String crdGrantOrgNum) {
		return baseMapper.countProviceSplitAviValid(certInfoIds, crdGrantOrgNum);
	}

	@Override
	public int countProviceAviValid(List<String> certInfoIds, String crdGrantOrgNum) {
		return baseMapper.countProviceAviValid(certInfoIds, crdGrantOrgNum);
	}

	@Override
	public int countMemberSplitUsedValid(List<String> certInfoIds) {
		return baseMapper.countMemberSplitUsedValid(certInfoIds);
	}

	@Override
	public int countMemberUsedValid(List<String> certInfoIds) {
		return baseMapper.countMemberUsedValid(certInfoIds);
	}

	@Override
	public int countProviceSplitUsedValid(List<String> certInfoIds, String crdGrantOrgNum) {
		return baseMapper.countProviceSplitUsedValid(certInfoIds, crdGrantOrgNum);
	}

	@Override
	public int countProviceUsedValid(List<String> certInfoIds, String crdGrantOrgNum) {
		return baseMapper.countProviceUsedValid(certInfoIds, crdGrantOrgNum);
	}

	@Override
	public String selectCustByDealNum(String busiDealNum) {
		return baseMapper.selectCustByDealNum(busiDealNum);
	}

	@Override
	public CrdApplySerialVO selectNewestSerial(String busiDealNum) {
		return baseMapper.selectNewestSerial(busiDealNum);
	}
}
