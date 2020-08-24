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
package org.git.modules.clm.loan.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.loan.entity.BillEventDetail;
import org.git.modules.clm.loan.entity.BillEventMain;
import org.git.modules.clm.loan.entity.CrdMain;

import java.io.Serializable;
import java.util.List;

/**
 * 额度主表（客户+二级额度产品+机构）数据传输对象实体类
 *
 * @author git
 * @since 2019-11-12
 */
@Data
public class LoanBillUseEventDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int customerNumStatus;
	private BillEventMain billEventMain;
	private List<BillEventDetail> billEventDetail;
}
