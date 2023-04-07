package com.bankingmanagement.service;

import com.bankingmanagement.dto.response.AccountDTO;
import com.bankingmanagement.enums.AccountType;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.repo.AccountRepository;
import com.bankingmanagement.service.AccountService;
import com.bankingmanagement.service.CustomerService;
import com.bankingmanagement.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;
    @Mock
    private TransactionService transactionService;
    @Mock
    private CustomerService customerService;
    @Mock
    private AccountRepository accountRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateAccount() throws JsonProcessingException {
        String jsonNodeString = "{\n" +
                "    \"accountType\":\"CURRENT\",\n" +
                "    \"credit\":2000\n" +
                "  \n" +
                "}";
        JsonNode newNode = mapper.readTree(jsonNodeString);
        when(accountRepository.findByCustomerIdAndAccountType(any(), any())).thenReturn(Optional.empty());
        when(transactionService.addTransaction(any())).thenReturn(Optional.empty());
        doNothing().when(customerService).updateCustomer(any(), any());
        Optional<AccountDTO> accountDTO = sut.createAccount("customer123", newNode);
        assertThat(accountDTO.isPresent()).isEqualTo(true);
        assertThat(accountDTO.get().getAccountType()).isEqualTo(AccountType.CURRENT);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void testCreateAccountWhenAccountAlreadyExist() throws JsonProcessingException {
        String jsonNodeString = "{\n" +
                "    \"accountType\":\"CURRENT\",\n" +
                "    \"credit\":2000\n" +
                "  \n" +
                "}";
        JsonNode newNode = mapper.readTree(jsonNodeString);
        when(accountRepository.findByCustomerIdAndAccountType(any(), any())).thenReturn(Optional.of(new Account()));
        Optional<AccountDTO> accountDTO = sut.createAccount("customer123", newNode);
        assertThat(accountDTO.isPresent()).isEqualTo(false);
        verify(accountRepository, times(1)).findByCustomerIdAndAccountType(any(), any());
    }

}