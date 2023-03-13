package com.iunetworks.services.impl;

import com.iunetworks.LegalEntityAccountsRepository;
import com.iunetworks.LegalEntityCardsRepository;
import com.iunetworks.PhysicalEntityAccountsRepository;
import com.iunetworks.PhysicalEntityCardsRepository;
import com.iunetworks.dtos.legalEntity.LegalEntityCardDto;
import com.iunetworks.dtos.legalEntity.LegalEntityCardTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountTransferDto;
import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardTransferDto;
import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.entities.LegalEntityCard;
import com.iunetworks.entities.PhysicalEntityAccount;
import com.iunetworks.entities.PhysicalEntityCard;
import com.iunetworks.enums.CardStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.services.ILegalEntityCardService;
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
public class LegalEntityCardService implements ILegalEntityCardService {

  private final LegalEntityCardsRepository legalEntityCardsRepository;
  private final LegalEntityAccountsRepository legalEntityAccountsRepository;
  private final PhysicalEntityAccountsRepository physicalEntityAccountsRepository;
  private final PhysicalEntityCardsRepository physicalEntityCardsRepository;
  private final RateService rateService;
  private final ModelMapper modelMapper;

  public LegalEntityCardService(LegalEntityCardsRepository repository, LegalEntityAccountsRepository legalEntityAccountsRepository, LegalEntityCardsRepository legalEntityCardsRepository, PhysicalEntityAccountsRepository physicalEntityAccountsRepository, PhysicalEntityCardsRepository physicalEntityCardsRepository, RateService rateService, ModelMapper modelMapper) {
    this.legalEntityCardsRepository = repository;
    this.legalEntityAccountsRepository = legalEntityAccountsRepository;
    this.physicalEntityAccountsRepository = physicalEntityAccountsRepository;
    this.physicalEntityCardsRepository = physicalEntityCardsRepository;
    this.rateService = rateService;
    this.modelMapper = modelMapper;
  }

  @Override
  public LegalEntityCardDto create(UUID id, LegalEntityCardDto legalEntityCard) {
    if (legalEntityCardsRepository.existsById(legalEntityCard.getId())) {
      throw new ResourceNotFoundException("Card already exists");
    }
    LegalEntityCard savedCard = modelMapper.map(legalEntityCard, LegalEntityCard.class);
    LegalEntityAccount account = legalEntityAccountsRepository.findById(id).get();

    Random random = new Random();
    int cardNumber = random.nextInt(999999999);

    savedCard.setCardNumber(String.valueOf(cardNumber));
    savedCard.setLegalEntityAccount(account);
    return modelMapper.map(legalEntityCardsRepository.save(savedCard), LegalEntityCardDto.class);
  }

  @Override
  public List<LegalEntityCardDto> getAll() {
    return legalEntityCardsRepository.findAll().stream()
      .map(legalEntityCard -> modelMapper.map(legalEntityCard, LegalEntityCardDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public LegalEntityCardDto getById(UUID id) {
    return modelMapper
      .map(legalEntityCardsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)), LegalEntityCardDto.class);
  }

  @Override
  public LegalEntityCardDto update(UUID id, LegalEntityCardDto legalEntityCard) {
    LegalEntityCard entityCard = legalEntityCardsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    entityCard.setAmount(legalEntityCard.getAmount());
    entityCard.setUpdated(LocalDateTime.now());
    return modelMapper.map(legalEntityCardsRepository.save(entityCard), LegalEntityCardDto.class);
  }

  @Override
  public void accept(UUID id) {
    LegalEntityCard legalEntityCard = legalEntityCardsRepository.findById(id).get();
    if (legalEntityCard.getStatus().equals(CardStatus.isActive)) {
      throw new ResourceNotFoundException("THE ACCOUNT IS ACTIVE");
    }
    legalEntityCard.setStatus(CardStatus.isActive);
    legalEntityCardsRepository.save(legalEntityCard);
  }

  @Override
  public void reject(UUID id) {
    LegalEntityCard legalEntityCard = legalEntityCardsRepository.findById(id).get();
    if (legalEntityCard.getStatus().equals(CardStatus.isBlocked)) {
      throw new ResourceNotFoundException("THE CARD IS BLOCKED, PLEASE CONTACT SUPPORT");
    }
    legalEntityCard.setStatus(CardStatus.isBlocked);
    legalEntityCardsRepository.save(legalEntityCard);
  }

  @Override
  public void delete(UUID id) {
    LegalEntityCard legalEntityCard = legalEntityCardsRepository.findById(id).get();
    if (legalEntityCard.getDeleted() != null) {
      throw new ResourceNotFoundException("THE ACCOUNT IS DELETED, PLEASE CONTACT SUPPORT");
    }

    legalEntityCard.setDeleted(LocalDateTime.now());
    legalEntityCardsRepository.save(legalEntityCard);
  }

  @Transactional
  @Override
  public void transfer(LegalEntityCard from, LegalEntityCardTransferDto to, BigDecimal amount) {
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

  private BigDecimal getTransferAmount(BigDecimal amount, Currency in, Currency out) {
    return in.equals(out) ? amount : rateService.getTransferAmount(out, in, amount);
  }

  @Transactional
  @Override
  public void transfer(LegalEntityCard from, PhysicalEntityCardTransferDto to, BigDecimal amount) {
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

  @Transactional
  @Override
  public void transfer(LegalEntityCard from, PhysicalEntityAccountTransferDto to, BigDecimal amount) {
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
