package com.iunetworks.entities;

import com.iunetworks.superClasses.Card;

import javax.persistence.*;

@Entity
@Table(name = "legal_entity_card")
public class LegalEntityCard extends Card {

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "legal_entity_account_id", nullable = false)
  private LegalEntityAccount legalEntityAccount;

  public LegalEntityAccount getLegalEntityAccount() {
    return legalEntityAccount;
  }

  public void setLegalEntityAccount(LegalEntityAccount legalEntityAccount) {
    this.legalEntityAccount = legalEntityAccount;
  }
}
