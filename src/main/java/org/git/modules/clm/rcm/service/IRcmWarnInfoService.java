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

import org.git.modules.clm.rcm.entity.RcmWarnInfo;
import org.git.modules.clm.rcm.vo.RcmWarnInfoQueryVO;
import org.git.modules.clm.rcm.vo.RcmWarnInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 限额预警信息表 服务类
 *
 * @author git
 * @since 2019-12-04
 */
public interface IRcmWarnInfoService extends IService<RcmWarnInfo> {

	/**
	 * 按条件查询限额预警信息
	 * @param page
	 * @param rcmWarnInfo 查询视图实体类
	 * @return
	 */
	IPage<RcmWarnInfoVO> queryRcmWarnInfo(IPage<RcmWarnInfoVO> page, RcmWarnInfoQueryVO rcmWarnInfo);


	/**
	 * 根据限额预警信息查询限额预警信息详情
	 * @param rcmWarnInfo
	 * @return
	 */
	RcmWarnInfoVO selectById(RcmWarnInfoQueryVO rcmWarnInfo);

	/**
	 * 根据限额配置编号统计预警数量
	 * @param quotaNum
	 * @return
	 */
	int countByQuotaNum(String quotaNum);
}
