package com.iunetworks.entities;

import com.iunetworks.superClasses.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "legal_entity_accounts")

public class LegalEntityAccount extends Account {

  @Column(name = "company_name", nullable = false)
  private String companyName;

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }


}
