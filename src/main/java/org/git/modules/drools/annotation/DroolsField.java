package org.git.modules.drools.annotation;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DroolsField {
	String name() default "";
	String explain() default "";
}
