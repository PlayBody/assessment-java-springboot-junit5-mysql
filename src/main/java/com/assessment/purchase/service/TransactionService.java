package com.assessment.purchase.service;


import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.purchase.model.customer.Customer;
import com.assessment.purchase.model.transaction.Transaction;
import com.assessment.purchase.model.transaction.TransactionRepository;
import com.assessment.purchase.util.RewardPointsCalculator;

@Service
public class TransactionService {

  @Autowired
  TransactionRepository transactionRepository;
  
  @Autowired
  RewardPointsCalculator pointsCalculator;
  
  public Map<Customer, Map<Month, Integer>> getCustomerPointsByMonth(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        
    Map<Customer, Map<Month, Integer>> customerPoints = new HashMap<>();
    
    // Get all transactions between the given dates   
    List<Transaction> transactions = transactionRepository.findByDateBetween(startDateTime, endDateTime);
        
    // Calculate points for each transaction        
    for (Transaction transaction : transactions) {
      int points = pointsCalculator.calculatePoints(transaction.getAmount());

      Customer customer = transaction.getCustomer();
      Month month = transaction.getUpdatedAt().getMonth();
      
      // Add points to customer and month
      customerPoints.computeIfAbsent(customer, k -> new HashMap<>())
        .computeIfAbsent(month, k -> 0);
      
      Map<Month, Integer> monthMap = customerPoints.get(customer);
      monthMap.put(month, monthMap.get(month) + points);
    }
        
    return points;
  }   
}