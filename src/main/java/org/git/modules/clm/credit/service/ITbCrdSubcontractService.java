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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;
import org.git.modules.clm.credit.vo.TbCrdSubcontractVO;

/**
 * 担保合同信息表 服务类
 *
 * @author git
 * @since 2019-11-14
 */
public interface ITbCrdSubcontractService extends IService<TbCrdSubcontract> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tbCrdSubcontract
	 * @return
	 */
	IPage<TbCrdSubcontractVO> selectTbCrdSubcontractPage(IPage<TbCrdSubcontractVO> page, TbCrdSubcontractVO tbCrdSubcontract);

	/**
	 * 限额台账  对外担保信息数据
	 * @param page
	 * @param customerNum
	 * @return
	 */
	IPage<TbCrdSubcontractVO> selectTbCrdSubcontractList(IPage<TbCrdSubcontractVO> page, String customerNum,String orgNum);

	/**
	 * 查询合同详情中担保合同信息
	 * @param page
	 * @param contractNum 合同编号
	 * @return
	 */
	IPage<TbCrdSubcontractVO> queryCrdSubcontractPage(IPage page, String contractNum);

}
