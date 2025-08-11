package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "The login must not be empty.")
        @Size(min = 2, max = 40, message = "Login is less than two or more than 40 characters long.")
        String login,

        @NotBlank(message = "The password cannot be empty.")
        @Size(min = 4, max = 20, message = "The password must be at least 4 and no more than 20 characters long.")
        String password,

        @NotBlank(message = "Enter the role.")
        @Pattern(regexp = "USER|ADMIN", message = "Invalid role")
        String role// ROLE_USER, ROLE_ADMIN,
)
{}
