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
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.vo.CrdMainVO;

import java.util.List;
import java.util.Map;

/**
 * 额度主表（客户+二级额度产品+机构） Mapper 接口
 *
 * @author git
 * @since 2019-11-12
 */
public interface CrdMainMapper extends BaseMapper<CrdMain> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdMain
	 * @return
	 */
	List<CrdMainVO> selectCrdMainPage(IPage page, CrdMainVO crdMain);

	/**
	 * 查询同业客户额度信息
	 * @param page
	 * @param customerNum
	 * @param orgNum
	 * @return
	 */
	List<CrdMainVO> findLedgerCrdMainPage(IPage<CrdMainVO> page, String customerNum,String orgNum);

	/**
	 * 查询公司客户额度信息
	 * @param page
	 * @param customerNum
	 * @return
	 */
	List<CrdMainVO> selectCorporateCrdList(IPage<CrdMainVO> page, String customerNum,String orgNum);

	/**
	 * 将授信信息存从授信流水移动到额度主表
	 * @param crdMain
	 * @param grantingSerialId
	 * @return
	 */
	int moveSerialToMain(@Param("crdMain") CrdMain crdMain,@Param("grantingSerialId")String grantingSerialId);

	/**
	 * 预占用、预占用撤销 修改授信主表预占用和可用额度
	 * @param crdMain
	 * @return
	 */
	int updateMainForPre(CrdMain crdMain);

	/**
	 * 占用、占用撤销、恢复、恢复撤销 修改授信主表预占用和已用
	 * @param crdMain
	 * @return
	 */
	int updateMainForUsed(CrdMain crdMain);

}
