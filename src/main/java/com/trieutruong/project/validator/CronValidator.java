package com.trieutruong.project.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.quartz.CronExpression;

import com.trieutruong.project.annotation.ValidCron;

public class CronValidator implements ConstraintValidator<ValidCron, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		} else {
			return CronExpression.isValidExpression(value);
		}
	}

}
