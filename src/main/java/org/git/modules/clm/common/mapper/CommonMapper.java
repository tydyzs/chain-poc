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
package org.git.modules.clm.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.credit.entity.TbCrdApprove;
import org.git.modules.clm.credit.vo.TbCrdApproveVO;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.desk.vo.NoticeVO;

import java.util.List;

/**
 * 批复信息表 Mapper 接口
 *
 * @author git
 * @since 2019-11-14
 */
public interface CommonMapper extends BaseMapper<T> {


	void creditRecount(String customerNum);

	void thirdRecount(String customerNum);

	void guaranteeRecount(String customerNum);

	void creditStatisCsm(String customerNum);

	void creditStatis();

	void creditStatisHs();

	/**
	 * 自定义分页
	 *
	 * @param page   分页
	 * @param noticeVO 实体
	 * @return List<NoticeVO>
	 */
	List<NoticeVO> selectNoticePage(@Param("page") IPage page, @Param("noticeVO") NoticeVO noticeVO);

}
