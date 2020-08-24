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

import org.git.modules.clm.credit.entity.FundAdmitDetail;
import org.git.modules.clm.credit.vo.FundAdmitDetailVO;
import org.git.modules.clm.credit.mapper.FundAdmitDetailMapper;
import org.git.modules.clm.credit.service.IFundAdmitDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资金客户状态维护明细 服务实现类
 *
 * @author git
 * @since 2019-11-28
 */
@Service
public class FundAdmitDetailServiceImpl extends ServiceImpl<FundAdmitDetailMapper, FundAdmitDetail> implements IFundAdmitDetailService {

	@Override
	public IPage<FundAdmitDetailVO> selectFundAdmitDetailPage(IPage<FundAdmitDetailVO> page, FundAdmitDetailVO fundAdmitDetail) {
		return page.setRecords(baseMapper.selectFundAdmitDetailPage(page, fundAdmitDetail));
	}

}
