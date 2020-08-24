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
package org.git.modules.clm.credit.mapper;

import org.git.modules.clm.credit.entity.TbCrdSubcontractCon;
import org.git.modules.clm.credit.vo.TbCrdSubcontractConVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 担保合同与业务合同关联关系 Mapper 接口
 *
 * @author git
 * @since 2019-11-14
 */
public interface TbCrdSubcontractConMapper extends BaseMapper<TbCrdSubcontractCon> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdSubcontractCon
	 * @return
	 */
	List<TbCrdSubcontractConVO> selectTbCrdSubcontractConPage(IPage page, TbCrdSubcontractConVO tbCrdSubcontractCon);

}
