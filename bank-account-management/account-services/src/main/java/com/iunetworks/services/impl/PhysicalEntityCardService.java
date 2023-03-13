package com.iunetworks.services.impl;

import com.iunetworks.LegalEntityAccountsRepository;
import com.iunetworks.LegalEntityCardsRepository;
import com.iunetworks.PhysicalEntityAccountsRepository;
import com.iunetworks.PhysicalEntityCardsRepository;
import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.entities.LegalEntityCard;
import com.iunetworks.entities.PhysicalEntityAccount;
import com.iunetworks.entities.PhysicalEntityCard;
import com.iunetworks.enums.CardStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.services.IPhysicalEntityCardService;
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
public class PhysicalEntityCardService implements IPhysicalEntityCardService {

  private final PhysicalEntityCardsRepository physicalEntityCardsRepository;
  private final PhysicalEntityAccountsRepository physicalEntityAccountsRepository;
  private final LegalEntityAccountsRepository legalEntityAccountsRepository;
  private final LegalEntityCardsRepository legalEntityCardsRepository;
  private final RateService rateService;
  private final ModelMapper modelMapper;

  public PhysicalEntityCardService(PhysicalEntityCardsRepository physicalEntityCardsRepository, PhysicalEntityAccountsRepository physicalEntityAccountsRepository, LegalEntityAccountsRepository legalEntityAccountsRepository, LegalEntityCardsRepository legalEntityCardsRepository, RateService rateService, ModelMapper modelMapper) {
    this.physicalEntityCardsRepository = physicalEntityCardsRepository;
    this.physicalEntityAccountsRepository = physicalEntityAccountsRepository;
    this.legalEntityAccountsRepository = legalEntityAccountsRepository;
    this.legalEntityCardsRepository = legalEntityCardsRepository;
    this.rateService = rateService;
    this.modelMapper = modelMapper;
  }

  @Override
  public PhysicalEntityCardDto create(UUID id, PhysicalEntityCardDto cardDto) {
    if (physicalEntityCardsRepository.existsById(cardDto.getId())) {
      throw new ResourceNotFoundException("Card already exists");
    }
    PhysicalEntityCard savedCard = modelMapper.map(cardDto, PhysicalEntityCard.class);
    PhysicalEntityAccount account = physicalEntityAccountsRepository.findById(id).get();

    Random random = new Random();
    int cardNumber = random.nextInt(999999999);

    savedCard.setCardNumber(String.valueOf(cardNumber));
    savedCard.setPhysicalEntityAccount(account);
    return modelMapper.map(physicalEntityCardsRepository.save(savedCard), PhysicalEntityCardDto.class);
  }

  @Override
  public List<PhysicalEntityCardDto> getAll() {
    return physicalEntityCardsRepository.findAll().stream()
      .map(physicalEntityCard -> modelMapper.map(physicalEntityCard, PhysicalEntityCardDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public PhysicalEntityCardDto getById(UUID id) {
    return modelMapper
      .map(physicalEntityCardsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)), PhysicalEntityCardDto.class);
  }

  @Override
  public PhysicalEntityCardDto update(UUID id, PhysicalEntityCardDto cardDto) {
    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    entityCard.setAmount(cardDto.getAmount());
    entityCard.setUpdated(LocalDateTime.now());
    return modelMapper.map(physicalEntityCardsRepository.save(entityCard), PhysicalEntityCardDto.class);
  }

  @Override
  public void accept(UUID id) {
    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findById(id).get();
    if (entityCard.getStatus().equals(CardStatus.isActive)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS ACTIVE");
    }
    entityCard.setStatus(CardStatus.isActive);
    physicalEntityCardsRepository.save(entityCard);
  }

  @Override
  public void reject(UUID id) {
    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findById(id).get();
    if (entityCard.getStatus().equals(CardStatus.isBlocked)) {
      throw new ResourceNotFoundException("THE CARD IS BLOCKED, PLEASE CONTACT SUPPORT");
    }
    entityCard.setStatus(CardStatus.isBlocked);
    physicalEntityCardsRepository.save(entityCard);
  }

  @Override
  public void delete(UUID id) {
    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findById(id).get();
    if (entityCard.getDeleted() != null) {
      throw new ResourceNotFoundException("THE ACCOUNT IS DELETED, PLEASE CONTACT SUPPORT");
    }

    entityCard.setDeleted(LocalDateTime.now());
    physicalEntityCardsRepository.save(entityCard);
  }

  @Transactional
  @Override
  public void transfer(PhysicalEntityCard from, PhysicalEntityCardTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    PhysicalEntityCard entityCard = physicalEntityCardsRepository.findByCardNumber(to.getCardNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getCardNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityCard.getCurrency());
    if (from.getAmount().compareTo(finalAmount) >= 0) {
      from.getAmount().subtract(amount);
      entityCard.getAmount().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }

  @Transactional
  @Override
  public void transfer(PhysicalEntityCard from, LegalEntityCardTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    LegalEntityCard entityCard = legalEntityCardsRepository.findByCardNumber(to.getCardNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getCardNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityCard.getCurrency());
    if (from.getAmount().compareTo(finalAmount) >= 0) {
      from.getAmount().subtract(amount);
      entityCard.getAmount().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }


  @Transactional
  @Override
  public void transfer(PhysicalEntityCard from, PhysicalEntityAccountTransferDto to, BigDecimal amount) {
    if (amount.signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }

    PhysicalEntityAccount entityAccount = physicalEntityAccountsRepository.findByAccountNumber(to.getAccountNumber())
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with account number : {%s} not found", to.getAccountNumber())));

    BigDecimal finalAmount = getTransferAmount(amount, from.getCurrency(), entityAccount.getCurrency());
    if (from.getAmount().compareTo(finalAmount) >= 0) {
      from.getAmount().subtract(amount);
      entityAccount.getBalance().add(finalAmount);
    } else {
      throw new ResourceNotFoundException("Your account balance is not sufficient");
    }
  }
}
