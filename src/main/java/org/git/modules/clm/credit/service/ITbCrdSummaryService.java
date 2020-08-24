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
package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.TbCrdSummary;
import org.git.modules.clm.credit.vo.TbCrdSummaryVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 借据信息表 服务类
 *
 * @author git
 * @since 2019-11-22
 */
public interface ITbCrdSummaryService extends IService<TbCrdSummary> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdSummary
	 * @return
	 */
	IPage<TbCrdSummaryVO> selectTbCrdSummaryPage(IPage<TbCrdSummaryVO> page, TbCrdSummaryVO tbCrdSummary);

	/**
	 * 借据详细（带有客户名称）
	 * @param summaryNum
	 * @return
	 */
	TbCrdSummaryVO selectSummaryDetailByCusNum(String summaryNum);

	/**
	 * 借据详情 （从 额度台账 来）
	 * @param customerNum
	 * @return
	 */
	IPage<TbCrdSummaryVO> selectSummaryListFromCusNum(IPage<TbCrdSummaryVO> page,String customerNum,String orgNum);
}
