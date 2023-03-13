package com.iunetworks.services;

import com.iunetworks.dtos.legalEntity.LegalEntityCardDto;
import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.LegalEntityCard;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ILegalEntityCardService {

  LegalEntityCardDto create(UUID id, LegalEntityCardDto legalEntityCard);

  List<LegalEntityCardDto> getAll();

  LegalEntityCardDto getById(UUID id);

  LegalEntityCardDto update(UUID id, LegalEntityCardDto legalEntityCard);

  void accept(UUID id);

  void reject(UUID id);

  void delete(UUID id);

  void transfer(LegalEntityCard from, LegalEntityCardTransferDto to, BigDecimal amount);

  void transfer(LegalEntityCard from, PhysicalEntityCardTransferDto to, BigDecimal amount);

  void transfer(LegalEntityCard from, PhysicalEntityAccountTransferDto to, BigDecimal amount);

}
