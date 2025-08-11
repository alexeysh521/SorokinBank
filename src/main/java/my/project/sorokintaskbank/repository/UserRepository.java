package my.project.sorokintaskbank.repository;

import my.project.sorokintaskbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

}
