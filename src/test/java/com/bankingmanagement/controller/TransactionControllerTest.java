package com.bankingmanagement.controller;

import com.bankingmanagement.common.Utils;
import com.bankingmanagement.controller.TransactionController;
import com.bankingmanagement.dto.request.TransactionDTO;
import com.bankingmanagement.enums.TransactionType;
import com.bankingmanagement.model.Transaction;
import com.bankingmanagement.service.AccountService;
import com.bankingmanagement.service.CustomerService;
import com.bankingmanagement.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @InjectMocks
    private TransactionController sut;
    @Mock
    private TransactionService transactionService;

    @Test
    public void testAddTransactionFailed(){
        when(transactionService.addTransaction(any())).thenReturn(Optional.empty());
        ResponseEntity response = sut.addTransaction(getTransactionDTO());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Failed to create transaction!");
        verify(transactionService, times(1)).addTransaction(any());
    }
    @Test
    public void testAddTransaction(){
        when(transactionService.addTransaction(any())).thenReturn(Optional.of(getTransaction()));
        ResponseEntity response = sut.addTransaction(getTransactionDTO());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(transactionService, times(1)).addTransaction(any());
    }
    private TransactionDTO getTransactionDTO(){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(Utils.generateAccountNumber());
        transactionDTO.setTransactionType(TransactionType.CREDIT);
        transactionDTO.setAmount(BigDecimal.TEN);
        transactionDTO.setTransactionDescripton(TransactionType.CREDIT.toString());
        return transactionDTO;
    }
    private Transaction getTransaction(){
        Transaction transaction = new Transaction();
        transaction.setAccountId(Utils.generateAccountNumber());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAmount(BigDecimal.TEN);
        transaction.setTransactionDescripton(TransactionType.CREDIT.toString());
        return transaction;
    }

}
