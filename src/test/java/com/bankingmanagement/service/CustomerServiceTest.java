package com.bankingmanagement.service;

import com.bankingmanagement.dto.request.CustomerDTO;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.model.Customer;
import com.bankingmanagement.repo.AccountRepository;
import com.bankingmanagement.repo.CustomerRepository;
import com.bankingmanagement.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService sut;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;

    @Test
    public void testGetCustomerByIdForNonExistingCustomer_NegativeScenario() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        Optional<com.bankingmanagement.dto.response.CustomerDTO> customer = sut.getCustomerById("priti1234");
        assertThat(customer.isEmpty()).isEqualTo(true);
        verify(customerRepository, times(1)).findById(any());
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(getCustomer()));
        Optional<com.bankingmanagement.dto.response.CustomerDTO> customer = sut.getCustomerById("priti1234");
        assertThat(customer.isPresent()).isEqualTo(true);
        assertThat(customer.get().getCurrentBalance().compareTo(BigDecimal.valueOf(3000))).isEqualTo(0);
        verify(customerRepository, times(1)).findById(any());

    }

    @Test
    public void testAddCustomerWhenCustomerAlreadyExist_NegativeScenario() {
        when(customerRepository.findByFirstName(any())).thenReturn(Optional.of(getCustomer()));
        when(customerRepository.findByLastName(any())).thenReturn(Optional.of(getCustomer()));
        Optional<Customer> customer = sut.addCustomer(getCustomerDTO());
        assertThat(customer.isPresent()).isEqualTo(false);
        verify(customerRepository, times(1)).findByFirstName(any());
        verify(customerRepository, times(1)).findByLastName(any());

    }

    @Test
    public void testAddCustomer() {
        when(customerRepository.findByFirstName(any())).thenReturn(Optional.empty());
        when(customerRepository.findByLastName(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(getCustomer());
        Optional<Customer> customer = sut.addCustomer(getCustomerDTO());
        assertThat(customer.isPresent()).isEqualTo(true);
        verify(customerRepository, times(1)).findByFirstName(any());
        verify(customerRepository, times(1)).findByFirstName(any());
        verify(customerRepository, times(1)).save(any());

    }

    private Customer getCustomer() {
        Account account = new Account();
        account.setCurrentBalance(BigDecimal.valueOf(3000));
        Customer c = new Customer();
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setAccountDetails(List.of(account));
        return c;
    }

    private CustomerDTO getCustomerDTO() {
        CustomerDTO c = new CustomerDTO();
        c.setFirstName("John");
        c.setLastName("Doe");
        return c;
    }

}