package com.iunetworks.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "customers")

public class Customer {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "loggedUser_id", updatable = false)
  private UUID loggedUserId;

  @Column(name = "passport_number", updatable = false, nullable = false)
  private String passportNumber;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "legal_account_id")
  private LegalEntityAccount legalAccount;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "physical_account_id")
  private PhysicalEntityAccount physicalAccount;

  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  @Column(name = "deleted")
  private LocalDateTime deleted;

  public Customer() {
  }

  public Customer(UUID id, UUID loggedUserId, String passportNumber, LegalEntityAccount legalAccount, PhysicalEntityAccount physicalAccount, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted) {
    this.id = id;
    this.loggedUserId = loggedUserId;
    this.passportNumber = passportNumber;
    this.legalAccount = legalAccount;
    this.physicalAccount = physicalAccount;
    this.created = created;
    this.updated = updated;
    this.deleted = deleted;
  }

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

  public LegalEntityAccount getLegalAccount() {
    return legalAccount;
  }

  public void setLegalAccount(LegalEntityAccount legalAccount) {
    this.legalAccount = legalAccount;
  }

  public PhysicalEntityAccount getPhysicalAccount() {
    return physicalAccount;
  }

  public void setPhysicalAccount(PhysicalEntityAccount physicalAccount) {
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
