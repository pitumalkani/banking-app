package com.bankingmanagement.dto.response;

import com.bankingmanagement.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long accountId;
    private BigDecimal currentBalance;
    private AccountType accountType;

}