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
package org.git.modules.clm.rcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.rcm.entity.RcmWarnInfoHis;
import org.git.modules.clm.rcm.vo.RcmWarnInfoHisVO;
import org.git.modules.clm.rcm.vo.RcmWarnInfoQueryVO;

import java.util.List;

/**
 * 限额预警历史信息表（一年以上） Mapper 接口
 *
 * @author git
 * @since 2019-12-04
 */
public interface RcmWarnInfoHisMapper extends BaseMapper<RcmWarnInfoHis> {

	/**
	 * 按条件查询限额预警信息表
	 * @param page
	 * @param rcmWarnInfo 查询视图实体类
	 * @return
	 */
	List<RcmWarnInfoHisVO> queryRcmWarnInfoHis(IPage page, RcmWarnInfoQueryVO rcmWarnInfo);

	/**
	 * 按条件查询限额预警历史信息单条数据
	 * @param rcmWarnInfo
	 * @return
	 */
	RcmWarnInfoHisVO queryRcmWarnDetail(@Param("rcmWarnInfo") RcmWarnInfoQueryVO rcmWarnInfo);
}
