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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.mapper.CrdBusiCertInfoMapper;
import org.git.modules.clm.loan.service.ICrdBusiCertInfoService;
import org.git.modules.clm.loan.vo.CrdApplySerialVO;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务凭证信息表 服务实现类
 *
 * @author git
 * @since 2019-11-12
 */
@Service
public class CrdBusiCertInfoServiceImpl extends ServiceImpl<CrdBusiCertInfoMapper, CrdBusiCertInfo> implements ICrdBusiCertInfoService {

	@Override
	public IPage<CrdBusiCertInfoVO> selectCrdBusiCertInfoPage(IPage<CrdBusiCertInfoVO> page, CrdBusiCertInfoVO crdBusiCertInfo) {
		return page.setRecords(baseMapper.selectCrdBusiCertInfoPage(page, crdBusiCertInfo));
	}


	/**
	 * 根据额度明细编号\业务编号 统计不同凭证状的凭证数
	 */
	@Override
	public int countCertByCrdAndBusi(List<String> crdDetailNums, String certStatus, String busiDealNum){
		CrdBusiCertInfo params = new CrdBusiCertInfo();
		params.setBusiDealNum(busiDealNum);
		if(!StringUtil.isEmpty(certStatus)){
			params.setCertStatus(certStatus);
		}
		QueryWrapper<CrdBusiCertInfo> queryWrapper = Condition.getQueryWrapper(params);
		if(StringUtil.isEmpty(certStatus)){
			queryWrapper.isNull("cert_status");
		}
		queryWrapper.in("crd_detail_num",crdDetailNums);
		int count = baseMapper.selectCount(queryWrapper);
		return count;
	}

	@Override
	public int countDateValid(List<String> certInfoIds){
		return baseMapper.countDateValid(certInfoIds);
	}

	@Override
	public CrdBusiCertInfo collectDate(String busiDealNum,String crdDetailNum){
		return baseMapper.collectDate(busiDealNum,crdDetailNum);
	}

	@Override
	public List<CrdBusiCertInfo> selectForUpdate(String eventMainId
		,String crdGrantOrgNum
		,String customerNum
		,String crdDetailPrd){
		return baseMapper.selectForUpdate(eventMainId,crdGrantOrgNum,customerNum,crdDetailPrd);
	}


	/**
	 * 	 * 查询同业客户额度台账中的业务产品信息
	 * 	 * @param page
	 * 	 * @param customerNum
	 * 	 * @param orgNum
	 * 	 * @return
	 */
	@Override
	public IPage<CrdBusiCertInfoVO> findLedgerCrdBusiCertInfoPage(IPage<CrdBusiCertInfoVO> page, String customerNum ,String orgNum) {
		//字典翻译
		List<CrdBusiCertInfoVO> list = transVO(baseMapper.findLedgerCrdBusiCertInfoPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 通过业务品种查询同业产品详情
	 * @param crdDetailPrd
	 * @return
	 */
	public CrdBusiCertInfoVO findLedgerByCrdPrd(String crdDetailPrd,
												String crdGrantOrgNum,
												String customerNum,
												String certNum){
		return baseMapper.findLedgerByCrdPrd(crdDetailPrd,crdGrantOrgNum,customerNum,certNum);
	}

	/**
	 * 查询批复详情，含有客户名称
	 * @param cretInfoId 凭证信息id
	 * @return
	 */
	@Override
	public CrdBusiCertInfoVO findCrdBusiCertInfoDetailByCusNum(String cretInfoId) {
		return baseMapper.findCrdBusiCertInfoDetailByCusNum(cretInfoId);
	}

	/**
	 * 转换类，CrdBusiCertInfoVO
	 *
	 * @param crdBusiCertInfoVOList
	 * @return
	 */
	public List<CrdBusiCertInfoVO> transVO(List<CrdBusiCertInfoVO> crdBusiCertInfoVOList) {
		List<CrdBusiCertInfoVO> list = new ArrayList<>();
		for (CrdBusiCertInfoVO crdBusiCertInfoVO : crdBusiCertInfoVOList) {
			//币种
			crdBusiCertInfoVO.setCertCurrencyCdName(DictCache.getValue("CD000019", crdBusiCertInfoVO.getCertCurrencyCd()));
			//凭证状态
			crdBusiCertInfoVO.setCertStatusName(DictCache.getValue("CD000201", crdBusiCertInfoVO.getCertStatus()));
			//机构名称
			crdBusiCertInfoVO.setOrgNumName(SysCache.getDeptName(crdBusiCertInfoVO.getOrgNum()));
			//经办人
			crdBusiCertInfoVO.setUserNumName(UserCache.getUserName(crdBusiCertInfoVO.getUserNum()));
			list.add(crdBusiCertInfoVO);
		}
		return list;
	}
}
