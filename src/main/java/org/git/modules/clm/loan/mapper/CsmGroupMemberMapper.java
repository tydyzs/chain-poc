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
package org.git.modules.clm.loan.mapper;

import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.loan.entity.CsmGroupMember;
import org.git.modules.clm.loan.vo.CsmGroupMemberVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 集团成员表 Mapper 接口
 *
 * @author git
 * @since 2019-12-22
 */
public interface CsmGroupMemberMapper extends BaseMapper<CsmGroupMember> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param csmGroupMember
	 * @return
	 */
	List<CsmGroupMemberVO> selectCsmGroupMemberPage(IPage page, CsmGroupMemberVO csmGroupMember);

	/**
	 * 查询集团成员信息
	 * @param page
	 * @param csmGroupMemberVO
	 * @param orgNum
	 * @return
	 */
	List<CsmGroupMemberVO> findCsmGroupMemberPage(IPage page,CsmGroupMemberVO csmGroupMemberVO,String orgNum);

	/**
	 * 查询集团客户信息
	 * @param page
	 * @param csmGroupMemberVO
	 * @param orgNum
	 * @return
	 */
	List<CsmGroupMemberVO> listCsmGroupMemberPage(IPage page,CsmGroupMemberVO csmGroupMemberVO,String orgNum);

}
