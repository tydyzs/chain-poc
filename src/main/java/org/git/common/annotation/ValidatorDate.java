package org.git.common.annotation;

import org.git.common.annotation.impl.ValidatorDateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target(ElementType.FIELD)
//指明这个注解要作用在什么地方，可以是对象、域、构造器等，因为要作用在age域上，因此这里选择FIELD
@Retention(RetentionPolicy.RUNTIME)
//指明了注解的生命周期，可以有SOURCE（仅保存在源码中，会被编译器丢弃），
// CLASS（在class文件中可用，会被VM丢弃）以及RUNTIME（在运行期也被保留），这里选择了生命周期最长的RUNTIME
@Constraint(validatedBy = ValidatorDateImpl.class)
//是最关键的，它表示这个注解是一个验证注解，并且指定了一个实现验证逻辑的验证器
public @interface ValidatorDate {
	String message() default "日期格式不符合规范";

	String fmt();//日期格式

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
