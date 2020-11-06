package kata.sg.rest;

import kata.sg.model.Account;
import kata.sg.model.Operation;
import kata.sg.model.User;
import kata.sg.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class AccountControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AccountService accountService;


    @Test
    public void testDepositMoney() throws Exception {
        String accountNbr = "FR78XXX";
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setAccountNumber(accountNbr);
        Operation operation = new Operation();
        operation.setId(1L);
        operation.setAccount(account);

        operation.setUser(user);
        operation.setOperationDate(LocalDateTime.now());
        String value = "5000.00";
        operation.setBalance(new BigDecimal(25000));

        when(accountService.addMoney(any(String.class), any(BigDecimal.class)))
                .thenReturn(operation);

        String content = "{\"accountNumber\":\"FR76XXX\",\"amount\":\"" + value + "\"}";
        mockMvc.perform(post("/api/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testWithdrawMoney() throws Exception {
        String accountNbr = "FR78XXX";
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setAccountNumber(accountNbr);
        Operation operation = new Operation();
        operation.setId(1L);
        operation.setAccount(account);

        operation.setUser(user);
        operation.setOperationDate(LocalDateTime.now());
        String value = "5000.00";
        operation.setBalance(new BigDecimal(25000));

        when(accountService.removeMoney(any(String.class), any(BigDecimal.class)))
                .thenReturn(operation);

        String content = "{\"accountNumber\":\"FR76XXX\",\"amount\":\"" + value + "\"}";
        mockMvc.perform(post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetHistoric() throws Exception {
        String accountNbr = "FR78XXX";
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setAccountNumber(accountNbr);
        Operation operation = new Operation();
        operation.setId(1L);
        operation.setAccount(account);

        operation.setUser(user);
        operation.setOperationDate(LocalDateTime.now());
        String value = "5000.00";
        operation.setBalance(new BigDecimal(25000));
        Set<Operation> operations = new HashSet<>();
        operations.add(operation);

        when(accountService.getOperationsByAccountNbr(accountNbr))
                .thenReturn(operations);
        String content = "{\"accountNumber\":\"FR76XXX\",\"amount\":\"" + value + "\"}";
        mockMvc.perform(get("/api/account/historic?accountNbr="+accountNbr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}