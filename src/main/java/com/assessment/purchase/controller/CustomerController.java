package com.assessment.purchase.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.purchase.model.customer.CustomerRepository;
import com.assessment.purchase.model.customer.Customer;
import com.assessment.purchase.model.customer.CustomerRequest;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public long createCustomer(@RequestBody CustomerRequest customerRequest) {
        // Extract data from the request
        String customerName = customerRequest.getName();

        // Create a new Customer instance
        Customer customer = new Customer();
        customer.setName(customerName);

        // Save the Customer to the database
        customerRepository.save(customer);

        long customerId = customer.getId();

        return customerId;
    }
    
}
