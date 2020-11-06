package kata.sg.service;

import kata.sg.model.Account;
import kata.sg.model.AccountType;
import kata.sg.model.Operation;
import kata.sg.model.User;
import kata.sg.repository.AccountRepository;
import kata.sg.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void testAddMoney01() {
        User user = createTestUser();
        String accountNbr = "FR77XXX";
        Account account = createTestAccount(user, accountNbr);
        BigDecimal bigDecimal = new BigDecimal(10000);
        Operation operation = accountService.addMoney(accountNbr, bigDecimal);
        Account persistedAccount = operation.getAccount();
        BigDecimal expected = bigDecimal.add(account.getAmount());
        BigDecimal amount = persistedAccount.getAmount();
        BigDecimal opAmount = operation.getAmount();
        BigDecimal balance = operation.getBalance();
        assertEquals(expected.stripTrailingZeros(), amount.stripTrailingZeros());
        assertEquals(bigDecimal.stripTrailingZeros(), opAmount.stripTrailingZeros());
        assertEquals(expected.stripTrailingZeros(), balance.stripTrailingZeros());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMoney02() {
        BigDecimal bigDecimal = new BigDecimal(10000);
        accountService.addMoney("NOT_EXISTED", bigDecimal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMoney03() {
        BigDecimal bigDecimal = new BigDecimal(10000);
        accountService.addMoney(null, bigDecimal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMoney04() {
        accountService.addMoney("AA", null);
    }

    @Test
    public void testRemoveMoney01() {
        User user = createTestUser();
        String accountNbr = "FR78XXX";
        Account account = createTestAccount(user, accountNbr);
        BigDecimal bigDecimal = new BigDecimal(10000);
        Operation operation = accountService.removeMoney(accountNbr, bigDecimal);
        Account persistedAccount = operation.getAccount();
        BigDecimal expected = bigDecimal.subtract(account.getAmount());
        BigDecimal amount = persistedAccount.getAmount();
        BigDecimal opAmount = operation.getAmount();
        BigDecimal balance = operation.getBalance();
        assertEquals(expected.stripTrailingZeros(), amount.stripTrailingZeros());
        assertEquals(bigDecimal.stripTrailingZeros(), opAmount.stripTrailingZeros());
        assertEquals(expected.stripTrailingZeros(), balance.stripTrailingZeros());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMoney02() {
        BigDecimal bigDecimal = new BigDecimal(10000);
        accountService.removeMoney("NOT_EXISTED", bigDecimal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMoney03() {
        BigDecimal bigDecimal = new BigDecimal(10000);
        accountService.removeMoney(null, bigDecimal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMoney04() {
        accountService.removeMoney("AA", null);
    }

    @Test
    public void testGetOperationsByAccountNbr01() {

        User user = createTestUser();
        String accountNbr = "FR85XXX";
        createTestAccount(user, accountNbr);
        BigDecimal bigDecimal = new BigDecimal(10000);
        Operation operation1 = accountService.removeMoney(accountNbr, bigDecimal);
        Operation operation2 = accountService.addMoney(accountNbr, bigDecimal);

        Set<Operation> operations = this.accountService.getOperationsByAccountNbr(accountNbr);

        assertEquals(2, operations.size());
        assertTrue(operations.contains(operation1));
        assertTrue(operations.contains(operation2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOperationsByAccountNbr02() {
        User user = createTestUser();
        String accountNbr = "FR86XXX";
        createTestAccount(user, accountNbr);
        BigDecimal bigDecimal = new BigDecimal(10000);
        accountService.removeMoney(accountNbr, bigDecimal);
        accountService.addMoney(accountNbr, bigDecimal);

        this.accountService.getOperationsByAccountNbr("UNKNOWN");
    }

    private Account createTestAccount(User user, String accountNbr) {
        Account account = new Account();
        account.setUser(user);
        account.setAmount(new BigDecimal(10000));
        account.setType(AccountType.CURRENT_ACCOUNT);
        account.setAccountNumber(accountNbr);
        account = accountRepository.save(account);
        return account;
    }

    private User createTestUser() {
        User user = new User();
        user.setLastName("TEST");
        user.setFirstName("TOP");
        user.setEmail("test@red.com");
        User save = userRepository.save(user);
        return save;
    }
}