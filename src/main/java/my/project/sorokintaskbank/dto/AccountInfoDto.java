package my.project.sorokintaskbank.dto;

import my.project.sorokintaskbank.model.User;

import java.math.BigDecimal;

public record AccountInfoDto(
        int id,
        BigDecimal balance,
        User user
)
{}
