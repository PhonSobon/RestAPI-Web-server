package co.istad.moblie_banking.api.auth.web;

import co.istad.moblie_banking.api.user.validator.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Password
        String password
) {
}
