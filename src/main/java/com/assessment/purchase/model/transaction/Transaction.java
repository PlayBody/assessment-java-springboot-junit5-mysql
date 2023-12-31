package com.assessment.purchase.model.transaction;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.assessment.purchase.model.customer.Customer;


@Entity(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne      
  @JoinColumn(name = "customer_id")
  private Customer customer;

  private int amount;

  @Column(updatable=false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private String status;

  public Transaction() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}