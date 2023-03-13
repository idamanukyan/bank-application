package com.iunetworks.superClasses;


import com.iunetworks.entities.Customer;
import com.iunetworks.enums.AccountStatus;
import com.iunetworks.enums.Currency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class Account {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "balance")
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private AccountStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency")
  private Currency currency;

  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  @Column(name = "deleted")
  private LocalDateTime deleted;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  public Account() {
  }

  public Account(UUID id, BigDecimal balance, Currency currency, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted, Customer customer, String accountNumber) {
    this.id = id;
    this.balance = balance;
    this.currency = currency;
    this.created = created;
    this.updated = updated;
    this.deleted = deleted;
    this.customer = customer;
    this.accountNumber = accountNumber;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public LocalDateTime getDeleted() {
    return deleted;
  }

  public void setDeleted(LocalDateTime deleted) {
    this.deleted = deleted;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @PrePersist
  protected void onCreate() {
    this.created = LocalDateTime.now();
    this.updated = this.created;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated = LocalDateTime.now();
  }

}
