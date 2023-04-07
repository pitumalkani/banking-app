package com.bankingmanagement.controller;


import com.bankingmanagement.common.Utils;
import com.bankingmanagement.controller.CustomerController;
import com.bankingmanagement.dto.request.CustomerDTO;
import com.bankingmanagement.enums.AccountType;
import com.bankingmanagement.model.Customer;
import com.bankingmanagement.service.AccountService;
import com.bankingmanagement.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CustomerControllerTest {
    private static final String CUST_FIRST_NAME = "John";
    private static final String CUST_LAST_NAME = "DOE";
    private final ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    private CustomerController sut;
    @Mock
    private AccountService accountService;
    @Mock
    private CustomerService customerService;

    @Test
    public void testAddCustomerWhenCustomerAlreadyExist() {
        when(customerService.addCustomer(any())).thenReturn(Optional.empty());
        ResponseEntity response = sut.addCustomer(getCustomerDTO());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Customer already exist!");
        verify(customerService, times(1)).addCustomer(any());
    }

    @Test
    public void testAddCustomer() {
        when(customerService.addCustomer(any())).thenReturn(Optional.of(getCustomer()));
        ResponseEntity response = sut.addCustomer(getCustomerDTO());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(customerService, times(1)).addCustomer(any());
    }

    @Test
    public void testSaveAccountWhenCustomerExist() throws JsonProcessingException {
        String jsonNodeString = "{\n" +
                "    \"accountType\":\"CURRENT\",\n" +
                "    \"credit\":2000\n" +
                "  \n" +
                "}";
        JsonNode newNode = mapper.readTree(jsonNodeString);
        when(customerService.getCustomerById(any())).thenReturn(Optional.of(getCustomerDTORespone()));
        ResponseEntity response = sut.saveAccount("customerId", newNode);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Account already exist!");
    }

    @Test
    public void testSaveAccountWhenCustomerDoesNotExist() throws JsonProcessingException {
        String jsonNodeString = "{\n" +
                "    \"accountType\":\"CURRENT\",\n" +
                "    \"credit\":2000\n" +
                "  \n" +
                "}";
        JsonNode newNode = mapper.readTree(jsonNodeString);
        ResponseEntity response = sut.saveAccount("customerId", newNode);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Customer does not exist!");
    }


    @Test
    public void testSaveAccountWhenCustomerAndAccountExist() throws JsonProcessingException {
        String jsonNodeString = "{\n" +
                "    \"accountType\":\"CURRENT\",\n" +
                "    \"credit\":2000\n" +
                "  \n" +
                "}";
        JsonNode newNode = mapper.readTree(jsonNodeString);
        when(customerService.getCustomerById(any())).thenReturn(Optional.of(getCustomerDTORespone()));
        when(accountService.createAccount(any(), any())).thenReturn(Optional.empty());
        ResponseEntity response = sut.saveAccount("customerId", newNode);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Account already exist!");
    }

    @Test
    public void testGetCustomerIdWhenCustomerDoesNotExist() throws JsonProcessingException {

        when(customerService.getCustomerById(any())).thenReturn(Optional.empty());
        ResponseEntity response = sut.getCustomerById("customerId");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Customer not found!");
    }

    @Test
    public void testGetCustomerId() throws JsonProcessingException {
        when(customerService.getCustomerById(any())).thenReturn(Optional.of(getCustomerDTORespone()));
        ResponseEntity response = sut.getCustomerById("customerId");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(customerService, times(1)).getCustomerById(any());
    }

    private CustomerDTO getCustomerDTO() {
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(CUST_FIRST_NAME);
        dto.setLastName(CUST_LAST_NAME);
        return dto;
    }

    private com.bankingmanagement.dto.response.CustomerDTO getCustomerDTORespone() {
        com.bankingmanagement.dto.response.CustomerDTO dto = new com.bankingmanagement.dto.response.CustomerDTO();
        dto.setFirstName(CUST_FIRST_NAME);
        dto.setLastName(CUST_LAST_NAME);
        return dto;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(CUST_FIRST_NAME);
        customer.setLastName(CUST_LAST_NAME);
        return customer;
    }

    private com.bankingmanagement.dto.response.AccountDTO getAccountDTO() {
        com.bankingmanagement.dto.response.AccountDTO dto = new com.bankingmanagement.dto.response.AccountDTO();
        dto.setAccountId(Long.valueOf(Utils.generateAccountNumber()));
        dto.setCurrentBalance(BigDecimal.TEN);
        dto.setAccountType(AccountType.CURRENT);
        return dto;
    }
}
