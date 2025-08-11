package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.*;

public record UserCreateDto(
        @NotNull @Pattern(regexp = "USER|ADMIN", message = "Input USER or ADMIN") String role,
        @NotNull @Size(min = 2, max = 40, message = "Login size from 2 before 40 characters") String login
)
{}
