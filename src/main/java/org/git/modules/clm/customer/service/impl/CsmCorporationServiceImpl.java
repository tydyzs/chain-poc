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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.modules.clm.customer.entity.CsmCorporation;
import org.git.modules.clm.customer.entity.CsmRelation;
import org.git.modules.clm.customer.mapper.CsmCorporationMapper;
import org.git.modules.clm.customer.service.ICsmCorporationService;
import org.git.modules.clm.customer.service.ICsmRelationService;
import org.git.modules.clm.customer.vo.CsmCorporationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公司客户基本信息 服务实现类
 *
 * @author git
 * @since 2019-11-27
 */
@Service
public class CsmCorporationServiceImpl extends ServiceImpl<CsmCorporationMapper, CsmCorporation> implements ICsmCorporationService {
	@Autowired
	private ICsmRelationService iCsmRelationService;

	@Override
	public IPage<CsmCorporationVO> selectCsmCorporationPage(IPage<CsmCorporationVO> page, CsmCorporationVO csmCorporation) {
		return page.setRecords(baseMapper.selectCsmCorporationPage(page, csmCorporation));
	}

	/**
	 * 根据人行清算行号获取客户编号
	 * -1:没有找到客户编号
	 */
	@Override
	public String getCustomerNum(String bankPaySysNum) {
		String customerNum = "";
		CsmCorporation csmCorporation = new CsmCorporation();
		csmCorporation.setBankPaySysNum(bankPaySysNum);
		CsmCorporation csmCorporationRs = baseMapper.selectOne(Wrappers.query(csmCorporation));
		/**如果没有找到客户编号。可以先去关联关系表找到上级人行清算行号*/
		String supSettleBankNum = "";
		if (csmCorporationRs == null) {
			CsmRelation csmRelation = new CsmRelation();
			csmRelation.setSettleBankNum(bankPaySysNum);
			CsmRelation csmRelationRs = iCsmRelationService.getOne(Wrappers.query(csmRelation));
			supSettleBankNum = csmRelationRs.getSupSettleBankNum();
			csmCorporation.setBankPaySysNum(supSettleBankNum);
			csmCorporationRs = baseMapper.selectOne(Wrappers.query(csmCorporation));
			/**根据上级人行清算行号还是找不到客户编号*/
			if (csmCorporationRs == null) {
				customerNum = "-1";
			}
			customerNum = csmCorporationRs.getCustomerNum();
		} else {
			customerNum = csmCorporationRs.getCustomerNum();
		}
		return customerNum;
	}

	/**
	 * 根据统一信用代码查询客户信息
	 */
	@Override
	public CsmCorporation creditOrganCodeGetCustomerNum(String bankPaySysNum) {
		CsmCorporation csmCorporation = new CsmCorporation();
		csmCorporation.setCreditOrganCode(bankPaySysNum);
		CsmCorporation csmCorporationRs = baseMapper.selectOne(Wrappers.query(csmCorporation));
		return csmCorporationRs;
	}

	/**
	 * 通过客户编号查询公司客户信息
	 *
	 * @param customerNum
	 * @return
	 */
	@Override
	public CsmCorporationVO selectCsmCorporationPageByCusNum(String customerNum) {
		//字典翻译
		CsmCorporationVO csmCorporationVO = transVO(baseMapper.selectCsmCorporationPageByCusNum(customerNum));
		return csmCorporationVO;
	}

	/**
	 * 转换类，将CsmCorporationVO进行字典翻译
	 *
	 * @param csmCorporationVO
	 * @return
	 */
	public CsmCorporationVO transVO(CsmCorporationVO csmCorporationVO) {
		//对公客户类型
		csmCorporationVO.setBeneCustTypeName(DictCache.getValue("CD000184", csmCorporationVO.getBeneCustType()));
		//国别代码
		csmCorporationVO.setCountryCodeName(DictCache.getValue("CD000001", csmCorporationVO.getCountryCode()));
		//注册资本币种
		csmCorporationVO.setRegCptlCurrName(DictCache.getValue("CD000019", csmCorporationVO.getRegCptlCurr()));
		//国民经济行业4
		csmCorporationVO.setNationalEconomyDepart4Name(DictCache.getValue("CD000015", csmCorporationVO.getNationalEconomyDepart4()));
		//企业规模
		csmCorporationVO.setUnitScaleName(DictCache.getValue("CD000020", csmCorporationVO.getUnitScale()));

		return csmCorporationVO;
	}

}
