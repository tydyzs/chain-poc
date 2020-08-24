package org.git.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.RandomType;
import org.git.core.tool.utils.StringUtil;

public class BizNumUtil {


	/**
	 * 字母（由业务传入，2位） +日期（yyyyMMdd HH:mm:ss）+ 6位序列号
	 *
	 * @param prefix
	 * @return
	 */
	public static String getBizNumWithDateTime(String prefix) {
		String date = DateUtil.format(DateUtil.now(), "yyyyMMddHHmmss");
		return getBizNum(prefix, date);
	}

	/**
	 * 字母（由业务传入，2位） + 日期（YYYYMMDD）+ 6位序列号
	 *
	 * @param prefix
	 * @return
	 */
	public static String getBizNumWithDate(String prefix) {
		String date = DateUtil.format(DateUtil.now(), "yyyyMMdd");
		return getBizNum(prefix, date);
	}

	/**
	 * 字母（由业务传入，2位）+ 日期（YYYYMMDD） + 6位序列号
	 * 默认
	 * @param prefix
	 * @return
	 */
	public static String getBizNum(String prefix) {
		return getBizNumWithDate(prefix);
	}

	private static String getBizNum(String prefix, String date) {
		String orgId = SecureUtil.getDeptId();
//		orgId = String.format("%5s", orgId).replace(' ', '0');//左填充0为5位
		String random = StringUtil.random(6, RandomType.INT);
		return prefix + date + random;
	}

	/**
	 * 获取业务流水号 SN + 14位日期 + 5位随机数
	 *
	 * @return
	 */
	public static String getBizSn() {
		String date = DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME_SHORT);
		String random = StringUtil.random(5, RandomType.INT);
		return "SN" + date + random;
	}


	public static void main(String[] args) {
		System.out.println(getBizNum("JJ"));
		System.out.println(getBizNumWithDate("JJ"));
		System.out.println(getBizNumWithDateTime("JJ"));
		System.out.println(StringUtils.leftPad("ccc", 5, '0'));
		System.out.println(String.format("%5s", "ccc").replace(' ', '0'));

	}
}
