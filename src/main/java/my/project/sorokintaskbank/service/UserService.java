package my.project.sorokintaskbank.service;

import lombok.RequiredArgsConstructor;
import my.project.sorokintaskbank.dto.AccountCreateDto;
import my.project.sorokintaskbank.dto.UserInfoDto;
import my.project.sorokintaskbank.model.Account;
import my.project.sorokintaskbank.model.User;
import my.project.sorokintaskbank.repository.AccountRepository;
import my.project.sorokintaskbank.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    public boolean existsByLogin(String userName){
        return userRepository.existsByLogin(userName);
    }

    @Transactional
    public void create(User user){
        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
    }

    public List<UserInfoDto> forViewAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::fromDto)
                .toList();
    }

    public UserInfoDto fromDto(User user){
        return new UserInfoDto(
                user.getId(),
                user.getLogin(),
                user.getRole(),
                user.getAccountList()
        );
    }

}
