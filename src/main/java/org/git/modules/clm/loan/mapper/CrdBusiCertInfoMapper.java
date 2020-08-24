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
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.vo.CrdBusiCertInfoVO;

import java.util.List;

/**
 * 业务凭证信息表 Mapper 接口
 *
 * @author git
 * @since 2019-11-12
 */
public interface CrdBusiCertInfoMapper extends BaseMapper<CrdBusiCertInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdBusiCertInfo
	 * @return
	 */
	List<CrdBusiCertInfoVO> selectCrdBusiCertInfoPage(IPage page, CrdBusiCertInfoVO crdBusiCertInfo);

	/**
	 * 统计凭证有效期不在额度有效期内的数量
	 * @param certInfoIds
	 */
	int countDateValid(List<String> certInfoIds);

	/**
	 * 汇总当前流水的最小起始日和最大终止日
	 * @param busiDealNum
	 * @param crdDetailNum
	 * @return
	 */
	CrdBusiCertInfo collectDate(@Param("busiDealNum") String busiDealNum,@Param("crdDetailNum") String crdDetailNum);

	/**
	 * 查询事件表数据待移入凭证信息
	 * @param eventMainId
	 * @param crdGrantOrgNum
	 * @param customerNum
	 * @param crdDetailPrd
	 * @return
	 */
	List<CrdBusiCertInfo> selectForUpdate(@Param("eventMainId")String eventMainId
		,@Param("crdGrantOrgNum")String crdGrantOrgNum
		,@Param("customerNum")String customerNum
		,@Param("crdDetailPrd")String crdDetailPrd);


	/**
	 * 查询同业客户额度台账中的业务产品信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdBusiCertInfoVO> findLedgerCrdBusiCertInfoPage(IPage<CrdBusiCertInfoVO> page,String customerNum,String orgNum);

	/**
	 * 通过业务品种查询同业产品详情
	 * @param crdDetailPrd
	 * @return
	 */
	CrdBusiCertInfoVO findLedgerByCrdPrd(@Param("crdDetailPrd")String crdDetailPrd,
										@Param("crdGrantOrgNum")String crdGrantOrgNum,
										@Param("customerNum")String customerNum,
										@Param("certNum")String certNum);

	/**
	 * 查询批复详情，含有客户名称
	 * @param cretInfoId 凭证信息id
	 * @return
	 */
	CrdBusiCertInfoVO findCrdBusiCertInfoDetailByCusNum(String cretInfoId);

}
