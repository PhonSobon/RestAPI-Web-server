package co.istad.moblie_banking.api.user.validator.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordMatchConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordMatch {
    String message() default "Password Match";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String password();
    String confirmedPassword();

    @Target({ TYPE })
    @Retention(RUNTIME)
     @interface List {
        PasswordMatch[] value();
    }
}
