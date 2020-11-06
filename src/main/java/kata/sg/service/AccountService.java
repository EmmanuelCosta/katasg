package kata.sg.service;

import kata.sg.model.Account;
import kata.sg.model.Operation;
import kata.sg.model.OperationType;
import kata.sg.model.User;
import kata.sg.repository.AccountRepository;
import kata.sg.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    /**
     * add the given money to the correspondant account
     *
     * @param accountNbr : the accountNbr of the Account
     * @param money      : the money to add
     * @return the operation corresponing to the performed action
     */
    public Operation addMoney(String accountNbr, BigDecimal money) {

        if (accountNbr == null || accountNbr.isEmpty()) {
            throw new IllegalArgumentException("The account number can not be null or empty");
        }

        if (money == null) {
            throw new IllegalArgumentException("The given money can not be null");
        }
        Optional<Account> optionalAccount = this.accountRepository.findByAccountNumber(accountNbr);
        if (optionalAccount.isPresent()) {
            OperationType operationType = OperationType.CREDIT;

            Operation operation = saveOperation(money, optionalAccount.get(), operationType);
            return operation;

        }
        throw new IllegalArgumentException("There is no account with the given id " + accountNbr);
    }


    /**
     * substract the given money from the correspondant account
     *
     * @param accountNbr : the accountNbr of the Account
     * @param money      : the money to remove
     * @return the operation corresponing to the performed action
     */
    public Operation removeMoney(String accountNbr, BigDecimal money) {
        if (accountNbr == null || accountNbr.isEmpty()) {
            throw new IllegalArgumentException("The account number can not be null or empty");
        }

        if (money == null) {
            throw new IllegalArgumentException("The given money can not be null");
        }
        Optional<Account> optionalAccount = this.accountRepository.findByAccountNumber(accountNbr);
        if (optionalAccount.isPresent()) {
            if (optionalAccount.isPresent()) {
                OperationType operationType = OperationType.DEBIT;
                Operation operation = saveOperation(money, optionalAccount.get(), operationType);
                return operation;
            }
        }
        throw new IllegalArgumentException("There is no account with the given id " + accountNbr);
    }

    /**
     * return all operation performed in the account
     *
     * @param accountNbr : account number of the account
     * @return a set of Operation
     */
    public Set<Operation> getOperationsByAccountNbr(String accountNbr) {
        Optional<Account> optionalAccount = this.accountRepository.findByAccountNumber(accountNbr);
        if (optionalAccount.isPresent()) {
            return this.operationRepository.findByAccount(optionalAccount.get());
        }
        throw new IllegalArgumentException("There is no account with the given id " + accountNbr);

    }

    /**
     * this will add or substract money according to the operation type and persist the operation
     *
     * @param money         : money to add or substract
     * @param account       : account to apply the operation
     * @param operationType : type of operation to be performed
     * @return the persisted operation
     */
    private Operation saveOperation(BigDecimal money, Account account, OperationType operationType) {
        BigDecimal amount = account.getAmount();
        BigDecimal balance = null;
        if (OperationType.CREDIT.equals(operationType)) {
            balance = amount.add(money);
        } else {
            balance = amount.subtract(money);
        }
        account.setAmount(balance);
        Operation operation = new Operation();
        operation.setAmount(money);
        operation.setBalance(balance);
        operation.setOperationDate(LocalDateTime.now());
        operation.setType(operationType);
        User user = account.getUser();
        operation.setUser(user);
        operation.setAccount(account);

        operation = this.operationRepository.save(operation);
        return operation;
    }

}
