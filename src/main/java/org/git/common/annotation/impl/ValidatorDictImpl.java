package org.git.common.annotation.impl;

import org.git.common.annotation.ValidatorDict;
import org.git.common.cache.DictCache;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.system.entity.Dict;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidatorDictImpl implements ConstraintValidator<ValidatorDict, String> {
	private String code;

	@Override
	public void initialize(ValidatorDict constraintAnnotation) {
		this.code = constraintAnnotation.code();
	}

	@Override
	public boolean isValid(String key, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtil.isEmpty(key)) {//如果值为空，通过检验
			return true;
		}

		if(code.contains(",")){//如果传入的代码带有逗号，那就检验值是否符合代码
			return code.contains(key);
		}

		List<Dict> list = DictCache.getList(code);//查询所属字典代码
		for (Dict dict : list) {
			if (key.equals(dict.getDictKey())) {//判断字典代码是否和值匹配。
				return true;
			}
		}
		return false;
	}
}
