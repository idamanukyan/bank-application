package com.iunetworks.services.transfer.strategy.impl;

import com.iunetworks.PhysicalEntityAccountsRepository;
import com.iunetworks.entities.PhysicalEntityAccount;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.services.impl.RateService;
import com.iunetworks.services.transfer.model.AmountTransaction;
import com.iunetworks.services.transfer.model.AmountTransferType;
import com.iunetworks.services.transfer.strategy.TransferStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class PhysicalAccountToPhysicalAccountStrategy implements TransferStrategy {

  private final RateService rateService;

  private final PhysicalEntityAccountsRepository physicalEntityAccountsRepository;

  public PhysicalAccountToPhysicalAccountStrategy(final PhysicalEntityAccountsRepository physicalEntityAccountsRepository,
                                                  final RateService rateService) {
    this.physicalEntityAccountsRepository = physicalEntityAccountsRepository;
    this.rateService = rateService;
  }

  @Override
  @Transactional
  public void transfer(final AmountTransaction amountTransaction) {

    PhysicalEntityAccount to = physicalEntityAccountsRepository.findByAccountNumber(amountTransaction.getTo())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getTo())));

    PhysicalEntityAccount from = physicalEntityAccountsRepository.findByAccountNumber(amountTransaction.getFrom())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getFrom())));

    BigDecimal finalAmount = getTransferAmount(amountTransaction.getAmount(), from.getCurrency(), to.getCurrency());
    if (from.getBalance().compareTo(finalAmount) >= 0) {
      from.setBalance(from.getBalance().subtract(amountTransaction.getAmount()));
      to.setBalance(to.getBalance().add(finalAmount));
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
    physicalEntityAccountsRepository.save(to);
    physicalEntityAccountsRepository.save(from);
  }

  @Override
  public AmountTransferType byType() {
    return AmountTransferType.PHYSICAL_ACCOUNT_TO_PHYSICAL_ACCOUNT;
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }
}
