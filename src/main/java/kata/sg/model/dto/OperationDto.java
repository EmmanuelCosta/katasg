package kata.sg.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationDto {

    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime localDateTime;

    public OperationDto(String accountNumber, BigDecimal amount, BigDecimal balance, LocalDateTime localDateTime) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balance = balance;
        this.localDateTime = localDateTime;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
