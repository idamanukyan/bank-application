package com.iunetworks.superClasses;

import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.CardStatus;
import com.iunetworks.enums.CardType;
import com.iunetworks.enums.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "card_number", nullable = false)
  private String cardNumber;

  @Column(name = "cardLevel", nullable = false)
  @Enumerated(EnumType.STRING)
  private CardLevel cardLevel;

  @Column(name = "cardType", nullable = false)
  @Enumerated(EnumType.STRING)
  private CardType cardType;

  @Column(name = "amount")
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency")
  private Currency currency;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CardStatus status;

  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  @Column(name = "deleted")
  private LocalDateTime deleted;

  public Card() {
  }

  public Card(UUID id, CardLevel cardLevel, CardType cardType, BigDecimal amount, CardStatus status, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted) {
    this.id = id;
    this.cardLevel = cardLevel;
    this.cardType = cardType;
    this.amount = amount;
    this.status = status;
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

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
