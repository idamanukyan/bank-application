package com.iunetworks.services.impl;

import com.iunetworks.CustomersRepository;
import com.iunetworks.dtos.customer.CustomerDto;
import com.iunetworks.dtos.customer.CustomerRegistrationDto;
import com.iunetworks.dtos.customer.CustomerUserDto;
import com.iunetworks.entities.Customer;
import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.entities.PhysicalEntityAccount;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.security.CurrentUser;
import com.iunetworks.services.ICustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerService implements ICustomerService {

  final Logger log = LoggerFactory.getLogger(CustomerService.class);
  private final WebClient.Builder webClientBuilder;

  private final CustomersRepository customersRepository;
  private final ModelMapper modelMapper;

  public CustomerService(WebClient.Builder webClientBuilder, CustomersRepository customersRepository, ModelMapper modelMapper) {
    this.webClientBuilder = webClientBuilder;
    this.customersRepository = customersRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CustomerDto create(CustomerRegistrationDto customer) {
    Customer savedCustomer = modelMapper.map(customer, Customer.class);
    UUID loggedId;
    try {
      loggedId = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    } catch (Exception e) {
      throw new ResourceNotFoundException("JWT is missing");
    }
    savedCustomer.setLoggedUserId(loggedId);
    if (customersRepository.existsByLoggedUserIdAndDeletedIsNull(loggedId))
      throw new ApplicationContextException("User is already present");

    customersRepository.save(savedCustomer);
    return modelMapper.map(savedCustomer, CustomerDto.class);

  }

  @Override
  public List<CustomerDto> getAll() {
    return customersRepository.findAll().stream()
      .map(customer -> modelMapper.map(customer, CustomerDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public CustomerDto getById(UUID id) {
    return modelMapper
      .map(customersRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)), CustomerDto.class);
  }

  @Override
  public CustomerDto update(UUID id, CustomerDto customerDto) {
    Customer customer = customersRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    customer.setPassportNumber(customerDto.getPassportNumber());
    customer.setLegalAccount(modelMapper.map(customerDto.getLegalAccount(), LegalEntityAccount.class));
    customer.setPhysicalAccount(modelMapper.map(customerDto.getPhysicalAccount(), PhysicalEntityAccount.class));
    return modelMapper.map(customersRepository.save(customer), CustomerDto.class);
  }

  @Override
  public void delete(UUID id) {
    Customer customer = customersRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with id : {%s} not found", id.toString())));
    customer.setDeleted(LocalDateTime.now());
  }

  public String getCustomerNameById(UUID id) {
    Customer customer = customersRepository.findByIdAndDeletedIsNull(id).get();
    UUID loggedUserId = customer.getLoggedUserId();
    CustomerUserDto customerUserDto = webClientBuilder
      .baseUrl("http://bank-user-management-application/")
      .build()
      .get()

      .uri("/bank-users/customer/{loggedUserId}", loggedUserId)
      .retrieve()
      .bodyToMono(CustomerUserDto.class)
      .block();
    assert customerUserDto != null;
    return customerUserDto.getName();
  }
}
