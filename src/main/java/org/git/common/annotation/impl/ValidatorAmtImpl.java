package org.git.common.annotation.impl;

import org.git.common.annotation.ValidatorAmt;
import org.git.common.constant.CommonConstant;
import org.git.core.tool.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ValidatorAmtImpl implements ConstraintValidator<ValidatorAmt, Object> {
	private String pattern;


	@Override
	public void initialize(ValidatorAmt constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {//如果需要校验的值为空，返回成功
			return true;
		}

		//判断参数类型
		String s = null;
		if (value instanceof String) {
			s = String.valueOf(value);
		} else if (value instanceof BigDecimal) {
			s = String.valueOf(value);
		} else {
			return true;
		}

		if (StringUtil.isEmpty(s)) {//如果需要校验的值为空，返回成功
			return true;
		}

		if (s.matches(pattern)) {
			return true;
		}
		return false;
	}


}
