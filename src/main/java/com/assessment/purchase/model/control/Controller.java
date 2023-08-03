package com.assessment.purchase.model.control;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.assessment.purchase.model.customer.CustomerRepository;
import com.assessment.purchase.model.customer.Customer;
import com.assessment.purchase.model.transaction.TransactionRepository;
import com.assessment.purchase.model.transaction.Transaction;

@RestController
@RequestMapping("/api/customers")
public class Controller {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest customerRequest) {
        // Extract data from the request
        String customerName = customerRequest.getName();
        LocalDate date = customerRequest.getDate();
        double purchasePrice = customerRequest.getPurchasePrice();

        // Create a new Customer instance
        Customer customer = new Customer();
        customer.setName(customerName);

        // Create a new Transaction instance
        Transaction transaction = new Transaction();
        transaction.setAmount(purchasePrice);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus("Pending");

        // Associate the Transaction with the Customer
        transaction.setCustomer(customer);

        // Save the Customer and Transaction to the database
        customerRepository.save(customer);
        transactionRepository.save(transaction);

        return ResponseEntity.ok("Customer created successfully");
    }
}
