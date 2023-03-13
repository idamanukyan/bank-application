package com.iunetworks.dtos.legalEntity;

import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.CardStatus;
import com.iunetworks.enums.CardType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class LegalEntityCardDto {

  private UUID id;
  private CardLevel cardLevel;
  private CardType cardType;
  private BigDecimal amount;
  private CardStatus status;
  private LocalDateTime created;
  private LocalDateTime updated;
  private LocalDateTime deleted;
  private LegalEntityAccount legalEntityAccount;

  public LegalEntityCardDto() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CardLevel getCardLevel() {
    return cardLevel;
  }

  public void setCardLevel(CardLevel cardLevel) {
    this.cardLevel = cardLevel;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public CardStatus getStatus() {
    return status;
  }

  public void setStatus(CardStatus status) {
    this.status = status;
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

  public LegalEntityAccount getLegalEntityAccount() {
    return legalEntityAccount;
  }

  public void setLegalEntityAccount(LegalEntityAccount legalEntityAccount) {
    this.legalEntityAccount = legalEntityAccount;
  }
}
