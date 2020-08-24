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
package org.git.modules.clm.rcm.mapper;

import org.apache.ibatis.annotations.Param;
import org.git.modules.clm.rcm.entity.RcmConfigPara;
import org.git.modules.clm.rcm.vo.RcmConfigParaVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 限额参数配置信息表 Mapper 接口
 *
 * @author liuye
 * @since 2019-11-01
 */
public interface RcmConfigParaMapper extends BaseMapper<RcmConfigPara> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rcmConfigurationPara
	 * @return
	 */
	List<RcmConfigParaVO> selectRcmControlPage(IPage page, RcmConfigParaVO rcmConfigurationPara);

	/**
	 * 查看一条限额参数配置信息
	 * @param quotaNum
	 * @return
	 */
	List<RcmConfigParaVO> selectOneRcmControl(@Param("quotaNum") String quotaNum);

	/**
	 *
	 * @param rcmConfigurationParas
	 * @return
	 */
	int addRcmControl(@Param("rcmConfigurationParas") List<RcmConfigPara> rcmConfigurationParas);
}
