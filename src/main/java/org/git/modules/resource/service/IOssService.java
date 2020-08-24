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
package org.git.modules.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.resource.entity.Oss;
import org.git.modules.resource.entity.OssVO;
import org.git.core.mp.base.BaseService;

/**
 * 服务类
 *
 * @author Chain
 * @since 2019-05-24
 */
public interface IOssService extends BaseService<Oss> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param oss
	 * @return
	 */
	IPage<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss);

	/**
	 * 启动配置
	 *
	 * @param id
	 * @return
	 */
	boolean enable(Long id);

}
