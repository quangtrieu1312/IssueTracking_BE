package com.trieutruong.project.validator;

import java.util.TimeZone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.trieutruong.project.annotation.ValidTimezone;

public class TimezoneValidator implements ConstraintValidator<ValidTimezone, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String[] validIDs = TimeZone.getAvailableIDs();
		for (String str : validIDs) {
			if (str != null && str.equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

}
