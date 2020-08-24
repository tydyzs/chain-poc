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

import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.vo.RcmIndexVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author git
 * @since 2019-10-28
 */
public interface IRcmIndexService extends IService<RcmIndex> {

	/**
	 * 查询贷业务限额指标和同业业务限额指标自定义分页
	 *
	 * @param page
	 * @param rcmConfigurationBase
	 * @return
	 */
	IPage<RcmIndexVO> selectRcmConfigurationBasePage(IPage<RcmIndexVO> page, RcmIndexVO rcmConfigurationBase);

	/**
	 * 启用指标配置
	 * @param quotaIndexNum
	 * @return
	 */
	boolean updateToUsable(String quotaIndexNum);

	/**
	 * 停用指标配置
	 * @param quotaIndexNum
	 * @return
	 */
	boolean updateToUnUsable(String quotaIndexNum);
}
