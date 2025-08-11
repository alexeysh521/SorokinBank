package my.project.sorokintaskbank.dto;

import my.project.sorokintaskbank.model.Account;

import java.util.List;

public record UserInfoDto(
        int id,
        String login,
        String role,
        List<Account> accountList
)
{}
