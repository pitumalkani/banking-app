package com.bankingmanagement.dto.request;

import com.bankingmanagement.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDTO {
    @NotNull
    private String accountId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private String transactionDescripton;
}
