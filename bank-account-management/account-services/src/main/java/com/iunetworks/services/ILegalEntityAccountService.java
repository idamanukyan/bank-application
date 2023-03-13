package com.iunetworks.services;

import com.iunetworks.services.transfer.model.AmountTransaction;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountDto;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountRegistrationDto;

import java.util.List;
import java.util.UUID;

public interface ILegalEntityAccountService {

  LegalEntityAccountDto create(UUID id, LegalEntityAccountRegistrationDto legalEntityAccount);

  List<LegalEntityAccountDto> getAll();

  LegalEntityAccountDto getById(UUID id);

  LegalEntityAccountDto update(UUID id, LegalEntityAccountRegistrationDto legalEntityAccount);

  void accept(UUID id);

  void reject(UUID id);

  void delete(UUID id);

  void transferToAccount(AmountTransaction request);

  void transferToCard(AmountTransaction request);


}
