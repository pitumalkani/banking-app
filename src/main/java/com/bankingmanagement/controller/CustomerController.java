package com.bankingmanagement.controller;


import com.bankingmanagement.dto.request.CustomerDTO;
import com.bankingmanagement.dto.response.AccountDTO;
import com.bankingmanagement.model.Customer;
import com.bankingmanagement.service.AccountService;
import com.bankingmanagement.service.CustomerService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final AccountService accountService;

    @Operation(tags = "Customer Controller", summary = "GetCustomerById", description = "Gets customer by Id")
    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable String id) {
        Optional<com.bankingmanagement.dto.response.CustomerDTO> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isEmpty()) {
            return new ResponseEntity<>("Customer not found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(optionalCustomer.get());
    }

    @Operation(tags = "Customer Controller", summary = "Create an new Account", description = "Creates an account for existing Customer")
    @PostMapping("/{customerId}/save-account")
    public ResponseEntity saveAccount(@PathVariable("customerId") String customerId, @RequestBody JsonNode requestJson) {
        if (customerService.getCustomerById(customerId).isEmpty()) {
            return new ResponseEntity<>("Customer does not exist!", HttpStatus.NOT_FOUND);
        }
        Optional<AccountDTO> account = accountService.createAccount(customerId, requestJson);
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account already exist!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(account.get());
    }

    @PostMapping()
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDTO) {
        // validate if customer already exist before adding
        Optional<Customer> customer = customerService.addCustomer(customerDTO);
        if (customer.isEmpty()) {
            return new ResponseEntity<>("Customer already exist!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(customer.get());
    }

}
