package com.trieutruong.projectzero.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.trieutruong.projectzero.validator.TimezoneValidator;

@Documented
@Constraint(validatedBy = TimezoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimezone{
	String message() default "Must be valid time zone";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
