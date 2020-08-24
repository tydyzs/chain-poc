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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.mapper.CrdDetailMapper;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.vo.CrdDetailVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 额度明细表（客户+三级额度产品+机构） 服务实现类
 *
 * @author git
 * @since 2019-11-12
 */
@Service
public class CrdDetailServiceImpl extends ServiceImpl<CrdDetailMapper, CrdDetail> implements ICrdDetailService {

	@Override
	public IPage<CrdDetailVO> selectCrdDetailPage(IPage<CrdDetailVO> page, CrdDetailVO crdDetail) {
		return page.setRecords(baseMapper.selectCrdDetailPage(page, crdDetail));
	}

	@Override
	public CrdDetail findCrdDetail(String busiDealOrgNum, String busiOpptOrgNum, String crdProductNum) {
		CrdDetail crdDetail = new CrdDetail();
		crdDetail.setCrdGrantOrgNum(busiDealOrgNum);
		crdDetail.setCustomerNum(busiOpptOrgNum);
		crdDetail.setCrdDetailPrd(crdProductNum);
		return baseMapper.selectOne(Wrappers.query(crdDetail));
	}

	@Override
	public List<CrdDetail> listCrdDetailOrder(String crdGrantOrgNum, String customerNum, List<String> crdDetailPrds) {
		return baseMapper.listCrdDetailOrder(crdGrantOrgNum, customerNum, crdDetailPrds);
	}

	@Override
	public List<CrdDetail> toInsertCrdDetail(List<String> grantingSerialIds, String tranSystem, String crdMainNum) {
		return baseMapper.toInsertCrdDetail(grantingSerialIds, tranSystem, crdMainNum);
	}

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用)
	 *
	 * @param serialIds
	 * @return
	 */
	@Override
	public boolean updateDetailForPre(List<String> serialIds) {
		int count = baseMapper.updateDetailForPre(serialIds);
		return count >= 1;
	}

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用撤销)
	 *
	 * @param serialIds
	 * @return
	 */
	@Override
	public boolean updateDetailForPreCancle(List<String> serialIds) {
		int count = baseMapper.updateDetailForPreCancle(serialIds);
		return count >= 1;
	}

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用)
	 *
	 * @param serialIds
	 * @return
	 */
	@Override
	public boolean updateDetailForUsed(List<String> serialIds) {
		int count = baseMapper.updateDetailForUsed(serialIds);
		return count >= 1;
	}

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用撤销)
	 *
	 * @param serialIds
	 * @return
	 */
	@Override
	public boolean updateDetailForUsedCancle(List<String> serialIds) {
		int count = baseMapper.updateDetailForUsedCancle(serialIds);
		return count >= 1;
	}

	@Override
	public List<CrdDetail> listByUniAndStatu(List<CrdDetail> crdDetails, String crdAdmitFlag) {
		return baseMapper.listByUniAndStatu(crdDetails, crdAdmitFlag);
	}

	@Override
	public IPage<CrdDetailVO> findCrdDetailPage(IPage<CrdDetailVO> page, CrdDetailVO crdDetailVO) {
		return page.setRecords(baseMapper.findCrdDetailPage(page, crdDetailVO));
	}

	/**
	 * 通过二级额度编号查询额度明细
	 *
	 * @param page
	 * @param crdMainNum
	 * @return
	 */
	@Override
	public IPage<CrdDetailVO> selectCrdDetailFromCrdMainNum(IPage<CrdDetailVO> page, String customerNum, String orgNum, String crdMainNum) {
		// 字典翻译
		List<CrdDetailVO> list = transVO(baseMapper.selectCrdDetailFromCrdMainNum(page, customerNum, orgNum, crdMainNum));
		return page.setRecords(list);
	}

	/**
	 * 查询第三方额度台账额度明细信息
	 *
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdDetailVO> findThirdPartyCrdDetailPage(IPage<CrdDetailVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<CrdDetailVO> list = transVO(baseMapper.findThirdPartyCrdDetailPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}


	/**
	 * 担保台账：担保额度明细
	 *
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdDetailVO> selectGuaranteeCrdDetail(IPage<CrdDetailVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<CrdDetailVO> list = transVO(baseMapper.selectGuaranteeCrdDetail(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将crdDetailVO进行字典翻译
	 *
	 * @param crdDetailVOList
	 * @return
	 */
	public List<CrdDetailVO> transVO(List<CrdDetailVO> crdDetailVOList) {
		List<CrdDetailVO> list = new ArrayList<>();
		for (CrdDetailVO crdDetailVO : crdDetailVOList) {
			// 币种
			crdDetailVO.setCurrencyCdName(DictCache.getValue("CD000019", crdDetailVO.getCurrencyCd()));
			// 机构
			crdDetailVO.setOrgNumName(SysCache.getDeptName(crdDetailVO.getOrgNum()));
			// 额度类型
			crdDetailVO.setCrdProductTypeName(DictCache.getValue("CD000211", crdDetailVO.getCrdProductType()));
			// 是否可循环
			crdDetailVO.setIsCycleName(DictCache.getValue("CD000167", crdDetailVO.getIsCycle()));

			list.add(crdDetailVO);
		}
		return list;
	}
}
