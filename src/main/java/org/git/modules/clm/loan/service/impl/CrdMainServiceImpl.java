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
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.mapper.CrdMainMapper;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.loan.vo.CrdMainVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 额度主表（客户+二级额度产品+机构） 服务实现类
 *
 * @author git
 * @since 2019-11-12
 */
@Service
public class CrdMainServiceImpl extends ServiceImpl<CrdMainMapper, CrdMain> implements ICrdMainService {

	@Override
	public IPage<CrdMainVO> selectCrdMainPage(IPage<CrdMainVO> page, CrdMainVO crdMain) {
		return page.setRecords(baseMapper.selectCrdMainPage(page, crdMain));
	}

	/**
	 * 额度状态
	 * 有效检查
	 * param:用信客户(对手机构）；授信机构（本方机构）
	 */
	@Override
	public CrdMain findCrdMain(String busiOpptOrgNum, String busiDealOrgNum) {
		CrdMain crdMain = new CrdMain();
		crdMain.setCustomerNum(busiOpptOrgNum);
		crdMain.setCrdGrantOrgNum(busiDealOrgNum);
		return baseMapper.selectOne(Wrappers.query(crdMain));
	}

	@Override
	public IPage<CrdMainVO> findLedgerCrdMainPage(IPage<CrdMainVO> page, String customerNum, String orgNum) {
		/*CrdMainVO crdMainVO = new CrdMainVO();
		crdMainVO.setOrgNum(orgNum);
		crdMainVO.setCustomerNum(customerNum);*/
		// 字典翻译
		List<CrdMainVO> list = transVO(baseMapper.findLedgerCrdMainPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}


	@Override
	public boolean moveSerialToMain(CrdMain crdMain, String grantingSerialId) {
		int result = baseMapper.moveSerialToMain(crdMain, grantingSerialId);
		return result >= 1;
	}

	/**
	 * 查询公司客户额度信息
	 *
	 * @param page
	 * @param customerNum
	 * @return
	 */
	@Override
	public IPage<CrdMainVO> selectCorporateCrdList(IPage<CrdMainVO> page, String customerNum, String orgNum) {
		// 字典翻译
		List<CrdMainVO> list = transVO(baseMapper.selectCorporateCrdList(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	/**
	 * 转换类，将CrdMainVO进行字典翻译
	 * @param crdMainVOList
	 * @return
	 */
	public List<CrdMainVO> transVO(List<CrdMainVO> crdMainVOList) {
		List<CrdMainVO> list = new ArrayList<>();
		for (CrdMainVO crdMainVO : crdMainVOList) {
			// 币种
			crdMainVO.setCurrencyCdName(DictCache.getValue("CD000019", crdMainVO.getCurrencyCd()));
			// 机构
			crdMainVO.setOrgNumName(SysCache.getDeptName(crdMainVO.getOrgNum()));

			list.add(crdMainVO);
		}
		return list;
	}

	/**
	 * 预占用、预占用撤销 修改授信主表预占用和可用额度
	 *
	 * @param crdMain
	 * @return
	 */
	@Override
	public boolean updateMainForPre(CrdMain crdMain) {
		int result = baseMapper.updateMainForPre(crdMain);
		return result >= 1;
	}

	/**
	 * 占用、占用撤销、恢复、恢复撤销 修改授信主表预占用和已用
	 *
	 * @param crdMain
	 * @return
	 */
	public boolean updateMainForUsed(CrdMain crdMain) {
		int result = baseMapper.updateMainForUsed(crdMain);
		return result >= 1;
	}
}
