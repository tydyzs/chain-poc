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
package org.git.modules.clm.credit.mapper;

import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.vo.CrdGrantingSerialVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 额度授信流水 Mapper 接口
 *
 * @author liuye
 * @since 2019-11-15
 */
public interface CrdGrantingSerialMapper extends BaseMapper<CrdGrantingSerial> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crdGrantingSerial
	 * @return
	 */
	List<CrdGrantingSerialVO> selectCrdGrantingSerialPage(IPage page, CrdGrantingSerialVO crdGrantingSerial);

	/**
	 * 查询同业客户额度台账额度授信情况
	 * @param page
	 * @param customerNum
	 * @return
	 */
	List<CrdGrantingSerialVO> findLedgerCrdGrantingSerialPage(IPage<CrdGrantingSerialVO> page,String customerNum,String orgNum);
}
