package com.iunetworks.services;

import com.iunetworks.dtos.customer.CustomerDto;
import com.iunetworks.dtos.customer.CustomerRegistrationDto;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {

  CustomerDto create(CustomerRegistrationDto customer);

  List<CustomerDto> getAll();

  CustomerDto getById(UUID id);

  CustomerDto update(UUID id, CustomerDto customer);

  void delete(UUID id);

}
