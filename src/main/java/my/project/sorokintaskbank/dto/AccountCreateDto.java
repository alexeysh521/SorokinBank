package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateDto(
        @NotNull @DecimalMin(value = "0.00", message = "The amount must not be negative.")
        BigDecimal balance
){}
