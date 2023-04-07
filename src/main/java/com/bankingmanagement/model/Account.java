package com.bankingmanagement.model;

import com.bankingmanagement.enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "ACCOUNT")

public class Account {
    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    private String accountId;
    @Column(name = "CUSTOMER_ID", nullable = false)
    private String customerId;
    @Column(name = "CURRENT_BALANCE", nullable = false)
    private BigDecimal currentBalance = BigDecimal.valueOf(0.0);
    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 30, nullable = false)
    private AccountType accountType;
    @Column(name = "ACCOUNT_CREATED")
    private LocalDate accountCreated;
    @ElementCollection
    @Column(name = "ACCOUNT_TRANSACTIONS")
    private List<Transaction> acountTransactions = new ArrayList<>();
}
