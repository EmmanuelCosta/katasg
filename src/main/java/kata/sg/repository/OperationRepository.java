package kata.sg.repository;

import kata.sg.model.Account;
import kata.sg.model.Operation;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OperationRepository extends CrudRepository<Operation, Long> {


    Set<Operation> findByAccount(Account account);
}
