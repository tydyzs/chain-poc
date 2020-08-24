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
package org.git.modules.clm.param.service.impl;

import org.git.modules.clm.param.entity.TbParBorrow;
import org.git.modules.clm.param.vo.CustomerVO;
import org.git.modules.clm.param.vo.TbParBorrowVO;
import org.git.modules.clm.param.mapper.TbParBorrowMapper;
import org.git.modules.clm.param.service.ITbParBorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 串用组信息表 服务实现类
 *
 * @author git
 * @since 2020-03-31
 */
@Service
public class TbParBorrowServiceImpl extends ServiceImpl<TbParBorrowMapper, TbParBorrow> implements ITbParBorrowService {

	@Override
	public IPage<TbParBorrowVO> selectTbParBorrowPage(IPage<TbParBorrowVO> page, TbParBorrowVO tbParBorrow) {
		return page.setRecords(baseMapper.selectTbParBorrowPage(page, tbParBorrow));
	}

	@Override
	public IPage<CustomerVO> selectCustomerPage(IPage<CustomerVO> page, CustomerVO customerVO) {
		return page.setRecords(baseMapper.selectCustomerPage(page, customerVO));
	}


}
