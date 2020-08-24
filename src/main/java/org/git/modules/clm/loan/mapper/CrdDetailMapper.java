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
import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.vo.CrdDetailVO;
import org.git.modules.clm.loan.vo.CrdMainVO;

import java.util.List;

/**
 * 额度明细表（客户+三级额度产品+机构） Mapper 接口
 *
 * @author git
 * @since 2019-11-12
 */
public interface CrdDetailMapper extends BaseMapper<CrdDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdDetail
	 * @return
	 */
	List<CrdDetailVO> selectCrdDetailPage(IPage page, CrdDetailVO crdDetail);

	/**
	 * 查出某机构对某客户的某产品的额度切分
	 * @param crdGrantOrgNum
	 * @param customerNum
	 * @param crdDetailPrds
	 * @return
	 */
	List<CrdDetail> listCrdDetailOrder(@Param("crdGrantOrgNum") String crdGrantOrgNum,@Param("customerNum")String customerNum,@Param("crdDetailPrds")List<String> crdDetailPrds);

	/**
	 * 查出流水表数据，并和额度明细表对比计算出新的可用额度
	 * @param grantingSerialIds
	 * @param tranSystem
	 * @param crdMainNum
	 * @return
	 */
	List<CrdDetail> toInsertCrdDetail(@Param("grantingSerialIds") List<String> grantingSerialIds,@Param("tranSystem")String tranSystem,@Param("crdMainNum") String crdMainNum);

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用)
	 * @param serialIds
	 * @return
	 */
	int updateDetailForPre(@Param("serialIds") List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表预占用和可用额度(预占用撤销)
	 * @param serialIds
	 * @return
	 */
	int updateDetailForPreCancle(@Param("serialIds") List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用)
	 * @param serialIds
	 * @return
	 */
	int updateDetailForUsed(@Param("serialIds") List<String> serialIds);

	/**
	 * 根据额度使用流水修改额度明细表已用和预占用额度(占用撤销)
	 * @param serialIds
	 * @return
	 */
	int updateDetailForUsedCancle(@Param("serialIds") List<String> serialIds);

	/**
	 *
	 * @param crdDetails
	 * @return
	 */
	List<CrdDetail> listByUniAndStatu(@Param("crdDetails")List<CrdDetail> crdDetails,@Param("crdAdmitFlag")String crdAdmitFlag);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdDetailVO
	 * @return
	 */
	List<CrdDetailVO> findCrdDetailPage(IPage<CrdDetailVO>page, CrdDetailVO crdDetailVO);

	/**
	 * 通过二级额度编号查询额度明细
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @param crdMainNum
	 * @return
	 */
	List<CrdDetailVO> selectCrdDetailFromCrdMainNum(IPage<CrdDetailVO>page, String customerNum,String orgNum,String crdMainNum);

	/**
	 * 担保台账：担保额度明细
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdDetailVO> selectGuaranteeCrdDetail(IPage<CrdDetailVO>page, String customerNum,String orgNum);

	/**
	 * 查询第三方额度台账额度明细信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdDetailVO> findThirdPartyCrdDetailPage(IPage<CrdDetailVO> page, String customerNum,String orgNum );


}
