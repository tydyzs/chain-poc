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
package org.git.modules.clm.rcm.service;

import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.vo.RcmConfigTotalVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 限额详细统计表 服务类
 *
 * @author git
 * @since 2019-12-23
 */
public interface IRcmConfigTotalService extends IService<RcmConfigTotal> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmConfigTotal
	 * @return
	 */
	IPage<RcmConfigTotalVO> selectRcmConfigTotalPage(IPage<RcmConfigTotalVO> page, RcmConfigTotalVO rcmConfigTotal);


	/**
	 * 查询某一笔限额统计数据
	 *
	 * @param useOrgNum
	 * @param quotaIndexNum
	 * @param year
	 * @param month
	 * @return
	 */
	RcmConfigTotal selectRcmConfigTotal(String useOrgNum, String year, String month, String quotaIndexNum);

	/**
	 * 查询多笔限额统计数据
	 *
	 * @param useOrgNum
	 * @param quotaIndexNums
	 * @param year
	 * @param month
	 * @return
	 */
	List<RcmConfigTotal> selectRcmConfigTotal(String useOrgNum, String year, String month, String... quotaIndexNums);

}
