package com.bankingmanagement.controller;

import com.bankingmanagement.dto.request.TransactionDTO;
import com.bankingmanagement.model.Transaction;
import com.bankingmanagement.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    @Operation(tags = "Transaction Controller", summary = "Add transaction to customer account", description = "Adds transaction to existing account")
    public ResponseEntity addTransaction(@RequestBody TransactionDTO transactionDTO) {
        Optional<Transaction> transaction = transactionService.addTransaction(transactionDTO);
        if (transaction.isEmpty()) {
            return new ResponseEntity<>("Failed to create transaction!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
    }
}
