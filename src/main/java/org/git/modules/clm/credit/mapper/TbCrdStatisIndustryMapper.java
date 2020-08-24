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

import org.git.modules.clm.credit.entity.TbCrdStatisIndustry;
import org.git.modules.clm.credit.vo.TbCrdStatisIndustryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 额度统计表-行业（历史+实时） Mapper 接口
 *
 * @author git
 * @since 2019-12-04
 */
public interface TbCrdStatisIndustryMapper extends BaseMapper<TbCrdStatisIndustry> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdStatisIndustry
	 * @return
	 */
	List<TbCrdStatisIndustryVO> selectTbCrdStatisIndustryPage(IPage page, TbCrdStatisIndustryVO tbCrdStatisIndustry);

}
