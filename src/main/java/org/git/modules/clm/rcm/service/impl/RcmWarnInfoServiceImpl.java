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
package org.git.modules.clm.rcm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.rcm.entity.RcmWarnInfo;
import org.git.modules.clm.rcm.mapper.RcmWarnInfoMapper;
import org.git.modules.clm.rcm.service.IRcmWarnInfoService;
import org.git.modules.clm.rcm.vo.RcmWarnInfoQueryVO;
import org.git.modules.clm.rcm.vo.RcmWarnInfoVO;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 限额预警信息表 服务实现类
 *
 * @author git
 * @since 2019-12-04
 */
@Service
public class RcmWarnInfoServiceImpl extends ServiceImpl<RcmWarnInfoMapper, RcmWarnInfo> implements IRcmWarnInfoService {

	@Autowired
	IDeptService deptService;

	/**
	 * 按条件查询限额预警信息
	 * @param page
	 * @param rcmWarnInfo 查询视图实体类
	 * @return
	 */
	@Override
	public IPage<RcmWarnInfoVO> queryRcmWarnInfo(IPage<RcmWarnInfoVO> page, RcmWarnInfoQueryVO rcmWarnInfo) {
		RcmWarnInfoQueryVO rcmWarnInfoQueryVO = setRcmWarnInfoQueryVO(rcmWarnInfo);
		return page.setRecords(baseMapper.queryRcmWarnInfo(page, rcmWarnInfoQueryVO));
	}

   @Override
   public RcmWarnInfoVO selectById(RcmWarnInfoQueryVO rcmWarnInfo){
	   RcmWarnInfoQueryVO rcmWarnInfoQueryVO = setRcmWarnInfoQueryVO(rcmWarnInfo);
	   return baseMapper.queryRcmWarnDetail(rcmWarnInfoQueryVO);
   }

   private RcmWarnInfoQueryVO setRcmWarnInfoQueryVO(RcmWarnInfoQueryVO rcmWarnInfo){
	   /*获取登录用户机构信息*/
	   Dept dept = deptService.getById(CommonUtil.getCurrentOrgId());
	   /*登录用户机构类型赋值*/
	   rcmWarnInfo.setUserOrgType(dept.getOrgType());
	   /*登录用户机构号赋值*/
	   rcmWarnInfo.setUserOrgNum(CommonUtil.getCurrentOrgId());
	   if (rcmWarnInfo.getUseOrgNum() != null && !"".equals(rcmWarnInfo.getUseOrgNum())) {
		   /*若页面传入了机构,获取机构类型及法人机构*/
		   dept = deptService.getById(rcmWarnInfo.getUseOrgNum());
		   if (dept != null) {
			   /*机构类型赋值*/
			   rcmWarnInfo.setUseOrgType(dept.getOrgType());
			   /*机构号赋值*/
			   rcmWarnInfo.setUseOrgNum(rcmWarnInfo.getUseOrgNum());
		   }
	   }
	   return rcmWarnInfo;
   }

   @Override
   public int countByQuotaNum(String quotaNum){
		return baseMapper.countByQuotaNum(quotaNum);
   }
}
