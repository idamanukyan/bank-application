package com.iunetworks.services;

import com.iunetworks.services.transfer.model.AmountTransaction;

public interface TransferService {

  void transfer(AmountTransaction amountTransaction);
}
