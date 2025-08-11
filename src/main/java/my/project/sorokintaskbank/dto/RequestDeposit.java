package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestDeposit(
        @NotNull int id,
        @NotNull @DecimalMin(value = "0.00", inclusive = false, message = "The amount must not be null or negative.")
        BigDecimal amount
){}