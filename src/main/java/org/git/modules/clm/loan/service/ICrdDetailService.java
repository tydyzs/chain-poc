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
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.vo.CrdDetailVO;
import org.git.modules.clm.param.entity.Crd;

import java.util.List;

/**
 * 额度明细表（客户+三级额度产品+机构） 服务类
 *
 * @author git
 * @since 2019-11-12
 */
public interface ICrdDetailService extends IService<CrdDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdDetail
	 * @return
	 */
	IPage<CrdDetailVO> selectCrdDetailPage(IPage<CrdDetailVO> page, CrdDetailVO crdDetail );
	CrdDetail findCrdDetail(String busiDealOrgNum,String busiOpptOrgNum,String crdProductNum);

	List<CrdDetail> listCrdDetailOrder(String crdGrantOrgNum,String customerNum,List<String> crdDetailPrds);

	/**
	 * 查授信出流水表数据，并和额度明细表对比计算出新的可用额度
	 * @param grantingSerialIds
	 * @param tranSystem
	 * @param crdMainNum
	 * @return
	 */
	List<CrdDetail> toInsertCrdDetail(List<String> grantingSerialIds,String tranSystem,String crdMainNum);

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用)
	 * @param serialIds
	 * @return
	 */
	boolean updateDetailForPre( List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用撤销)
	 * @param serialIds
	 * @return
	 */
	boolean updateDetailForPreCancle( List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用)
	 * @param serialIds
	 * @return
	 */
	boolean updateDetailForUsed( List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用撤销)
	 * @param serialIds
	 * @return
	 */
	boolean updateDetailForUsedCancle( List<String> serialIds);

	/**
	 * 根据客户号、授信机构、三级额度产品编号、客户准入状态等参数
	 * 查看三级额度产品
	 * @param crdDetails
	 * @return
	 */
	List<CrdDetail> listByUniAndStatu(List<CrdDetail> crdDetails,String crdAdmitFlag);

	/**
	 * 分页查询
	 * @param page
	 * @param crdDetailVO
	 * @return
	 */
	IPage<CrdDetailVO>findCrdDetailPage(IPage<CrdDetailVO> page, CrdDetailVO crdDetailVO);

	/**
	 * 通过二级额度编号查询额度明细
	 * @param page
	 * @param crdMainNum
	 * @return
	 */
	IPage<CrdDetailVO> selectCrdDetailFromCrdMainNum(IPage<CrdDetailVO> page,String customerNum,String orgNum ,String crdMainNum);

	/**
	 * 担保台账：担保额度明细
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	IPage<CrdDetailVO> selectGuaranteeCrdDetail(IPage<CrdDetailVO>page, String customerNum,String orgNum);

	/**
	 * 查询第三方额度台账额度明细信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	IPage<CrdDetailVO> findThirdPartyCrdDetailPage(IPage<CrdDetailVO> page,String customerNum,String orgNum );
}
