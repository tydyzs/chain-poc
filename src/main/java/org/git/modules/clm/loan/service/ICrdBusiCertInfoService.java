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
import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.credit.entity.FundEventDetail;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;

import java.util.List;

/**
 * 业务凭证信息表 服务类
 *
 * @author git
 * @since 2019-11-12
 */
public interface ICrdBusiCertInfoService extends IService<CrdBusiCertInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdBusiCertInfo
	 * @return
	 */
	IPage<CrdBusiCertInfoVO> selectCrdBusiCertInfoPage(IPage<CrdBusiCertInfoVO> page, CrdBusiCertInfoVO crdBusiCertInfo);

	/**
	 *
	 * 根据额度明细编号\业务编号 统计不同凭证状的凭证数
	 * @param crdDetailNums
	 * @param certStatus
	 * @param busiDealNum
	 * @return
	 */
	int countCertByCrdAndBusi(List<String> crdDetailNums, String certStatus, String busiDealNum);

	/**
	 * 统计凭证有效期不在额度有效期内的数量
	 * @param certInfoIds
	 * @return
	 */
	int countDateValid(List<String> certInfoIds);

	/**
	 * 汇总当前流水的最小起始日和最大终止日
	 * @param busiDealNum
	 * @param crdDetailNum
	 * @return
	 */
	CrdBusiCertInfo collectDate(String busiDealNum,String crdDetailNum);

	/**
	 * 查询事件表数据待移入凭证信息
	 * @param eventMainId
	 * @param crdGrantOrgNum
	 * @param customerNum
	 * @param crdDetailPrd
	 * @return
	 */
	List<CrdBusiCertInfo> selectForUpdate(String eventMainId
		,String crdGrantOrgNum
		,String customerNum
		,String crdDetailPrd);

	/**
	 * 查询同业客户额度台账中的业务产品信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	IPage<CrdBusiCertInfoVO> findLedgerCrdBusiCertInfoPage(IPage<CrdBusiCertInfoVO> page, String customerNum,String orgNum);

	/**
	 * 通过业务品种查询同业产品详情
	 * @param crdDetailPrd
	 * @return
	 */
	CrdBusiCertInfoVO findLedgerByCrdPrd(String crdDetailPrd,
										 String crdGrantOrgNum,
										 String customerNum,
										 String certNum);

	/**
	 * 查询批复详情，含有客户名称
	 * @param cretInfoId 凭证信息id
	 * @return
	 */
	CrdBusiCertInfoVO findCrdBusiCertInfoDetailByCusNum(String cretInfoId);
}
