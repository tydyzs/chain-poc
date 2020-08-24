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

import org.git.modules.clm.credit.entity.TbCrdSum;
import org.git.modules.clm.credit.vo.CrdSumVO;
import org.git.modules.clm.credit.vo.TbCrdSumVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 额度汇总表（实时） Mapper 接口
 *
 * @author git
 * @since 2019-12-04
 */
public interface TbCrdSumMapper extends BaseMapper<TbCrdSum> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdSum
	 * @return
	 */
	List<TbCrdSumVO> selectTbCrdSumPage(IPage page, TbCrdSumVO tbCrdSum);

	/**
	 * 担保额度汇总分页
	 * @param page
	 * @param crdSumVO
	 * @return
	 */
	List<CrdSumVO> selectTbCrdGuaranteeSumPage(IPage page, CrdSumVO crdSumVO);

}
