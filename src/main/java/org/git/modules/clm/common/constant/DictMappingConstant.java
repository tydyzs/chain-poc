package org.git.modules.clm.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段转码常量类
 *
 * @author chenchuan
 */
public class DictMappingConstant {
	/*
	信贷客户类型	01	企业
	信贷客户类型	02	事业单位
	信贷客户类型	03	党政机关及村级组织
	信贷客户类型	04	其它经济组织（含农村经济组织）
	信贷客户类型	05	同业客户
	信贷客户类型	06	农户
	信贷客户类型	07	个体工商户
	信贷客户类型	08	助学客户
	信贷客户类型	09	其它自然人客户
	客户类型	1	个人客户
	客户类型	2	公司客户
	客户类型	3	同业客户
	*/
	public static Map<String, String> customerTypeMap = new HashMap<String, String>();

	static {
		customerTypeMap.put("01", "2");
		customerTypeMap.put("02", "2");
		customerTypeMap.put("03", "2");
		customerTypeMap.put("04", "2");
		customerTypeMap.put("05", "3");
		customerTypeMap.put("06", "1");
		customerTypeMap.put("07", "1");
		customerTypeMap.put("08", "1");
		customerTypeMap.put("09", "1");
	}

	/*
	 *票据状态： CD000078 0 待兑付 1 提示付款 2 未用注销 3 实时清算 4 未用退回 5 待签发 9 提示付款（选择销登记簿）  E 结清
	 * 借据状态：CD000175 01正常  02逾期  03部分逾期  07核销  08销户  09票据置换  10资产置换  11股权置换
	 */
	public static Map<String, String> summaryStatusMap = new HashMap<String, String>();
	static {
		summaryStatusMap.put("0", "01");
		summaryStatusMap.put("1", "01");
		summaryStatusMap.put("3", "01");
		summaryStatusMap.put("5", "01");
		summaryStatusMap.put("9", "01");
		summaryStatusMap.put("2", "08");
		summaryStatusMap.put("4", "08");
		summaryStatusMap.put("E", "08");
	}


}
