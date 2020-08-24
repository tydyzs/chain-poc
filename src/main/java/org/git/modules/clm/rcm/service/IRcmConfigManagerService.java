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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.rcm.entity.RcmConfigParaHis;
import org.git.modules.clm.rcm.vo.RcmQuotaManagerHisVO;
import org.git.modules.clm.rcm.vo.RcmQuotaManagerVO;
/**
 * 限额参数配置信息表 服务类
 *
 * @author liuye
 * @since 2019-11-01
 */
public interface IRcmConfigManagerService {

	/**
	 * 查看
	 * @param page
	 * @param rcmConfigurationManager
	 */
	IPage<RcmQuotaManagerVO> selectRcmConfigurationManager(IPage<RcmQuotaManagerVO> page, RcmQuotaManagerVO rcmConfigurationManager);

	/**
	 * 查看限额详细信息
	 * @param rcmConfigurationManager
	 * @return
	 */
	RcmQuotaManagerVO detail(RcmQuotaManagerVO rcmConfigurationManager);

	/**
	 * 添加限额详细信息和参数配置信息
	 * @param rcmConfigurationManager
	 * @return
	 */
	boolean addRcmConfigInfoAndPara(RcmQuotaManagerVO rcmConfigurationManager);

	/**
	 * 修改限额详细信息和参数配置信息
	 * @param rcmConfigurationManager
	 * @return
	 */
	boolean updateRcmConfigInfoAndPara(RcmQuotaManagerVO rcmConfigurationManager);

	/**
	 * 查看历史记录(初始页面，包含现在值和修改记录列表)
	 * @param page
	 * @param quatoNum
	 * @return
	 */
	RcmQuotaManagerHisVO hisList(IPage<RcmConfigParaHis> page, String quatoNum);

	/**
	 * 删除限额管理
	 * @param quatoNum
	 * @return
	 */
	boolean removeById(String quatoNum);
}
