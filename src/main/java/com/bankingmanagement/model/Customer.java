package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Table(name="CUSTOMER")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Customer {

    @Id
    @Column(name = "CUSTOMER_ID", nullable = false)
    private String customerId;
    @Column(name = "CUSTOMER_FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "CUSTOMER_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name="CUSTOMER_ACCOUNTS")
    @ElementCollection
    private List<Account> accountDetails = new ArrayList<>();
}
