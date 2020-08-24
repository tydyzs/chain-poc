package org.git.modules.clm.batch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface BatchMapper extends BaseMapper<T> {


	/**
	 * 获取所有有授信额度的客户
	 *
	 * @return List<String>
	 */
	List<String> listCustomerCredit();


	/**
	 * 获取所有有担保额度的客户
	 *
	 * @return List<String>
	 */
	List<String> listCustomerGrt();

	/**
	 * 获取所有有第三方额度的客户
	 *
	 * @return List<String>
	 */
	List<String> listCustomerThird();

	/**
	 * 获取所有有第三方额度的客户
	 *
	 * @return List<String>
	 */
	List<String> listCreditStatis();


	void ccStatis();

}
