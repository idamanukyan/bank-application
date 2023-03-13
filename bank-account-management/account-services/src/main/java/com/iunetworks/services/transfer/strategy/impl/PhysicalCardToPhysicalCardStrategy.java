package com.iunetworks.services.transfer.strategy.impl;

import com.iunetworks.PhysicalEntityCardsRepository;
import com.iunetworks.entities.PhysicalEntityCard;
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
public class PhysicalCardToPhysicalCardStrategy implements TransferStrategy {

  private final RateService rateService;

  private final PhysicalEntityCardsRepository physicalEntityCardsRepository;

  public PhysicalCardToPhysicalCardStrategy(final PhysicalEntityCardsRepository physicalEntityCardsRepository,
                                            final RateService rateService) {
    this.physicalEntityCardsRepository = physicalEntityCardsRepository;
    this.rateService = rateService;
  }

  @Override
  @Transactional
  public void transfer(final AmountTransaction amountTransaction) {

    PhysicalEntityCard to = physicalEntityCardsRepository.findByCardNumber(amountTransaction.getTo())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getTo())));

    PhysicalEntityCard from = physicalEntityCardsRepository.findByCardNumber(amountTransaction.getFrom())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", amountTransaction.getFrom())));

    BigDecimal finalAmount = getTransferAmount(amountTransaction.getAmount(), from.getCurrency(), to.getCurrency());
    if (from.getAmount().compareTo(finalAmount) >= 0) {
      from.setAmount(from.getAmount().subtract(amountTransaction.getAmount()));
      to.setAmount(to.getAmount().add(finalAmount));
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
    physicalEntityCardsRepository.save(to);
    physicalEntityCardsRepository.save(from);
  }

  @Override
  public AmountTransferType byType() {
    return AmountTransferType.PHYSICAL_CARD_TO_PHYSICAL_CARD;
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }
}
