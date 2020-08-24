package org.git.modules.drools.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Drools {

	String name() default "";
	String code() default "";
	String explain() default "";

}
