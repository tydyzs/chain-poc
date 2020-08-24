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

import org.git.modules.clm.rcm.entity.RcmConfig;
import org.git.modules.clm.rcm.vo.RcmConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 限额详细信息表 服务类
 *
 * @author liuye
 * @since 2019-11-01
 */
public interface IRcmConfigService extends IService<RcmConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmConfigurationInfo
	 * @return
	 */
	IPage<RcmConfigVO> selectRcmConfigPage(IPage<RcmConfigVO> page, RcmConfigVO rcmConfigurationInfo);

	/**
	 * 修改限额设定信息名称、状态及修改时间、修改人、修改机构
	 */
	boolean updateRcmConfigNameAndState(RcmConfig rcmConfigurationInfo);


}
