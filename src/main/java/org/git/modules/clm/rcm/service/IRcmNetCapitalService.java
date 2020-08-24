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
package org.git.modules.clm.rcm.service;

import org.git.modules.clm.rcm.entity.RcmNetCapital;
import org.git.modules.clm.rcm.vo.RcmNetCapitalVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资本信息配置表 服务类
 *
 * @author git
 * @since 2019-10-24
 */
public interface IRcmNetCapitalService extends IService<RcmNetCapital> {


	/**
	 * 添加限额参数
	 *
	 * @param rcmNetCapitalVO
	 * @return
	 */
	boolean saveNetCapital(RcmNetCapitalVO rcmNetCapitalVO);


	/**
	 * 删除限额参数详情
	 *
	 * @param netCapitalNum
	 * @return
	 */
	boolean removeRcmNetCapital(String netCapitalNum);
}
