package com.assessment.purchase.model.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;    

import com.assessment.purchase.model.customer.Customer;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  List<Transaction> findByUser(Customer user);

List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
  
}