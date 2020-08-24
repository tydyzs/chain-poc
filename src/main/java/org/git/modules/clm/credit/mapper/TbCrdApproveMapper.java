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
package org.git.modules.clm.credit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;

import java.util.List;

/**
 * 批复信息表 Mapper 接口
 *
 * @author git
 * @since 2019-11-14
 */
public interface TbCrdApproveMapper extends BaseMapper<TbCrdApprove> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdApprove
	 * @return
	 */
	List<TbCrdApproveVO> selectTbCrdApprovePage(IPage page, TbCrdApproveVO tbCrdApprove);

	/**
	 * 查询批复详情，含有客户名称
	 * @param approveNum 批复ID
	 * @return
	 */
	TbCrdApproveVO selectApproveDetailByCusNum(String approveNum);

	/**
	 *  查询第三方额度台账批复信息
	 * @return
	 */
	List<TbCrdApproveVO> findThirdPartyCrdApprovePage(IPage page,TbCrdApproveVO tbCrdApproveVO);

	/**
	 * 查询批复信息列表
	 * @param approveNum
	 * @return
	 */
	List<TbCrdApproveVO> findListCrdApprove(IPage page,String approveNum);

}
