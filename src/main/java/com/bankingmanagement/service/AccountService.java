package com.bankingmanagement.service;

import com.bankingmanagement.common.Utils;
import com.bankingmanagement.dto.request.TransactionDTO;
import com.bankingmanagement.dto.response.AccountDTO;
import com.bankingmanagement.enums.AccountType;
import com.bankingmanagement.enums.TransactionType;
import com.bankingmanagement.mapper.AccountMapper;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.repo.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final AccountRepository accountRepository;

    public Optional<AccountDTO> createAccount(final String customerId, final JsonNode requestJson) {
        final AccountType accountType = AccountType.valueOf(requestJson.get("accountType").textValue());
        final BigDecimal initialCredit = BigDecimal.valueOf(requestJson.get("credit").asLong());
        if (accountRepository.findByCustomerIdAndAccountType(customerId, accountType).isPresent()) {
            LOGGER.debug(">>createAccount {} account already exist for customer {} ", accountType, customerId);
            return Optional.empty();
        }
        LOGGER.debug(">>createAccount {} account for customer {} ", accountType, customerId);
        AccountDTO accountDTO = AccountMapper.INSTANCE.mapToAccountDTO(this.createAccountAndTransaction(customerId, accountType, initialCredit));
        LOGGER.debug(">>createAccount successful {} ", customerId);
        return Optional.ofNullable(accountDTO);

    }

    private Account createAccountAndTransaction(final String customerId, final AccountType accountType, final BigDecimal initialCredit) {
        Account newAccount = new Account();
        newAccount.setAccountId(Utils.generateAccountNumber());
        newAccount.setAccountCreated(LocalDate.now());
        newAccount.setCustomerId(customerId);
        newAccount.setAccountType(accountType);
        accountRepository.save(newAccount);
        LOGGER.info("Created {} account successfully for customerId {}", accountType, customerId);
        if (!(initialCredit.compareTo(BigDecimal.ZERO) < 1)) {
            transactionService.addTransaction(this.getTransactionObject(newAccount.getAccountId(), initialCredit));
            newAccount.setCurrentBalance(initialCredit);
        }
        customerService.updateCustomer(customerId, newAccount);
        return newAccount;
    }

    private TransactionDTO getTransactionObject(final String accountId, final BigDecimal amount) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionDescripton("NEW_ACCOUNT_ADDED");
        dto.setAmount(amount);
        dto.setAccountId(accountId);
        dto.setTransactionType(TransactionType.CREDIT);
        return dto;
    }

}
