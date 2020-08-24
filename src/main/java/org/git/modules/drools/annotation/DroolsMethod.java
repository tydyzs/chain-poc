package org.git.modules.drools.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DroolsMethod {

	String name() default "";
	String explain() default "";
}
