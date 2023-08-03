package com.assessment.purchase.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.purchase.model.transaction.TransactionRepository;
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
        String customerId = customerRequest.getCustomerId();
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
    public ResponseEntity<String> createCustomer(@RequestBody TimeRangeRequest timeRangeRequest) {
        // Extract time from the request
        LocalDateTime startDateTime = timeRangeRequest.getStartDate();
        LocalDateTime endDateTime = timeRangeRequest.getEndDate();
        TransactionService transactionService = new TransactionService();
        int point = transactionService.getCustomerPointsByMonth(startDateTime, endDateTime);
        
        return point;
    }
}
