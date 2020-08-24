package org.git.modules.clm.batch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.batch.mapper.BatchMapper;
import org.git.modules.clm.batch.service.IBatchService;
import org.git.modules.clm.common.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchSeviceImpl extends ServiceImpl<BatchMapper, T> implements IBatchService {


	@Autowired
	ICommonService iCommonService;

	/**
	 * 执行批量授信额度重算
	 *
	 * @author chenchuancu's
	 */
	@Override
	public String btCreditRecount() {
		String msg = null;
		//查询所有存在授信额度的客户信息
		List<String> list = baseMapper.listCustomerCredit();

		//调用额度重算
		for (String customer : list) {
			msg = iCommonService.creditRecount(customer);
			if (!StringUtil.isEmpty(msg)) {
				return msg;
			}
		}
		return null;
	}

	/**
	 * 执行批量担保额度重算
	 *
	 * @author chenchuan
	 */
	@Override
	public String btGuaranteeRecount() {
		String msg = null;
		//查询所有存在授信额度的客户信息
		List<String> list = baseMapper.listCustomerGrt();
		//调用额度重算
		for (String customer : list) {
			msg = iCommonService.guaranteeRecount(customer);
			if (!StringUtil.isEmpty(msg)) {
				return msg;
			}
		}
		return null;
	}

	/**
	 * 执行批量第三方额度重算
	 *
	 * @author chenchuan
	 */
	@Override
	public String btThirdRecount() {
		String msg = null;
		//查询所有存在授信额度的客户信息
		List<String> list = baseMapper.listCustomerThird();
		//调用额度重算
		for (String customer : list) {
			msg = iCommonService.thirdRecount(customer);
			if (!StringUtil.isEmpty(msg)) {
				return msg;
			}
		}
		return null;
	}


	/**
	 * 执行批量额度统计
	 *
	 * @author chenchuan
	 */
	@Override
	public String btCreditStatis() {
		String msg = null;
		//查询所有存在授信额度的客户信息
		List<String> list = baseMapper.listCreditStatis();
		//调用额度重算
		for (String customer : list) {
			msg = iCommonService.creditStatisCsm(customer);
			if (!StringUtil.isEmpty(msg)) {
				return msg;
			}
		}
		msg = iCommonService.creditStatis();
		if (!StringUtil.isEmpty(msg)) {
			return msg;
		}
		return null;
	}

	/**
	 * 信用卡额度处理
	 *
	 * @author chenchuan
	 */
	@Override
	public String btCcStatis() {
		try {
			baseMapper.ccStatis();
		} catch (Exception e) {
			e.printStackTrace();
			return "信用卡额度处理失败！";
		}
		return null;
	}

}
