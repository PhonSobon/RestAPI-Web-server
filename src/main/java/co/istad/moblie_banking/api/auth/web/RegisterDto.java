package co.istad.moblie_banking.api.auth.web;

import co.istad.moblie_banking.api.user.validator.email.EmailUnique;
import co.istad.moblie_banking.api.user.validator.password.PasswordMatch;
import co.istad.moblie_banking.api.user.validator.role.RoleIdConstraint;
import co.istad.moblie_banking.api.user.validator.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
@PasswordMatch(message = "your password is not match",password ="password",confirmedPassword = "confirmedPassword")
public record RegisterDto(
        @NotBlank(message = "Email is required")
        @EmailUnique
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Password
        String password,
        @NotBlank(message = "ConfirmedPassword is required")
        @Password
        String confirmedPassword,
        @NotNull(message = "Roles are required!")
        @RoleIdConstraint
        List<Integer> roleIds) {
}
