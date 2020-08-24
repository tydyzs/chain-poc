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
package org.git.modules.clm.param.mapper;

import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.param.entity.TbCrdBorrowDetail;
import org.git.modules.clm.param.vo.TbCrdBorrowDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 串用流水明细表 Mapper 接口
 *
 * @author git
 * @since 2020-03-31
 */
public interface TbCrdBorrowDetailMapper extends BaseMapper<TbCrdBorrowDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdBorrowDetail
	 * @return
	 */
	List<TbCrdBorrowDetailVO> selectTbCrdBorrowDetailPage(IPage page, @Param("tbCrdBorrowDetail") TbCrdBorrowDetailVO tbCrdBorrowDetail);

}
