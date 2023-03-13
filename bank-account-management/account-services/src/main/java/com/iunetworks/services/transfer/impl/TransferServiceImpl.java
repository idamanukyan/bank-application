package com.iunetworks.services.transfer.impl;

import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.services.TransferService;
import com.iunetworks.services.transfer.model.AmountTransaction;
import com.iunetworks.services.transfer.strategy.TransferStrategyRegister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

  private final TransferStrategyRegister transferStrategyRegister;

  public TransferServiceImpl(final TransferStrategyRegister transferStrategyRegister) {
    this.transferStrategyRegister = transferStrategyRegister;
  }

  @Override
  @Transactional
  public void transfer(final AmountTransaction amountTransaction) {
    if (amountTransaction.getAmount().signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }
    transferStrategyRegister.byStrategy(amountTransaction.getTransferType())
      .transfer(amountTransaction);
  }
}
