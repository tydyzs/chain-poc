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
package org.git.modules.clm.credit.service.impl;

import org.git.modules.clm.credit.entity.FundEarmarkMain;
import org.git.modules.clm.credit.vo.FundEarmarkMainVO;
import org.git.modules.clm.credit.mapper.FundEarmarkMainMapper;
import org.git.modules.clm.credit.service.IFundEarmarkMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资金授信圈存主表 服务实现类
 *
 * @author git
 * @since 2019-12-03
 */
@Service
public class FundEarmarkMainServiceImpl extends ServiceImpl<FundEarmarkMainMapper, FundEarmarkMain> implements IFundEarmarkMainService {

	@Override
	public IPage<FundEarmarkMainVO> selectFundEarmarkMainPage(IPage<FundEarmarkMainVO> page, FundEarmarkMainVO fundEarmarkMain) {
		return page.setRecords(baseMapper.selectFundEarmarkMainPage(page, fundEarmarkMain));
	}

}
