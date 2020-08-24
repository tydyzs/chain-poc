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
package org.git.modules.clm.rcm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.log.exception.ServiceException;
import org.git.core.mp.support.Condition;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.RcmConfig;
import org.git.modules.clm.rcm.entity.RcmNetCapital;
import org.git.modules.clm.rcm.mapper.RcmNetCapitalMapper;
import org.git.modules.clm.rcm.service.IRcmConfigService;
import org.git.modules.clm.rcm.service.IRcmNetCapitalService;
import org.git.modules.clm.rcm.vo.RcmNetCapitalVO;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * 资本信息配置表 服务实现类
 *
 * @author git
 * @since 2019-10-24
 */
@Service
public class RcmNetCapitalServiceImpl extends ServiceImpl<RcmNetCapitalMapper, RcmNetCapital> implements IRcmNetCapitalService {

	@Autowired
	private IRcmConfigService rcmConfigService;

	@Autowired
	private IDeptService deptService;

	@Override
	@Transactional
	public boolean saveNetCapital(RcmNetCapitalVO rcmNetCapitalVO) {
		Assert.state(rcmNetCapitalVO != null && StringUtil.isNotBlank(rcmNetCapitalVO.getUseOrgNum()), "生效机构不能为空");
		Assert.state(deptService.isMemberBank(rcmNetCapitalVO.getUseOrgNum()), "请检查所选机构是否成员行！");
		Assert.state(rcmNetCapitalVO != null && StringUtil.isNotBlank(rcmNetCapitalVO.getUseDate()), "生效日期不能为空");
		LocalDate inputDate = LocalDate.parse(rcmNetCapitalVO.getUseDate());
		Assert.state(inputDate.compareTo(CommonUtil.getLocalWorkDate()) > 0, "生效日期必须大于当前日期");

		RcmNetCapitalVO params = new RcmNetCapitalVO();
		params.setUseOrgNum(rcmNetCapitalVO.getUseOrgNum());
		params.setNetState(RcmConstant.NET_STATE_EFFECTIVE);//生效状态：1.生效 2.失效
		RcmNetCapital result = baseMapper.selectOne(Condition.getQueryWrapper(params));
		//如果该机构配置为空，直接新增生效数据，生效日为当天（一个机构号某生效日只有一条生效数据）
		if (result == null) {
			result = new RcmNetCapitalVO();
			result.setNetCapitalNum(BizNumUtil.getBizNumWithDate(RcmConstant.RCM_NET_CAPITAL_TYPE));
			result.setNetState(RcmConstant.NET_STATE_EFFECTIVE);//生效状态：1.生效 2.失效
			result.setUseDate(CommonUtil.getWorkDate());//生效日期
		} else {//已查到当前存在生效记录
			//判断新增还是更新:当前在用的生效日期不等于当前输入日期
			if (!result.getUseDate().equals(rcmNetCapitalVO.getUseDate())) {
				RcmNetCapitalVO params2 = new RcmNetCapitalVO();
				params2.setUseOrgNum(rcmNetCapitalVO.getUseOrgNum());
				params2.setUseDate(rcmNetCapitalVO.getUseDate());
				RcmNetCapital result2 = baseMapper.selectOne(Condition.getQueryWrapper(params2));
				//要新增的日期已存在记录
				if (result2 != null) {
					result.setNetCapitalNum(result2.getNetCapitalNum());//更新目前已存在记录
					result.setNetState(result2.getNetState());
				} else {
					result.setNetCapitalNum(null);//如果不等于目前的生效日期则新增记录（主键自动生成）
					result.setNetState(RcmConstant.NET_STATE_UNEFFECTIVE);//生效状态：1.生效 2.失效
				}
			}
			result.setUseDate(rcmNetCapitalVO.getUseDate());//生效日期

		}
		result.setUseOrgNum(rcmNetCapitalVO.getUseOrgNum());
		result.setNetCapital(rcmNetCapitalVO.getNetCapital());
		result.setNetPrimaryCapital(rcmNetCapitalVO.getNetPrimaryCapital());
		result.setNetAssets(rcmNetCapitalVO.getNetAssets());
		result.setCreateTime(CommonUtil.getWorkDateTime());
		result.setUpdateTime(CommonUtil.getWorkDateTime());
		result.setUserNum(SecureUtil.getUserId());
		result.setOrgNum(SecureUtil.getDeptId());

		return saveOrUpdate(result);
	}


	@Override
	public boolean removeRcmNetCapital(String netCapitalNum) {
		RcmNetCapital rcmNetCapital = baseMapper.selectById(netCapitalNum);
		String useOrgNum = rcmNetCapital != null ? rcmNetCapital.getUseOrgNum() : "";
		RcmConfig rcmConfig = new RcmConfig();
		rcmConfig.setUseOrgNum(useOrgNum);
		rcmConfig = rcmConfigService.getOne(Condition.getQueryWrapper(rcmConfig));
		Assert.isNull(rcmConfig, "此机构已有生效限额配置，不能删除！");
		return this.removeById(netCapitalNum);
	}

}
