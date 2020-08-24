package org.git.common.utils;

import org.hibernate.validator.HibernateValidator;
import org.git.core.tool.utils.StringUtil;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {


	/**
	 * 使用hibernate的注解来进行验证
	 * failFast(true)一旦验证失败马上报错
	 * failFast(false)全部验证完成后报错
	 */
	private static Validator validator = Validation
		.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

	/**
	 * 验证对象
	 *
	 * @param obj
	 * @param <T>
	 */
	public static <T> void validate(T obj) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
		// 抛出检验异常
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
			StringBuilder sb = StringUtil.builder();
			while (it.hasNext()) {
				ConstraintViolation violation = it.next();
				String key = violation.getPropertyPath().toString();
				String value = violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "";
				String msg = violation.getMessage();
				sb.append("【" + key + " = " + value + "】: " + msg);
			}
			Assert.isTrue(false, "数据校验失败： " + sb.toString());
		}
	}

	/**
	 * 正则表达式校验方法
	 *
	 * @param str
	 * @param patt
	 * @return
	 * @author chenchuan
	 */
	public static boolean checkVailde(String str, String patt) {
		Pattern pattern = Pattern.compile(patt);
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
