package com.bankingmanagement.service;

import com.bankingmanagement.common.Utils;
import com.bankingmanagement.dto.request.TransactionDTO;
import com.bankingmanagement.enums.AccountType;
import com.bankingmanagement.enums.TransactionType;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.model.Transaction;
import com.bankingmanagement.repo.AccountRepository;
import com.bankingmanagement.repo.TransactionRepository;
import com.bankingmanagement.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService sut;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;


    @Test
    public void testAddTransaction() {
        Account account = getAccount();
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        Optional<Transaction> transactionOptional = sut.addTransaction(getTransactionDTO());
        assertThat(transactionOptional.get()).isNotNull();
        assertThat(transactionOptional.get().getTransactionType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transactionOptional.get().getCurrentBalance()).isEqualTo(BigDecimal.valueOf(20));
        verify(accountRepository, times(1)).findById(any());
        verify(accountRepository, times(1)).save(any());
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testAddTransactionForNonExistingAccount() {
        Account account = getAccount();
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        Optional<Transaction> transactionOptional = sut.addTransaction(getTransactionDTO());
        assertThat(transactionOptional.isEmpty()).isEqualTo(true);
    }

    private Account getAccount() {
        Account account = new Account();
        account.setCurrentBalance(BigDecimal.TEN);
        account.setAccountType(AccountType.CURRENT);
        return account;
    }

    private TransactionDTO getTransactionDTO() {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionType(TransactionType.CREDIT);
        dto.setAmount(BigDecimal.TEN);
        dto.setTransactionDescripton(TransactionType.CREDIT.toString());
        dto.setAccountId(Utils.generateAccountNumber());
        return dto;
    }

}
