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
package org.git.modules.clm.credit.service.impl;

import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.vo.CrdGrantingSerialVO;
import org.git.modules.clm.credit.mapper.CrdGrantingSerialMapper;
import org.git.modules.clm.credit.service.ICrdGrantingSerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.modules.clm.loan.vo.CrdApplySerialVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 额度授信流水 服务实现类
 *
 * @author liuye
 * @since 2019-11-15
 */
@Service
public class CrdGrantingSerialServiceImpl extends ServiceImpl<CrdGrantingSerialMapper, CrdGrantingSerial> implements ICrdGrantingSerialService {

	@Override
	public IPage<CrdGrantingSerialVO> selectCrdGrantingSerialPage(IPage<CrdGrantingSerialVO> page, CrdGrantingSerialVO crdGrantingSerial) {
		return page.setRecords(baseMapper.selectCrdGrantingSerialPage(page, crdGrantingSerial));
	}

	/**
	 * 查询同业客户额度台账额度授信情况
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	@Override
	public IPage<CrdGrantingSerialVO> findLedgerCrdGrantingSerialPage(IPage<CrdGrantingSerialVO> page, String customerNum,String orgNum) {
		//字典翻译
		List<CrdGrantingSerialVO> list = transVO(baseMapper.findLedgerCrdGrantingSerialPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，CrdApplySerialVO
	 *
	 * @param crdGrantingSerialVOList
	 * @return
	 */
	public List<CrdGrantingSerialVO> transVO(List<CrdGrantingSerialVO> crdGrantingSerialVOList) {
		List<CrdGrantingSerialVO> list = new ArrayList<>();
		for (CrdGrantingSerialVO crdGrantingSerialVO : crdGrantingSerialVOList) {
			//币种
			crdGrantingSerialVO.setCrdCurrencyCdName(DictCache.getValue("CD000019", crdGrantingSerialVO.getCrdCurrencyCd()));
			//交易类型
			crdGrantingSerialVO.setTranTypeCdName(DictCache.getValue("CD000192", crdGrantingSerialVO.getTranTypeCd()));
			//机构名称
			crdGrantingSerialVO.setCrdGrantOrgNumName(SysCache.getDeptName(crdGrantingSerialVO.getOrgNum()));
			//经办人
			crdGrantingSerialVO.setUserNumName(UserCache.getUserName(crdGrantingSerialVO.getUserNum()));
			//额度状态
//			crdGrantingSerialVO.setCrdStatusName(DictCache.getValue("CD000176", crdGrantingSerialVO.getCrdStatus()));
			//客户准入
			crdGrantingSerialVO.setCrdAdmitFlagName(DictCache.getValue("CD000196", crdGrantingSerialVO.getCrdAdmitFlag()));
			list.add(crdGrantingSerialVO);
		}
		return list;
	}
}
