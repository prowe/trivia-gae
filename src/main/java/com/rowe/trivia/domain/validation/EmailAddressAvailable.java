package com.rowe.trivia.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.rowe.trivia.domain.validation.EmailAddressAvailable.EmailAddressAvailableValidator;
import com.rowe.trivia.repo.UserRepository;

@Constraint(validatedBy = EmailAddressAvailableValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface EmailAddressAvailable {
	
	String message() default "Email address already taken";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	public static class EmailAddressAvailableValidator implements ConstraintValidator<EmailAddressAvailable, String>{
		@Autowired
		private UserRepository userRepo;

		@Override
		public void initialize(EmailAddressAvailable constraintAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if(StringUtils.isNotBlank(value)){
				return userRepo.isUsernameAvailable(value);
			}
			return true;
		}
	}
}
