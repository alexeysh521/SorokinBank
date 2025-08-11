package my.project.sorokintaskbank.repository;

import my.project.sorokintaskbank.model.Account;
import my.project.sorokintaskbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAccountByUser(User user);

}
