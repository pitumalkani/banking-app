package com.bankingmanagement.dto.request;

import com.bankingmanagement.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    private Long customerId;
    private BigDecimal currentBalance;
    private AccountType accountType;
}