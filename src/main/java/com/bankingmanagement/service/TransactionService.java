package com.bankingmanagement.service;

import com.bankingmanagement.common.Utils;
import com.bankingmanagement.dto.request.TransactionDTO;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.model.Transaction;
import com.bankingmanagement.repo.AccountRepository;
import com.bankingmanagement.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public Optional<Transaction> addTransaction(TransactionDTO transactionDTO) {
        Optional<Account> acc = accountRepository.findById(transactionDTO.getAccountId());
        if (acc.isEmpty()) {
            LOGGER.debug(">>addTransaction failed, account {} not found", transactionDTO.getAccountId());
            return Optional.empty();
        }
        Account account = acc.get();
        account.setCurrentBalance(account.getCurrentBalance().add(transactionDTO.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(Utils.generateTransactionID());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAccountId(transactionDTO.getAccountId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setCurrentBalance((account.getCurrentBalance()));
        transaction.setTransactionDescripton(transactionDTO.getTransactionDescripton());
        account.getAcountTransactions().add(transaction);

        transactionRepository.save(transaction);
        accountRepository.save(account);
        LOGGER.debug(">>addTransaction successful, for account {}", transactionDTO.getAccountId());
        return Optional.of(transaction);

    }
}

