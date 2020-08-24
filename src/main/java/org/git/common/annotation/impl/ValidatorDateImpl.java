package org.git.common.annotation.impl;


import org.git.common.annotation.ValidatorDate;
import org.git.core.tool.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidatorDateImpl implements ConstraintValidator<ValidatorDate, String> {
	private String fmt;

	@Override
	public void initialize(ValidatorDate constraintAnnotation) {
		this.fmt = constraintAnnotation.fmt()==null ? "yyyy-MM-dd":constraintAnnotation.fmt();
	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtil.isEmpty(date)) {//如果值为空，通过检验
			return true;
		}
		date = date.trim();
		if(date.length()!=fmt.length()){
			return false;
		}
		return isValidDate(date,fmt);
	}

	public static boolean isValidDate(String str,String famat) {
		boolean convertSuccess=true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；famat 如：yyyy/MM/dd HH:mm
		SimpleDateFormat format = new SimpleDateFormat(famat);
		try {
		// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess=false;
		}
		return convertSuccess;
	}

}
