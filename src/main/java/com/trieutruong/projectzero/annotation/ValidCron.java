package com.trieutruong.projectzero.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.trieutruong.projectzero.validator.CronValidator;


@Documented
@Constraint(validatedBy = CronValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCron {
	String message() default "Must be valid quartz cron expression";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
