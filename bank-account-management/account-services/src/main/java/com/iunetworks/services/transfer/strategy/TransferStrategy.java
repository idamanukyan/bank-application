package com.iunetworks.services.transfer.strategy;

import com.iunetworks.services.transfer.model.AmountTransaction;
import com.iunetworks.services.transfer.model.AmountTransferType;

public interface TransferStrategy {

  void transfer(AmountTransaction amountTransaction);

  AmountTransferType byType();

}
