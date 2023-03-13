package com.iunetworks.services;

import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.PhysicalEntityCard;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IPhysicalEntityCardService {

  PhysicalEntityCardDto create(UUID id, PhysicalEntityCardDto cardDto);

  List<PhysicalEntityCardDto> getAll();

  PhysicalEntityCardDto getById(UUID id);

  PhysicalEntityCardDto update(UUID id, PhysicalEntityCardDto cardDto);

  void accept(UUID id);

  void reject(UUID id);

  void delete(UUID id);

  void transfer(PhysicalEntityCard from, PhysicalEntityCardTransferDto to, BigDecimal amount);

  void transfer(PhysicalEntityCard from, LegalEntityCardTransferDto to, BigDecimal amount);

  void transfer(PhysicalEntityCard from, PhysicalEntityAccountTransferDto to, BigDecimal amount);

}
