package com.bankingmanagement.dto.response;

import com.bankingmanagement.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private BigDecimal currentBalance;
    private List<Account> accountDetails;
}


