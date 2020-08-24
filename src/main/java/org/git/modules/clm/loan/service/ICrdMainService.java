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
package org.git.modules.clm.loan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.vo.CrdMainVO;

import java.util.List;
import java.util.Map;

/**
 * 额度主表（客户+二级额度产品+机构） 服务类
 *
 * @author git
 * @since 2019-11-12
 */
public interface ICrdMainService extends IService<CrdMain> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdMain
	 * @return
	 */
	IPage<CrdMainVO> selectCrdMainPage(IPage<CrdMainVO> page, CrdMainVO crdMain);
	CrdMain findCrdMain(String busiOpptOrgNum,String busiDealOrgNum);

	/**
	 * 查询同业客户额度信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	IPage<CrdMainVO> findLedgerCrdMainPage(IPage<CrdMainVO> page, String customerNum, String orgNum);


	/**
	 * 查询公司客户额度信息
	 * @param page
	 * @param customerNum
	 * @return
	 */
	IPage<CrdMainVO> selectCorporateCrdList(IPage<CrdMainVO> page,String customerNum,String orgNum);
	/**
	 * 将授信流水表里的信息移动到额度主表
	 * @param crdMain
	 * @return
	 */
	boolean moveSerialToMain(CrdMain crdMain,String grantingSerialId);

	/**
	 * 预占用、预占用撤销 修改授信主表预占用和可用额度
	 * @param crdMain
	 * @return
	 */
	boolean updateMainForPre(CrdMain crdMain);

	/**
	 * 占用、占用撤销、恢复、恢复撤销 修改授信主表预占用和已用
	 * @param crdMain
	 * @return
	 */
	boolean updateMainForUsed(CrdMain crdMain);
}
