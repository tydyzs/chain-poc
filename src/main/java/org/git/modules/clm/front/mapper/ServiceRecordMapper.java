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
package org.git.modules.clm.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.vo.ServiceRecordVO;

import java.util.List;

/**
 * 前置服务记录表 Mapper 接口
 *
 * @author caohaijie
 * @since 2019-09-25
 */
public interface ServiceRecordMapper extends BaseMapper<ServiceRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param serviceRecord
	 * @return
	 */
	List<ServiceRecordVO> selectServiceRecordPage(IPage page, ServiceRecordVO serviceRecord);

}
