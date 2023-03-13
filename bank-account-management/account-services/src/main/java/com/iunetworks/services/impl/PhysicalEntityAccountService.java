package com.iunetworks.services.impl;

import com.iunetworks.*;
import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.*;
import com.iunetworks.enums.AccountStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.services.IPhysicalEntityAccountService;
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
public class PhysicalEntityAccountService implements IPhysicalEntityAccountService {

  private final PhysicalEntityAccountsRepository physicalEntityAccountsRepository;
  private final LegalEntityAccountsRepository legalEntityAccountsRepository;
  private final PhysicalEntityCardsRepository physicalEntityCardsRepository;
  private final LegalEntityCardsRepository legalEntityCardsRepository;
  private final RateService rateService;
  private final CustomersRepository customersRepository;
  private final ModelMapper modelMapper;

  public PhysicalEntityAccountService(PhysicalEntityAccountsRepository physicalEntityAccountsRepository, LegalEntityAccountsRepository legalEntityAccountsRepository, PhysicalEntityCardsRepository physicalEntityCardsRepository, LegalEntityCardsRepository legalEntityCardsRepository, RateService rateService, CustomersRepository customersRepository, ModelMapper modelMapper) {
    this.physicalEntityAccountsRepository = physicalEntityAccountsRepository;
    this.legalEntityAccountsRepository = legalEntityAccountsRepository;
    this.physicalEntityCardsRepository = physicalEntityCardsRepository;
    this.legalEntityCardsRepository = legalEntityCardsRepository;
    this.rateService = rateService;
    this.customersRepository = customersRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public PhysicalEntityAccountDto create(UUID id, PhysicalEntityAccountDto physicalEntityAccountDto) {
    if (physicalEntityAccountsRepository.existsById(physicalEntityAccountDto.getId())) {
      throw new ResourceNotFoundException("Account already exists");
    }
    PhysicalEntityAccount savedAccount = modelMapper.map(physicalEntityAccountDto, PhysicalEntityAccount.class);
    Customer customer = customersRepository.findById(id).get();

    Random random = new Random();
    int accountNumber = random.nextInt(999999999);

    savedAccount.setAccountNumber(String.valueOf(accountNumber));
    savedAccount.setCustomer(customer);
    return modelMapper.map(physicalEntityAccountsRepository.save(savedAccount), PhysicalEntityAccountDto.class);
  }

  @Override
  public List<PhysicalEntityAccountDto> getAll() {
    return physicalEntityAccountsRepository.findAll().stream()
      .map(physicalEntityAccount -> modelMapper.map(physicalEntityAccount, PhysicalEntityAccountDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public PhysicalEntityAccountDto getById(UUID id) {
    return modelMapper
      .map(physicalEntityAccountsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)), PhysicalEntityAccountDto.class);
  }

  @Override
  public PhysicalEntityAccountDto update(UUID id, PhysicalEntityAccountDto physicalEntityAccountDto) {
    PhysicalEntityAccount physicalEntityAccount = physicalEntityAccountsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    physicalEntityAccount.setBalance(physicalEntityAccountDto.getBalance());
    physicalEntityAccount.setUpdated(LocalDateTime.now());
    return modelMapper.map(physicalEntityAccountsRepository.save(physicalEntityAccount), PhysicalEntityAccountDto.class);
  }

  @Override
  public void accept(UUID id) {
    PhysicalEntityAccount account = physicalEntityAccountsRepository.findById(id).get();
    if (account.getStatus().equals(AccountStatus.ACTIVE)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS ACTIVE");
    }
    account.setStatus(AccountStatus.ACTIVE);
    physicalEntityAccountsRepository.save(account);
  }

  @Override
  public void reject(UUID id) {
    PhysicalEntityAccount account = physicalEntityAccountsRepository.findById(id).get();
    if (account.getStatus().equals(AccountStatus.REJECTED)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS REJECTED, PLEASE CONTACT SUPPORT");
    }
    account.setStatus(AccountStatus.REJECTED);
    physicalEntityAccountsRepository.save(account);
  }

  @Override
  public void delete(UUID id) {
    PhysicalEntityAccount account = physicalEntityAccountsRepository.findById(id).get();
    if (account.getDeleted() != null) {
      throw new ResourceNotFoundException("THE ACCOUNT IS DELETED, PLEASE CONTACT SUPPORT");
    }

    account.setDeleted(LocalDateTime.now());
    physicalEntityAccountsRepository.save(account);
  }

  @Transactional
  @Override
  public void transfer(PhysicalEntityAccount from, PhysicalEntityAccountTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    PhysicalEntityAccount entityAccount = physicalEntityAccountsRepository.findByAccountNumber(to.getAccountNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getAccountNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityAccount.getCurrency());
    if (from.getBalance().compareTo(finalAmount) >= 0) {
      from.getBalance().subtract(amount);
      entityAccount.getBalance().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }



  @Transactional
  @Override
  public void transfer(PhysicalEntityAccount from, PhysicalEntityCardTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findByCardNumber(to.getCardNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getCardNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityCard.getCurrency());
    if (from.getBalance().compareTo(finalAmount) >= 0) {
      from.getBalance().subtract(amount);
      entityCard.getAmount().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }

  @Transactional
  @Override
  public void transfer(PhysicalEntityAccount from, LegalEntityCardTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    LegalEntityCard entityCard = legalEntityCardsRepository.findByCardNumber(to.getCardNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getCardNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityCard.getCurrency());
    if (from.getBalance().compareTo(finalAmount) >= 0) {
      from.getBalance().subtract(amount);
      entityCard.getAmount().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }
}
