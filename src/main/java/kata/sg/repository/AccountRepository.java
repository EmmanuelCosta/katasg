package kata.sg.repository;

import kata.sg.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account,Long> {
    Optional<Account> findByAccountNumber(String accountNbr);
}
