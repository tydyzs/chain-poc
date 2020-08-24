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
package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.entity.FundAssignedIn;
import org.git.modules.clm.credit.vo.FundAssignedInVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资金纯券过户事件表-转入 服务类
 *
 * @author git
 * @since 2020-01-15
 */
public interface IFundAssignedInService extends IService<FundAssignedIn> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundAssignedIn
	 * @return
	 */
	IPage<FundAssignedInVO> selectFundAssignedInPage(IPage<FundAssignedInVO> page, FundAssignedInVO fundAssignedIn);

}
