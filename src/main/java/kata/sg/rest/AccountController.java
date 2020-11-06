package kata.sg.rest;

import kata.sg.model.Operation;
import kata.sg.model.dto.OperationDto;
import kata.sg.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/deposit")
    public OperationDto depositMoney(@RequestBody OperationDto operation) {
        String accountNbr = operation.getAccountNumber();
        BigDecimal amount = operation.getAmount();
        Operation operation1 = this.accountService.addMoney(accountNbr, amount);
        OperationDto operationDto = new OperationDto(accountNbr, operation1.getAmount(), operation1.getBalance(), operation1.getOperationDate());
        return operationDto;
    }

    @PostMapping("/withdraw")
    public OperationDto withdrawMoney(@RequestBody OperationDto operation) {
        String accountNbr = operation.getAccountNumber();
        BigDecimal amount = operation.getAmount();
        Operation operation1 = this.accountService.removeMoney(accountNbr, amount);
        OperationDto operationDto = new OperationDto(accountNbr, operation1.getAmount(), operation1.getBalance(), operation1.getOperationDate());
        return operationDto;
    }

    @GetMapping("/historic")
    public List<OperationDto> getHistory(@RequestParam String accountNbr) {
        Set<Operation> operationsByaccountNbr = this.accountService.getOperationsByAccountNbr(accountNbr);

        List<OperationDto> collect = operationsByaccountNbr.stream()
                .map(x -> {
                    OperationDto operationDto = new OperationDto(accountNbr, x.getAmount(), x.getBalance(), x.getOperationDate());
                    return operationDto;
                })
                .collect(Collectors.toList());
        return collect;
    }

}
