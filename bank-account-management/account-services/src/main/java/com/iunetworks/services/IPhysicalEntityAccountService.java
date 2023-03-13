package com.iunetworks.services;

import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.PhysicalEntityAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IPhysicalEntityAccountService {

  PhysicalEntityAccountDto create(UUID id, PhysicalEntityAccountDto physicalEntityAccountDto);

  List<PhysicalEntityAccountDto> getAll();

  PhysicalEntityAccountDto getById(UUID id);

  PhysicalEntityAccountDto update(UUID id, PhysicalEntityAccountDto physicalEntityAccountDto);

  void accept(UUID id);

  void reject(UUID id);

  void delete(UUID id);

  void transfer(PhysicalEntityAccount from, PhysicalEntityAccountTransferDto to, BigDecimal amount);

  void transfer(PhysicalEntityAccount from, PhysicalEntityCardTransferDto to, BigDecimal amount);

  void transfer(PhysicalEntityAccount from, LegalEntityCardTransferDto to, BigDecimal amount);

}
