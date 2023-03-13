package com.iunetworks.services.impl;

import com.iunetworks.*;
import com.iunetworks.services.transfer.model.AmountTransaction;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountDto;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountRegistrationDto;
import com.iunetworks.entities.Customer;
import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.enums.AccountStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.services.ILegalEntityAccountService;
import com.iunetworks.superClasses.Account;
import com.iunetworks.superClasses.Card;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LegalEntityAccountService implements ILegalEntityAccountService {

  private final LegalEntityAccountsRepository legalEntityAccountsRepository;
  private final PhysicalEntityAccountsRepository physicalEntityAccountsRepository;
  private final LegalEntityCardsRepository legalEntityCardsRepository;
  private final PhysicalEntityCardsRepository physicalEntityCardsRepository;
  private final CustomersRepository customersRepository;
  private final ModelMapper modelMapper;
  private final RateService rateService;

  public LegalEntityAccountService(LegalEntityAccountsRepository legalEntityAccountsRepository, PhysicalEntityAccountsRepository physicalEntityAccountsRepository, LegalEntityCardsRepository legalEntityCardsRepository, PhysicalEntityCardsRepository physicalEntityCardsRepository, CustomersRepository customersRepository, ModelMapper modelMapper, RateService rateService) {
    this.legalEntityAccountsRepository = legalEntityAccountsRepository;
    this.physicalEntityAccountsRepository = physicalEntityAccountsRepository;
    this.legalEntityCardsRepository = legalEntityCardsRepository;
    this.physicalEntityCardsRepository = physicalEntityCardsRepository;
    this.customersRepository = customersRepository;
    this.modelMapper = modelMapper;
    this.rateService = rateService;
  }


  @Override
  public LegalEntityAccountDto create(UUID id, LegalEntityAccountRegistrationDto legalEntityAccount) {
    if (legalEntityAccountsRepository.existsById(id)) {
      throw new ResourceNotFoundException("Account already exists");
    }
    LegalEntityAccount savedAccount = modelMapper.map(legalEntityAccount, LegalEntityAccount.class);
    Customer customer = customersRepository.findById(id).get();

    Random random = new Random();
    int accountNumber = random.nextInt(999999999);

    savedAccount.setAccountNumber(String.valueOf(accountNumber));
    savedAccount.setCustomer(customer);
    return modelMapper.map(legalEntityAccountsRepository.save(savedAccount), LegalEntityAccountDto.class);

  }

  @Override
  public List<LegalEntityAccountDto> getAll() {
    return legalEntityAccountsRepository.findAll().stream()
      .map(legalEntityAccount -> modelMapper.map(legalEntityAccount, LegalEntityAccountDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public LegalEntityAccountDto getById(UUID id) {
    return modelMapper
      .map(legalEntityAccountsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)), LegalEntityAccountDto.class);
  }

  @Override
  public LegalEntityAccountDto update(UUID id, LegalEntityAccountRegistrationDto legalEntityAccount) {
    LegalEntityAccount entityAccount = legalEntityAccountsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    entityAccount.setBalance(legalEntityAccount.getBalance());
    entityAccount.setUpdated(LocalDateTime.now());
    return modelMapper.map(legalEntityAccountsRepository.save(entityAccount), LegalEntityAccountDto.class);
  }

  @Override
  public void accept(UUID id) {
    LegalEntityAccount legalAccount = legalEntityAccountsRepository.findById(id).get();
    if (legalAccount.getStatus().equals(AccountStatus.ACTIVE)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS ACTIVE");
    }
    legalAccount.setStatus(AccountStatus.ACTIVE);
    legalEntityAccountsRepository.save(legalAccount);

  }

  @Override
  public void reject(UUID id) {
    LegalEntityAccount legalAccount = legalEntityAccountsRepository.findById(id).get();
    if (legalAccount.getStatus().equals(AccountStatus.REJECTED)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS REJECTED, PLEASE CONTACT SUPPORT");
    }
    legalAccount.setStatus(AccountStatus.REJECTED);
    legalEntityAccountsRepository.save(legalAccount);
  }

  @Override
  public void delete(UUID id) {
    LegalEntityAccount legalAccount = legalEntityAccountsRepository.findById(id).get();
    if (legalAccount.getDeleted() != null) {
      throw new ResourceNotFoundException("THE ACCOUNT IS DELETED, PLEASE CONTACT SUPPORT");
    }

    legalAccount.setDeleted(LocalDateTime.now());
    legalEntityAccountsRepository.save(legalAccount);
  }

  @Transactional
  @Override
  public void transferToAccount(AmountTransaction request) {
    if (request.getAmount().signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    LegalEntityAccount entityAccountFrom = legalEntityAccountsRepository.findByAccountNumber(request.getFrom())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", request.getFrom())));

    LegalEntityAccount entityAccountTo = legalEntityAccountsRepository.findByAccountNumber(request.getTo())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", request.getTo())));

  }

  @Override
  public void transferToCard(AmountTransaction request) {

  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }


}
