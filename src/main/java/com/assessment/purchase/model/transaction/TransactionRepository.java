package com.assessment.purchase.model.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;  

import com.assessment.purchase.model.customer.Customer;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  List<Transaction> findByUser(Customer user);

  @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :startDate AND t.createdAt <= :endDate")
  List<Transaction> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
  
}