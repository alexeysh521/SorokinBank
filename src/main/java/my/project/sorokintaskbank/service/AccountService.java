package my.project.sorokintaskbank.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.sorokintaskbank.dto.AccountCreateDto;
import my.project.sorokintaskbank.model.Account;
import my.project.sorokintaskbank.model.User;
import my.project.sorokintaskbank.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.07");

    public Account findById(int id){
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account not found."));
    }

    @Transactional
    public void forCreateAccount(AccountCreateDto dto, User user){
        Account account = new Account();
        account.setUser(user);
        account.setBalance(dto.balance());
        accountRepository.save(account);
    }

    @Transactional
    public void createDefaultAccount(User user){
        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
    }

    @Transactional
    public void forDepositAccount(int id, BigDecimal amount){
        Account account = foundAccountById(id);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void forWithdrawAccount(int id, BigDecimal amount){
        Account account = foundAccountById(id);

        if(account.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Not enough money.");

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void forTransferAccounts(int sender, int recipient, BigDecimal amount){
        Account send = foundAccountById(sender);
        Account rec = foundAccountById(recipient);

        BigDecimal commission = COMMISSION_RATE.multiply(amount);

        if(send.getBalance().subtract(commission).compareTo(amount) < 0)
            throw new IllegalArgumentException("Not enough money.");

        send.setBalance(send.getBalance().subtract(amount).subtract(commission));
        rec.setBalance(rec.getBalance().add(amount));

        accountRepository.save(send);
        accountRepository.save(rec);
    }

    @Transactional
    public void forCloseAccount(int acId, User user){
        if(!accountRepository.existsById(acId))
            throw new EntityNotFoundException("Account not already exists.");

        Account account = accountRepository.findAccountByUser(user)
                .stream()
                .filter(a -> a.getId() == acId)
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Account not found.")
                );

        accountRepository.delete(account);
    }

    private Account foundAccountById(int id){
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account id " + id + " not found."));
    }

}
