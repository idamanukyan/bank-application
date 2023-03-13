package com.iunetworks.dtos.customer;

import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public class CustomerRegistrationDto {

  private UUID loggedUserId;
  private String passportNumber;

  public CustomerRegistrationDto() {
  }

  public UUID getLoggedUserId() {
    return loggedUserId;
  }

  public void setLoggedUserId(UUID loggedUserId) {
    this.loggedUserId = loggedUserId;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }
}
