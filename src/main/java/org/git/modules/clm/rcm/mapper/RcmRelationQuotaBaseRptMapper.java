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
package org.git.modules.clm.rcm.mapper;

import org.git.modules.clm.rcm.entity.RcmRelationQuotaBaseRpt;
import org.git.modules.clm.rcm.vo.RcmRelationQuotaBaseRptVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 关联客户授信集中度简表 Mapper 接口
 *
 * @author git
 * @since 2019-12-11
 */
public interface RcmRelationQuotaBaseRptMapper extends BaseMapper<RcmRelationQuotaBaseRpt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmRelationQuotaBaseRpt
	 * @return
	 */
	List<RcmRelationQuotaBaseRptVO> selectRcmRelationQuotaBaseRptPage(IPage page, RcmRelationQuotaBaseRptVO rcmRelationQuotaBaseRpt);

}
