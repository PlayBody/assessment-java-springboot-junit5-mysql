package com.assessment.purchase.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.purchase.model.transaction.TransactionRepository;
import com.assessment.purchase.model.transaction.Transaction;
import com.assessment.purchase.model.transaction.TransactionRequest;
import com.assessment.purchase.model.transaction.TimeRangeRequest;
import com.assessment.purchase.model.customer.Customer;
import com.assessment.purchase.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest customerRequest) {
        // Extract data from the request
        long customerId = customerRequest.getCustomerId();
        int purchasePrice = customerRequest.getPurchasePrice();

        // Create a new Transaction instance
        Transaction transaction = new Transaction();
        transaction.setAmount(purchasePrice);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus("Pending");

        // Associate the Transaction with the Customer
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("yourPersistenceUnitName");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "SELECT c FROM Customer c WHERE c.id = :customerId";
        TypedQuery<Customer> query = entityManager.createQuery(queryString, Customer.class);
        query.setParameter("customerId", customerId);

        Customer customer = query.getSingleResult();
        entityManager.close();
        entityManagerFactory.close();
        transaction.setCustomer(customer);

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
