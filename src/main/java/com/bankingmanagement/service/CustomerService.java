package com.bankingmanagement.service;

import com.bankingmanagement.common.Utils;
import com.bankingmanagement.dto.request.CustomerDTO;
import com.bankingmanagement.mapper.CustomerMapper;
import com.bankingmanagement.model.Account;
import com.bankingmanagement.model.Customer;
import com.bankingmanagement.repo.AccountRepository;
import com.bankingmanagement.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    public Optional<com.bankingmanagement.dto.response.CustomerDTO> getCustomerById(final String customerId) {
        LOGGER.debug(">>GetCustomerById {} ", customerId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return Optional.empty();
        }
        com.bankingmanagement.dto.response.CustomerDTO customerDTO = CustomerMapper.INSTANCE.mapToCustomerDTO(customer.get());
        customer.get().getAccountDetails().stream().forEach(account -> {
            customerDTO.setCurrentBalance(account.getCurrentBalance());
        });
        LOGGER.debug(">>Fetched GetCustomerById Successful{} ", customerId);
        return Optional.ofNullable(customerDTO);
    }

    public Optional<Customer> addCustomer(final CustomerDTO customerDto) {
        // validate if customer already registered
        if (customerRepository.findByFirstName(customerDto.getFirstName()).isPresent() && customerRepository.findByLastName(customerDto.getLastName()).isPresent()) {
            LOGGER.debug(">>AddCustomer Customer with same details already exist {}, {} ", customerDto.getFirstName(),customerDto.getLastName());
            return Optional.empty();
        }
        LOGGER.debug(">>AddCustomer");
        Customer customer = CustomerMapper.INSTANCE.mapToCutomer(customerDto);
        customer.setCustomerId(Utils.generateCustomerID());
        LOGGER.info(">>AddCustomer Successful");
        return Optional.of(customerRepository.save(customer));
    }

    public void updateCustomer(final String customerId, final Account account ) {
        LOGGER.debug(">>UpdateCustomer");
        Customer customer = customerRepository.findById(customerId).get();
        LOGGER.debug(">>UpdateCustomer successful");
        customer.getAccountDetails().add(account);
    }

}
