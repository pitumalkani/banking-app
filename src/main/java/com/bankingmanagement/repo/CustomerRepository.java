package com.bankingmanagement.repo;

import com.bankingmanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByFirstName(String firstName);

    Optional<Customer> findByLastName(String lastName);
}
