package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestTransfer(
        @NotNull(message="The field must not be empty.") int sender,
        @NotNull(message="The field must not be empty.") int recipient,

        @NotNull(message="The field must not be empty.")
        @DecimalMin(value = "0.00", inclusive = false, message = "The amount must not be null or negative.")
        BigDecimal amount
){}
