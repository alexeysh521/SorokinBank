package my.project.sorokintaskbank.dto;

import jakarta.validation.constraints.NotNull;

public record RequestId (
        @NotNull int id
)
{}
