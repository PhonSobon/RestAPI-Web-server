package co.istad.moblie_banking.api.user.validator.email;

import co.istad.moblie_banking.api.user.validator.email.EmailUniqueConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = EmailUniqueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface EmailUnique {
    String message() default "Email is already exists";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
