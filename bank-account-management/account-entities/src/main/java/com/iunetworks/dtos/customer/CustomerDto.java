package com.iunetworks.dtos.customer;

import com.iunetworks.dtos.legalEntity.LegalEntityAccountDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {

  private UUID id;
  private UUID loggedUserId;
  private String passportNumber;
  private LegalEntityAccountDto legalAccount;
  private PhysicalEntityAccountDto physicalAccount;
  private LocalDateTime created;
  private LocalDateTime updated;
  private LocalDateTime deleted;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public LegalEntityAccountDto getLegalAccount() {
    return legalAccount;
  }

  public void setLegalAccount(LegalEntityAccountDto legalAccount) {
    this.legalAccount = legalAccount;
  }

  public PhysicalEntityAccountDto getPhysicalAccount() {
    return physicalAccount;
  }

  public void setPhysicalAccount(PhysicalEntityAccountDto physicalAccount) {
    this.physicalAccount = physicalAccount;
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
}
