package my.project.sorokintaskbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.sorokintaskbank.dto.*;
import my.project.sorokintaskbank.model.User;
import my.project.sorokintaskbank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
/// Для создания таблиц есть файл в .resources ///

@RestController
@RequestMapping("/user/operation")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;

    @PostMapping("/account/create")// ACCOUNT_CREATE
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountCreateDto dto,
                                           @AuthenticationPrincipal User user) {
        accountService.forCreateAccount(dto, user);
        return ResponseEntity.ok("Account created successfully.");
    }

    @DeleteMapping("/account/close")// ACCOUNT_CLOSE
    public ResponseEntity<String> closeAccount(@Valid @RequestBody RequestId request,
                                          @AuthenticationPrincipal User user){
        accountService.forCloseAccount(request.id(), user);
        return ResponseEntity.ok("Account closed successfully.");
    }

    @PostMapping("/account/deposit")// ACCOUNT_DEPOSIT
    public ResponseEntity<String> depositAccount(@Valid @RequestBody RequestDeposit dto){
        accountService.forDepositAccount(dto.id(), dto.amount());
        return ResponseEntity.ok("Deposit successfully.");
    }

    @PostMapping("/account/transfer")// ACCOUNT_TRANSFER
    public ResponseEntity<String> transferAccounts(@Valid @RequestBody RequestTransfer dto){
        accountService.forTransferAccounts(dto.sender(), dto.recipient(), dto.amount());
        return ResponseEntity.ok("Transfer successfully.");
    }

    @PostMapping("/account/withdraw")// ACCOUNT_WITHDRAW
    public ResponseEntity<?> withdrawAccount(@Valid @RequestBody RequestWithdraw dto){
        accountService.forWithdrawAccount(dto.id(), dto.amount());
        return ResponseEntity.ok("Withdraw successfully.");
    }

}
