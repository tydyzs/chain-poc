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
package org.git.modules.clm.credit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.modules.clm.credit.entity.TbCrdStatis;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.clm.credit.mapper.TbCrdStatisMapper;
import org.git.modules.clm.credit.mapper.TbCrdStatisOrgMapper;
import org.git.modules.clm.credit.service.ITbCrdStatisOrgService;
import org.git.modules.clm.credit.service.ITbCrdStatisService;
import org.git.modules.clm.credit.vo.TbCrdStatisVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 额度统计表（实时） 服务实现类
 *
 * @author git
 * @since 2019-12-04
 */
@Service
public class TbCrdStatisServiceOrgImpl extends ServiceImpl<TbCrdStatisOrgMapper, TbCrdStatisOrg> implements ITbCrdStatisOrgService {


}
