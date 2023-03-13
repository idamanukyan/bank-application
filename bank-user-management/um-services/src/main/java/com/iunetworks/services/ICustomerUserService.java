package com.iunetworks.services;

import com.iunetworks.dtos.userdtos.CustomerUserDto;

import java.util.UUID;

public interface ICustomerUserService {

  CustomerUserDto create(CustomerUserDto customerUserDto);

  CustomerUserDto update(UUID id, CustomerUserDto customerUserDto);


}
