package com.iunetworks.dtos.physicalEntity;

import com.iunetworks.dtos.customer.CustomerDto;
import com.iunetworks.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PhysicalEntityAccountDto {


  private UUID id;
  private BigDecimal balance;
  private Currency currency;
  private LocalDateTime created;
  private LocalDateTime updated;
  private LocalDateTime deleted;
  private CustomerDto customer;

  public PhysicalEntityAccountDto() {
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

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }
}
