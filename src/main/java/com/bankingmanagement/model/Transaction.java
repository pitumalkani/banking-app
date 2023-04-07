package com.bankingmanagement.model;

import com.bankingmanagement.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="Transaction")
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    private String transactionId;

    @Column(name="ACCOUNT_NUMBER",nullable = false)
    private String accountId;
    @Column(name="AMOUNT",nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name="TRANSFER_TYPE",nullable = false)
    private TransactionType transactionType;
    @Column(name="TRANSACTION_DATE")
    private LocalDate transactionDate;
    @Column(name="CURRENT_BALANCE",nullable = false)
    private BigDecimal currentBalance;
    @Column(name="TRANSACTION_DESCRIPTION")
    private String transactionDescripton;
}
