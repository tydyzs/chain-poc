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
package org.git.modules.clm.customer.service;

import org.git.modules.clm.customer.entity.CsmPhoneInfo;
import org.git.modules.clm.customer.vo.CsmPhoneInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 电话号码信息表 服务类
 *
 * @author git
 * @since 2019-11-27
 */
public interface ICsmPhoneInfoService extends IService<CsmPhoneInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param csmPhoneInfo
	 * @return
	 */
	IPage<CsmPhoneInfoVO> selectCsmPhoneInfoPage(IPage<CsmPhoneInfoVO> page, CsmPhoneInfoVO csmPhoneInfo);

}
