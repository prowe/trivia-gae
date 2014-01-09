package com.rowe.trivia.domain.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.apache.commons.lang3.StringUtils;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.validation.PasswordConfirmed.PasswordConfirmedValidator;

@Constraint(validatedBy = PasswordConfirmedValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface PasswordConfirmed {

	String message() default "Passwords do not match";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	static class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, User> {

		@Override
		public void initialize(PasswordConfirmed constraintAnnotation) {
		}

		@Override
		public boolean isValid(User user, ConstraintValidatorContext context) {
			if(!StringUtils.equals(user.getPassword(), user.getPasswordConfirmation())){
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("passwordConfirmation")
					.addConstraintViolation();
				context.disableDefaultConstraintViolation();
				return false;
			}
			return true;
		}
	}
}
