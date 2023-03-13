package com.iunetworks.dtos.legalEntity;

import com.iunetworks.dtos.customer.CustomerRegistrationDto;
import com.iunetworks.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

public class LegalEntityAccountRegistrationDto {

  private UUID id;
  private BigDecimal balance;
  private Currency currency;
  private String companyName;
  private CustomerRegistrationDto customerDto;

  public LegalEntityAccountRegistrationDto() {
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

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public CustomerRegistrationDto getCustomerDto() {
    return customerDto;
  }

  public void setCustomerDto(CustomerRegistrationDto customerDto) {
    this.customerDto = customerDto;
  }
}
