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
package org.git.modules.clm.rcm.service;

import org.git.modules.clm.rcm.vo.RcmIndexFullVO;

/**
 *  服务类
 *
 * @author git
 * @since 2019-10-28
 */
public interface IRcmIndexFullService {

	/**
	 * 查询信贷业务限额指标或同业业务限额指标详情
	 * @param quotaIndexNum
	 * @return
	 */
	RcmIndexFullVO selectRcmConfigDetail(String quotaIndexNum);

	/**
	 * 添加信贷业务限额指标或同业业务限额指标
	 * @param rcmIndexFullVO
	 * @return
	 */
	boolean addRcmConfig(RcmIndexFullVO rcmIndexFullVO);

	/**
	 * 修改信贷业务限额指标或同业业务限额指标
	 * @param rcmIndexFullVO
	 * @return
	 */
	boolean updateRcmConfig(RcmIndexFullVO rcmIndexFullVO);

	/**
	 *
	 * @param quotaIndexNum
	 * @return
	 */
	boolean removeRcmConfigById(String quotaIndexNum);
}
