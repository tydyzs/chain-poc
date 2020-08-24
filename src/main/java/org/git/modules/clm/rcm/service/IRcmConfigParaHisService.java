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

import org.git.modules.clm.rcm.entity.RcmConfigParaHis;
import org.git.modules.clm.rcm.vo.RcmConfigParaHisVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 限额参数配置历史信息表 服务类
 *
 * @author git
 * @since 2019-11-05
 */
public interface IRcmConfigParaHisService extends IService<RcmConfigParaHis> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmConfigurationParaHis
	 * @return
	 */
	IPage<RcmConfigParaHisVO> selectRcmControlHisPage(IPage<RcmConfigParaHisVO> page, RcmConfigParaHisVO rcmConfigurationParaHis);

	/**
	 * 将正式表数据移至历史信息表
	 *
	 * @param rcmConfigurationParaHis
	 * @return
	 */
	boolean moveToHis(RcmConfigParaHis rcmConfigurationParaHis);
	

	/**
	 * 查看一条历史限额参数配置信息
	 *
	 * @param rcmConfigurationParaHisVO
	 * @return
	 */
	List<RcmConfigParaHisVO> selectOneRcmConfigParaHis(RcmConfigParaHisVO rcmConfigurationParaHisVO);
}
