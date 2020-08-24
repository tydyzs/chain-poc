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
package org.git.modules.clm.rcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.vo.RcmIndexVO;
import org.git.modules.clm.rcm.vo.RcmIndexFullVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author liuye
 * @since 2019-10-28
 */
public interface RcmIndexFullMapper extends BaseMapper<RcmIndex> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmIndexFull
	 * @return
	 */
	List<RcmIndexFullVO> selectRcmIndexFullPage(IPage page, RcmIndexFullVO rcmIndexFull);

	/**
	 *
	 * @param quotaIndexNum
	 * @return
	 */
	RcmIndexFullVO selectRcmIndexFullDetail(@Param("quotaIndexNum") String quotaIndexNum);
}
