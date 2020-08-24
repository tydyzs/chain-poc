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
package org.git.modules.clm.loan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.vo.CrdApplySerialVO;

import java.util.List;

/**
 * 额度使用流水 Mapper 接口
 *
 * @author git
 * @since 2019-11-12
 */
public interface CrdApplySerialMapper extends BaseMapper<CrdApplySerial> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdApplySerial
	 * @return
	 */
	List<CrdApplySerialVO> selectCrdApplySerialPage(IPage page, CrdApplySerialVO crdApplySerial);

	/**
	 * 查询同业客户额度台账占用恢复信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdApplySerialVO> findLedgerCrdApplySerialPage(IPage page, String customerNum,String orgNum);

	/**
	 * 查询额度台账占用恢复信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdApplySerialVO> getApplySerialPage(IPage page, String customerNum,String orgNum);

	/**
	 * 统计使用额度大于成员行明细额度可用额度的数量
	 * @param certInfoIds
	 * @return
	 */
	int countMemberSplitAviValid(List<String> certInfoIds);

	/**
	 * 统计使用额度大于成员行授信额度可用额度的数量
	 * @param certInfoIds
	 * @return
	 */
	int countMemberAviValid(List<String> certInfoIds);

	/**
	 * 统计使用额度大于省联社明细额度可用额度的数量
	 * @param certInfoIds
	 * @param crdGrantOrgNum
	 * @return
	 */
	int countProviceSplitAviValid(List<String> certInfoIds,String crdGrantOrgNum);

	/**
	 * 统计使用额度大于成员行授信额度可用额度的数量
	 * @param certInfoIds
	 * @param crdGrantOrgNum
	 * @return
	 */
	int countProviceAviValid(List<String> certInfoIds,String crdGrantOrgNum);

	/**
	 * 统计使用额度大于成员行明细额度已用额度的数量
	 * @param certInfoIds
	 * @return
	 */
	int countMemberSplitUsedValid(List<String> certInfoIds);

	/**
	 * 统计使用额度大于成员行授信额度已用额度的数量
	 * @param certInfoIds
	 * @return
	 */
	int countMemberUsedValid(List<String> certInfoIds);

	/**
	 * 统计使用额度大于省联社明细额度已用额度的数量
	 * @param certInfoIds
	 * @param crdGrantOrgNum
	 * @return
	 */
	int countProviceSplitUsedValid(List<String> certInfoIds,String crdGrantOrgNum);

	/**
	 * 统计使用额度大于成员行授信额度已用额度的数量
	 * @param certInfoIds
	 * @param crdGrantOrgNum
	 * @return
	 */
	int countProviceUsedValid(List<String> certInfoIds,String crdGrantOrgNum);

	/**
	 * 根据业务编号查看用户编号
	 * @param busiDealNum
	 * @return
	 */
	String selectCustByDealNum(String busiDealNum);

	/**
	 * 根据业务编号查看最后一笔业务流水
	 * @param busiDealNum
	 * @return
	 */
	CrdApplySerialVO selectNewestSerial(String busiDealNum);
}
