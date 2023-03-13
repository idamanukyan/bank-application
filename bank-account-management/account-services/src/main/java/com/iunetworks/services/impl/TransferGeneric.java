package com.iunetworks.services.impl;

import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.superClasses.Account;
import com.iunetworks.superClasses.Card;

import java.math.BigDecimal;

public class TransferGeneric<C extends Card, A extends Account> {

  private final RateService rateService;

  public TransferGeneric(RateService rateService) {
    this.rateService = rateService;
  }

  public void transfer(A from, A to, BigDecimal amount) {
    BigDecimal finalAmount = getTransferAmount(from.getBalance(), from.getCurrency(), to.getCurrency());
    if (from.getBalance().compareTo(finalAmount) >= 0) {
      from.getBalance().subtract(amount);
      to.getBalance().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }

  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }

}
