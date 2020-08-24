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
package org.git.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.SysCache;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * 通用工具类
 *
 * @author Chill
 */
public class CommonUtil {

	/**
	 * 判断贷款期限类型
	 * 按贷款期限的长短，可以将贷款分为短期贷款和中长期贷款。
	 * 1)、短期贷期限在1年或者1年内（3个月以上，6个月以下为临时贷款），，主要用于满足借款人对短期资金的需求。
	 * 2)、中期贷款期限都在1年以上（不含1年），五年以下（含5年），其特点是期限长、利率高、流动性差、风险大。
	 * 3)、长期贷款为5年以上（不含5年）贷款
	 *
	 * @param term
	 * @param termUnit
	 * @return
	 * @author chenchuan
	 */
	public static String getLoanTermType(BigDecimal term, String termUnit) {
		BigDecimal termM = term;
		if (JxrcbBizConstant.TERM_UNIT_Y.equals(termUnit)) {//年*12
			termM = term.multiply(new BigDecimal(12));
		} else if (JxrcbBizConstant.TERM_UNIT_M.equals(termUnit)) {//月
			termM = term;
		} else if (JxrcbBizConstant.TERM_UNIT_D.equals(termUnit)) {//日/30
			termM = term.divide(new BigDecimal(30), BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_UP);
		}

		if (termM.compareTo(new BigDecimal(12)) <= 0) {//短期
			return JxrcbBizConstant.TERM_TYPE_DQ;
		} else if (termM.compareTo(new BigDecimal(12)) > 0 && termM.compareTo(new BigDecimal(60)) <= 0) {//中期
			return JxrcbBizConstant.TERM_TYPE_ZQ;
		} else {//长期
			return JxrcbBizConstant.TERM_TYPE_CQ;
		}
	}

	/**
	 * String转换成BigDecimal，如果为空，返回0
	 *
	 * @param str
	 * @return BigDecimal
	 * @author chenchuan
	 */
	public static BigDecimal stringToBigDecimal(String str) {
		if (StringUtil.isEmpty(str)) {//如果字符串为空，返回0
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(str);
		}
	}

	/**
	 * BigDecimal，如果为空，返回0
	 * cc
	 *
	 * @param bigDecimal
	 * @return BigDecimal
	 * @author chenchuan
	 */
	public static BigDecimal nullToZero(BigDecimal bigDecimal) {
		if (bigDecimal == null) {//如果为空，返回0
			return BigDecimal.ZERO;
		} else {
			return bigDecimal;
		}
	}

	/**
	 * 获取当前登录用户ID
	 *
	 * @return
	 */
	public static String getCurrentUserId() {
		return SecureUtil.getUserId();
	}

	/**
	 * 获取当前登录机构ID
	 *
	 * @return
	 */
	public static String getCurrentOrgId() {
		return SecureUtil.getDeptId();
	}

	/**
	 * 获取当前营业日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getWorkDate() {
		return SysCache.getWorkDate();
	}

	/**
	 * 获取当前营业日期时间
	 *
	 * @return Timestamp
	 */
	public static Timestamp getWorkDateTime() {
		LocalDateTime newDateTime = getLocalWorkDateTime();
		return Timestamp.valueOf(newDateTime);
	}

	/**
	 * 获取当前营业日期时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getWorkDateTime2() {
		return getLocalWorkDateTime().format(DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME_ALL));
	}

	/**
	 * 判断当前是否月末
	 *
	 * @return
	 */
	public static boolean isLastDayOfMonth() {
		LocalDate endDay = getLocalWorkDate().with(TemporalAdjusters.lastDayOfMonth());
		return getLocalWorkDate().getDayOfMonth() == endDay.getDayOfMonth();
	}

	/**
	 * 获取当前年份 yyyy
	 */
	public static String getWorkYear() {
		return String.format("%4s", getLocalWorkDate().getYear()).replace(" ", "0");
	}

	/**
	 * 获取指定日期的年份 yyyy
	 */
	public static String getWorkYear(LocalDate date) {
		return String.format("%4s", date.getYear()).replace(" ", "0");
	}

	/**
	 * 获取当前月份 mm
	 */
	public static String getWorkMonth() {
		return String.format("%2s", getLocalWorkDate().getMonthValue()).replace(" ", "0");
	}

	/**
	 * 获取当指定日期的月份 mm
	 */
	public static String getWorkMonth(LocalDate date) {
		return String.format("%2s", date.getMonthValue()).replace(" ", "0");
	}

	/**
	 * 获取当前营业日期
	 *
	 * @return LocalDate
	 */
	public static LocalDate getLocalWorkDate() {
		return LocalDate.parse(getWorkDate(), DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATE));
	}

	/**
	 * 获取当前营业日期时间
	 *
	 * @return LocalDateTime
	 */
	public static LocalDateTime getLocalWorkDateTime() {
		return LocalDateTime.of(getLocalWorkDate(), LocalTime.now());
	}


	/**
	 * 获取当前批量日期
	 *
	 * @return
	 */
	public static String getBatchDate() {
		return SysCache.getBatchDate();
	}


	/**
	 * 根据金额和保证金比例计算敞口
	 *
	 * @param amt          金额
	 * @param depositRatio 保证金比例
	 * @return 敞口
	 */
	public static BigDecimal getExpAmt(BigDecimal amt, BigDecimal depositRatio) {
		if (depositRatio == null) {
			depositRatio = BigDecimal.ZERO;
		}
		if (amt == null) {
			return BigDecimal.ZERO;
		}
		return amt.multiply(new BigDecimal(100).subtract(depositRatio)).divide(new BigDecimal(100));//敞口额度
	}

	/**
	 * 根据金额和保证金比例计算敞口
	 *
	 * @param amt          金额
	 * @param depositRatio 保证金比例
	 * @return 敞口
	 */
	public static BigDecimal getExpAmt(String amt, String depositRatio) {
		BigDecimal amtB = new BigDecimal(amt);
		BigDecimal depositRatioB = new BigDecimal(depositRatio);
		return amtB.multiply(new BigDecimal(100).subtract(depositRatioB)).divide(new BigDecimal(100));//敞口额度
	}

}
