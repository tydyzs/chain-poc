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
package org.git.modules.clm.loan.service.impl;

import org.git.modules.clm.loan.entity.BillEventMain;
import org.git.modules.clm.loan.vo.BillEventMainVO;
import org.git.modules.clm.loan.mapper.BillEventMainMapper;
import org.git.modules.clm.loan.service.IBillEventMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 票据用信事件主表 服务实现类
 *
 * @author git
 * @since 2019-11-11
 */
@Service
public class BillEventMainServiceImpl extends ServiceImpl<BillEventMainMapper, BillEventMain> implements IBillEventMainService {

	@Override
	public IPage<BillEventMainVO> selectBillEventMainPage(IPage<BillEventMainVO> page, BillEventMainVO billEventMain) {
		return page.setRecords(baseMapper.selectBillEventMainPage(page, billEventMain));
	}

}
