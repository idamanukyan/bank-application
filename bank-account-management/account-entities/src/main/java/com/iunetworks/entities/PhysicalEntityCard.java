package com.iunetworks.entities;

import com.iunetworks.superClasses.Card;

import javax.persistence.*;

@Entity
@Table(name = "physical_entity_card")
public class PhysicalEntityCard extends Card {

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "physical_entity_account_id", nullable = false)
  private PhysicalEntityAccount physicalEntityAccount;

  public PhysicalEntityAccount getPhysicalEntityAccount() {
    return physicalEntityAccount;
  }

  public void setPhysicalEntityAccount(PhysicalEntityAccount physicalEntityAccount) {
    this.physicalEntityAccount = physicalEntityAccount;
  }
}
