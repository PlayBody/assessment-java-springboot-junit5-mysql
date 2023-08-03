package com.assessment.purchase.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.purchase.model.transaction.TransactionRepository;
import com.assessment.purchase.model.customer.Customer;
import com.assessment.purchase.model.transaction.Transaction;
import com.assessment.purchase.service.TransactionService;

@RestController
@RequestMapping("/api/customers")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest customerRequest) {
        // Extract data from the request
        int customerId = customerRequest.getCustomerId();
        int purchasePrice = customerRequest.getPurchasePrice();
        LocalDateTime date = LocalDateTime.now();

        // Create a new Transaction instance
        Transaction transaction = new Transaction();
        transaction.setAmount(purchasePrice);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus("Pending");

        // Associate the Transaction with the Customer
        transaction.setCustomerId(customerId);

        // Save the Transaction to the database
        transactionRepository.save(transaction);

        return ResponseEntity.ok("Customer created successfully");
    }

    @PostMapping
    public Map<Customer, Map<Month, Integer>> calculation(@RequestBody TimeRangeRequest timeRangeRequest) {
        Map<Customer, Map<Month, Integer>> point= new HashMap<>();
        // Extract time from the request
        LocalDateTime startDateTime = timeRangeRequest.getStartDate();
        LocalDateTime endDateTime = timeRangeRequest.getEndDate();
        TransactionService transactionService = new TransactionService();
        point = transactionService.getCustomerPointsByMonth(startDateTime, endDateTime);
        
        return point;
    }
}
